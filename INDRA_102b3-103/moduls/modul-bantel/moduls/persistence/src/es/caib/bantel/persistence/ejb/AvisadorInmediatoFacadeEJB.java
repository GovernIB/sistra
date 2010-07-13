package es.caib.bantel.persistence.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.model.ReferenciaTramiteBandeja;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.bantel.persistence.plugins.PluginBackOffice;
import es.caib.bantel.persistence.plugins.UsernamePasswordCallbackHandler;
import es.caib.bantel.persistence.util.CacheProcesamiento;
import es.caib.bantel.persistence.util.StringUtil;

/**
 * 
 * EJB encargado de realizar los avisos inmediatos de las nuevas entradas de forma asíncrona
 * 
 * Esta forma de aviso intentará avisar al BackOffice una vez. En caso de error o caída de la cola el aviso se 
 * realizará mediante el proceso de background de avisos. 
 * 
 * @ejb.bean
 * 	name="bantel/persistence/AvisadorInmediatoFacade"
 *  destination-type = "javax.jms.Queue" 
 *  view-type="remote"
 *  transaction-type="Container"
 *  destination-jndi-name = "es.caib.bantel.persistence.AvisadorInmediatoQueue"
 *
 * @ejb.transaction type="NotSupported"
 * 
 * 
 * @jboss.destination-jndi-name queue/AvisadorBTE
 * 
 * 
 */
public class AvisadorInmediatoFacadeEJB implements MessageDrivenBean, MessageListener {
	
	MessageDrivenContext messageDrivenContext;
	private static Log log = LogFactory.getLog(AvisadorInmediatoFacadeEJB.class);
	private static int MAX_ENTRADAS=100;

	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException {
		this.messageDrivenContext = ctx;
	}

	public void ejbCreate() throws EJBException {	
		try{
			MAX_ENTRADAS = Integer.parseInt(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisoPeriodico.maxEntradas"));
		}catch(Exception ex){
			log.error("Excepcion obteniendo parametro de maximo numero de entradas. Se tomara valor="+MAX_ENTRADAS,ex );
		}
		
	}
	
	public void ejbRemove() throws EJBException {	
	}

	public void onMessage(Message message) {
		
		LoginContext lc = null;
		
		// Entradas que formaran parte del aviso (subconjunto de las indicadas en el mensaje por limite de numero de entradas y cache de duplicidad)
		List entradasParaAvisar = new ArrayList();
		List referenciasParaAvisar = new ArrayList();
		
		
		try{					
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String userAuto = props.getProperty("auto.user");
			String passAuto = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( userAuto, passAuto ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();						
			
			// Obtenemos numeros de entrada a comunicar
			String sNumeroEntradas = ((TextMessage) message).getText();			
			log.debug("Aviso entradas: Mensaje recibido: " + sNumeroEntradas);
			
			String numeroEntradas [] =  StringUtil.stringToNumeroEntradas(sNumeroEntradas);		
			
			// Comprobamos que las entradas existen (puede ser que no haya terminado transaccion para avisos inmediatos)
			// y que son del mismo trámite
			TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
			String idTramite="";
			TramiteBandeja tramite=null;					
			if (numeroEntradas != null){
					int count = 0;
					for (int i=0;i<numeroEntradas.length;i++){
						
						// Limitamos maximo de entradas					
						if (count > MAX_ENTRADAS) {
							// Dejamos para siguiente aviso
							break;
						}
						
						// Intentamos guardar entrada en cache para procesarla (si devuelve false es que ya esta en cache -> esta procesandose)
						if (!CacheProcesamiento.guardar(numeroEntradas[i])){
							// Pasamos a siguiente entrada
							continue;
						}
						
						// Indicamos que se va a procesar la entrada
						entradasParaAvisar.add(numeroEntradas[i]);
						
						// Obtenemos tramite con información destino BackOffice					
						tramite = delegate.obtenerTramiteBandeja(numeroEntradas[i]);
						if (tramite == null){
							log.debug("Entrada " + numeroEntradas[i] + " no existe en BBDD");						
							return;				
						}
						
						// Indicamos que la entrada se avisara en este aviso
						ReferenciaTramiteBandeja ref = new ReferenciaTramiteBandeja();
						ref.setNumeroEntrada(tramite.getNumeroEntrada());
						ref.setClaveAcceso(tramite.getClaveAcceso());
						referenciasParaAvisar.add(ref);
						
						
						// Comprobamos que el trámite sea el mismo para todas las entradas
						if (i==0) {
							idTramite = tramite.getTramite().getIdentificador();
						}else{
							if (!idTramite.equals(tramite.getTramite().getIdentificador())){
								throw new Exception("No se pueden realizar un aviso con entradas de distinto trámite");
							}
						}
						
						// Comprobamos que no este procesada
						if (tramite.getProcesada() == 'N'){
							count++; // incrementamos num de entradas en este aviso							
						}else{
							// Si ha sido procesada no entrara dentro del proceso
							CacheProcesamiento.borrar(numeroEntradas[i]);
							entradasParaAvisar.remove(numeroEntradas[i]);
							referenciasParaAvisar.remove(ref);
						}
					}				
			}
			
			// Avisamos a BackOffice de las nuevas entradas
			log.debug("Aviso entradas: Entradas a avisar: " +  ToStringBuilder.reflectionToString(entradasParaAvisar));					
			if (entradasParaAvisar != null && entradasParaAvisar.size()>0){			
				PluginBackOffice bo = new PluginBackOffice(tramite.getTramite());									
				bo.avisarEntradas(referenciasParaAvisar,userAuto,passAuto);				
			}
			log.debug("Aviso entradas realizado");	
			
		}catch (Exception ex){
			// Informamos al log			
			log.error("No se puede enviar aviso de nuevas entradas al BackOffice: " + ex.getMessage(),ex);
		}finally{
			
			// Nos aseguramos de que todas las entradas incluidas en el aviso se borren de cache
			CacheProcesamiento.borrar(entradasParaAvisar);
			
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}	
	

}
