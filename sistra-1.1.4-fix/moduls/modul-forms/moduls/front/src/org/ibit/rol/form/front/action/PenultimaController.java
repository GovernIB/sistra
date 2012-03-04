package org.ibit.rol.form.front.action;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.util.MessageResources;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Obtiene los recursos necesarios para renderizar la plantilla final.
 */
public class PenultimaController extends BaseController {

    protected static Log log = LogFactory.getLog(PenultimaController.class);

    public void execute(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        boolean telematic = (delegate instanceof InstanciaTelematicaDelegate);

        // Fijar el titulo.
        MessageResources messages;
        if (telematic) {
            messages = MessageResources.getMessageResources("form-front-messages-caib");
        } else {
            messages = MessageResources.getMessageResources("form-front-messages");
        }
        tileContext.putAttribute("title", messages.getMessage(getLocale(request),"penultima.title"));

        /*tileContext.putAttribute("title",
                                 getResources(request).getMessage(getLocale(request), "penultima.title"));*/


        // Fijar formulario
        Formulario formulario = delegate.obtenerFormulario();
        request.setAttribute("formulario", formulario);

        
        // -- INDRA: ESTABLECEMOS VERSION DE FUNCIONAMIENTO
        String sufijo = StringUtils.defaultString(formulario.getModoFuncionamiento().getSufijo());
        request.setAttribute("sufijoModoFuncionamiento", sufijo );               
        // -- INDRA: FIN MODIFICACION
        
        List pantallasProcesadas = delegate.obtenerPantallasProcesadas();
        request.setAttribute("pantallasProcesadas", pantallasProcesadas);

        // Path Icongrafia
        request.setAttribute("pathIconografia", delegate.obtenerPathIconografia());

        // Propiedades del formulario en el caso de que sea telemático
        Map propiedadesForm = null;
        if (telematic) {
            InstanciaTelematicaDelegate itd = (InstanciaTelematicaDelegate) delegate;
            propiedadesForm = itd.obtenerPropiedadesFormulario();
            request.setAttribute("propiedadesForm", propiedadesForm);
        }

        // Tiene boton de back?
        request.setAttribute("saveButton", Boolean.valueOf(!telematic));
        request.setAttribute("discardButton", Boolean.TRUE);
        request.setAttribute("backButton", Boolean.TRUE);
        request.setAttribute("nextButton", Boolean.FALSE);

        // Botón para crear documento o volver al sistema de tramitacion?
        request.setAttribute("endButton", Boolean.TRUE);

        // Url de finalización
        if (telematic) {        	
        	// Consultamos configuración para la última pantalla:
        	// 	- pantallaFin.mostrar: true/false
        	//	- pantallaFin.titulo
        	//  - pantallaFin.texto        	
    		String pantallaFinTitulo =(String)propiedadesForm.get("pantallaFin.titulo");        		
    		String pantallaFinTexto  =(String)propiedadesForm.get("pantallaFin.texto");        		
    		if (pantallaFinTitulo!=null) tileContext.putAttribute("title", pantallaFinTitulo);
    		if (pantallaFinTexto!=null) request.setAttribute("pantallaFinTexto", pantallaFinTexto);         		
    		request.setAttribute("actionPath", "/finalitzarTelematic");        	
        } else {
            request.setAttribute("actionPath", "/calcularResultados");
        }
    }

}
