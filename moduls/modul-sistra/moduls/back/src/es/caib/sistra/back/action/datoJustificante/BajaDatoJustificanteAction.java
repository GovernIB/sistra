package es.caib.sistra.back.action.datoJustificante;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.persistence.delegate.DatoJustificanteDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;

/**
 * Action para preparar borrar una DatoJustificante.
 *
 * @struts.action
 *  name="datoJustificanteForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/datoJustificante/baja"
 *
 * @struts.action-forward
 *  name="success" path=".datoJustificante.lista"
 *  
 * @struts.action-forward
 * name="fail" path=".datoJustificante.lista"
 */
public class BajaDatoJustificanteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaDatoJustificanteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaDatoJustificante");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        DatoJustificanteDelegate datoJustificanteDelegate = DelegateUtil.getDatoJustificanteDelegate();
        Long id = new Long(idString);
        datoJustificanteDelegate.borrarDatoJustificante(id);
        request.setAttribute("reloadMenu", "true");

        String idEspecTramiteNivelString = request.getParameter("idEspecTramiteNivel");
        if (idEspecTramiteNivelString == null || idEspecTramiteNivelString.length() == 0) {
            log.warn("El paràmetre idEspecTramiteNivel és null");
            return mapping.findForward("fail");
        }

        Long idEspecTramiteNivel = new Long( idEspecTramiteNivelString );
        // TODO : Estudiar como se resuelve este problema.
        //guardarEspecTramiteNivel( mapping, request, idEspecTramiteNivel );

        return mapping.findForward("success");
    }

}
