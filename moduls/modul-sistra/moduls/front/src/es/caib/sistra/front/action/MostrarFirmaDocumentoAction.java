package es.caib.sistra.front.action;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.MostrarFirmaDocumentoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FicheroFirma;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

/**
 * @struts.action
 *  name="mostrarFirmaDocumentoForm"
 *  path="/protected/mostrarFirmaDocumento"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="fail" path="/index.jsp"
 */
public class MostrarFirmaDocumentoAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarFirmaDocumentoForm formulario = ( MostrarFirmaDocumentoForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = null;

		String identificador = formulario.getIdentificador();
		int instancia = formulario.getInstancia();
		String nif = formulario.getNif();
		
		respuestaFront = delegate.recuperaFirmasDocumento(identificador, instancia, nif);
		
		this.setRespuestaFront( request, respuestaFront );
		
		Map mParams = respuestaFront.getParametros();
		
		String nombreFichero = ( String ) mParams.get( Constants.NOMBREFICHERO_KEY );
		byte[] datosFichero = ( byte[] ) mParams.get( Constants.DATOSFICHERO_KEY );
				
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nombreFichero );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, datosFichero );
		
		return mapping.findForward("success");		
    }
}
