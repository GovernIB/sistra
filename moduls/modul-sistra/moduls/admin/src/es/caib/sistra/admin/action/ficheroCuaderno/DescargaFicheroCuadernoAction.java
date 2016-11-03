package es.caib.sistra.admin.action.ficheroCuaderno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.Constants;
import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.model.admin.FicheroCuaderno;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.FicheroCuadernoDelegate;

/**
 * Action para consultar un CuadernoCarga.
 *
 * @struts.action
 *  name="ficheroCuadernoForm"
 *  path="/admin/ficheroCuaderno/descarga"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/admin/download.do"
 *
 * @struts.action-forward
 *  name="fail" path=".cuadernoCarga.editar"
 *
 */
public class DescargaFicheroCuadernoAction extends BaseAction
{
	private static Log log = LogFactory.getLog( DescargaFicheroCuadernoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
	    String idString = request.getParameter("codigoFichero");
	    if (idString == null || idString.length() == 0) {
	        log.warn("El paràmetre codigo és null!!");
	        return mapping.findForward("fail");
	    }
	    Long id = new Long(idString);
	    
	    FicheroCuadernoDelegate delegate = DelegateUtil.getFicheroCuadernoDelegate();
	    FicheroCuaderno ficheroCuaderno = delegate.obtenerFicheroCuaderno( id );
	    
		request.setAttribute( Constants.NOMBREFICHERO_KEY, ficheroCuaderno.getNombre() );
		request.setAttribute( Constants.DATOSFICHERO_KEY, ficheroCuaderno.getContenido());
	    
	    return mapping.findForward( "success" );
    }
}
