package es.caib.mobtratel.persistence.plugins;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.mail.SendFailedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Constantes;
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.MensajeEmail;
import es.caib.mobtratel.model.MensajeSms;
import es.caib.mobtratel.model.ProcesoEnvioInfo;
import es.caib.mobtratel.modelInterfaz.ConstantesMobtratel;
import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;
import es.caib.mobtratel.persistence.util.CacheProcesamiento;
import es.caib.mobtratel.persistence.util.EmailUtils;
import es.caib.mobtratel.persistence.util.MobUtils;
import es.caib.mobtratel.persistence.util.SmsUtils;
import es.caib.sistra.plugins.email.ConstantesEmail;
import es.caib.sistra.plugins.email.EstadoEnvio;
import es.caib.sistra.plugins.sms.ConstantesSMS;
import es.caib.xml.ConstantesXML;

/**
 
  	Plugin que realiza el proceso de envios
 	
 	No comprobamos permisos, ya que al darse de alta el envio ya se comprobaron.
    Si se comprobaran ahora y se hubiesen revocado los envios no podrian realizarse.

 */
public class PluginEnvio {
	
	private static Log log = LogFactory.getLog(PluginEnvio.class);
	private static Boolean simularEnvioEmail;
	private static Boolean simularEnvioSms;
	private static int simularEnvioDuracion;
	private static int limiteDiasVerificar;
	private static String prefijoEnvioEmail;
	
	
	public static int getSimularEnvioDuracion() {
		return simularEnvioDuracion;
	}

	public static void setSimularEnvioDuracion(int simularEnvioDuracion) {
		PluginEnvio.simularEnvioDuracion = simularEnvioDuracion;
	}

	public static int getLimiteDiasVerificar() {
		return limiteDiasVerificar;
	}

	public static void setLimiteDiasVerificar(int limiteDiasVerificar) {
		PluginEnvio.limiteDiasVerificar = limiteDiasVerificar;
	}			
    	
	/**
	 * Realiza el proceso de envio para la lista de envios pendientes
	 * @param envios
	 * @throws DelegateException
	 */
	public static void enviar(Long envios[]) throws DelegateException{
    	
		log.debug("Inicio proceso de envio: " + envios.length + " envios pendientes");         	
    	try {
			
    		ProcesoEnvioInfo infoProceso = new ProcesoEnvioInfo();
			infoProceso.setSmsEnviados(0);
			infoProceso.setFechaInicio(new Date());
			
	        for (int i=0;i<envios.length;i++){	        	
	        	enviar(envios[i],infoProceso);	        	
	        }	        
    	}finally{
    		log.debug("Fin proceso de envio");     
    	}
    }
	
	/**
	 * Realiza el proceso de verificar los envios email.
	 * 
	 * @param envios
	 * @throws DelegateException
	 */
	public static void verificarEmails(Long envios[]) throws DelegateException{
    	
		log.debug("Inicio proceso de verificar envio email: " + envios.length + " envios pendientes de verificar");  
		try {
			for (int i=0;i<envios.length;i++){	        	
	        	verificarEnvioEmail(envios[i]);	        	
	        }	        
    	}finally{
    		log.debug("Fin proceso de envio");     
    	}
    }
	
	
	/**
	 * Realiza el proceso de verificar los envios sms.
	 * 
	 * @param envios
	 * @throws DelegateException
	 */
	public static void verificarSmss(Long envios[]) throws DelegateException{
    	log.debug("Inicio proceso de verificar envio sms: " + envios.length + " envios pendientes de verificar");  
		try {
			for (int i=0;i<envios.length;i++){	        	
	        	verificarEnvioSms(envios[i]);	        	
	        }	        
    	}finally{
    		log.debug("Fin proceso de envio");     
    	}
    }
	
