package es.caib.bantel.back.action.gestorBandeja;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.GestorBandejaForm;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un GestorBandeja.
 *
 * @struts.action
 *  name="gestorBandejaForm"
 *  scope="session"
 *  validate="true"
 *  input=".gestorBandeja.editar"
 *  path="/back/gestorBandeja/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".gestorBandeja.editar"
 *
 * @struts.action-forward
 *  name="success" path=".gestorBandeja.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".gestorBandeja.lista"
 *
 */
public class EditarGestorBandejaAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarGestorBandejaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        
    	request.setAttribute( "idReadOnly", new Boolean( true ) );
    	log.debug("Entramos en EditarGestorBandeja");

        GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
        GestorBandejaForm gestorBandejaForm = (GestorBandejaForm) form;
        
        GestorBandeja gestorBandeja = (GestorBandeja) gestorBandejaForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }
        
        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");
            gestorBandejaDelegate.grabarGestorBandeja( gestorBandeja, gestorBandejaForm.getTramites() );
                        
            log.debug("Creat/Actualitzat " + gestorBandeja.getSeyconID());
            guardarGestorBandeja(mapping, request, gestorBandeja.getSeyconID());            

            return mapping.findForward("success");
        }

        return mapping.findForward("reload");
    }

}
