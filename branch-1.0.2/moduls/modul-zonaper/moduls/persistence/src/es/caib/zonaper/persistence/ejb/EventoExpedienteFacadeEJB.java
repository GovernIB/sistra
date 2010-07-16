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
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
	public EventoExpediente obtenerEventoExpedienteAutenticado( Long id )
	{
		// Obtenemos elemento expediente asociado para realizar control acceso		
		try {
			DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(ElementoExpediente.TIPO_AVISO_EXPEDIENTE,id);				
		} catch (Exception e) {
			throw new EJBException(e);
		}
		
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
     * @ejb.permission role-name="${role.user}"
     */
	public EventoExpediente obtenerEventoExpedienteAnonimo( Long id , String idPersistencia)
	{
		// Obtenemos elemento expediente asociado para realizar control acceso		
		try {
			DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(ElementoExpediente.TIPO_AVISO_EXPEDIENTE,id,idPersistencia);				
		} catch (Exception e) {
			throw new EJBException(e);
		}
		
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
	public Long grabarEventoExpediente( EventoExpediente evento )
	{
		Session session = getSession();
		try
		{
			if ( evento.getCodigo() == null )
			{
				evento.setFecha(new Timestamp(System.currentTimeMillis()));
				session.save( evento );
			}
			else
			{
				session.update( evento );
			}
			return evento.getCodigo();
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
     * @ejb.permission role-name="${role.user}"
     */
	public void marcarConsultadoEventoExpedienteAutenticado( Long id )
	{
		// Obtenemos elemento expediente asociado (se realiza control acceso)
		ElementoExpediente elemento;
		try {
			elemento = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(ElementoExpediente.TIPO_AVISO_EXPEDIENTE,id);				
		} catch (Exception e) {
			throw new EJBException(e);
		}
		
		
		// Marcamos como consultado
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
		
		// Actualizamos estado expediente
		try{
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(elemento.getExpediente().getCodigo());
		}catch(Exception ex){
			throw new EJBException("Error actualizando estado expediente",ex);
		}		
	}			
	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
	public void marcarConsultadoEventoExpedienteAnonimo( Long id, String idPersistencia )
	{
		// Obtenemos elemento expediente asociado (se realiza control acceso)
		ElementoExpediente elemento;
		try {
			elemento = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(ElementoExpediente.TIPO_AVISO_EXPEDIENTE,id,idPersistencia);				
		} catch (Exception e) {
			throw new EJBException(e);
		}
		
		
		// Marcamos como consultado
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
		
		// Actualizamos estado expediente
		try{
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(elemento.getExpediente().getCodigo());
		}catch(Exception ex){
			throw new EJBException("Error actualizando estado expediente",ex);
		}		
	}			
}
