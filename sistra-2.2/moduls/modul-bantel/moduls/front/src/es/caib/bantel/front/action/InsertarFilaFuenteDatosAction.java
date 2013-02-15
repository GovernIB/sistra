package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.form.DetalleFuenteDatosForm;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;

/**
 * @struts.action
 *  name="detalleFuenteDatosForm"
 *  path="/insertarFilaFuenteDatos"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalleFuenteDatos"
 * 
 */
public class InsertarFilaFuenteDatosAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleFuenteDatosForm detalleFuenteDatos = ( DetalleFuenteDatosForm ) form;
		FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
		delegate.altaFilaFuenteDatos(detalleFuenteDatos.getIdentificador());
		
		response.sendRedirect("detalleFuenteDatos.do?identificador=" + detalleFuenteDatos.getIdentificador());
		return null;
		
		/*
		FuenteDatos fuenteDatos = delegate.obtenerFuenteDatos(detalleFuenteDatos.getIdentificador());
		request.setAttribute( "fuenteDatos", fuenteDatos );
		return mapping.findForward( "success" );
		*/
    }

}
