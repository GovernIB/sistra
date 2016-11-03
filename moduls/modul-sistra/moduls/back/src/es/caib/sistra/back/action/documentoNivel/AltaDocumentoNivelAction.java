
package es.caib.sistra.back.action.documentoNivel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DocumentoNivelForm;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;

/**
 * Action para preparar el alta de una DocumentoNivel.
 *
 * @struts.action
 *  path="/back/documentoNivel/alta"
 *
 * @struts.action-forward
 *  name="success" path=".documentoNivel.editar"
 *
 *
 */
public class AltaDocumentoNivelAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaDocumentoNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	ActionMessages messages = new ActionMessages();
    	
        String idDocumentoString = request.getParameter("idDocumento");
        if (idDocumentoString == null || idDocumentoString.length() == 0) {
            log.info("idDocumento es null");
            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.parametrosIncorrectos"));
            this.saveMessages(request,messages);
            return mapping.findForward("mensaje");
        }

        Long idDocumento = new Long(idDocumentoString);
        
        // Comprobamos bloqueo
        DocumentoDelegate docd = DelegateUtil.getDocumentoDelegate();
        TramiteVersion tv = docd.obtenerDocumento(idDocumento).getTramiteVersion();        
        if (getBloqueado(request,tv.getBloqueadoModificacion(),tv.getBloqueadoModificacionPor()) != null){
        	 log.debug("Version bloqueada");
        	 messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.versionBloqueada"));
        	 this.saveMessages(request,messages);
             return mapping.findForward("mensaje");
        }

        DocumentoNivelForm documentoNivelForm = (DocumentoNivelForm) obtenerActionForm(mapping,request, "/back/documentoNivel/editar");
        documentoNivelForm.destroy(mapping, request);

        documentoNivelForm.setIdDocumento(idDocumento);
        return mapping.findForward("success");
    }
}
