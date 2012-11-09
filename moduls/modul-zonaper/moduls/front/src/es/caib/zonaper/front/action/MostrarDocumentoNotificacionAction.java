package es.caib.zonaper.front.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.notificaciones.MostrarDocumentoNotificacionForm;
import es.caib.zonaper.model.DocumentoNotificacionTelematica;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="mostrarDocumentoNotificacionForm"
 *  path="/protected/mostrarDocumentoNotificacion"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="fail" path=".mensaje"
 */
public class MostrarDocumentoNotificacionAction extends BaseAction
{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		MostrarDocumentoNotificacionForm formulario = ( MostrarDocumentoNotificacionForm ) form;
		NotificacionTelematica not;
		if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
			not = DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAnonima(new Long(formulario.getCodigoNotificacion()),this.getIdPersistencia(request));
		}else{
			not = DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAutenticada(new Long(formulario.getCodigoNotificacion()));
		}
		if(not.getFechaAcuse() != null && !not.isRechazada()){
			if(formulario.getCodigo() > 0){
				for (Iterator it=not.getDocumentos().iterator();it.hasNext();){
					DocumentoNotificacionTelematica doc = (DocumentoNotificacionTelematica) it.next();
					if (doc.getCodigo().longValue() == formulario.getCodigo()) {
						ReferenciaRDS referenciaRDS = new ReferenciaRDS();
						referenciaRDS.setCodigo( doc.getCodigoRDS() );
						referenciaRDS.setClave( doc.getClaveRDS() );
						DocumentoRDS documentoRDS = DelegateRDSUtil.getRdsDelegate().consultarDocumentoFormateado( referenciaRDS ,doc.getNotificacionTelematica().getIdioma());
						request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
						request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
						return mapping.findForward( "success" );
					}
				}
			}else{
				ReferenciaRDS refRDS = new ReferenciaRDS();
				refRDS.setCodigo( not.getCodigoRdsJustificante() );
				refRDS.setClave( not.getClaveRdsJustificante() );
				
				DocumentoRDS documentoRDS = DelegateRDSUtil.getRdsDelegate().consultarDocumentoFormateado(refRDS, not.getIdioma());
				
				request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
				request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
		
				return mapping.findForward("success");	
			}
		}
		return mapping.findForward( "fail" );
		
		
		
	}

}
