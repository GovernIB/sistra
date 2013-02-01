package es.caib.zonaper.persistence.ejb;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * SessionBean para mantener y consultar Expediente
 *
 * @ejb.bean
 *  name="zonaper/persistence/EventoExpedienteFacade"
 *  jndi-name="es.caib.zonaper.persistence.EventoExpedienteFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class EventoExpedienteFacadeEJB extends HibernateEJB
{
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public EventoExpediente obtenerEventoExpedienteAutenticado( Long id )
	{
		// Verificamos acceso expediente	
		verificarAccesoAutenticado(id);
		
		return recuperarEventoPorId(id);
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public EventoExpediente obtenerEventoExpedienteAnonimo( Long id , String idPersistencia)
	{
		// Verificamos acceso expediente	
		verificarAccesoAnonimo(id, idPersistencia);
		
		return recuperarEventoPorId(id);
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public EventoExpediente obtenerEventoExpediente( Long id )
	{
		Session session = getSession();
		try
		{
			EventoExpediente eventoExpediente = ( EventoExpediente ) session.load( EventoExpediente.class, id );
			Hibernate.initialize( eventoExpediente.getDocumentos() );
			return eventoExpediente;
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
     */
	public EventoExpediente obtenerEventoExpediente( long unidadAdministrativa, String identificadorExpediente, java.sql.Timestamp fechaEvento )
	{
		Session session = getSession();
		try
		{
			Query query = 
				session.createQuery( "SELECT ee FROM EventoExpediente AS ee, ElementoExpediente el where el.expediente.idExpediente = :identificadorExpediente and el.expediente.unidadAdministrativa = :ua and ee.fecha = :fechaEvento and el.tipoElemento = '" + ElementoExpediente.TIPO_AVISO_EXPEDIENTE +"' and el.codigoElemento = ee.codigo" ).
				setParameter("identificadorExpediente", identificadorExpediente).
				setParameter("ua", new Long( unidadAdministrativa ) ).
				setParameter("fechaEvento", fechaEvento);
			EventoExpediente eventoExpediente = ( EventoExpediente ) query.uniqueResult();
			if ( eventoExpediente != null )
			{
				Hibernate.initialize( eventoExpediente.getDocumentos() );
			}
			return eventoExpediente;
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
	public Long grabarNuevoEventoExpediente( EventoExpediente evento )
	{
		Session session = getSession();
		try
		{
			if ( evento.getCodigo() == null )
			{
				session.save( evento );
			}
			else
			{
				throw new Exception("No se permite actualizar evento");
			}
			return evento.getCodigo();
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
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void marcarConsultadoEventoExpedienteAutenticado( Long id )
	{
		// Verificamos acceso expediente	
		Long idExpediente = verificarAccesoAutenticado(id);
		
		// Marcamos como consultado
		marcarEventoConsultadoImpl(id);
		
		// Actualizamos estado expediente
		try{
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(idExpediente);
		}catch(Exception ex){
			throw new EJBException("Error actualizando estado expediente",ex);
		}		
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void marcarConsultadoEventoExpedienteAnonimo( Long id, String idPersistencia )
	{
		// Verificamos acceso expediente	
		Long idExpediente = verificarAccesoAnonimo(id, idPersistencia);
		
		// Marcamos como consultado
		marcarEventoConsultadoImpl(id);
		
		// Actualizamos estado expediente
		try{
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(idExpediente);
		}catch(Exception ex){
			throw new EJBException("Error actualizando estado expediente",ex);
		}		
	}			
	
	// ------------------------------------------------------------------------------------------------
	//	FUNCIONES PRIVADAS
	// ------------------------------------------------------------------------------------------------
	
	private void marcarEventoConsultadoImpl(Long id) {
		Session session = getSession();
		try
		{
			EventoExpediente eventoExpediente = ( EventoExpediente ) session.load( EventoExpediente.class, id );
			if (eventoExpediente.getFechaConsulta() == null){
				eventoExpediente.setFechaConsulta(new Timestamp(System.currentTimeMillis()));
				session.update(eventoExpediente);				
			}
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
	
	private EventoExpediente recuperarEventoPorId(Long id) {
		Session session = getSession();
		try
		{
			EventoExpediente eventoExpediente = ( EventoExpediente ) session.load( EventoExpediente.class, id );
			Hibernate.initialize( eventoExpediente.getDocumentos() );
			return eventoExpediente;
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
	
	private Long verificarAccesoAutenticado(Long idEvento) {
		try {
			Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_AVISO_EXPEDIENTE, idEvento);
			if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAutenticado(idExpediente)) {
				throw new Exception("No tiene acceso al expediente");
			}
			return idExpediente;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	
	private Long verificarAccesoAnonimo(Long idEvento, String idPersistencia) {
		try {
			Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_AVISO_EXPEDIENTE, idEvento);
			if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAnonimo(idExpediente, idPersistencia)) {
				throw new Exception("No tiene acceso al expediente");
			}
			return idExpediente;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}
		

	
}
