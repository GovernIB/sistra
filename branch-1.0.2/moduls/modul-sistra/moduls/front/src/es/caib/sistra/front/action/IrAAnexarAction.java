package es.caib.sistra.front.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.IrAAnexarForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="irAAnexarForm"
 *  path="/protected/irAAnexar"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".irAAnexar"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class IrAAnexarAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		IrAAnexarForm formulario = ( IrAAnexarForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = delegate.pasoActual();
		TramiteFront tramite = respuestaFront.getInformacionTramite();
		ArrayList arlAnexos = tramite.getAnexos();
		for ( int i = 0; i < arlAnexos.size(); i++ )
		{
			DocumentoFront anexo = ( DocumentoFront ) arlAnexos.get( i );
			if ( formulario.getIdentificador().equals( anexo.getIdentificador() ) )
			{
				request.setAttribute( "anexo", anexo );
				// Si debe firmarse digitalmente establecemos atributo para la carga de entorno de firma
				if (anexo.isFirmar()){
					request.setAttribute(Constants.MOSTRAR_FIRMA_DIGITAL,"S");
				}
				break;
			}
		}

		this.setRespuestaFront( request, respuestaFront );
		return mapping.findForward("success");
	}
}
