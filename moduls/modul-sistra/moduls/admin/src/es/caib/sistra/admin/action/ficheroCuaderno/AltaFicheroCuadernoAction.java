package es.caib.sistra.admin.action.ficheroCuaderno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.admin.form.FicheroCuadernoForm;
import es.caib.sistra.model.admin.FicheroCuaderno;

/**
 * Action para preparar el alta de un Fichero de un Cuaderno.
 *
 * @struts.action
 * 	name="ficheroCuadernoForm"
 *  path="/admin/ficheroCuaderno/alta"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".ficheroCuaderno.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".cuadernoCarga.editar"
 *
 */
public class AltaFicheroCuadernoAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( AltaFicheroCuadernoAction.class );
	
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
    	FicheroCuadernoForm ficheroCuadernoForm = ( FicheroCuadernoForm ) form;
    	FicheroCuaderno ficheroCuaderno = new FicheroCuaderno();
    	//ficheroCuadernoForm.setCodigo( ficheroCuadernoForm  );
    	request.setAttribute( "ficheroCuaderno", ficheroCuaderno );
    	return mapping.findForward( "success" );
    }
}
