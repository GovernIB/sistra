package es.caib.sistra.front.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.MostrarDocumentoConsultaForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="mostrarDocumentoConsultaForm"
 *  path="/protected/mostrarDocumentoConsulta"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="successUrl" path=".redireccion"
 *  
 * @struts.action-forward
 *  name="fail" path="/index.jsp"
 */
public class MostrarDocumentoConsulta extends BaseAction
{

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		MostrarDocumentoConsultaForm formulario = ( MostrarDocumentoConsultaForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		
		RespuestaFront respuestaFront = delegate.mostrarDocumentoConsulta( formulario.getIdx() );
		
		Map params = respuestaFront.getParametros();

		String urlFichero = ( String ) params.get( "urlfichero" );
		if ( urlFichero != null )
		{
			request.setAttribute( "accionRedireccion", urlFichero );
			return mapping.findForward( "successUrl" );
		}
			
		String nombreFichero = ( String ) params.get( "nombrefichero" );
		byte[] datosFichero = ( byte [] ) params.get( "datosfichero" ); 
		
		
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nombreFichero );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, datosFichero );
		
		return mapping.findForward("success");
	}

}
