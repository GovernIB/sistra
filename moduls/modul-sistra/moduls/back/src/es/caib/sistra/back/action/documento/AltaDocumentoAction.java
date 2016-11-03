package es.caib.sistra.back.action.documento;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DocumentoForm;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una Documento.
 *
 * @struts.action
 *  path="/back/documento/alta"
 *
 * @struts.action-forward
 *  name="success" path=".documento.editar"
 * 
 */
public class AltaDocumentoAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaDocumentoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	ActionMessages messages = new ActionMessages();
    	
        String idTramiteVersionString = request.getParameter("idTramiteVersion");
        if (idTramiteVersionString == null || idTramiteVersionString.length() == 0) {
            log.debug("idTramiteVersion es null");
            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.parametrosIncorrectos"));
            this.saveMessages(request,messages);
            return mapping.findForward("mensaje");
        }

        Long idTramiteVersion = new Long(idTramiteVersionString);

        // Comprobamos bloqueo
        TramiteVersionDelegate tvd = DelegateUtil.getTramiteVersionDelegate();
        TramiteVersion tv = tvd.obtenerTramiteVersion(idTramiteVersion);
        if (getBloqueado(request,tv.getBloqueadoModificacion(),tv.getBloqueadoModificacionPor()) != null){
        	 log.debug("Version bloqueada");
        	 messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.versionBloqueada"));
        	 this.saveMessages(request,messages);
             return mapping.findForward("mensaje");
        }
        
        DocumentoForm documentoForm = (DocumentoForm) obtenerActionForm(mapping,request, "/back/documento/editar");
        documentoForm.destroy(mapping, request);

        documentoForm.setIdTramiteVersion(idTramiteVersion);
        return mapping.findForward("success");
    }
}
