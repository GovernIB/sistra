package es.caib.sistra.persistence.plugins;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.model.TraMensajeTramite;
import es.caib.sistra.model.TramiteVersion;

/**
 * Plugin que permite acceder a los mensajes de validaci�n
 */
public class PluginMensajes {
	private static Log log = LogFactory.getLog(PluginMensajes.class);	

	private TramiteVersion tramiteVersion;
	
	public PluginMensajes(TramiteVersion tramiteVersion){		
		this.tramiteVersion = tramiteVersion;
	}
	
	public String getMensaje(String idMensaje){
		try{
			MensajeTramite mens = (MensajeTramite) tramiteVersion.getMensajes().get(idMensaje);
			if (mens != null){
				return ((TraMensajeTramite) mens.getTraduccion()).getMensaje();
			}	
			return "";
		}catch(Exception ex){
			log.error("Error accediendo a mensaje validaci�n con id " + idMensaje + " para tramite " + tramiteVersion.getTramite().getIdentificador() + " v" + tramiteVersion.getVersion());
			return "";
		}
	}
}
