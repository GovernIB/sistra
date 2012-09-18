package es.caib.sistra.admin.action.grupos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GruposDelegate;

/**
 * Action para consultar un Tramite.
 *
 * @struts.action
 *  path="/admin/grupo/desar"
 *  
 *
 * @struts.action-forward
 *  name="success" path=".grupo.editar"
 * 
 *
 * @struts.action-forward
 *  name="fail" path=".grupo.lista"
 */
public class GrupoTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(GrupoTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionGrupo");

        String codigoGrupo = request.getParameter("codigo");
        if (codigoGrupo == null || codigoGrupo.length() == 0) {
            log.warn("El paràmetre codi és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el tramite " + codigoGrupo);

        
        GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
        request.setAttribute("grupo",gruposDelegate.obtenerGrupo(codigoGrupo));

        return mapping.findForward("success");
    }
}
