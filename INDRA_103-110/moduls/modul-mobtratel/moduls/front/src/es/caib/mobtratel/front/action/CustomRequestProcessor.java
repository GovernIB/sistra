package es.caib.mobtratel.front.action;

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
			log.info("CharacterEncoding és null, emprant utf-8");
			request.setCharacterEncoding("utf-8");
		}else{
			log.info("CharacterEncoding NO és null, emprant " + request.getCharacterEncoding() );
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
