package es.caib.mobtratel.back.action.cuenta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.back.action.BaseAction;
import es.caib.mobtratel.back.form.CuentaForm;
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;



/**
 * Action para editar una Cuenta.
 *
 * @struts.action
 *  name="cuentaForm"
 *  scope="session"
 *  validate="true"
 *  input=".cuenta.editar"
 *  path="/back/cuenta/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".cuenta.editar"
 *
 * @struts.action-forward
 *  name="success" path=".cuenta.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".cuenta.lista"
 *  
 */
public class EditarCuentaAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarCuentaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	request.setAttribute( "idReadOnly", new Boolean( true ) );
    	
        log.info("Entramos en EditarCuenta");

        CuentaDelegate cuentaDelegate = DelegateUtil.getCuentaDelegate();
        CuentaForm cuentaForm = (CuentaForm) form;
        Cuenta cuenta = (Cuenta) cuentaForm.getValues();

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
            
            cuentaDelegate.grabarCuenta( cuenta );
            
            
            //request.setAttribute("reloadMenu", "true");
            log.info("Creat/Actualitzat " + cuenta.getCodigo());

            guardarCuenta(mapping, request, cuenta.getCodigo());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }
    


}
