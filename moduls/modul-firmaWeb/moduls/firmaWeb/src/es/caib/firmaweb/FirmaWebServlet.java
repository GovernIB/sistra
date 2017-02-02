package es.caib.firmaweb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fundaciobit.plugins.signature.api.CommonInfoSignature;
import org.fundaciobit.plugins.signature.api.FileInfoSignature;
import org.fundaciobit.plugins.signature.api.ITimeStampGenerator;
import org.fundaciobit.plugins.signature.api.PdfVisibleSignature;
import org.fundaciobit.plugins.signature.api.PolicyInfoSignature;
import org.fundaciobit.plugins.signature.api.SecureVerificationCodeStampInfo;
import org.fundaciobit.plugins.signature.api.SignaturesTableHeader;
import org.fundaciobit.plugins.signature.api.StatusSignature;
import org.fundaciobit.plugins.signature.api.StatusSignaturesSet;
import org.fundaciobit.plugins.signatureweb.api.ISignatureWebPlugin;

import es.caib.firmaweb.model.CallbackData;
import es.caib.firmaweb.model.ConfigData;
import es.caib.firmaweb.model.DocumentData;
import es.caib.firmaweb.model.Plugin;
import es.caib.firmaweb.model.SistraSignaturesSet;
import es.caib.firmaweb.util.Base64UrlSafe;
import es.caib.firmaweb.util.ConfigurationUtil;
import es.caib.firmaweb.util.SignaturePluginManager;
import es.caib.firmaweb.util.SignatureSetStore;
import es.caib.firmaweb.util.SignatureWebPluginManager;
import es.caib.firmaweb.util.Utils;


/**
 * Interfaz API Firma Web.
 * 
 * @author Indra
 *
 */
public class FirmaWebServlet extends HttpServlet {
	
