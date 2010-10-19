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
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.MostrarDocumentoForm;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.EntradaTelematicaDelegate;

/**
 * @struts.action
 *  name="mostrarDocumentoForm"
 *  path="/protected/mostrarJustificante"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 */
public class MostrarJustificanteAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarDocumentoForm formulario = ( MostrarDocumentoForm ) form;
		
		Entrada entrada;
		long codForJust=-1;
		String claForJust=null;
		
		if ( 	formulario.getTipoEntrada() == ConstantesAsientoXML.TIPO_ENVIO ||
				formulario.getTipoEntrada() == ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA )
		{
			EntradaTelematicaDelegate delegate = DelegateUtil.getEntradaTelematicaDelegate();
			
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
				entrada = delegate.obtenerEntradaTelematicaAnonima( formulario.getCodigoEntrada(),this.getIdPersistencia(request));
			}else{
				entrada = delegate.obtenerEntradaTelematicaAutenticada( formulario.getCodigoEntrada() );
			}
		}
		else
		{
			EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
				entrada = delegate.obtenerEntradaPreregistroAnonima( formulario.getCodigoEntrada(),this.getIdPersistencia(request));
			}else{
				entrada = delegate.obtenerEntradaPreregistroAutenticada( formulario.getCodigoEntrada() );
			}
			
			//Comprobamos si la entrada tiene un formulario marcado como justificante
			for (Iterator it= entrada.getDocumentos().iterator();it.hasNext();){
				DocumentoEntradaPreregistro d = (DocumentoEntradaPreregistro) it.next();
				if (d.getTipoDocumento() == 'G') {
					codForJust = d.getCodigoRDS();
					claForJust = d.getClaveRDS();
					break;
				}
			}
		}
		
		
		if (codForJust != -1){
			// Mostramos formulario como justificante		
			ReferenciaRDS refRDS = new ReferenciaRDS();
			refRDS.setCodigo( codForJust );
			refRDS.setClave( claForJust );
			
			RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
			DocumentoRDS documentoRDS = null;
			// Si es preregistro mostramos justificante con copias para admon/interesado
			if (entrada instanceof EntradaTelematica ){
				documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS, entrada.getIdioma());
			}else{
				documentoRDS = rdsDelegate.consultarDocumentoFormateadoCopiasInteresadoAdmon(refRDS, entrada.getIdioma());
			}
			
			request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
			request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
			
			return mapping.findForward("success");
		}else{
			//	Mostramos justificante estandard		
			ReferenciaRDS refRDS = new ReferenciaRDS();
			refRDS.setCodigo( entrada.getCodigoRdsJustificante() );
			refRDS.setClave( entrada.getClaveRdsJustificante() );
			
			RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
			DocumentoRDS documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS, entrada.getIdioma());
			
			request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
			request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
			
			return mapping.findForward("success");		
		}
    }

	
	
}
