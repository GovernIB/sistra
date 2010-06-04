package org.ibit.rol.form.admin.action.puntosalida;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PuntoSalidaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Action para preparar borrar un PuntoSalida
*
* @struts.action
*  path="/admin/puntosalida/baja"
*
* @struts.action-forward
*  name="success" path=".puntosalida.lista"
*
* @struts.action-forward
*  name="fail" path=".puntosalida.lista"
*/
public class BajaPuntoSalidaAction extends BaseAction {
 protected static Log log = LogFactory.getLog(BajaPuntoSalidaAction.class);

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

       log.debug("Entramos en BajaPuntoSalida");
       PuntoSalidaDelegate puntoSalidaDelegate = DelegateUtil.getPuntoSalidaDelegate();

       String idString = request.getParameter("id");
       if (idString == null || idString.length() == 0) {
           log.warn("El paràmetre id és null!!");
           return mapping.findForward("fail");
       }

       log.debug("Borrando el punto salida " + idString);
       Long id = new Long(idString);
       puntoSalidaDelegate.borrarPuntoSalida(id);
       return mapping.findForward("success");
   }
}
