package es.caib.util.ws.client;

import java.util.Properties;

import javax.xml.ws.BindingProvider;

import es.caib.util.ws.ConfigurationUtil;
import es.caib.util.ws.Constantes;

public class WsClientSistraUtil {

	/**
	 * Configura puerto segun la configuracion de sistra (por global.properties)
	 * 
	 * @param port
	 * @param url
	 * @param user
	 * @param pass
	 * @throws Exception
	 */
	public static void configurePort(BindingProvider port, String url, String soapAction,
			String user, String pass) throws Exception {
		configurePort(port, url, soapAction, user, pass, Constantes.TIPO_CONFIGURACION_PROPERTIES);
	}
	
	/**
	 * Configura puerto segun la configuracion de sistra seleccionando desde donde coge la configuracion (properties o system)
	 * 
	 * @param port
	 * @param url
	 * @param user
	 * @param pass
	 * @throws Exception
	 */
	public static void configurePort(BindingProvider port, String url, String soapAction,
			String user, String pass, String tipoConfiguracion) throws Exception {

		Properties props;
		if (Constantes.TIPO_CONFIGURACION_PROPERTIES.equals(tipoConfiguracion)) {
			props = ConfigurationUtil.getInstance().obtenerPropiedades();
		} else {	
			props = System.getProperties();
		}
		
		String auth = props.getProperty("sistra.ws.authenticacion");

		boolean generateTimestamp = "USERNAMETOKEN".equals(auth)
				&& (props
						.getProperty("sistra.ws.authenticacion.usernameToken.generateTimestamp") != null ? "true"
						.equals(props
								.getProperty("sistra.ws.authenticacion.usernameToken.generateTimestamp"))
						: false);
		boolean logCalls = (props.getProperty("sistra.ws.client.logCalls") != null ? "true"
				.equals(props.getProperty("sistra.ws.client.logCalls"))
				: false);
		boolean disableCnCheck = (props
				.getProperty("sistra.ws.client.disableCnCheck") != null ? "true"
				.equals(props.getProperty("sistra.ws.client.disableCnCheck"))
				: false);
		boolean disableChunked = (props
				.getProperty("sistra.ws.client.disableChunked") != null ? "true"
				.equals(props.getProperty("sistra.ws.client.disableChunked"))
				: false);
				
		WsClientUtil.configurePort(port, url, soapAction, user, pass, auth,
				generateTimestamp, logCalls, disableCnCheck, disableChunked);

	}

}
