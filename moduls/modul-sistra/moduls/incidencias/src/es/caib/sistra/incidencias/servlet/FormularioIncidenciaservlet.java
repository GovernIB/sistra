package es.caib.sistra.incidencias.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;




import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.persistence.delegate.BteSistraDelegate;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.sistra.modelInterfaz.TramiteInfo;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.util.DataUtil;
import es.caib.util.UsernamePasswordCallbackHandler;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;


public class FormularioIncidenciaservlet extends HttpServlet
{
	
	protected static Log log = LogFactory.getLog(FormularioIncidenciaservlet.class);
	
	private static Map<String, ResourceBundle> literales = new HashMap<String, ResourceBundle>();
	
	private static Properties propiedadesConfiguracion = null;
	
	private static String plantillaHtml = null;
	
	private static String[] camposFormulario = { "tramiteDesc", "tramiteId",
			"procedimientoId", "fechaCreacion", "idPersistencia","nivelAutenticacion", "nif",
			"nombre", "telefono", "email","horarioContacto","procedimientoSelec", "problemaTipo", "problemaDesc" };	 
	
	public void init() {
		// Inicializa props
		try {
			propiedadesConfiguracion = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
		} catch (DelegateException e) {
			log.error("No se ha podido acceder a propiedades configuracion", e);
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
    	try {   	
	    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	    	if (!isMultipart) {
	    		// Mostrar form
	    		mostrarFormularioIncidencias(request, response);
	    	} else {
	    		// Procesar form   
	    		procesarFormularioIncidencias(request, response);    		
	    	}	    		    	
    	} catch (Exception ex) {
    		throw new ServletException("Error formulario incidencias", ex);
    	}    	    	
	}

	private void procesarFormularioIncidencias(HttpServletRequest request,
			HttpServletResponse response) throws Exception,
			IOException {
		boolean res = false;
		String lang = "ca";
		String procedimiento = "";
		try {
			Map<String, String> paramString = new HashMap<String, String>();
			byte[] contentFile = null;
			String nameFile = null;
			String contentTypeFile = null;
			
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(repository);
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);    		
			for (FileItem item : items) {
			    if (item.isFormField()) {
			    	paramString.put(item.getFieldName(), item.getString());
			    } else {
			    	contentFile = IOUtils.toByteArray(item.getInputStream());
			        nameFile = item.getName();
			        contentTypeFile = item.getContentType();
			    }
			}
			
			lang = paramString.get("lang");
			
			String destinatariosProp = propiedadesConfiguracion.getProperty("incidencias.mail.tipo." + paramString.get("problemaTipo"));
			String emailDs = propiedadesConfiguracion.getProperty("incidencias.mailDs");
			String titulo = getLiteral(lang, "incidencias.email.asunto");
			
			if (StringUtils.isNotBlank(paramString.get("procedimientoId"))){
				procedimiento = paramString.get("procedimientoId");
			} else if (StringUtils.isNotBlank(paramString.get("procedimientoSelec"))){
				procedimiento = paramString.get("procedimientoSelec");
			}			
			
			String textoHtml = construyeMensajeHtml(lang, paramString);
			List<String> destinatarios = getDestinatarios(destinatariosProp, procedimiento, paramString.get("problemaTipo"));
			
			String paramValue = paramString.get("problemaTipo");	
			if (StringUtils.isNotBlank(paramValue)){
				titulo = titulo + " - " + getDescIncidencia(lang, paramValue);
			}
			
			if (StringUtils.isNotBlank(procedimiento)){
				titulo = titulo + " - " + procedimiento;
			}
			
			if (destinatarios.size() == 0) {
				throw new Exception("No se ha especificado lista de destinatarios");
			}
			res = EmailUtil.sendEmail(emailDs, destinatarios, titulo, textoHtml, contentFile, nameFile, contentTypeFile);
		} catch (Exception ex) {
			log.error("Error al enviar correo: " + ex.getMessage(), ex);
		}
		
		String msg = getLiteral(lang, "incidencias.resultado.ok");
		if (!res) {
			msg = getLiteral(lang, "incidencias.resultado.ko");
		}
		
		request.setAttribute("resultado", msg);
		
		// - Redirige JSP
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( "/WEB-INF/jsp/resultado.jsp" );
		dispatcher.forward( request, response );
	}

