package es.caib.bantel.persistence.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import es.caib.bantel.model.DocumentoBandeja;
import es.caib.bantel.model.ReferenciaTramiteBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.bantel.persistence.plugins.PluginBackOffice;
import es.caib.bantel.persistence.plugins.UsernamePasswordCallbackHandler;
import es.caib.bantel.persistence.util.CacheProcesamiento;
import es.caib.bantel.persistence.util.BteStringUtil;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.xml.ConstantesXML;

/**
 * 
 * EJB encargado de realizar los avisos inmediatos de las nuevas entradas de forma asíncrona
 * 
 * Esta forma de aviso intentará avisar al BackOffice una vez. En caso de error o caída de la cola el aviso se 
 * realizará mediante el proceso de background de avisos. 
 * 
 * 
 * Las entradas a avisar deben ser del mismo trámite y procedimiento.
 * 
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
		TramiteBandeja entradaBandeja=null;					
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
			
			String numeroEntradas [] =  BteStringUtil.stringToNumeroEntradas(sNumeroEntradas);		
			
			// Comprobamos que las entradas existen (puede ser que no haya terminado transaccion para avisos inmediatos)
			// y que son del mismo procedimiento
			TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
			String idTramite="";
			String idProcedimiento="";
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
						entradaBandeja = delegate.obtenerTramiteBandeja(numeroEntradas[i]);
						if (entradaBandeja == null){
							log.debug("Entrada " + numeroEntradas[i] + " no existe en BBDD");						
							return;				
						}
						
						// Indicamos que la entrada se avisara en este aviso
						ReferenciaTramiteBandeja ref = new ReferenciaTramiteBandeja();
						ref.setNumeroEntrada(entradaBandeja.getNumeroEntrada());
						ref.setClaveAcceso(entradaBandeja.getClaveAcceso());
						referenciasParaAvisar.add(ref);
						
						
						// Comprobamos que el procedimiento y trámite sea el mismo para todas las entradas
						if (i==0) {
							idProcedimiento = entradaBandeja.getProcedimiento().getIdentificador();
							idTramite = entradaBandeja.getIdentificadorTramite();
						}else{
							if (!idProcedimiento.equals(entradaBandeja.getProcedimiento().getIdentificador())){
								throw new Exception("No se pueden realizar un aviso con entradas de distinto procedimiento");
							}
							if (!idTramite.equals(entradaBandeja.getIdentificadorTramite())){
								throw new Exception("No se pueden realizar un aviso con entradas de distinto trámite");
							}
						}
						
						// Comprobamos que no este procesada
						if (entradaBandeja.getProcesada() == 'N'){
							count++; // incrementamos num de entradas en este aviso							
						}else{
							// Si ha sido procesada no entrara dentro del proceso
							CacheProcesamiento.borrar(numeroEntradas[i]);
							entradasParaAvisar.remove(numeroEntradas[i]);
							referenciasParaAvisar.remove(ref);
						}
						
						//	Si el RDS esta integrado en un Gestor Documental para procesarse la entrada
						//  todos sus documentos deben estar consolidados
						try{
							// Asiento
							rds.consolidarDocumento(new ReferenciaRDS(entradaBandeja.getCodigoRdsAsiento().longValue(),entradaBandeja.getClaveRdsAsiento()) );
							// Justificante
							rds.consolidarDocumento(new ReferenciaRDS(entradaBandeja.getCodigoRdsJustificante().longValue(),entradaBandeja.getClaveRdsJustificante()) );
							// Datos propios + docs
							for (Iterator it=entradaBandeja.getDocumentos().iterator();it.hasNext();){
								DocumentoBandeja db = (DocumentoBandeja) it.next();
								if (db.getRdsCodigo() != null){
									rds.consolidarDocumento(new ReferenciaRDS(db.getRdsCodigo().longValue(),db.getRdsClave()) ); 
								}
							}
						}catch(Exception ex){
							// Error al consolidar documentos de la entrada, no entrara dentro del proceso
							log.debug("Aviso entradas: No se han podido consolidar los documentos para la entrada: " + entradaBandeja.getNumeroEntrada() + ". Se intentara en el proximo reintento.");
							CacheProcesamiento.borrar(numeroEntradas[i]);
							entradasParaAvisar.remove(numeroEntradas[i]);
							referenciasParaAvisar.remove(ref);
						}
						
					}		
					
					// Avisamos a BackOffice de las nuevas entradas
					log.debug("Aviso entradas: Entradas a avisar: " +  ToStringBuilder.reflectionToString(entradasParaAvisar));					
					if (entradasParaAvisar != null && entradasParaAvisar.size()>0){			
						PluginBackOffice bo = new PluginBackOffice(entradaBandeja.getProcedimiento(), entradaBandeja.getIdentificadorTramite());									
						bo.avisarEntradas(referenciasParaAvisar,userAuto,passAuto);				
					}
			}
			
			
			
			log.debug("Aviso entradas realizado");	
			
		}catch (Exception ex){
			// Informamos al log			
			log.debug("No se puede enviar aviso de nuevas entradas al BackOffice: " + ex.getMessage(),ex);
			
			// Actualizamos error último aviso
			if(entradaBandeja != null){
				actualizarErrorUltimoAviso(entradaBandeja, ex);
			}
			
		}finally{
			
			// Nos aseguramos de que todas las entradas incluidas en el aviso se borren de cache
			CacheProcesamiento.borrar(entradasParaAvisar);
			
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}

	private void actualizarErrorUltimoAviso(TramiteBandeja tramite, Exception ex) {
		try {
			ProcedimientoDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
			Procedimiento tra = tramite.getProcedimiento();
			String error = ConstantesBTE.MARCA_ERROR +
							es.caib.util.StringUtil.fechaACadena(new Date(),es.caib.util.StringUtil.FORMATO_TIMESTAMP) + ": " + 
							es.caib.util.StringUtil.stackTraceToString(ex);
			
			/* MODIFICACION: SOLO MOSTRAMOS ULTIMO ERROR DE CONEXION, ANTES 2 ULTIMOS
			String errorAntiguo = reconstruirError(tra.getErrores());
			error = error + errorAntiguo;
			*/
			
			tramiteDelegate.errorConexion(tra.getIdentificador(),error.getBytes(ConstantesXML.ENCODING));
		} catch (Exception e) {
			log.debug("error al guardar la excepción en el tramite.",e);
		}		
	}	
	
	private static String  reconstruirError(byte[] erroresByteArray) throws Exception{
		String errores = "";
		if(erroresByteArray != null){
			try {
				errores = new String( erroresByteArray, ConstantesXML.ENCODING );
				if(errores.lastIndexOf(ConstantesBTE.MARCA_ERROR) != -1 && errores.lastIndexOf(ConstantesBTE.MARCA_ERROR) != 0){
					//quiere decir que hay mas de un error en el String, tendremos que quitar el ultimo 
					//que es el mas antiguo.
					errores = errores.substring(0,errores.lastIndexOf(ConstantesBTE.MARCA_ERROR));
				}
			} catch (Exception e) {
				throw e;
			} 
		}
		return errores;
	}
}
