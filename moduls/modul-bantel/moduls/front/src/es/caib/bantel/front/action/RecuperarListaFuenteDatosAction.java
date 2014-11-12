package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.ListaFuenteDatosForm;

/**
 * @struts.action
 *  name="listaFuenteDatosForm"
 *  path="/listaFuenteDatos"
 *  scope="session"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".fuenteDatos"
 *
 */
public class RecuperarListaFuenteDatosAction extends BaseAction
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		ListaFuenteDatosForm formularioBusqueda = ( ListaFuenteDatosForm ) form;
		request.getSession().setAttribute(Constants.FUENTE_DATOS_PAGINA_ACTUAL, new Integer(formularioBusqueda.getPagina()));
		return mapping.findForward( "success" );
    }
	
	
}
