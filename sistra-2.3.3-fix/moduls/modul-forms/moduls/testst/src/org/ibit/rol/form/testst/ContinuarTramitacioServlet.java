package org.ibit.rol.form.testst;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

/**
 * @web.servlet name="continuarTramitacio"
 * @web.servlet-mapping url-pattern="/continuarTramitacio"
 */
public class ContinuarTramitacioServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log("Continuar tramitacó");

        String token = request.getParameter("TOKEN");
        if (token == null) {
            log("Token és null");
            return;
        }

        log("Rebut token: " + token);


        final String inicialKey = "org.ibit.rol.form.testst.inicial@" + token;
        final String finalKey = "org.ibit.rol.form.testst.final@" + token;

        String xmlInicial = (String) getServletContext().getAttribute(inicialKey);
        String xmlFinal = (String) getServletContext().getAttribute(finalKey);

        getServletContext().removeAttribute(inicialKey);
        getServletContext().removeAttribute(finalKey);

        if (xmlInicial == null) {
            log("XML Inicial és null");
            return;
        }

        if (xmlFinal == null) {
            log("XML Final és null");
            return;
        }

        request.setAttribute("xmlInicial", xmlInicial);
        request.setAttribute("xmlFinal", xmlFinal);

        log("Enviant tornada");

        RequestDispatcher rd = request.getRequestDispatcher("/tornada.jsp");
        rd.forward(request, response);
    }
}
