package es.caib.mobtratel.persistence.ejb;

import java.util.List;
import java.util.Properties;

import javax.ejb.CreateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;
import es.caib.mobtratel.persistence.plugins.PluginEnvio;

/**
 * SessionBean para proceso de Envios.
 *
 * @ejb.bean
 *  name="mobtratel/persistence/ProcEnviosFacade"
 *  jndi-name="es.caib.mobtratel.persistence.ProcEnviosFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="NotSupported"
 *  
 */
public abstract class ProcEnviosFacadeEJB extends HibernateEJB {

	protected static Log log = LogFactory.getLog(ProcEnviosFacadeEJB.class);
	
    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
        
        // Comprobamos si hay que establecer fronton para envios
        if (PluginEnvio.getSimularEnvio() == null){
        	try
    		{
        		Properties config = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
    			PluginEnvio.setSimularEnvio( new Boolean (config.getProperty("envio.simularEnvio")));
    			PluginEnvio.setSimularEnvioDuracion( Integer.parseInt(config.getProperty("envio.simularEnvio.duracion")));
    			log.debug ( "simularEnvio : [" + PluginEnvio.getSimularEnvio() + "]");    			
    		}
    		catch( Exception exc )
    		{    			
    			log.error( "Error estableciendo propiedad simularEnvio. Establecemos a false.",exc );
    			PluginEnvio.setSimularEnvio(new Boolean(false));
    		}
        }
    }

	/**
	 * Realiza envios programados pendientes
	 * 
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public void enviar()
	{
		EnvioDelegate ed = DelegateUtil.getEnvioDelegate();
		
		/* Recuperamos codigos de envios pendientes */
		Long idPendientes[];
		try {
			List pendientes = ed.listarPendientes();	
			idPendientes = new Long[pendientes.size()];
			for (int i = 0; i < pendientes.size();i++){
				idPendientes[i] = ((Envio) (pendientes.get(i)) ).getCodigo();
			}
		} catch (DelegateException e) {			
			log.error("Error accediendo a los mensajes pendientes",e);
			return;
		}
		
		/* Realizamos envio de los mensajes de cache */
		try{
			PluginEnvio.enviar(idPendientes);
		} catch (Exception e) {
			log.error("Error realizando envios",e);
		}
	}
	
	
	/**
	 * Realiza envios inmediatos pendientes
	 * 
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public void enviarInmediatos()
	{
		
		EnvioDelegate ed = DelegateUtil.getEnvioDelegate();
		
		/* Recuperamos codigos de envios pendientes */
		Long idPendientes[];
		try {
			List pendientes = ed.listarInmediatosPendientes();	
			idPendientes = new Long[pendientes.size()];
			for (int i = 0; i < pendientes.size();i++){
				idPendientes[i] = ((Envio) (pendientes.get(i)) ).getCodigo();
			}
		} catch (DelegateException e) {			
			log.error("Error accediendo a los mensajes pendientes",e);
			return;
		}
		
		/* Realizamos envio de los mensajes de cache */
		try{
			PluginEnvio.enviar(idPendientes);
		} catch (Exception e) {
			log.error("Error realizando envios",e);
		}
	
	}
	



}