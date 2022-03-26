package es.caib.sistra.util.loginib;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.modelInterfaz.InformacionLoginTramite;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PeticionInicioSesionAutenticacion;
import es.caib.sistra.plugins.login.PluginLoginIntf;

// TODO Se podria gestionar tema de errores a mostrar en pagina login con "esError"

/**
 * Utilidades para cuan
 *
 * @author Indra
 *
 */
public class LoginPageLoginIBUtil {

	private static Log log = LogFactory.getLog(LoginPageLoginIBUtil.class);

	public static LoginPageLoginIBInfo generarInfoLogin(
			HttpSession session, HttpServletRequest request) {

		try {
			// Parametros login clave
			String idioma = "es";
			Object savedRequest = null;
			boolean esLoginClave = false;
			boolean redirigirLoginIB = false;
			boolean esError = false;
			String userNameLogin = null;
			String passwordLogin = null;
			String urlCallback = null;
			String urlCallbackError = null;
			String urlDestino = null;
			String loginPage = null;
			String entidadNombre = null;
			String entidadPortal=null;
			String entidadCssLogin=null;
			int qaaAutenticacion = 2;
			LoginPageLoginIBCustom customizacionLogin = null;

			// Request origen
			savedRequest = session.getAttribute("savedrequest");
			if (savedRequest == null) {
				throw new Exception("No se encuentra savedrequest en session");
			}

			// Configuracion organismo
			Properties configProperties = ConfigurationUtil.getInstance().obtenerPropiedades();
			String urlSistra = configProperties.getProperty("sistra.url");
			String contextoRaiz = configProperties
					.getProperty("sistra.contextoRaiz.front");
			String entidadCodigo = (String) request.getParameter("entidad");
			if (entidadCodigo == null) {
				entidadCodigo = configProperties.getProperty("organismo.entidadDefecto");
				entidadNombre = configProperties.getProperty("organismo.nombre");
				entidadPortal = configProperties.getProperty("organismo.portal.url");
				entidadCssLogin = configProperties.getProperty("organismo.cssLoginCustom");
			} else {
				entidadNombre = configProperties.getProperty("organismo.entidad." + entidadCodigo + ".nombre");
				entidadPortal = configProperties.getProperty("organismo.entidad." + entidadCodigo + ".portal.url");
				entidadCssLogin = configProperties.getProperty("organismo.entidad." + entidadCodigo + ".cssLoginCustom");
			}


			// Obtenemos por reflection de savedrequest la url origen y query
			// string para obtener la url destino
			Method methodUrlOrigen = savedRequest.getClass().getDeclaredMethod(
					"getRequestURI", null);
			String urlSavedRequest = (String) methodUrlOrigen.invoke(
					savedRequest, null);
			Method methodQueryString = savedRequest.getClass()
					.getDeclaredMethod("getQueryString", null);
			String queryStringSavedRequest = (String) methodQueryString.invoke(
					savedRequest, null);

			// Indica si es login despues de pasar por clave
			esLoginClave = urlSavedRequest
					.endsWith("/protected/redireccionLoginIB.jsp");

			// Indica si es redirección por login erróneo
			if (request.getParameter("error") != null) {
				esError = Boolean.parseBoolean(request.getParameter("error"));
			}

			// Verificamos si el login es en sistrafront o zonaperfront
			String contextoAplicacion = getContextoAplicacion(request);

			// Verificamos si es retorno de LoginIB o hay que redirigir a LoginIB
			if (esLoginClave) {
				// Retorno de LoginIB: obtenemos ticket pasado por POST y hacemos login automático
				Class[] paramsParametersOrigen = new Class[] {String.class};
				Method methodParametersOrigen = savedRequest.getClass().getDeclaredMethod("getParameterValues", paramsParametersOrigen);
				String[] values =  (String []) methodParametersOrigen.invoke(savedRequest, new String("ticket"));
				passwordLogin = values[0];
				if (values.length > 1) {
					idioma = values[1];
				}
				userNameLogin = "{TICKET-" + session.getId() + "}";
				if ("ca".equals(idioma) || "es".equals(idioma)) {
					idioma = "es";
				}
			} else {
				// Redirigimos a sistema autenticacion

				// Url destino
				urlDestino = urlSavedRequest;
				if (queryStringSavedRequest != null) {
					urlDestino += "?" + queryStringSavedRequest;
				}

				// Obtiene customizacion según aplicación
				if ("sistrafront".equals(contextoAplicacion)) {
					customizacionLogin = getAutenticacionCustomSistrafront(request, urlDestino);
				} else {
					customizacionLogin = getAutenticacionCustomZonaperfront(request, urlDestino);
				}

				// Generamos url de callback para retornar de LoginIB
				urlCallback = urlSistra + contextoRaiz + "/" + contextoAplicacion + "/protected/redireccionLoginIB.jsp";
				urlCallbackError = entidadPortal;
				if (urlCallbackError == null) {
					log.debug("No se ha establecido url de portal para entidad " + entidadCodigo + ". Se establece como urlcallback error: " + urlDestino);
					urlCallbackError = urlDestino;
				}



				// Url destino (guardamos en sesion para despues redirigir)
				session.setAttribute("urlDestino", urlDestino);

				// TODO FALTA TRATAR LOGIN ANONIMO AUTO (NO SE PASA PARAMETRO A LOGINIB)
				// customizacionLogin.isLoginAnonimoAuto();

				// TODO VER QUE SE HACE CON QAA Y USUARIO. DE MOMENTO LO BAJAMOS SI HAY USUARIO.
				if (customizacionLogin.getNivelesAutenticacion().indexOf("U") != -1) {
					qaaAutenticacion = 1;
				}


				// Realizamos peticion login a sistema autenticacion remoto
				PluginLoginIntf plg = PluginFactory.getInstance().getPluginLogin();
				PeticionInicioSesionAutenticacion peticion = new PeticionInicioSesionAutenticacion();
				peticion.setEntidad(entidadCodigo);
				peticion.setForzarAutenticacion(false);
				peticion.setIdioma(customizacionLogin.getIdioma());
				peticion.setNivelesAutenticacion(customizacionLogin.getNivelesAutenticacion());
				peticion.setPermitirUserPass(customizacionLogin.isPermitirUserPass());
				peticion.setQaa(qaaAutenticacion);
				peticion.setUrlCallback(urlCallback);
				peticion.setUrlCallbackError(entidadPortal);
				peticion.setAuditar(null); // Dejamos que se audite segun lo configurado en plugin
				peticion.setLoginClaveAuto(customizacionLogin.isLoginClaveAuto());

				// TODO FORZAMOS A AUDITAR SIEMPRE (
				peticion.setAuditar(true);

				loginPage = plg.iniciarSesionAutenticacion(peticion);

				idioma = customizacionLogin.getIdioma();
				redirigirLoginIB = true;

			}

			// Retorna info login page
			LoginPageLoginIBInfo infoLoginPage = new LoginPageLoginIBInfo();
			infoLoginPage.setIdioma(idioma);
			infoLoginPage.setTitulo(entidadNombre);
			infoLoginPage.setCss(entidadCssLogin);
			infoLoginPage.setLoginError(esError);
			infoLoginPage.setRedirigirLoginIB(redirigirLoginIB);
			infoLoginPage.setUrlLoginIB(loginPage);
			infoLoginPage.setRealizarLogin(esLoginClave);
			infoLoginPage.setUsernameNameLogin(userNameLogin);
			infoLoginPage.setPasswordLogin(passwordLogin);
			return infoLoginPage;

		} catch (Exception ex) {
			log.error(
					"Error generando información página para LoginIB: "
							+ ex.getMessage(), ex);
			throw new RuntimeException(
					"Error generando información página para LoginIB: "
							+ ex.getMessage(), ex);
		}

	}



