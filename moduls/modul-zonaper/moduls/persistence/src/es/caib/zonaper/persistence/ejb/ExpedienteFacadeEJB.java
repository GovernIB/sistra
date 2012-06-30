package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.GregorianCalendar;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.util.DataUtil;
import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.modelInterfaz.ConfiguracionAvisosExpedientePAD;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaExpedientePAD;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

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
	public Expediente obtenerExpedienteAutenticado( Long id )
	{
		Session session = getSession();
		try
		{			
			Expediente expediente = ( Expediente )session.load( Expediente.class, id );
			Hibernate.initialize( expediente.getElementos() );			
			
			
			// Comprobamos que accede el usuario o si es un delegado
			controlAccesoExpedienteAutenticado(expediente);
			
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
	
	 /**
     * @throws ExcepcionPAD 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public Expediente obtenerExpedienteAnonimo(Long id, String idPersistencia )
	{
		EstadoExpediente estadoExpe;
		try {
			estadoExpe = DelegateUtil.getEstadoExpedienteDelegate().obtenerExpedienteAnonimo(idPersistencia);
		} catch (DelegateException e) {
			throw new EJBException("No se ha podido comprobar acceso expediente",e);
		}
		
		if (estadoExpe == null){
			log.error("Expediente no existe. Devolvemos nulo.");
			return null;
		}
		
		if (estadoExpe.getId() == null){
			log.error("Expediente no existe. Devolvemos nulo.");
			return null;
		}
		
		String tipoElemento   = estadoExpe.getId().substring(0,1);
		Long codigoElemento =   new Long(estadoExpe.getId().substring(1)); 				
		
		if (!tipoElemento.equals(EstadoExpediente.TIPO_EXPEDIENTE)){
			log.error("No hay expediente asociado a el tramite. Devolvemos nulo.");
			return null;
		}
		
		if (id.longValue() != codigoElemento.longValue()){
			throw new EJBException("El expediente no pertenece a la clave de tramitacion");
		}
		
		Session session = getSession();
		try			
		{	
			Expediente expediente = ( Expediente )session.load( Expediente.class, codigoElemento );
			if (StringUtils.isNotEmpty(expediente.getSeyconCiudadano())){
				log.error("Expediente no es anonimo");
				throw new HibernateException("Expediente no es anonimo");
			}
			Hibernate.initialize( expediente.getElementos() );
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
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public boolean existeExpediente( long unidadAdministrativa, String identificadorExpediente)
	{
		Expediente expe = obtenerExpedienteImpl(unidadAdministrativa,
				identificadorExpediente);
		return (expe != null);		
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
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public Expediente obtenerExpediente( long unidadAdministrativa, String identificadorExpediente, String claveExpediente )
	{
		Expediente expe = obtenerExpedienteImpl(unidadAdministrativa,
				identificadorExpediente);
		
		if (expe.getClaveExpediente() != null && !expe.getClaveExpediente().equals(claveExpediente)) {
			throw new EJBException("No se ha proporcionado la clave correcta de acceso al expediente");
		}
		
		return expe;
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void modificarAvisosExpediente( long unidadAdministrativa, String identificadorExpediente, String claveExpediente, ConfiguracionAvisosExpedientePAD configuracionAvisos)  {
		
		// Obtenemos expediente
		Expediente expe = this.obtenerExpediente(unidadAdministrativa, identificadorExpediente, claveExpediente);
		
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
	public Long grabarExpediente( Expediente expediente ) 
	{
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
	 * Obtiene numero de expedientes pendientes de revisar por el usuario (con avisos pendientes o con notificaciones pendientes)
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public int obtenerNumeroExpedientesPendientesUsuario() 
	{
		Session session = getSession();
		try
		{
			String user = this.ctx.getCallerPrincipal().getName();
			Query query = session
			.createQuery( "SELECT count(e) FROM Expediente e where e.seyconCiudadano = :user and (e.estado='" + ConstantesZPE.ESTADO_AVISO_PENDIENTE + "' or e.estado='" + ConstantesZPE.ESTADO_NOTIFICACION_PENDIENTE + "' )" )
			.setParameter("user",user);
			return Integer.parseInt(query.uniqueResult().toString());	
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
	 * Obtiene expedientes gestionados por el gestor
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
	public Page busquedaPaginadaExpedientesGestor(
			FiltroBusquedaExpedientePAD filtro, int numPagina, int longPagina) {
		Session session = getSession();
        try 
        {
        	Criteria criteria = session.createCriteria( Expediente.class );
        	criteria.setCacheable( false );        
        	// Filtro procedimientos
        	criteria.add(Expression.in( "idProcedimiento", filtro.getIdentificadorProcedimientos()) );
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
	   			criteria.add( Expression.between( "fecha", new java.sql.Date(gregorianCalendar1.getTime().getTime()), new java.sql.Date( DataUtil.obtenerUltimaHora(gregorianCalendar2.getTime()).getTime() )) );	   			
	   		 }
	   		// Filtro nif
	   		if (StringUtils.isNotBlank(filtro.getNifRepresentante())) {
	   			criteria.add(Expression.like("nifRepresentante", filtro.getNifRepresentante()));
	   		}
	   		// Filtro numero entrada
	   		if (StringUtils.isNotBlank(filtro.getNumeroEntradaBTE())) {
	   			criteria.add(Expression.eq("numeroEntradaBTE", filtro.getNumeroEntradaBTE()));
	   		}
	   		
	   		// Realizamos busqueda paginada
			Page page = new Page( criteria, numPagina, longPagina );
						
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
	
	
	private void controlAccesoExpedienteAutenticado(Expediente expediente) throws Exception{
		Principal sp =this.ctx.getCallerPrincipal();
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
    		throw new HibernateException("Acceso solo permitido para autenticado");
    	}
		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(expediente.getNifRepresentante())){
			// Si no es el usuario quien accede miramos si es un delegado
        	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(expediente.getNifRepresentante());
        	if (StringUtils.isEmpty(permisos)){
        		throw new Exception("Acceso no autorizado al expediente " + expediente.getCodigo() + " - usuario " + sp.getName());
        	}
		}
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
	
}
