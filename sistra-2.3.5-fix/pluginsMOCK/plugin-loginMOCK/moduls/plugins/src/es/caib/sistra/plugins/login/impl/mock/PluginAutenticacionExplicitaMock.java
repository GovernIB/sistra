package es.caib.sistra.plugins.login.impl.mock;

import es.caib.sistra.plugins.login.AutenticacionExplicitaInfo;
import es.caib.sistra.plugins.login.PluginAutenticacionExplicitaIntf;

public class PluginAutenticacionExplicitaMock implements PluginAutenticacionExplicitaIntf  {

	public AutenticacionExplicitaInfo getAutenticacionInfo(char tipoElemento, String idElemento) {
		AutenticacionExplicitaInfo inf = new AutenticacionExplicitaInfo();
		inf.setUser("explicitUser");
		inf.setPassword("explicitPass");
		return inf;
	}

}
