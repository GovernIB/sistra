package es.caib.bantel.front.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @struts.action
 *  path="/retornoFirmaWeb"
 * 
 */
public class RetornoFirmaWebAction extends Action
{
	private static Log _log = LogFactory.getLog( RetornoFirmaWebAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
			request.setCharacterEncoding("UTF-8");
			
			String error = (String) request.getParameter("error");
			String firma = (String) request.getParameter("firma");
			 
	        // Retornamos html que notificara de que se ha realiza el upload
	        response.setContentType("text/html");
	        response.setHeader("Cache-Control", "no-cache");
	        final PrintWriter pw = response.getWriter();
	        pw.println("<html>");
	        pw.println("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
	        pw.println("<script type=\"text/javascript\">");
	        pw.println("<!--");
	        if ("true".equals(error)) {
	        	pw.println("var error = 'true';");
	        	pw.println("var firma = '';");
	        } else {
	        	pw.println("var error = 'false';");
	        	pw.println("var firma = '" + firma + "';");
	        }
	        pw.println("top.callbackFirmaWeb(error, firma);");
	        pw.println("-->");
	        pw.println("</script>");
	        pw.println("</head>");
	        pw.println("<body>");
	        pw.println("</body>");
	        pw.println("</html>");
	        
	        return null;
			 
	}
	
}
