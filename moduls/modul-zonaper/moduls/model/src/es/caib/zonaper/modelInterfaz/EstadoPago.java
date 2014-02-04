package es.caib.zonaper.modelInterfaz;

/**
 * Estado pago.
 * 
 * @author Indra
 *
 */
public class EstadoPago {

	/**
	 * Id documento (codigo documento - instancia).
	 */
	private String idDocumento;
	
	/**
	 * Estado pago (ConstantesZPE).
	 */
	private String estado;

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
}
