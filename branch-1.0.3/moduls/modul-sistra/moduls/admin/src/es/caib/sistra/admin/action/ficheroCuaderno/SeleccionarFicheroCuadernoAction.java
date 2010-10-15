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
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.FicheroCuadernoDelegate;

/**
 * Action para consultar un CuadernoCarga.
 *
 * @struts.action
 *  name="ficheroCuadernoForm"
 *  path="/admin/ficheroCuaderno/seleccion"
 *  validate="false"
 *
 *
 * @struts.action-forward
 *  name="success" path=".ficheroCuaderno.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".ficheroCuaderno.lista"
 *
 */
public class SeleccionarFicheroCuadernoAction extends BaseAction
{
	private static Log log = LogFactory.getLog( SeleccionarFicheroCuadernoAction.class );
	 
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
	    String idString = request.getParameter("codigoFichero");
	    if (idString == null || idString.length() == 0) {
	        log.warn("El paràmetre codigo és null!!");
	        return mapping.findForward("fail");
	    }
	    Long id = new Long(idString);
	    
	    FicheroCuadernoForm ficheroCuadernoForm = ( FicheroCuadernoForm ) form;
	    
	    FicheroCuadernoDelegate delegate = DelegateUtil.getFicheroCuadernoDelegate();
	    FicheroCuaderno ficheroCuaderno = delegate.obtenerFicheroCuaderno( id );
	 
	    ficheroCuadernoForm.setCodigoCuaderno( ficheroCuaderno.getCuadernoCarga().getCodigo() );
	    ficheroCuadernoForm.setCodigoFichero( ficheroCuaderno.getCodigo() );
	    
	    request.setAttribute( "ficheroCuaderno", ficheroCuaderno );
	    
	    return mapping.findForward( "success" );
    }
}
