package es.caib.bantel.persistence.ejb;

import java.util.ArrayList;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.security.SecurityContext; 
import org.jboss.security.SecurityContextAssociation;

import es.caib.bantel.model.AvisosBandeja;
import es.caib.bantel.model.DetalleEntradasProcedimiento;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TraProcedimiento;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.modelInterfaz.ExcepcionBTE;
import es.caib.bantel.persistence.delegate.AvisosBandejaDelegate;
import es.caib.bantel.persistence.delegate.BteOperacionesProcesosDelegate;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.bantel.persistence.plugins.UsernamePasswordCallbackHandler;
import es.caib.bantel.persistence.util.BteStringUtil;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail;
import es.caib.util.DataUtil;
import es.caib.util.StringUtil;
import es.caib.zonaper.modelInterfaz.DetalleNotificacionesProcedimiento;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

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
 * @ejb.transaction type="NotSupported"
 * 
 * @ejb.env-entry name="colaAvisos" value="queue/AvisadorBTE" 
 * 
 */
public abstract class BteProcesosFacadeEJB implements SessionBean  {

	private static Log log = LogFactory.getLog(BteProcesosFacadeEJB.class);
	private long intervaloSeguridad=0;
	private int maximoDiasAviso=0;
	private long intervaloAviso=30;
		
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
		try{			 
			maximoDiasAviso =  Integer.parseInt(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisoPeriodico.maxDiasAviso"));			
		}catch(Exception ex){
			log.error("Excepcion obteniendo parametro de maximo dias aviso. Se tomara valor="+maximoDiasAviso,ex );
		}
		try{			 
			intervaloAviso =  Long.parseLong(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisoPeriodico.intervaloAviso"));			
		}catch(Exception ex){
			log.error("Excepcion obteniendo parametro de intervalo de aviso. Se tomara valor="+intervaloAviso,ex );
		}
		
	}
	
	/**
     * Realiza proceso de aviso de nuevas entradas a BackOffices
     * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
	public void avisoBackOffices()  throws ExcepcionBTE{
		avisoBackOffices(null);
	}
	
	/**
     * Realiza proceso de aviso de nuevas entradas a BackOffices
     * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void avisoBackOffices(String idProcedimiento)  throws ExcepcionBTE{
    	
    	LoginContext lc = null;
    	
    	try{
    		
    		log.debug("Inicio proceso aviso Backoffices");
    		
    		// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();
			
			List procedimientos =  new ArrayList();
			ProcedimientoDelegate td = DelegateUtil.getTramiteDelegate();
    		
	    	if (idProcedimiento == null){
	    		// Recuperamos lista de procedimientos
		    	procedimientos = td.listarProcedimientos();
	    	} else {
	    		procedimientos.add(td.obtenerProcedimiento(idProcedimiento));
	    	}
			
	    		    	
	    	// Para los tramites que tengan configurado el proceso de aviso consultamos nuevas entradas (aplicando intervalo de seguridad para evitar
	    	// solapamiento entre aviso inmediato y periodico)
	    	Date ahora = new Date();
	    	Date hasta = new Date( ahora.getTime() - (intervaloSeguridad * 60 * 1000) );
	    	
	    	
	    	BteOperacionesProcesosDelegate opd = DelegateUtil.getBteOperacionesProcesosDelegate();
	    	
    		boolean workaround = (props.getProperty("sistra.workaround.jmsError") != null ? "true".equals(props.getProperty("sistra.workaround.jmsError")): false);
	    	
	    	for (Iterator it = procedimientos.iterator();it.hasNext();){
	    		Procedimiento procedimiento =  (Procedimiento) it.next();
	    		
	    		// Comprobaciones aplicables al aviso periodico
	    		if(idProcedimiento == null){
		    		// Comprobamos si tiene el aviso periodico activado pero asegurandonos que no se ha lanzado el aviso manualmente
		    		if (procedimiento.getPeriodica() == 'N') continue;
		    		
		    		// Comprobamos si se ha cumplido el intervalo fijado por propiedades
		    		if (procedimiento.getUltimoAviso() != null &&
		    			( (procedimiento.getUltimoAviso().getTime() + (intervaloAviso * 60 * 1000)) > ahora.getTime() ) ) continue;
	    		}
	    		
	    		// Marcamos con error las entradas que no han sido procesadas	    		
				if (maximoDiasAviso > 0) {
	        		// Calculamos fecha limite: restamos a fecha actual el numero maximo de dias limite para avisar
	        		Date fechaLimite = DataUtil.sumarDias(new Date(), maximoDiasAviso * -1);
	        		opd.marcarEntradasCaducadas(procedimiento.getIdentificador(), fechaLimite);
	        	}
				
				SecurityContext oldContext = SecurityContextAssociation.getSecurityContext();
	    		
	    		// Si se ha cumplido el intervalo avisamos al backoffice de las nuevas entradas
	    		avisoBackOffice(procedimiento,hasta);
	      		
	      		if (workaround){
	      			SecurityContextAssociation.setSecurityContext( oldContext );
	      		}
	    		
	    		// Guardamos tiempo en que se ha realizado el aviso	    	
	    		opd.marcarAvisoRealizado(procedimiento.getIdentificador(), ahora);    	
	   
	    	}	    		    	
    	}catch(Exception ex){
    		log.error("Excepción en proceso de aviso a BackOffices",ex);
    		throw new ExcepcionBTE("Excepción en proceso de aviso a BackOffices",ex);
    	}finally{
    		log.debug("Fin proceso aviso Backoffices");
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
    	avisoGestorImpl(AvisosBandeja.AVISO_GESTOR);    	
    }

	/**
     * Realiza proceso de aviso de monitorizacion en la ultima hora.
     * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void avisoMonitorizacion()  throws ExcepcionBTE{
    	avisoGestorImpl(AvisosBandeja.AVISO_MONITORIZACION);    	
    }

	
    
    private String construirMensajeAvisoGestor(Procedimiento procedimiento, String intervalo, DetalleEntradasProcedimiento de,
			DetalleNotificacionesProcedimiento dn) {
    	boolean existen = false;
    	StringBuffer mensaje = new StringBuffer(1024);
    	TraProcedimiento proc = (TraProcedimiento) procedimiento.getTraduccion("ca");
    	mensaje.append("<strong> * " + StringEscapeUtils.escapeHtml(procedimiento.getIdentificador() + " - " + proc.getDescripcion()) + "</strong>");
    	mensaje.append("<ul>");
    	if (de != null) {    		
	    	if (de.existenEntradas()) {
	    		existen = true;
	    		mensaje.append("<li>");
	    		mensaje.append(StringEscapeUtils.escapeHtml("Noves entrades produïdes en l'interval:"));
	    		mensaje.append("<ul>");
		    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Processades correctament: ") + de.getProcesadasOk() + "</li>");
		    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Processades amb error:") + de.getProcesadasError() + "</li>");
			    	mensaje.append("<li>" + StringEscapeUtils.escapeHtml("No processades:") + de.getNoProcesadas() + "</li>");
		    		if (de.getProcesadasErrorPendientes() > 0) {
		    			mensaje.append("<li>" + StringEscapeUtils.escapeHtml("ATENCIÓ: EXISTEIXEN ENTRADES PROCESSADES AMB ERROR QUE HAN DE SER REVISADES (TOTAL:" + de.getProcesadasErrorPendientes() + ") ") + "</li>");
		    		}
		    		if (de.getNoProcesadasPendientes() > 0) {
		    			mensaje.append("<li>" + StringEscapeUtils.escapeHtml("ATENCIÓ: SEGUEIXEN HAVENT ENTRADES SENSE PROCESSAR ANTERIORS A L'INTERVAL ACTUAL (TOTAL:" + de.getNoProcesadasPendientes() + ") ") + "</li>");
		    		}
	    		mensaje.append("</ul>");
	    		mensaje.append("</li>");
	    	}
    	}
    	
    	if (dn != null) {
	    	int notifsConAcuse = dn.getNotificacionesConAcuse().getAceptadas().size() +
									dn.getNotificacionesConAcuse().getNuevas().size() +
									dn.getNotificacionesConAcuse().getRechazadas().size();
			int notifsSinAcuse = dn.getNotificacionesSinAcuse().getAceptadas().size() +
									dn.getNotificacionesSinAcuse().getNuevas().size() +
									dn.getNotificacionesSinAcuse().getRechazadas().size();
			if ((notifsConAcuse + notifsSinAcuse) > 0) {
	    		existen = true;
	    		mensaje.append("<li>");
	    		mensaje.append(StringEscapeUtils.escapeHtml("Notificacions actualitzades en l'interval:"));
	    		mensaje.append("<ul>");
	    		if ((notifsConAcuse) > 0) {
		    		mensaje.append("<li>");
		    		mensaje.append(StringEscapeUtils.escapeHtml("Amb acusament de rebut signat:"));
		    		mensaje.append("<ul>");
			    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Noves notificacions: ") + generarHtmlDetalleNotificaciones(dn.getNotificacionesConAcuse().getNuevas()) + "</li>");			    		
			    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Notificacions acceptades: ") + generarHtmlDetalleNotificaciones(dn.getNotificacionesConAcuse().getAceptadas()) + "</li>");
			    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Notificacions rebutjades: ") + generarHtmlDetalleNotificaciones(dn.getNotificacionesConAcuse().getRechazadas()) + "</li>");
		    		mensaje.append("</ul>");
		    		mensaje.append("</li>");  	    		
	    		}
	    		if ((notifsSinAcuse) > 0) {
	    			mensaje.append("<li>");    		
		    		mensaje.append(StringEscapeUtils.escapeHtml("Sense acusament de rebut signat:"));
		    		mensaje.append("<ul>");
			    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Noves notificacions: ") + generarHtmlDetalleNotificaciones(dn.getNotificacionesSinAcuse().getNuevas()) + "</li>");
			    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Notificacions acceptades: ") + generarHtmlDetalleNotificaciones(dn.getNotificacionesSinAcuse().getAceptadas()) + "</li>");
			    		mensaje.append("<li>" + StringEscapeUtils.escapeHtml("Notificacions rebutjades: ") + generarHtmlDetalleNotificaciones(dn.getNotificacionesSinAcuse().getRechazadas()) + "</li>");
		    		mensaje.append("</ul>");
		    		mensaje.append("</li>");
	    		}
	    		mensaje.append("</ul>");
	    		mensaje.append("</li>");    		
	    	}
    	}
    	mensaje.append("</ul>");
    	
		String result = null;
    	if (existen) {
    		result = mensaje.toString();
    	}
    	return result;    	
	}

    
	private String generarHtmlDetalleNotificaciones(List notifs) {
		String html;
		
		html = notifs.size() + "<ul>";
		
		for (Iterator it = notifs.iterator(); it.hasNext();) {
			String idExpe = (String) it.next();
			html += "<li>Expedient " + idExpe + "</li>";
		}
		
		html += "</ul>";
		
		return html;
	}

	/**
     * Realiza proceso de aviso a BackOffice para un trámite. Metemos en cola asíncrona.
     * En caso de error lanza mensaje al log y permite continuar.
     * @param procedimiento
     */
    private void avisoBackOffice(Procedimiento procedimiento,Date hasta) throws Exception{    	
    		
    		log.debug("Aviso a backoffice procedimiento  " + procedimiento.getIdentificador() + " (hasta " + StringUtil.fechaACadena(hasta,StringUtil.FORMATO_TIMESTAMP) + ")");
    		
    		// Obtenemos entradas no procesadas    		    		    	
    		TramiteBandejaDelegate tbd = (TramiteBandejaDelegate) DelegateUtil.getTramiteBandejaDelegate();
    		
    		SecurityContext context = SecurityContextAssociation.getSecurityContext();
    		
    		// Obtenemos tramites del procedimiento que tienen entradas pendientes y generamos un mensaje por tramite
    		String idTramites[] = tbd.obtenerIdTramitesProcedimiento(procedimiento.getIdentificador(),ConstantesBTE.ENTRADA_NO_PROCESADA,null,hasta);
    		if (idTramites != null) {
    			for (int i = 0; i < idTramites.length; i++) {
    		
    				String  entradas [] = tbd.obtenerNumerosEntradas(procedimiento.getCodigo(), idTramites[i], ConstantesBTE.ENTRADA_NO_PROCESADA,null,hasta);
		    		
		    		log.debug("Aviso de " + entradas.length + " nuevas entradas para backoffice trámite " + idTramites[i] + " hasta " + StringUtil.fechaACadena(hasta,StringUtil.FORMATO_TIMESTAMP) + " (Procedimiento: " + procedimiento.getIdentificador() + ")");
		    		
		    		if (entradas.length > 0){
						// Dejamos entrada en la cola de avisos
				    	InitialContext ctx = new InitialContext();
				    	String colaAvisos = (String) ctx.lookup("java:comp/env/colaAvisos");
					    Queue queue = (Queue) ctx.lookup(colaAvisos);		 
					    QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("java:/XAConnectionFactory");
					    QueueConnection cnn = factory.createQueueConnection();
					    QueueSession sess = cnn.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);    		  
						TextMessage msg = sess.createTextMessage(BteStringUtil.numeroEntradasToString(entradas));
						QueueSender sender = sess.createSender(queue);
						sender.send(msg,DeliveryMode.NON_PERSISTENT,4,0);				
		    		}
    			}
    		}
    		
    		SecurityContextAssociation.setSecurityContext( context );
    }    	    
    
