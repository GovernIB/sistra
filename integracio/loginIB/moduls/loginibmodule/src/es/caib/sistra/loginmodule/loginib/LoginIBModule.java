package es.caib.sistra.loginmodule.loginib;

import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

import es.caib.sistra.loginibclient.LoginIBClient;
import es.caib.sistra.loginibclient.LoginIBClientException;
import es.caib.sistra.loginibclient.json.JSONObject;

/**
 * Login module para clave basado en ticket.
 *
 *
 * Username={TICKET-sessionId} Password=ticket
 *
 */
public class LoginIBModule extends AbstractServerLoginModule {

	/** Principal autenticado. */
	private Principal identity;

	/** Password */
	private char[] credential;

	/** Timeout ticket (segundos). */
	private long timeoutTicket;

	/** Role de acceso publico. */
	private String roleTothom;

	/** Nombre identidad anonimo. */
	private String unauthenticatedIdentityName;

	/** LoginIBClient. */
	private LoginIBClient loginIBClient;

	/** Caché tickets logados. **/
	private static final Map<String, TicketAutenticado> ticketsLogados = new HashMap<String, TicketAutenticado>();

	/**
	 * Inicializacion
	 */
	@Override
	public void initialize(final Subject subject,
			final CallbackHandler callbackHandler, final Map sharedState,
			final Map options) {
		super.initialize(subject, callbackHandler, sharedState, options);

		unauthenticatedIdentityName = (String) options
				.get("unauthenticatedIdentity");
		roleTothom = (String) options.get("roleTothom");
		timeoutTicket = Long.parseLong((String) options.get("timeoutTicket"));

		try {
			loginIBClient = LoginIBClient.getInstance();
		} catch (LoginIBClientException e) {
			this.log.error("Error al crear cliente LoginIB", e);
			throw new RuntimeException("Error al crear cliente LoginIB", e);
		}
	}

	/**
	 * Login.
	 */
	public boolean login() throws LoginException {

		// Purgamos cache
		purgarCacheTickets();

		// No se usa password-stacking (opcion useFirstPass).
		if (getUseFirstPass() == true) {
			log.error("No se usa password-stacking");
			throw new LoginException("No se usa password-stacking");
		}

		// Realiza login
		super.loginOk = false;

		// - Obtenemos usuario / password (username={TICKET-sessionHttpId} /
		// password=ticket)
		final String[] userPass = this.getUsernameAndPassword();
		final String username = userPass[0];
		final String password = userPass[1];
		if (username == null || !username.startsWith("{TICKET-")) {
			return false;
		}

		// - Obtenemos sesion y ticket
		String ticketLoginIB = password;

		// Autenticamos ticket (nueva autenticación o reautenticación)
		LoginIBPrincipal caller = null;
		if (!ticketsLogados.containsKey(getTicketKeyCache(username, password))) {
			// - Nueva autenticación: recuperamos info de LoginIB
			caller = obtenerInfoAutenticacionLoginIB(ticketLoginIB);
			// - Estableceomos información ticket
			TicketAutenticado nuevoTicket = new TicketAutenticado();
			nuevoTicket.setFechaLogin(new Date());
			nuevoTicket.setPrincipalAut(caller);
			ticketsLogados.put(getTicketKeyCache(username, password),
					nuevoTicket);
		} else {
			// - Re-autenticacion: obtenemos de ticket
			TicketAutenticado ticket = ticketsLogados.get(getTicketKeyCache(
					username, password));
			// Actualizamos fecha uso ticket
			ticket.setFechaLogin(new Date());
			// Retornamos principal autenticado en ticket
			caller = ticket.getPrincipalAut();
		}

		// - Establecemos principal
		identity = caller;

		// - Indica que login es correcto
		super.loginOk = true;
		super.log.trace("User '" + identity + "' authenticated");
		return true;

	}

