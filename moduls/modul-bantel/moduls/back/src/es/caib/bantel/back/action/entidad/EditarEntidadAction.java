package es.caib.bantel.back.action.entidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.EntidadForm;
import es.caib.bantel.model.Entidad;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.EntidadDelegate;

/**
 * Action para editar entidad.
 *
 * @struts.action
 *  name="entidadForm"
 *  scope="session"
 *  validate="true"
 *  input=".entidad.editar"
 *  path="/back/entidad/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".entidad.editar"
 *
 * @struts.action-forward
 *  name="success" path=".entidad.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".entidad.lista"
 *  
 */
public class EditarEntidadAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarEntidadAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	request.setAttribute( "idReadOnly", "true" );
    	
        log.debug("Entramos en EditarEntidadAction");

        EntidadDelegate dlg = DelegateUtil.getEntidadDelegate();
        EntidadForm entidadForm = (EntidadForm) form;
        Entidad entidad = (Entidad) entidadForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarEntidad") != null) 
        {
            return mapping.findForward("reload");
        }         
        
        
        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");                      
            dlg.grabarEntidad( entidad );
            //request.setAttribute("reloadMenu", "true");
            log.debug("Creat/Actualitzat " + entidad.getIdentificador());

            guardarEntidad(mapping, request, entidad.getIdentificador());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }       

}
