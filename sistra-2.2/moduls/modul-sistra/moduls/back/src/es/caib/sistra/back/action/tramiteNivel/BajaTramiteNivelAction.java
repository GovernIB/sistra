package es.caib.sistra.back.action.tramiteNivel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteNivelDelegate;

/**
 * Action para preparar borrar una TramiteNivel.
 *
 * @struts.action
 *  name="tramiteNivelForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/tramiteNivel/baja"
 *
 * @struts.action-forward
 *  name="success" path=".exitoBaja"
 *  
 * @struts.action-forward
 * name="fail" path="/error.jsp"

 */
public class BajaTramiteNivelAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaTramiteNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaTramiteNivel");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        TramiteNivelDelegate tramiteNivelDelegate = DelegateUtil.getTramiteNivelDelegate();
        
        Long id = new Long(idString);
        
        TramiteNivel tramiteNivel = tramiteNivelDelegate.obtenerTramiteNivel( id );
        TramiteVersion tramiteVersion = tramiteNivel.getTramiteVersion();
        
        this.setReloadTree( request, Nodo.IR_A_TRAMITE_VERSION, tramiteVersion.getCodigo() );
        
        tramiteNivelDelegate.borrarTramiteNivel(id);
                
        

        /*
        String idTramiteVersionString = request.getParameter("idTramiteVersion");
        if (idTramiteVersionString == null || idTramiteVersionString.length() == 0) {
            log.warn("El paràmetre idTramiteVersion és null");
            return mapping.findForward("fail");
        }

        Long idTramiteVersion = new Long( idTramiteVersionString );
        guardarTramiteVersion( mapping, request, idTramiteVersion );
        
        */

        return mapping.findForward("success");
    }

}
