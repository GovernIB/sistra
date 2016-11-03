package es.caib.mobtratel.front.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.Constants;
import es.caib.mobtratel.front.form.InitForm;



/**
 * @struts.action
 *  name="initForm"
 *  path="/initAction"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".busqueda"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class InitAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		InitForm initForm = ( InitForm ) form;
		
		if ( !StringUtils.isEmpty( initForm.getLanguage() ) || !StringUtils.isEmpty( initForm.getLang() ) )
		{
			String language = initForm.getLanguage();
			language = StringUtils.isEmpty( language ) ? ( !StringUtils.isEmpty( initForm.getLang() ) ? initForm.getLang() : Constants.DEFAULT_LANG ) : language;
	 		setLocale(request, new Locale( language ) );
		}
		
		return mapping.findForward( "success" );
    }
}
