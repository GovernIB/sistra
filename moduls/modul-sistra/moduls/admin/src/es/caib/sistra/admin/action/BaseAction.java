package es.caib.sistra.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.sistra.admin.taglib.Constants;


/**
 * Action con métodos de utilidad.
 */
public abstract class BaseAction extends Action {

    /** Retorna true si se ha pulsado un botón submit de alta */
    protected boolean isAlta(HttpServletRequest request) {
      return (request.getParameter(Constants.ALTA_PROPERTY) != null);
    }

    /** Retorna true si se ha pulsado un botón submit de baja */
    protected boolean isBaja(HttpServletRequest request) {
      return (request.getParameter(Constants.BAIXA_PROPERTY) != null);
    }

    /** Retorna true si se ha pulsado un botón submit de modificación */
    protected boolean isModificacion(HttpServletRequest request) {
      return (request.getParameter(Constants.MODIFICACIO_PROPERTY) != null);
    }

     /** Retorna true si se ha pulsado un botón submit de selección */
    protected boolean isSeleccion(HttpServletRequest request) {
      return (request.getParameter(Constants.SELECCIO_PROPERTY) != null);
    }

    protected boolean archivoValido(FormFile formFile) {
        return (formFile != null && formFile.getFileSize() > 0);
    }
    
    public abstract  ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
}
