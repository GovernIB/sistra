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
import es.caib.xml.ConstantesXML;
import es.caib.xml.documentoExternoNotificacion.factoria.FactoriaObjetosXMLDocumentoExternoNotificacion;
import es.caib.xml.documentoExternoNotificacion.factoria.ServicioDocumentoExternoNotificacionXML;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.DocumentoExternoNotificacion;

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
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		
		
		// Recuperamos de sesion el expediente actual
		String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
		Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
		String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
		
		// Recuperamos documentos, si no existen los creamos
		ArrayList documentos;
		if(request.getSession().getAttribute("documentosAltaAviso") == null){
			documentos = new ArrayList();
			request.getSession().setAttribute("documentosAltaAviso", documentos);
		}else{
			documentos = (ArrayList)request.getSession().getAttribute("documentosAltaAviso");
		}
		
		DocumentoFirmar documento = null;
		try{
			if("documento".equals(avisoForm.getFlagValidacion())){
				if (avisoForm.getTipoDocumento().equals("URL")) {
					documento = altaDocumentoUrl(avisoForm, uniAdm);
				} else {
					documento = altaDocumentoFichero(avisoForm, uniAdm);
				}
			}			
		}catch(Exception ex){
			Log.debug("Error alta documento", ex);			
		}	
		
		// Reseteamos valores
		avisoForm.setFlagValidacion("");
		avisoForm.setDocumentoAnexoFichero(null);
		avisoForm.setDocumentoAnexoTitulo("");
		
		// Establecemos funcion resultado
		String funcion;
		if (documento != null) {
			documentos.add(documento);
			funcion = "parent.fileUploaded()";
		} else {
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			funcion="parent.errorFileUploaded(\""+resources.getMessage( getLocale( request ), "error.excepcion.general")+"\")";
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

	private DocumentoFirmar altaDocumentoUrl(DetalleAvisoForm avisoForm,
			Long uniAdm) throws Exception {
		DocumentoFirmar documento;
		
		
		if (StringUtils.isEmpty(avisoForm.getDocumentoAnexoTitulo()) ||
				StringUtils.isEmpty(avisoForm.getDocumentoUrlAnexo())){
				Log.error("Faltan datos referencia");
				return null;
			}
				
		if (!avisoForm.getDocumentoUrlAnexo().toLowerCase().startsWith("http://") && 
				!avisoForm.getDocumentoUrlAnexo().toLowerCase().startsWith("https://")) {
			avisoForm.setDocumentoUrlAnexo("http://" + avisoForm.getDocumentoUrlAnexo());
		}		
		
		// Generamos xml		
		FactoriaObjetosXMLDocumentoExternoNotificacion factoria = ServicioDocumentoExternoNotificacionXML.crearFactoriaObjetosXML();
		factoria.setEncoding("UTF-8");
		DocumentoExternoNotificacion documentoExternoNotificacion = factoria.crearDocumentoExternoNotificacion();		
		factoria.setIndentacion(true);
		documentoExternoNotificacion.setNombre(avisoForm.getDocumentoAnexoTitulo());
		documentoExternoNotificacion.setUrl(avisoForm.getDocumentoUrlAnexo());
		String xml = factoria.guardarDocumentoExternoNotificacion(documentoExternoNotificacion);
		
		documento = new DocumentoFirmar();
		documento.setTipoDocumento("URL");
		documento.setTitulo(avisoForm.getDocumentoAnexoTitulo());
		documento.setContenidoFichero(xml.getBytes(ConstantesXML.ENCODING));
		documento.setNombre("enlace.xml");
		documento.setUrl(avisoForm.getDocumentoUrlAnexo());
		documento.setRutaFichero(null);

		//Guardo un documento con la extension que tiene y me devueve el documento con el contenido en pdf
		DocumentoRDS documentRDS = null;
		documentRDS = DocumentosUtil.crearDocumentoRDS(documento, uniAdm.toString(), false);
		
		documento.setTitulo(documentRDS.getTitulo());
		documento.setContenidoFichero(null);
		documento.setNombre(documentRDS.getNombreFichero());
		documento.setClaveRDS(documentRDS.getReferenciaRDS().getClave());
		documento.setCodigoRDS(documentRDS.getReferenciaRDS().getCodigo());
		documento.setModeloRDS(documentRDS.getModelo());
		documento.setVersionRDS(documentRDS.getVersion());
		documento.setVistoPDF(true);
		documento.setFirmar(false);
		return documento;
	}

	private DocumentoFirmar altaDocumentoFichero(DetalleAvisoForm avisoForm,
			Long uniAdm) throws Exception {
		DocumentoFirmar documento;
		boolean convertirPDF = true;
		if (avisoForm.getDocumentoAnexoFichero() == null ||
				StringUtils.isEmpty(avisoForm.getDocumentoAnexoFichero().getFileName()) ||
				StringUtils.isEmpty(avisoForm.getDocumentoAnexoTitulo()) ){
				return null;
		}
		
		// Si es un PDF comprobamos que sea PDF/A		
		if  ("PDF".equalsIgnoreCase(DocumentosUtil.getExtension(avisoForm.getDocumentoAnexoFichero().getFileName()))) {
			/*
			if (!UtilPDF.isPdfA( new ByteArrayInputStream(avisoForm.getDocumentoAnexoFichero().getFileData()))) {
				Log.error("No es PDF/A");
				return null;
			}
			*/
			convertirPDF = false;
		} else {
			// Si no es PDF, miramos si se puede convertir
			if(!DocumentosUtil.extensionCorrecta(avisoForm.getDocumentoAnexoFichero().getFileName())){
				return null;
			}
		}
		
		documento = new DocumentoFirmar();
		documento.setTipoDocumento("FICHERO");
		documento.setTitulo(avisoForm.getDocumentoAnexoTitulo());
		documento.setContenidoFichero(avisoForm.getDocumentoAnexoFichero().getFileData());
		documento.setNombre(avisoForm.getDocumentoAnexoFichero().getFileName());
		documento.setRutaFichero(DocumentosUtil.formatearFichero(avisoForm.getRutaFitxer()));

		//Guardo un documento con la extension que tiene y me devueve el documento con el contenido en pdf
		DocumentoRDS documentRDS = null;
		documentRDS = DocumentosUtil.crearDocumentoRDS(documento, uniAdm.toString(), convertirPDF);
		
		documento.setTitulo(documentRDS.getTitulo());
		documento.setContenidoFichero(null);
		documento.setNombre(documentRDS.getNombreFichero());
		documento.setClaveRDS(documentRDS.getReferenciaRDS().getClave());
		documento.setCodigoRDS(documentRDS.getReferenciaRDS().getCodigo());
		documento.setModeloRDS(documentRDS.getModelo());
		documento.setVersionRDS(documentRDS.getVersion());
		documento.setVistoPDF(true);
		documento.setFirmar(false);
		return documento;
	}
}