	/**
	 * Verifica si se ha realizado el envio email.
	 * @param idEnvio idEnvio
	 */
	private static void verificarEnvioEmail(Long idEnvio) {
		EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();
		try  {
			// Recuperamos envio
	    	 MensajeEmail envio = delegate.obtenerMensajeEmail(idEnvio);
	    	 
	    	 // Comprobamos si ha caducado la comprobacion
	    	 if (calcularFechaCaducidadVerificarEnvio(envio.getFechaFinEnvio()).before(new Date())) {
	    		 delegate.establecerEstadoVerificarMensajeEmail(idEnvio, ConstantesSMS.ESTADO_DESCONOCIDO, "Cancelada la comprobación de envío por límite de tiempo");
	    		 return;
	    	 }
	    	 
	    	 // Verificamos estado envio
	    	 EstadoEnvio estado = null;
	    	 if (simularEnvioEmail.booleanValue()){
		    	 // Fronton: lo damos por enviado
	    		 estado = new EstadoEnvio();
	    		 estado.setEstado(ConstantesEmail.ESTADO_ENVIADO);
	    	 } else {
	    		 // Invocamos al plugin para verificar el envio
		    	 estado = EmailUtils.getInstance().verificarEnvioMensaje(envio);
	    	 }
	    	 
	    	 // Actualizamos estado
	    	 if (estado != null && estado.getEstado() != ConstantesEmail.ESTADO_PENDIENTE && estado.getEstado() != ConstantesEmail.ESTADO_DESCONOCIDO) {
	    		 delegate.establecerEstadoVerificarMensajeEmail(idEnvio, estado.getEstado(), estado.getDescripcionEstado());	
	    	 }	    	 
	    	 	    	
		} catch (Exception ex){			
			// Capturamos excepcion para continuar con proceso de envios. Se intentara en siguiente proceso.
			log.error("Envio email " + idEnvio + ": excepcion al verificar envio",ex);
			return;			
		}
	}
	
	/**
	 * Verifica si se ha realizado el envio sms.
	 * @param idEnvio idEnvio
	 */
	private static void verificarEnvioSms(Long idEnvio) {
		EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();
		try  {
			 // Recuperamos envio
	    	 MensajeSms envio = delegate.obtenerMensajeSms(idEnvio);
	    	 
	    	 // Comprobamos si ha caducado la comprobacion
	    	 if (calcularFechaCaducidadVerificarEnvio(envio.getFechaFinEnvio()).before(new Date())) {
	    		 delegate.establecerEstadoVerificarMensajeEmail(idEnvio, ConstantesSMS.ESTADO_DESCONOCIDO, "Cancelada la comprobación de envío por límite de tiempo");
	    		 return;
	    	 }
	    	 	    	 
	    	 // Verificamos estado envio
	    	 es.caib.sistra.plugins.sms.EstadoEnvio estado = null;
	    	 if (simularEnvioSms.booleanValue()){
		    	 // Fronton: lo damos por enviado
	    		 estado = new es.caib.sistra.plugins.sms.EstadoEnvio();
	    		 estado.setEstado(ConstantesSMS.ESTADO_ENVIADO);
	    	 } else {
	    		 // Invocamos al plugin para verificar el envio
	    		 estado = SmsUtils.getInstance().verificarEnvioMensaje(envio);
	    	 }
	    	 
	    	 // Actualizamos estado
	    	 if (estado != null && estado.getEstado() != ConstantesSMS.ESTADO_PENDIENTE && estado.getEstado() != ConstantesSMS.ESTADO_DESCONOCIDO) {
	    		 delegate.establecerEstadoVerificarMensajeSms(idEnvio, estado.getEstado(), estado.getDescripcionEstado());	
	    	 }	    	 
	    	 	    	
		}catch (Exception ex){			
			// Capturamos excepcion para continuar con proceso de envios. Se intentara en siguiente proceso.
			log.error("Envio sms " + idEnvio + ": excepcion al verificar envio",ex);
			return;			
		}
	}

