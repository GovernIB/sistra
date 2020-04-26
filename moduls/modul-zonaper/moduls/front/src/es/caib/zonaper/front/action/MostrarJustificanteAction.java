package es.caib.zonaper.front.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.regtel.ConstantesPluginRegistro;
import es.caib.sistra.plugins.regtel.PluginRegistroIntf;
import es.caib.util.StringUtil;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.MostrarDocumentoForm;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
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
 *
 * @struts.action-forward
 *  name="successUrl" path="/protected/mostrarDetalleElemento.do"
 */
public class MostrarJustificanteAction extends BaseAction
{

	private static Log log = LogFactory.getLog( MostrarJustificanteAction.class );

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		MostrarDocumentoForm formulario = ( MostrarDocumentoForm ) form;

		Entrada entrada;

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
		}

		String referencia = null;
		byte[] content = null;
		String numReg = null;
		boolean pendienteGenerar = false;

		if (entrada instanceof EntradaPreregistro) {
			// Si es preregistro, generamos justificante plataforma
			content = generarJustificanteRegistro(entrada);
			numReg = entrada.getNumeroPreregistro();
		} else {
			if (entrada.getTipo() == ConstantesAsientoXML.TIPO_ENVIO){
				//Si es envio, generamos justificante plataforma
				content = generarJustificanteRegistro(entrada);
			} else {
				ProcedimientoBTE proc = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(entrada.getProcedimiento());
				// Si es tramite telematico intentamos mostrar justificante generado por Registro
				try {
					PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
					switch (plgRegistro.obtenerTipoJustificanteRegistroEntrada()) {
		    			case ConstantesPluginRegistro.JUSTIFICANTE_DESCARGA:
		    				content = plgRegistro.obtenerJustificanteRegistroEntrada(proc.getEntidad().getIdentificador(), entrada.getNumeroRegistro(), entrada.getFecha());
		    				break;
		    			case ConstantesPluginRegistro.JUSTIFICANTE_REFERENCIA:
		    				referencia = plgRegistro.obtenerReferenciaJustificanteRegistroEntrada(proc.getEntidad().getIdentificador(), entrada.getNumeroRegistro(), entrada.getFecha());
		    				break;
		   				default:
		   					throw new Exception("Tipo de justificante no permitido: " + plgRegistro.obtenerTipoJustificanteRegistroEntrada());
					}
				} catch (Exception ex) {
					log.warn("No se ha podido obtener justificante de entrada: " + ex.getMessage(), ex);
					pendienteGenerar = true;
				}

				// Si registro no muestra justificante, mostramos el de la plataforma
				if (content == null && referencia == null ) {
					content = generarJustificanteRegistro(entrada);
				}

			}
			numReg = entrada.getNumeroRegistro();
		}

		if (pendienteGenerar) {
			request.setAttribute( Constants.PENDIENTE_GENERAR_KEY, "true");
			return mapping.findForward("successUrl");
		} else {
			if (referencia != null) {
				request.setAttribute( Constants.URLFICHERO_KEY, referencia);
				return mapping.findForward("successUrl");
			} else {
				String nomFic = StringUtil.generarNombreFicheroJustificante(this.getLang(request), numReg, entrada.getFecha());
				request.setAttribute( Constants.NOMBREFICHERO_KEY, nomFic );
				request.setAttribute( Constants.DATOSFICHERO_KEY, content);
				return mapping.findForward("success");
			}
		}


    }

	private byte[] generarJustificanteRegistro(Entrada entrada)
			throws Exception {
		byte[] content;
		long codForJust=-1;
		String claForJust=null;

		if (entrada instanceof EntradaPreregistro) {
			//Comprobamos si la entrada tiene un formulario marcado como justificante
			for (Iterator it= entrada.getDocumentos().iterator();it.hasNext();){
				DocumentoEntradaPreregistro d = (DocumentoEntradaPreregistro) it.next();
				if (d.getTipoDocumento() != null && d.getTipoDocumento().charValue() == 'G') {
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

			content = documentoRDS.getDatosFichero();

		}else{
			//	Mostramos justificante estandard
			ReferenciaRDS refRDS = new ReferenciaRDS();
			refRDS.setCodigo( entrada.getCodigoRdsJustificante() );
			refRDS.setClave( entrada.getClaveRdsJustificante() );

			RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
			DocumentoRDS documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS, entrada.getIdioma());

			content = documentoRDS.getDatosFichero();

		}
		return content;
	}



}
