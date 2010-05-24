package es.caib.mobtratel.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.persistence.intf.MobTraTelFacade;
import es.caib.mobtratel.persistence.util.MobTraTelFacadeUtil;

/**
 * Facade de Mobilidad
 */
public class MobTraTelDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	/**
	 * Realiza un Envio con n mensajes de tipo Email o SMS
	 * Podra lanzar las siguientes excepciones:
	 * . LimiteDestinatariosException: Cuando se supere el limite de destinatarios por mensaje. 
	 * . PermisoException: Cuando se intente enviar un SMS con una cuenta a la que no tiene
	 *   permiso el usuario que realiza la invocacion
	 * . MobilidadException: En cualquier otro caso.
	 * Devuelve el codigo del Envio.
	 * @param mensaje
	 * @return Devuelve el codigo del Envio
	 * @throws DelegateException
	 */
	public String envioMensaje( MensajeEnvio mensaje ) throws DelegateException {
        try {
            return getFacade().envioMensaje(mensaje);
        } catch (DelegateException e) {
        	throw e;
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private MobTraTelFacade getFacade() throws NamingException,RemoteException,CreateException {         	    	
    	return MobTraTelFacadeUtil.getHome( ).create();
    }
    

    protected MobTraTelDelegate() throws DelegateException {      
    }                  
}

