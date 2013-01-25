package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaPreregistroBackup;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaPreregistroBackup;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * SessionBean para mantener y consultar EntradaPreregistro
 *
 * @ejb.bean
 *  name="zonaper/persistence/EntradaPreregistroFacade"
 *  jndi-name="es.caib.zonaper.persistence.EntradaPreregistroFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleHelpDesk" type="java.lang.String" value="${role.helpdesk}"
 * @ejb.env-entry name="roleRegistro" type="java.lang.String" value="${role.registro}"
 * @ejb.env-entry name="roleGestor" type="java.lang.String" value="${role.gestor}"
 * 
 */
public abstract class EntradaPreregistroFacadeEJB extends HibernateEJB {

	private static final String HQL_FROM = "FROM EntradaPreregistro AS m ";
	private static final String HQL_ORDER = "ORDER BY m.tramite ";
	private static final String HQL_FECHAS = "m.fecha >= :fechaInicial AND m.fecha <= :fechaFinal ";
	private static final String KEY_MODELO = "MODELO";
	private static final String KEY_FECHA_INICIO = "FECHA_INICIO";
	private static final String KEY_FECHA_FIN = "FECHA_FIN";
	private static final String KEY_CADUCIDAD = "CADUCIDAD";
	private static final String KEY_TIPO = "TIPO";
	private static final String KEY_NIVEL = "NIVEL";
	private static final String KEY_WHERE = "WHERE";
	