    private void avisoGestorImpl(String tipoAviso) throws ExcepcionBTE {
		LoginContext lc = null;
    	
    	try {
    		
    		log.debug("Aviso gestor (" + tipoAviso + ") - inicio");
    		
    		// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();	
    		
			// Obtenemos delegates
			AvisosBandejaDelegate avd = DelegateUtil.getAvisosBandejaDelegate();
			GestorBandejaDelegate gb = DelegateUtil.getGestorBandejaDelegate();    		
    		BteOperacionesProcesosDelegate opd = DelegateUtil.getBteOperacionesProcesosDelegate();
    		PadDelegate pad = DelegatePADUtil.getPadDelegate();
			
    		// Recuperamos lista de gestores
			List gestores = gb.listarGestoresBandeja();
			
			// Obtenemos intervalo aviso. Establecemos un intervalo de seguridad (10 min) para evitar que
			// entradas recientes nos alerten de que aun no estan procesadas
			int ventanaTiempo = 10;
			Date ahora = new Date();
    		Date hasta = DataUtil.sumarMinutos(ahora, (ventanaTiempo * -1));
    		Date desde = avd.obtenerFechaUltimoAviso(tipoAviso);
			if (desde == null) {
				if (AvisosBandeja.AVISO_GESTOR.equals(tipoAviso)){
					// Es el primer aviso para gestores, tomamos 24 horas antes
					desde = DataUtil.sumarHoras(ahora, -24);
				} else {
					// Es el primer aviso para monitorizacion, tomamos 1 horas antes
					desde = DataUtil.sumarHoras(ahora, -1);
				}			
			} else {
				// Restamos intervalo de seguridad que se descontó en aviso anterior
				desde = DataUtil.sumarMinutos(desde, (ventanaTiempo * -1));
			}
			String intervalo = "des de " + StringUtil.fechaACadena(desde,StringUtil.FORMATO_TIMESTAMP) + 
				 " fins a " + StringUtil.fechaACadena(hasta,StringUtil.FORMATO_TIMESTAMP);	
			
			
			log.debug("Aviso gestor (" + tipoAviso + ") - intervalo: " + intervalo);
	    	
	    	// Recorremos los gestores y construimos mensaje de aviso
	    	String titulo = "Safata Telematica  - " + intervalo;
	    	if (AvisosBandeja.AVISO_MONITORIZACION.equals(tipoAviso)){
	    		titulo = "Safata Telematica (monitorizacion) - " + intervalo;
	    	}
    		StringBuffer mensaje;
    		Hashtable entradasProcedimiento = new Hashtable();
    		Hashtable notificacionesProcedimiento = new Hashtable();
    		boolean enviar;    		
    		MensajeEnvio enviosEmail = new MensajeEnvio();
	    	for (Iterator it = gestores.iterator();it.hasNext();){
	    		
	    		// Obtenemos siguiente gestor
	    		GestorBandeja g =  (GestorBandeja) it.next();
	    		
	    		// Si en aviso gestor no esta habilitado el proceso de aviso pasamos a siguiente gestor
	    		// Si en aviso monitorizacion no esta habilitado el proceso de aviso monitorizacion pasamos a siguiente gestor
	    		if ( (AvisosBandeja.AVISO_GESTOR.equals(tipoAviso) && "N".equals(g.getAvisarEntradas()) && "N".equals(g.getAvisarNotificaciones()))
	    				||
	    				(AvisosBandeja.AVISO_MONITORIZACION.equals(tipoAviso) && "N".equals(g.getAvisarMonitorizacion()))
	    			) 
	    		{
	    				continue;
	    		}
	    		
	    		log.debug("Aviso gestor (" + tipoAviso + ") - Gestor " + g.getSeyconID() + ": verificar si existen elementos para avisar");
	    		
	    		// Creamos string buffer para el mensaje
	    		mensaje = new StringBuffer(8192); 	
	    		
	    		// Establecemos titulo e inicializamos mensaje	    		
	    		mensaje.append("<html><head><style type=\"text/css\">body	{font-family: verdana, arial, helvetica, sans-serif; font-size: 8pt; color: #515b67;}</style></head><body>");
	    		mensaje.append("Informe Safata Telem&agrave;tica  (" + intervalo + ") <br/><br/>");
	    		
	    		// Marcamos como que no hay entradas xa avisar
	    		enviar=false;
	    		
	    		// Obtenemos entradas nuevas para los tramites asociados al gestor
	    		if (g.getProcedimientosGestionados() != null) {
		    		for (Iterator it2 = g.getProcedimientosGestionados().iterator();it2.hasNext();){	    				    			
		    			
		    			// Obtenemos siguiente tramite gestionado por el gestor
		    			Procedimiento procedimiento = (Procedimiento) it2.next();	   	    			
		    			
		    			DetalleEntradasProcedimiento de = null;
		    			DetalleNotificacionesProcedimiento dn = null;
		    			
		    			// Buscamos entradas en el intervalo verificando si se han buscado antes
		    			if ( (AvisosBandeja.AVISO_GESTOR.equals(tipoAviso) && "S".equals(g.getAvisarEntradas())) || 
		    				 (AvisosBandeja.AVISO_MONITORIZACION.equals(tipoAviso) && "S".equals(g.getAvisarMonitorizacion()))	) {
							de = (DetalleEntradasProcedimiento) entradasProcedimiento.get(procedimiento.getIdentificador());
			    			if (de == null) {
			    				de = opd.obtenerDetalleEntradasProcedimiento(procedimiento, desde, hasta);
			    				entradasProcedimiento.put(procedimiento.getIdentificador(), de);
			    			}
		    			}
		    			// Buscamos notificaciones en el intervalo verificando si se han buscado antes	    	
		    			if (AvisosBandeja.AVISO_GESTOR.equals(tipoAviso) && 
		    					"S".equals(procedimiento.getAvisarNotificaciones()) && "S".equals(g.getAvisarNotificaciones())) {
							dn = (DetalleNotificacionesProcedimiento) notificacionesProcedimiento.get(procedimiento.getIdentificador());
			    			if (dn == null) {
			    				dn = pad.obtenerDetalleNotificacionesProcedimiento(procedimiento.getIdentificador(), desde, hasta);
			    				notificacionesProcedimiento.put(procedimiento.getIdentificador(), dn);
			    			} 
		    			}
		    			
		    			
		    			// Construimos mensaje de aviso (null si no hay que avisar)
		    			String msgAviso = construirMensajeAvisoGestor(procedimiento, intervalo, de, dn);
		    			if (msgAviso != null){
		    				enviar=true;
		    				mensaje.append(msgAviso + "<br/>");	    	    				
		    			}
		    			
		    		}
	    		}
	    		
	    		// Enviamos correo a gestor con nuevas entradas
	    		if (enviar) {
	    			
	    			log.debug("Aviso gestor (" + tipoAviso + ") - Gestor " + g.getSeyconID() + ": se genera mail de aviso");
	    			
	    			mensaje.append("</body></html>");
	    			
	    			MensajeEnvioEmail me = new MensajeEnvioEmail();
	    			String [] dest = {g.getEmail()};
	    			me.setHtml(true);
	    			me.setDestinatarios(dest);
	    			me.setTitulo(titulo);
	    			me.setTexto(mensaje.toString());
	    			enviosEmail.addEmail(me);	    		    				    			
	    		} else {
	    			log.debug("Aviso gestor (" + tipoAviso + ") - Gestor " + g.getSeyconID() + ": no se genera mail de aviso");
	    		}
	    			    		    	
	    	}	    		
	    	
	    	// Enviamos al modulo de movilidad los emails
	    	log.debug("Aviso gestor (" + tipoAviso + ") - envio de mensajes a mobtratel");
	    	opd.realizarEnviosGestores(tipoAviso, enviosEmail, ahora);
	    	
	    	
    	}catch (Exception ex){
    		log.error("Excepción enviando correo a gestor",ex);
    		throw new ExcepcionBTE("Excepción en proceso de aviso a Gestores",ex);
    	}finally{
    		// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
			log.debug("Aviso gestor (" + tipoAviso + ") - fin");
    	}
	}
    
}