	/**
	 * Realiza el proceso de envio para un Envio
	 * 
	 * @param envio
	 * @param infoProceso
	 */
	private static void enviar(Long idEnvio, ProcesoEnvioInfo infoProceso){

		boolean locked = false;
		EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();
		
		try{
			
			log.debug("Inicio envio " + idEnvio);
			
			// Bloqueamos envio para procesarlo
			locked = CacheProcesamiento.guardar(idEnvio.toString());
			if (!locked){
				log.debug("Envio " + idEnvio + " bloqueado por otro proceso de envio");
				return;
			}
			
			// Envio bloqueado comenzamos a procesar
        	log.debug("Envio " + idEnvio + " bloqueado. Comenzamos a procesarlo");
        	
        	// Recuperamos envio
        	Envio envio = delegate.obtenerEnvio(idEnvio);
        	
        	// Si ha caducado lo marcamos como cancelado automaticamente
        	if (envio.getFechaCaducidad() != null && envio.getFechaCaducidad().getTime() <= System.currentTimeMillis()){
        		envio.setEstado(ConstantesMobtratel.ESTADOENVIO_CANCELADO);
        		delegate.grabarEnvio(envio);
        		return;
        	}
        	
        	// Si estaba pendiente de envio indicamos que envio comienza a procesarse			
        	if (envio.getEstado() == ConstantesMobtratel.ESTADOENVIO_PENDIENTE){
        		envio.setEstado(ConstantesMobtratel.ESTADOENVIO_PROCESANDO);
        		delegate.grabarEnvio(envio);
        	}        	        	
        	
        	// Enviamos emails
			envioEmails(envio,infoProceso);
			
			// Enviamos sms
			envioSms(envio,infoProceso);
						
			// Actualizamos estado envio
			envio = delegate.obtenerEnvio(envio.getCodigo());	
			// Si se ha completado todo el envio indicamos que se ha enviado				
			if (envio.isCompletado()){			
				envio.setEstado(ConstantesMobtratel.ESTADOENVIO_ENVIADO);
				envio.setFechaEnvio(new Timestamp(System.currentTimeMillis()));
				delegate.grabarEnvio(envio);
			}
			// Si se ha producido un error indicamos que el envio tiene errores
			if (envio.isConError()){			
				envio.setEstado(ConstantesMobtratel.ESTADOENVIO_ERROR);
				delegate.grabarEnvio(envio);
			}
			
		}catch (Exception ex){			
			// Capturamos excepcion para continuar con proceso de envios. Se intentara en siguiente proceso.
			log.error("Envio " + idEnvio + ": excepcion al enviar",ex);
			return;			
		}finally{
			
			log.debug("Fin envio " + idEnvio + ". Estado envio:" + idEnvio );
			
			// Si hemos bloqueado el envio lo desbloqueamos
			if (locked) {
				CacheProcesamiento.borrar(idEnvio.toString());
			}			
		}
		
	}
	
		
	
	/**
	 * Realiza los sms pendientes del envio 
	 * @param envio
	 * @param infoProceso 
	 */
    private static void envioSms(Envio envio, ProcesoEnvioInfo infoProceso) throws DelegateException{
    	
    	log.debug("Envio " + envio.getCodigo() + ": inicio envio SMS ");
    	
    	try{
	    	EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();    	
	    	for(Iterator ite = envio.getSmss().iterator(); ite.hasNext();)
			{
				MensajeSms ms = (MensajeSms)ite.next();
				
				// Mensaje ya enviado
				if(ms.getEstado() == ConstantesMobtratel.ESTADOENVIO_ENVIADO) continue;
				
				// Mensaje pendiente -> pasamos a procesarlo
				if(ms.getEstado() == ConstantesMobtratel.ESTADOENVIO_PENDIENTE)
				{
					ms.setEstado(ConstantesMobtratel.ESTADOENVIO_PROCESANDO);
					ms.setError("");
					ms.setFechaInicioEnvio(new Timestamp(System.currentTimeMillis()));
					delegate.grabarEnvio(envio);
				}
				
				// Si ya se han enviado a todos los destinatarios. Proteccion
				if(ms.getNumeroDestinatarios() == ms.getNumeroDestinatariosEnviados())
				{
					ms.setEstado(ConstantesMobtratel.ESTADOENVIO_ENVIADO);						
					delegate.grabarEnvio(envio);
					continue;
				}
				
				// Realiza el envio de SMS del MensajeSMS
				try{
					enviaSMS(ms,envio,infoProceso);
					delegate.grabarEnvio(envio);
				}
				catch(Exception e)
				{
					// En caso de error al enviar marcamos mensajeSMS con el error
					ms.setError(ms.getError() + "." + e.getMessage());
					ms.setEstado(ConstantesMobtratel.ESTADOENVIO_ERROR);
					delegate.grabarEnvio(envio);										
				}					
				
			}    	
    	}finally{
    		log.debug("Envio " + envio.getCodigo() + ": fin envio SMS ");
    	}
	}

