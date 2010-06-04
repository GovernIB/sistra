package es.caib.zonaper.back.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.zonaper.back.form.MostrarDocumentoForm;
import es.caib.zonaper.back.Constants;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;

/**
 * @struts.action
 *  name="mostrarDocumentoForm"
 *  path="/mostrarJustificante"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class MostrarJustificanteAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarDocumentoForm formulario 	= ( MostrarDocumentoForm ) form;
		EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
		EntradaPreregistro preregistro		= delegate.obtenerEntradaPreregistroReg( formulario.getCodigo() );
		
		ReferenciaRDS refRDS = new ReferenciaRDS();
		refRDS.setCodigo( preregistro.getCodigoRdsJustificante() );
		refRDS.setClave( preregistro.getClaveRdsJustificante() );
		
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS, preregistro.getIdioma());
		
		request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
	
		return mapping.findForward("success");
    }
}
