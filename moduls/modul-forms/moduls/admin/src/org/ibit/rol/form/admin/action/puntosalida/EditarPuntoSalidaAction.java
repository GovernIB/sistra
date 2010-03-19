package org.ibit.rol.form.admin.action.puntosalida;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PuntoSalidaForm;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PuntoSalidaDelegate;
import org.ibit.rol.form.model.PuntoSalida;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Punto Salida
 *
 * @struts.action
 *  name="puntoSalidaForm"
 *  scope="request"
 *  validate="false"
 *  path="/admin/puntosalida/editar"
 *
 * @struts.action-forward
 *  name="success" path=".puntosalida.lista"
 *
 * @struts.action-forward
 *  name="cancel" path=".puntosalida.lista"
 */
public class EditarPuntoSalidaAction extends BaseAction {
    protected static Log log = LogFactory.getLog(EditarPuntoSalidaAction.class);

       public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

           log.debug("Entramos en EditarPuntoSalida");
           PuntoSalidaDelegate puntoSalidaDelegate = DelegateUtil.getPuntoSalidaDelegate();
           PuntoSalidaForm puntoSalidaForm = (PuntoSalidaForm) form;
           PuntoSalida puntoSalida = puntoSalidaForm.getValues();

           if (isCancelled(request)) {
               log.debug("isCancelled");
               return mapping.findForward("cancel");
           }

           if (isAlta(request) || isModificacio(request)) {
               log.debug("isAlta || isModificacio");
               puntoSalidaDelegate.gravarPuntoSalida(puntoSalida);
               log.debug("Creat/Actualitzat " + puntoSalida.getNombre());
               return mapping.findForward("success");
           }

           return mapping.findForward("success");
       }

}
