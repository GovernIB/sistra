package es.caib.sistra.plugins.pagos;

import java.io.Serializable;
import java.util.Date;

/**
 * Datos de un pago
 */
public class DatosPago implements Serializable{
	/**
	 * Nombre tr�mite
	 */
	 private String nombreTramite;
	 /**
	  * Nombre usuario
	  */
	 private String nombreUsuario;	
	/**
	 * Tipo de pago permitido: Presencial (P) / Telem�tico (T) / Ambos (A) 
	 */
	private char tipoPago;
	/**
	 * Idioma en el que se realiza el pago (es,ca)
	 */
	private String idioma;	
	/**
	 * Modelo
	 */
	private String modelo; 
	/**
	 * Identificador de tasa
	 */
	private String idTasa;
	/**
	 * Concepto
	 */
	private String concepto;
	/**
	 * Fecha devengo
	 */
	private Date fechaDevengo;
	/**
	 * Importe pago
	 */
	private String importe;
	/**
	 * Nif declarante
	 */
	private String nifDeclarante;
	/**
	 * Nombre y apellidos del declarante
	 */
	private String nombreDeclarante;
	
	/**
	 * Concepto de la tasa
	 * @return Concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * Concepto de la tasa
	 * @param concepto Concepto
	 */ 
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * Fecha devengo (fecha en la que se calcula el pago)
	 * @return Fecha devengo
	 */
	public Date getFechaDevengo() {
		return fechaDevengo;
	}
	/**
	 * Fecha devengo (fecha en la que se calcula el pago)
	 * @param fechaDevengo Fecha devengo
	 */
	public void setFechaDevengo(Date fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}
	/**
	 * Idioma en el que se efectua el pago (es,ca)
	 * @return Idioma
	 */
	public String getIdioma() {
		return idioma;		
	}
	/**
	 * Idioma en el que se efectua el pago (es,ca)
	 * @param idioma Idioma
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	/**
	 * Identificador de la tasa
	 * @return Identificador tasa
	 */
	public String getIdTasa() {
		return idTasa;
	}
	/**
	 * Identificador de la tasa
	 * @param idTasa Identificador tasa
	 */
	public void setIdTasa(String idTasa) {
		this.idTasa = idTasa;
	}
	/**
	 * Importe a pagar (en cents)
	 * @return Importe
	 */
	public String getImporte() {
		return importe;
	}
	/**
	 * Importe a pagar (en cents)
	 * @param importe Importe
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}	
	/**
	 * Modelo de la tasa
	 * @return Modelo
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * Modelo de la tasa
	 * @param modelo Modelo
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	/**
	 * Nif declarante
	 * @return nif
	 */
	public String getNifDeclarante() {
		return nifDeclarante;
	}
	/**
	 * Nif declarante
	 * @param nif
	 */
	public void setNifDeclarante(String nif) {
		this.nifDeclarante = nif;
	}
	/**
	 * Nombre y apellidos declarante al que corresponde la tasa que se est� pagando
	 * @return Nombre y apellidos
	 */
	public String getNombreDeclarante() {
		return nombreDeclarante;
	}
	/**
	 *  Nombre y apellidos declarante al que corresponde la tasa que se est� pagando
	 * @param nombre Nombre y apellidos
	 */
	public void setNombreDeclarante(String nombre) {
		this.nombreDeclarante = nombre;
	}
	/**
	 * Tipo de pago permitido: Presencial (P) / Telem�tico (T) / Ambos (A)
	 * @return Tipo de pago
	 */
	public char getTipoPago() {
		return tipoPago;
	}
	/**
	 * Tipo de pago permitido: Presencial (P) / Telem�tico (T) / Ambos (A)
	 * @param tipoPago Tipo pago
	 */
	public void setTipoPago(char tipoPago) {
		this.tipoPago = tipoPago;
	}
	/**
	 * Nombre del tr�mite en el que se realiza el pago. Se utilizar� para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitaci�n.   
	 * @return Nombre tr�mite 
	 */	
	public String getNombreTramite() {
		return nombreTramite;
	}
	/**
	 * Nombre del tr�mite en el que se realiza el pago. Se utilizar� para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitaci�n.
	 * @param nombreTramite Nombre tr�mite
	 */
	public void setNombreTramite(String nombreTramite) {
		this.nombreTramite = nombreTramite;
	}
	/**
	 * Nombre del usuario que realiza el tr�mite. Se utilizar� para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitaci�n.
	 * @return Nombre usuario que realiza el tr�mite
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	/**
	 * Nombre del usuario que realiza el tr�mite. Se utilizar� para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitaci�n.
	 * @param nombreUsuario Nombre usuario que realiza el tr�mite
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}	
}
