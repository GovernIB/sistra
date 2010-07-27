package es.caib.redose.front.action;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.front.util.RedoseFrontRequestHelper;


/**
 * Action Base.
 */
public abstract class BaseAction extends Action
{
	/**
     * Return the user's currently selected Locale.
     * @param request The request we are processing
     */
    public  Locale getLocale(HttpServletRequest request) 
    {
        return RedoseFrontRequestHelper.getLocale( request );
    }
    
    public String getLang(HttpServletRequest request) {
        return  RedoseFrontRequestHelper.getLang( request );
    }
    
    	public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception; 

}
