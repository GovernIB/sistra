package es.caib.zonaper.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
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
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
	public Expediente obtenerExpedienteAutenticado( Long id )
	{
		Session session = getSession();
		try
		{			
			Expediente expediente = ( Expediente )session.load( Expediente.class, id );
			Hibernate.initialize( expediente.getElementos() );			
			
			// Solo el usuario puede acceder al expediente
			if (!this.ctx.getCallerPrincipal().getName().equals(expediente.getSeyconCiudadano())){
				throw new HibernateException("Acceso no autorizado al expediente");
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
	
	
	 /**
     * @throws ExcepcionPAD 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
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
	public Expediente obtenerExpediente( long unidadAdministrativa, String identificadorExpediente )
	{
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
     * @ejb.permission role-name="${role.user}"
     */
	public int obtenerNumeroExpedientesPendientesUsuario() 
	{
		Session session = getSession();
		try
		{
			String user = this.ctx.getCallerPrincipal().getName();
			Query query = session
			.createQuery( "SELECT count(e) FROM Expediente e where e.seyconCiudadano = :user and (e.estado='" + Expediente.ESTADO_AVISO_PENDIENTE + "' or e.estado='" + Expediente.ESTADO_NOTIFICACION_PENDIENTE + "' )" )
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
	
	
	
	
}
