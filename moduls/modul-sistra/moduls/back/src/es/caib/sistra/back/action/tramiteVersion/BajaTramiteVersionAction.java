package es.caib.sistra.back.action.tramiteVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

/**
 * Action para preparar borrar una TramiteVersion.
 *
 * @struts.action
 *  name="tramiteVersionForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/tramiteVersion/baja"
 *
 * @struts.action-forward
 *  name="success" path=".exitoBaja"
 *  
 * @struts.action-forward
 * name="fail" path="/error.jsp"

 */
public class BajaTramiteVersionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaTramiteVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaTramiteVersion");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        TramiteVersionDelegate tramiteVersionDelegate = DelegateUtil.getTramiteVersionDelegate();
        Long id = new Long(idString);
        
        TramiteVersion versionTramite = tramiteVersionDelegate.obtenerTramiteVersion( id );
        Tramite tramite = versionTramite.getTramite();
        
        this.setReloadTree( request, Nodo.IR_A_TRAMITE, tramite.getCodigo() );
        
        
        tramiteVersionDelegate.borrarTramiteVersion(id);
        //request.setAttribute("reloadMenu", "true");
        
        /*

        String idTramiteString = request.getParameter("idTramite");
        if (idTramiteString == null || idTramiteString.length() == 0) {
            log.warn("El paràmetre idTramite és null");
            return mapping.findForward("fail");
        }

        Long idTramite = new Long(idTramiteString);
        guardarTramite(mapping, request, idTramite);
        
        */

        return mapping.findForward("success");
    }

}
