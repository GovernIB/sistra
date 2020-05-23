package es.caib.sistra.loginibclient;

import java.io.UnsupportedEncodingException;

import javax.security.auth.login.LoginException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.loginibclient.json.JSONObject;

/**
 * Cliente LoginIB.
 *
 * @author Indra
 *
 */
public class LoginIBClient {

	private static LoginIBClient client = null;

	private String url;
	private String user;
	private String password;

	private static Log log = LogFactory.getLog(LoginIBClient.class);

	/**
	 * Constructor.
	 *
	 * @throws LoginIBClientException
	 */
	private LoginIBClient() throws LoginIBClientException {
		try {
			// Acceso LoginIB
			url = ConfigurationUtil.getInstance().obtenerPropiedades()
					.getProperty("loginibclient.url");
			if (url == null || url.length() == 0) {
				throw new Exception("LoginIBClient: No se ha establecido url");
			}
			user = ConfigurationUtil.getInstance().obtenerPropiedades()
					.getProperty("loginibclient.usr");
			if (url == null || url.trim().length() == 0) {
				throw new Exception("LoginIBClient: No se ha establecido user");
			}
			url = url.trim();
			if (!url.endsWith("/")) {
				url += "/";
			}
			password = ConfigurationUtil.getInstance().obtenerPropiedades()
					.getProperty("loginibclient.pwd");
			if (url == null || url.length() == 0) {
				throw new Exception("LoginIBClient: No se ha establecido password");
			}
		} catch (Exception ex) {
			log.error("Error inicializando propiedades",
					ex);
			throw new LoginIBClientException("Error inicializando propiedades",
					ex);
		}
	}

	/**
	 * Obtiene singleton
	 *
	 * @return singleton
	 * @throws LoginIBClientException
	 */
	public static LoginIBClient getInstance() throws LoginIBClientException {
		if (client == null) {
			client = new LoginIBClient();
		}
		return client;
	}

	/**
	 * Obtiene información autenticación.
	 *
	 * @param ticket
	 * @return Información autenticación
	 * @throws LoginIBClientException
	 */
	public JSONObject obtenerInformacionTicketAutenticacion(final String ticket)
			throws LoginIBClientException {
		GetMethod getMethod = null;

		try {

			// Accede a LoginIB para obtener info autenticacion
			final String urlClave = url + "ticket/" + ticket;

			final HttpClient client = new HttpClient();
			getMethod = new GetMethod(urlClave);

			getMethod.addRequestHeader("Content-Type", "application/json");
			getMethod.addRequestHeader("Authorization", "Basic " + getBasicCredentials());
			int status = client.executeMethod(getMethod);

			if (status != HttpStatus.SC_OK) {
				throw new LoginException(
						"Error al invocar el servicio de obtención de datos de autenticación de LoginIB"
								+ getMethod.getStatusCode() + " - " + getMethod.getStatusText() + ". Consultar logs de LoginIB.");
			}

			final String response = getMethod.getResponseBodyAsString();
			JSONObject datosAut = new JSONObject(response);

			return datosAut;

		} catch (Exception ex) {
			throw new LoginIBClientException(
					"Error obteniendo datos de LoginIB: " + ex.getMessage());
		} finally {
			getMethod.releaseConnection();
		}

	}


	/**
	 * Obtiene evidencias autenticación.
	 *
	 * @param id sesion
	 * @return evidencias autenticación
	 * @throws LoginIBClientException
	 */
	public JSONObject obtenerEvidenciasAutenticacion(final String idSesion)
			throws LoginIBClientException {
		GetMethod getMethod = null;

		try {

			// Accede a LoginIB para obtener info autenticacion
			final String urlClave = url + "evidencias/" + idSesion;

			final HttpClient client = new HttpClient();
			getMethod = new GetMethod(urlClave);

			getMethod.addRequestHeader("Content-Type", "application/json");
			getMethod.addRequestHeader("Authorization", "Basic " + getBasicCredentials());
			int status = client.executeMethod(getMethod);

			if (status != HttpStatus.SC_OK) {
				throw new LoginException(
						"Error al invocar el servicio de obtención de evidencias de autenticación de LoginIB"
								+ getMethod.getStatusCode() + " - " + getMethod.getStatusText() + ". Consultar logs de LoginIB.");
			}

			final String response = getMethod.getResponseBodyAsString();
			JSONObject datosAut = new JSONObject(response);

			return datosAut;

		} catch (Exception ex) {
			throw new LoginIBClientException(
					"Error obteniendo datos de LoginIB: " + ex.getMessage());
		} finally {
			getMethod.releaseConnection();
		}

	}