	/**
	 * Purga cache tickets.
	 */
	private void purgarCacheTickets() {
		Iterator it = ticketsLogados.keySet().iterator();
		while (it.hasNext()) {
			TicketAutenticado ticket = (TicketAutenticado) ticketsLogados
					.get(it.next());
			final boolean purgar = (System.currentTimeMillis()
					- ticket.getFechaLogin().getTime() > (timeoutTicket * 60000L));
			if (purgar) {
				ticketsLogados.remove(ticket);
			}
		}
	}

	/**
	 * Obtiene key para la cache de tickets.
	 *
	 * @return key
	 */
	private String getTicketKeyCache(String username, String password) {
		return username + "#" + password;
	}

	/**
	 * Obtiene info autenticación consultando a LoginIB.
	 *
	 * @param ticketLoginIB
	 *            ticket
	 * @return principal
	 * @throws LoginException
	 */
	private LoginIBPrincipal obtenerInfoAutenticacionLoginIB(
			final String ticketLoginIB) throws LoginException {

		// Accede a LoginIB para recuperar info ticket
		JSONObject datosAut = null;
		try {
			datosAut = loginIBClient
					.obtenerInformacionTicketAutenticacion(ticketLoginIB);
		} catch (LoginIBClientException e) {
			throw new LoginException("Error al conectar con LoginIB: "
					+ e.getMessage());
		}

		// Genera LoginIBPrincipal
		LoginIBPrincipal principalLoginIB = generaPrincipal(datosAut);
		return principalLoginIB;
	}

	/**
	 * Genera LoginIBPrincipal a partir info ticket.
	 *
	 * @param datosAut
	 *            info ticket
	 * @return LoginIBPrincipal
	 * @throws LoginException
	 */
	private LoginIBPrincipal generaPrincipal(JSONObject datosAut)
			throws LoginException {

		try {
			String idSesion = loginIBClient.getJsonValue(datosAut, "idSesion",
					true);
			String metodoAutenticacionLoginIB = loginIBClient.getJsonValue(
					datosAut, "metodoAutenticacion", true);

			char autenticacionSistra = calcularAutenticacionSistra(metodoAutenticacionLoginIB);
			Persona autenticado = null;
			Persona representante = null;
			String username = null;
			Integer qaa = null;

			if (autenticacionSistra == 'A') {
				// TODO Que usuario para anonimo?
				username = unauthenticatedIdentityName;
			} else {

				qaa = Integer.parseInt(loginIBClient.getJsonValue(datosAut,
						"qaa", true));

				autenticado = new Persona();
				autenticado.setNif(loginIBClient.getJsonValue(datosAut, "nif",
						true));
				autenticado.setNombre(loginIBClient.getJsonValue(datosAut,
						"nombre", true));
				autenticado.setApellidos(loginIBClient.getJsonValue(datosAut,
						"apellidos", false));
				autenticado.setApellido1(loginIBClient.getJsonValue(datosAut,
						"apellido1", false));
				autenticado.setApellido2(loginIBClient.getJsonValue(datosAut,
						"apellido2", false));

				JSONObject datosRep = loginIBClient.getJSONObject(datosAut, "representante");
				if ( datosRep != null) {
					representante = new Persona();
					representante.setNif(loginIBClient.getJsonValue(datosRep,
							"nif", true));
					representante.setNombre(loginIBClient.getJsonValue(
							datosRep, "nombre", true));
					representante.setApellidos(loginIBClient.getJsonValue(
							datosRep, "apellidos", false));
					representante.setApellido1(loginIBClient.getJsonValue(
							datosRep, "apellido1", false));
					representante.setApellido2(loginIBClient.getJsonValue(
							datosRep, "apellido2", false));
				}

				username = autenticado.getNif();
			}

			LoginIBPrincipal principalLoginIB = new LoginIBPrincipal(username,
					autenticacionSistra, idSesion, metodoAutenticacionLoginIB,
					qaa, autenticado, representante);
			return principalLoginIB;
		} catch (LoginIBClientException ex) {
			super.log.error(
					"Error al crear LoginIBPrincipal: " + ex.getMessage(), ex);
			throw new LoginException("Error al crear LoginIBPrincipal: "
					+ ex.getMessage());
		}
	}

