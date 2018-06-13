package es.caib.bantel.back.action;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.RequestUtils;

import es.caib.bantel.back.form.CampoFuenteDatosForm;
import es.caib.bantel.back.form.EntidadForm;
import es.caib.bantel.back.form.FicheroExportacionForm;
import es.caib.bantel.back.form.FuenteDatosForm;
import es.caib.bantel.back.form.GestorBandejaForm;
import es.caib.bantel.back.form.TramiteForm;
import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.model.CampoFuenteDatos;
import es.caib.bantel.model.Entidad;
import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.EntidadDelegate;
import es.caib.bantel.persistence.delegate.FicheroExportacionDelegate;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.util.CifradoUtil;


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
    
    
    protected Procedimiento guardarTramite(ActionMapping mapping, HttpServletRequest request, Long idTramite)
    throws DelegateException 
    {
		TramiteForm pForm = (TramiteForm) obtenerActionForm(mapping, request, "/back/tramite/editar");
		
		ProcedimientoDelegate delegate = DelegateUtil.getTramiteDelegate();
		Procedimiento tramite = delegate.obtenerProcedimiento(idTramite);
		
		try{
			String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
	        String userPlain = CifradoUtil.descifrar(claveCifrado,tramite.getUsr());
	        String pasPlain = CifradoUtil.descifrar(claveCifrado,tramite.getPwd()); 
			
			pForm.setUserPlain(userPlain);
			pForm.setPassPlain(pasPlain);
		}catch (Exception ex){
			throw new DelegateException(ex);
		}
		
		pForm.setValues(tramite);
		
		request.setAttribute("idTramite", idTramite);
		
		return tramite;
	}
    
    protected Entidad guardarEntidad(ActionMapping mapping, HttpServletRequest request, String idEntidad)
    	    throws DelegateException 
    	    {
    			EntidadForm pForm = (EntidadForm) obtenerActionForm(mapping, request, "/back/entidad/editar");
    			
    			EntidadDelegate delegate = DelegateUtil.getEntidadDelegate();
    			Entidad entidad = delegate.obtenerEntidad(idEntidad);
    			
    			pForm.setValues(entidad);
    			
    			request.setAttribute("idEntidad", idEntidad);
    			
    			return entidad;
    		}
    
    protected FicheroExportacion guardarFicheroExportacion(ActionMapping mapping, HttpServletRequest request, String idFicheroExportacion)
    throws DelegateException 
    {
    	FicheroExportacionForm pForm = (FicheroExportacionForm) obtenerActionForm(mapping, request, "/back/ficheroExportacion/editar");
		
    	FicheroExportacionDelegate delegate = DelegateUtil.getFicheroExportacionDelegate();
    	FicheroExportacion ficExportacion = delegate.obtenerFicheroExportacion(idFicheroExportacion);
				
		
		pForm.setValues(ficExportacion);
		
		request.setAttribute("idFicheroExportacion", idFicheroExportacion);
		
		return ficExportacion;
	}
    
    protected GestorBandeja guardarGestorBandeja(ActionMapping mapping, HttpServletRequest request, String idGestorBandeja)
    throws DelegateException 
    {
		GestorBandejaForm pForm = (GestorBandejaForm) obtenerActionForm(mapping, request, "/back/gestorBandeja/editar");
		
		GestorBandejaDelegate delegate = DelegateUtil.getGestorBandejaDelegate();
		GestorBandeja gestorBandeja = delegate.obtenerGestorBandeja(idGestorBandeja);
		Set tramites = gestorBandeja.getProcedimientosGestionados();
		String[] codigos = new String[tramites.size()];
		int i = 0;
		if (tramites != null) {
			for ( Iterator it = tramites.iterator(); it.hasNext(); i++ )
			{
				codigos[ i ] = ( ( Procedimiento ) it.next() ).getIdentificador(); 
			}
		}
			
		pForm.setTramites( codigos );
		pForm.setValues(gestorBandeja);
		
		request.setAttribute("idGestorBandeja", idGestorBandeja);
		
		return gestorBandeja;
	}
    
    
    protected FuenteDatos guardarFuenteDatos(ActionMapping mapping, HttpServletRequest request, String idFuenteDatos)
    throws DelegateException 
    {
    	FuenteDatosForm pForm = (FuenteDatosForm) obtenerActionForm(mapping, request, "/back/fuenteDatos/editar");
		
    	FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
    	FuenteDatos fuenteDatos = delegate.obtenerFuenteDatos(idFuenteDatos);
				
		pForm.setValues(fuenteDatos);
		pForm.setIdentificadorOld(fuenteDatos.getIdentificador());
		pForm.setIdProcedimiento(fuenteDatos.getProcedimiento().getIdentificador());
		
		request.setAttribute("idFuenteDatos", idFuenteDatos);
		
		return fuenteDatos;
	}
    
    protected CampoFuenteDatos guardarCampoFuenteDatos(ActionMapping mapping, HttpServletRequest request, String idFuenteDatos, String idCampoFuenteDatos)
    throws DelegateException 
    {
    	CampoFuenteDatosForm pForm = (CampoFuenteDatosForm) obtenerActionForm(mapping, request, "/back/campoFuenteDatos/editar");
		
    	FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
    	FuenteDatos fuenteDatos = delegate.obtenerFuenteDatos(idFuenteDatos);
    			
		CampoFuenteDatos campoFuenteDatos = fuenteDatos.getCampoFuenteDatos(idCampoFuenteDatos);
		pForm.setValues(campoFuenteDatos);
		pForm.setIdFuenteDatos(idFuenteDatos);
		pForm.setIdentificadorOld(idCampoFuenteDatos);
		
		request.setAttribute("idCampoFuenteDatos", idFuenteDatos + "#" + idCampoFuenteDatos);
		
		return campoFuenteDatos;
	}
}
