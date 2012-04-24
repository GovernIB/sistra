package es.caib.redose.back.action.modelo;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.ModeloForm;
import es.caib.redose.model.Modelo;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.ModeloDelegate;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Modelo.
 *
 * @struts.action
 *  name="modeloForm"
 *  scope="session"
 *  validate="false"
 *  input=".modelo.editar"
 *  path="/back/modelo/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".modelo.editar"
 *
 * @struts.action-forward
 *  name="success" path=".modelo.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".modelo.lista"
 *
 */
public class EditarModeloAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarModeloAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarModelo");

        ModeloDelegate modeloDelegate = DelegateUtil.getModeloDelegate();
        ModeloForm modeloForm = (ModeloForm) form;
        Modelo modelo = (Modelo) modeloForm.getValues();

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
        	if (archivoValido(modeloForm.getPlantilla())) {

            }
            */
        }

        if (isAlta(request) || isModificacion(request)) {
            log.info("isAlta || isModificacio");
            modeloDelegate.grabarModelo( modelo );
            request.setAttribute("reloadMenu", "true");
            log.info("Creat/Actualitzat " + modelo.getModelo());

            guardarModelo(mapping, request, modelo.getCodigo());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }

}
