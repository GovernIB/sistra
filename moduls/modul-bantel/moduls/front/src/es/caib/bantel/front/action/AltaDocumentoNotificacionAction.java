package es.caib.bantel.front.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;

/**
 * @struts.action
 *  name="uploadNotificacionForm"
 *  path="/altaDocumentoNotificacion"
 *  validate="true"
 */
public class AltaDocumentoNotificacionAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		ArrayList documentos;
		String funcion;
		try{
			funcion = "parent.fileUploaded()";
 			if (notificacionForm.getDocumentoAnexoOficio() != null && StringUtils.isNotEmpty(notificacionForm.getDocumentoAnexoOficio().getFileName()) &&  StringUtils.isNotEmpty(notificacionForm.getTituloAnexoOficio())){
 				if(DocumentosUtil.extensionCorrecta(notificacionForm.getDocumentoAnexoOficio().getFileName())){
	 				//if(DocumentosUtil.noExisteDocumento(notificacionForm.getTituloAnexoOficio(),request,"documentosAltaNotificacion")){
	 					DocumentoFirmar documento = new DocumentoFirmar();
	 					documento.setTitulo(notificacionForm.getTituloAnexoOficio());
	 					documento.setContenidoFichero(notificacionForm.getDocumentoAnexoOficio().getFileData());
	 					documento.setNombre(notificacionForm.getDocumentoAnexoOficio().getFileName());
						documento.setRutaFichero(DocumentosUtil.formatearFichero(notificacionForm.getRutaFitxer()));
	
						//Guardo un documento con la extension que tiene y me devueve el documento con el contenido en pdf
						DocumentoRDS documentRDS = null;
						try{
							documentRDS = DocumentosUtil.crearDocumentoRDS(documento,notificacionForm.getUnidadAdministrativa());
						}catch(Exception e){
							MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
							funcion="parent.errorFileUploaded(\""+resources.getMessage( getLocale( request ), "error.aviso.guardar.fichero")+"\")";
						}
						documento.setTitulo(documentRDS.getTitulo());
						documento.setContenidoFichero(null);
						documento.setNombre(documentRDS.getNombreFichero());
						documento.setClaveRDS(documentRDS.getReferenciaRDS().getClave());
						documento.setCodigoRDS(documentRDS.getReferenciaRDS().getCodigo());
						documento.setModeloRDS(documentRDS.getModelo());
						documento.setVersionRDS(documentRDS.getVersion());
						documento.setVistoPDF(true);
						documento.setFirmar(false);
						if(request.getSession().getAttribute("documentosAltaNotificacion") == null){
							documentos = new ArrayList();
						}else{
							documentos = (ArrayList)request.getSession().getAttribute("documentosAltaNotificacion");
						}
						documentos.add(documento);
						request.getSession().setAttribute("documentosAltaNotificacion", documentos);
	 				//}
					notificacionForm.setDocumentoAnexoOficio(null);
					notificacionForm.setTituloAnexoOficio("");
 				}else{
 					throw new Exception("error.aviso.extensiones.fichero");
 				}
			}
		}catch(Exception ex){
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			if(ex.getMessage() != null && ex.getMessage().startsWith("error.aviso.extensiones.fichero")){
				funcion="parent.errorFileUploaded(\"" + resources.getMessage( getLocale( request ), ex.getMessage()) + "\")";				
			}else{
				funcion="parent.errorFileUploaded(\""+resources.getMessage( getLocale( request ), "error.excepcion.general")+"\")";
			}
		}
		response.setContentType("text/html");		    
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		pw.println("<script type=\"text/javascript\">");
		pw.println("<!--");
		pw.println(funcion);
		pw.println("-->");
		pw.println("</script>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("</body>");
		pw.println("</html>");
		return null;
	}
	
}