package es.caib.sistra.admin.action.cuadernoCarga;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.persistence.delegate.CuadernoCargaDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;

/**
 * Action para preparar borrar un CuadernoCarga.
 *
 * @struts.action
 *  name="cuadernoCargaForm"
 *  scope="request"
 *  validate="false"
 *  path="/admin/cuadernoCarga/baja"
 *
 * @struts.action-forward
 *  name="success" path=".cuadernoCarga.pendientesDesarrollador"
 *  
 * @struts.action-forward
 * name="fail" path="/error.jsp"

 */
public class BajaCuadernoCargaAction extends BaseAction
{
	protected static Log log = LogFactory.getLog( BajaCuadernoCargaAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		log.info("Entramos en BajaCuadernoCarga");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        CuadernoCargaDelegate delegate = DelegateUtil.getCuadernoCargaDelegate();
        delegate.borrarCuadernoCarga( new Long( idString ) );
        
        
        return mapping.findForward("success");
	}
	
	
	
	
}
