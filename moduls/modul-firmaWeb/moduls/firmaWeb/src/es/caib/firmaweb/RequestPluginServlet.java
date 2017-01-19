package es.caib.firmaweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fundaciobit.plugins.signatureweb.api.ISignatureWebPlugin;
import org.fundaciobit.plugins.webutils.AbstractWebPlugin;

import es.caib.firmaweb.model.SistraSignaturesSet;
import es.caib.firmaweb.util.SignatureSetStore;
import es.caib.firmaweb.util.SignatureWebPluginManager;




public class RequestPluginServlet extends HttpServlet {
	
	protected static Log log = LogFactory.getLog(RequestPluginServlet.class);
	
	
	/** ALERTA: Hi ha referències a aquest context dins web.xml !!!!! */
	  public static final String CONTEXTWEB = "/common/signwebmodule";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processServlet(request, response, false);
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processServlet(request, response, true);
	}

	
	protected void processServlet(HttpServletRequest request,
			      HttpServletResponse response, boolean isPost) throws ServletException, IOException {
			final boolean debug = log.isDebugEnabled();
	    
	    if (debug) {
	      log.debug(AbstractWebPlugin.servletRequestInfoToStr(request));
	    }
	    
	    // uri = /common/signmodule/requestPlugin/1466408733012148444/-1/index.html
	    String uri = request.getRequestURI();
	    if (debug) {
	      log.debug(" uri = " + uri);
	    }
	    final String BASE = CONTEXTWEB + "/requestPlugin";
	    int index = uri.indexOf(BASE);
	    
	    if (index == -1) {
	      String msg = "URL base incorrecte !!!! Esperat " + BASE + ". URI = " + uri;
	      throw new IOException(msg);
	    }
	  
	    //  idAndQuery = 1466408733012148444/-1/index.html
	    String idAndQuery = uri.substring(index + BASE.length() + 1);
	    if (debug) {
	      log.info(" idAndQuery = " + idAndQuery);
	    }
	    
	    index = idAndQuery.indexOf('/');
	    
	    String idStr = idAndQuery.substring(0, index);
	    int index2 = idAndQuery.indexOf('/',index + 1);
	    String indexStr = idAndQuery.substring(index + 1, index2);
	    String query = idAndQuery.substring(index2 + 1, idAndQuery.length());
	        
	    if (debug) {
	      log.info(" idStr = " + idStr);
	      log.info(" indexStr = " + indexStr);
	      log.info(" query = " + query);
	    }
	    
	    int signatureIndex = Integer.parseInt(indexStr);

	    try {
	      requestPlugin(request, response, idStr, signatureIndex, query, isPost);
	    } catch (Exception e) {
	      throw new IOException(e.getMessage(), e);
	    }
		
		
	}

	
	protected void requestPlugin(HttpServletRequest request, HttpServletResponse response,
		      String signaturesSetID, int signatureIndex, String query, boolean isPost)
		      throws Exception {

	    String absoluteRequestPluginBasePath = getAbsoluteRequestPluginBasePath(request,
	        CONTEXTWEB, signaturesSetID, signatureIndex);
	    String relativeRequestPluginBasePath = getRelativeRequestPluginBasePath(request,
	        CONTEXTWEB, signaturesSetID, signatureIndex);

	    
	    SistraSignaturesSet ss = (SistraSignaturesSet) SignatureSetStore.load(signaturesSetID);
	    
	    log.info("requestPlugin()::ExempleSignaturesSet ss = " + ss);
	    
	    if (ss == null) {
	     response.sendError(HttpServletResponse.SC_REQUEST_URI_TOO_LONG, 
	         "Proces de Firma amb ID " + signaturesSetID + " ha caducat !!!!");
	     return;
	   }
	   
	   
	    
	    long pluginID = ss.getPluginID();
	    
	    log.info(" ExempleSignaturesSet pluginID = ss.getPluginID(); =>  " + pluginID);
	    
	    ISignatureWebPlugin signaturePlugin;
	    try {
	      signaturePlugin = SignatureWebPluginManager.getInstance().getInstanceByPluginID(pluginID);
	    } catch (Exception e) {
	      
	      String msg = "plugin.signatureweb.noexist: " +String.valueOf(pluginID);
	      throw new Exception(msg);
	    }
	    if (signaturePlugin == null) {
	      String msg = "plugin.signatureweb.noexist: " + String.valueOf(pluginID);
	      throw new Exception(msg);
	    }
	    
	    if (isPost) {
	      signaturePlugin.requestPOST(absoluteRequestPluginBasePath,
	          relativeRequestPluginBasePath, query, 
	          signaturesSetID, signatureIndex, request, response);
	    } else {
	      signaturePlugin.requestGET(absoluteRequestPluginBasePath,
	          relativeRequestPluginBasePath, query,
	          signaturesSetID, signatureIndex, request, response);
	    }

	    
	    

	  }
	
	
	// ------ Obtener urls  
	 protected static String getAbsoluteRequestPluginBasePath(HttpServletRequest request,
		      String webContext, String signaturesSetID, int signatureIndex) {

		    String base = getAbsoluteControllerBase(request, webContext);
		    return getRequestPluginBasePath(base, signaturesSetID, signatureIndex);
		  }

		  public static String getRelativeRequestPluginBasePath(HttpServletRequest request,
		      String webContext, String signaturesSetID, int signatureIndex) {

		    String base = getRelativeControllerBase(request, webContext);
		    return getRequestPluginBasePath(base, signaturesSetID, signatureIndex);
		  }

		  private static String getRequestPluginBasePath(String base, String signaturesSetID,
		      int signatureIndex) {
		    String absoluteRequestPluginBasePath = base + "/requestPlugin/" + signaturesSetID + "/"
		        + signatureIndex;

		    return absoluteRequestPluginBasePath;
		  }
		  
		  public static String getRelativeURLBase(HttpServletRequest request) {
			    return request.getContextPath();
			  }

			  protected static String getAbsoluteControllerBase(HttpServletRequest request,
			      String webContext) {
			    return getAbsoluteURLBase(request) + webContext;
			  }

			  public static String getRelativeControllerBase(HttpServletRequest request, String webContext) {
			    return getRelativeURLBase(request) + webContext;
			  }
	
			  protected static String getAbsoluteURLBase(HttpServletRequest request) {

				    return request.getScheme() + "://" + request.getServerName() + ":"
				        + +request.getServerPort() + request.getContextPath();

				  }
}
