package org.ibit.rol.form.testst;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.List;

/**
 * @web.servlet name="iniciForm"
 * @web.servlet-mapping url-pattern="/iniciForm"
 */
public class IniciFormServlet extends HttpServlet {

    private String urlTramitacio;
    private String urlRedireccio;
    
    
    private String tokenName;

    public void init() throws ServletException {
        super.init();
        urlTramitacio = UrlForms.getUrl() + "/formfront/iniciTelematic.do";
        urlRedireccio = "/formfront/continuacioTelematic.do";
        tokenName = "ID_INSTANCIA";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log("Iniciar tramitació");

        String xmlData = "";
        String xmlConfig = "";

        if (FileUpload.isMultipartContent(request)) {
            try {
                DiskFileUpload fileUpload = new DiskFileUpload();
                List fileItems = fileUpload.parseRequest(request);

                for (int i = 0; i < fileItems.size(); i++) {
                    FileItem fileItem = (FileItem) fileItems.get(i);
                    if (fileItem.getFieldName().equals("xmlData")) {
                        xmlData = fileItem.getString();
                    }

                    if (fileItem.getFieldName().equals("xmlConfig")) {
                        xmlConfig = fileItem.getString();
                    }
                }
            } catch (FileUploadException e) {
                throw new UnavailableException("Error uploading", 1);
            }
        } else {
            xmlData = request.getParameter("xmlData");
            xmlConfig = request.getParameter("xmlConfig");
        }

        log("XML Data: " + xmlData);
        log("XML Config: " + xmlConfig);

        log("Enviant dades a: " + urlTramitacio);

        String token = iniciarTramite(xmlData, xmlConfig);

        if (token == null) {
            log("Token és null");
        } else {
            log("Token: " + token);

            StringBuffer url = new StringBuffer(urlRedireccio);
            url.append(urlRedireccio.indexOf('?') == -1 ? '?' : '&');
            url.append(tokenName);
            url.append('=');
            url.append(token);

            log("Redireccionant a: " + url.toString());
            response.sendRedirect(response.encodeRedirectURL(url.toString()));
        }
    }

    private String iniciarTramite(String xmlData, String xmlConfig) {
        HttpClient client = new HttpClient();

        PostMethod method = new PostMethod(urlTramitacio);
        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        method.getParams().setContentCharset("UTF-8");
        method.addParameter("xmlData", xmlData);
        method.addParameter("xmlConfig", xmlConfig);

        try {
            int status = client.executeMethod(method);

            if (status != HttpStatus.SC_OK) {
                log("Error iniciando tramite: " + status);
                return null;
            }

            return method.getResponseBodyAsString();

        } catch (IOException e) {
            return null;
        } finally {
            method.releaseConnection();
        }

    }
}
