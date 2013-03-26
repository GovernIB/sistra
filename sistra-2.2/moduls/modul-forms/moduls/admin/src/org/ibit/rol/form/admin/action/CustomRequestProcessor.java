package org.ibit.rol.form.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import org.ibit.rol.form.admin.form.InitForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.TilesRequestProcessor;

import java.io.IOException;

/**
 * <code>RequestProcessor</code> que añade funcionalidad para
 * los InitForm
 */
public class CustomRequestProcessor extends TilesRequestProcessor {

    protected ActionForm processActionForm(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) {
        ActionForm form = super.processActionForm(request, response, mapping);
        if (form != null && form instanceof InitForm) {
            ((InitForm) form).init(mapping, request);
        }

        return form;
    }

    public void process(HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        super.process(request, response);
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
