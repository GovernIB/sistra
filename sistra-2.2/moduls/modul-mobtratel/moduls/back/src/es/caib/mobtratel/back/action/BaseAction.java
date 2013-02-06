package es.caib.mobtratel.back.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.RequestUtils;

import es.caib.mobtratel.back.form.CuentaForm;
import es.caib.mobtratel.back.form.PermisoForm;
import es.caib.mobtratel.back.taglib.Constants;
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.model.Permiso;
import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;



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

    /*
    protected void actualizaPath(HttpServletRequest request, int nivel, Long id)
            throws DelegateException {
        
        HttpSession sesion = request.getSession(true);

        sesion.removeAttribute("plantilla");
        sesion.removeAttribute("version");
        sesion.removeAttribute("modelo");

        if (nivel > 2){
        }

        if (nivel > 1){
        }

        if (nivel > 0){
        	
        }

    }
    */
    
    protected ActionForm obtenerActionForm(ActionMapping mapping, HttpServletRequest request, String path) 
    {
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
    
    
    protected void setReloadTree( HttpServletRequest request, String nodeName, Long entidadId )
    {
        request.setAttribute( "nodoId", nodeName + entidadId );
        request.setAttribute( "reloadMenu", "true");
    }
    
    
    protected Cuenta guardarCuenta(ActionMapping mapping, HttpServletRequest request, String idCuenta)
    throws DelegateException 
    {
		CuentaForm pForm = (CuentaForm) obtenerActionForm(mapping, request, "/back/cuenta/editar");
		
		CuentaDelegate delegate = DelegateUtil.getCuentaDelegate();
		Cuenta cuenta = delegate.obtenerCuenta(idCuenta);
		pForm.setValues(cuenta);
		
		request.setAttribute("idCuenta", idCuenta);
		
		return cuenta;
	}
    
    
    protected Permiso guardarPermiso(ActionMapping mapping, HttpServletRequest request, Long codigo)
    throws DelegateException 
    {
		PermisoForm pForm = (PermisoForm) obtenerActionForm(mapping, request, "/back/permiso/editar");
		
		PermisoDelegate delegate = DelegateUtil.getPermisoDelegate();
		Permiso permiso = delegate.obtenerPermiso(codigo);
		Cuenta cuenta = permiso.getCuenta();
		String[] codigos = new String[1];
		codigos[ 0 ] = cuenta.getCodigo(); 
			
		pForm.setCuentas( codigos );
		pForm.setValues(permiso);
		
		request.setAttribute("usuarioSeycon", permiso.getUsuarioSeycon());
		
		return permiso;
	}
	
    
}
