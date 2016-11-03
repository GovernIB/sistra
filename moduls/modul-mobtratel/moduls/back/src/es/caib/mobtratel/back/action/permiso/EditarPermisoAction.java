package es.caib.mobtratel.back.action.permiso;


import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import es.caib.mobtratel.back.action.BaseAction;
import es.caib.mobtratel.back.form.PermisoForm;
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.model.Permiso;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Permiso para enviar mensajes.
 *
 * @struts.action
 *  name="permisoForm"
 *  scope="session"
 *  validate="true"
 *  input=".permiso.editar"
 *  path="/back/permiso/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".permiso.editar"
 *
 * @struts.action-forward
 *  name="success" path=".permiso.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".permiso.lista"
 *
 */
public class EditarPermisoAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarPermisoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        
    	request.setAttribute( "idReadOnly", new Boolean( true ) );
    	log.info("Entramos en EditarPermiso");

        
    	
        PermisoDelegate permisoDelegate = DelegateUtil.getPermisoDelegate();
        PermisoForm permisoForm = (PermisoForm) form;
        
        Permiso permiso = (Permiso) permisoForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            return mapping.findForward("cancel");
        }
        
        if (isAlta(request) || isModificacion(request)) {
            log.info("isAlta || isModificacio");
            // TODO
            
            String[] cuentas = permisoForm.getCuentas();
            Cuenta cuenta = new Cuenta();
            // Solo devolverá una cuenta
            cuenta.setCodigo(cuentas[0]);
            permiso.setCuenta(cuenta);
            Long codigo = permisoDelegate.grabarPermiso(permiso);
                        
            log.info("Creat/Actualitzat " + permiso.getUsuarioSeycon());
            guardarPermiso(mapping, request, codigo);            

            return mapping.findForward("success");
        }
        
        

        return mapping.findForward("reload");
    }

}
