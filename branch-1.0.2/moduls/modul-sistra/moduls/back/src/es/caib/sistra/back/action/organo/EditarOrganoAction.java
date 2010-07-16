package es.caib.sistra.back.action.organo;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.OrganoForm;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.OrganoResponsableDelegate;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Organo.
 *
 * @struts.action
 *  name="organoForm"
 *  scope="session"
 *  validate="true"
 *  input=".organo.editar"
 *  path="/back/organo/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".organo.editar"
 *
 * @struts.action-forward
 *  name="success" path=".organo.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".organo.lista"
 *
 */
public class EditarOrganoAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarOrganoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarOrgano");

        OrganoResponsableDelegate organoDelegate = DelegateUtil.getOrganoResponsableDelegate();
        OrganoForm organoForm = (OrganoForm) form;
        OrganoResponsable organo = (OrganoResponsable) organoForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarTramite") != null) 
        {
            return mapping.findForward("reload");
        } 
        
        if (isAlta(request) || isModificacion(request)) {
            log.info("isAlta || isModificacio");
            organoDelegate.grabarOrganoResponsable( organo );
            //request.setAttribute("reloadMenu", "true");
            log.info("Creat/Actualitzat " + organo.getCodigo());

            guardarOrgano(mapping, request, organo.getCodigo());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }

}
