package org.ibit.rol.form.front.action.anexo;

import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.FileBox;
import org.ibit.rol.form.model.Anexo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prepara los datos para el formulario que recibirà un anexo.
 *
 * @struts.action
 *  name="anexoForm"
 *  path="/addAnexo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="anexoForm"
 *  path="/auth/addAnexo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="anexoForm"
 *  path="/secure/addAnexo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="anexoForm"
 *  path="/auth/secure/addAnexo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward name="success" path=".anexo.ver"
 */
public class AddAnexoAction extends BaseAction {

    protected static Log log = LogFactory.getLog(AddAnexoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        AnexoForm anexoForm = (AnexoForm) form;
        Pantalla pantalla = delegate.obtenerPantalla();
        Campo campo = pantalla.findCampo(anexoForm.getNombre());

        if (campo == null || !(campo instanceof FileBox)) {
            log.error(anexoForm.getNombre() + " no és un filebox de la pantalla actual");
            return mapping.findForward("fail");
        }
        request.setAttribute("campo", campo);

        FormFile aFile = anexoForm.getFichero();
        if (aFile != null) {
            Anexo anexo = new Anexo(aFile.getFileName(), aFile.getContentType(), aFile.getFileData());
            delegate.introducirAnexo(campo.getNombreLogico(), anexo);
            request.setAttribute("anexo", anexo);
        }

        return mapping.findForward("success");
    }

}
