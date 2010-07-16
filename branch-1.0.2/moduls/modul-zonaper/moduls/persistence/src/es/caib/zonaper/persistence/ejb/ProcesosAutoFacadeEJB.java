package es.caib.zonaper.persistence.ejb;

import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.util.AvisosMovilidad;
import es.caib.zonaper.persistence.util.UsernamePasswordCallbackHandler;

/**
 * SessionBean que realiza procesos auto.
 * Los metodos se ejecutaran con el usuario auto
 * 
 *
 * @ejb.bean
 *  name="zonaper/persistence/ProcesosAutoFacade"
 *  jndi-name="es.caib.zonaper.persistence.ProcesosAutoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ProcesosAutoFacadeEJB extends HibernateEJB
{
	private static Log backupLog = LogFactory.getLog( ProcesosAutoFacadeEJB.class );
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	
	/**
	 * Actualiza estado de un expediente	
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     * 
     */
	public void actualizaEstadoExpediente(Long id)  
	{
		backupLog.debug("actualiza estado expediente " + id);
		
		LoginContext lc = null;		
		try{					
			// Realizamos login JAAS con usuario para proceso automatico	
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();
			
			// Realizamos envio
			doActualizaEstadoExpediente(id);	
		}catch (Exception le){
			throw new EJBException("No se puede hacer login con usuario auto",le);
		}finally{				
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}
	
	
	/**
	 * Genera aviso de creacion de un elemento de un expediente
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     * 
     */
	public void avisoCreacionElementoExpediente(ElementoExpediente ele) {
		
		backupLog.debug("aviso creacion elemento expediente");
		
		LoginContext lc = null;		
		try{					
			// Realizamos login JAAS con usuario para proceso automatico					
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();
			
			// Realizamos aviso
			AvisosMovilidad.getInstance().avisoCreacionElementoExpediente(ele);
		}catch (LoginException le){
			throw new EJBException("No se puede hacer login con usuario auto",le);
		}catch (Exception e){
			throw new EJBException("Error realizando aviso creacion elemento expediente con usuario auto",e);
		}finally{				
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
		
	}
	
	// ----------------------------------------------------------------------------------------------
	//	FUNCIONES AUXILIARES
	// ----------------------------------------------------------------------------------------------
	private ElementoExpediente obtenerUltimoElementoExpediente(Long id) 
	{
		Session session = getSession();
		try
		{
			Expediente expediente = ( Expediente )session.load( Expediente.class, id );			
			if (!expediente.getElementos().isEmpty()){
				ElementoExpediente e = null;
				for (Iterator it = expediente.getElementos().iterator();it.hasNext();){
					e = (ElementoExpediente) it.next();
				}
				return e;
			}
			return null;
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
	

	private void doActualizaEstadoExpediente(Long id){
		// Obtenemos ultimo elemento del expediente y obtenemos su detalle
		ElementoExpedienteItf de = null;
		ElementoExpediente e = obtenerUltimoElementoExpediente(id);		
		try{
			de = DelegateUtil.getElementoExpedienteDelegate().obtenerDetalleElementoExpediente(e.getCodigo());
		}catch(Exception dex){
			throw new EJBException(dex);
		}
		
		// Calculamos estado y fecha fin
		String estado = null;
		Date fechaFin = null;
		if (e.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA)){
			estado = Expediente.ESTADO_SOLICITUD_ENVIADA;
			fechaFin = ((EntradaTelematica) de).getFecha();
		}else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO)){
			estado = Expediente.ESTADO_SOLICITUD_ENVIADA;
			fechaFin = ((EntradaPreregistro) de).getFecha();
		}else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE)){
			if ( ((EventoExpediente) de).getFechaConsulta() != null){
				estado 	 = Expediente.ESTADO_AVISO_RECIBIDO;
				fechaFin = ((EventoExpediente) de).getFecha();
			}else{
				estado = Expediente.ESTADO_AVISO_PENDIENTE;
				fechaFin = ((EventoExpediente) de).getFecha();
			}
		}else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION)){
			if ( ((NotificacionTelematica) de).getFechaAcuse() != null){
				estado = Expediente.ESTADO_NOTIFICACION_RECIBIDA;
				fechaFin = ((NotificacionTelematica) de).getFechaRegistro();
			}else{
				estado = Expediente.ESTADO_NOTIFICACION_PENDIENTE;
				fechaFin = ((NotificacionTelematica) de).getFechaRegistro();
			}
		}
		
		// Actualizamos expediente
		Session session = getSession();
		try
		{
			Expediente expediente = ( Expediente )session.load( Expediente.class, id );			
			expediente.setFechaFin(fechaFin);
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
    
}