	/**
	 * Obtiene método de autenticación
	 *
	 * @param metodoAutenticacionLoginIB
	 *            metodo autenticación
	 * @return método
	 * @throws LoginException
	 */
	private char calcularAutenticacionSistra(String metAut)
			throws LoginException {
		String metodoAutenticacion = null;
		if ("ANONIMO".equals(metAut)) {
			metodoAutenticacion = "A";
		} else if ("CLAVE_CERTIFICADO".equals(metAut)
				|| "CLIENTCERT".equals(metAut)) {
			metodoAutenticacion = "C";
		} else if ("CLAVE_PIN".equals(metAut)
				|| "CLAVE_PERMANENTE".equals(metAut)
				|| "USUARIO_PASSWORD".equals(metAut)) {
			metodoAutenticacion = "U";
		}

		if (metodoAutenticacion == null) {
			throw new LoginException("Metodo de autenticacion no soportado: "
					+ metAut);
		}

		return metodoAutenticacion.charAt(0);
	}

	/**
	 * Obtiene usuario / password.
	 *
	 * @return usuario / password.
	 * @throws LoginException
	 */
	protected String[] getUsernameAndPassword() throws LoginException {
		String[] info = { null, null };

		if (callbackHandler == null) {
			throw new LoginException(
					"Error: no CallbackHandler available to collect authentication information");
		}

		NameCallback nc = new NameCallback("User name: ", "guest");
		PasswordCallback pc = new PasswordCallback("Password: ", false);
		Callback[] callbacks = { nc, pc };
		String username = null;
		String password = null;
		try {
			callbackHandler.handle(callbacks);
			username = nc.getName();
			char[] tmpPassword = pc.getPassword();
			if (tmpPassword != null) {
				credential = new char[tmpPassword.length];
				System.arraycopy(tmpPassword, 0, credential, 0,
						tmpPassword.length);
				pc.clearPassword();
				password = new String(credential);
			}
		} catch (IOException ioe) {
			throw new LoginException(ioe.toString());
		} catch (UnsupportedCallbackException uce) {
			throw new LoginException("CallbackHandler does not support: "
					+ uce.getCallback());
		}
		info[0] = username;
		info[1] = password;
		return info;
	}

	/**
	 * Obtiene roles usuario (modificado para que no llame a createIdentity al
	 * crear cada role)
	 */
	@Override
	protected Group[] getRoleSets() throws LoginException {
		final Principal principal = getIdentity();

		if (!(principal instanceof LoginIBPrincipal)) {
			if (log.isTraceEnabled())
				log.trace("Principal " + principal + " not a LoginIBPrincipal");
			return new Group[0];
		}

		final String username = principal.getName();

		List roles = null;
		try {
			roles = getUserRoles(username);
		} catch (final Exception e) {
			log.error("Excepcion obteniendo roles", e);
			throw new LoginException("Excepcion obteniendo roles");
		}

		final Group rolesGroup = new SimpleGroup("Roles");
		for (final Iterator iterator = roles.iterator(); iterator.hasNext();) {
			final String roleName = (String) iterator.next();
			rolesGroup.addMember(new SimplePrincipal(roleName));
		}
		final HashMap setsMap = new HashMap();
		setsMap.put("Roles", rolesGroup);

		// Montamos grupo "CallerPrincipal"
		final Group principalGroup = new SimpleGroup("CallerPrincipal");
		principalGroup.addMember(principal);
		setsMap.put("CallerPrincipal", principalGroup);

		// Devolvemos respuesta
		final Group roleSets[] = new Group[setsMap.size()];
		setsMap.values().toArray(roleSets);
		return roleSets;
	}

	/**
	 * Obtiene roles asociados al usuario. En este caso serán accesos por
	 * ciudadanos que tendrán el role tothom
	 *
	 * @param username
	 * @return
	 */
	private List getUserRoles(final String username) {
		final List userRoles = new ArrayList();
		userRoles.add(roleTothom);
		return userRoles;
	}

	@Override
	protected Principal getIdentity() {
		return identity;
	}

}
