package es.caib.sistra.plugins.login;

import java.security.Principal;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 *
 * Interfaz con el módulo de login.
 * <br/>
 * Permite extraer información del usuario autenticado
 *
 */
public interface PluginLoginIntf  extends PluginSistraIntf {

	/**
	 * Devuelve el método de autenticación utilizado en el login
	 * @param principal Principal autenticado en el login
	 * @return Método autenticación
	 * @see ConstantesLogin
	 */
	public char getMetodoAutenticacion(Principal principal);

	/**
	 * Devuelve el nif/cif del usuario autenticado (en caso de que no sea anónimo). El nif debe estar normalizado a 9 carácteres y mayúsculas sin espacios en blanco ni guiones.
	 * @param principal Principal autenticado en el login
	 * @return Nif
	 */
	public String getNif(Principal principal);

	/**
	 * Obtiene el nombre completo del usuario autenticado (en caso de que no sea anónimo)
	 * @param principal Principal autenticado en el login
	 * @return Nombre completo
	 */
	public String getNombreCompleto(Principal principal);

	/**
	 * Obtiene el nif del representante (en caso de que lo haya)
	 * @param principal Principal autenticado en el login
	 * @return nif representante
	 */
	public String getRepresentanteNif(Principal principal);

	/**
	 * Obtiene el nombre del representante (en caso de que lo haya)
	 * @param principal Principal autenticado en el login
	 * @return Nombre representante
	 */
	public String getRepresentanteNombre(Principal principal);

	/**
	 * Obtiene el apellido1 del representante (en caso de que lo haya)
	 * @param principal Principal autenticado en el login
	 * @return Apellido1 representante
	 */
	public String getRepresentanteApellido1(Principal principal);

	/**
	 * Obtiene el apellido2 del representante (en caso de que lo haya)
	 * @param principal Principal autenticado en el login
	 * @return Apellido2 representante
	 */
	public String getRepresentanteApellido2(Principal principal);

	// --------- Para cuando hay proveedor externo autenticacion (LoginIB) ---------
	/**
	 * Petición inicio sesión autenticación.
	 *
	 * @param peticion petición inicio sesión
	 * @return Url redirección proveedor externo.
	 */
	public String iniciarSesionAutenticacion(PeticionInicioSesionAutenticacion peticion);

	/**
	 * En caso de haber obtenido la autenticación contra un proveedor externo, obtiene identificador de sesión de autenticación.
	 * @return identificador de sesión de autenticación
	 */
	public String getIdSesionAutenticacion(Principal principal);

	/**
	 * Obtiene evidencias de sesión de autenticación contra un proveedor externo.
	 * @param idSesionAutenticacion id sesión autenticación.
	 * @return Evidencias.
	 */
	public EvidenciasAutenticacion getEvidenciasAutenticacion(String idSesionAutenticacion);




}