	/**
	 * Obtiene niveles autenticacion para Zonaperfront
	 * @param request request
	 * @param urlDestino
	 * @return niveles autenticacion
	 * @throws Exception
	 */
	private static LoginPageLoginIBCustom getAutenticacionCustomZonaperfront(
			HttpServletRequest request, String urlDestino) throws Exception {
		String language = "es";
		String niveles="CUA";
		boolean anonimoAutomatico = false;
		boolean claveAutomatico = false;

		// Si intenta acceder a un trámite buscamos modelo y versión
		String in = request.getContextPath() + "/protected/init.do";
		if (!urlDestino.startsWith(in)) {
			throw new Exception("Punto de entrada no válido: " + urlDestino);
		}

		// Obtiene parametros query
		String params = urlDestino.substring(in.length());
		Map<String, String> parameters = getQueryMap(params);

		// Procesa parametros
		if (parameters.containsKey("language")) {
			language = parameters.get("language");
		} else if (parameters.containsKey("lang")) {
			language = parameters.get("lang");
		}

		// Filtro niveles acceso
		if (parameters.containsKey("autenticacion")){
			niveles = parameters.get("autenticacion");
		}

		// Inicio clave auto
		if ("true".equals(parameters.get("loginClaveAuto"))) {
			claveAutomatico = true;
		}

		// Devuelve customizacion para sistrafront
		LoginPageLoginIBCustom custom = new LoginPageLoginIBCustom();
		custom.setIdioma(language);
		custom.setNivelesAutenticacion(niveles);
		custom.setLoginAnonimoAuto(anonimoAutomatico);
		custom.setLoginClaveAuto(claveAutomatico);
		return custom;
	}



