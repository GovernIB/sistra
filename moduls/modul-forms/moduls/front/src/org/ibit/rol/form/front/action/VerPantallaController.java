package org.ibit.rol.form.front.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.ComboBox;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.RadioButton;
import org.ibit.rol.form.model.TablaListaElementos;
import org.ibit.rol.form.model.TraPantalla;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;
import org.ibit.rol.form.persistence.util.ScriptUtil;

/**
 * Obtiene los recursos necesarios para renderizar la plantilla.
 */
public class VerPantallaController extends BaseController {

    protected static Log log = LogFactory.getLog(VerPantallaController.class);

    public void execute(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        boolean telematic = (delegate instanceof InstanciaTelematicaDelegate);

        // Fijar formulario
        Formulario formulario = delegate.obtenerFormulario();
        request.setAttribute("formulario", formulario);
        
        // -- INDRA: ESTABLECEMOS VERSION DE FUNCIONAMIENTO Y PANTALLA A UTILIZAR
        String sufijo = StringUtils.defaultString(formulario.getModoFuncionamiento().getSufijo());
        request.setAttribute("sufijoModoFuncionamiento", sufijo );        
        // -- INDRA: FIN MODIFICACION

        // -- INDRA: ESTABLECEMOS URL PARA MANTENIMIENTO DE SESION SISTRA Y VER PANTALLA FIN
        if (telematic) {
        	request.setAttribute("urlSisTraMantenimientoSesion",((InstanciaTelematicaDelegate)delegate).obtenerUrlSistraMantenimientoSesion());        	
        }
        // -- INDRA: ESTABLECEMOS URL PARA MANTENIMIENTO DE SESION SISTRA Y VER PANTALLA FIN
        
    	
        
        Pantalla pantalla = delegate.obtenerPantalla();
        request.setAttribute("pantalla", pantalla);

        Map datosAnteriores = delegate.obtenerDatosAnteriores();
        request.setAttribute("datosAnteriores", datosAnteriores);
        
        Map datosListasElementos = delegate.obtenerDatosListasElementos();
        request.setAttribute("datosListasElementos", datosListasElementos);
        
        List pantallasProcesadas = delegate.obtenerPantallasProcesadas();
        request.setAttribute("pantallasProcesadas", pantallasProcesadas);

        // Path Icongrafia
        request.setAttribute("pathIconografia", delegate.obtenerPathIconografia());

        TraPantalla traduccio = (TraPantalla) pantalla.getTraduccion();
        tileContext.putAttribute("title", traduccio.getTitulo());

        // Propiedades del formulario en el caso de que sea telemático
        boolean mostrarPantallaFin = true;
        boolean mostrarBotonGuardar = Boolean.valueOf(!telematic);
        if (telematic) {
            InstanciaTelematicaDelegate itd = (InstanciaTelematicaDelegate) delegate;
            Map propiedadesForm = itd.obtenerPropiedadesFormulario();
            request.setAttribute("propiedadesForm", propiedadesForm);
            try{
        		mostrarPantallaFin = (propiedadesForm.get("pantallaFin.mostrar")!=null?Boolean.parseBoolean((String)propiedadesForm.get("pantallaFin.mostrar")):false);
        	}catch (Exception ex){
        		log.error("La propiedad pantallaFin.mostrar no tiene un valor válido (true/false): " + propiedadesForm.get("pantallaFin.mostrar"));
        		mostrarPantallaFin = false;
        	}
        	mostrarBotonGuardar = itd.permitirGuardarSinTerminar();
        }

        // Botones que van a salir: distinguimos pantalla normal y pantalla detalle de lista
        if (StringUtils.isEmpty(pantalla.getComponenteListaElementos())){
        	// Pantalla normal
	        request.setAttribute("saveButton", mostrarBotonGuardar);
	        request.setAttribute("discardButton", Boolean.TRUE);
	        request.setAttribute("backButton", Boolean.valueOf(!pantalla.isInicial()));
	        request.setAttribute("nextButton", new Boolean(!pantalla.isUltima() || mostrarPantallaFin));
	        request.setAttribute("endButton", new Boolean(pantalla.isUltima() && !mostrarPantallaFin));
        }else{
        	// Pantalla detalle
        	// - Deshabilitamos todos los botones
        	request.setAttribute("saveButton", Boolean.FALSE);
	        request.setAttribute("discardButton", Boolean.FALSE);
	        request.setAttribute("backButton", Boolean.FALSE);
	        request.setAttribute("nextButton", Boolean.FALSE);
	        request.setAttribute("endButton", Boolean.FALSE);
	        request.setAttribute("saveButton", Boolean.FALSE);
	        request.setAttribute("discardButton", Boolean.FALSE);       
	        // - Indicamos que es pantalla detalle para que se configure adecuadamente
	        request.setAttribute("pantallaDetalle", Boolean.TRUE);
        }
        
        AyudaPantalla ap = delegate.obtenerAyudaPantalla();
        request.setAttribute("ayudaPantalla", ap);
        
        
        // -- INDRA: MODIFICACION PARA: TENER EN CUENTA CAMPOS MODIFICADOS EN AUTOCALCULO Y MOSTRAR DIV DE ESPERA PARA AJAX
        //
        //   - Obtenemos las variables que van a definirse para almacenar los campos
        StringBuffer sb = new StringBuffer();
        Map ajaxOnchange = new HashMap();
        for (Iterator it = pantalla.getCampos().iterator();it.hasNext();){
        	Campo  campo = (Campo) it.next();
        	
        	// Obtenemos variables a definir para el campo
        	String nombre= campo.getNombreLogico();        	        	        	
        	sb.append("var f_"+nombre+";\n");
        	if (campo instanceof ComboBox ||
        		campo instanceof ListBox  ||
        		campo instanceof RadioButton){                		
        		sb.append("var f_"+nombre+"_text;\n");        
        	}
        	
        	// Calculamos si al modificar el campo se invocara a ajax: si el campo aparece en autocalculo,autorellenable o valores posibles
        	boolean ajax = false;
        	String deps;
        	 for (Iterator it2 = pantalla.getCampos().iterator();it2.hasNext();){
        		 Campo  campo2 = (Campo) it2.next(); 
        		 if (campo2.getNombreLogico().equals(nombre)) continue;
        		 if (StringUtils.isNotEmpty(campo2.getExpresionAutocalculo())) {
        			 deps = ScriptUtil.dependencias(campo2.getExpresionAutocalculo());
        			 if (deps.indexOf("f_"+nombre+" ")!=-1){
        				 ajax=true;
        				 break;
        			 }
        		 }        		 
        		 if (StringUtils.isNotEmpty(campo2.getExpresionAutorellenable())) {
        			 deps = ScriptUtil.dependencias(campo2.getExpresionAutorellenable());
        			 if (deps.indexOf("f_"+nombre+" ")!=-1){
        				 ajax=true;
        				 break;
        			 }
        		 }
        		 if (StringUtils.isNotEmpty(campo2.getExpresionValoresPosibles())) {
        			 deps = ScriptUtil.dependencias(campo2.getExpresionValoresPosibles());
        			 if (deps.indexOf("f_"+nombre+" ")!=-1){
        				 ajax=true;
        				 break;
        			 }
        		 }
        	 }        	 
        	 ajaxOnchange.put(nombre,""+ajax);        	
        }
        
        request.setAttribute("definicionVariablesForm", sb.toString());
        request.setAttribute("ajaxOnChange", ajaxOnchange);
        
        // -- INDRA: FIN	
        
        
        // -- INDRA: LISTA ELEMENTOS
        // Si hay campos de tipo lista elementos construimos tabla a mostrar
        for (Iterator it = pantalla.getCampos().iterator();it.hasNext();){
        	Campo campo = (Campo) it.next();
        	if (campo instanceof ListaElementos){
        		List camposTabla = delegate.obtenerCamposTablaListaElementos(campo.getNombreLogico());
        		List datosLista = delegate.obtenerDatosListaElementos(campo.getNombreLogico());
        		TablaListaElementos tabla = new TablaListaElementos(camposTabla,datosLista);
        		request.setAttribute("listaelementos@"+campo.getNombreLogico(), tabla);
        	}
        }
        // -- INDRA: LISTA ELEMENTOS
    }
}
