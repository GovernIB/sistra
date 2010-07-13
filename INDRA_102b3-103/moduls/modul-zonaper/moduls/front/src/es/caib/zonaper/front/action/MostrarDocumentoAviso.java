package es.caib.zonaper.front.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.MostrarDocumentoAvisoForm;
import es.caib.zonaper.model.DocumentoEventoExpediente;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="mostrarDocumentoAvisoForm"
 *  path="/protected/mostrarDocumentoAviso"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 *  
 * @struts.action-forward
 *  name="fail" path=".mensaje"
 */
public class MostrarDocumentoAviso extends BaseAction
{
	private static Log log = LogFactory.getLog( MostrarDocumentoAviso.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		MostrarDocumentoAvisoForm formulario = ( MostrarDocumentoAvisoForm ) form;
		
		EventoExpediente eventoExpediente;
		ElementoExpediente elementoExpediente;
		if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
			eventoExpediente =  DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAnonimo(formulario.getCodigoAviso(),this.getIdPersistencia(request));
			elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(ElementoExpediente.TIPO_AVISO_EXPEDIENTE,eventoExpediente.getCodigo(),this.getIdPersistencia(request));
		}else{
			eventoExpediente =  DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAutenticado(formulario.getCodigoAviso());
			elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(ElementoExpediente.TIPO_AVISO_EXPEDIENTE,eventoExpediente.getCodigo());
		}
		
		
		for (Iterator it = eventoExpediente.getDocumentos().iterator();it.hasNext();){
			DocumentoEventoExpediente documentoExpediente = (DocumentoEventoExpediente) it.next();
			
			if (documentoExpediente.getCodigo().longValue() == formulario.getCodigo().longValue()){
				ReferenciaRDS refRDS = new ReferenciaRDS();
				refRDS.setCodigo( documentoExpediente.getRdsCodigo().longValue() );
				refRDS.setClave( documentoExpediente.getRdsClave() );
				
				RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
				
				DocumentoRDS documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS, elementoExpediente.getExpediente().getIdioma());
				
				request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
				request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
				
				return mapping.findForward("success");
			}
			
		}
		
		return mapping.findForward( "fail" );
		
		
    }
}
