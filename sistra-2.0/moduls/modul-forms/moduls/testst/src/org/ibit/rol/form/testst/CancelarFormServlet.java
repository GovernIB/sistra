package org.ibit.rol.form.testst;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @web.servlet name="cancelarForm"
 * @web.servlet-mapping url-pattern="/cancelarForm"
 */
public class CancelarFormServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log("Cancel·lació formulari");

        byte[] bytes = String.valueOf(System.currentTimeMillis()).getBytes();
        byte[] encBytes = Base64.encodeBase64(bytes);
        String token = new String(encBytes, "UTF-8");

        getServletContext().setAttribute("org.ibit.rol.form.testst.cancelacio@" + token, Boolean.TRUE);

        log("Tornant token: " + token);

        response.reset();
        response.setContentLength(encBytes.length);
        response.setContentType("text/plain; charset=UTF-8");
        response.getOutputStream().write(encBytes);
        response.flushBuffer();
    }
}
