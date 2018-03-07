package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.modelInterfaz.ConfiguracionAvisosExpedientePAD;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaExpedientePAD;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ElementoExpedienteDelegate;

/**
 * SessionBean para mantener y consultar Expediente
 *
 * @ejb.bean
 *  name="zonaper/persistence/ExpedienteFacade"
 *  jndi-name="es.caib.zonaper.persistence.ExpedienteFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 */
public abstract class ExpedienteFacadeEJB extends HibernateEJB
{
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public Expediente obtenerExpedienteAutenticado(Long codigoExpediente)
	{
		// Recupera expediente
		Expediente expediente = obtenerExpedienteImpl(codigoExpediente);
		
		// Comprobamos que accede el usuario o si es un delegado
		Principal sp =this.ctx.getCallerPrincipal();
		PluginLoginIntf plgLogin = null;
		try {
			plgLogin = PluginFactory.getInstance().getPluginLogin();
		} catch (Exception e) {
			throw new EJBException(e);
		}
		if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
    		throw new EJBException("Acceso solo permitido para autenticado");
    	}
		// Si no es el usuario quien accede miramos si es un delegado
		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(expediente.getNifRepresentante())){			
        	String permisos = null;
			try {
				permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(expediente.getNifRepresentante());
			} catch (DelegateException e) {
				throw new EJBException(e);
			}
        	if (StringUtils.isEmpty(permisos)){
        		throw new EJBException("Acceso no autorizado al expediente " + expediente.getCodigo() + " - usuario " + sp.getName());
        	}
		}
		
		return expediente;		
		
	}
	
	/**
     * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public boolean verificarAccesoExpedienteAnonimo(Long codigoExpediente, String claveAcceso) {
		try {
			// Obtenemos expediente
			Expediente expediente = this.obtenerExpedienteImpl(codigoExpediente);
			if (expediente == null) {
				return false;
			}
			
			// Buscamos si existe algun elemento del expediente con ese id persistencia
			ElementoExpedienteDelegate elexDelegate = DelegateUtil.getElementoExpedienteDelegate();
			for (Iterator it = expediente.getElementos().iterator(); it.hasNext();) {
				ElementoExpediente ele = (ElementoExpediente) it.next();
				if ( ele.isAccesoAnonimoExpediente() &&
						ele.getIdentificadorPersistencia().equals(claveAcceso) && 
						!ele.getIdentificadorPersistencia().startsWith("MIGRADO-")) {
					return true;
				}
			}
			
			return false;			
		} catch (Exception he) 
		{	        
			throw new EJBException(he);
	    } 
	}
	
	/**
     * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public boolean verificarAccesoExpedienteAutenticado(Long codigoExpediente) {
		try {
			// Obtenemos expediente
			Expediente expediente = this.obtenerExpedienteImpl(codigoExpediente);
			if (expediente == null) {
				return false;
			}
			
			// Verificamos que el nif sea el del represente o delegado
			Principal sp =this.ctx.getCallerPrincipal();
			PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
			if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
	    		return false;
	    	}
			if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(expediente.getNifRepresentante())){
				// Si no es el usuario quien accede miramos si es un delegado
	        	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(expediente.getNifRepresentante());
	        	if (StringUtils.isEmpty(permisos)){
	        		return false;	        	
	        	}
			}
			return true;				
		} catch (Exception he) 
		{	        
			throw new EJBException(he);
	    } 
	}
		
	
	/**
     * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public Expediente obtenerExpedienteAnonimo(Long codigoExpediente, String claveAcceso )
	{		
		// Verificamos acceso
		if (!verificarAccesoExpedienteAnonimo(codigoExpediente, claveAcceso)) {
			throw new EJBException("No se tiene acceso a expediente");
		}	
		
		// Cargamos expediente
		Expediente expediente =  this.obtenerExpedienteImpl(codigoExpediente);
		return expediente;		
	}
	
	
	/**
	 * Borrar expediente
	 * @param unidadAdministrativa
	 * @param identificadorExpediente
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     * 
	 */
	public void borrarExpedienteReal(long unidadAdministrativa, String identificadorExpediente)
	{
		Expediente expe = obtenerExpedienteImpl(unidadAdministrativa,
				identificadorExpediente);
		if (expe == null) {
			throw new EJBException("No existe expediente " + identificadorExpediente + " - UA: " + unidadAdministrativa);
		}
		
		if (Expediente.TIPO_EXPEDIENTE_VIRTUAL.equals(expe.getTipoExpediente())) {
			throw new EJBException("El expediente " + identificadorExpediente + " - UA: " + unidadAdministrativa + " es virtual");
		}
		
		if (expe.getElementos() != null && expe.getElementos().size() > 0) {
			throw new EJBException("No se puede borrar expediente con elementos (expediente " + identificadorExpediente + " - UA: " + unidadAdministrativa + ")");
		}
		
		removeExpedienteImpl(expe.getCodigo());
		
	}
	
	/**
	 * Borrar expediente virtual
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * 
	 */
	public void borrarExpedienteVirtual(Long codigoExpediente)
	{
		// Verificamos que sea virtual
		Expediente expe = obtenerExpedienteImpl(codigoExpediente);
		if (!Expediente.TIPO_EXPEDIENTE_VIRTUAL.equals(expe.getTipoExpediente())) {
			throw new EJBException("El expediente no es virtual");
		}
		// Borramos expediente
		removeExpedienteImpl(expe.getCodigo());
		
	}

	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public boolean existeExpedienteReal( long unidadAdministrativa, String identificadorExpediente)
	{
		Expediente expe = obtenerExpedienteImpl(unidadAdministrativa,
				identificadorExpediente);		
		return (expe != null && Expediente.TIPO_EXPEDIENTE_REAL.equals(expe.getTipoExpediente()));		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public Expediente obtenerExpedienteAuto( long unidadAdministrativa, String identificadorExpediente )
	{
		return obtenerExpedienteImpl(unidadAdministrativa,
				identificadorExpediente);
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public Expediente obtenerExpedienteAuto( Long codigoExpediente )
	{
		Expediente expe = obtenerExpedienteImpl(codigoExpediente);
		return expe;
	}
	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public Expediente obtenerExpedienteReal( long unidadAdministrativa, String identificadorExpediente, String claveExpediente )
	{
		Expediente expe = obtenerExpedienteImpl(unidadAdministrativa,
				identificadorExpediente);
		
		if (expe.getClaveExpediente() != null && !expe.getClaveExpediente().equals(claveExpediente)) {
			throw new EJBException("No se ha proporcionado la clave correcta de acceso al expediente");
		}
		
		if (!Expediente.TIPO_EXPEDIENTE_REAL.equals(expe.getTipoExpediente())) {
			throw new EJBException("El expediente " + identificadorExpediente + " - UA: " + unidadAdministrativa + " es virtual");
		}
				
		return expe;
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public Expediente obtenerExpedienteVirtual( Long codigoExpedienteVirtual )
	{
		Expediente expe = obtenerExpedienteImpl(codigoExpedienteVirtual);
		if (expe != null) {
			if (!Expediente.TIPO_EXPEDIENTE_VIRTUAL.equals(expe.getTipoExpediente())) {
				throw new EJBException("El expediente " + expe.getIdExpediente() + " - UA: " + expe.getUnidadAdministrativa() + " no es virtual");
			}
		}
				
		return expe;
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void modificarAvisosExpedienteReal( long unidadAdministrativa, String identificadorExpediente, String claveExpediente, ConfiguracionAvisosExpedientePAD configuracionAvisos)  {
		
		// Obtenemos expediente
		Expediente expe = this.obtenerExpedienteReal(unidadAdministrativa, identificadorExpediente, claveExpediente);
				
		if (!Expediente.TIPO_EXPEDIENTE_REAL.equals(expe.getTipoExpediente())) {
			throw new EJBException("El expediente " + identificadorExpediente + " - UA: " + unidadAdministrativa + " es virtual");
		}
		
		// Modificamos avisos
		Session session = getSession();
		try
		{
			if (configuracionAvisos.getHabilitarAvisos() != null) {
				expe.setHabilitarAvisos(configuracionAvisos.getHabilitarAvisos().booleanValue()?"S":"N");
				if (configuracionAvisos.getHabilitarAvisos().booleanValue()) {
					expe.setAvisoEmail(configuracionAvisos.getAvisoEmail());
					expe.setAvisoSMS(configuracionAvisos.getAvisoSMS());
				} else {
					expe.setAvisoEmail(null);
					expe.setAvisoSMS(null);
				}
			} else {
				expe.setHabilitarAvisos("N");
				expe.setAvisoEmail(null);
				expe.setAvisoSMS(null);
			}
			session.update( expe );
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
	
		
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public Long grabarExpedienteReal( Expediente expediente ) 
	{
		if (!Expediente.TIPO_EXPEDIENTE_REAL.equals(expediente.getTipoExpediente())) {
			throw new EJBException("El expediente no puede ser virtual");
		}
		
		// Damos de alta el expediente
		Session session = getSession();
		try
		{
			if ( expediente.getCodigo() == null )
			{
				session.save( expediente );
			}
			else
			{
				session.update( expediente );
			}
			return expediente.getCodigo();
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
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public Long grabarExpedienteVirtual( Expediente expediente ) 
	{
		if (!Expediente.TIPO_EXPEDIENTE_VIRTUAL.equals(expediente.getTipoExpediente())) {
			throw new EJBException("El expediente debe ser virtual");
		}
		
		// Damos de alta el expediente
		Session session = getSession();
		try
		{
			if ( expediente.getCodigo() == null )
			{
				session.save( expediente );
			}
			else
			{
				session.update( expediente );
			}
			return expediente.getCodigo();
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
	
	/**
	 * Convierte expediente virtual en expediente real para no generar nuevo id de expediente.
	 * 
	 * @param expeVirtual
	 * @param expeReal
	 * 
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void convertirExpedienteVirtualAReal(Expediente expeVirtual,
			Expediente expeReal) {
		
		if (!Expediente.TIPO_EXPEDIENTE_VIRTUAL.equals(expeVirtual.getTipoExpediente())) {
			throw new EJBException("El expediente debe ser virtual");
		}
		
		if (!Expediente.TIPO_EXPEDIENTE_REAL.equals(expeReal.getTipoExpediente())) {
			throw new EJBException("El expediente debe ser real");
		}
		
		Session session = getSession();
		try
		{
			// Guardamos expediente real en el virtual, manteniendo codigo elementos
			for (Iterator it = expeVirtual.getElementos().iterator(); it.hasNext();) {
				ElementoExpediente ev = (ElementoExpediente) it.next();
				ElementoExpediente er = expeReal.obtenerElementoExpediente(ev.getTipoElemento(), ev.getCodigoElemento());
				er.setCodigo(ev.getCodigo());				
			}
			expeReal.setCodigo(expeVirtual.getCodigo());
			session.update( expeReal );				
		}
		catch (Exception he) 
		{   
			throw new EJBException(he);
	    } 
		finally 
		{
	        close(session);
	    }
		
		
	}
	
	/**
	 * Obtiene numero de expedientes pendientes de revisar para el usuario autenticado (con avisos pendientes, con notificaciones pendientes o preregistro pendiente confirmar)
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public int obtenerNumeroExpedientesPendientesUsuario() 
	{
		Session session = getSession();
		try
		{
			Principal sp =this.ctx.getCallerPrincipal();
			PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
			if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
	    		throw new HibernateException("Acceso solo permitido para autenticado");
	    	}
			Query query = session
			.createQuery( "SELECT count(e) FROM Expediente e where e.nifRepresentante = :nifRepresentante and (e.estado='" + ConstantesZPE.ESTADO_SOLICITUD_ENVIADA_PENDIENTE_DOCUMENTACION_PRESENCIAL + "' or e.estado='" + ConstantesZPE.ESTADO_AVISO_PENDIENTE + "' or e.estado='" + ConstantesZPE.ESTADO_NOTIFICACION_PENDIENTE + "' )" )
			.setParameter("nifRepresentante",plgLogin.getNif(this.ctx.getCallerPrincipal()));
			return Integer.parseInt(query.uniqueResult().toString());	
		}
		catch (Exception he) 
		{   
			throw new EJBException(he);
	    } 
		finally 
		{
	        close(session);
	    }
	}
	
	
	/**
	 * Obtiene expedientes gestionados por el gestor
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
	public Page busquedaPaginadaExpedientesReales(
			FiltroBusquedaExpedientePAD filtro, int numPagina, int longPagina) {
		Session session = getSession();
        try 
        {
        	Map parameters = new HashMap();
        	
        	String sqlFiltro = "";
        	
        	// Filtro año / mes
	   		if ( filtro.getAnyo() > 0 )
	   		 {
	   			 GregorianCalendar gregorianCalendar1 = null;
	   			 GregorianCalendar gregorianCalendar2 = null;
	   			 if ( filtro.getMes() <= 0 )
	   			 {
	   			 	 gregorianCalendar1 = new GregorianCalendar( filtro.getAnyo(), 0, 1 );
	   			 	 gregorianCalendar2 = new GregorianCalendar( filtro.getAnyo(), 11, 31 );
	   			 }
	   			 else
	   			 {
	   				 gregorianCalendar1 = new GregorianCalendar( filtro.getAnyo(), filtro.getMes(), 1 );
	   				 int year =  filtro.getAnyo();
	   				 int month = filtro.getMes();
	   				 gregorianCalendar2 = new GregorianCalendar( year, month, gregorianCalendar1.getMaximum( GregorianCalendar.DAY_OF_MONTH ) );				 
	   			 }
	   			 
	   			sqlFiltro += " and (e.fecha between :fechaInicio and :fechaFin)"; 
	   			parameters.put("fechaInicio", new java.sql.Date(gregorianCalendar1.getTime().getTime()));
	   			parameters.put("fechaFin", new java.sql.Date(gregorianCalendar2.getTime().getTime()));	   				   		
	   		 }
        	
	   		// Filtro fecha desde / hasta
	   		 if ( filtro.getFechaDesde() != null) {
	   			sqlFiltro += " and e.fecha >= :fechaDesde";
	   			parameters.put("fechaDesde", filtro.getFechaDesde());	   			
			 }
			 if ( filtro.getFechaHasta() != null) {
				sqlFiltro += " and e.fecha <= :fechaHasta";
		   		parameters.put("fechaHasta", filtro.getFechaHasta());	
			 }	
        	
			// Filtro nif
	   		if (StringUtils.isNotBlank(filtro.getNifRepresentante())) {
	   			sqlFiltro += " and e.nifRepresentante like :nifRepresentante";
	   			parameters.put("nifRepresentante", filtro.getNifRepresentante());	   			
	   		}
	   		// Filtro numero entrada
	   		if (StringUtils.isNotBlank(filtro.getNumeroEntradaBTE())) {
	   			sqlFiltro += " and e.numeroEntradaBTE like :numeroEntradaBTE";
	   			parameters.put("numeroEntradaBTE", filtro.getNumeroEntradaBTE());
	   		}
	   		// Filtro numero expediente
	   		if (StringUtils.isNotBlank(filtro.getIdExpediente())) {
	   			sqlFiltro += " and upper(e.idExpediente) like :idExpediente";
	   			parameters.put("idExpediente", "%" + filtro.getIdExpediente().toUpperCase() + "%");
	   		}	   	   	
	   		
	   		// Estado
	   		if (StringUtils.isNotBlank(filtro.getEstado())) {
	   			sqlFiltro += " and e.estado = :estado";
	   			parameters.put("estado", filtro.getEstado());
	   		}
	   		
			// Crea query 
        	Query query = session.createQuery("FROM Expediente AS e WHERE e.tipoExpediente = 'E' and e.idProcedimiento in (:procedimientos) " +
        				sqlFiltro + 
        				" ORDER BY e.fecha DESC" );
        	query.setCacheable(false);	
        	
        	// Establece parametros fijos
        	query.setParameterList("procedimientos",filtro.getIdentificadorProcedimientos());
        	// Establece parametros filtro
        	for (Iterator it = parameters.keySet().iterator(); it.hasNext();) {
        		String paramName = (String) it.next();
        		Object paramValue = parameters.get(paramName);
        		query.setParameter(paramName,paramValue);
        	}
        	
        	// Realizamos busqueda paginada
			Page page = new Page( query, numPagina, longPagina );
						
            return page;
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
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
	 * Busca id procedimientos usados en expedientes del usuario logeado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public List obtenerProcedimientosId(Date fecha) {
		List resultado = new ArrayList();
		Session session = getSession();
        try 
        {
        	// Construimos sql viendo si aplicamos filtro expedientes
        	String sqlFiltro = "";
        	if (fecha != null) {
        		// Añadimos lista de expedientes	
        		sqlFiltro = " AND e.fecha >= :fechaLimite"; 	        		        
        	}
        	
        	// Obtenemos nif usuario autenticado
        	String nifUser = PluginFactory.getInstance().getPluginLogin().getNif(this.ctx.getCallerPrincipal());
        	Query query = 
            		session.createQuery( 
            				"SELECT e.idProcedimiento FROM Expediente AS e WHERE e.nifRepresentante = :nifUser" + 
                    				sqlFiltro + " ORDER BY e.fecha DESC")
            		.setParameter("nifUser",nifUser);
        	
        	if (fecha != null){
    			query.setParameter("fechaLimite",fecha);
        	}
        	
        	List result = query.list();
        	if (result != null) {
        		for (Iterator it = result.iterator(); it.hasNext();) {
        			String idProc = (String) it.next();
        			if (!resultado.contains(idProc)) {
        				resultado.add(idProc);
        			}
        		}
        	}
        	
        	return resultado;
        	
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
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
	 * Realiza búsqueda paginada para expedientes del usuario logeado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public Page busquedaPaginadaExpedientes(int pagina, int longitudPagina, List filtroExpe )
	{
		Session session = getSession();
        try 
        {
        	// Obtenemos nif usuario autenticado
        	String nifUser = PluginFactory.getInstance().getPluginLogin().getNif(this.ctx.getCallerPrincipal());
        	
        	// Construimos sql de filtro expedientes
        	String sqlFiltro = "";
        	if (filtroExpe != null) {
        		// Añadimos lista de expedientes	
	        	sqlFiltro = " AND e.codigo in (:filtroExpe) ";        		
        	}
        	
        	// Realizamos busqueda paginada
        	Query query = 
        		session.createQuery( 
        				"FROM Expediente AS e WHERE e.nifRepresentante = :nifUser " +
        				sqlFiltro + 
        				" ORDER BY e.fechaFin DESC,e.codigo DESC" )
        		.setParameter("nifUser",nifUser);   
        	if (filtroExpe != null) {
        		if (filtroExpe.size() > 0) {
        	  		query.setParameterList("filtroExpe",filtroExpe);
        		} else {
        			// Si no se ha especificado ningun expediente en el filtro no debemos devolver ningun resultado
        			List listaVacia = new ArrayList();
        			listaVacia.add("");
        			query.setParameterList("filtroExpe",listaVacia);
        		}
        	}
            Page page = new Page( query, pagina, longitudPagina );
            return page;
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
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
	 * Realiza búsqueda paginada para expedientes de la entidad
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public Page busquedaPaginadaExpedientesEntidadDelegada(int pagina, int longitudPagina, String nifEntidad, List filtroExpe )
	{
		Session session = getSession();
        try 
        {
        	// Comprobamos si usuario esta autenticado
        	Principal sp = this.ctx.getCallerPrincipal(); 
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO) throw new HibernateException("Debe estar autenticado");
        	
        	// 	Comprobamos que el usuario es delegado de la entidad
        	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nifEntidad);
        	if (StringUtils.isEmpty(permisos)){
        		throw new Exception("El usuario no es delegado de la entidad");
        	}
        	
        	// Construimos sql viendo si aplicamos filtro expedientes
        	String sqlFiltro = "";
        	if (filtroExpe != null) {
        		// Añadimos lista de expedientes	
        		sqlFiltro = " AND e.codigo in (:filtroExpe) "; 	        		        
        	}
        	
        	// Realizamos busqueda paginada
        	Query query = 
        		session.createQuery( 
        				"FROM Expediente AS e WHERE e.nifRepresentante = :nifEntidad " + 
        				sqlFiltro + 
        				" ORDER BY e.fechaFin DESC,e.codigo DESC" )
        		.setParameter("nifEntidad",nifEntidad);    
        	if (filtroExpe != null) {
        		if (filtroExpe.size() > 0) {
        	  		query.setParameterList("filtroExpe",filtroExpe);
        		} else {
        			// Si no se ha especificado ningun expediente en el filtro no debemos devolver ningun resultado
        			List listaVacia = new ArrayList();
        			listaVacia.add("");
        			query.setParameterList("filtroExpe",listaVacia);
        		}
        	}
            Page page = new Page( query, pagina, longitudPagina );
            return page;
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
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
	 * Realiza búsqueda paginada para expedientes de la entidad
	 * 
    * @ejb.interface-method
    * @ejb.permission role-name="${role.auto}" 
    */
	public void actualizaEstadoExpedienteAuto(Long id, String estado, Date fecha) {
		// Actualizamos estado expediente
		Session session = getSession();
		try
		{
			Expediente expediente = ( Expediente ) session.load( Expediente.class, id );			
			expediente.setFechaFin(fecha);
			expediente.setEstado(estado);
			session.update(expediente);
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
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String obtenerEntidadExpediente( Long codigoExpediente )
	{
		try {
			String entidad = null;
			
			// Obtiene expediente
			Expediente expe = obtenerExpedienteImpl(codigoExpediente);
			if (expe == null) {
				throw new Exception("No existe expediente con codigo: " + codigoExpediente);
			}
			
			// Obtiene entidad procedimiento
			entidad = obtenerEntidadProcedimiento(expe.getIdProcedimiento());
			
			return entidad;
		} catch (Exception e) {
			log.error("Error obteniendo entidad procedimiento: " + e.getMessage(), e);
			throw new EJBException("Error obteniendo entidad procedimiento: " + e.getMessage(), e);
		}
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	//		FUNCIONES AUXILIARES
	// ------------------------------------------------------------------------------------------------------------------
	
	private String obtenerEntidadProcedimiento(String idProcedimiento) throws Exception {
	   String entidad = null;
	   ProcedimientoBTE proc = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(idProcedimiento);
	   if (proc != null) {
		   entidad = proc.getEntidad().getIdentificador();
	   }
	   return entidad;
   }
	
	private Expediente obtenerExpedienteImpl(long unidadAdministrativa,
			String identificadorExpediente) {
		Session session = getSession();
		try
		{
			Query query = session.createQuery( "FROM Expediente e where e.idExpediente = :id and e.unidadAdministrativa = :ua" );
			query.setParameter( "id", identificadorExpediente );
			query.setParameter( "ua", new Long( unidadAdministrativa ) );
			//query.setCacheable( true );
			Expediente expediente = ( Expediente ) query.uniqueResult();
			if ( expediente != null )
			{
				Hibernate.initialize( expediente.getElementos() );				
			}
			return expediente;
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
	
	
	private void removeExpedienteImpl(Long codigoExpediente) {
		Session session = getSession();
		try
		{
			Expediente expediente = ( Expediente )session.load( Expediente.class, codigoExpediente );
			session.delete(expediente);										
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
	
	private Expediente obtenerExpedienteImpl(Long codigoExpediente) {
		Session session = getSession();
		try
		{			
			Expediente expediente = ( Expediente )session.load( Expediente.class, codigoExpediente );
			Hibernate.initialize( expediente.getElementos() );			
			return expediente;		
		} 
		catch (Exception he) 
		{	        
			throw new EJBException(he);
	    } 
		finally 
		{
	        close(session);
	    }
	}
	
}
