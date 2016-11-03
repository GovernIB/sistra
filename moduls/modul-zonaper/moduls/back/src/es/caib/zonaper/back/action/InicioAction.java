package es.caib.zonaper.back.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.back.Constants;

/**
 * @struts.action
 *  path="/inicio"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class InicioAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		
		// Comprobamos si esta configurado la aplicacion de registro
		String urlInicio = (String) request.getSession().getAttribute(Constants.URL_APLICACION_REGISTRO);
				
		// Si no esta configurado mostramos pagina inicial
		if (StringUtils.isBlank(urlInicio)){
			return mapping.findForward( "success" );
		}else{		
			// Si hay configurado el enlace a la aplicacion de registro, redirigimos a la aplicacion
			response.sendRedirect(urlInicio);
			return null;
		}
		
    }

}
