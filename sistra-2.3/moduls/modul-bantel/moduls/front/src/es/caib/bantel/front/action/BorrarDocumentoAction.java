package es.caib.bantel.front.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.json.JSONObject;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.util.ConvertUtil;

/**
 * @struts.action
 *  path="/borrarDocumento"
 *  validate="true"
 */
public class BorrarDocumentoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		request.setCharacterEncoding("UTF-8");
		ArrayList documentos;
		String nombre = "";
		String atributo="";
		String idioma="";
		boolean trobat = false;
		if(request.getParameter("nombre") != null){
			nombre= request.getParameter("nombre").trim();
		}
		if(request.getParameter("atributo") != null){
			atributo= request.getParameter("atributo").trim();
		}
		if(request.getParameter("idioma") != null){
			idioma= request.getParameter("idioma").trim();
		}
		
		try{
			nombre = ConvertUtil.base64UrlSafeToCadena(nombre);
			if(nombre!=null && !"".equals(nombre.trim()) && atributo!=null && !"".equals(atributo.trim())){
				if(request.getSession().getAttribute(atributo) == null){
					documentos = new ArrayList();
				}else{
					documentos = (ArrayList)request.getSession().getAttribute(atributo);
				}
				for(int i=0;i<documentos.size() && !trobat;i++){
					if(nombre.equals(((DocumentoFirmar)documentos.get(i)).getTitulo())){
						DocumentoFirmar doc = (DocumentoFirmar)documentos.get(i);
						doc.setVistoPDF(true);
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
				JSONObject jsonObject = new JSONObject();		        
				jsonObject.put("taula",json);
				jsonObject.put("base64","");
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
