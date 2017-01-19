package es.caib.bantel.front.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import es.caib.util.ConvertUtil;

/**
 * @struts.action
 *  path="/irFirmarDocumento"
 *  validate="true"
 */
public class IrFirmarDocumentoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		request.setCharacterEncoding("UTF-8");
		ArrayList documentos;
		String nombre = "";
		String atributo="";
		String codigoRDS="";
		String claveRDS="";
		DocumentoRDS docRDS = null;
		boolean trobat = false;
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
						DocumentoFirmar doc = (DocumentoFirmar)documentos.get(i);
						ReferenciaRDS ref = new ReferenciaRDS(doc.getCodigoRDS(),doc.getClaveRDS());
						docRDS = rdsDelegate.consultarDocumento(ref);
						trobat = true;
					}
				}
				request.getSession().setAttribute(atributo, documentos);
				String json = "";
//				for(int i=0;i<documentos.size();i++){
//					DocumentoFirmar doc = (DocumentoFirmar)documentos.get(i);
//					json = json + "<p>";
//					json = json + "<label> &nbsp;</label> ";
//					json = json + "<span>";
//					json = json + "<a href=\"#\" onclick=\"recargarDiv('"+doc.getTituloB64()+"', '"+doc.getCodigoRDS()+"','"+doc.getClaveRDS()+"');return false;\">"+doc.getTitulo()+"</a>";
//					if(doc.isVistoPDF() && !doc.isFirmar()){
//						json = json + " <img src=\"imgs/icones/envio.gif\" title=\"Firmar\" onclick=\"mostrarFirmar('"+doc.getTituloB64()+"', '"+doc.getCodigoRDS()+"','"+doc.getClaveRDS()+"')\"/>";
//					}else if(doc.isVistoPDF() && doc.isFirmar()){
//						json = json + " <img src=\"imgs/icones/envio_firmado.gif\" title=\"documento Firmado\"/>";
//					}
//					json = json + "</span>";
//					json = json + "</p>";
//				}
				String jsonFirma = "";
				JSONObject jsonObject = new JSONObject();		        
				if(trobat){
					jsonFirma = ConvertUtil.bytesToBase64UrlSafe(docRDS.getDatosFichero());
					jsonObject.put("base64",jsonFirma);
					jsonObject.put("filename",docRDS.getNombreFichero());
					jsonObject.put("error","");
				}else{
					jsonObject.put("base64",jsonFirma);
					jsonObject.put("error",MensajesUtil.getValue("aviso.error.no.documento", request));
				}
				populateWithJSON(response,jsonObject.toString());
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
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
