package es.caib.zonaper.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.NotificacionTelematicaDelegate;


/**
 * Proceso de rechazar una notificación en una transacción nueva.
 * 
 *
 * @ejb.bean
 *  name="zonaper/persistence/ProcesoRechazarNotificacionFacade"
 *  jndi-name="es.caib.zonaper.persistence.ProcesoRechazarNotificacionFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="RequiresNew"
 */
public abstract class ProcesoRechazarNotificacionFacadeEJB extends HibernateEJB
{
	private static Log backupLog = LogFactory.getLog( ProcesoRechazarNotificacionFacadeEJB.class );
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}	
	
	/**
	 * Realiza el backup de un tramite persistente: elimina de tabla de persistencia y lo pasa a la tabla backup 
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.auto}"
     * 
     */
	public void rechazarNotificacion( Long idNotificacion )
	{
		try {
			backupLog.debug( "Rechazando notificacion " + idNotificacion);			
			NotificacionTelematicaDelegate dlg = DelegateUtil.getNotificacionTelematicaDelegate();
			dlg.rechazarNotificacion(idNotificacion);		
			backupLog.debug( "Notificacion " + idNotificacion + " rechazada");			
		} catch (Exception ex) {
			throw new EJBException(ex);
		}
					
	}
	    
}
