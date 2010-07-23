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
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.DocumentoFirmar;
import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.front.util.ValorOrganismo;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;

/**
 * @struts.action
 *  name="detalleNotificacionForm"
 *  path="/altaDocumentoNotificacion"
 *  validate="true"
 *  input = ".altaAviso"
 *  
 * @struts.action-forward
 *  name="success" path=".altaNotificacion"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaDocumentoNotificacionAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		ArrayList documentos;
		try{
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
							carregarLlistes(request, notificacionForm.getCodigoProvincia());
							ActionErrors errors = new ActionErrors();
		 					errors.add("altaNotificacion", new ActionError("error.aviso.guardar.fichero"));
		 					saveErrors(request,errors);
		 					return mapping.findForward( "success" );
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
 					ActionErrors errors = new ActionErrors();
 					errors.add("altaNotificacion", new ActionError("error.aviso.extensiones.fichero"));
 					saveErrors(request,errors);
 				}
			}
			carregarLlistes(request, notificacionForm.getCodigoProvincia());
		}catch(Exception e){
			e.printStackTrace();
			String mensajeOk = MensajesUtil.getValue("error.excepcion.general");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
		return mapping.findForward( "success" );
    }
	
	private void carregarLlistes(HttpServletRequest request, String provincia) throws Exception{
		List unidades=Dominios.listarUnidadesAdministrativas();
		request.setAttribute("unidades",unidades);
		List paises = Dominios.listarPaises();
		request.setAttribute("paises",paises);
		List provincias = Dominios.listarProvincias();
		request.setAttribute("provincias",provincias);
		List municipios = new ArrayList();
		if(StringUtils.isNotEmpty(provincia)){
			municipios = Dominios.listarLocalidadesProvincia(provincia);
		}
		request.setAttribute("municipios",municipios);
		RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
        List organosDestino = dlgRte.obtenerServiciosDestino();
        request.setAttribute( "listaorganosdestino", regtelToBantel(organosDestino));
        List oficinasRegistro = dlgRte.obtenerOficinasRegistro();
        request.setAttribute( "listaoficinasregistro", regtelToBantel(oficinasRegistro));
        List tiposAsunto = dlgRte.obtenerTiposAsunto();
        request.setAttribute("tiposAsunto", regtelToBantel(tiposAsunto));
	}
	
	private List regtelToBantel(List lista){
		List listaBantel = new ArrayList();
		if(lista != null){
			for(int i=0;i<lista.size();i++){
				ValorOrganismo vo = new ValorOrganismo();
				vo.setCodigo(((es.caib.regtel.model.ValorOrganismo)lista.get(i)).getCodigo());
				vo.setDescripcion(((es.caib.regtel.model.ValorOrganismo)lista.get(i)).getDescripcion());
				listaBantel.add(vo);
			}
		}
		return listaBantel;
	}
	
}