    /**
     * Realiza los emails pendientes del envio
     * @param envio
     */
	private static void envioEmails(Envio envio, ProcesoEnvioInfo infoProceso) throws DelegateException {
		
		log.debug("Envio " + envio.getCodigo() + ": inicio envio Emails ");
		try{
			EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();   
			
			for(Iterator ite = envio.getEmails().iterator(); ite.hasNext();)
			{
				MensajeEmail me = (MensajeEmail)ite.next();
				
				// Comprobamos si el mensajeEmail ya esta enviado
				if(me.getEstado() == ConstantesMobtratel.ESTADOENVIO_ENVIADO) continue;
				
				// Si el mensajeEmail esta pendiente de envio pasa a procesandose
				if(me.getEstado() == ConstantesMobtratel.ESTADOENVIO_PENDIENTE)
				{
					me.setEstado(ConstantesMobtratel.ESTADOENVIO_PROCESANDO);
					me.setError("");
					me.setFechaInicioEnvio(new Timestamp(System.currentTimeMillis()));					
					delegate.grabarEnvio(envio);
				}
				
				// Si ya se han enviado a todos los destinatarios. Proteccion
				if(me.getNumeroDestinatarios() == me.getNumeroDestinatariosEnviados())
				{
					me.setEstado(ConstantesMobtratel.ESTADOENVIO_ENVIADO);						
					me.setFechaFinEnvio(new Timestamp(System.currentTimeMillis()));
					delegate.grabarEnvio(envio);
					continue;
				}
				
				// Envio del mensajeEmail
				try{
					enviaEmail(me,envio,infoProceso);
					delegate.grabarEnvio(envio);
				}
				catch(Exception e)
				{
					me.setError(e.getMessage());
					me.setEstado(ConstantesMobtratel.ESTADOENVIO_ERROR);
					delegate.grabarEnvio(envio);
				}					
				
			}
		}finally{
			log.debug("Envio " + envio.getCodigo() + ": fin envio Emails ");
		}
	}

