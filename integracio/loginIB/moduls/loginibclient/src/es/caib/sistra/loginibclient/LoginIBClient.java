package es.caib.sistra.loginibclient;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import javax.security.auth.login.LoginException;

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
      url = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("loginibclient.url");
      if (url == null || url.length() == 0) {
        throw new Exception("LoginIBClient: No se ha establecido url");
      }
      user = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("loginibclient.usr");
      if (url == null || url.trim().length() == 0) {
        throw new Exception("LoginIBClient: No se ha establecido user");
      }
      url = url.trim();
      if (!url.endsWith("/")) {
        url += "/";
      }
      password = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("loginibclient.pwd");
      if (url == null || url.length() == 0) {
        throw new Exception("LoginIBClient: No se ha establecido password");
      }
    } catch (final Exception ex) {
      log.error("Error inicializando propiedades: " + ex.getMessage(), ex);
      throw new LoginIBClientException("Error inicializando propiedades: " + ex.getMessage(), ex);
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
   * Obtiene informacion autenticacion.
   *
   * @param ticket
   * @return Informacion autenticacion
   * @throws LoginIBClientException
   */
  public JSONObject obtenerInformacionTicketAutenticacion(final String ticket) throws LoginIBClientException {

    HttpResponse httpResponse = null;

    try {

      // Accede a LoginIB para obtener info autenticacion
      final String urlClave = url + "ticket/" + ticket;

      final HttpClient httpClient = HttpClients.custom().build();
      final HttpUriRequest request = RequestBuilder.get().setUri(urlClave).setHeader("Content-Type", "application/json")
          .setHeader("Authorization", "Basic " + getBasicCredentials()).build();
      httpResponse = httpClient.execute(request);

      if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        throw new LoginException("Error al invocar el servicio de obtencion de datos de autenticacion de LoginIB" + httpResponse.getStatusLine().getStatusCode()
            + " - " + httpResponse.getStatusLine().getReasonPhrase() + ". Consultar logs de LoginIB.");
      }

      final HttpEntity entity = httpResponse.getEntity();
      final String response = EntityUtils.toString(entity, "UTF-8");
      final JSONObject datosAut = new JSONObject(response);

      return datosAut;

    } catch (final Exception ex) {
      log.error("Error obteniendo informacion ticket de LoginIB: " + ex.getMessage(), ex);
      throw new LoginIBClientException("Error obteniendo informacion ticket de LoginIB: " + ex.getMessage(), ex);
    } finally {
      if (httpResponse != null) {
        try {
          EntityUtils.consume(httpResponse.getEntity());
        } catch (final IOException e) {
          log.error("Error liberando el httpResponse.getEntity", e);
        }
      }
    }

  }


  /**
   * Obtiene evidencias autenticacion.
   *
   * @param id sesion
   * @return evidencias autenticacion
   * @throws LoginIBClientException
   */
  public JSONObject obtenerEvidenciasAutenticacion(final String idSesion) throws LoginIBClientException {
    HttpResponse httpResponse = null;
    try {

      // Accede a LoginIB para obtener info autenticacion
      final String urlClave = url + "evidencias/" + idSesion;

      final HttpClient httpClient = HttpClients.custom().build();
      final HttpUriRequest request = RequestBuilder.get().setUri(urlClave).setHeader("Content-Type", "application/json")
          .setHeader("Authorization", "Basic " + getBasicCredentials()).build();
      httpResponse = httpClient.execute(request);

      if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        throw new LoginException("Error al invocar el servicio de obtencion de evidencias de autenticacion de LoginIB "
            + httpResponse.getStatusLine().getStatusCode() + " - " + httpResponse.getStatusLine().getReasonPhrase() + ". Consultar logs de LoginIB.");
      }

      final HttpEntity entity = httpResponse.getEntity();
      final String response = EntityUtils.toString(entity, "UTF-8");
      final JSONObject datosAut = new JSONObject(response);

      return datosAut;

    } catch (final Exception ex) {
      log.error("Error obteniendo evidencias de LoginIB: " + ex.getMessage(), ex);
      throw new LoginIBClientException("Error obteniendo evidencias de LoginIB: " + ex.getMessage());
    } finally {
      if (httpResponse != null) {
        try {
          EntityUtils.consume(httpResponse.getEntity());
        } catch (final IOException e) {
          log.error("Error liberando el httpResponse.getEntity", e);
        }
      }
    }

  }

  /**
   * Iniciar sesion autenticacion.
   *
   * @param peticion peticion
   * @return Url redireccion login
   * @throws LoginIBClientException
   */
  public String iniciarSesionAutenticacion(final DatosInicioSesion peticion) throws LoginIBClientException {
    HttpResponse httpResponse = null;
    try {

      // Accede a LoginIB para obtener info autenticacion
      final String urlClave = url + "login";
      final JSONObject json = new JSONObject();
      json.put("aplicacion", peticion.getAplicacion());
      json.put("entidad", peticion.getEntidad());
      json.put("forzarAutenticacion", peticion.isForzarAutenticacion());
      json.put("auditar", peticion.isAuditar());
      json.put("idioma", peticion.getIdioma());
      json.put("metodosAutenticacion", peticion.getMetodosAutenticacion());
      json.put("qaa", peticion.getQaa());
      json.put("urlCallback", peticion.getUrlCallback());
      json.put("urlCallbackError", peticion.getUrlCallbackError());

      final HttpClient httpClient = HttpClients.custom().build();
      final HttpUriRequest request = RequestBuilder.post().setUri(urlClave).setHeader("Authorization", "Basic " + getBasicCredentials())
          .setEntity(new StringEntity(json.toString(), ContentType.create("application/json", "UTF-8"))).build();

      log.debug("Iniciando sesion autenticacion: URL: " + (url + "login") + " usuario: " + user + " JSON: " + json.toString());
      httpResponse = httpClient.execute(request);

      if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        throw new Exception("Respuesta no correcta (" + httpResponse.getStatusLine().getStatusCode() + " - " + httpResponse.getStatusLine().getReasonPhrase()
            + "). Verificar logs en LoginIB.");
      }

      final HttpEntity entity = httpResponse.getEntity();
      final String loginPage = EntityUtils.toString(entity, "UTF-8");
      return loginPage;

    } catch (final Exception ex) {
      log.error("Error iniciando sesion en LoginIB: " + ex.getMessage(), ex);
      throw new LoginIBClientException("Error iniciando sesion en LoginIB: " + ex.getMessage());
    } finally {
      if (httpResponse != null) {
        try {
          EntityUtils.consume(httpResponse.getEntity());
        } catch (final IOException e) {
          log.error("Error liberando el httpResponse.getEntity", e);
        }
      }
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
  public String getJsonValue(final JSONObject json, final String propiedad, final boolean obligatorio) throws LoginIBClientException {
    String res = null;
    final Object data = json.get(propiedad);
    if (data == null && obligatorio) {
      throw new LoginIBClientException("Propiedad " + propiedad + " no puede ser nulo");
    }
    if (data != null) {
      res = data.toString();
    }
    return res;
  }

  /**
   * Obtiene objeto json.
   *
   * @param json objeto json
   * @param propiedad propiedad
   * @return objeto json
   */
  public JSONObject getJSONObject(final JSONObject json, final String propiedad) {
    JSONObject res = null;
    if (json != null && json.has(propiedad) && !JSONObject.NULL.equals(json.get(propiedad))) {
      res = (JSONObject) json.get(propiedad);
    }
    return res;
  }

  /**
   * Obtiene valor JSON como string.
   *
   * @param json objeto json
   * @param propiedad propiedad
   * @return objeto json
   */
  public String getJSONObjectStringValue(final JSONObject json, final String propiedad) {
    String res = null;
    if (json != null && json.has(propiedad) && !JSONObject.NULL.equals(json.get(propiedad))) {
      res = json.get(propiedad).toString();
    }
    return res;
  }


  /**
   * Obtiene autenticacion para basic.
   *
   * @return basic credentials
   */
  private String getBasicCredentials() {
    final String plainCreds = user + ":" + password;
    final byte[] plainCredsBytes = plainCreds.getBytes();
    final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    final String base64Creds = new String(base64CredsBytes);
    return base64Creds;
  }


}
