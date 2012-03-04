package es.caib.bantel.front.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.bantel.front.json.JSONObject;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;

/**
 * @struts.action
 *  path="/irBuscarDocumentos"
 *  validate="true"
 */
public class BuscarDocumentos extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		ArrayList documentos;
		boolean error = false;
		String div="";
		try{
			String codigo = request.getParameter("atributo");
			if(request.getSession().getAttribute(codigo) != null){
				documentos = (ArrayList)request.getSession().getAttribute(codigo);
				div = calcularContenidoDocumentos(documentos, request);
			}else{
				error = true;
			}
			JSONObject jsonObject = new JSONObject();		        				
			if (error){
				jsonObject.put("error",MensajesUtil.getValue("aviso.error.no.documentos"));
			}else{
				jsonObject.put("error","");
				jsonObject.put("divDocuments", div);
			}
			populateWithJSON(response,jsonObject.toString());
		}catch(Exception ex){
			JSONObject jsonObject = new JSONObject();		        				
			jsonObject.put("error",MensajesUtil.getValue("error.excepcion.general"));
			populateWithJSON(response,jsonObject.toString());
		}
		return null;
    }
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}
	
	private String calcularContenidoDocumentos(ArrayList documentos, HttpServletRequest request){
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		String contenido = "<ul>";
		for(int i=0;i<documentos.size();i++){
			DocumentoFirmar documento = (DocumentoFirmar)documentos.get(i);
			contenido = contenido+"<li>";
			contenido = contenido+"<a href=\"/bantelfront/abrirDocumento.do?codigo="+documento.getCodigoRDS()+"&clave="+documento.getClaveRDS()+"\" >"+documento.getTitulo()+"</a>";
			contenido = contenido+"<div id=\"infoFirmado-"+documento.getCodigoRDS()+"\" style=\"display:inline;\">";
			if(!documento.isFirmar()){
				contenido = contenido+" - <a class=\"firmar\" onclick=\"mostrarFirmar('"+documento.getTituloB64()+"','"+documento.getCodigoRDS()+"','"+documento.getClaveRDS()+"')\">Firmar</a>";
			}else{
				contenido = contenido+" - <strong>"+resources.getMessage( getLocale( request ), "detalleTramite.datosTramite.envio.firmado")+"</strong>";
			}
			contenido = contenido+"</div>";
			contenido = contenido+"</li>";
		}
		return contenido + "</ul>";
	}
}
