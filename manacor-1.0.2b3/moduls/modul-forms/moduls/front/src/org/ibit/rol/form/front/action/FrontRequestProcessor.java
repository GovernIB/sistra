package org.ibit.rol.form.front.action;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.ComponentDefinition;
import org.ibit.rol.form.front.Constants;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.IdiomaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.FormularioSeguro;
import org.ibit.rol.form.model.OrganismoInfo;


/**
 * RequestProcessor con funcionalidad añadida de selección automàtica
 * de locale y punto de entrada con sesiones.
 */
public class FrontRequestProcessor extends TilesRequestProcessor {

    protected static Log log = LogFactory.getLog(FrontRequestProcessor.class);

    private String defaultLang = null;
    private List supportedLangs = null;

    /**
     * Inicializa los idiomas soportados por la aplicación
     */
    public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
        super.init(servlet, config);
        try {
            IdiomaDelegate delegate = DelegateUtil.getIdiomaDelegate();

            // Lenguaje por defecto.
            defaultLang = delegate.lenguajePorDefecto();
            log.debug("Default lang: " + defaultLang);
            getServletContext().setAttribute(Constants.DEFAULT_LANG_KEY, defaultLang);

            // Todos los lenguajes soportados (incluido el por defecto).
            supportedLangs = delegate.listarLenguajes();
            log.debug("Supported langs: " + supportedLangs);
            
            // Inicializamos informacion organismo (almacenamos en contexto)
            try{
    	 		if (getServletContext().getAttribute(Constants.ORGANISMO_INFO_KEY) == null){
    	 			OrganismoInfo oi = DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo();
    	 			getServletContext().setAttribute(Constants.ORGANISMO_INFO_KEY,oi);
    	 		}
            }catch (Exception ex){
            	log.error("Error obteniendo informacion organismo",ex);
            	throw new ServletException(ex);
            }
            
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Elije el lenguaje en el que se servira la petición.
     * @param request
     * @param response
     */
    protected void processLocale(HttpServletRequest request, HttpServletResponse response) {
        // Si se ha indicado que no se debe fijar no hacemos nada.
        if (!moduleConfig.getControllerConfig().getLocale()) {
            return;
        }

        HttpSession session = request.getSession(true);

        // Se ha especificado sobreescribir el locale?
        String paramLang = request.getParameter("lang");
        if (paramLang != null) {
            if (supportedLangs.contains(paramLang)) {
                log.debug("Setting selected locale: " + paramLang);
                session.setAttribute(Globals.LOCALE_KEY, new Locale(paramLang));
                return;
            } else {
                log.warn("Invalid selected locale: " + paramLang);
            }
        }

        // Si ya tenemos locale abandonamos.
        if (session.getAttribute(Globals.LOCALE_KEY) != null) {
            return;
        }

        // Buscamos un locale enviado por el navegador que nos coincida con los soportados.
        Enumeration enumLocales = request.getLocales();
        while (enumLocales.hasMoreElements()) {
            Locale locale = (Locale) enumLocales.nextElement();
            String lang = locale.getLanguage().trim();

            if (supportedLangs.contains(lang)) {
                log.debug("Setting browser locale: " + lang);
                session.setAttribute(Globals.LOCALE_KEY, new Locale(lang));
                return;
            }
        }

        // Si no encontramos ninguno fijamos el por defecto.
        log.debug("Setting default locale: " + defaultLang);
        session.setAttribute(Globals.LOCALE_KEY, new Locale(defaultLang));
    }

    public void process(HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException, ServletException {    	
        if (request.getCharacterEncoding() == null) {
            log.debug("CharacterEncoding és null, emprant utf-8");
            request.setCharacterEncoding("utf-8");
        }else{
        	log.debug("CharacterEncoding NO és null, emprant " + request.getCharacterEncoding() );
        }
        super.process(request, response);
    }

    /**
     * General-purpose preprocessing hook that can be overridden as required
     * by subclasses.  Return <code>true</code> if you want standard processing
     * to continue, or <code>false</code> if the response has already been
     * completed.
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     */
    protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute(RegistroManager.ID_INSTANCIA) == null) {
            String instancia = request.getParameter(RegistroManager.ID_INSTANCIA);
            if (instancia != null) {
                request.setAttribute(RegistroManager.ID_INSTANCIA, instancia);
            }
        }

        String securePath = "";
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate != null) {
            try {
                Formulario formulario = delegate.obtenerFormulario();
                securePath = getSecurePath(formulario);

                if (formulario instanceof FormularioSeguro) {
                    FormularioSeguro formSeguro = (FormularioSeguro) formulario;
                    if (formSeguro.isRequerirLogin() && !formSeguro.getRoles().isEmpty()) {
                        boolean roleFound = false;
                        for (Iterator iterator = formSeguro.getRoles().iterator(); iterator.hasNext();) {
                            String role = (String) iterator.next();
                            if (request.isUserInRole(role)) {
                                roleFound = true;
                                break;
                            }
                        }
                        if (!roleFound) {
                            try {
                                response.reset();
                                log.warn("Intentat accedir al formulari " + formSeguro.getModelo() + " sense cap dels rols " + formSeguro.getRoles());
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene los permisos requeridos");
                                return false;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if (formSeguro.isHttps() && !request.isSecure()) {
                        try {
                            response.reset();
                            log.warn("Intentant accedir al formulari " + formSeguro.getModelo() + " de forma insegura");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "El formulario requiere https");
                            return false;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (DelegateException e) {
                throw new RuntimeException(e);
            }
        }
        request.setAttribute("securePath", securePath);

        return true;
    }

    /**
     * Process a Tile definition name.
     * This method tries to process the parameter <code>definitionName</code> as a definition name.
     * It returns <code>true</code> if a definition has been processed, or <code>false</code> otherwise.
     * Parameter <code>contextRelative</code> is not used in this implementation.
     *
     * @param definitionName  Definition name to insert.
     * @param contextRelative Is the definition marked contextRelative ?
     * @param request         Current page request.
     * @param response        Current page response.
     * @return <code>true</code> if the method has processed uri as a definition name, <code>false</code> otherwise.
     */
    protected boolean processTilesDefinition(String definitionName, boolean contextRelative, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //log.info("processTilesDefinition(" + definitionName + ")");
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        boolean telematic = (delegate instanceof InstanciaTelematicaDelegate);
        if (telematic) {
            //log.info("telematic");
            try {
                String layout = ((InstanciaTelematicaDelegate) delegate).obtenerLayOut();
                //log.info("layout=" + layout);
                if (layout != null && !"main".equals(layout)) {
                    String newDefinitionName = definitionName + "_" + layout;
                    //log.info("newDefinitionName=" + newDefinitionName);
                    ComponentDefinition definition = definitionsFactory.getDefinition(newDefinitionName, request, getServletContext());
                    //log.info("definition=" + definition);
                    if (definition != null) {
                        //log.info("super.processTilesDefinition(" + newDefinitionName + ")");
                        return super.processTilesDefinition(newDefinitionName, contextRelative, request, response);
                    }
                }
            } catch (DelegateException e) {
                throw new ServletException(e);
            } catch (DefinitionsFactoryException e) {
                throw new ServletException(e);
            }
        }

        //log.info("super.processTilesDefinition(" + definitionName + ")");
        return super.processTilesDefinition(definitionName, contextRelative, request, response);
    }


    /**
     * Retorna el prefijo del path necesario para procesar el formulario.
     * @param formulario
     * @return path que debe añadirse despues del contextpath.
    */
    protected String getSecurePath(Formulario formulario) {
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
