package es.caib.sistra.plugins.pagos;

import java.io.Serializable;
import java.util.Date;

/**
 * Estado sesi�n de pago
 */
public class EstadoSesionPago implements Serializable{
	
	/**
	 * Estado sesi�n pago
	 * @see ConstantesPago
	 */
	private int estado;
	/**
	 * Tipo de pago: presencial (P) / telem�tico (T)
	 * @see ConstantesPago
	 */
	private char tipo;	
	/**
	 * Identificador del pago (identificador que use la pasarela: DUI, NRC,etc.)
	 */
	private String identificadorPago;
	/**
	 * Para pagos telem�ticos confirmados establece fecha del pago 
	 */
	private Date fechaPago;
	/**
	 * Para pagos telem�ticos confirmados permite recoger de la pasarela de pagos un recibo electr�nico (en 
	 * base 64) que identifique la transacci�n (No obligatorio)
	 */	
	private String reciboB64PagoTelematico;
	/**
	 * Mensaje de descripcion de estado en el caso de que un pago sea telem�tico y este pendiente de confirmaci�n 
	 */
	private String descripcionEstado;
	/**
	 * Estado sesi�n pago
	 * @return Estado
	 * @see ConstantesPago
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * Estado sesi�n pago
	 * @param estado Estado
	 * @see ConstantesPago
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	/**
	 * Para pagos telem�ticos confirmados establece fecha del pago 
	 * @return Fecha pago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}
	/**
	 * Para pagos telem�ticos confirmados establece fecha del pago 
	 * @param fechaPago Fecha pago
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	/**
	 * Identificador del pago (identificador que use la pasarela: DUI, NRC,etc.)
	 * @return Identificador pago
	 */
	public String getIdentificadorPago() {
		return identificadorPago;
	}
	/**
	 * Identificador del pago (identificador que use la pasarela: DUI, NRC,etc.)
	 * @param identificadorPago Identificador pago
	 */
	public void setIdentificadorPago(String identificadorPago) {
		this.identificadorPago = identificadorPago;
	}
	/**
	 * Para pagos telem�ticos confirmados permite recoger de la pasarela de pagos un recibo electr�nico (en 
	 * base 64) que identifique la transacci�n (No obligatorio)
	 * @return Recibo en base 64
	 */
	public String getReciboB64PagoTelematico() {
		return reciboB64PagoTelematico;
	}
	/**
	 * Para pagos telem�ticos confirmados permite recoger de la pasarela de pagos un recibo electr�nico (en 
	 * base 64) que identifique la transacci�n (No obligatorio)
	 * @param reciboB64PagoTelematico Recibo en base 64
	 */
	public void setReciboB64PagoTelematico(String reciboB64PagoTelematico) {
		this.reciboB64PagoTelematico = reciboB64PagoTelematico;
	}
	/**
	 * Tipo de pago: presencial (P) / telem�tico (T) 
	 * @return Tipo de pago
	 * @see ConstantesPago
	 */
	public char getTipo() {
		return tipo;
	}
	/**
	 * Tipo de pago: presencial (P) / telem�tico (T)
	 * @param tipo Tipo de pago
	 * @see ConstantesPago
	 */
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}	
	/**
	 * Mensaje de descripcion de estado en el caso de que un pago sea telem�tico y este pendiente de confirmaci�n 
	 * @return descripci�n del estado
	 */
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	/**
	 * Mensaje de descripcion de estado en el caso de que un pago sea telem�tico y este pendiente de confirmaci�n 
	 * @param descripcionEstado descripci�n del estado
	 */
	 public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}	
	
}
