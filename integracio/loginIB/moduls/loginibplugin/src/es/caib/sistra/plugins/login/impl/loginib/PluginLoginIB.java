package es.caib.sistra.plugins.login.impl.loginib;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.loginibclient.DatosInicioSesion;
import es.caib.sistra.loginibclient.LoginIBClient;
import es.caib.sistra.loginibclient.LoginIBClientException;
import es.caib.sistra.loginibclient.json.JSONArray;
import es.caib.sistra.loginibclient.json.JSONObject;
import es.caib.sistra.loginmodule.loginib.LoginIBPrincipal;
import es.caib.sistra.plugins.login.EvidenciasAutenticacion;
import es.caib.sistra.plugins.login.PeticionInicioSesionAutenticacion;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.sistra.plugins.login.PropiedadAutenticacion;
import es.caib.sistra.plugins.login.TypePropiedad;

/**
 * Plugin login que permite la autenticación de los frontales con LoginIB y que
 * permite configurar el uso de otro Principal para los backs.
 *
 * @author Indra
 *
 */
public class PluginLoginIB implements PluginLoginIntf {

	/** Plugin login para resolver principal para los backs. */
	private PluginLoginIntf plgLoginBack = null;

	/** Metodos autenticacion LoginIB para autenticacion Sistra por Certificado. */
	private String metodosCertificado = null;

	/** Metodos autenticacion LoginIB para autenticacion Sistra por Usuario. */
	private String metodosUsuario = null;

	/** Indica si se autentican todas las autenticaciones. */
	private boolean auditarAutenticaciones;

	/** Log. */
	private static Log log = LogFactory.getLog(PluginLoginIB.class);

	/**
	 * Constructor.
	 */
	public PluginLoginIB() {

		// Obtiene plugin login para backs
		Properties configuracion;
		try {
			configuracion = ConfigurationUtil.getInstance()
					.obtenerPropiedades();
		} catch (Exception e) {
			log.error(
					"Error accediendo a configuracion de PluginLoginIB: "
							+ e.getMessage(), e);
			throw new RuntimeException(
					"Error accediendo a configuracion de PluginLoginIB: "
							+ e.getMessage(), e);
		}

		String className = null;
		try {
			className = configuracion.getProperty("pluginLoginBack.classname");
			if (className == null || className.length() == 0) {
				log.error("No se ha establecido plugin de login para los backs");
				throw new Exception(
						"No se ha establecido plugin de login para los backs");
			}
			plgLoginBack = (PluginLoginIntf) Class.forName(className)
					.newInstance();
		} catch (Exception e) {
			log.error("Error creando plugin de login para los backs");
			throw new RuntimeException(
					"Error creando plugin de login para los backs: "
							+ className, e);
		}

		// Obtiene metodos de autenticacion
		metodosCertificado = configuracion
				.getProperty("metodosLoginIB.certificado");
		metodosUsuario = configuracion.getProperty("metodosLoginIB.usuario");

		// Auditar autenticaciones
		auditarAutenticaciones = "true".equals(configuracion
				.getProperty("auditarAutenticaciones"));

	}

	/**
	 * Obtiene metodo de autenticacion
	 */
	public char getMetodoAutenticacion(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			return mp.getAutenticacionSistra();
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getMetodoAutenticacion(principal);

	}

	/**
	 * Obtiene nif
	 */
	public String getNif(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			String res = null;
			if (mp.getAutenticado() != null) {
				res = mp.getAutenticado().getNif();
			}
			return res;
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getNif(principal);

	}

	/**
	 * Obtiene nombre y apellidos
	 */
	public String getNombreCompleto(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			String res = null;
			if (mp.getAutenticado() != null) {
				res = mp.getAutenticado().getNombreCompleto();
			}
			return res;
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getNombreCompleto(principal);

	}

	/**
	 * Obtiene nif representante
	 */
	public String getRepresentanteNif(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			if (mp.getRepresentante() != null) {
				return mp.getRepresentante().getNif();
			} else {
				return null;
			}
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getRepresentanteNif(principal);
	}

	/**
	 * Obtiene nombre representante.
	 */
	public String getRepresentanteNombre(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			if (mp.getRepresentante() != null) {
				return mp.getRepresentante().getNombre();
			} else {
				return null;
			}
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getRepresentanteNombre(principal);
	}

	/**
	 * Obtiene apellido 1 representante.
	 */
	public String getRepresentanteApellido1(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			if (mp.getRepresentante() != null) {
				return mp.getRepresentante().getApellido1();
			} else {
				return null;
			}
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getRepresentanteApellido1(principal);
	}

	/**
	 * Obtiene apellido 1 representante.
	 */
	public String getRepresentanteApellido2(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			if (mp.getRepresentante() != null) {
				return mp.getRepresentante().getApellido2();
			} else {
				return null;
			}
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getRepresentanteApellido2(principal);

	}

