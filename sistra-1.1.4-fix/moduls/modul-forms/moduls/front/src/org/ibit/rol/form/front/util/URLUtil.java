package org.ibit.rol.form.front.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utilitat per construir URLs.
 */
public class URLUtil {

    /**
     * Obté la url completa amb el protocol segur o no per redireccionar. 
     * @param request Petició original.
     * @param response Response actual.
     * @param secure Ha de ser una petició segura?.
     * @param relativeUrl Url relativa a l'aplicació, no inclou el contextPath.
     * @return Url completa preparada per redireccionar.
     */
    public static String getRedirectUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        boolean secure,
                                        String relativeUrl) {
        String requestPort = request.getSession(true).getServletContext().
                getInitParameter(secure ? "secure.port" : "default.port");

        StringBuffer url = new StringBuffer();
        if (secure) {
            url.append("https");
        } else {
            url.append("http");
        }
        url.append("://").append(request.getServerName());

        if (secure ? !"443".equals(requestPort) : !"80".equals(requestPort)) {
            url.append(':').append(requestPort);
        }

        url.append(request.getContextPath()).append(relativeUrl);
        if (request.getQueryString() != null) {
            url.append('?').append(request.getQueryString());
        }
        return response.encodeRedirectURL(url.toString());
    }
}
