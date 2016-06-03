package es.caib.sistra.front.action;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesRequestProcessor;
import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.ConfiguracionDelegate;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.IdiomaDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.model.OrganismoInfo;

/**
 * RequestProcessor con funcionalidad añadida de selección automàtica
 * de locale y punto de entrada con sesiones.
 */
public class FrontRequestProcessor extends TilesRequestProcessor {

    protected static Log log = LogFactory.getLog(FrontRequestProcessor.class);

    private String defaultLang = null;
    private List supportedLangs = null;

    public void init(ActionServlet actionServlet,ModuleConfig moduleConfig) throws ServletException{

    	super.init(actionServlet,moduleConfig);

    	// Inicializamos implementacion de firma (almacenamos en contexto)
        try{
     		if (StringUtils.isEmpty((String) getServletContext().getAttribute(Constants.IMPLEMENTACION_FIRMA_KEY))){
     			getServletContext().setAttribute(Constants.IMPLEMENTACION_FIRMA_KEY,PluginFactory.getInstance().getPluginFirma().getProveedor());
     		}
        }catch (Exception ex){
        	log.error("Error obteniendo implementacion firma",ex);
        	throw new ServletException(ex);
        }

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

        //Indicamos si se tiene que ejecutar dentro de un iframe o no,si son obligatorios los avisos y contexto raiz
        try{
        	ConfiguracionDelegate config = DelegateUtil.getConfiguracionDelegate();
        	Properties configProps = config.obtenerConfiguracion();

        	getServletContext().setAttribute(Constants.MOSTRAR_EN_IFRAME,new Boolean(configProps.getProperty("sistra.iframe")).booleanValue());

			getServletContext().setAttribute(Constants.AVISOS_OBLIGATORIOS_NOTIFICACIONES,StringUtils.defaultString(configProps.getProperty("sistra.avisoObligatorioNotificaciones"), "false"));

			getServletContext().setAttribute(Constants.CONTEXTO_RAIZ,StringUtils.defaultString(configProps.getProperty("sistra.contextoRaiz.front"), ""));


        }catch(Exception ex){
        	log.error("Error obteniendo la variable iframe",ex);
        	throw new ServletException(ex);
        }

    }



    /**
     * Inicializa los idiomas soportados por la aplicación
     */
    protected void initLangs() {
        try {
            IdiomaDelegate delegate = DelegateUtil.getIdiomaDelegate();

            // Lenguaje por defecto.
            defaultLang = delegate.lenguajePorDefecto();
            log.info("Default lang: " + defaultLang);
            getServletContext().setAttribute(Constants.DEFAULT_LANG_KEY, defaultLang);

            // Todos los lenguajes soportados (incluido el por defecto).
            supportedLangs = delegate.listarLenguajes();
            log.info("Supported langs: " + supportedLangs);

        } catch (DelegateException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elije el lenguaje en el que se servira la petición.
     * @param request
     * @param response
     */
    protected void processLocale(HttpServletRequest request, HttpServletResponse response) {


    	// DESHABILITAMOS PROCESAMIENTO DEL IDIOMA. PARA QUE NO SE PUEDA CAMBIAR A MITAD DE UN TRAMITE.
    	if (true) return;

        // Si se ha indicado que no se debe fijar no hacemos nada.
        if (!moduleConfig.getControllerConfig().getLocale()) {
            return;
        }

        if (defaultLang == null) initLangs();

        HttpSession session = request.getSession(true);

        // Se ha especificado sobreescribir el locale?
        //String paramLang = request.getParameter("lang");
        String paramLang = request.getParameter("language");
        if (paramLang != null) {
            if (supportedLangs.contains(paramLang)) {
                log.info("Setting selected locale: " + paramLang);
                session.setAttribute(Globals.LOCALE_KEY, new Locale(paramLang));
                return;
            } else {
                log.info("Invalid selected locale: " + paramLang);
            }
        }

        // Si ya tenemos locale abandonamos.
        if (session.getAttribute(Globals.LOCALE_KEY) != null) {
            return;
        }

        // Buscamos un locale enviado por el navegador que nos coincida con los soportados.
        Enumeration enumer = request.getLocales();
        while (enumer.hasMoreElements()) {
            Locale locale = (Locale) enumer.nextElement();
            String lang = locale.getLanguage().trim();

            if (supportedLangs.contains(lang)) {
                log.info("Setting browser locale: " + lang);
                session.setAttribute(Globals.LOCALE_KEY, new Locale(lang));
                return;
            }
        }

        // Si no encontramos ninguno fijamos el por defecto.
        log.info("Setting default locale: " + defaultLang);
        session.setAttribute(Globals.LOCALE_KEY, new Locale(defaultLang));
    }

    public void process(HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("ISO-8859-15");
        }
        super.process(request, response);
    }

    protected void processPopulate(HttpServletRequest request,
                                   HttpServletResponse response,
                                   ActionForm form,
                                   ActionMapping mapping)
            throws ServletException {
        if (request.getAttribute(InstanciaManager.ID_INSTANCIA) == null) {
            String instancia = request.getParameter(InstanciaManager.ID_INSTANCIA);
            if (instancia != null) {
                request.setAttribute(InstanciaManager.ID_INSTANCIA, instancia);
            }
        }
        super.processPopulate(request, response, form, mapping);
    }

    @Override
    protected void processNoCache(HttpServletRequest request, HttpServletResponse response)
    {
    	// PATCH PARA MEJORAR CONTROL CACHE
        if(moduleConfig.getControllerConfig().getNocache())
        {
        	 response.setHeader("Pragma", "No-cache");
        	 response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
        	 response.setDateHeader("Expires", 1);

        }
    }

}
