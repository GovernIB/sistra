package es.caib.bantel.front.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.json.JSONObject;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.util.ConvertUtil;

/**
 * @struts.action
 *  path="/firmarDocumentoAnexo"
 *  validate="true"
 */

public class FirmarDocumentoAnexoAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(FirmarDocumentoAnexoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		request.setCharacterEncoding("UTF-8");
		ArrayList documentos;
		String nombre = "";
		String atributo="";
		String codigoRDS="";
		String claveRDS="";
		String firmaJSP="";
		DocumentoRDS docRDS = null;
		DocumentoFirmar doc = null;
		boolean trobat = false;
		boolean error = false;
		
		// Recogemos parametros
		if(request.getParameter("nombre") != null){
			nombre= request.getParameter("nombre").trim();
		}
		if(request.getParameter("atributo") != null){
			atributo= request.getParameter("atributo").trim();
		}
		if(request.getParameter("codigoRDS") != null){
			codigoRDS= request.getParameter("codigoRDS").trim();
		}
		if(request.getParameter("claveRDS") != null){
			claveRDS= request.getParameter("claveRDS").trim();
		}
		if(request.getParameter("firma") != null){
			firmaJSP= request.getParameter("firma").trim();
		}
		
		
		try{						
			nombre = ConvertUtil.base64UrlSafeToCadena(nombre);
			if(codigoRDS!=null && !"".equals(codigoRDS.trim()) && atributo!=null && !"".equals(atributo.trim())){
				if(request.getSession().getAttribute(atributo) == null){
					documentos = new ArrayList();
				}else{
					documentos = (ArrayList)request.getSession().getAttribute(atributo);
				}
				RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
				for(int i=0;i<documentos.size() && !trobat;i++){
					if(codigoRDS.equals(((DocumentoFirmar)documentos.get(i)).getCodigoRDS()+"")){
						doc = (DocumentoFirmar)documentos.get(i);
						ReferenciaRDS ref = new ReferenciaRDS(doc.getCodigoRDS(),doc.getClaveRDS());
						docRDS = rdsDelegate.consultarDocumento(ref);
						FirmaIntf firma = null;
						FirmaIntf firmaForm[] = null;
						if ( StringUtils.isNotEmpty(firmaJSP))
						{
							try{
								PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
								firma = plgFirma.parseFirmaFromHtmlForm(firmaJSP);
								
								if (firma == null){
									// Si no hay firma, y estamos firmando pues hay error
									error=true;
									log.error("La firma es null");
								}else{
									rdsDelegate.asociarFirmaDocumento(docRDS.getReferenciaRDS(), firma);
									doc.setFirmar(true);
								}
								
							}catch(Exception e){
								error = true;
								log.error("Error validando la firma",e);								
							}
						}
						trobat = true;
					}
				}
				request.getSession().setAttribute(atributo, documentos);
				
				JSONObject jsonObject = new JSONObject();		        				
				jsonObject.put("base64","");
				if (error){
					jsonObject.put("error",MensajesUtil.getValue("aviso.error.no.firmar", request));
				}else{
					jsonObject.put("error","");
				}
				populateWithJSON(response,jsonObject.toString());
				return null;
			}
		}catch(Exception e){
			log.error("Error asociando firma al anexo",e);
		}
		return null;
    }


	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}

}
