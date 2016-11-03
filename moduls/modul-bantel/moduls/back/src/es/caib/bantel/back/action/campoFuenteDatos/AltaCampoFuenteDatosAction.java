package es.caib.bantel.back.action.campoFuenteDatos;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.CampoFuenteDatosForm;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de campo fuente datos.
 *
 * @struts.action
 *  path="/back/campoFuenteDatos/alta"
 *
 * @struts.action-forward
 *  name="success" path=".campoFuenteDatos.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".fuenteDatos.lista"
 *
 */
public class AltaCampoFuenteDatosAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaCampoFuenteDatosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idFuenteDatos = request.getParameter("idFuenteDatos");
        if (StringUtils.isBlank(idFuenteDatos)) {
            log.info("idFuenteDatos es null");
            return mapping.findForward("fail");
        }

        CampoFuenteDatosForm campoFuenteDatosForm = (CampoFuenteDatosForm) obtenerActionForm(mapping,request, "/back/campoFuenteDatos/editar");
        campoFuenteDatosForm.destroy(mapping, request);

        campoFuenteDatosForm.setIdFuenteDatos(idFuenteDatos);
        return mapping.findForward("success");
    }
}
