package es.caib.bantel.back.action.fuenteDatos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;

/**
 * Action para preparar borrar fuente datos.
 *
 * @struts.action
 *  name="fuenteDatosForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/fuenteDatos/baja"
 *
 * @struts.action-forward
 *  name="success" path=".fuenteDatos.lista"
 */
public class BajaFuenteDatosAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaFuenteDatosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	FuenteDatosDelegate fuenteDatosDelegate = DelegateUtil.getFuenteDatosDelegate();

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre identificador és null!!");
            return mapping.findForward("fail");
        }
        
       fuenteDatosDelegate.borrarFuenteDatos(idString);

       return mapping.findForward("success");
    }
}
