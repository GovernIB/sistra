package es.caib.audita.front.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.front.Constants;
import es.caib.audita.front.form.InitForm;


/**
 * @struts.action
 *  name="initForm"
 *  path="/init"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/inicio.do"
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
		return mapping.findForward( "success" );    }

}