	private static LoginPageLoginIBCustom getAutenticacionCustomSistrafront(
			HttpServletRequest request, String urlDestino) throws Exception {

		String language = "es";
		String niveles="";
		boolean anonimoAutomatico = false;
		boolean claveAutomatico = false;

		// Si intenta acceder a un trámite buscamos modelo y versión
		String in = request.getContextPath() + "/protected/init.do";
		if (!urlDestino.startsWith(in)) {
			throw new Exception("Punto de entrada no válido: " + urlDestino);
		}

		// Obtiene parametros query
		String params = urlDestino.substring(in.length());
		Map<String, String> parameters = getQueryMap(params);

		// Verificamos parametros obligatorios
		if (!parameters.containsKey("modelo") || !parameters.containsKey("version")) {
			throw new Exception("Faltan parámetros");
		}
		String modelo = parameters.get("modelo");
		int version = Integer.parseInt(parameters.get("version"));
		if (parameters.containsKey("language")) {
			language = parameters.get("language");
		} else if (parameters.containsKey("lang")) {
			language = parameters.get("lang");
		}

		// Inicio clave auto
		if ("true".equals(parameters.get("loginClaveAuto"))) {
			claveAutomatico = true;
		}

		// Obtenemos descripcion y metodos de autenticacion permitidos
		InformacionLoginTramite infoTramite = DelegateSISTRAUtil.getSistraDelegate().obtenerInfoLoginTramite(modelo, version, language);
		niveles = infoTramite.getNivelesAutenticacion();
		anonimoAutomatico = infoTramite.isInicioAnonimoDefecto();

		// Filtro niveles acceso
		if (parameters.containsKey("modo")){
			String modo = parameters.get("modo");
			String nivelesModo = "";
	 		if (niveles.indexOf("C") >= 0 && modo.indexOf("C") >= 0 ) nivelesModo += "C";
	 		if (niveles.indexOf("U") >= 0 && modo.indexOf("U") >= 0 ) nivelesModo += "U";
	 		if (niveles.indexOf("A") >= 0 && modo.indexOf("A") >= 0 ) nivelesModo += "A";
	 		niveles = nivelesModo;
		}

		// Filtro user/pass para nivel U
		boolean permitirUP = permitirUsuarioPassword(modelo);

		// Devuelve customizacion para sistrafront
		LoginPageLoginIBCustom custom = new LoginPageLoginIBCustom();
		custom.setIdioma(language);
		custom.setNivelesAutenticacion(niveles);
		custom.setLoginAnonimoAuto(anonimoAutomatico);
		custom.setLoginClaveAuto(claveAutomatico);
		custom.setPermitirUserPass(permitirUP);
		return custom;
	}



	/**
	 * Verifica si permite usuario/password (si esta definido en lista tramites que se permite).
	 * @param modelo Modelo
	 * @return boolean
	 * @throws Exception
	 */
	private static boolean permitirUsuarioPassword(String modelo)
			throws Exception {
		boolean permitirUP = false;
		String listaTramitesUPStr = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("metodosLoginIB.filtroUserPass.tramite");
		if (listaTramitesUPStr != null) {
		String[] lst = listaTramitesUPStr.split(";");
			for (String s : lst) {
				if (modelo.equals(s)) {
					permitirUP = true;
					break;
				}
			}
		}
		return permitirUP;
	}



	/**
	 * Obtiene contexto (sistrafront / zonaperfront).
	 *
	 * @param request request
	 * @return contexto
	 */
	private static String getContextoAplicacion(HttpServletRequest request) {
		String contexto = null;
		if (request.getContextPath().indexOf("zonaperfront") != -1) {
			contexto = "zonaperfront";
		} else {
			contexto = "sistrafront";
		}
		return contexto;
	}


	/**
	 * Obtiene parámetros de la query.
	 * @param query query
	 * @return parámetros
	 */
	private static Map<String, String> getQueryMap(String query)
	{
		if (query.startsWith("?")) {
			query = query.substring(1);
		}

	    String[] params = query.split("&");
	    Map<String, String> map = new HashMap<String, String>();
	    for (String param : params)
	    {  String [] p=param.split("=");
	        String name = p[0];
	      if(p.length>1)  {String value = p[1];
	        map.put(name, value);
	      }
	    }
	    return map;
	}


}
