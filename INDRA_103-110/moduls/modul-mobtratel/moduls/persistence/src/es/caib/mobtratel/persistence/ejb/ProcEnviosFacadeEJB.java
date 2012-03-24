package es.caib.mobtratel.persistence.ejb;

import java.util.List;
import java.util.Properties;

import javax.ejb.CreateException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.MensajeEmail;
import es.caib.mobtratel.model.MensajeSms;
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
    			if (StringUtils.isNotBlank(config.getProperty("envio.limite.verificarEnvio"))) {
    				PluginEnvio.setLimiteDiasVerificar(Integer.parseInt(config.getProperty("envio.limite.verificarEnvio")));
    			} else {
    				PluginEnvio.setLimiteDiasVerificar(5);
    			}
    			log.debug ( "simularEnvio : [" + PluginEnvio.getSimularEnvio() + "]");    			
    		}
    		catch( Exception exc )
    		{    			
    			log.error( "Error estableciendo propiedad simularEnvio. Establecemos a false.",exc );
    			PluginEnvio.setSimularEnvio(new Boolean(false));
    			PluginEnvio.setLimiteDiasVerificar(5);
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
	
	
	/**
	 * Verifica envíos pendientes
	 * 
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public void verificar()
	{
		EnvioDelegate ed = DelegateUtil.getEnvioDelegate();
		
		/* Recuperamos codigos de envios pendientes */
		Long idEmailsPendientes[];
		Long idSmsPendientes[];
		try {
			
			// Buscamos emails pendientes de verificar
			List emailsPendientes = ed.listarEmailsPendientesVerificar();	
			idEmailsPendientes = new Long[emailsPendientes.size()];
			for (int i = 0; i < emailsPendientes.size();i++){
				idEmailsPendientes[i] = ((MensajeEmail) (emailsPendientes.get(i)) ).getCodigo();
			}			
			
			// Buscamos sms pendientes de verificar
			List smsPendientes = ed.listarSmssPendientesVerificar();	
			idSmsPendientes = new Long[smsPendientes.size()];
			for (int i = 0; i < smsPendientes.size();i++){
				idSmsPendientes[i] = ((MensajeSms) (smsPendientes.get(i)) ).getCodigo();
			}	
			
			
		} catch (DelegateException e) {			
			log.error("Error accediendo a los mensajes pendientes de verificar",e);
			return;
		}
		
		/* Verificamos envios */
		try{
			PluginEnvio.verificarEmails(idEmailsPendientes);
			PluginEnvio.verificarSmss(idSmsPendientes);
		} catch (Exception e) {
			log.error("Error verificando envios",e);
		}
		
		
		
	}
	



}