	/**
	 * Obtiene id sesion autenticacion en front.
	 */
	public String getIdSesionAutenticacion(Principal principal) {

		// Autenticacion frontal: LoginIB
		if (principal instanceof LoginIBPrincipal) {
			LoginIBPrincipal mp = (LoginIBPrincipal) principal;
			return mp.getIdSesionAutenticacion();
		}

		// Autenticacion backs: Principal particularizado
		return plgLoginBack.getIdSesionAutenticacion(principal);
	}

	/**
	 * Inicia sesion autenticacion en LoginIB.
	 */
	public String iniciarSesionAutenticacion(
			PeticionInicioSesionAutenticacion peticion) {

		try {

			// Convertimos niveles sistra a metodos autenticacion loginib
			String metodosAutenticacion = "";
			if (peticion.getNivelesAutenticacion().indexOf("C") >= 0)
				metodosAutenticacion += metodosCertificado + ";";
			if (peticion.getNivelesAutenticacion().indexOf("U") >= 0)
				metodosAutenticacion += metodosUsuario + ";";
			if (peticion.getNivelesAutenticacion().indexOf("A") >= 0)
				metodosAutenticacion += "ANONIMO;";
			if (metodosAutenticacion.endsWith(";")) {
				metodosAutenticacion = metodosAutenticacion.substring(0,
						metodosAutenticacion.length() - 1);
			}

			// Auditar autorizacion (si no se indica expresamente, establecemos valor configurado en plugin login)
			boolean auditar;
			if (peticion.getAuditar() != null) {
				auditar = peticion.getAuditar();
			} else {
				auditar = auditarAutenticaciones;
			}

			// Realizamos peticion a LoginIB
			DatosInicioSesion datosInicioSesion = new DatosInicioSesion();
			datosInicioSesion.setAplicacion("SISTRA");
			datosInicioSesion.setEntidad(peticion.getEntidad());
			datosInicioSesion.setForzarAutenticacion(peticion
					.isForzarAutenticacion());

			datosInicioSesion.setAuditar(auditar);
			datosInicioSesion.setIdioma(peticion.getIdioma());
			datosInicioSesion.setMetodosAutenticacion(metodosAutenticacion);
			;
			datosInicioSesion.setQaa(peticion.getQaa());
			datosInicioSesion.setUrlCallback(peticion.getUrlCallback());
			datosInicioSesion.setUrlCallbackError(peticion
					.getUrlCallbackError());
			String urlLogin = LoginIBClient.getInstance()
					.iniciarSesionAutenticacion(datosInicioSesion);
			return urlLogin;
		} catch (LoginIBClientException e) {
			log.error("Error iniciando sesion en LoginIB: " + e.getMessage(), e);
			throw new RuntimeException("Error iniciando sesion en LoginIB: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Obtiene evidencias sesion autenticacion.
	 */
	public EvidenciasAutenticacion getEvidenciasAutenticacion(
			String idSesion) {
	try {

		// TODO Ver que pasa si sesion no se audita ¿que devuelve?

		LoginIBClient client = LoginIBClient.getInstance();
		JSONObject evidencias = client.obtenerEvidenciasAutenticacion(idSesion);
		String huellaElectronica = client.getJsonValue(evidencias, "huellaElectronica", true);

		List<PropiedadAutenticacion> propsAutenticacion = new ArrayList<PropiedadAutenticacion>();
		JSONArray propiedadesJson = (JSONArray) evidencias.get("evidencias");
		for (int i = 0; i<propiedadesJson.length(); i++) {
			JSONObject evidencia = (JSONObject) propiedadesJson.get(i);
			PropiedadAutenticacion p = new PropiedadAutenticacion();
			p.setPropiedad((String) evidencia.get("propiedad"));
			p.setValor((String) evidencia.get("valor"));
			p.setTipo(TypePropiedad.fromString((String) evidencia.get("tipo")));
			p.setMostrar("true".equals(evidencia.get("mostrar").toString()));
			propsAutenticacion.add(p);
		}

		EvidenciasAutenticacion res = new EvidenciasAutenticacion();
		res.setHuellaElectronica(huellaElectronica);
		res.setEvidencias(propsAutenticacion);
		return res;

	} catch (LoginIBClientException e) {
		log.error("Error iniciando sesion en LoginIB: " + e.getMessage(), e);
		throw new RuntimeException("Error iniciando sesion en LoginIB: "
				+ e.getMessage(), e);
	}
	}

	/**
	 * Devuelve auditarAutenticaciones.
	 *
	 * @return auditarAutenticaciones
	 */
	public boolean isAuditarAutenticaciones() {
		return auditarAutenticaciones;
	}

	/**
	 * Establece auditarAutenticaciones.
	 *
	 * @param auditarAutenticaciones
	 *            auditarAutenticaciones
	 */
	public void setAuditarAutenticaciones(boolean auditarAutenticaciones) {
		this.auditarAutenticaciones = auditarAutenticaciones;
	}
}
