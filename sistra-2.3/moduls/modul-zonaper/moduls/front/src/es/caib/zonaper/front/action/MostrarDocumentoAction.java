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
import es.caib.zonaper.model.DocumentoEntrada;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaTelematica;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="mostrarDocumentoForm"
 *  path="/protected/mostrarDocumento"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 *  
 *  @struts.action-forward
 *  name="successUrl" path="/protected/mostrarDetalleElemento.do"
 *  
 * @struts.action-forward
 *  name="fail" path=".mensaje"
 */

public class MostrarDocumentoAction extends BaseAction
{	
	//private static Log log = LogFactory.getLog( MostrarDocumentoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarDocumentoForm formulario = ( MostrarDocumentoForm ) form;
		
		DocumentoEntrada documento=null;	
		Entrada entradaDocumento=null;
		
		// Obtenemos entrada asociada al documento			
		if ( formulario.getTipoEntrada() == ConstantesAsientoXML.TIPO_ENVIO ||
			 formulario.getTipoEntrada() == ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA )
		{
			// - Entrada telematica
			EntradaTelematica entrada;
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
				entrada = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAnonima(formulario.getCodigoEntrada(),this.getIdPersistencia(request));
			}else{
				entrada = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAutenticada(formulario.getCodigoEntrada());
			}
			entradaDocumento = entrada;	
			
			// - Obtenemos documento
			for (Iterator it = entrada.getDocumentos().iterator();it.hasNext();){
				DocumentoEntradaTelematica documentoET = (DocumentoEntradaTelematica) it.next();
				if (documentoET.getCodigo().longValue() == formulario.getCodigoDocumento().longValue()){
					documento = documentoET;			
					break;
				}
			}
		}		
		else
		{
			// 	- Entrada preregistro						
			EntradaPreregistro entrada;
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
				entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAnonima(formulario.getCodigoEntrada(),this.getIdPersistencia(request));
			}else{
				entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAutenticada(formulario.getCodigoEntrada());
			}
			entradaDocumento = entrada;		
			
			// - Obtenemos documento
			for (Iterator it = entrada.getDocumentos().iterator();it.hasNext();){
				DocumentoEntradaPreregistro documentoEP = (DocumentoEntradaPreregistro) it.next();
				if (documentoEP.getCodigo().longValue() == formulario.getCodigoDocumento().longValue()){
					documento = documentoEP;			
					break;
				}
			}
		}
		
		// Comprobamos que exista el documento en la entrada con ese codigo		
		if (documento == null){
			return mapping.findForward("fail");
		}
		
		// Construimos referencia RDS del documento
		ReferenciaRDS refRDS = new ReferenciaRDS();
		refRDS.setCodigo( documento.getCodigoRDS() );
		refRDS.setClave( documento.getClaveRDS() );
		
		// Devolvemos documento formateado
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS, entradaDocumento.getIdioma());
		request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
		return mapping.findForward("success");
		
    }
}
