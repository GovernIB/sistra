package es.caib.sistra.casClient.plugin;

import java.security.Principal;

import es.caib.sistra.casClient.loginModule.CasPrincipal;
import es.caib.sistra.plugins.login.PluginLoginIntf;

public class CasPluginLogin implements PluginLoginIntf {

	/**
	 * Obtiene metodo de autenticacion
	 */
	public char getMetodoAutenticacion(Principal principal) {
		CasPrincipal cp =  (CasPrincipal) principal;
		return cp.getMetodoAutenticacion();		
	}
	
	/**
	 * Obtiene nif
	 */
	public String getNif(Principal principal) {
		CasPrincipal cp =  (CasPrincipal) principal;
		return cp.getNif();		
	}

	/**
	 * Obtiene nombre y apellidos
	 */
	public String getNombreCompleto(Principal principal) {
		CasPrincipal cp =  (CasPrincipal) principal;
		return cp.getApellidosNombre();			
	}
	
}
