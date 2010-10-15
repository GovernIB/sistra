package es.caib.bantel.front.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;

/**
 * @struts.action
 *  name="detalleAvisoForm"
 *  path="/altaDocumentoAviso"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".altaAviso"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaDocumentoAvisoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleAvisoForm avisoForm = (DetalleAvisoForm)form;
		ArrayList documentos;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		try{
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
							documentRDS = DocumentosUtil.crearDocumentoRDS(documento,avisoForm.getUnidadAdministrativa());
						}catch(Exception e){
							List unidades=Dominios.listarUnidadesAdministrativas();
							request.setAttribute("unidades",unidades);
							ActionErrors errors = new ActionErrors();
		 					errors.add("altaAviso", new ActionError("error.aviso.guardar.fichero"));
		 					saveErrors(request,errors);
		 					return mapping.findForward("success");
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
 					ActionErrors errors = new ActionErrors();
 					errors.add("altaAviso", new ActionError("error.aviso.extensiones.fichero"));
 					saveErrors(request,errors);
 				}
			}
			List unidades=Dominios.listarUnidadesAdministrativas();
			request.setAttribute("unidades",unidades);
		}catch(Exception e){
			e.printStackTrace();
			String mensajeOk = MensajesUtil.getValue("error.excepcion.general");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
		return mapping.findForward( "success" );
    }
	
}