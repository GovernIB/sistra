package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.Date;

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
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;


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
 */
public abstract class PadAplicacionFacadeEJB extends HibernateEJB {

	private static Log log = LogFactory.getLog(PadAplicacionFacadeEJB.class);
	private String roleAuto;
			
    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }

    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void ejbCreate() throws CreateException 
    {
        super.ejbCreate();
        try{
			InitialContext initialContext = new InitialContext();			 
			roleAuto = (( String ) initialContext.lookup( "java:comp/env/roleAuto" ));			
		}catch(Exception ex){
			log.error(ex);
		}
    }
	  
    /**
     * Obtiene datos persona almacenados en PAD buscando por usuario.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporUsuario( String usuario ) throws ExcepcionPAD
    {
    	try
    	{
    		// Solo puede acceder el propio usuario o el usuario auto
    		if (!this.ctx.isCallerInRole(roleAuto) && !usuario.equals(this.ctx.getCallerPrincipal().getName())){
    			throw new Exception("Solo puede acceder el propio usuario o el usuario auto");
    		}
    		
    		Persona persona = obtenerDatosPersonaPorUsuarioSeycon( usuario );
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
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporNif( String nif ) throws ExcepcionPAD
    {
    	try
    	{
    		// Solo puede acceder el propio usuario o el usuario auto
    		Principal sp = this.ctx.getCallerPrincipal();
    		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    		if (!this.ctx.isCallerInRole(roleAuto) && ! plgLogin.getNif(sp).equalsIgnoreCase(nif)){
    			throw new Exception("Solo puede acceder el propio usuario o el usuario auto");
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
     * @ejb.permission role-name="${role.user}"
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
     * Da de alta una persona en la PAD
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public PersonaPAD altaPersona( PersonaPAD personaPAD ) throws ExcepcionPAD
    {
    	try
    	{
    		Principal sp = this.ctx.getCallerPrincipal();
    		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    		
    		// Solo podra darse de alta uno mismo
    		if (!personaPAD.getUsuarioSeycon().equals(sp.getName())){
    			throw new Exception("Solo puede darse de alta el propio usuario");
    		}
    		
    		// Comprobamos que concuerde el nif de seycon y personaPAD
    		if (!plgLogin.getNif(sp).equalsIgnoreCase(personaPAD.getNif())){
    			throw new Exception("No concuerda el nif especificado con el de seycon");
    		}
    		
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
     * @ejb.permission role-name="${role.user}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public void modificarPersona( PersonaPAD personaPAD ) throws ExcepcionPAD
    {
    	try
    	{
    		// Solo podra modificarse uno mismo
    		if (!personaPAD.getUsuarioSeycon().equals(this.ctx.getCallerPrincipal().getName())){
    			throw new Exception("Solo puede modificar los datos el propio usuario");
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
    		persona.setCodigo(p.getCodigo());
    		
    		// Damos de alta la persona
    		Date fechaModificacion = new Date();
    		persona.setFechaAlta( fechaModificacion );
    		persona.setFechaUltimaMod( fechaModificacion );
    		
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
     * @ejb.permission role-name="${role.user}"
	 */
	public int[] validarModificacionDatosPersonaPAD(PersonaPAD persona) throws ExcepcionPAD{
		
		// Solo podra modificarse uno mismo
		if (!persona.getUsuarioSeycon().equals(this.ctx.getCallerPrincipal().getName())){
			throw new ExcepcionPAD("Solo puede modificar los datos el propio usuario");
		}
		
		String errores="";
		
		// Usuario seycon y nif no pueden variarse
		Principal user = this.ctx.getCallerPrincipal();
		PluginLoginIntf plgLogin=null;
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
    		
    		return result;
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error obteniendo datos persona en PAD",ex);
    	}    	
    }
	
   
	/**
     * 
     * Cambia de usuario seycon (solucion temporal hasta cambio de operativa)
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public void modificarHelpdeskCodigoUsuario( String usuOld, String usuNew) throws ExcepcionPAD
    {
    	log.debug("Modificando usuario " + usuOld + " a " + usuNew);
    	Persona p = this.obtenerDatosPersonaPorUsuarioSeycon(usuOld);
    	if (p == null) {
    		throw new ExcepcionPAD("No existe usuario con codigo " + usuOld);
    	}
    	p.setUsuarioSeycon(usuNew);
    	this.grabarPersona(p);    	
    }
    
	
	// ----------------------------------------------
	//	FUNCIONES AUXILIARES
	// ----------------------------------------------
	 
    private Persona obtenerDatosPersonaPorUsuarioSeycon( String usuarioSeycon )
    {
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
		
    	return personaPAD;
    }
    
    private Persona personaPADToPersona( PersonaPAD personaPAD )
    {
    	if ( personaPAD == null )
    		return null;
    	
    	// TODO Validar campos en persistencia?
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