	/**
	 * Iniciar sesión autenticación.
	 *
	 * @param peticion
	 *            petición
	 * @return Url redirección login
	 * @throws LoginIBClientException
	 */
	public String iniciarSesionAutenticacion(DatosInicioSesion peticion)
			throws LoginIBClientException {

		PostMethod postMethod = null;

		try {
			final HttpClient client = new HttpClient();
			postMethod = new PostMethod(url + "login");
			JSONObject json = new JSONObject();
			json.put("aplicacion", peticion.getAplicacion());
			json.put("entidad", peticion.getEntidad());
			json.put("forzarAutenticacion", peticion.isForzarAutenticacion());
			json.put("auditar", peticion.isAuditar());
			json.put("idioma", peticion.getIdioma());
			json.put("metodosAutenticacion", peticion.getMetodosAutenticacion());
			json.put("qaa", peticion.getQaa());
			json.put("urlCallback", peticion.getUrlCallback());
			json.put("urlCallbackError", peticion.getUrlCallbackError());
			postMethod.addRequestHeader("Authorization", "Basic "
					+ encodeBase64(user + ":" + password));
			postMethod.setRequestEntity((new StringRequestEntity(json
					.toString(), "application/json", "UTF-8")));
			client.executeMethod(postMethod);
			final String loginPage = postMethod.getResponseBodyAsString();
			if (postMethod.getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception("Respuesta no correcta (" + postMethod.getStatusCode() +" - " + postMethod.getStatusText() + "). Verificar logs en LoginIB." );
			}
			return loginPage;
		} catch (Exception ex) {
			throw new LoginIBClientException(
					"Error iniciando sesion en LoginIB: " + ex.getMessage(), ex);
		} finally {
			postMethod.releaseConnection();
		}

	}

	/**
	 * Obtiene propiedad json.
	 *
	 * @param json
	 * @param propiedad
	 * @param obligatorio
	 * @return
	 * @throws LoginIBClientException
	 */
	public String getJsonValue(JSONObject json, String propiedad,
			boolean obligatorio) throws LoginIBClientException {
		String res = null;
		Object data = json.get(propiedad);
		if (data == null && obligatorio) {
			throw new LoginIBClientException("Propiedad " + propiedad
					+ " no puede ser nulo");
		}
		if (data != null) {
			res = data.toString();
		}
		return res;
	}

	/**
	 * Obtiene objeto json.
	 *
	 * @param json
	 *            objeto json
	 * @param propiedad
	 *            propiedad
	 * @return objeto json
	 */
	public JSONObject getJSONObject(JSONObject json, String propiedad) {
		JSONObject res = null;
		if (json != null && json.has(propiedad)
				&& !JSONObject.NULL.equals(json.get(propiedad))) {
			res = (JSONObject) json.get(propiedad);
		}
		return res;
	}

	/**
	 * Codificar en Base64.
	 *
	 * @param cadena
	 *            cadena
	 * @return B64
	 */
	private static String encodeBase64(String cadena) {
		Base64 base64 = new Base64();
		String encodedVersion = null;
		try {
			encodedVersion = new String(base64.encode(cadena.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error convirtiendo en Base64", e);
		}
		return encodedVersion;
	}


	/**
	 * Obtiene autenticacion para basic.
	 * @return basic credentials
	 */
	private String getBasicCredentials() {
		final String plainCreds = user + ":" + password;
		final byte[] plainCredsBytes = plainCreds.getBytes();
		final byte[] base64CredsBytes = Base64
				.encodeBase64(plainCredsBytes);
		final String base64Creds = new String(base64CredsBytes);
		return base64Creds;
	}


}
