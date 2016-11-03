package es.caib.sistra.casClient.loginModule;

import java.util.List;
import java.util.Properties;

/**
 * Interface de la clase autenticadora para la cual se debe proveer una 
 * implementación para:
 * <ul>
 * 		<li> autenticar los usuarios que no pasan por CAS (p.e. webservices y usuario auto) </li>
 * 		<li> obtener lista de roles si estos no los proporciona CAS </li>
 * </ul>
 */
public interface AutenticadorInt {
	
	/**
	 * Autentica el usuario contra la fuente de usuarios 
	 * 
	 * @param propsPlugin Propiedades del plugin
	 * @param user Username
	 * @param pass Password
	 * @return En caso correcto devuelve la información del usuario. En caso incorrecto devuelve nulo.
	 */
	public UserInfo autenticar(Properties propsPlugin, String user,String pass);
	
	/**
	 * Obtener lista de roles de un usuario. Se usará cuando la lista de roles no sea proporcionada por CAS.
	 * 
	 * @param propsPlugin Propiedades del plugin
	 * @param user Username
	 * @return En caso correcto devuelve lista de roles del usuario. En caso incorrecto devuelve nulo.
	 */
	public List<String> obtenerRoles(Properties propsPlugin, String user);

}
