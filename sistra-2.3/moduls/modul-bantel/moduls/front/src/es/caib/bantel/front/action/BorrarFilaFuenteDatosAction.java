package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.form.BorrarFilaFuenteDatosForm;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;

/**
 * @struts.action
 *  name="borrarFilaFuenteDatosForm"
 *  path="/borrarFilaFuenteDatos"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalleFuenteDatos"
 * 
 */
public class BorrarFilaFuenteDatosAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BorrarFilaFuenteDatosForm borraFilaForm = ( BorrarFilaFuenteDatosForm ) form;
		FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
		delegate.borrarFilaFuenteDatos(borraFilaForm.getIdentificador(), borraFilaForm.getNumfila());
		
		response.sendRedirect("detalleFuenteDatos.do?identificador=" + borraFilaForm.getIdentificador());
		return null;
		
		/*
		FuenteDatos fuenteDatos = delegate.obtenerFuenteDatos(borraFilaForm.getIdentificador());
		request.setAttribute( "fuenteDatos", fuenteDatos );
		return mapping.findForward( "success" );
		*/
    }

}
