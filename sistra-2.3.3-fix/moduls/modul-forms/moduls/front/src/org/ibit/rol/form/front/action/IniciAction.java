package org.ibit.rol.form.front.action;

import java.io.InputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;
import org.ibit.rol.form.front.util.InstanciaZipCodec;
import org.ibit.rol.form.front.util.URLUtil;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.InstanciaBean;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.FormularioSeguro;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.front.Constants;

/**
 * @struts.action
 *  name="iniciForm"
 *  path="/inici"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="iniciForm"
 *  path="/auth/inici"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="iniciForm"
 *  path="/secure/inici"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="iniciForm"
 *  path="/auth/secure/inici"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/ver.do" redirect="true"
 */
public class IniciAction extends BaseAction {

    protected static Log log = LogFactory.getLog(IniciAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        IniciForm iniciForm = (IniciForm) form;
        InstanciaDelegate delegate = DelegateUtil.getInstanciaDelegate(true);

        FormFile fichero = iniciForm.getFichero();
        if (fichero == null || fichero.getFileSize() == 0) {
            ActionErrors errors = iniciForm.validate(mapping, request);
            if (errors == null || errors.isEmpty()) {
                if (!testSecurity(request, response, iniciForm.getModelo(), iniciForm.getVersion())) {
                    return null;
                }
                delegate.create(iniciForm.getModelo(), getLocale(request), iniciForm.getPerfil(), iniciForm.getVersion());
            } else {
                saveErrors(request, errors);
                return mapping.findForward("fail");
            }
        } else {
            InputStream inputStream = fichero.getInputStream();
            InstanciaBean bean = InstanciaZipCodec.decodeInstancia(inputStream);
            fichero.destroy();

            if (!testSecurity(request, response, bean.getModelo(), bean.getVersion())) {
                return null;
            }

            delegate.create(bean);
            setLocale(request, bean.getLocale());
        }

        // Se ha inicializado con exito. Registramos la instáncia.
        RegistroManager.registrarInstancia(request, delegate);

        String securePath = getSecurePath(delegate.obtenerFormulario());

        // Inicializamos ayuda
        request.getSession().setAttribute(Constants.AYUDA_ACTIVADA_KEY, "true");
        
        //return mapping.findForward("success");
        response.sendRedirect(prepareRedirectInstanciaURL(request, response, securePath + "/ver.do"));
        response.flushBuffer();
        return null;
    }

    public boolean testSecurity(HttpServletRequest request, HttpServletResponse response, String modelo, int version) throws DelegateException, IOException {
        FormularioDelegate formDelegate = DelegateUtil.getFormularioDelegate();
        Formulario formulario = formDelegate.obtenerFormulario(modelo,version);
        String securePath = getSecurePath(formulario);
        boolean secureForm = formDelegate.esConfidencial(modelo,version);
        if (secureForm && !request.isSecure()) {
            String url = URLUtil.getRedirectUrl(request, response, true, securePath + "/inici.do" );
            response.reset();
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", url);
            return false;
        }
        if (!formDelegate.tieneAcceso(modelo,version)) {
            if (request.getUserPrincipal() != null) {
                if (RegistroManager.tieneInstancias(request)) {
                    response.reset();
                    response.sendError(HttpServletResponse.SC_FORBIDDEN,
                            "El formulario requiere otro usuario y no puede cambiarlo cuando " +
                                    "tiene instancias iniciadas.");
                    return false;
                } else {
                    request.getSession().invalidate();
                    response.reset();
                    request.getSession(true);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader("WWW-Authenticate","BASIC realm=\"FORMS\"");
                    return false;
                }
            }

            String url = URLUtil.getRedirectUrl(request, response, secureForm, securePath + "/inici.do");
            response.reset();
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", url);
            return false;
        }
        return true;
    }

    /**
     * Retorna el prefijo del path necesario para procesar el formulario.
     * @param formulario
     * @return path que debe añadirse despues del contextpath.
     */
    public String getSecurePath(Formulario formulario) {
        String securePath = "";
        if (formulario instanceof FormularioSeguro) {
            FormularioSeguro formSeguro = (FormularioSeguro) formulario;
            if (formSeguro.isRequerirLogin()) {
                securePath += "/auth";
            }
            if (formSeguro.isHttps()) {
                securePath += "/secure";
            }
        }
        return securePath;
    }
}
