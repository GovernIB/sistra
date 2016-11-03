package es.caib.sistra.back.action.gestorFormularios;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DominioForm;
import es.caib.sistra.back.form.GestorFormulariosForm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Dominio.
 *
 * @struts.action
 *  path="/back/gestorFormularios/alta"
 *
 * @struts.action-forward
 *  name="success" path=".gestorFormularios.editar"
 */
public class AltaGestorFormulariosAction extends BaseAction {
	
	 protected static Log log = LogFactory.getLog( AltaGestorFormulariosAction.class );
	
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	
    	GestorFormulariosForm fForm = (GestorFormulariosForm) obtenerActionForm(mapping,request, "/back/gestorFormularios/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
        
    }

}
