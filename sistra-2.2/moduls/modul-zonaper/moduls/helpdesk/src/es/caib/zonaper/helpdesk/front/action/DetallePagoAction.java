package es.caib.zonaper.helpdesk.front.action;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.pagos.EstadoSesionPago;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;
import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.DetallePagoForm;
import es.caib.zonaper.model.DetallePagoTelematico;

/**
 * @struts.action
 *  name="detallePagoForm"
 *  path="/detallePago"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalle"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DetallePagoAction extends BaseAction
{
	// Formato en la que se guarda la fecha del pago en el xml
	private final static String FORMATO_FECHAS = "yyyyMMddHHmmss";	

	// Constantes para la generación del xml
	public final static String XML_ROOT = "/PAGO";
	private final static String XML_ID_PLUGIN = XML_ROOT + "/DATOS_PAGO/PLUGIN_ID";
	private final static String XML_TIPO = XML_ROOT + "/DATOS_PAGO/TIPO";
	private final static String XML_ESTADO = XML_ROOT + "/DATOS_PAGO/ESTADO";
	private final static String XML_NUMERO_DUI = XML_ROOT + "/DATOS_PASARELA/NUMERO_DUI";
	private final static String XML_LOCALIZADOR = XML_ROOT + "/DATOS_PASARELA/LOCALIZADOR";
	private final static String XML_FECHA_PAGO = XML_ROOT + "/DATOS_PASARELA/FECHA_PAGO";
	private final static String XML_NOMBRE = XML_ROOT + "/DATOS_PAGO/DECLARANTE/NOMBRE";
	private final static String XML_NIF = XML_ROOT + "/DATOS_PAGO/DECLARANTE/NIF";
	private final static String XML_TASA = XML_ROOT + "/DATOS_PAGO/ID_TASA";
	private final static String XML_CODIGO_POSTAL = XML_ROOT + "/DATOS_PAGO/DECLARANTE/CODIGOPOSTAL";
	
	protected static Log log = LogFactory.getLog(DetallePagoAction.class);
	


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetallePagoForm detallePagoFormulario = ( DetallePagoForm ) form;
		
		ReferenciaRDS referenciaRDS = new ReferenciaRDS();
		referenciaRDS.setCodigo( detallePagoFormulario.getCodigo().longValue() );
		referenciaRDS.setClave( detallePagoFormulario.getClave() );

		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		
		DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento(referenciaRDS,false);
		
		/* Accedemos al documento pero con los datos */
		documentoRDS = rdsDelegate.consultarDocumento( referenciaRDS );
		byte[] byteArraySolicitud = documentoRDS.getDatosFichero();
		SimpleDateFormat sdf = new SimpleDateFormat( FORMATO_FECHAS );

		// Parseamos los datos del formulario a la hash			
		Analizador analizador = new Analizador ();			
		HashMapIterable map = analizador.analizar ( new ByteArrayInputStream(byteArraySolicitud), ConstantesXML.ENCODING );
		DetallePagoTelematico dpt = new DetallePagoTelematico();
		dpt.setIdPlugin((map.get(XML_ID_PLUGIN) != null) ? ((Nodo) map.get(XML_ID_PLUGIN)).getValor() : PluginFactory.ID_PLUGIN_DEFECTO);
		dpt.setEstadoPlataforma(Constants.XMLPAGO_NO_INICIADO);
		dpt.setEstadoPortal(Constants.PAGO_NO_COMPROBADO);
		String estadoPago = (map.get(XML_ESTADO) != null) ? ((Nodo) map.get(XML_ESTADO)).getValor() : null;
		if(estadoPago != null) dpt.setEstadoPlataforma(estadoPago);

		String tipoPago =  ((Nodo) map.get(XML_TIPO)).getValor();
		dpt.setTipo(tipoPago.charAt(0));
		dpt.setDui( (map.get(XML_NUMERO_DUI) != null) ? ((Nodo) map.get(XML_NUMERO_DUI)).getValor() : null);
		dpt.setLocalizador((map.get(XML_LOCALIZADOR) != null) ? ((Nodo) map.get(XML_LOCALIZADOR)).getValor() : null);
		Nodo nodo = (Nodo) map.get(XML_FECHA_PAGO);		
		if ( nodo != null){
			dpt.setFecha( StringUtils.isNotEmpty(nodo.getValor()) ? new Timestamp(sdf.parse(nodo.getValor()).getTime()):null);
		}
		dpt.setIdioma(detallePagoFormulario.getIdioma());
		dpt.setNombre( (map.get(XML_NOMBRE) != null) ? ((Nodo) map.get(XML_NOMBRE)).getValor() : null);
		dpt.setNif( (map.get(XML_NIF) != null) ? ((Nodo) map.get(XML_NIF)).getValor() : null);
		dpt.setTasa( (map.get(XML_TASA) != null) ? ((Nodo) map.get(XML_TASA)).getValor() : null);
		dpt.setCodigoPostal( (map.get(XML_CODIGO_POSTAL) != null) ? ((Nodo) map.get(XML_CODIGO_POSTAL)).getValor() : null);
		
		
		/*
		 *  COMPROBACION DEL PAGO: YA NO SE PUEDE REALIZAR DEBIDO A LA GENERALIZACION EN PLUGINS  
		 *  
		 *  EN CASO DE QUE EL PAGO ESTE INICIADO, PODEMOS COMPROBAR EL ESTADO DE LA SESION DE PAGO  
		 *    
		 */
		EstadoSesionPago esp  = null;
		try{
			if(estadoPago.equals(Constants.XMLPAGO_PENDIENTE_CONFIRMAR)){
				if(dpt.getLocalizador() != null){
					esp = PluginFactory.getInstance().getPluginPagos(dpt.getIdPlugin()).comprobarEstadoSesionPago(dpt.getLocalizador());					
				}
			}
		}catch(Exception ex){
			log.error("Error consultando estado sesion de pago",ex);			
		}
		
		/* 
		 * En el caso de que esté segun la plataforma Confirmado el pago o pendiente de confirmar
		 * miramos si está pagado o no.
		 * En el caso de que esté "vacio" o no pagado según la plataforma directamente devolvemos que el estado es NO_PAGADO
		 
		if(		estadoPago != null &&
				!estadoPago.equals(Constants.XMLPAGO_NO_INICIADO) &&
				tipoPago.equals(String.valueOf(Constants.TELEMATICO))
			)
		{	
			
			// Accedemos a plataforma de pagos
			try
			{
				ClientePagos cliente = ClientePagos.getInstance();
				String token = cliente.imprimirTasaPagada(dpt.getLocalizador());
				String url = cliente.getUrlJustificantePago(dpt.getIdioma(),token);
				dpt.setUrl(url);				
				dpt.setEstadoPortal(Constants.PAGO_PAGADO);
			}
			catch(ClienteException ex)
			{				
				log.error("Error el pago no esta pagado - codigo error:" + ex.getCode(),ex);
				
				switch (ex.getCode()){
					// Controlamos excepciones que indican que el pago no esta pagado
					case ClientePagos.ERROR_DUI_NO_PAGADO:
					case ClientePagos.ERROR_LOCALIZADOR_NO_EXISTE:
						dpt.setEstadoPortal(Constants.PAGO_NO_PAGADO);
						dpt.setUrl(null);
						break;
					// Controlamos excepciones que indican que no se ha podido comprobar el pago	
					default:
						dpt.setEstadoPortal(Constants.PAGO_NO_COMPROBADO);
						dpt.setUrl(null);
						break;
				}				
			}
			
			// Independientemente de si se ha podido confirmar el pago contra la plataforma de pagos
			// si en el XML aparece como confirmado lo damos por pagado
			if (estadoPago.equals(Constants.XMLPAGO_CONFIRMADO)){
				
				if (dpt.getEstadoPortal().equals(Constants.PAGO_NO_COMPROBADO)){
					dpt.setEstadoPortal(Constants.PAGO_PAGADO);
					dpt.setUrl(null);
				}
				
				// CASO NO POSIBLE
				// La plataforma de tramitacion me indica que SI esta pagado y 
				// la plataforma de pagos me indica que NO esta pagado
				if (dpt.getEstadoPortal().equals(Constants.PAGO_NO_PAGADO)){
					throw new Exception("La plataforma de tramitacion me indica que SI esta pagado y la plataforma de pagos me indica que NO esta pagado");
				}
				 
			}
			
		}
		*/

		request.setAttribute( "pago", dpt);
		request.setAttribute("sesionPago",esp);
		
		return mapping.findForward( "success" );
    }
}