	/**
	 * Envia los emails de un elemento MensajeEmail.
	 * En caso de ser necerario pagina los mensajes en distintos emails segun la limitacion de destinatarios por email
	 * 
	 * @param me
	 * @param envio
	 * @param infoProceso
	 * @throws Exception
	 */
	private static void enviaEmail(MensajeEmail me, Envio envio, ProcesoEnvioInfo infoProceso) throws Exception
    {
		log.debug("Envia MensajeEmail: " + me.getCodigo());
		
		List enviados = new ArrayList();
    	List paraEnviar = new ArrayList();
    	List emailsParaEnviar = new ArrayList();
    	MobUtils utils = MobUtils.getInstance();
    	
		
    	// Si no se ha especificado cuenta, obtenemos cuenta por defecto
		Cuenta cuenta = envio.getCuenta();
    	if(cuenta == null)
    	{
    		CuentaDelegate cdelegate = DelegateUtil.getCuentaDelegate();
        	log.debug("Vamos a obtener la cuenta por defecto");
    		cuenta = cdelegate.obtenerCuentaDefectoEmail();
        	log.debug("Cuenta por defecto: " + cuenta.getCodigo());
    		envio.setCuenta(cuenta);
    	}
    	
    	// Realizamos envios
		String[] emails = new String(me.getDestinatarios(),ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS);

		// Obtiene lista de destinatarios y lista de destinatarios ya enviados
    	byte[] bde = me.getDestinatariosEnviados();
    	if(bde != null)
    	{
	    	String[] emailsEnviados = new String(bde,ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS);
	    	enviados = new ArrayList(Arrays.asList(emailsEnviados));
    	}
    	me.setError("");

    	int npaginas = 0;

    	// Realiza el envio paginado de emails    	
    	for(int i=0; i<emails.length; i++)
    	{
    		// Comprueba si ya se ha enviado al destinatario 
    		if(isEnviado(enviados,emails[i])) continue;
    		
    		paraEnviar.add(emails[i]);
    		emailsParaEnviar.add(emails[i]);
    		
    		if(utils.getPagina().intValue() == paraEnviar.size())
    		{
    			npaginas++;
    			log.debug("Enviamos pagina: " + npaginas + " del envio: " + envio.getNombre());
    			boolean result = enviarPaginaEmails(me,envio,paraEnviar,infoProceso.getFechaInicio());
    			if(result){
    				enviados.addAll(emailsParaEnviar);
    				me.setDestinatariosEnviados(MobUtils.compoundDestinatarios(enviados));
    		    	me.setNumeroDestinatariosEnviados(enviados.size());
    			}else{
    				if(me.getDestinatariosValidos() != null && me.getDestinatariosValidos().size() > 0){
    					enviados.addAll(me.getDestinatariosValidos());
    					me.setDestinatariosEnviados(MobUtils.compoundDestinatarios(enviados));
    		    		me.setNumeroDestinatariosEnviados(enviados.size());
    		    		me.setDestinatariosValidos(null);
    				}
    			}
    			paraEnviar.clear();
    			emailsParaEnviar.clear();
    		}
    		
    	}
    	if(paraEnviar.size() != 0)
    	{
			boolean result = enviarPaginaEmails(me,envio,paraEnviar,infoProceso.getFechaInicio());
			if(result){
				enviados.addAll(emailsParaEnviar);
			}else{
				if(me.getDestinatariosValidos() != null && me.getDestinatariosValidos().size() > 0){
					enviados.addAll(me.getDestinatariosValidos());
					me.setDestinatariosValidos(null);
				}
			}
    	}
    	
    	// Actualiza lista de destinatarios enviados
		me.setDestinatariosEnviados(MobUtils.compoundDestinatarios(enviados));
    	me.setNumeroDestinatariosEnviados(enviados.size());
    	if(me.getNumeroDestinatarios() == me.getNumeroDestinatariosEnviados())
    	{
    		me.setEstado(ConstantesMobtratel.ESTADOENVIO_ENVIADO);
    		me.setError("");
    		me.setFechaFinEnvio(new Timestamp(System.currentTimeMillis()));
    	}    	    	

    }
    