	private List<String> getDestinatarios(String destinatariosProp, String idProcedimiento, String tipoIncidencia) {
		List<String> resultado = new ArrayList<String>();
		
		if (destinatariosProp != null && !destinatariosProp.equals("")){ 
			String[] destinatarios = destinatariosProp.trim().split(",");
			for (String s : destinatarios) {
				if ("#correo.gestor#".equals(s)) {
					if (StringUtils.isNotBlank(idProcedimiento)) {
						List<String> correoGestores = getCorreoGestores(idProcedimiento);
						resultado.addAll(correoGestores);				
					}
				} else {
					resultado.add(s);
				}
			}
		}
		
		if ("FUN".equals(tipoIncidencia)){
			if (StringUtils.isNotBlank(idProcedimiento)){
				List<String> correoIncidencias = getCorreoIncidencia(idProcedimiento);
				resultado.addAll(correoIncidencias);
			}
		}
		
		return resultado;
	}

	private List<String> getCorreoGestores(String idProcedimiento) {
		List<String> correoGestores = new ArrayList<String>();
		LoginContext lc = null;		
		try{					
			String user = propiedadesConfiguracion.getProperty("auto.user");
			String pass = propiedadesConfiguracion.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();
			
			BteSistraDelegate bte = DelegateBTEUtil.getBteSistraDelegate();
			ProcedimientoBTE p = bte.obtenerProcedimiento(idProcedimiento);
			if (p.getEmailGestores() != null) {
				for (Iterator it = p.getEmailGestores().iterator(); it.hasNext();) {
					correoGestores.add((String) it.next());
				}
			}
			
		} catch (Exception ex) {
			log.error("Error accediendo a correo gestor para procedimiento " + idProcedimiento);			
		}finally{				
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
		return correoGestores;
	}
	
	private List<String> getCorreoIncidencia(String idProcedimiento) {
		List<String> correoIncidencias = new ArrayList<String>();
		LoginContext lc = null;		
		try{					
			String user = propiedadesConfiguracion.getProperty("auto.user");
			String pass = propiedadesConfiguracion.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();
			
			BteSistraDelegate bte = DelegateBTEUtil.getBteSistraDelegate();
			ProcedimientoBTE p = bte.obtenerProcedimiento(idProcedimiento);
			if (p.getEmailIncidencias() != null) {
				String[] destinatarios = p.getEmailIncidencias().split(";");
				for (String s : destinatarios){
					log.debug("Correo de incidencias " + s );
					correoIncidencias.add(s);
				}
			}
			
		} catch (Exception ex) {
			log.error("Error accediendo a correos de incidencias para procedimiento " + idProcedimiento);			
		}finally{				
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
		return correoIncidencias;
	}

	private String construyeMensajeHtml(String lang, Map<String, String> paramString) {
		String listaCampos = "";
		for (String s : camposFormulario) {
			listaCampos = addParameterMensaje(lang, listaCampos, paramString, getLiteral(lang, "incidencias." + s), s);
		}				
		
		String mensaje = getPlantilla();
		String paramValue = paramString.get("problemaTipo");
		if (StringUtils.isNotBlank(paramValue)){
			paramValue = getDescIncidencia(lang, paramValue);
		}
		String titulo = getLiteral(lang, "incidencias.email.titulo") + " - " + paramValue;
		mensaje = StringUtils.replace(mensaje, "[#TITULO#]", titulo);
		mensaje = StringUtils.replace(mensaje, "[#CONTENIDO#]", listaCampos);
		
		return mensaje;
	}

	private String addParameterMensaje(String lang, String res, Map<String, String> paramString,
			String paramDesc, String paramName) {
		String paramValue = paramString.get(paramName);		
		if (StringUtils.isNotBlank(paramValue)) {
			if ("problemaTipo".equals(paramName)) {
				paramValue = getDescIncidencia(lang, paramValue);
			}
			
			if ("nivelAutenticacion".equals(paramName)) {
				paramValue = getDescNivelesAutenticacion(lang, paramValue);
			}
			
			if ("procedimientoSelec".equals(paramName)) {
				try{

					BteSistraDelegate bte = DelegateBTEUtil.getBteSistraDelegate();
					ProcedimientoBTE p = bte.obtenerProcedimiento(paramString.get("procedimientoSelec"), lang);	
				} catch (Exception ex) {
					log.error("Error accediendo a descripción del procedimiento " + paramString.get("procedimientoSelec"));			
				}
			}
			
			res += "<tr><th>" + StringEscapeUtils.escapeHtml(paramDesc) + "</th><td>" + StringEscapeUtils.escapeHtml(paramValue) +"</td></tr>";
		}
		return res;
	}

	private void mostrarFormularioIncidencias(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// - Idioma
		String lang = "ca";
		Date fechaLimite = null;
		int dias = Integer.parseInt(StringUtils.defaultIfEmpty(propiedadesConfiguracion.getProperty("incidencias.dias"), "0"));
		if (dias > 0){
			fechaLimite = DataUtil.sumarDias(new Date(), dias * -1);
		}
		if (request.getParameter("lang") != null) {
			lang = request.getParameter("lang");
		}
		request.setAttribute("lang", lang);
		// - Obtiene lista de problemas
		Map<String, String> problemasLista = obtenerListaProblemas(lang);    			
		request.setAttribute("problemasLista", problemasLista);
		
		
		// - Obtiene lista de procedimientos en caso de que no se haya fijado
		try {
			PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();	
			if (StringUtils.isEmpty(request.getParameter("procedimientoId")) && plgLogin.getMetodoAutenticacion(request.getUserPrincipal()) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO) {			
				PadDelegate pad = DelegatePADUtil.getPadDelegate();
				List procedimientos = pad.obtenerProcedimientosUsuario(lang, fechaLimite);
				request.setAttribute("mostrarListaProcedimientos", "S");
				request.setAttribute("listaProcedimientos", procedimientos);
			}
			
			
		} catch (Exception ex) {
			log.error("Error al listar procedimientos del usuario: " + ex.getMessage(), ex);
		}			

		
		// - Redirige JSP
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( "/WEB-INF/jsp/formulario.jsp" );
		dispatcher.forward( request, response );
	}

	private Map<String, String> obtenerListaProblemas(String lang) {		
		Map<String, String> problemasLista = new LinkedHashMap<String, String>();		
		String tipos = propiedadesConfiguracion.getProperty("incidencias.tipos");
		if (StringUtils.isNotBlank(tipos)) {
			String[] listTipos = tipos.split(",");
			for (String t : listTipos) {
				String descTipo = null;    					
				descTipo = getDescIncidencia(lang, t);
				problemasLista.put(t, descTipo);				    				
			}
		}
		return problemasLista;
	}
	
	private String getDescIncidencia(String lang, String tipoIncidencia){
		return propiedadesConfiguracion.getProperty("incidencias." + tipoIncidencia + "." + lang);
	}
	
	private String getDescNivelesAutenticacion(String lang, String nivelAutenticacion){
		return getLiteral(lang, "incidencias.nivelesAutenticacion." + nivelAutenticacion);
	}
	
	private String getLiteral(String lang, String codLiteral) {
		ResourceBundle rb = literales.get(lang);
		if (rb == null) {
			synchronized (literales) {	
				rb = ResourceBundle.getBundle("incidencias-messages", new Locale(lang));
				literales.put(lang, rb);
			}
		}		
		String literal = "";
		try {
			literal = rb.getString(codLiteral);
		} catch (Exception ex) {
			log.error("Error accediendo literal " + codLiteral, ex);				
		}
		return literal;
	}
	
	
	private String getPlantilla() {
		try {
			if (plantillaHtml == null) {
				InputStream isHtml = this.getClass().getResourceAsStream("mailIncidencia.html");
				byte[] content  = IOUtils.toByteArray(isHtml);
				plantillaHtml = new String(content, "UTF-8");
			}
			return plantillaHtml;
		} catch (Exception ex) {
			log.error("No se ha podido cargar la plantilla para envio aviso", ex);
			return "";
		}
	}
    
    
}
