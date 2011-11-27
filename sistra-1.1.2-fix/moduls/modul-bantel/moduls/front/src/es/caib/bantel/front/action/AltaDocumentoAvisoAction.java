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
import org.jfree.util.Log;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;

/**
 * @struts.action
 *  name="uploadAvisoForm"
 *  path="/altaDocumentoAviso"
 *  validate="false"
 */
public class AltaDocumentoAvisoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleAvisoForm avisoForm = (DetalleAvisoForm)form;
		ArrayList documentos;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		String funcion;
		
		// Recuperamos de sesion el expediente actual
		String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
		Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
		String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
		
		try{
			funcion = "parent.fileUploaded()";
 			if (avisoForm.getDocumentoAnexoFichero() != null && StringUtils.isNotEmpty(avisoForm.getDocumentoAnexoFichero().getFileName()) &&  StringUtils.isNotEmpty(avisoForm.getDocumentoAnexoTitulo()) ){
 				if(DocumentosUtil.extensionCorrecta(avisoForm.getDocumentoAnexoFichero().getFileName())){
	 				if("documento".equals(avisoForm.getFlagValidacion())){
	 					DocumentoFirmar documento = new DocumentoFirmar();
						documento.setTitulo(avisoForm.getDocumentoAnexoTitulo());
						documento.setContenidoFichero(avisoForm.getDocumentoAnexoFichero().getFileData());
						documento.setNombre(avisoForm.getDocumentoAnexoFichero().getFileName());
						documento.setRutaFichero(DocumentosUtil.formatearFichero(avisoForm.getRutaFitxer()));
	
						//Guardo un documento con la extension que tiene y me devueve el documento con el contenido en pdf
						DocumentoRDS documentRDS = null;
						try{
							documentRDS = DocumentosUtil.crearDocumentoRDS(documento, uniAdm.toString());
						}catch(Exception e){
							Log.error("Error creando documento rds",e);
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
						if(request.getSession().getAttribute("documentosAltaAviso") == null){
							documentos = new ArrayList();
						}else{
							documentos = (ArrayList)request.getSession().getAttribute("documentosAltaAviso");
						}
						documentos.add(documento);
						request.getSession().setAttribute("documentosAltaAviso", documentos);
						avisoForm.setFlagValidacion("");
	 				}
	 				avisoForm.setDocumentoAnexoFichero(null);
	 				avisoForm.setDocumentoAnexoTitulo("");
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
		
		// Devolvemos resultado
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