	/**
	 * Realiza el envio de un email paginado
	 * @param me
	 * @param envio
	 * @param destinatarios
	 * @param fechaInicio
	 * @return
	 */
    private static boolean enviarPaginaEmails(MensajeEmail me, Envio envio, List destinatarios, Date fechaInicio)
    {
    	MobUtils utils = MobUtils.getInstance();
    	try {
    		EmailUtils emailUtils = EmailUtils.getInstance();
    		Date now = new Date();
    		if((now.getTime()-fechaInicio.getTime()) > utils.getLimiteTiempo().longValue()){
    			log.debug("Cancelamos envio por haberse superado el tiempo Maximo del proceso de envio");
    			return false;
    		}

    		if (simularEnvioEmail.booleanValue()){
				log.debug("Email simulando envio");
				Date inicioSimulacion = new Date();
				while ( (inicioSimulacion.getTime() + (simularEnvioDuracion * 1000)) > System.currentTimeMillis() ){
					// Esperamos tiempo simulacion
				}
				log.debug("Email envio simulado");
			}else{
				emailUtils.enviar(prefijoEnvioEmail, me,envio,destinatarios);
			}
    		
    		
    		return true;
		}catch (SendFailedException ex){
			List invalidAddresses = null;
			List unsentAddresses = null;
			List validAddresses = null;
			if(ex.getInvalidAddresses() != null){
				invalidAddresses = new ArrayList();
				for(int i=0;i<ex.getInvalidAddresses().length;i++){
					invalidAddresses.add(ex.getInvalidAddresses()[i].toString());
				}
			}
			if(ex.getValidUnsentAddresses() != null){
				unsentAddresses = new ArrayList();
				for(int i=0;i<ex.getValidUnsentAddresses().length;i++){
					unsentAddresses.add(ex.getValidUnsentAddresses()[i].toString());
				}
			}
			if(ex.getValidSentAddresses() != null){
				validAddresses = new ArrayList();
				for(int i=0;i<ex.getValidSentAddresses().length;i++){
					validAddresses.add(ex.getValidSentAddresses()[i].toString());
				}
			}
			log.debug("Error generando correo para el envio: " + envio.getNombre(), ex);
			me.setError("Error generando correo para el envio: " + envio.getNombre() + "." + ex.getMessage());
			me.setError(me.getError()+ " ERROR EMAILS: ");
			if(invalidAddresses != null){
				me.setError(me.getError()+ "-no validos-" + invalidAddresses.toString());
			}
			if(unsentAddresses != null){
				me.setError(me.getError()+ "-no enviados-" + unsentAddresses.toString());
			}
			me.setError(me.getError() + ": " + ex.getMessage()+ ".");
			me.setDestinatariosValidos(validAddresses);
			me.setEstado(ConstantesMobtratel.ESTADOENVIO_ERROR);
		} catch (DelegateException de) {
			log.debug("Error accediendo a la cuenta por defecto de EMAIL");
			me.setError("Error accediendo a la cuenta por defecto de EMAIL");
			me.setEstado(ConstantesMobtratel.ESTADOENVIO_ERROR);
		} catch (Exception e) {
			log.debug("Error generando correo para el envio: " + envio.getNombre());
			me.setError("Error generando correo para el envio: " + envio.getNombre() + "." + e.getMessage());
			me.setError(me.getError()+ "ERROR EMAILS: " + destinatarios.toString() + ": " + e.getMessage()+ ".");
			me.setEstado(ConstantesMobtratel.ESTADOENVIO_ERROR);
		}
		return false;
    }

    
    /**
     * Realiza los envios sms de un MensajeSMS
     * 
     * @param ms
     * @param envio
     * @param infoProceso
     * @return
     * @throws Exception
     */
    private static void enviaSMS(MensajeSms ms, Envio envio, ProcesoEnvioInfo infoProceso) throws Exception
    {
    	
    	log.debug("EnviaSMS: " + envio.getCodigo());
    	    	
    	MobUtils utils = MobUtils.getInstance();
    	SmsUtils smsUtils = SmsUtils.getInstance();
    	List enviados = new ArrayList();
    	int numErroresConexion = 0;
    	
    	// Si no se especifica cuenta se obtiene cuenta por defecto
    	Cuenta cuenta = envio.getCuenta();
    	if(cuenta == null)
    	{
    		CuentaDelegate delegate = DelegateUtil.getCuentaDelegate();
        	log.debug("Vamos a obtener la cuenta por defecto");
    		cuenta = delegate.obtenerCuentaDefectoSMS();
        	log.debug("Cuenta por defecto: " + cuenta.getCodigo());
    		envio.setCuenta(cuenta);
    	}
    	
    	
    	// Obtenemos lista destinatarios y la lista de destinatarios a los que ya se ha enviado 
    	String[] telefonos = new String(ms.getDestinatarios(),ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS);
    	byte[] bde = ms.getDestinatariosEnviados();
    	if(bde != null)
    	{
	    	String[] telefonosEnviados = new String(bde,ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS);
	    	enviados = new ArrayList(Arrays.asList(telefonosEnviados));
    	}
    	ms.setError("");
    	

    	// Se envia SMS a los destinatarios que no se ha enviado
    	for(int i=0; i<telefonos.length; i++)
    	{
    		
    		if(isEnviado(enviados,telefonos[i])) continue;
    		
    		// Controlamos num maximo de errores de conexion
    		if((utils.getMaxErroresSMS().intValue() != 0) && (numErroresConexion > utils.getMaxErroresSMS().intValue()))
    		{
    			log.debug("Dejamos de enviar el mensaje SMS por problemas de conexion. Se reintentara mas tarde");
    			break;
    		}
    		
    		// Controlamos si se ha llegado al numero maximo de sms por proceso
    		if((utils.getMaxDestinatariosSms().intValue() != 0) && (infoProceso.getSmsEnviados() >= utils.getMaxDestinatariosSms().intValue())){
    			log.debug("Cancelamos envio por haberse superado el numero Maximo de SMS");
    			break;
    		}
    		
    		// Controlamos si hemos llegado al limite de tiempo por proceso
    		Date now = new Date();
    		if((now.getTime() - infoProceso.getFechaInicio().getTime()) > utils.getLimiteTiempo().longValue()){
    			log.debug("Cancelamos envio por haberse superado el tiempo Maximo del proceso de envio");
    			break;
    		}
    		
    		// Realizamos envio
    		try{
    			// Comprobamos si hay que establecer el fronton
    			if (simularEnvioSms.booleanValue()){
    				log.debug("Sms simulando envio");
    				Date inicioSimulacion = new Date();
    				while ( (inicioSimulacion.getTime() + (simularEnvioDuracion * 1000)) > System.currentTimeMillis() ){
    					// Esperamos tiempo simulacion
    				}
    				log.debug("Sms envio simulado");
    			}else{
    				smsUtils.enviarMensaje(ms,telefonos[i], envio.getCuenta().getSms());
    				// Esperamos tiempo delay entre un sms y el siguiente
    				if (MobUtils.getInstance().getSmsDelay().intValue() > 0){
	    				Date inicioDelay = new Date();
	    				while ( (inicioDelay.getTime() + (MobUtils.getInstance().getSmsDelay().intValue() * 1000)) > System.currentTimeMillis() ){
	    					// Esperamos tiempo delay entre un sms y el siguiente
	    				}
    				}
    			}
    			infoProceso.setSmsEnviados(infoProceso.getSmsEnviados() + 1);
    			enviados.add(telefonos[i]);
    		} catch (Exception e) {
    			numErroresConexion++;
    			log.error("Enviando mensaje al telefono: " + telefonos[i],e);
    			ms.setError(ms.getError()+ "ERROR SMS: " + telefonos[i] + ": " + e.getMessage()+ ".");
    			ms.setEstado(ConstantesMobtratel.ESTADOENVIO_ERROR);
    		}

    	}

    	// Actualizamos lista destinatarios enviados y estado mensajeSMS
    	Set hashSet = new LinkedHashSet(enviados);
		ms.setDestinatariosEnviados(MobUtils.compoundDestinatarios(hashSet));
    	ms.setNumeroDestinatariosEnviados(enviados.size());
    	if(ms.getNumeroDestinatarios() == ms.getNumeroDestinatariosEnviados())
    	{
    		ms.setEstado(ConstantesMobtratel.ESTADOENVIO_ENVIADO);
    		ms.setError("");
    		ms.setFechaFinEnvio(new Timestamp(System.currentTimeMillis()));
    	}    	    	
    	
    	// Lo dejamos procesandose
    	//if(ms.getEstado() == Envio.PROCESANDOSE) ms.setEstado(Envio.PENDIENTE_ENVIO);
    }
        
