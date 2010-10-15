package es.caib.redose.back.action.ubicacion;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.UbicacionForm;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.UbicacionDelegate;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Ubicacion.
 *
 * @struts.action
 *  name="ubicacionForm"
 *  scope="session"
 *  validate="true"
 *  input=".ubicacion.editar"
 *  path="/back/ubicacion/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".ubicacion.editar"
 *
 * @struts.action-forward
 *  name="success" path=".ubicacion.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".ubicacion.lista"
 *
 */
public class EditarUbicacionAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarUbicacionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarUbicacion");

        UbicacionDelegate ubicacionDelegate = DelegateUtil.getUbicacionDelegate();
        UbicacionForm ubicacionForm = (UbicacionForm) form;
        Ubicacion ubicacion = (Ubicacion) ubicacionForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarVersion") != null) 
        {
            return mapping.findForward("reload");
        } 
        else 
        {
            /*
        	if (archivoValido(ubicacionForm.getPlantilla())) {

            }
            */
        }

        if (isAlta(request) || isModificacion(request)) {
            log.info("isAlta || isModificacio");
            ubicacionDelegate.grabarUbicacion( ubicacion );
            request.setAttribute("reloadMenu", "true");
            log.info("Creat/Actualitzat " + ubicacion.getCodigoUbicacion());

            guardarUbicacion(mapping, request, ubicacion.getCodigo());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }

}
