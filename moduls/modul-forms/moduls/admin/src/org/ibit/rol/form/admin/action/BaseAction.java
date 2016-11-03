package org.ibit.rol.form.admin.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.upload.FormFile;
import org.ibit.rol.form.admin.form.ComponenteForm;
import org.ibit.rol.form.admin.form.PaletaForm;
import org.ibit.rol.form.admin.taglib.Constants;
import org.ibit.rol.form.admin.util.ComponenteConfig;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Paleta;
import org.ibit.rol.form.model.Traducible;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PaletaDelegate;

import java.io.IOException;

/**
 * Action con métodos de utilidad.
 */
public abstract class BaseAction extends Action {
	 protected static Log log = LogFactory.getLog(BaseAction.class);

    /** Retorna true si se ha pulsado un botón submit de alta */
    protected boolean isAlta(HttpServletRequest request) {
        return (request.getParameter(Constants.ALTA_PROPERTY) != null);
    }

    /** Retorna true si se ha pulsado un botón submit de baja */
    protected boolean isBaixa(HttpServletRequest request) {
        return (request.getParameter(Constants.BAIXA_PROPERTY) != null);
    }

    /** Retorna true si se ha pulsado un botón submit de modificación */
    protected boolean isModificacio(HttpServletRequest request) {
        return (request.getParameter(Constants.MODIFICACIO_PROPERTY) != null);
    }

    protected boolean archivoValido(final FormFile formFile) {
        return (formFile != null && formFile.getFileSize() > 0);
    }

    protected static Archivo populateArchivo(Archivo archivo, FormFile formFile) throws IOException {
        if (archivo == null) archivo = new Archivo();
        archivo.setTipoMime(formFile.getContentType());
        archivo.setNombre(formFile.getFileName());
        archivo.setPesoBytes(formFile.getFileSize());
        archivo.setDatos(formFile.getFileData());
        return archivo;
    }

    /** Retorna el ActionForm asociado al action en el path especificado */
    protected ActionForm obtenerActionForm(ActionMapping mapping, HttpServletRequest request, String path) {
        ModuleConfig config = mapping.getModuleConfig();
        ActionMapping newMapping = (ActionMapping) config.findActionConfig(path);
        ActionForm newForm = RequestUtils.createActionForm(request, newMapping, config, this.servlet);
        if ("session".equals(newMapping.getScope())) {
            request.getSession(true).setAttribute(newMapping.getAttribute(), newForm);
        } else {
            request.setAttribute(newMapping.getAttribute(), newForm);
        }
        newForm.reset(newMapping, request);
        return newForm;
    }

    protected Paleta guardarPaleta(ActionMapping mapping, HttpServletRequest request, Long idPaleta)
            throws DelegateException {
        PaletaForm pForm = (PaletaForm) obtenerActionForm(mapping, request, "/admin/paleta/editar");

        PaletaDelegate delegate = DelegateUtil.getPaletaDelegate();
        Paleta paleta = delegate.obtenerPaleta(idPaleta);
        pForm.setValues(paleta);

        request.setAttribute("idPaleta", idPaleta);

        return paleta;
    }

    protected Componente guardarComponente(ActionMapping mapping, HttpServletRequest request, Long idComponente)
            throws DelegateException {
        ComponenteDelegate delegate = DelegateUtil.getComponenteDelegate();
        Componente componente = delegate.obtenerComponente(idComponente);

        String path = ComponenteConfig.getMapping(componente);
        ComponenteForm componenteForm = (ComponenteForm) obtenerActionForm(mapping, request, path);
        componenteForm.setValues((Traducible) componente);
        componenteForm.setIdPaleta(componente.getPaleta().getId());

        request.setAttribute("idComponente", componente.getId());

        return componente;
    }


}
