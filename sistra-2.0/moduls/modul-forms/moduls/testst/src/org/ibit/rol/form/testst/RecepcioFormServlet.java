package org.ibit.rol.form.testst;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.List;

/**
 * @web.servlet name="recepcioForm"
 * @web.servlet-mapping url-pattern="/recepcioForm"
 */
public class RecepcioFormServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log("Recepció formulari");        
        log("Charset: " + request.getCharacterEncoding());

        String xmlInicial = "";
        String xmlFinal = "";
        if (FileUpload.isMultipartContent(request)) {
            try {
                DiskFileUpload fileUpload = new DiskFileUpload();
                List fileItems = fileUpload.parseRequest(request);

                for (int i = 0; i < fileItems.size(); i++) {
                    FileItem fileItem = (FileItem) fileItems.get(i);
                    if (fileItem.getFieldName().equals("xmlInicial")) {
                        xmlInicial = fileItem.getString();
                    }

                    if (fileItem.getFieldName().equals("xmlFinal")) {
                        xmlFinal = fileItem.getString();
                    }
                }
            } catch (FileUploadException e) {
                throw new UnavailableException("Error uploading", 1);
            }
        } else {
            xmlInicial = request.getParameter("xmlInicial");
            xmlFinal = request.getParameter("xmlFinal");
        }

        log("XML Inicial: " + xmlInicial);
        log("XML Final: " + xmlFinal);

        byte[] bytes = String.valueOf(System.currentTimeMillis()).getBytes();
        byte[] encBytes = Base64.encodeBase64(bytes);
        String token = new String(encBytes, "UTF-8");

        getServletContext().setAttribute("org.ibit.rol.form.testst.inicial@" + token, xmlInicial);
        getServletContext().setAttribute("org.ibit.rol.form.testst.final@" + token, xmlFinal);

        log("Tornant token: " + token);

        response.reset();
        response.setContentLength(encBytes.length);
        response.setContentType("text/plain; charset=UTF-8");
        response.getOutputStream().write(encBytes);
        response.flushBuffer();
    }
}
