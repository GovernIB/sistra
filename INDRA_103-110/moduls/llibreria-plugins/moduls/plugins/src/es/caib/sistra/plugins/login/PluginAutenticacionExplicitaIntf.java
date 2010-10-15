package es.caib.sistra.plugins.login;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 *	Interfaz para establecer sistema de autenticación explícita por usuario y password.
 *  <br/>
 *  Este plugin será utilizado cuando se seleccione la autenticación explícita del organismo (p.e. en dominios).
 *  Al acceder al elemento, bien por EJB o por Webservice, se utilizará la información de autenticación suministrada por este plugin.
 *
 */
public interface PluginAutenticacionExplicitaIntf extends PluginSistraIntf {
	
	/**
	 * Obtiene información de autenticación a utilizar cuando se utilice autenticación explícita por usuario y password 
	 * @return Información de autenticación (usuario y password)
	 */
	public AutenticacionExplicitaInfo getAutenticacionInfo();
	 
}
