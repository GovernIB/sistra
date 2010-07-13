package es.caib.sistra.front.action.formulario;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.action.BaseAction;
import es.caib.sistra.front.form.formulario.IrAFirmarFormularioForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.util.ConvertUtil;

/**
 * @struts.action
 *  name="irAFirmarFormularioForm"
 *  path="/protected/irAFirmarFormulario"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".irAFirmarFormulario"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class IrAFirmarFormularioAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		IrAFirmarFormularioForm formulario = ( IrAFirmarFormularioForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = delegate.pasoActual();
		TramiteFront tramite = respuestaFront.getInformacionTramite();
		ArrayList arlFormularios = tramite.getFormularios();
		for ( int i = 0; i < arlFormularios.size(); i++ )
		{
			DocumentoFront f = ( DocumentoFront ) arlFormularios.get( i );
			if ( formulario.getIdentificador().equals( f.getIdentificador() ) )
			{
				request.setAttribute( "formulario", f );
				break;
			}
		}
		respuestaFront = delegate.irAFirmarFormulario( formulario.getIdentificador(), formulario.getInstancia() );
		String xmlFormulario = ( String )respuestaFront.getParametros().get( "datos" );
		String base64EncXml = ConvertUtil.cadenaToBase64UrlSafe( xmlFormulario );
		request.setAttribute( "base64XmlForm", base64EncXml );
		request.setAttribute(Constants.MOSTRAR_FIRMA_DIGITAL,"S");
		this.setRespuestaFront( request, respuestaFront );
		return mapping.findForward("success");
	}
}