    /**
     * Comprueba si el destinatario esta en la lista
     * @param enviados
     * @param destinatario
     * @return
     */
	private static boolean isEnviado(List enviados, String destinatario)
	{
		for(Iterator it=enviados.iterator();  it.hasNext();)
		{
			String tlf = (String)it.next();
			if(tlf.equals(destinatario)) return true;
		}
		return false;
	}		
	

	private static Date calcularFechaCaducidadVerificarEnvio(Date fechaEnvio) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaEnvio);
        calendar.add(Calendar.DAY_OF_MONTH, getLimiteDiasVerificar());
		Date fechaCaducidad = calendar.getTime();		
		return fechaCaducidad;
	}

	public static String getPrefijoEnvioEmail() {
		return prefijoEnvioEmail;
	}

	public static void setPrefijoEnvioEmail(String prefijoEnvioEmail) {
		PluginEnvio.prefijoEnvioEmail = prefijoEnvioEmail;
	}

	public static Boolean getSimularEnvioEmail() {
		return simularEnvioEmail;
	}

	public static void setSimularEnvioEmail(Boolean simularEnvioEmail) {
		PluginEnvio.simularEnvioEmail = simularEnvioEmail;
	}

	public static Boolean getSimularEnvioSms() {
		return simularEnvioSms;
	}

	public static void setSimularEnvioSms(Boolean simularEnvioSms) {
		PluginEnvio.simularEnvioSms = simularEnvioSms;
	}
	
}

