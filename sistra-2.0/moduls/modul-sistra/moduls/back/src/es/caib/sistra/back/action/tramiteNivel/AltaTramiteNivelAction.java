package es.caib.sistra.back.action.tramiteNivel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.TramiteNivelForm;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una TramiteNivel.
 *
 * @struts.action
 *  path="/back/tramiteNivel/alta"
 *
 * @struts.action-forward
 *  name="success" path=".tramiteNivel.editar"
 *
 *
 */
public class AltaTramiteNivelAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaTramiteNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	ActionMessages messages = new ActionMessages();
    	
    	String idTramiteVersionString = request.getParameter("idTramiteVersion");
        if (idTramiteVersionString == null || idTramiteVersionString.length() == 0) {
            log.info("idTramiteVersion es null");
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

        TramiteNivelForm tramiteNivelForm = (TramiteNivelForm) obtenerActionForm(mapping,request, "/back/tramiteNivel/editar");
        tramiteNivelForm.destroy(mapping, request);

        tramiteNivelForm.setIdTramiteVersion(idTramiteVersion);        
        return mapping.findForward("success");
    }
}
