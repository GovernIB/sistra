package es.caib.sistra.plugins.login;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 *	Interfaz para establecer sistema de autenticaci�n expl�cita por usuario y password.
 *  <br/>
 *  Este plugin ser� utilizado cuando se seleccione la autenticaci�n expl�cita del organismo (p.e. en dominios).
 *  Al acceder al elemento, bien por EJB o por Webservice, se utilizar� la informaci�n de autenticaci�n suministrada por este plugin.
 *
 */
public interface PluginAutenticacionExplicitaIntf extends PluginSistraIntf {
	
	/**
	 * Obtiene informaci�n de autenticaci�n a utilizar cuando se utilice autenticaci�n expl�cita por usuario y password 
	 * @return Informaci�n de autenticaci�n (usuario y password)
	 */
	public AutenticacionExplicitaInfo getAutenticacionInfo();
	 
}
