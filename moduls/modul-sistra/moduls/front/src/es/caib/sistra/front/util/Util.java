package es.caib.sistra.front.util;
import java.security.SecureRandom;
import java.util.Iterator;

import javax.naming.InitialContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.plugins.pagos.ConstantesPago;


public class Util
{
	private static String version = null;	
	private static String urlMantenimientoSesion = null;
	
	private static Log log = LogFactory.getLog(Util.class);
	private static Boolean entornoDesarrollo = null;
	
	/**
	 * Obtiene version (en web.xml)
	 */
	public static String getVersion(){
		if (version == null) {
			try{
				InitialContext ic = new InitialContext();
				version = (String) ic.lookup("java:comp/env/release.cvs.tag");
			}catch(Exception ex){
				log.error("Error obteniendo propiedad 'release.cvs.tag'",ex);
				version = null;
			}		
		}
		return version;
	}
	

	/**
	 * Genera un token
	 * @return Token
	 */
    public static String generateToken()
    {
    	String token = null;
    	try
    	{
    		// Generamos token: time + random
    		SecureRandom sr = new SecureRandom();
	 		String rn = "" + sr.nextInt(99999999);
	 		if (rn.length() < 8) {
	 			for (int i=rn.length();i<8;i++){
	 				rn += "0";
	 			}			
	 		}
    		String tokenStr = String.valueOf(System.currentTimeMillis())  + rn;
    		
    		// Pasamos a B64 
			byte[] bytes = tokenStr.getBytes("UTF-8");
	    	byte[] encBytes = Base64.encodeBase64(bytes);
		    token = new String(encBytes, "UTF-8");
    	}
    	catch ( Exception exc )
    	{
    		log.error("Error generando token",exc);
    		return null;
    	}
    	return token;
    }
    
	/**
	 * Genera una url para mantener la sesion abierta cuando se salta a otra aplicación (Forms, pagos, etc.)
	 * @param request Request
	 * @return Url mantenimiento sesion
	 */
	public static String generaUrlMantenimientoSesion(String idInstancia){
		if (urlMantenimientoSesion == null) {
			try{
				urlMantenimientoSesion = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("sistra.url");
				urlMantenimientoSesion += "/sistrafront/protected/mantenimientoSesionForm?"+InstanciaManager.ID_INSTANCIA+"=";
			}catch(Exception ex){
				log.error("Error obteniendo propiedad 'sistra.url'",ex);
				urlMantenimientoSesion = null;
			}		
		}
		return (urlMantenimientoSesion + idInstancia);
		
	}
	
	/**
	 * Indica si es entorno de desarrollo
	 * @return true: entorno desarrollo / false: entorno produccion
	 */
	public static boolean esEntornoDesarrollo(){
		if (entornoDesarrollo  == null) {
			try{
				entornoDesarrollo = new Boolean ("DESARROLLO".equals(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("entorno")));				
			}catch(Exception ex){
				log.error("Error obteniendo propiedad 'entorno'",ex);
				entornoDesarrollo = null;
			}		
		}
		return (entornoDesarrollo != null?entornoDesarrollo.booleanValue():false);
		
	}
	
	/**
	 * Establece sobre el paso de tramitacion el titulo y cuerpo.
	 * @param tramite TramiteFront
	 * @param paso Paso tramitacion
	 */
	public static void establecerTituloYCuerpo( TramiteFront tramite, PasoTramitacion paso )
	{
		int tipoPaso = paso.getTipoPaso();
		String keyPreffix = "paso." + tipoPaso;
		String titleKey = keyPreffix + ".titulo";
		String tabKey = keyPreffix + ".tab";
		String textKey = keyPreffix + ".texto";
		paso.setClaveTitulo(  titleKey );
		paso.setClaveTab( tabKey );
		char tipoTramitacion = tramite.getTipoTramitacion();
		char nivelAutenticacion = tramite.getDatosSesion().getNivelAutenticacion();
		switch( tipoPaso )
		{
			case PasoTramitacion.PASO_DEBESABER : 
			{
				String informacionInicio = tramite.getInformacionInicio();
				if ( !StringUtils.isEmpty( informacionInicio ) )
				{
					textKey += ".informacionInicio";
				}
				if ( tramite.isDescargarPlantillas() )
				{
					textKey += ".descargarPlantillas";
				}
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_RELLENAR :
			{
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_ANEXAR :
			{
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_PAGAR :
			{
				// Verificamos tipos de pagos
				char tipoPago = 'X';
				for (Iterator it = tramite.getPagos().iterator(); it.hasNext();) {
					DocumentoFront pago = (DocumentoFront) it.next();
					switch (pago.getPagoMetodos()) {
						case ConstantesPago.TIPOPAGO_AMBOS:
							tipoPago = ConstantesPago.TIPOPAGO_AMBOS;
							break;
						case ConstantesPago.TIPOPAGO_PRESENCIAL:
							if (tipoPago == ConstantesPago.TIPOPAGO_TELEMATICO) {
								tipoPago = ConstantesPago.TIPOPAGO_AMBOS;
							} else {
								tipoPago = ConstantesPago.TIPOPAGO_PRESENCIAL;
							}
							break;
						case ConstantesPago.TIPOPAGO_TELEMATICO:
							if (tipoPago == ConstantesPago.TIPOPAGO_PRESENCIAL) {
								tipoPago = ConstantesPago.TIPOPAGO_AMBOS;
							} else {
								tipoPago = ConstantesPago.TIPOPAGO_TELEMATICO;
							}
							break;
					}
					
					if (tipoPago == ConstantesPago.TIPOPAGO_AMBOS) {
						break;
					}
				}
				
				paso.setClaveCuerpo( textKey + "." + tipoPago );
				return;
			}
			case PasoTramitacion.PASO_REGISTRAR :
			{
				if ( tramite.getRegistrar() )
				{
					textKey += ".registro";
					titleKey += ".registro";
					tabKey += ".registro";
				}
				else
				{
					textKey += ".envio";
					titleKey += ".envio";
					tabKey += ".envio";
				}
				if ( Constants.TIPO_CIRCUITO_TRAMITACION_TELEMATICO == tipoTramitacion )
				{
					textKey += ".telematico";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_PRESENCIAL == tipoTramitacion )
				{
					textKey += ".presencial";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_DEPENDE == tipoTramitacion )
				{
					textKey += ".depende";
				}
				paso.setClaveTab( tabKey );
				paso.setClaveTitulo( titleKey );
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_FINALIZAR :
			{
				if ( tramite.getRegistrar() )
				{
					textKey += ".registro";
					titleKey += ".registro";
					tabKey += ".registro";
				}
				else if ( tramite.isConsultar())
				{
					textKey += ".consulta";
					titleKey += ".consulta";
					tabKey += ".consulta";
				}
				else
				{
					textKey += ".envio";
					titleKey += ".envio";
					tabKey += ".envio";
				}
				
				if ( Constants.TIPO_CIRCUITO_TRAMITACION_TELEMATICO == tipoTramitacion   )
				{
					textKey += ".telematico";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_PRESENCIAL == tipoTramitacion )
				{
					textKey += ".presencial";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_DEPENDE == tipoTramitacion )
				{
					textKey += ".depende";
				}
				paso.setClaveTab( tabKey );
				paso.setClaveTitulo( titleKey );
				paso.setClaveCuerpo( textKey );
				
				return;
			}				
			default :
			{
				paso.setClaveCuerpo( keyPreffix + ".texto" );
			}
		}
	}
}
