package es.caib.sistra.back.action.tramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteDelegate;

/**
 * Action para preparar borrar una Tramite.
 *
 * @struts.action
 *  name="tramiteForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/tramite/baja"
 *
 * @struts.action-forward
 *  name="success" path=".exitoBaja"
 *  
 * @struts.action-forward
 * name="fail" path="/error.jsp"

 */
public class BajaTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaTramite");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        TramiteDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
        Long id = new Long(idString);
        
        Tramite tramite = tramiteDelegate.obtenerTramite( id );
        OrganoResponsable organo = tramite.getOrganoResponsable();
        
        setReloadTree( request, Nodo.IR_A_ORGANO, organo.getCodigo() );
        
        
        tramiteDelegate.borrarTramite(id);
        //request.setAttribute("reloadMenu", "true");

        /*
        String idOrganoString = request.getParameter("idOrgano");
        if (idOrganoString == null || idOrganoString.length() == 0) {
            log.warn("El paràmetre idOrgano és null");
            return mapping.findForward("fail");
        }

        Long idOrgano = new Long(idOrganoString);
        guardarOrgano(mapping, request, idOrgano);
        
        */

        return mapping.findForward("success");
    }

}
