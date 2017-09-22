package es.caib.sistra.plugins.login.impl.mock;

import java.security.Principal;

import es.caib.mock.loginModule.MockPrincipal;
import es.caib.sistra.plugins.login.PluginLoginIntf;

public class PluginLoginMOCK implements PluginLoginIntf {

	/**
	 * Obtiene metodo de autenticacion
	 */
	public char getMetodoAutenticacion(Principal principal) {
		MockPrincipal mp = (MockPrincipal) principal;
		return mp.getMetodoAutenticacion();		
	}
	
	/**
	 * Obtiene nif
	 */
	public String getNif(Principal principal) {
		MockPrincipal mp = (MockPrincipal) principal;
		return mp.getNif();		
	}

	/**
	 * Obtiene nombre y apellidos
	 */
	public String getNombreCompleto(Principal principal) {
		MockPrincipal mp = (MockPrincipal) principal;
		return mp.getNombreCompleto();		
	}
	
	public String getRepresentanteNif(Principal principal) {
		return null;
	}

	public String getRepresentanteNombre(Principal principal) {
		return null;
	}

	public String getRepresentanteApellido1(Principal principal) {
		return null;
	}

	public String getRepresentanteApellido2(Principal principal) {
		return null;
	}
	
}
