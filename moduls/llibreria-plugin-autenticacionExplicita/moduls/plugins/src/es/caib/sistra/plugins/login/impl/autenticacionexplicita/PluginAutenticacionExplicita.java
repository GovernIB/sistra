package es.caib.sistra.plugins.login.impl.autenticacionexplicita;

import es.caib.sistra.plugins.login.AutenticacionExplicitaInfo;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.sistra.plugins.login.PluginAutenticacionExplicitaIntf;

/**Plugin autenticación explícita genérico que puede ser usado por cualquier organismo.
 *
 */
public class PluginAutenticacionExplicita implements PluginAutenticacionExplicitaIntf {

	/**
	 * Obtiene información de autenticación a utilizar cuando se utilice autenticación explícita por usuario y password
	 * @param tipoElemento Id elemento que se esta accediendo (P: procedimiento / D: dominio)
	 * @param idElemento Id elemento que se esta accediendo (procedimiento / iddominio)
	 * @return Información de autenticación (usuario y password)
	 */
	public AutenticacionExplicitaInfo getAutenticacionInfo(char tipoElemento,
			String idElemento) {
		try{
			AutenticacionExplicitaInfo ae = new AutenticacionExplicitaInfo();

			// Obtenemos usr/pass por defecto
			String userDefault = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("auto.user");
			String passwdDefault = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("auto.pass");

			// Prefijo
			String prefijo = null;
			if (ConstantesLogin.TIPO_DOMINIO == tipoElemento) {
				prefijo = "DOMINIO.";
			} else if (ConstantesLogin.TIPO_PROCEDIMIENTO == tipoElemento) {
				prefijo = "PROCEDIMIENTO.";
			} else {
				throw new Exception("Tipo de elemento no válido: " + tipoElemento);
			}

			// Establecemos autenticacion por defecto
			ae.setUser(userDefault);
			ae.setPassword(passwdDefault);

			// Buscamos si existe configuracion explicita para el elemento
			//	 - Autenticacion indicando usr/pass
			String userElemento = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty(prefijo + idElemento + ".user");
			String passElemento = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty(prefijo + idElemento + ".pass");
			if (userElemento != null && userElemento.length() > 0) {
				// Si existe ponemos usuario elemento
				ae.setUser(userElemento);
				ae.setPassword(passElemento);
			} else {
				// - Autenticacion indicando login
				userElemento = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty(prefijo + idElemento + ".login");
				if (userElemento != null && userElemento.length() > 0) {
					String userLogin = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("LOGIN." + userElemento + ".user");
					String pwdLogin = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("LOGIN." + userElemento + ".pass");
					if (userLogin != null && userLogin.length() > 0) {
						ae.setUser(userLogin);
						ae.setPassword(pwdLogin);
					}
				}
			}
			return ae;
		}catch(Exception ex){
			throw new RuntimeException("No se ha podido establecer informacion de autenticacion explicita");
		}
	}





}
