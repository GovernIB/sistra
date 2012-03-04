package org.ibit.rol.form.front.registro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.TokenProcessor;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.conector.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.Enumeration;

/**
 * Mantiene el registro de {@link RegistroInstancia}.
 */
public class RegistroManager {

    private static final String REGISTRE = "org.ibit.rol.from.front.util.registre";

    public static final String ID_INSTANCIA = "ID_INSTANCIA";

    private static final Log log = LogFactory.getLog(RegistroManager.class);

    public static synchronized void registrarInstancia(HttpServletRequest request, InstanciaDelegate delegate) {
        String idInstancia = TokenProcessor.getInstance().generateToken(request);
        request.setAttribute(ID_INSTANCIA, idInstancia);
        request.getSession(true).setAttribute(REGISTRE + "@" + idInstancia, new RegistroInstancia(delegate));
        log.debug("Registrar instancia: " + idInstancia);
    }

    public static synchronized String preregistrarInstancia(HttpServletRequest request, InstanciaDelegate delegate) {
        String idInstancia = TokenProcessor.getInstance().generateToken(request);
        ServletContext sc = request.getSession().getServletContext();
        sc.setAttribute(REGISTRE + "@" + idInstancia, new RegistroInstancia(delegate));
        log.debug("Preregistrar instancia: " + idInstancia);
        return idInstancia;
    }

    public static synchronized boolean asignarInstanciaPreregistrada(HttpServletRequest request, String idInstancia) {
        ServletContext sc = request.getSession().getServletContext();
        RegistroInstancia registro = (RegistroInstancia) sc.getAttribute(REGISTRE + "@" + idInstancia);
        if (registro == null) return false;

        request.setAttribute(ID_INSTANCIA, idInstancia);
        request.getSession(true).setAttribute(REGISTRE + "@" + idInstancia, registro);
        sc.removeAttribute(REGISTRE + "@" + idInstancia);
        log.debug("Registrar instancia: " + idInstancia);
        return true;
    }

    public static InstanciaDelegate recuperarInstancia(HttpServletRequest request) {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);
        if (idInstancia == null) {
            return null;
        }

        RegistroInstancia registro = (RegistroInstancia) request.getSession().getAttribute(REGISTRE + "@" + idInstancia);
        if (registro == null) {
            return null;
        }

        return registro.getDelegate();
    }

    public static synchronized void desregistrarInstancia(HttpServletRequest request) {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);
        log.debug("Desregistrar instancia: " + idInstancia);
        HttpSession session = request.getSession(true);
        session.removeAttribute(REGISTRE + "@" + idInstancia);
    }

    public static synchronized void guardarResultados(HttpServletRequest request, Result[] results) {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);
        if (idInstancia == null) {
            return;
        }
        RegistroInstancia registro = (RegistroInstancia) request.getSession(true).getAttribute(REGISTRE + "@" + idInstancia);
        registro.setResults(results);
    }

    public static synchronized Result[] recuperarResultados(HttpServletRequest request) {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);
        if (idInstancia == null) {
            return null;
        }
        RegistroInstancia registro = (RegistroInstancia) request.getSession(true).getAttribute(REGISTRE + "@" + idInstancia);
        return registro.getResults();
    }

    public static boolean tieneInstancias(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Enumeration attrNamesEnum = session.getAttributeNames();
        while (attrNamesEnum.hasMoreElements()) {
            String attrName = (String) attrNamesEnum.nextElement();
            if (attrName.startsWith(REGISTRE + "@")) {
                return true;
            }
        }
        return false;
    }
}
