package org.ibit.rol.form.back.action.formulario;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar desbloquear un Formulario.
 *
 * @struts.action
 *  path="/back/formulario/desbloquear"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 */

public class  DesbloquearFormularioAction extends BaseAction {
    protected static Log log = LogFactory.getLog(DesbloquearFormularioAction.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en DesbloquearFormulario");
        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        //
        String idString = request.getParameter("id");
        if ( (idString == null) || (idString.length() == 0) ) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }
        if( Boolean.valueOf( DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("habilitar.permisos")).booleanValue()){
    		GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
	        if( !(gruposDelegate.existeUsuarioByGruposForm(request.getUserPrincipal().getName(),new Long(idString)) 
	        	|| gruposDelegate.existeUsuarioByForm(request.getUserPrincipal().getName(),new Long(idString)) 
	        	))
	        {
	        	request.setAttribute("message","No tiene permisos para desbloquear este formulario");
	        	return mapping.findForward("fail");
	        }
        }
        //
        Long id = new Long(idString);
        formularioDelegate.desbloquearFormulario(id);
        //
        return mapping.findForward("success");
    }
}
