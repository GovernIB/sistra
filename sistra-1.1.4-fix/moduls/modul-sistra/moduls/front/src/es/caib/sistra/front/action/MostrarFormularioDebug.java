package es.caib.sistra.front.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.MostrarDocumentoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="mostrarDocumentoForm"
 *  path="/protected/mostrarFormularioDebug"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 *   
 */
public class MostrarFormularioDebug extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarDocumentoForm formulario = ( MostrarDocumentoForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = null;

		String identificador = formulario.getIdentificador();
		int instancia = formulario.getInstancia();
		
		respuestaFront = delegate.mostrarFormularioDebug(identificador,instancia );
		this.setRespuestaFront( request, respuestaFront );
		
		Map mParams = respuestaFront.getParametros();
		String nombreFichero = ( String ) mParams.get( Constants.NOMBREFICHERO_KEY );
		byte[] datosFichero = ( byte[] ) mParams.get( Constants.DATOSFICHERO_KEY );
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nombreFichero );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, datosFichero );
		return mapping.findForward("success");
		
    }
}
