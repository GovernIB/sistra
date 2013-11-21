package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.form.DetalleFuenteDatosForm;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="detalleFuenteDatosForm"
 *  path="/detalleFuenteDatos"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalleFuenteDatos"
 * 
 */
public class DetalleFuenteDatosAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleFuenteDatosForm detalleFuenteDatos = ( DetalleFuenteDatosForm ) form;
		FuenteDatos fuenteDatos = DelegateUtil.getFuenteDatosDelegate().obtenerFuenteDatos(detalleFuenteDatos.getIdentificador());
		request.setAttribute( "fuenteDatos", fuenteDatos );
		return mapping.findForward( "success" );
    }

}
