package es.caib.sistra.plugins.pagos;

import java.io.Serializable;
import java.util.Date;

/**
 * Datos de un pago
 */
public class DatosPago implements Serializable{
	
	/**
	 * Modelo trámite
	 */
	 private String modeloTramite;
	/**
	 * Versión trámite
	 */
	 private int versionTramite;
	/**
	 * Identificador trámite
	 */
	 private String identificadorTramite;
	/**
	 * Nombre trámite
	 */
	 private String nombreTramite;
	 /**
	  * Nombre usuario
	  */
	 private String nombreUsuario;	
	/**
	 * Tipo de pago permitido: Presencial (P) / Telemático (T) / Ambos (A) 
	 */
	private char tipoPago;
	/**
	 * Idioma en el que se realiza el pago (es,ca)
	 */
	private String idioma;
	/**
	 * Identificador organismo que emite la tasa.
	 */
	private String identificadorOrganismo;
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
	 * Telefono del declarante
	 */
	private String telefonoDeclarante;
	/**
	 * Fecha limite para realizar el pago. 
	 * Después de este tiempo no se permitirá iniciar el proceso de pago, 
	 * pero sí debe permitir confirmar procesos de pago pendientes.
	 */
	private Date fechaMaximaPago;
	
	/**
	 * Mensaje a mostrar en caso de que se exceda el tiempo de pago.
	 */
	private String mensajeTiempoMaximoPago;
	
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
	 * Nombre y apellidos declarante al que corresponde la tasa que se está pagando
	 * @return Nombre y apellidos
	 */
	public String getNombreDeclarante() {
		return nombreDeclarante;
	}
	/**
	 *  Nombre y apellidos declarante al que corresponde la tasa que se está pagando
	 * @param nombre Nombre y apellidos
	 */
	public void setNombreDeclarante(String nombre) {
		this.nombreDeclarante = nombre;
	}
	/**
	 * Tipo de pago permitido: Presencial (P) / Telemático (T) / Ambos (A)
	 * @return Tipo de pago
	 */
	public char getTipoPago() {
		return tipoPago;
	}
	/**
	 * Tipo de pago permitido: Presencial (P) / Telemático (T) / Ambos (A)
	 * @param tipoPago Tipo pago
	 */
	public void setTipoPago(char tipoPago) {
		this.tipoPago = tipoPago;
	}
	/**
	 * Nombre del trámite en el que se realiza el pago. Se utilizará para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitación.   
	 * @return Nombre trámite 
	 */	
	public String getNombreTramite() {
		return nombreTramite;
	}
	/**
	 * Nombre del trámite en el que se realiza el pago. Se utilizará para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitación.
	 * @param nombreTramite Nombre trámite
	 */
	public void setNombreTramite(String nombreTramite) {
		this.nombreTramite = nombreTramite;
	}
	/**
	 * Nombre del usuario que realiza el trámite. Se utilizará para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitación.
	 * @return Nombre usuario que realiza el trámite
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	/**
	 * Nombre del usuario que realiza el trámite. Se utilizará para que la cabecera del asistente de pago
	 * sea similar al asistente de tramitación.
	 * @param nombreUsuario Nombre usuario que realiza el trámite
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}	
	/**
	 * Identificador del trámite
	 * @return Identificador del trámite
	 */
	public String getIdentificadorTramite() {
		return identificadorTramite;
	}
	/**
	 * Identificador del trámite
	 * @param identificadorTramite Identificador del trámite
	 */
	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}
	/**
	 * Modelo del trámite 
	 * @return Modelo del trámite
	 */
	public String getModeloTramite() {
		return modeloTramite;
	}
	/**
	 * Modelo del trámite
	 * @param modeloTramite Modelo del trámite
	 */
	public void setModeloTramite(String modeloTramite) {
		this.modeloTramite = modeloTramite;
	}
	/**
	 * Versión del trámite
	 * @return Versión del trámite
	 */
	public int getVersionTramite() {
		return versionTramite;
	}
	/**
	 * Versión del trámite
	 * @param versionTramite Versión del trámite
	 */
	public void setVersionTramite(int versionTramite) {
		this.versionTramite = versionTramite;
	}
	public String getIdentificadorOrganismo() {
		return identificadorOrganismo;
	}
	public void setIdentificadorOrganismo(String identificadorOrganismo) {
		this.identificadorOrganismo = identificadorOrganismo;
	}
	public String getTelefonoDeclarante() {
		return telefonoDeclarante;
	}
	public void setTelefonoDeclarante(String telefonoDeclarante) {
		this.telefonoDeclarante = telefonoDeclarante;
	}
	public Date getFechaMaximaPago() {
		return fechaMaximaPago;
	}
	public void setFechaMaximaPago(Date tiempoMaximoRealizarPago) {
		this.fechaMaximaPago = tiempoMaximoRealizarPago;
	}
	public String getMensajeTiempoMaximoPago() {
		return mensajeTiempoMaximoPago;
	}
	public void setMensajeTiempoMaximoPago(String mensajeTiempoMaximoPago) {
		this.mensajeTiempoMaximoPago = mensajeTiempoMaximoPago;
	}	
}
