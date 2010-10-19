package es.caib.redose.back.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.RequestUtils;

import es.caib.redose.back.form.ErroresGestorDocumentalForm;
import es.caib.redose.back.form.FormateadoresForm;
import es.caib.redose.back.form.HistoricoForm;
import es.caib.redose.back.form.ModeloForm;
import es.caib.redose.back.form.PlantillaForm;
import es.caib.redose.back.form.UbicacionForm;
import es.caib.redose.back.form.VersionForm;
import es.caib.redose.back.taglib.Constants;
import es.caib.redose.model.ArchivoPlantilla;
import es.caib.redose.model.Formateador;
import es.caib.redose.model.LogGestorDocumentalError;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.model.Modelo;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.model.Version;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.FormateadorDelegate;
import es.caib.redose.persistence.delegate.LogGestorDocumentalErroresDelegate;
import es.caib.redose.persistence.delegate.LogOperacionDelegate;
import es.caib.redose.persistence.delegate.ModeloDelegate;
import es.caib.redose.persistence.delegate.PlantillaDelegate;
import es.caib.redose.persistence.delegate.UbicacionDelegate;
import es.caib.redose.persistence.delegate.VersionDelegate;


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
    
    protected static ArchivoPlantilla populateArchivo(ArchivoPlantilla archivo, FormFile formFile) throws IOException {
        if (archivo == null) archivo = new ArchivoPlantilla();
        archivo.setDatos(formFile.getFileData());
        return archivo;
    }

    
    protected void actualizaPath(HttpServletRequest request, int nivel, Long id)
            throws DelegateException {
        ModeloDelegate modeloDelegate = DelegateUtil.getModeloDelegate();
        VersionDelegate versionDelegate = DelegateUtil.getVersionDelegate();
        PlantillaDelegate plantillaDelegate = DelegateUtil.getPlantillaDelegate();

        HttpSession sesion = request.getSession(true);

        sesion.removeAttribute("plantilla");
        sesion.removeAttribute("version");
        sesion.removeAttribute("modelo");

        if (nivel > 2){
            Plantilla plantilla = plantillaDelegate.obtenerPlantilla(id);
            sesion.setAttribute("plantilla", plantilla.getTipo() );
            id = plantilla.getVersion().getCodigo();
        }

        if (nivel > 1){
            Version version = versionDelegate.obtenerVersion(id);
            sesion.setAttribute("version", "" + version.getVersion() );
            id = version.getModelo().getCodigo();
        }

        if (nivel > 0){
            Modelo modelo = modeloDelegate.obtenerModelo(id);
            sesion.setAttribute("modelo", modelo.getModelo());
        }

    }
    
    

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

    protected Modelo guardarModelo(ActionMapping mapping, HttpServletRequest request, Long idModelo)
            throws DelegateException {
        ModeloForm pForm = (ModeloForm) obtenerActionForm(mapping, request, "/back/modelo/editar");

        ModeloDelegate delegate = DelegateUtil.getModeloDelegate();
        Modelo modelo = delegate.obtenerModelo(idModelo);
        pForm.setValues(modelo);

        request.setAttribute("idModelo", idModelo);
        actualizaPath(request, 1, idModelo);

        return modelo;
    }
    
    protected Version guardarVersion( ActionMapping mapping, HttpServletRequest request, Long idVersion )
    throws DelegateException
    {
    	VersionForm pForm = ( VersionForm ) obtenerActionForm( mapping, request, "/back/version/editar" );
    	VersionDelegate delegate = DelegateUtil.getVersionDelegate();
    	Version version = delegate.obtenerVersion( idVersion );
    	pForm.setIdModelo( version.getModelo().getCodigo() );
    	pForm.setValues(version);
    	
    	request.setAttribute("idVersion", idVersion);
    	actualizaPath(request, 2, idVersion);
    	return version;
    	
    }
    
    protected Plantilla guardarPlantilla( ActionMapping mapping, HttpServletRequest request, Long idPlantilla )
    throws DelegateException
    {
    	PlantillaForm pForm = ( PlantillaForm ) obtenerActionForm( mapping, request, "/back/plantilla/editar" );
    	PlantillaDelegate delegate = DelegateUtil.getPlantillaDelegate();
    	Plantilla plantilla = delegate.obtenerPlantilla( idPlantilla );
    	plantilla.setCurrentLang( pForm.getLang() );
    	pForm.setIdVersion( plantilla.getVersion().getCodigo() );
    	pForm.setValues(plantilla);
    	
    	request.setAttribute("idPlantilla", idPlantilla);
    	actualizaPath(request, 3, idPlantilla);
    	return plantilla;
    	
    }
    
    protected Ubicacion guardarUbicacion( ActionMapping mapping, HttpServletRequest request, Long idUbicacion )
    throws DelegateException
    {
    	UbicacionForm pForm = ( UbicacionForm ) obtenerActionForm( mapping, request, "/back/ubicacion/editar" );
    	UbicacionDelegate delegate = DelegateUtil.getUbicacionDelegate();
    	Ubicacion ubicacion = delegate.obtenerUbicacion( idUbicacion );
    	pForm.setValues(ubicacion);
    	
    	request.setAttribute("idUbicacion", idUbicacion);
    	return ubicacion;
    }
    
    protected Formateador guardarFormateador( ActionMapping mapping, HttpServletRequest request, Long idFormateador )
    throws DelegateException
    {
    	FormateadoresForm fForm = ( FormateadoresForm ) obtenerActionForm( mapping, request, "/back/formateadores/editar" );
    	FormateadorDelegate delegate = DelegateUtil.getFormateadorDelegate();
    	Formateador formateador = delegate.obtenerFormateador( idFormateador );
    	fForm.setValues(formateador);
    	
    	request.setAttribute("idFormateador", idFormateador);
    	return formateador;
    }
    
    protected LogOperacion guardarHistorico(ActionMapping mapping, HttpServletRequest request, Long idLogOperacion)
    throws DelegateException 
    {
		HistoricoForm pForm = (HistoricoForm) obtenerActionForm(mapping, request, "/back/historico/editar");
		
		LogOperacionDelegate delegate = DelegateUtil.getLogOperacionDelegate();
		LogOperacion operacion = delegate.obtenerLogOperacion( idLogOperacion );
		pForm.setValues(operacion);
		
		request.setAttribute("idHistorico", idLogOperacion);
		
		return operacion;
	}

    
    protected LogGestorDocumentalError guardarErrorGestorDocumental(ActionMapping mapping, HttpServletRequest request, Long idLogError)
    throws DelegateException 
    {
		ErroresGestorDocumentalForm pForm = (ErroresGestorDocumentalForm) obtenerActionForm(mapping, request, "/back/erroresGestorDocumental/editar");
		
		LogGestorDocumentalErroresDelegate delegate = DelegateUtil.getLogErrorGestorDocumentalDelegate();
        LogGestorDocumentalError logError = delegate.obtenerLogGestorDocumentalError(idLogError);
		
		pForm.setValues(logError);
		
		request.setAttribute("idErrorGestor", idLogError);
		
		return logError;
	}

}