	private String roleHelpDesk;
	private String roleRegistro;
	private String roleGestor;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
		try{
			InitialContext initialContext = new InitialContext();			 
			roleHelpDesk = (( String ) initialContext.lookup( "java:comp/env/roleHelpDesk" ));	
			roleRegistro = (( String ) initialContext.lookup( "java:comp/env/roleRegistro" ));		
			roleGestor = (( String ) initialContext.lookup( "java:comp/env/roleGestor" ));
		}catch(Exception ex){
			log.error(ex);
		}
	}
	
    /**
     * Obtiene entrada preregistro de forma autenticada
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroAutenticada(Long id) {
        
    	// Cargamos entradaPreregistro        	
    	EntradaPreregistro entradaPreregistro = this.recuperarEntradaPorCodigo(id);
    	
    	// Control acceso expediente
    	controlAccesoAutenticadoExpediente(entradaPreregistro); 
        
        return entradaPreregistro;
      
    	
    }

	
    
    /**
     *  Obtiene entrada preregistro de forma anonima
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroAnonima(Long id,String claveAcceso) {
    	
    	// Cargamos entradaPreregistro  
    	EntradaPreregistro entradaPreregistro = this.recuperarEntradaPorCodigo(id);    	       
        
        // Control acceso anonimo expediente
    	controlAccesoAnonimoExpediente(claveAcceso, entradaPreregistro);
    	
    	return entradaPreregistro;
    }

	
    
    /**
     * Obtiene entrada por id (registro, gestor y auto)
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     *  @ejb.permission role-name="${role.registro}"
     */
    public EntradaPreregistro obtenerEntradaPreregistro(Long id) {
        return recuperarEntradaPorCodigo(id);
    }

	
    
    /**
     *  Obtiene entrada por id persistencia. Según tipo de acceso se comprobará acceso a la entrada.
     *  Si helpdesk no hay control acceso. Si autenticado se comprueba que el nif corresponda a la entrada.
     *  
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.helpdesk}"
     * 
     */
    public EntradaPreregistro obtenerEntradaPreregistro(String idPersistencia) {
    	try {
	    	EntradaPreregistro entradaPreregistro = recuperarEntradaPreregistroPorIdPersistencia(idPersistencia);
	    	// Control acceso  (role helpdesk salta comprobacion)    	
	    	if (entradaPreregistro != null && !this.ctx.isCallerInRole(roleHelpDesk)){
	    		// Obtenemos codigo expediente asociado
	    		Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO, entradaPreregistro.getCodigo());
	        	// Obtenemos metodo de autenticacion
	    		Principal sp = this.ctx.getCallerPrincipal();	  
	        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
	        	char metodoAutenticacion = plgLogin.getMetodoAutenticacion(sp);
	        	// Controlamos acceso anonimo
	        	if (metodoAutenticacion == 'A') {
	        		if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAnonimo(idExpediente, idPersistencia)) {
	            		throw new Exception("No tiene acceso a entrada preregistro: " + idPersistencia);
	            	}        		
	        	}else{
	        	// Controlamos acceso autenticado	        	
	        		if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAutenticado(idExpediente)) {
	            		throw new Exception("No tiene acceso a entrada preregistro: " + idPersistencia);
	            	}	        		
	        	}                		
	    	}
	    	return entradaPreregistro;
    	} catch (Exception ex){
    		throw new EJBException("Excepcion accediendo a entrada preregistro: " + ex.getMessage(), ex);
    	}
    }
    
    /**
     * Busca entrada preregistro anonima por id persistencia. El control de acceso es el id persistencia.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroAnonima(String idPersistencia) {
    	EntradaPreregistro entradaPreregistro = recuperarEntradaPreregistroPorIdPersistencia(idPersistencia);   
    	if (entradaPreregistro != null && entradaPreregistro.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO) {
        	entradaPreregistro = null;
        }
        return entradaPreregistro;           
    }

    /**
     * Busca entrada preregistro autenticada por id persistencia 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroAutenticada(String idPersistencia) {
    	// Recuperamos entrada
    	EntradaPreregistro entradaPreregistro = recuperarEntradaPreregistroPorIdPersistencia(idPersistencia);   
    	// Control acceso expediente 
    	controlAccesoAutenticadoExpediente(entradaPreregistro);
    	return entradaPreregistro;    	
    }
	
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroPorNumero(String numeroPreregistro) {
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.numeroPreregistro = :numeroPreregistro")
            .setParameter("numeroPreregistro",numeroPreregistro);
        	//query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
            EntradaPreregistro entradaPreregistro = (EntradaPreregistro)  query.uniqueResult();             
            
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
   
	/**
 	 * 
 	 * Confirma entrada preregistro. 
 	 * Si es confirmacion automatica debe poder presentar dicho tramite.
 	 * Si no es automatica y es confirmado incorrectamente, debera tener role gestor.
 	 * Si no es automatica y no es confirmado incorrectamente, debera tener role registro.
 	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public void confirmarEntradaPreregistro(Long codigo, String numeroRegistro,
			Date fechaConfirmacion, boolean confirmadoAutomaticamente, boolean confirmadoIncorrecto) {   
    	// Recupera entrada
    	EntradaPreregistro entrada = this.recuperarEntradaPorCodigo(codigo);
    	
    	Session session = getSession();
        try {        	
        	// Confirmacion automatica: control acceso para presentar preregistro
        	if (confirmadoAutomaticamente) {    	    	
    			verificarPermisosPresentacionPreregistro(entrada);
        	} else {
        		// Confirmacion manual: debe tener role registro
        		if (confirmadoIncorrecto) {
        			if (!this.ctx.isCallerInRole(this.roleGestor)) {
            			throw new Exception("No tiene el role de gestor");
            		}
        		} else {
	        		if (!this.ctx.isCallerInRole(this.roleRegistro)) {
	        			throw new Exception("No tiene el role de registro");
	        		}
        		}
        	}
        	
        	// Updateamos
        	entrada.setNumeroRegistro(numeroRegistro);
        	entrada.setFechaConfirmacion(fechaConfirmacion);
        	entrada.setConfirmadoAutomaticamente(confirmadoAutomaticamente?'S':'N');        	
        	session.update(entrada);
        	                    	            
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }    	
    }
    	
    	
 	/**
 	 * 
 	 * Graba nueva entrada preregistro.
 	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public Long grabarNuevaEntradaPreregistro(EntradaPreregistro entrada) {        
    	Session session = getSession();
        try {        	
        	
        	// Control acceso para presentar preregistro
    		verificarPermisosPresentacionPreregistro(entrada);
        	
        	// updateamos
        	if (entrada.getCodigo() == null){
        		session.save(entrada);
        	}else{
        		throw new Exception("No se puede modificar entrada");
        	}
        	                    	
            return entrada.getCodigo();
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    
    /** Obtiene estado entrada preregistro
     * @return N: No existe / T: Terminada / C: Pendiente confirmacion
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerEstadoEntradaPreregistro(String idPersistencia) {
    	EntradaPreregistro entrada = this.recuperarEntradaPreregistroPorIdPersistencia(idPersistencia);
    	String estado = "N";
    	if (entrada != null) {
    		if (entrada.getFechaConfirmacion() != null) {
    			estado = "T";
    		} else {
    			estado = "P";
    		}
    	}
    	return estado;
    }

	
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarEntradaPreregistrosCaducados( Date fecha ) 
    {
        Session session = getSession();
        try {     
        	
        	
            Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.fechaCaducidad < :fecha and m.fechaConfirmacion is null ORDER BY m.fecha ASC")
            .setParameter("fecha", fecha );
            //query.setCacheable(true);
            List tramites = query.list();
            
            // Cargamos documentos
            for (Iterator it=tramites.iterator();it.hasNext();){
            	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) it.next();
            	Hibernate.initialize(entradaPreregistro.getDocumentos());
            }
            
            return tramites;
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarEntradaPreregistrosNifModelo(String nif, String modelo, Date fechaInicial, Date fechaFinal, String nivelAutenticacion) {
        Session session = getSession();
        try {     
        	
        	
            Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE " +
            			((nivelAutenticacion != null && "A".equals(nivelAutenticacion)) ? " m.nifRepresentante like :nif " : " m.nifRepresentante = :nif ")+
            		     ((modelo != null) ? " and m.tramite = :modelo " : "")+
            		     " and  m.fecha >= :fechaInicial and m.fecha <= :fechaFinal "+
            		     ((nivelAutenticacion != null) ? " and m.nivelAutenticacion = :nivel" : "" ) +
            		     " ORDER BY m.fecha DESC");
            if(modelo != null) query.setParameter("modelo",modelo);
            if(nivelAutenticacion != null) query.setParameter("nivel",nivelAutenticacion);
            if(nivelAutenticacion != null && "A".equals(nivelAutenticacion)){
            	query.setParameter("nif","%"+nif+"%");
            }else{
            query.setParameter("nif",nif);
            }
            query.setParameter("fechaInicial",fechaInicial);
            query.setParameter("fechaFinal",fechaFinal);

            
            return query.list();
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void borrarEntradaPreregistro(Long id) {
        Session session = getSession();
        try {
            EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
            session.delete(entradaPreregistro);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}" 
     * @param tramitePersistenteBackup
     * @return
     */
    public void borrarEntradaPreregistroBackup( EntradaPreregistroBackup entradaPreregistroBackup )
    {
    	Session session = getSession();
    	try
    	{
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		if(entradaPreregistroBackup != null && entradaPreregistroBackup.getDocumentosBackup() != null){
    			for(Iterator it = entradaPreregistroBackup.getDocumentosBackup().iterator(); it.hasNext();){
    				DocumentoEntradaPreregistroBackup backup = ( DocumentoEntradaPreregistroBackup ) it.next();
    				ReferenciaRDS ref = new ReferenciaRDS();
    				ref.setCodigo(backup.getCodigoRDS());
    				ref.setClave(backup.getClaveRDS());
    				List usos = rds.listarUsos(ref);
    				for(int i=0;i<usos.size();i++){
    					rds.eliminarUso((UsoRDS)usos.get(i));
    				}
    			}
    			entradaPreregistroBackup.getDocumentosBackup().removeAll(entradaPreregistroBackup.getDocumentosBackup());
    		}
    		session.delete(entradaPreregistroBackup);
    	}
    	catch( Exception exc )
    	{
    		throw new EJBException( exc );
        } finally {
            close(session);
        }
    	
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarEntradaPreregistroBackup( Date fecha ) {
    	Session session = getSession();
        try {      
            Query query = session
            .createQuery("FROM EntradaPreregistroBackup AS m WHERE m.fechaCaducidad < :fecha")
            .setParameter("fecha", fecha );
            //query.setCacheable(true);
            List entradas = query.list();
            
            // Cargamos documentos
            for (Iterator it=entradas.iterator();it.hasNext();){
            	EntradaPreregistroBackup entradaPreregistroBackup = (EntradaPreregistroBackup) it.next();
            	Hibernate.initialize(entradaPreregistroBackup.getDocumentosBackup());
            }
            
            return entradas;
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @param entradaPreregistro
     */
    public void backupEntradaPreregistro( EntradaPreregistro entradaPreregistro )
    {
    	Session session = getSession();
    	try
    	{
	    	EntradaPreregistroBackup entradaBackup = new EntradaPreregistroBackup();
	    	BeanUtils.copyProperties( entradaBackup, entradaPreregistro );
			Set setDocumentos = entradaPreregistro.getDocumentos(); 
			for ( Iterator it = setDocumentos.iterator(); it.hasNext(); )
			{
				DocumentoEntradaPreregistro documento = ( DocumentoEntradaPreregistro ) it.next();
				DocumentoEntradaPreregistroBackup docBackup = new DocumentoEntradaPreregistroBackup();
				BeanUtils.copyProperties( docBackup, documento );
				entradaBackup.addDocumentoBackup( docBackup );
			}
			session.save( entradaBackup );
    	}
    	catch( Exception exc )
    	{
    		throw new EJBException( exc );
    	}
    	finally
    	{
    		close( session );
    	}
    }
    	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarEntradaPreregistrosNoConfirmados( Date fechaInicial, Date fechaFinal, String modelo, String caducidad, String tipo, String nivel ) {

    	Hashtable filtro = getFiltro(fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel);
    	Hashtable condiciones = getCondiciones(filtro);
    	condiciones.put(KEY_WHERE, "WHERE m.fechaConfirmacion IS NULL AND ");
    	return getListaEntradaPreregistro(condiciones, filtro);
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarEntradaPreregistrosConfirmados( Date fechaInicial, Date fechaFinal, String modelo, String caducidad, String tipo, String nivel ) {
    	
    	Hashtable filtro = getFiltro(fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel);
    	Hashtable condiciones = getCondiciones(filtro);
    	condiciones.put(KEY_WHERE, "WHERE m.fechaConfirmacion IS NOT NULL AND ");
    	return getListaEntradaPreregistro(condiciones, filtro);
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarEntradaPreregistros( Date fechaInicial, Date fechaFinal, String modelo, String caducidad, String tipo, String nivel ) {
    	
    	Hashtable filtro = getFiltro(fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel);
    	Hashtable condiciones = getCondiciones(filtro);
    	condiciones.put(KEY_WHERE, "WHERE ");
    	return getListaEntradaPreregistro(condiciones, filtro);
    }

    /**
     * Genera un Hashtable a partir de los filtros 
     * @param fechaInicial
     * @param fechaFinal
     * @param modelo
     * @param caducidad
     * @param tipo
     * @param nivel
     * @return
     */
    private Hashtable getFiltro(Date fechaInicial, Date fechaFinal, String modelo, String caducidad, String tipo, String nivel) {
    	Hashtable filtro = new Hashtable();
		
		filtro.put(KEY_MODELO, modelo);
		filtro.put(KEY_FECHA_INICIO, fechaInicial);
		filtro.put(KEY_FECHA_FIN, fechaFinal);
		filtro.put(KEY_CADUCIDAD, caducidad);
		filtro.put(KEY_TIPO, tipo);
		filtro.put(KEY_NIVEL, nivel);
		return filtro;
    }
    
    /**
     * Genera las condiciones a partir de los valores de filtro
     * @param filtro
     * @return
     */
    private Hashtable getCondiciones(Hashtable filtro){
    	
    	Hashtable condiciones = new Hashtable();
    	String modelo = (String)filtro.get(KEY_MODELO);
    	String caducidad = (String)filtro.get(KEY_CADUCIDAD);
    	String tipo = (String)filtro.get(KEY_TIPO);
    	String nivel = (String)filtro.get(KEY_NIVEL);
    	
    	String hql_modelo;

    	if ("0".equals(modelo)) {
    		hql_modelo = " AND :modelo = :modelo ";
    	} else {
    		hql_modelo = " AND m.tramite = :modelo ";
    	}
    	
    	condiciones.put(KEY_MODELO, hql_modelo);
    	
    	String hql_caducidad;
    	
    	if ("S".equals(caducidad)) {
    		hql_caducidad = " AND m.fechaCaducidad <= :fechaActual ";
    	} else if ("N".equals(caducidad)) {
    		hql_caducidad = " AND m.fechaCaducidad > :fechaActual ";
    	} else {
    		hql_caducidad = " AND :fechaActual = :fechaActual ";
    	}
    	
    	condiciones.put(KEY_CADUCIDAD, hql_caducidad);
    	
    	String hql_tipo;
    	
    	if ("T".equals(tipo)) {
    		hql_tipo = " AND :tipo = :tipo ";
    	} else {
    		hql_tipo = " AND m.tipo = :tipo ";
    	}
    	
    	condiciones.put(KEY_TIPO, hql_tipo);
    	
    	String hql_nivel;
    	
    	if ("T".equals(nivel)) {
    		hql_nivel = " AND :nivel = :nivel ";
    	} else {
    		hql_nivel = " AND m.nivelAutenticacion = :nivel ";
    	}
    	
    	condiciones.put(KEY_NIVEL, hql_nivel);
    	
    	return condiciones;
    }
    
    /**
     * Ejecuta la consulta a partir de las condiciones y el valor de los filtros
     * @param condiciones
     * @param filtro
     * @return
     */
    private List getListaEntradaPreregistro(Hashtable condiciones, Hashtable filtro) {
    	
    	Session session = getSession();

		String hql_where = (String)condiciones.get(KEY_WHERE);
		String hql_caducidad = (String)condiciones.get(KEY_CADUCIDAD);
		String hql_modelo = (String)condiciones.get(KEY_MODELO);
		String hql_tipo = (String)condiciones.get(KEY_TIPO);
		String hql_nivel = (String)condiciones.get(KEY_NIVEL);

		int size = HQL_FROM.length() + hql_where.length() + hql_caducidad.length() + hql_modelo.length() + HQL_ORDER.length();
    	StringBuilder hql = new StringBuilder(size);
    	hql.append(HQL_FROM);
    	hql.append(hql_where);
    	hql.append(HQL_FECHAS);
    	hql.append(hql_modelo);
    	hql.append(hql_tipo);
    	hql.append(hql_nivel);
    	hql.append(hql_caducidad);
    	hql.append(HQL_ORDER);
    	try {
    		Query query = session.createQuery(hql.toString());
    		query.setParameter("fechaInicial", filtro.get(KEY_FECHA_INICIO));
    		query.setParameter("fechaFinal", filtro.get(KEY_FECHA_FIN));
    		query.setParameter("modelo", filtro.get(KEY_MODELO));
    		query.setParameter("tipo", filtro.get(KEY_TIPO));
    		query.setParameter("nivel", filtro.get(KEY_NIVEL));
    		query.setParameter("fechaActual", new Date());
    		return query.list();
    	} catch(HibernateException he) {
    		throw new EJBException(he);
    	} finally {
    		close(session);
    	}

    }
  	
    private EntradaPreregistro recuperarEntradaPreregistroPorIdPersistencia(
			String idPersistencia) {
		EntradaPreregistro entradaPreregistro = null;
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.idPersistencia = :id")
            .setParameter("id",idPersistencia);
            //query.setCacheable(true);
            if (!query.list().isEmpty()){
            	entradaPreregistro = (EntradaPreregistro)  query.uniqueResult();  
            	// Cargamos documentos
            	Hibernate.initialize(entradaPreregistro.getDocumentos());
            }
        } catch (Exception he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
		return entradaPreregistro;
	}
    
    private EntradaPreregistro recuperarEntradaPorCodigo(Long id) {
		Session session = getSession();
        try {
        	// 	Cargamos entradaPreregistro        	
        	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
        	
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
	}
    
    private void verificarPermisosPresentacionPreregistro(EntradaPreregistro obj) throws Exception {
		Principal sp = this.ctx.getCallerPrincipal();
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		if (plgLogin.getMetodoAutenticacion(sp) != 'A'){
			// Para autenticados comprobamos si es el usuario o es un delegado con permiso para presentar	        	
			if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(obj.getNifRepresentante())){
				// Si no es el usuario quien accede miramos si es un delegado
		    	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(obj.getNifRepresentante());
		    	if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) == -1){
		    		throw new Exception("Acceso no permitido a entrada preregistro " + obj.getIdPersistencia()  + " no pertenece al usuario ni es delegado con permiso de presentar - usuario " + sp.getName());	                		
		    	}
			}
		}else{	        		
			// Para anonimos vale con el id persistencia
			if (obj.getNivelAutenticacion() != 'A'){
				throw new Exception("Acceso no permitido a entrada preregistro " + obj.getIdPersistencia() + " - usuario " + sp.getName());
			}
		}
    }    	
    
    /**
	 * Comprueba acceso anonimo a expediente	
	 * @param claveAcceso
	 * @param entradaPreregistro
	 */
    private void controlAccesoAnonimoExpediente(String claveAcceso,
			EntradaPreregistro entradaPreregistro) {
		try {
	    	if (entradaPreregistro != null) {
	    	Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO, entradaPreregistro.getCodigo());
		    	if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAnonimo(idExpediente, claveAcceso)) {
		    		throw new Exception("No tiene acceso a entrada");
		    	}
	    	}
    	} catch (Exception ex) {
    		throw new EJBException("No se puede verificar acceso a entrada");
    	}
	}
    
    private void controlAccesoAutenticadoExpediente(
			EntradaPreregistro entradaPreregistro) {
		try {
    		if (entradaPreregistro != null) {
    			Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO, entradaPreregistro.getCodigo());
    	    	if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAutenticado(idExpediente)) {
    	    		throw new Exception("No tiene acceso a entrada prregistro");
    	    	}	
    		}
        } catch (Exception he) {
            throw new EJBException(he);
        }
	}
    
}
