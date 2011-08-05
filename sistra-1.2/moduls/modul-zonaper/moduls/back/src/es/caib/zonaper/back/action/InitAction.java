package es.caib.zonaper.back.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.zonaper.back.Constants;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/init"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class InitAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		
		// Obtenemos nombre usuario y lo almacenamos en sesion
		String nomUsua = PluginFactory.getInstance().getPluginLogin().getNombreCompleto(request.getUserPrincipal());
		request.getSession().setAttribute(Constants.NOMBRE_USUARIO_KEY,nomUsua);
		
		// Obtenemos url aplicacion registro y lo almacenamos en sesion
		String urlAplicRegistro = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("back.urlAplicacionRegistro");
		if (StringUtils.isBlank(urlAplicRegistro)){
			urlAplicRegistro = "";
		}
		request.getSession().setAttribute(Constants.URL_APLICACION_REGISTRO,urlAplicRegistro);
		
		// ELIMINAR TODAS LAS PETICIONES ANTIGUAS
		Enumeration ats = request.getSession().getAttributeNames();
		while (ats.hasMoreElements()){
			String key = (String) ats.nextElement();
			if (key.startsWith(Constants.IMPRESION_SELLO_KEY)) request.getSession().removeAttribute(key);
		}
		return mapping.findForward( "success" );
    }

}
