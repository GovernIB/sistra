package org.ibit.rol.form.testst;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

/**
 * @web.servlet name="continuarCancelacio"
 * @web.servlet-mapping url-pattern="/continuarCancelacio"
 */
public class ContinuarCancelacioServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log("Continuar cancel·lació");

        String token = request.getParameter("TOKEN");
        if (token == null) {
            log("Token és null");
            return;
        }

        log("Rebut token: " + token);


        final String cancelKey = "org.ibit.rol.form.testst.cancelacio@" + token;

        Boolean canceled = (Boolean) getServletContext().getAttribute(cancelKey);

        getServletContext().removeAttribute(cancelKey);

        if (canceled == null) {
            log("Canceled és null");
            return;
        }

        request.setAttribute("canceled", canceled);

        log("Enviant tornada");

        RequestDispatcher rd = request.getRequestDispatcher("/cancelacio.jsp");
        rd.forward(request, response);
    }
}
