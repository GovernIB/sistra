package es.caib.zonaper.front.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.util.ConvertUtil;
import es.caib.zonaper.front.form.FirmarDocumentoDelegadoForm;
import es.caib.zonaper.front.util.DocumentoPersistenteFront;
import es.caib.zonaper.front.util.FirmanteFront;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;

/**
 * @struts.action
 *  name="firmarDocumentoDelegadoForm"
 *	path="/protected/irFirmarDocumento"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 * name="success" path=".irFirmarDocumento"
 * 
 * @struts.action-forward
 *  name="fail" path=".bandejaFirma"
 */
public class IrFirmarDocumentoAction extends BaseAction
{
	private static Log log = LogFactory.getLog( IrFirmarDocumentoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String identificador = request.getParameter("identificador");
		
		try{
			FirmarDocumentoDelegadoForm firmarForm = (FirmarDocumentoDelegadoForm)form;
			//Buscamos Documentos pendientes de firma de la entidad delegante
			TramitePersistenteDelegate delegate = DelegateUtil.getTramitePersistenteDelegate();
			DocumentoPersistenteFront doc = docPerToDocFront(delegate.obtenerDocumentoTramitePersistente(new Long(identificador)));
			ReferenciaRDS ref = new ReferenciaRDS();
			ref.setClave(doc.getRdsClave());
			ref.setCodigo(doc.getRdsCodigo());
			DocumentoRDS docRDS = DelegateRDSUtil.getRdsDelegate().consultarDocumento(ref);
			doc.setDescripcionDocumento(docRDS.getTitulo());
			request.setAttribute("documentoParaFirmar",doc);
			firmarForm.setDocumentoB64(ConvertUtil.bytesToBase64UrlSafe(docRDS.getDatosFichero()));
			firmarForm.setIdentificador(doc.getCodigo()+"");
		}catch(Exception e){
			ActionErrors messages = new ActionErrors();
			messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.irFirmar.documento.Firma"));
        	saveErrors(request,messages);
        	return mapping.findForward("fail");
		}
		return mapping.findForward("success");
    }
	
	private DocumentoPersistenteFront docPerToDocFront(DocumentoPersistente doc) throws Exception{
		DocumentoPersistenteFront docFront = new DocumentoPersistenteFront();
		if(doc != null){
			docFront.setCodigo(doc.getCodigo());
			docFront.setDelegacionEstado(doc.getDelegacionEstado());
			docFront.setDelegacionFirmantes(doc.getDelegacionFirmantes());
			docFront.setDelegacionFirmantesPendientes(doc.getDelegacionFirmantesPendientes());
			docFront.setDescripcionGenerico(doc.getDescripcionGenerico());
			docFront.setEstado(doc.getEstado());
			docFront.setIdentificador(doc.getIdentificador());
			docFront.setNombreFicheroAnexo(doc.getNombreFicheroAnexo());
			docFront.setNumeroInstancia(doc.getNumeroInstancia());
			docFront.setRdsClave(doc.getRdsClave());
			docFront.setRdsCodigo(doc.getRdsCodigo());
			docFront.setTramitePersistente(doc.getTramitePersistente());
			
			List firmantes = new ArrayList();  
			String[] firmantesDel;
			if(docFront.getDelegacionFirmantes()!= null && StringUtils.isNotBlank(docFront.getDelegacionFirmantes())){
				firmantesDel = docFront.getDelegacionFirmantes().split("#");
				ConsultaPADDelegate deleg = DelegateUtil.getConsultaPADDelegate();
				try {
					for(int i=0;i<firmantesDel.length;i++){
						FirmanteFront firmante = new FirmanteFront();
						firmante.setNombre(deleg.obtenerDatosPADporNif(firmantesDel[i]).getNombreCompleto());
						firmante.setHaFirmado(!docFront.getDelegacionFirmantesPendientes().contains(firmantesDel[i]));
						firmantes.add(firmante);
					}
				} catch (DelegateException e) {
					throw new Exception("No existe el nif del firmante");
				}
			}
			docFront.setDelegacionFirmantesHTML(firmantes);
		}
		return docFront;
	}
}