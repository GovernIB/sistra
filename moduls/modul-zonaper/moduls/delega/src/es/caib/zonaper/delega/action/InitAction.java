package es.caib.zonaper.delega.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.delega.Constants;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/initAction"
 *  scope="request"
 *  validate="false"
 *  
 *  @struts.action-forward
 *  name="success" path=".mainLayout"
 */
public class InitAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String lang = (String)request.getParameter("lang");
		String language = (String)request.getParameter("language");
		
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
			setLocale(request,new Locale(ls_language));
		}		
		
		return mapping.findForward("success");    
	}

}
