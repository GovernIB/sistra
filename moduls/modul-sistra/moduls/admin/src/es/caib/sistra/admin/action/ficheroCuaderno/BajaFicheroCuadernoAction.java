package es.caib.sistra.admin.action.ficheroCuaderno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.persistence.delegate.FicheroCuadernoDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;

/**
 * Action para preparar borrar un FicheroCuaderno.
 *
 * @struts.action
 *  name="ficherocuadernoForm"
 *  scope="request"
 *  validate="false"
 *  path="/admin/ficheroCuaderno/baja"
 *
 * @struts.action-forward
 *  name="success" path="/admin/cuadernoCarga/seleccion.do"
 *  
 * @struts.action-forward
 * name="fail" path="/error.jsp"

 */
public class BajaFicheroCuadernoAction extends BaseAction
{
protected static Log log = LogFactory.getLog( BajaFicheroCuadernoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		log.info("Entramos en BajaFicheroCuaderno");
        String idString = request.getParameter("codigoFichero");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        FicheroCuadernoDelegate delegate = DelegateUtil.getFicheroCuadernoDelegate();
        delegate.borrarFicheroCuaderno( new Long( idString ) );
        
        
        return mapping.findForward("success");
	}
}
