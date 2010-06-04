package es.caib.pagos.front.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.TilesRequestProcessor;

import es.caib.pagos.front.Constants;


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
        String paramLang = request.getParameter("lang");
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
        super.processPopulate(request, response, form, mapping);
    }

}
