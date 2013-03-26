package es.caib.pagos.front.form;

import org.apache.struts.validator.ValidatorForm;

public class PagoForm extends ValidatorForm
{
	/**
	 * Modo pago (T/P)
	 */	
	private String modoPago = "T";
	/**
	 * Numero tarjeta
	 */	
	private String numeroTarjeta;
	/**
	 * Fecha caducidad tarjeta (MMAA)
	 */	
	private String fechaCaducidadTarjeta;
	/**
	 * Codigo verificacion tarjeta
	 */	
	private String codigoVerificacionTarjeta;
	
	public String getCodigoVerificacionTarjeta() {
		return codigoVerificacionTarjeta;
	}
	public void setCodigoVerificacionTarjeta(String codigoVerificacionTarjeta) {
		this.codigoVerificacionTarjeta = codigoVerificacionTarjeta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getFechaCaducidadTarjeta() {
		return fechaCaducidadTarjeta;
	}
	public void setFechaCaducidadTarjeta(String fechaCaducidadTarjeta) {
		this.fechaCaducidadTarjeta = fechaCaducidadTarjeta;
	}
	public String getModoPago() {
		return modoPago;
	}
	public void setModoPago(String modoPago) {
		this.modoPago = modoPago;
	}
	
	
}
