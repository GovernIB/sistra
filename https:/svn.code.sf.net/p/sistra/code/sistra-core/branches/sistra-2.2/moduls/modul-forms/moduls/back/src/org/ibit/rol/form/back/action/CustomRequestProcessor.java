package org.ibit.rol.form.back.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.TilesRequestProcessor;

/**
 * <code>RequestProcessor</code> que añade funcionalidad para
 * los InitForm
 */
public class CustomRequestProcessor extends TilesRequestProcessor {

    public void process(HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("ISO-8859-15");
        }
        
        super.process(request, response);
    }
/*
    protected ActionForm processActionForm(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) {
        ActionForm form = super.processActionForm(request, response, mapping);
        if (form != null && form instanceof InitForm) {
            ((InitForm) form).init(mapping, request);
        }

        return form;
    }
*/
}
