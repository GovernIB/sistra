package org.ibit.rol.form.back.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.RequestUtils;
import org.ibit.rol.form.back.form.ComponenteForm;
import org.ibit.rol.form.back.form.FormularioForm;
import org.ibit.rol.form.back.form.PantallaForm;
import org.ibit.rol.form.back.form.PropiedadSalidaForm;
import org.ibit.rol.form.back.form.SalidaForm;
import org.ibit.rol.form.back.form.ValorPosibleForm;
import org.ibit.rol.form.back.taglib.Constants;
import org.ibit.rol.form.back.util.ComponenteConfig;
import org.ibit.rol.form.back.util.FormularioConfig;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.CheckBox;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.FormularioSeguro;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.model.Salida;
import org.ibit.rol.form.model.Traducible;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.PantallaDelegate;
import org.ibit.rol.form.persistence.delegate.PropiedadSalidaDelegate;
import org.ibit.rol.form.persistence.delegate.ValidadorFirmaDelegate;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;

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

    protected static Archivo populateArchivo(Archivo archivo, FormFile formFile) throws IOException {
        if (archivo == null) archivo = new Archivo();
        archivo.setTipoMime(formFile.getContentType());
        archivo.setNombre(formFile.getFileName());
        archivo.setPesoBytes(formFile.getFileSize());
        archivo.setDatos(formFile.getFileData());
        return archivo;
    }

    protected void actualizaPath(HttpServletRequest request, int nivel, Long id)
            throws DelegateException {
        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        PantallaDelegate pantallaDelegate = DelegateUtil.getPantallaDelegate();
        ComponenteDelegate componenteDelegate = DelegateUtil.getComponenteDelegate();
        ValorPosibleDelegate valorPosibleDelegate = DelegateUtil.getValorPosibleDelegate();

        HttpSession sesion = request.getSession(true);

        sesion.removeAttribute("valor");
        sesion.removeAttribute("componente");
        sesion.removeAttribute("pantalla");
        sesion.removeAttribute("formulario");

        if (nivel > 3){
            ValorPosible valor = valorPosibleDelegate.obtenerValorPosible(id);
            sesion.setAttribute("valor", valor.getValor());
            id = valor.getCampo().getId();
        }

        if (nivel > 2){
            Componente componente = componenteDelegate.obtenerComponente(id);
            sesion.setAttribute("componente", componente.getNombreLogico());
            id = componente.getPantalla().getId();
        }

        if (nivel > 1){
            Pantalla pantalla = pantallaDelegate.obtenerPantalla(id);
            sesion.setAttribute("pantalla", pantalla.getNombre());
            id = pantalla.getFormulario().getId();
        }

        if (nivel > 0){
            Formulario formulario = formularioDelegate.obtenerFormulario(id);
            sesion.setAttribute("formulario", formulario.getModelo());
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

    protected Formulario guardarFormulario(ActionMapping mapping, HttpServletRequest request, Long idFormulario)
            throws DelegateException {
        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Formulario formulario = delegate.obtenerFormulario(idFormulario);

        String path = FormularioConfig.getMapping(formulario);
        FormularioForm pForm = (FormularioForm) obtenerActionForm(mapping, request, path);


        pForm.setValues(formulario);

        String tipo = FormularioConfig.getTipo(formulario);
        if("formularioseguro".equals(tipo)){
            ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();
            List ids = validadorDelegate.listarIdsValidadoresFirmaFormulario(idFormulario);
            pForm.setValidadores_ids((Long[])ids.toArray(new Long[0]));

            Set roles = ((FormularioSeguro)formulario).getRoles();
            String rolesString = "";
            for(Iterator iter = roles.iterator(); iter.hasNext();){
                rolesString = rolesString  + iter.next() + ",";
            }
            if(rolesString.length()>0){
                rolesString = rolesString.substring(0, rolesString.length()-1);
            }
            pForm.setRolesString(rolesString);
        }
        
        // -- INDRA: MODOS FUNCIONAMIENTO
        pForm.setModoFuncionamientoCod(formulario.getModoFuncionamiento().getCodigo());
        // -- INDRA: FIN

        resetearNiveles(request);
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idFormulario", idFormulario);
        actualizaPath(request, 1, idFormulario);

        return formulario;
    }

    protected Formulario guardarFormularioSeguro(ActionMapping mapping, HttpServletRequest request, Long idFormulario)
                throws DelegateException {
        FormularioForm pForm = (FormularioForm) obtenerActionForm(mapping, request, "/back/formularioseguro/editar");

        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Formulario formulario = delegate.obtenerFormulario(idFormulario);
        pForm.setValues(formulario);

        Set roles = ((FormularioSeguro)formulario).getRoles();
        String rolesString = "";
        for(Iterator iter = roles.iterator(); iter.hasNext();){
            rolesString = rolesString  + iter.next() + ",";
        }
        if(rolesString.length()>0){
            rolesString = rolesString.substring(0, rolesString.length()-1);
        }
        pForm.setRolesString(rolesString);
        
        // -- INDRA: MODOS FUNCIONAMIENTO
        pForm.setModoFuncionamientoCod(formulario.getModoFuncionamiento().getCodigo());
        // -- INDRA: FIN

        ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();
        List ids = validadorDelegate.listarIdsValidadoresFirmaFormulario(idFormulario);
        pForm.setValidadores_ids((Long[])ids.toArray(new Long[0]));


        resetearNiveles(request);
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idFormulario", idFormulario);
        actualizaPath(request, 1, idFormulario);

        return formulario;
    }


    protected Pantalla guardarPantalla(ActionMapping mapping, HttpServletRequest request, Long idPantalla)
            throws DelegateException {
        PantallaForm pForm = (PantallaForm) obtenerActionForm(mapping, request, "/back/pantalla/editar");

        PantallaDelegate delegate = DelegateUtil.getPantallaDelegate();
        Pantalla pantalla = delegate.obtenerPantalla(idPantalla);
        pForm.setValues(pantalla);
        pForm.setIdFormulario(pantalla.getFormulario().getId());

        resetearNiveles(request);
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idPantalla", idPantalla);
        sesion.setAttribute("idFormulario", pantalla.getFormulario().getId());
        actualizaPath(request, 2, idPantalla);

        return pantalla;
    }
    
    
    protected Pantalla guardarPantallaDetalle(ActionMapping mapping, HttpServletRequest request, Long idComponente)
		    throws DelegateException {
		PantallaForm pForm = (PantallaForm) obtenerActionForm(mapping, request, "/back/pantalla/editar");
				
		ComponenteDelegate cd = DelegateUtil.getComponenteDelegate();
        Componente c = cd.obtenerComponente(idComponente);
        
        String nomDetalle = c.getPantalla().getNombre() +"#@#"+  c.getNombreLogico();
        
        PantallaDelegate pd = DelegateUtil.getPantallaDelegate();
        Pantalla pantalla= pd.obtenerPantalla(c.getPantalla().getFormulario().getModelo(),c.getPantalla().getFormulario().getVersion(),nomDetalle);
		
		pForm.setValues(pantalla);
		pForm.setIdFormulario(pantalla.getFormulario().getId());
		
		resetearNiveles(request);
		HttpSession sesion = request.getSession(true);
		sesion.setAttribute("idPantalla", pantalla.getId());
		sesion.setAttribute("idFormulario", pantalla.getFormulario().getId());
		actualizaPath(request, 2, pantalla.getId());
		
		return pantalla;
		}

    protected Componente guardarComponente(ActionMapping mapping, HttpServletRequest request, Long idComponente)
            throws DelegateException {
        ComponenteDelegate delegate = DelegateUtil.getComponenteDelegate();
        Componente componente = delegate.obtenerComponente(idComponente);

        String path = ComponenteConfig.getMapping(componente);
        ComponenteForm componenteForm = (ComponenteForm) obtenerActionForm(mapping, request, path);
        componenteForm.setValues((Traducible) componente);
        componenteForm.setIdPantalla(componente.getPantalla().getId());
        if (componente.getPantalla()!=null && StringUtils.isNotEmpty(componente.getPantalla().getComponenteListaElementos())){
        	componenteForm.setPantallaDetalle(true);
        }
        
        resetearNiveles(request);
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idComponente", componente.getId());
        sesion.setAttribute("idPantalla", componente.getPantalla().getId());
        sesion.setAttribute("idFormulario", componente.getPantalla().getFormulario().getId());
        
        actualizaPath(request, 3, idComponente);

        return componente;
    }

     protected ValorPosible guardarValorPosible(ActionMapping mapping, HttpServletRequest request, Long idValorPosible)
            throws DelegateException {
        ValorPosibleForm vpForm = (ValorPosibleForm) obtenerActionForm(mapping, request, "/back/valorposible/editar");

        ValorPosibleDelegate delegate = DelegateUtil.getValorPosibleDelegate();
        ValorPosible valor = delegate.obtenerValorPosible(idValorPosible);

        vpForm.setValues(valor);
        vpForm.setIdComponente(valor.getCampo().getId());
        vpForm.setImagen(valor.getCampo().isImagen());

        resetearNiveles(request);
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idValorPosible", idValorPosible);
        sesion.setAttribute("idComponente", valor.getCampo().getId());
        sesion.setAttribute("idPantalla", valor.getCampo().getPantalla().getId());
        sesion.setAttribute("idFormulario", valor.getCampo().getPantalla().getFormulario().getId());
        actualizaPath(request, 4, idValorPosible);

        return valor;
    }

    protected PropiedadSalida guardarPropiedadSalida(ActionMapping mapping, HttpServletRequest request, Long idPropiedad)
            throws DelegateException {
        PropiedadSalidaDelegate delegate = DelegateUtil.getPropiedadSalidaDelegate();
        PropiedadSalida propiedad = delegate.obtenerPropiedadSalida(idPropiedad);


        PropiedadSalidaForm propiedadForm = (PropiedadSalidaForm) obtenerActionForm(mapping, request, "/back/propiedadsalida/editar");
        propiedadForm.setValues(propiedad);
        propiedadForm.setIdSalida(propiedad.getSalida().getId());

        log.debug("guardarPropiedadSalida "+ propiedad.getSalida().getId());


        resetearNiveles(request);
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idPropiedad", propiedad.getId());
        sesion.setAttribute("idSalida", propiedad.getSalida().getId());
        sesion.setAttribute("idFormulario", propiedad.getSalida().getFormulario().getId());
        //actualizaPath(request, 3, idPropiedad);

        return propiedad;
    }

    protected Salida guardarSalida(ActionMapping mapping, HttpServletRequest request, Long idSalida)
            throws DelegateException {
        SalidaForm sForm = (SalidaForm) obtenerActionForm(mapping, request, "/back/salida/alta");

        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Salida salida = delegate.obtenerSalida(idSalida);
        request.setAttribute("salida", salida);

        sForm.setIdFormulario(salida.getFormulario().getId());
        sForm.setIdPuntoSalida(salida.getPunto().getId());

        resetearNiveles(request);
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("idSalida", idSalida);
        sesion.setAttribute("idFormulario", salida.getFormulario().getId());

        return salida;
    }

    protected void resetearNiveles(HttpServletRequest request){
    	HttpSession sesion = request.getSession(true);
    	sesion.setAttribute("idPropiedad", null);
        sesion.setAttribute("idSalida", null);
        sesion.setAttribute("idValorPosible", null);
        sesion.setAttribute("idComponente", null);
        sesion.setAttribute("idPantalla", null);
        sesion.setAttribute("idFormulario", null);
    }



   /* protected PuntoSalida guardarPuntoSalida(ActionMapping mapping, HttpServletRequest request, Long idPunto)
            throws DelegateException {
        PuntoSalidaDelegate delegate = DelegateUtil.getPuntoSalidaDelegate();
        PuntoSalida punto = delegate.obtenerPuntoSalida(idPunto);


        PuntoSalidaForm puntoForm = (PuntoSalidaForm) obtenerActionForm(mapping, request, "/back/propiedadsalida/editar");
        puntoForm.setValues(punto);


        request.setAttribute("idPunto", punto.getId());
        //actualizaPath(request, 3, idPunto);

        return punto;
    }*/
}