	protected static Log log = LogFactory.getLog(FirmaWebServlet.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// Css personalizado
			String urlCssCustom = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("organismo.cssCustom");
			if (urlCssCustom != null) {
				request.setAttribute("urlCssCustom", urlCssCustom);
			}
			
			// Accion
			String accion = request.getParameter("accion");
			if ("iniciarFirma".equals(accion)) {
				iniciarFirma(request, response);						
			} if ("firmar".equals(accion)) {
				listarPlugins(request, response);			
			} if ("realizarFirma".equals(accion)) {
				firmar(request, response);			
			} else if (accion.equals("finalizarFirma")) {
				finalizarFirma(request, response);			
			}			
		} catch (Exception ex) {
			log.error("Excepcion no controlada: " + ex, ex);
			throw new ServletException("Excepcion no controlada: " + ex);
		}
	}


	/**
	 * Finaliza proceso de firma devolviendo la firma a la aplicacion.
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 */
	private void finalizarFirma(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String responseStr;
		String idFirmada = request.getParameter("id");
		
		SistraSignaturesSet ss = (SistraSignaturesSet) SignatureSetStore.load(idFirmada);
		
		String lang = ss.getCommonInfoSignature().getLanguageUI();
		
		SignatureSetStore.remove(idFirmada);
				
		if (ss.getStatusSignaturesSet().getStatus() == StatusSignaturesSet.STATUS_FINAL_OK) {
			FileInfoSignature fis = ss.getFileInfoSignatureArray()[0];
		    StatusSignature status = fis.getStatusSignature();
		    if (status.getStatus() == StatusSignature.STATUS_FINAL_OK) {
		    	// - Redirige JSP
		    	request.setAttribute("lang", lang);
		    	request.setAttribute("callbackAppUrl", ss.getCallbackAppUrl());
		    	request.setAttribute("callbackAppParamSignature", ss.getCallbackAppParamSignature());
		    	request.setAttribute("callbackAppParamOthers", ss.getCallbackAppParamOthers());
		    	request.setAttribute("callbackTarget", ss.getCallbackAppTarget());
		    	request.setAttribute("firmaB64", Base64UrlSafe.encodeB64UrlSafe(status.getSignedData()));
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( "/WEB-INF/jsp/redirectApp.jsp" );
				dispatcher.forward( request, response );
				return;
		    }
		}
		
		
		// Error
		request.setAttribute("lang", lang);
		request.setAttribute("error", ss.getStatusSignaturesSet().getErrorMsg());
		request.setAttribute("errorCodigo", ss.getStatusSignaturesSet().getStatus());
		request.setAttribute("urlCancel", ss.getCallbackAppUrlCancel());
		request.setAttribute("target", ss.getCallbackAppTarget());
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( "/WEB-INF/jsp/error.jsp" );
		dispatcher.forward( request, response );
		
	}

	
	private void listarPlugins(HttpServletRequest request, HttpServletResponse response)
			throws Exception, IOException {
		
		SignaturePluginManager pluginManager = SignatureWebPluginManager.getInstance();
		
		String signaturesSetID = request.getParameter("signaturesSetID");
		
		SistraSignaturesSet signaturesSet = (SistraSignaturesSet) SignatureSetStore.load(signaturesSetID);
		
		String lang = signaturesSet.getCommonInfoSignature().getLanguageUI();
		
		String urlInfoFirma = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("organismo.urlInfoFirma"+"."+lang);
		
		// Obtiene plugins  
		List<Plugin> plugins = pluginManager.getAllPlugins();
		List<Plugin> pluginsValidos = new ArrayList<Plugin>();				
		
		if (plugins != null) {
			for (Plugin p : plugins) {
				// Filtrar plugins x SignatureSet
				ISignatureWebPlugin signaturePlugin = (ISignatureWebPlugin) pluginManager.getInstanceByPluginID(p.getPluginID());
				boolean puedeUsarse = signaturePlugin.filter(request, signaturesSet);
				if (puedeUsarse) {
					pluginsValidos.add(p);
				}								
			}
		}	
		
		request.setAttribute("lang", lang);
		request.setAttribute("plugins", pluginsValidos);
		request.setAttribute("signaturesSetID", signaturesSetID);
		request.setAttribute("urlCancel", signaturesSet.getCallbackAppUrlCancel());
		request.setAttribute("target", signaturesSet.getCallbackAppTarget());
		request.setAttribute("urlInfoFirma", urlInfoFirma);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( "/WEB-INF/jsp/seleccionPlugin.jsp" );
		dispatcher.forward( request, response );
		
	}

	
	private void firmar(HttpServletRequest request, HttpServletResponse response)
			throws Exception, IOException {
		SignaturePluginManager pluginManager = SignatureWebPluginManager.getInstance();
		
		String signaturesSetID = request.getParameter("signaturesSetID");
		String pluginParam = request.getParameter("plugin");
		
		SistraSignaturesSet signaturesSet = (SistraSignaturesSet) SignatureSetStore.load(signaturesSetID);
		
		Long pluginID = Long.parseLong(pluginParam);
		
		// Establecemos plugin y almacenamos de nuevo			
		signaturesSet.setPluginID(pluginID);
		SignatureSetStore.store(signaturesSet); // TODO Para bien ser el plugin igual mejor quitarlo del signature set
		
		
		// Invocamos a plugin
		int signatureIndex = -1;
		String contextPlugin = "/common/signwebmodule";
		
		String urlFront = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.url");
		String relativeRequestPluginBasePath = request.getContextPath() + contextPlugin + "/requestPlugin/" + signaturesSet.getSignaturesSetID() + "/" + signatureIndex; 
		String absoluteRequestPluginBasePath = urlFront + relativeRequestPluginBasePath;
		
		log.debug(" - URL relativeRequestPluginBasePath: " + relativeRequestPluginBasePath);
		log.debug(" - URL absoluteRequestPluginBasePath: " + absoluteRequestPluginBasePath);

		// Long pluginID = signaturesSet.getPluginID();   v2??  
		
		
		// ISignatureWebPlugin signaturePlugin = pluginManager.getInstanceByPluginID(pluginID);   v2???
		ISignatureWebPlugin signaturePlugin = (ISignatureWebPlugin) pluginManager.getInstanceByPluginID(pluginID);
		
		String urlToPluginWebPage = signaturePlugin.signDocuments(request, absoluteRequestPluginBasePath, relativeRequestPluginBasePath, signaturesSet);

		log.debug(" - URL plugin: " + urlToPluginWebPage);		
		
		response.sendRedirect(urlToPluginWebPage);
	}


	private void iniciarFirma(HttpServletRequest request,
			HttpServletResponse response) throws Exception,
			UnsupportedEncodingException, IOException {
		String responseStr;
		DocumentData dd = new DocumentData();
		dd.setDocumentoB64UrlSafe(request.getParameter("documentoContentB64UrlSafe"));
		dd.setDocumentoFilename(request.getParameter("documentoFileName"));
		dd.setDocumentoNif(request.getParameter("documentoNif"));
		
		ConfigData cd = new ConfigData();
		cd.setLang(request.getParameter("configLang"));
		cd.setSignType(request.getParameter("configSignType"));
		cd.setSignMode(request.getParameter("configSignMode"));
		cd.setSignAlgorithm(request.getParameter("configSignAlgorithm"));
		
		CallbackData cbd = new CallbackData();
		cbd.setUrl(request.getParameter("callbackUrl"));
		cbd.setParamSignature(request.getParameter("callbackParamSignature"));
		cbd.setUrlCancel(request.getParameter("callbackUrlCancel"));		
		cbd.setParamOthers(getCallbackParamsOthers(request));
		cbd.setTarget(request.getParameter("callbackTarget"));
		
		// - ID Set
		String signaturesSetID = SignatureSetStore.generateSignaturesSetID();
		
		// Url retorno plugin
		String urlFinal = request.getContextPath() + request.getServletPath() + "?accion=finalizarFirma&id=" + signaturesSetID;
		
		// Crea signature set y almacena
		SistraSignaturesSet signaturesSet = createSignaturesSet(signaturesSetID, urlFinal, cd, dd, cbd);
		SignatureSetStore.store(signaturesSet);
		
		// Devuelve id signatureset
		responseStr = "{\"id\":" + "\"" + signaturesSet.getSignaturesSetID() + "\"}";
		
		// Devolvemos respuesta
		byte[] encBytes = (responseStr).getBytes( "UTF-8" );
		response.reset();
		response.setContentLength(encBytes.length);
		response.setContentType("application/json; charset=UTF-8");
		response.getOutputStream().write(encBytes);
		response.flushBuffer();
	}

	/**
	 * Obit
	 * @param request
	 * @return
	 */
	private Map<String, String> getCallbackParamsOthers(HttpServletRequest request) {
		String prefix = "callbackParamOther_";
		Map<String, String> result = new HashMap<String, String>();
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()){
			String paramName = (String) parameterNames.nextElement();
			if (paramName.startsWith(prefix)) {
				result.put(paramName.substring(prefix.length()), request.getParameter(paramName));
			}
		}		
		return result;
	}

	
	private SistraSignaturesSet createSignaturesSet(String signaturesSetID, String urlFinal, ConfigData cd, DocumentData dd, CallbackData cbd) throws Exception {
		
		
		// Creamos fichero temporal
		String fnm = FilenameUtils.getBaseName(dd.getDocumentoFilename());
		String ext = FilenameUtils.getExtension(dd.getDocumentoFilename());
		File fileToSign = File.createTempFile(fnm, "." + ext);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(Base64UrlSafe.decodeB64UrlSafe(dd.getDocumentoB64UrlSafe()));
		FileOutputStream fos = new FileOutputStream(fileToSign);
		IOUtils.copy(bis, fos);
		bis.close();
		fos.close();
		
		// Lenguaje
		String lang = cd.getLang();
		
		// Caducidad
		Calendar expiryDate = Calendar.getInstance();
		expiryDate.add(Calendar.MINUTE, 60);	
		
		
		String filtreCertificats = "filters.1=nonexpired:"; // Filtro certificados (ver doc miniapplet)			
		String username = dd.getDocumentoNif(); // Username (NIF) 
		String administrationID = dd.getDocumentoNif(); // AdministrationID (NIF)
		final PolicyInfoSignature policyInfoSignature = null; // Politica de firma (opcional, en ejemplo a null)
		
		CommonInfoSignature commonInfoSignature = new CommonInfoSignature(lang, filtreCertificats, username, administrationID, policyInfoSignature);
		
		
		// - ID firma (se firma 1 solo fichero, hacemos igual al set)
		String signatureID = signaturesSetID;
		String fileName = dd.getDocumentoFilename();
		String mimeType = Utils.getMimeType(fileToSign);
		String reason = "";  // Ver si son las props del cliente de @firma para visualizacion firmas
		String location = ""; // Ver si son las props del cliente de @firma para visualizacion firmas
		String email = ""; // ¿Para qué? ¿Obligatorio?
		int signNumber = 1; // Només es suporta una firma
		String signType = cd.getSignType();  // PAdES / XAdES / CAdES / SMIME
		int signMode = Integer.parseInt(cd.getSignMode()); // 0 - Attached / 1 - Detached (Para Xades y Cades)  
		String signAlgorithm = cd.getSignAlgorithm();
		int locationSignTableID = 0;  // 0: sin tabla firmas / 1: en primera pagina / -1: en ultima pagina  (Para Pades)
		SignaturesTableHeader signaturesTableHeader = null;  // No usamos tabla firmas
		PdfVisibleSignature pdfInfoSignature = null; // Visualizacion firma en Pades
		SecureVerificationCodeStampInfo csvStampInfo = null; // Estampación CSV en Pades
		boolean userRequiresTimeStamp = false; // Incluir sello de tiempo
		ITimeStampGenerator timeStampGenerator = null; // Se supone que generador del timestamp. Esta a null en el ejemplo.
		
		FileInfoSignature fis = new FileInfoSignature(signatureID, fileToSign, mimeType, fileName,
		        reason, location, email, signNumber, lang, signType, signAlgorithm,
		        signMode, locationSignTableID, signaturesTableHeader, pdfInfoSignature, csvStampInfo,
		        userRequiresTimeStamp, timeStampGenerator);
		FileInfoSignature[] fileInfoSignatureArray = new FileInfoSignature[] { fis };
		
		
		SistraSignaturesSet  signaturesSet = new SistraSignaturesSet(signaturesSetID, expiryDate.getTime(), commonInfoSignature, fileInfoSignatureArray, urlFinal);		
		
		// Url callback
		signaturesSet.setCallbackAppUrl(cbd.getUrl());
		signaturesSet.setCallbackAppParamSignature(cbd.getParamSignature());
		signaturesSet.setCallbackAppParamOthers(cbd.getParamOthers());
		signaturesSet.setCallbackAppUrlCancel(cbd.getUrlCancel());
		signaturesSet.setCallbackAppTarget("parent".equals(cbd.getTarget())?"_top":"_self");
		
		return signaturesSet;
	}
	
	
	
	
}
