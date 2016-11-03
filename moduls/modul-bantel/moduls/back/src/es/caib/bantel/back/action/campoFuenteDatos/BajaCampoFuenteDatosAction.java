package es.caib.bantel.back.action.campoFuenteDatos;

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
 * Action para borrar campo fuente datos.
 *
 * @struts.action
 *  name="campoFuenteDatosForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/campoFuenteDatos/baja"
 *
 * @struts.action-forward
 *  name="success" path=".fuenteDatos.editar"
 */
public class BajaCampoFuenteDatosAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaCampoFuenteDatosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        
        // Codigo: idfuentedatos#idcampo
        String[] ids = idString.split("#");
        
        FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
        delegate.borrarCampoFuenteDatos(ids[0], ids[1]);
        request.setAttribute("reloadMenu", "true");

        guardarFuenteDatos(mapping, request, ids[0]);
        
        return mapping.findForward("success");
    }

}
