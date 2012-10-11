package org.ibit.rol.form.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 *  
 *  Ir a pantalla detalle desde pantalla principal
 *  
 * @struts.action
 *  name="pantallaForm"
 *  path="/irPantallaDetalle"
 *  scope="request"
 *  validate="false"
 *  input=".verPantalla"
 *  
 *  @struts.action-forward name="success" path="/verDetalle.do"
 *  
 */
public class IrPantallaDetalleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(IrPantallaDetalleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	
    	String detalleAccion = request.getParameter("listaelementos@accion");
    	    	
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }        
        
        // Si la accion es insertar o modificar guardamos los datos actuales de la pantalla        
        if (detalleAccion.equals("insertar") || detalleAccion.equals("modificar")){
        	// Introducimos datos en pantalla actual
            PantallaForm pantallaForm = (PantallaForm) form;
            delegate.introducirDatosPantalla(pantallaForm.getMap());                       
        }
                
        return mapping.findForward("success");        
    }

}
