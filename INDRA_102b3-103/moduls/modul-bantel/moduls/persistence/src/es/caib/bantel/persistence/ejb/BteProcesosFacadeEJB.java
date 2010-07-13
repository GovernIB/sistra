package es.caib.bantel.persistence.ejb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Tramite;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.modelInterfaz.ExcepcionBTE;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.bantel.persistence.delegate.TramiteDelegate;
import es.caib.bantel.persistence.plugins.UsernamePasswordCallbackHandler;
import es.caib.bantel.persistence.util.StringUtil;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail;
import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;

//TODO: Referenciar localmente a los ejbs
/**
 * SessionBean que implementa la interfaz de la BTE para la gestión de proceso de avisos.
 * Estos procesos se ejecutaran con el usuario auto
 *  
 *
 * @ejb.bean
 *  name="bantel/persistence/BteProcesosFacade"
 *  jndi-name="es.caib.bantel.persistence.BteProcesosFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="colaAvisos" value="queue/AvisadorBTE" 
 * 
 */
public abstract class BteProcesosFacadeEJB implements SessionBean  {

	private static Log log = LogFactory.getLog(BteProcesosFacadeEJB.class);
	private long intervaloSeguridad=0;
		
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {	
		try{			 
			intervaloSeguridad =  Long.parseLong(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisoPeriodico.intervaloSeguridad"));
		}catch(Exception ex){
			log.error("Excepcion obteniendo parametro de intervalo de seguridad. Se tomara valor="+intervaloSeguridad,ex );
		}
	}
	
    /**
     * Realiza proceso de aviso de nuevas entradas a BackOffices
     * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void avisoBackOffices()  throws ExcepcionBTE{
    	
    	LoginContext lc = null;
    	
    	try{
    		// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();	
    		
	    	// Recuperamos lista de tramites
	    	TramiteDelegate td = DelegateUtil.getTramiteDelegate();
	    	List list = td.listarTramites();
	    		    	
	    	// Para los tramites que tengan configurado el proceso de aviso consultamos nuevas entradas (aplicando intervalo de seguridad para evitar
	    	// solapamiento entre aviso inmediato y periodico)
	    	Date ahora = new Date();
	    	Date hasta = new Date( ahora.getTime() - (intervaloSeguridad * 60 * 1000) );
	    	
	    	
	    	for (Iterator it = list.iterator();it.hasNext();){
	    		Tramite t =  (Tramite) it.next();
	    		
	    		// Si no tiene un intervalo positivo no esta habilitado el proceso de aviso para el trámite 
	    		if (t.getIntervaloInforme() == null || t.getIntervaloInforme().intValue() <= 0) continue;
	    		
	    		// Comprobamos si se ha cumplido el intervalo
	    		if (t.getUltimoAviso() != null &&
	    			( (t.getUltimoAviso().getTime() + (t.getIntervaloInforme().longValue() * 60 * 1000)) > ahora.getTime() ) ) continue;
	    		
	    		// Si se ha cumplido el intervalo avisamos al backoffice de las nuevas entradas
	    		avisoBackOffice(t,hasta);
	    		
	    		// Guardamos tiempo en que se ha realizado el aviso	    		
	    		td.avisoRealizado(t.getIdentificador(),ahora);	   
	    	}	    		    	
    	}catch(Exception ex){
    		log.error("Excepción en proceso de aviso a BackOffices",ex);
    		throw new ExcepcionBTE("Excepción en proceso de aviso a BackOffices",ex);
    	}finally{
    		// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
    	}
	    	
    }
    
    /**
     * Realiza proceso de aviso de nuevas entradas a Gestores
     * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void avisoGestores()  throws ExcepcionBTE{
    	LoginContext lc = null;
    	
    	try {
    		// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();	
    		
    		// Recuperamos lista de gestores
    		GestorBandejaDelegate gb = DelegateUtil.getGestorBandejaDelegate();
    		TramiteBandejaDelegate tbd = (TramiteBandejaDelegate) DelegateUtil.getTramiteBandejaDelegate();
    		List list = gb.listarGestoresBandeja();
    		String numEntradas,intervalo,titulo;
    		StringBuffer mensajeIntervalo,mensaje;
    		Hashtable entradasTramite = new Hashtable();
    		boolean existenEntradasTramite,existenEntradas;
    		long num;
    		
    		// Para los gestores que tengan configurado el proceso de aviso consultamos nuevas entradas
    		// Establecemos un intervalo de seguridad (10 min) para evitar que entradas recientes nos alerten de que
    		// aun no estan procesadas
    		int ventanaTiempo = 10;
	    	Date ahora = new Date();
	    	Date desde;
	    	Date hasta = new Date( ahora.getTime() - (ventanaTiempo * 60 * 1000) );
	    	
	    	
	    	// Montamos emails a enviar 	    	
	    	MensajeEnvio enviosEmail = new MensajeEnvio();
	    	
	    	// Recorremos los gestores y generamos mensaje personalizado
	    	for (Iterator it = list.iterator();it.hasNext();){
	    		
	    		// Obtenemos siguiente gestor
	    		GestorBandeja g =  (GestorBandeja) it.next();
	    		
	    		// Creamos string buffer para el mensaje
	    		mensaje = new StringBuffer(8192); 	
	    			    		
	    		// Si no tiene un intervalo positivo no esta habilitado el proceso de aviso 
	    		if (g.getIntervaloInforme() == null || g.getIntervaloInforme().intValue() <= 0) continue;
	    		
	    		// Comprobamos si se ha cumplido el intervalo
	    		if (g.getUltimoAviso() != null &&
	    			( (g.getUltimoAviso().getTime() + (g.getIntervaloInforme().longValue() * 60  * 60  * 1000)) > ahora.getTime() ) ) continue;	    	
	    		
	    		// Calculamos texto para intervalo
	    		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss");
	    		intervalo ="";	    		
	    		if (g.getUltimoAviso() != null) {
	    			desde = new Date( g.getUltimoAviso().getTime() - (ventanaTiempo * 60 * 1000) );
	    			intervalo += "des de " + sdf.format( desde ) + " ";
	    		}else{
	    			desde = null;
	    		}
	    		intervalo += "fins a " + sdf.format( hasta );	 
	    		
	    		// Establecemos titulo e inicializamos mensaje
	    		titulo = "Safata Telemàtica  - " + intervalo;
	    		mensaje.append("<html><head><style type=\"text/css\">body	{font-family: verdana, arial, helvetica, sans-serif; font-size: 8pt; color: #515b67;}</style></head><body>");
	    		mensaje.append("Informe d'entrades en la Safata Telem&agrave;tica  (" + intervalo + ") <br/><br/>");
	    		
	    		// Marcamos como que no hay entradas xa avisar
	    		existenEntradas=false;
	    		
	    		// Obtenemos entradas nuevas para los tramites asociados al gestor
	    		for (Iterator it2 = g.getTramitesGestionados().iterator();it2.hasNext();){	    				    			
	    			
	    			// Obtenemos siguiente tramite gestionado por el gestor
	    			Tramite t = (Tramite) it2.next();	   	    			
	    			
	    			// Creamos mensaje para el intervalo / tramite en cuestion
	    			mensajeIntervalo = new StringBuffer(1024);
	    			
	    			// Indicamos que de momento no se ha producido ninguna entrada
	    			existenEntradasTramite=false;
	    			
	    			// TODO Para nueva version sustituir quitarAcentos por escapeHtml de commonslang
	    			String desc = t.getDescripcion(); 
	    			try{
	    				desc = es.caib.util.StringUtil.quitaAcentos(t.getDescripcion());
	    			}catch(Throwable tw){}	    				    		
	    			
	    			mensajeIntervalo.append("<strong> * " + t.getIdentificador() + " - " + desc + "</strong> <br/>");
	    			mensajeIntervalo.append(" 	Noves entrades produ&iuml;des en l'interval <br/>");	    			
	    			
	    			// Buscar entradas procesadas ok / ko / no proc en el intervalo	    			
	    			
	    			// - procesadas OK
	    			numEntradas = (String) entradasTramite.get(t.getIdentificador() + " " + intervalo + " " + ConstantesBTE.ENTRADA_PROCESADA);
	    			if (numEntradas==null){	    			
	    				num = tbd.obtenerTotalEntradas(t.getIdentificador(),ConstantesBTE.ENTRADA_PROCESADA,desde,hasta);	    				
	    				entradasTramite.put(t.getIdentificador() + " " + intervalo + " " + ConstantesBTE.ENTRADA_PROCESADA,Long.toString(num));	
	    			}else{
	    				num = Long.parseLong(numEntradas);
	    			}	
	    			if (num > 0) existenEntradasTramite=true;
	    			mensajeIntervalo.append("	 	- Processades correctament:" + num + " <br/>");
	    			
	    			// - procesadas KO
	    			numEntradas = (String) entradasTramite.get(t.getIdentificador() + " " + intervalo + " " + ConstantesBTE.ENTRADA_PROCESADA_ERROR);
	    			if (numEntradas==null){	    			
	    				num = tbd.obtenerTotalEntradas(t.getIdentificador(),ConstantesBTE.ENTRADA_PROCESADA_ERROR,desde,hasta);	    				
	    				entradasTramite.put(t.getIdentificador() + " " + intervalo + " " + ConstantesBTE.ENTRADA_PROCESADA_ERROR,Long.toString(num));	
	    			}else{
	    				num = Long.parseLong(numEntradas);
	    			}
	    			if (num > 0) existenEntradasTramite=true;
	    			mensajeIntervalo.append("	 	- Processades amb error:" + num + " <br/>");
	    			
	    			// - no procesadas
	    			numEntradas = (String) entradasTramite.get(t.getIdentificador() + " " + intervalo + " " + ConstantesBTE.ENTRADA_NO_PROCESADA);
	    			if (numEntradas==null){	    			
	    				num = tbd.obtenerTotalEntradas(t.getIdentificador(),ConstantesBTE.ENTRADA_NO_PROCESADA,desde,hasta);	    				
	    				entradasTramite.put(t.getIdentificador() + " " + intervalo + " " + ConstantesBTE.ENTRADA_NO_PROCESADA,Long.toString(num));	
	    			}else{
	    				num = Long.parseLong(numEntradas);
	    			}
	    			if (num > 0) existenEntradasTramite=true;
	    			mensajeIntervalo.append("	 	- No processades:" + num + " <br/>");
	    			mensajeIntervalo.append(" <br/>");
	    			
	    			
	    			// Avisos especiales (sólo para tramites con procesos automaticos)
	    			if (t.getIntervaloInforme() != null && t.getIntervaloInforme().longValue() > 0){
		    			// 	- Buscar entradas ko (sin intervalo)
		    			num = tbd.obtenerTotalEntradas(t.getIdentificador(),ConstantesBTE.ENTRADA_PROCESADA_ERROR,null,hasta);
		    			if (num > 0) {
		    				existenEntradasTramite=true;	    			
		    				mensajeIntervalo.append(" 	ATENCI&Oacute;: EXISTEIXEN ENTRADES PROCESSADES AMB ERROR QUE HAN DE SER REVISADES (TOTAL:" + num + ") <br/>") ;
		    			}
		    			
		    			//   - Buscar entradas sin procesar anteriores al intervalo
		    			num = tbd.obtenerTotalEntradas(t.getIdentificador(),ConstantesBTE.ENTRADA_NO_PROCESADA,null,desde);
		    			if (num > 0) {
		    				existenEntradasTramite=true;	    			
		    				mensajeIntervalo.append(" 	ATENCI&Oacute;: SEGUEIXEN HAVENT ENTRADES SENSE PROCESSAR ANTERIORS A L'INTERVAL ACTUAL (TOTAL:" + num + ") <br/>" );
		    			}
	    			}
	    			
	    			// Si hay algun movimiento anexamos a mensaje
	    			if (existenEntradasTramite){
	    				existenEntradas=true;
	    				mensaje.append(mensajeIntervalo.toString() + "<br/>");	    	    				
	    			}
	    			
	    		}
	    		
	    		// Enviamos correo a gestor con nuevas entradas
	    		if (existenEntradas) {
	    			
	    			mensaje.append("</body></html>");
	    			
	    			MensajeEnvioEmail me = new MensajeEnvioEmail();
	    			String [] dest = {g.getEmail()};
	    			me.setHtml(true);
	    			me.setDestinatarios(dest);
	    			me.setTitulo(titulo);
	    			me.setTexto(mensaje.toString());
	    			enviosEmail.addEmail(me);	    		    				    			
	    		}
	    		
	    		// Guardamos tiempo en que se ha realizado el aviso
	    		g.setUltimoAviso(ahora);
		    	gb.avisoRealizado(g.getSeyconID(),ahora);
		    	
	    	}	    		
	    	
	    	// Enviamos al modulo de movilidad los emails
	    	if (enviosEmail.getEmails() != null && enviosEmail.getEmails().size() > 0){
	    	enviosEmail.setNombre("Avisos a gestores");
	    	enviosEmail.setCuentaEmisora(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisosGestores.cuentaEnvio"));
	    	enviosEmail.setFechaCaducidad(new Date(System.currentTimeMillis() + 86400000L )); // Damos 1 día para intentar enviar
	    	enviosEmail.setInmediato(true);
	    	DelegateMobTraTelUtil.getMobTraTelDelegate().envioMensaje(enviosEmail);
	    	}
	    	
    	}catch (Exception ex){
    		log.error("Excepción enviando correo a gestor",ex);
    		throw new ExcepcionBTE("Excepción en proceso de aviso a BackOffices",ex);
    	}finally{
    		// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
    	}
    	
    }
    
    /**
     * Realiza proceso de aviso a BackOffice para un trámite. Metemos en cola asíncrona.
     * En caso de error lanza mensaje al log y permite continuar.
     * @param tramite
     */
    private void avisoBackOffice(Tramite tramite,Date hasta){    	
    	try{    		
    		// Obtenemos entradas no procesadas    		    		    	
    		TramiteBandejaDelegate tbd = (TramiteBandejaDelegate) DelegateUtil.getTramiteBandejaDelegate();
    		String  entradas [] = tbd.obtenerNumerosEntradas(tramite.getIdentificador(),ConstantesBTE.ENTRADA_NO_PROCESADA,null,hasta);    		
    		log.debug("Aviso de " + entradas.length + " nuevas entradas para backoffice trámite " + tramite.getIdentificador() + " hasta " + es.caib.util.StringUtil.fechaACadena(hasta,es.caib.util.StringUtil.FORMATO_TIMESTAMP));
    		
    		if (entradas.length > 0){
				// Dejamos entrada en la cola de avisos
		    	InitialContext ctx = new InitialContext();
		    	String colaAvisos = (String) ctx.lookup("java:comp/env/colaAvisos");
			    Queue queue = (Queue) ctx.lookup(colaAvisos);		 
			    QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("java:/XAConnectionFactory");
			    QueueConnection cnn = factory.createQueueConnection();
			    QueueSession sess = cnn.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);    		  
				TextMessage msg = sess.createTextMessage(StringUtil.numeroEntradasToString(entradas));
				QueueSender sender = sess.createSender(queue);
				sender.send(msg,DeliveryMode.NON_PERSISTENT,4,0);				
    		}
			
    	}catch(Exception ex){
    		log.error("Excepción en proceso de aviso a BackOffice para tramite " + tramite.getIdentificador(),ex);    		
    	}    		
    }
    
}
