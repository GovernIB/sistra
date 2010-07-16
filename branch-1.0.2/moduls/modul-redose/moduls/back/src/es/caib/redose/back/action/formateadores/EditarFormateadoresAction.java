package es.caib.redose.back.action.formateadores;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.FormateadoresForm;
import es.caib.redose.back.form.UbicacionForm;
import es.caib.redose.model.Formateador;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.FormateadorDelegate;
import es.caib.redose.persistence.delegate.UbicacionDelegate;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un formateador.
 *
 * @struts.action
 *  name="formateadoresForm"
 *  scope="session"
 *  validate="true"
 *  input=".formateadores.editar"
 *  path="/back/formateadores/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".formateadores.editar"
 *
 * @struts.action-forward
 *  name="success" path=".formateadores.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".formateadores.lista"
 *
 */
public class EditarFormateadoresAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarFormateadoresAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarFormateadores");

        FormateadorDelegate fDelegate = DelegateUtil.getFormateadorDelegate();
        FormateadoresForm fForm = (FormateadoresForm) form;
        Formateador formateador = (Formateador) fForm.getValues();

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
            if(isAlta(request)){
            	fDelegate.grabarFormateadorAlta( formateador );
            }else{
            	fDelegate.grabarFormateadorUpdate( formateador );
            }
            request.setAttribute("reloadMenu", "true");
            log.info("Creat/Actualitzat " + formateador.getIdentificador());

            guardarFormateador(mapping, request, formateador.getIdentificador());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }

}
