package es.caib.sistra.persistence.plugins;

import java.io.Serializable;

import es.caib.sistra.model.TramiteVersion;
import es.caib.util.StringUtil;

/**
 * Clase para que los scripts accedan a datos del tramite
 *
 */
public class PluginDatosTramite implements Serializable{

	private TramiteVersion tramiteVersion;
	
	
	
	/**
	 * Devuelve fecha de inicio de plazo del trámite (en formato dd/MM/yyyy HH:mm:ss)
	 * @return
	 */
	public String getPlazoInicio(){
		return StringUtil.fechaACadena(tramiteVersion.getInicioPlazo(),StringUtil.FORMATO_TIMESTAMP);
	}

	/**
	 * Devuelve fecha de fin de plazo del trámite (en formato dd/MM/yyyy HH:mm:ss)
	 * @return
	 */
	public String getPlazoFin(){
		return StringUtil.fechaACadena(tramiteVersion.getFinPlazo(),StringUtil.FORMATO_TIMESTAMP);
	}

	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	public void setTramiteVersion(TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}
	
}
