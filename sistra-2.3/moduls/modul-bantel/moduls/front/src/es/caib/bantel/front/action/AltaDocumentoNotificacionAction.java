package es.caib.bantel.front.action;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

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
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.xml.ConstantesXML;
import es.caib.xml.documentoExternoNotificacion.factoria.FactoriaObjetosXMLDocumentoExternoNotificacion;
import es.caib.xml.documentoExternoNotificacion.factoria.ServicioDocumentoExternoNotificacionXML;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.DocumentoExternoNotificacion;
import es.indra.util.pdf.UtilPDF;

/**
 * @struts.action
 *  name="uploadNotificacionForm"
 *  path="/altaDocumentoNotificacion"
 *  validate="false"
 */
public class AltaDocumentoNotificacionAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		
		
		// Recuperamos de sesion el expediente actual
		String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
		Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
		String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
		
		// Obtenemos documentos almacenados en sesion, sino existe los creamos
		ArrayList documentos;
		if(request.getSession().getAttribute("documentosAltaNotificacion") == null){
			documentos = new ArrayList();
			request.getSession().setAttribute("documentosAltaNotificacion", documentos);
		}else{
			documentos = (ArrayList)request.getSession().getAttribute("documentosAltaNotificacion");
		}

 
		// Si se produce un error se indicara el literal a mostrar
		DocumentoFirmar documento = null;
		try{			
			if ("URL".equals(notificacionForm.getTipoDocumento())) {
				documento = altaDocumentoUrl(notificacionForm, uniAdm);
			} else {
				documento = altaDocumentoFichero(notificacionForm, uniAdm);
			}						
		}catch(Exception ex){
			Log.error("Error creando documento notificacion",ex);				
		}
		
		// Reseteamos valores
		notificacionForm.setTipoDocumento(null);
		notificacionForm.setDocumentoAnexoOficio(null);
		notificacionForm.setTituloAnexoOficio("");
		notificacionForm.setUrlAnexoOficio(null);
			
		
		// Si ha ido correcto añadimos a lista documentos
		// Si se ha producido error mostramos error
		String funcion;
		if (documento == null) {
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			String msgError = resources.getMessage( getLocale( request ), "error.excepcion.general");
 			funcion="parent.errorFileUploaded(\""+msgError+"\")";
		} else {
			documentos.add(documento);
			funcion = "parent.fileUploaded()";
		}
		
		// Devolvemos respuesta
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

	private DocumentoFirmar altaDocumentoUrl(DetalleNotificacionForm notificacionForm,
			Long uniAdm) throws Exception {
		
		if (StringUtils.isEmpty(notificacionForm.getTituloAnexoOficio()) ||
			StringUtils.isEmpty(notificacionForm.getUrlAnexoOficio())){
			Log.error("Faltan datos referencia");
			return null;
		}
		
		if (!notificacionForm.getUrlAnexoOficio().toLowerCase().startsWith("http://") && 
				!notificacionForm.getUrlAnexoOficio().toLowerCase().startsWith("https://")) {
			notificacionForm.setUrlAnexoOficio("http://" + notificacionForm.getUrlAnexoOficio());
		}
			
		// Generamos xml		
		FactoriaObjetosXMLDocumentoExternoNotificacion factoria = ServicioDocumentoExternoNotificacionXML.crearFactoriaObjetosXML();
		factoria.setEncoding("UTF-8");
		DocumentoExternoNotificacion documentoExternoNotificacion = factoria.crearDocumentoExternoNotificacion();		
		factoria.setIndentacion(true);
		documentoExternoNotificacion.setNombre(notificacionForm.getTituloAnexoOficio());
		documentoExternoNotificacion.setUrl(notificacionForm.getUrlAnexoOficio());
		String xml = factoria.guardarDocumentoExternoNotificacion(documentoExternoNotificacion);
		
		// Creamos documento
		DocumentoFirmar documento = new DocumentoFirmar();
		documento.setTipoDocumento("URL");		
		documento.setUrl(notificacionForm.getUrlAnexoOficio());
		documento.setTitulo(notificacionForm.getTituloAnexoOficio());
		documento.setContenidoFichero(xml.getBytes(ConstantesXML.ENCODING));
		documento.setNombre("enlace.xml");
		documento.setRutaFichero(null);

		// Guardo un documento con la extension que tiene y me devueve el documento con el contenido en pdf
		DocumentoRDS documentRDS = null;
		documentRDS = DocumentosUtil.crearDocumentoRDS(documento,uniAdm.toString(), false);
		
		documento.setTitulo(documentRDS.getTitulo());
		documento.setContenidoFichero(null);
		documento.setNombre(documentRDS.getNombreFichero());
		documento.setClaveRDS(documentRDS.getReferenciaRDS().getClave());
		documento.setCodigoRDS(documentRDS.getReferenciaRDS().getCodigo());
		documento.setModeloRDS(documentRDS.getModelo());
		documento.setVersionRDS(documentRDS.getVersion());
		documento.setVistoPDF(false);
		documento.setFirmar(false);
		
		return documento;
	}

	private DocumentoFirmar altaDocumentoFichero(
			DetalleNotificacionForm notificacionForm,
			Long uniAdm) throws Exception {
		boolean convertirPDF = true;
		boolean firmable = true;
		
		if (notificacionForm.getDocumentoAnexoOficio() == null ||
				StringUtils.isEmpty(notificacionForm.getDocumentoAnexoOficio().getFileName()) ||
				StringUtils.isEmpty(notificacionForm.getTituloAnexoOficio())){
			Log.error("Faltan datos fichero");
			return null;
		}
		
		// Verificamos si se debe convertir a PDF-A	
		if  ("PDF".equalsIgnoreCase(DocumentosUtil.getExtension(notificacionForm.getDocumentoAnexoOficio().getFileName()))) {
			convertirPDF = false;
			// En caso de ser PDF, solo sera firmable si es PDF-A
			if (!UtilPDF.isPdfA( new ByteArrayInputStream(notificacionForm.getDocumentoAnexoOficio().getFileData()))) {
				firmable = false;
			}			
		} else {
			// Si no es PDF, miramos si se puede convertir
			if(!DocumentosUtil.extensionCorrecta(notificacionForm.getDocumentoAnexoOficio().getFileName())){
				Log.error("Extension no es correcta");
				return null;
			}	
		}
			
		DocumentoFirmar documento = new DocumentoFirmar();
		documento.setTipoDocumento("FICHERO");
		documento.setTitulo(notificacionForm.getTituloAnexoOficio());
		documento.setContenidoFichero(notificacionForm.getDocumentoAnexoOficio().getFileData());
		documento.setNombre(notificacionForm.getDocumentoAnexoOficio().getFileName());
		documento.setRutaFichero(DocumentosUtil.formatearFichero(notificacionForm.getRutaFitxer()));
		documento.setFirmable(firmable);

		//Guardo un documento con la extension que tiene y me devueve el documento con el contenido en pdf
		DocumentoRDS documentRDS = null;
		documentRDS = DocumentosUtil.crearDocumentoRDS(documento,uniAdm.toString(), convertirPDF);
		
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