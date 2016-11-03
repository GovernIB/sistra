package es.caib.zonaper.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.admin.Constants;




/**
 * @struts.action
 *  path="/init"
 *  scope="request"
 *  validate="false"
 */
public class InitAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String lang = (String)request.getParameter("lang");
		String language = (String)request.getParameter("language");
		String urlBack = ( String ) request.getParameter( "urlBack" );
		
		if((lang != null) || (language != null))
		{
			String ls_language = null;
			if((language != null) && (!language.equals("")))
			{
				ls_language = language;
			}
			else
			{
				if((lang != null) && (!lang.equals("")))
				{
					ls_language = lang;
				}
				else
				{
					ls_language = Constants.DEFAULT_LANG;
				}
			}
		}
		response.sendRedirect( urlBack );
		return null;    
	}

}
