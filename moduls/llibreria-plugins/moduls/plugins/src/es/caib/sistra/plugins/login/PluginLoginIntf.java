package es.caib.sistra.plugins.login;

import java.security.Principal;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * 
 * Interfaz con el m�dulo de login.
 * <br/>
 * Permite extraer informaci�n del usuario autenticado
 *
 */
public interface PluginLoginIntf  extends PluginSistraIntf {
	/**
	 * Devuelve el m�todo de autenticaci�n utilizado en el login
	 * @param principal Principal autenticado en el login
	 * @return M�todo autenticaci�n
	 * @see ConstantesLogin
	 */
	public char getMetodoAutenticacion(Principal principal);
	
	/**
	 * Devuelve el nif/cif del usuario autenticado (en caso de que no sea an�nimo). El nif debe estar normalizado a 9 car�cteres y may�sculas sin espacios en blanco ni guiones.
	 * @param principal Principal autenticado en el login
	 * @return Nif
	 */
	public String getNif(Principal principal);
	
	/**
	 * Obtiene el nombre completo del usuario autenticado (en caso de que no sea an�nimo)
	 * @param principal Principal autenticado en el login
	 * @return Nombre completo
	 */
	public String getNombreCompleto(Principal principal);
}
