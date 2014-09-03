package es.caib.bantel.back.action.campoFuenteDatos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.CampoFuenteDatosForm;
import es.caib.bantel.model.CampoFuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar campo fuente datos.
 *
 * @struts.action
 *  name="campoFuenteDatosForm"
 *  scope="session"
 *  validate="true"
 *  input=".campoFuenteDatos.editar"
 *  path="/back/campoFuenteDatos/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/campoFuenteDatos/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".campoFuenteDatos.editar"
 *
 * @struts.action-forward
 *  name="success" path=".campoFuenteDatos.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".fuenteDatos.editar"
 *
 */
public class EditarCampoFuenteDatosAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarCampoFuenteDatosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
        CampoFuenteDatosForm campoFuenteDatosForm = (CampoFuenteDatosForm) form;
        CampoFuenteDatos campoFuenteDatos = (CampoFuenteDatos) campoFuenteDatosForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            guardarFuenteDatos(mapping, request, campoFuenteDatosForm.getIdFuenteDatos());
            return mapping.findForward("cancel");
        }

        if (isAlta(request) || isModificacion(request)) {
        	if (isAlta(request)) {
        		delegate.altaCampoFuenteDatos(campoFuenteDatosForm.getIdFuenteDatos(), campoFuenteDatos.getIdentificador(), campoFuenteDatos.getEsPK());
        	} else {
        		delegate.modificarCampoFuenteDatos(campoFuenteDatosForm.getIdFuenteDatos(), campoFuenteDatos.getEsPK(), campoFuenteDatosForm.getIdentificadorOld(), campoFuenteDatos.getIdentificador());
        	}
        	guardarCampoFuenteDatos(mapping, request, campoFuenteDatosForm.getIdFuenteDatos(), campoFuenteDatos.getIdentificador());
            request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        // Cambio de idioma
        return mapping.findForward("reload");
    }

}
