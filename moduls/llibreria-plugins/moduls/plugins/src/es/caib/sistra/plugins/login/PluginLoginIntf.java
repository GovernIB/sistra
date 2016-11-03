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
}
