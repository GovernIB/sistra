package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.NifCif;
import es.caib.util.NormalizacionNombresUtil;
import es.caib.util.ValidacionesUtil;
import es.caib.zonaper.model.Persona;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.util.ConfigurationUtil;


/**
 * SessionBean que sirve de interfaz con la Aplicación PAD 
 *
 * @ejb.bean
 *  name="zonaper/persistence/PadAplicacionFacade"
 *  jndi-name="es.caib.zonaper.persistence.PadAplicacionFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleAuto" type="java.lang.String" value="${role.auto}"
 * @ejb.env-entry name="roleDelegacion" type="java.lang.String" value="${role.delegacion}"
 * @ejb.env-entry name="roleHelpdesk" type="java.lang.String" value="${role.helpdesk}"
 * @ejb.env-entry name="roleGestor" type="java.lang.String" value="${role.gestor}"
 */
public abstract class PadAplicacionFacadeEJB extends HibernateEJB {

	private static Log log = LogFactory.getLog(PadAplicacionFacadeEJB.class);
	private String roleAuto;
	private String roleDelegacion;
	private String roleHelpdesk;
	private String roleGestor;
	private String prefijoAuto;
			
    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }

    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void ejbCreate() throws CreateException 
    {
        super.ejbCreate();
        try{
			InitialContext initialContext = new InitialContext();			 
			roleAuto = (( String ) initialContext.lookup( "java:comp/env/roleAuto" ));			
			roleDelegacion = (( String ) initialContext.lookup( "java:comp/env/roleDelegacion" ));
			roleHelpdesk = (( String ) initialContext.lookup( "java:comp/env/roleHelpdesk" ));
			roleGestor = (( String ) initialContext.lookup( "java:comp/env/roleGestor" ));
			
			// Obtenemos prefijo para alta automatica
    		String prefijo = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("usuario.prefijoAuto");
    		if (StringUtils.isBlank(prefijo)) {
    			throw new Exception("No se ha configurado prefijo para el alta automatica de usuario");
    		}
    		prefijoAuto = StringUtils.trim(prefijo);			
		}catch(Exception ex){
			log.error(ex);
		}
    }
	  
    /**
     * Obtiene datos persona almacenados en PAD buscando por usuario.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporUsuario( String usuario ) throws ExcepcionPAD
    {
    	try
    	{
    		Persona persona = obtenerDatosPersonaPorUsuarioSeycon( usuario );
    		
    		// Solo puede acceder el propio usuario o el usuario auto o un usuario con rol delegacion o rol gestor
   			if (!this.ctx.isCallerInRole(roleGestor) && !this.ctx.isCallerInRole(roleDelegacion) && !this.ctx.isCallerInRole(roleAuto) && !usuario.equals(this.ctx.getCallerPrincipal().getName())){
    			// Comprobamos si es un delegado de la entidad
    			String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(persona.getDocumentoIdLegal());
    			if (StringUtils.isEmpty(permisos)){
    				throw new Exception("Solo puede acceder el propio usuario, un delegado, el usuario auto o un usuario con rol delegación o rol gestor");
    			}
    		}
    		
    		return personaToPersonaPAD( persona );
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}
    }
    
    
    /**
     * Obtiene datos persona almacenados en PAD buscando por nif
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporNif( String nif ) throws ExcepcionPAD
    {
    	try
    	{
    		// Solo puede acceder el propio usuario, el usuario auto o un usuario delegado
    		Principal sp = this.ctx.getCallerPrincipal();
    		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    		if (!this.ctx.isCallerInRole(roleGestor) && !this.ctx.isCallerInRole(roleDelegacion) && !this.ctx.isCallerInRole(roleAuto) && ! plgLogin.getNif(sp).equalsIgnoreCase(nif)){
    			// Comprobamos si es un delegado de la entidad
    			String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nif);
    			if (StringUtils.isEmpty(permisos)){
    				throw new Exception("Solo puede acceder el propio usuario, un delegado, el usuario auto o un usuario con rol delegación o role gestor");
    		}
    		
    		}
    		Persona persona = this.obtenerDatosPersonaPorDocumentoIdentificacionLegal( nif );
    		return personaToPersonaPAD( persona );
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}
    	
    }
    
    /**
     * Obtiene datos persona almacenados en PAD buscando por nif
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */    
    public boolean existePersonaPADporUsuario( String usuario ) throws ExcepcionPAD
    {
    	try
    	{
    		Persona persona = this.obtenerDatosPersonaPorUsuarioSeycon(usuario);
    		return (persona != null);
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error verificando si existe persona en PAD",ex);
    	}
    	
    }
    
    /**
     * 
     * Da de alta una persona en la PAD generando el codigo de usuario forma automatica.
     * Usado por gestores y delegacion.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.delegacion}"
     * @ejb.permission role-name="${role.auto}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public PersonaPAD altaPersonaCodigoUsuarioAuto( PersonaPAD personaPAD) throws ExcepcionPAD {
    	// Establecemos codigo usuario de forma automatica
    	personaPAD.setUsuarioSeycon(prefijoAuto + personaPAD.getNif());
    	// Realizamos el alta
    	return altaPersonaPADImpl(personaPAD);    	
    }
    
    
    /**
     * 
     * Da de alta una persona en la PAD.
     * Debe ser el mismo usuario quien se de de alta.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public PersonaPAD altaPersona( PersonaPAD personaPAD ) throws ExcepcionPAD
    {
    	// Verificamos acceso
    	try
    	{
	    	// Comprobamos que sea el mismo usuario
	    	Principal sp = this.ctx.getCallerPrincipal();
			PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
			// Comprobamos que el usuario seycon a especificar sea el mismo
			if (!personaPAD.getUsuarioSeycon().equals(sp.getName())){
	    		throw new Exception("Solo puede darse de alta el propio usuario");
	    	}
			// Comprobamos que concuerde el nif de seycon y personaPAD
	    	if (!plgLogin.getNif(sp).equalsIgnoreCase(personaPAD.getNif())){
	    		throw new Exception("No concuerda el nif especificado con el de seycon");    		
	    	}
    	} catch(Exception ex)
    	{
    		throw new ExcepcionPAD("Error actualizando datos de la PAD",ex);
    	}
    	
    	// Realizamos el alta
    	return altaPersonaPADImpl(personaPAD);
    }

	private PersonaPAD altaPersonaPADImpl(PersonaPAD personaPAD)
			throws ExcepcionPAD {
		try
    	{
    		// Comprobamos que no exista persona con mismo nif o mismo seycon
    		if (obtenerDatosPersonaPADporNif(personaPAD.getNif()) != null){
    			throw new Exception("Ya existe una persona con nif " + personaPAD.getNif());
    		}
    		if (obtenerDatosPersonaPADporUsuario(personaPAD.getUsuarioSeycon()) != null){
    			throw new Exception("Ya existe una persona con usuario " + personaPAD.getUsuarioSeycon());
    		}
    		
    		// Convertimos objeto interfaz a objeto modelo
    		Persona persona = personaPADToPersona( personaPAD );
    		
    		// Damos de alta la persona
    		Date fechaModificacion = new Date();
    		persona.setFechaAlta( fechaModificacion );
    		persona.setFechaUltimaMod( fechaModificacion );    		
    		
    		grabarPersona( persona );
    		
    		personaPAD.setUsuarioSeyconGeneradoAuto(personaPAD.getUsuarioSeycon().startsWith(prefijoAuto));
    		return personaPAD;
    	}
    	catch(Exception ex)
    	{
    		throw new ExcepcionPAD("Error actualizando datos de la PAD",ex);
    	}
	}
    
    /**
     * 
     * Modifica datos persona en la PAD
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public void modificarPersona( PersonaPAD personaPAD ) throws ExcepcionPAD
    {
    	try
    	{
    		// Solo podra modificarse uno mismo o si es el representante o si tiene rol STR_DELEGA
   			if (!this.ctx.isCallerInRole(roleDelegacion) && !personaPAD.getUsuarioSeycon().equals(this.ctx.getCallerPrincipal().getName())){
    			// Comprobamos si es un delegado de la entidad
    			String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(personaPAD.getNif());
    			if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD) == -1){
    				throw new Exception("Solo puede modificar los datos el propio usuario o el representante entidad");
    			}    		
    		}
    		// Validamos cambios
    		// TODO DE MOMENTO LA VALIDACION SOLO PERMITE Q SEA EL PROPIO USUARIO
    		int[] errores = validarModificacionDatosPersonaPAD(personaPAD);
    		if (errores.length > 0){
    			throw new Exception("Cambios no permitidos: error validacion de datos");
    		}
    		
    		
    		// Comprobamos que exista persona con mismo nif o mismo seycon
    		Persona p = obtenerDatosPersonaPorUsuarioSeycon(personaPAD.getUsuarioSeycon());
    		
    		// Validar cambios permitidos
    			// No dejamos cambiar el nif
    		if (!p.getDocumentoIdLegal().equals(personaPAD.getNif())){
    			throw new Exception("No se puede modificar el nif");
    		}
    		
    		// Convertimos objeto interfaz a objeto modelo
    		Persona persona = personaPADToPersona( personaPAD );
    		
    		// Modificamos la persona manteniendo los campos no modificables
    		Date fechaModificacion = new Date();
    		persona.setCodigo(p.getCodigo());
    		persona.setFechaAlta(p.getFechaAlta());
    		persona.setFechaUltimaMod( fechaModificacion );
    		persona.setHabilitarDelegacion(p.getHabilitarDelegacion());
    		persona.setModificacionesUsuarioSeycon(p.getModificacionesUsuarioSeycon());
    		
    		grabarPersona( persona );
    		
    	}
    	catch(Exception ex)
    	{
    		throw new ExcepcionPAD("Error actualizando datos de la PAD",ex);
    	}
    }
    
    
	/**
	 * Valida cambio de datos de la persona PAD y devuelve codigos de error
	 * @throws ExcepcionPAD 
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
	 */
	public int[] validarModificacionDatosPersonaPAD(PersonaPAD persona) throws ExcepcionPAD{
		
		// Solo podra modificarse uno mismo o si es representante de la entidad
		// TODO DE MOMENTO SOLO UNO MISMO, POR TEMAS DE VALIDACION DATOS
		try {
			if (!this.ctx.isCallerInRole(roleDelegacion) && !persona.getUsuarioSeycon().equals(this.ctx.getCallerPrincipal().getName())){
				/* 
				String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(persona.getNif());
				if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD) == -1){
					throw new Exception("Solo puede modificar los datos el propio usuario o el representante entidad");
				}
				*/
				throw new Exception("Solo puede modificar los datos el propio usuario");
			}
		} catch (Exception e) {
			throw new ExcepcionPAD("Error verificando permisos",e);
		}
		
		String errores="";
		
		// Usuario seycon y nif no pueden variarse
		
		Principal user = this.ctx.getCallerPrincipal();
		PluginLoginIntf plgLogin=null;
		if(!this.ctx.isCallerInRole(roleDelegacion)){
		try {
			plgLogin = PluginFactory.getInstance().getPluginLogin();
		} catch (Exception e) {
			throw new ExcepcionPAD("Error al crear plugin login",e);
		}		
		if (!user.getName().equals(persona.getUsuarioSeycon())){
			errores +=PersonaPAD.ERROR_USUARIO_SEYCON_INCORRECTO;
		}
		if (!plgLogin.getNif(user).equalsIgnoreCase(persona.getNif())){
			errores +=PersonaPAD.ERROR_NIF_INCORRECTO;
		}		
		
		// Validacion nombre: no se permite variar el nombre establecido en seycon, solo variar orden,etc.
		int errorNombre=0;
		try {
			errorNombre = validar(user, persona.getNombre(),persona.getApellido1(),persona.getApellido2());
		} catch (Exception e) {
			throw new ExcepcionPAD("Error al validar nombre",e);
		}
		if (errorNombre > 0) errores += errorNombre;
		}
		// Validacion email		
		if (StringUtils.isNotEmpty(persona.getEmail())){
			if (!ValidacionesUtil.validarEmail(persona.getEmail())) errores += PersonaPAD.ERROR_EMAIL;
		}
		
		// Validacion movil
		if (StringUtils.isNotEmpty(persona.getTelefonoMovil())){
			if (!ValidacionesUtil.validarMovil(persona.getTelefonoMovil())) errores += PersonaPAD.ERROR_TELEFONO_MOVIL;
		}
		
		// Devolvemos codigos de errores
		int codErrores[] = new int[errores.length()];
		for (int i=0;i<errores.length();i++){
			codErrores[i] =  Integer.parseInt(""+errores.charAt(i));
		}
		return codErrores;		
	}
	
   
	/**
     * 
     * Obtiene datos de codigo usuario, nif, nombre y apellidos para helpdesk
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public PersonaPAD obtenerHelpdeskDatosPersonaPorUsuario( String codUsu) throws ExcepcionPAD
    {
    	try
    	{
    		Persona persona = this.obtenerDatosPersonaPorUsuarioSeycon(codUsu);
    		if (persona == null){
    			return null;
    		}

    		// Solo devolvemos datos del codigo y nombre
    		PersonaPAD result = new PersonaPAD();    		
    		result.setUsuarioSeycon(persona.getUsuarioSeycon());
    		result.setNif(persona.getDocumentoIdLegal());
    		result.setNombre(persona.getNombre());
    		result.setApellido1(persona.getApellido1());
    		result.setApellido2(persona.getApellido2());
    		result.setUsuarioSeyconGeneradoAuto(persona.getUsuarioSeycon().startsWith(prefijoAuto));
    		
    		return result;
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error obteniendo datos persona en PAD",ex);
    	}    	
    }
    
    
    /**
     * 
     * Obtiene datos de codigo usuario, nif, nombre y apellidos para helpdesk
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public PersonaPAD obtenerHelpdeskDatosPersonaPorNif( String nif) throws ExcepcionPAD
    {
    	try
    	{
    		Persona persona = this.obtenerDatosPersonaPorDocumentoIdentificacionLegal(nif);
    		if (persona == null){
    			return null;
    		}

    		// Solo devolvemos datos del codigo y nombre
    		PersonaPAD result = new PersonaPAD();    		
    		result.setUsuarioSeycon(persona.getUsuarioSeycon());
    		result.setNif(persona.getDocumentoIdLegal());
    		result.setNombre(persona.getNombre());
    		result.setApellido1(persona.getApellido1());
    		result.setApellido2(persona.getApellido2());
    		result.setUsuarioSeyconGeneradoAuto(persona.getUsuarioSeycon().startsWith(prefijoAuto));
    		
    		return result;
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error obteniendo datos persona en PAD",ex);
    	}    	
    }
	
	/**
     * 
     * Cambia de usuario seycon.
     * 
     *  Permite realizar el cambio al role helpdesk o bien al propio usuario (verifica que el nif es el mismo)
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.helpdesk}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public void actualizarCodigoUsuario( String usuOld, String usuNew) throws ExcepcionPAD
    {
    	log.debug("Modificando usuario " + usuOld + " a " + usuNew);
    	
    	// Obtenemos datos usuario a partir codigo antiguo
    	Persona p = this.obtenerDatosPersonaPorUsuarioSeycon(usuOld);
    	if (p == null) {
    		throw new ExcepcionPAD("No existe usuario con codigo " + usuOld);
    	}
    	
    	// Permite realizar el cambio al role helpdesk o bien al propio usuario (verifica que el nif es el mismo)
    	if (!this.ctx.isCallerInRole(roleHelpdesk)){
    		Principal sp = this.ctx.getCallerPrincipal();
    		PluginLoginIntf plgLogin;
			try {
				plgLogin = PluginFactory.getInstance().getPluginLogin();
			} catch (Exception e) {
				throw new ExcepcionPAD("Nio se ha podido verificar el nif del usuario");
			}
    		if (!plgLogin.getNif(sp).equalsIgnoreCase(p.getDocumentoIdLegal())){
    			throw new ExcepcionPAD("El usuario no tiene el mismo nif");
    		}
    	}
    	
    	// Actualizamos codigo usuario y apuntamos usuario anterior
    	p.setUsuarioSeycon(usuNew);
    	if (StringUtils.isEmpty(p.getModificacionesUsuarioSeycon()) ){
    		p.setModificacionesUsuarioSeycon(usuOld);
    	}else{
    		p.setModificacionesUsuarioSeycon(p.getModificacionesUsuarioSeycon() + ";" + usuOld);
    	}    	
    	this.grabarPersona(p);    	
    }
    
    /**
     * 
     * Realiza la busqueda de entidades segun el nif
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.delegacion}"
     * 
     * @param nifEntidad
     * @return Lsta con las entidades
     * @throws ExcepcionPAD
     */
    public List buscarEntidades( String nifEntidad) throws ExcepcionPAD
    {
    	Session session = getSession();
       	try
    	{
       		String select = "FROM Persona AS p WHERE p.documentoIdLegal = :idDocumento";
       		
       		Query query = session.createQuery(select);
            query.setParameter("idDocumento",nifEntidad);
           
       		List entidades = query.list();
       		List entidadesPAD = new ArrayList();	
            if (!entidades.isEmpty()){
            	for(int i=0;i<entidades.size();i++){
            		entidadesPAD.add(personaToPersonaPAD((Persona)entidades.get(i)));
            	}
            }
            
            return  entidadesPAD;
            	
                  			
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error obteniendo las entidades",ex);
    	}
    	finally 
        {
            close(session);
        } 
    }
    
    /**
     * 
     * Realiza la busqueda de entidades por nombre
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.delegacion}"
     * 
     * @param nifEntidad
     * @return Lsta con las entidades
     * @throws ExcepcionPAD
     */
    public List buscarEntidadesPorNombre( String nombreEntidad) throws ExcepcionPAD
    {
    	List entidadesPAD = new ArrayList();
    	if (StringUtils.isNotBlank(nombreEntidad)) {
	    	Session session = getSession();
	       	try
	    	{
	       		String select = "FROM Persona AS p WHERE upper(p.nombre) like :clave or upper(p.apellido1) like :clave or upper(p.apellido2) like :clave ";
	       		String clave = "%" +  nombreEntidad.toUpperCase() + "%";
	       		Query query = session.createQuery(select);
	       		query.setParameter("clave",clave);
	           
	       		List entidades = query.list();
	       		if (!entidades.isEmpty()){
	            	for(int i=0;i<entidades.size();i++){
	            		entidadesPAD.add(personaToPersonaPAD((Persona)entidades.get(i)));
	            	}
	            }	                  		
	    	}
	    	catch( Exception ex )
	    	{
	    		throw new ExcepcionPAD("Error obteniendo las entidades",ex);
	    	}
	    	finally 
	        {
	            close(session);
	        } 
    	}
    	return  entidadesPAD;
    }
	
	// ----------------------------------------------
	//	FUNCIONES AUXILIARES
	// ----------------------------------------------
	 
    private Persona obtenerDatosPersonaPorUsuarioSeycon( String usuarioSeycon )
    {
		log.info("obtenerDatosPersonaPorUsuarioSeycon(" + usuarioSeycon + ");");
        if (usuarioSeycon == null) {
          return null;
        }
    	Session session = getSession();
        try 
        {
        	Query query = session
            .createQuery("FROM Persona AS p WHERE p.usuarioSeycon = :usuario")
            .setParameter("usuario",usuarioSeycon);
        	
        	if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
        	return ( Persona ) query.uniqueResult();
        	
        }
        catch (HibernateException he) 
        {
        	throw new EJBException(he);
        } 
        finally 
        {
            close(session);
        }
    }
    
    private Persona obtenerDatosPersonaPorDocumentoIdentificacionLegal( String docIdLegal )
    {
    	Session session = getSession();
        try 
        {
        	Query query = session
            .createQuery("FROM Persona AS p WHERE p.documentoIdLegal = :idDocumento")
            .setParameter("idDocumento",docIdLegal);
        	
        	if (query.list().isEmpty()){
            	return null;
            }
        	return ( Persona ) query.uniqueResult();
        	
        }
        catch (HibernateException he) 
        {
        	throw new EJBException(he);
        } 
        finally 
        {
            close(session);
        }
    }
    
    private Long grabarPersona( Persona obj )
    {        
    	Session session = getSession();
        try 
        {        	
        	if (obj.getCodigo() == null)
        	{
        		session.save(obj);
        	}
        	else
        	{
        		session.update(obj);
        	}
        	                    	
            return obj.getCodigo();
        } 
        catch (HibernateException he) 
        {
            throw new EJBException(he);
        } 
        finally 
        {
            close(session);
        }
    }
    
    private PersonaPAD personaToPersonaPAD( Persona persona )
    {
    	if ( persona == null)
    		return null;
    	
    	PersonaPAD personaPAD = new PersonaPAD();
    	personaPAD.setUsuarioSeycon( persona.getUsuarioSeycon() );
    	personaPAD.setNif( persona.getDocumentoIdLegal().toUpperCase() );
		personaPAD.setNombre( persona.getNombre() );
		personaPAD.setApellido1( persona.getApellido1() );
		personaPAD.setApellido2( persona.getApellido2() );
		personaPAD.setPersonaJuridica( persona.isPersonaJuridica() );
		personaPAD.setDireccion(persona.getDireccion());
		personaPAD.setProvincia(persona.getProvincia());
		personaPAD.setMunicipio(persona.getMunicipio());
		personaPAD.setCodigoPostal(persona.getCodigoPostal());
		personaPAD.setTelefonoFijo(persona.getTelefonoFijo());
		personaPAD.setHabilitarAvisosExpediente(persona.isHabilitarAvisosExpediente());
		personaPAD.setTelefonoMovil(persona.getTelefonoMovil());
		personaPAD.setEmail(persona.getEmail());
		personaPAD.setHabilitarDelegacion("S".equals(persona.getHabilitarDelegacion()));
		personaPAD.setUsuarioSeyconGeneradoAuto(personaPAD.getUsuarioSeycon().startsWith(prefijoAuto));
		
    	return personaPAD;
    }
    
    private Persona personaPADToPersona( PersonaPAD personaPAD )
    {
    	if ( personaPAD == null )
    		return null;
    	
    	Persona persona = new Persona();
    	persona.setDocumentoIdLegal( personaPAD.getNif().toUpperCase() );
    	persona.setUsuarioSeycon( personaPAD.getUsuarioSeycon() );
    	persona.setNombre( personaPAD.getNombre() );
    	persona.setApellido1( personaPAD.getApellido1() );
    	persona.setApellido2( personaPAD.getApellido2() );
    	persona.setPersonaJuridica( personaPAD.isPersonaJuridica() );
    	persona.setDireccion(personaPAD.getDireccion());
		persona.setProvincia(personaPAD.getProvincia());
		persona.setMunicipio(personaPAD.getMunicipio());
		persona.setCodigoPostal(personaPAD.getCodigoPostal());
		persona.setTelefonoFijo(personaPAD.getTelefonoFijo());
    	persona.setHabilitarAvisosExpediente(personaPAD.isHabilitarAvisosExpediente());
    	persona.setTelefonoMovil(personaPAD.getTelefonoMovil());
    	persona.setEmail(personaPAD.getEmail());
    	
    	// HABILITAR DELEGACION NO SE ESTABLECE YA QUE ES SOLO LECTURA
    	
    	return persona;
    }
	
	
	private int validar( Principal seycon, String nombre, String apellido1, String apellido2 ) throws Exception
	{
		if ( StringUtils.isEmpty( nombre ) )
			return PersonaPAD.ERROR_ORDEN_PARTICULAS_FALTA_NOMBRE;
		
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		
		// Tan sólo validamos nombre y apellido si no se trata de una persona jurídica
		Principal sp = this.ctx.getCallerPrincipal();		
		String nif = plgLogin.getNif(sp);
		
		if ( NifCif.esCIF(nif) )
			return 0;
		
		// Validamos que no se introduce Nunca apellido2 sin apellido1
		if ( !StringUtils.isEmpty( apellido2 ) && StringUtils.isEmpty( apellido1 ) )
		{
			return PersonaPAD.ERROR_ORDEN_PARTICULAS_FALTA_APELLIDO1;
		}
		
		StringBuffer sbIntroducedFullName = new StringBuffer( nombre );
		if ( !StringUtils.isEmpty( apellido1 ) ) sbIntroducedFullName.append( " " ).append( apellido1 );
		if ( !StringUtils.isEmpty( apellido2 ) ) sbIntroducedFullName.append( " " ).append( apellido2 );
		
		String introducedFullName = sbIntroducedFullName.toString();
		
		int nParticulas = StringUtils.countMatches( introducedFullName, " ") + 1;
		
		// 1º Validamos que el nº de particulas introducidas es igual al nº de particulas del nombre seycon.
		String [] particulasSeycon = plgLogin.getNombreCompleto(seycon).replaceAll( "\\s+", " " ).split( "\\s" );
		int nParticulasSeycon = particulasSeycon.length;
		if ( nParticulas > nParticulasSeycon )
		{
			return PersonaPAD.ERROR_NUMERO_PARTICULAS_EXCESO;
		}
		if ( nParticulas < nParticulasSeycon )
		{
			return PersonaPAD.ERROR_NUMERO_PARTICULAS_DEFECTO;
		}
		// 3º Validamos que cada una de las particulas del nombre seycon están presentes en las particulas introducidas
		for ( int i = 0; i < nParticulasSeycon; i++ )
		{
			if ( !NormalizacionNombresUtil.existeParticulaEnElNombre( particulasSeycon[ i ], introducedFullName ) )
				// En este caso lo que sucede es que se ha modificado alguna particula
				return PersonaPAD.ERROR_PARTICULAS_NOMBRE_ERRONEAS;
		}
		// exito de la validación
		return 0;						
	}
		
}
