package es.caib.sistra.back.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

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

import es.caib.sistra.back.Constants;
import es.caib.sistra.persistence.delegate.ConfiguracionDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;

/**
 * <code>RequestProcessor</code> que añade funcionalidad para
 * los InitForm
 */
public class CustomRequestProcessor extends TilesRequestProcessor {

    protected static Log log = LogFactory.getLog(CustomRequestProcessor.class);

    private String defaultLang = null;
    private List supportedLangs = null;

    public void init(ActionServlet actionServlet,ModuleConfig moduleConfig) throws ServletException{

    	super.init(actionServlet,moduleConfig);

    	String obligAvisosNotif;
    	String contextoSistra;
    	try {
	    	// Indicamos si son obligatorias los avisos para las notificaciones
	        ConfiguracionDelegate config = DelegateUtil.getConfiguracionDelegate();
	    	Properties configProps = config.obtenerConfiguracion();
	    	contextoSistra = StringUtils.defaultString(configProps.getProperty("sistra.contextoRaiz.back"), "");
	        obligAvisosNotif = StringUtils.defaultString(configProps.getProperty("sistra.avisoObligatorioNotificaciones"), "false");
    	}catch (Exception ex){
        	log.error("Error obteniendo obligatoriedad avisos para notificaciones (ponemos a false): " + ex.getMessage(),ex);
        	obligAvisosNotif = "false";
        	contextoSistra = "";
        }
    	getServletContext().setAttribute(Constants.AVISOS_OBLIGATORIOS_NOTIFICACIONES,obligAvisosNotif);
    	getServletContext().setAttribute(Constants.CONTEXTO_RAIZ_BACK, contextoSistra);
    }

    /**
     * Inicializa los idiomas soportados por la aplicación
     */
    protected void initLangs() {
        try {
            //IdiomaDelegate delegate = DelegateUtil.getIdiomaDelegate();

            // Lenguaje por defecto.
            defaultLang = Constants.DEFAULT_LANG;
            log.info("Default lang: " + defaultLang);
            getServletContext().setAttribute(Constants.DEFAULT_LANG_KEY, defaultLang);

            // Todos los lenguajes soportados (incluido el por defecto).
            //supportedLangs = delegate.listarLenguajes();
            supportedLangs = new ArrayList();
            supportedLangs.add( "ca" );
            supportedLangs.add( "es" );
            supportedLangs.add( "en" );
            supportedLangs.add( "de" );
            log.info("Supported langs: " + supportedLangs);

        } catch (Exception e) {
            throw new RuntimeException(e);
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

        if (defaultLang == null) initLangs();

        HttpSession session = request.getSession(true);

        // Se ha especificado sobreescribir el locale?
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

        Locale local = (Locale) session.getAttribute(Globals.LOCALE_KEY);
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
