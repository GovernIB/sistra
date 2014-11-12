package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.List;

/**
 * Estado pagos tramite.
 * 
 * @author Indra
 *
 */
public class EstadoPagosTramite implements Serializable{

	/**
	 * Estado tramite (ConstantesZPE).
	 */
	private String estadoTramite;
	/**
	 * Estado pagos.
	 */
	private List estadoPagos;
	
	public String getEstadoTramite() {
		return estadoTramite;
	}
	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}
	public List getEstadoPagos() {
		return estadoPagos;
	}
	public void setEstadoPagos(List estadoPagos) {
		this.estadoPagos = estadoPagos;
	}
	
	
	
}
