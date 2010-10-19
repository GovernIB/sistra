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
		String mostrarFirmaDigital = "N";
		
		// Buscamos anexo
		boolean enc = false;
		DocumentoFront anexo = null;
		for ( int i = 0; i < arlAnexos.size(); i++ )
		{
			anexo = ( DocumentoFront ) arlAnexos.get( i );
			if ( formulario.getIdentificador().equals( anexo.getIdentificador() ) )
			{
				enc = true;
				break;
			}
		}
		if (!enc){
			throw new Exception("No se encuentra anexo");
		}
		
		// Comprobamos si hay que realizar la firma delegada o digital
		if (anexo.isFirmar()){
			if (anexo.isFirmaDelegada()){
				mostrarFirmaDigital = "D";
			}else{
				mostrarFirmaDigital = "S";
			}
		}

		
		request.setAttribute( "anexo", anexo );
		request.setAttribute(Constants.MOSTRAR_FIRMA_DIGITAL,mostrarFirmaDigital);

		this.setRespuestaFront( request, respuestaFront );
		return mapping.findForward("success");
	}
}
