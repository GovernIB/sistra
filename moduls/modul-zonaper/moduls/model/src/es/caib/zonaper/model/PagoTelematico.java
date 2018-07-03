package es.caib.zonaper.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Obtiene datos del Pago Telematico 
 * 
 */

public class PagoTelematico implements Serializable{
	

	private String idPersistencia;
    private Timestamp fecha;
    private Timestamp fechaPago;
    private String idioma;
    private String localizador;
    private String dui;
    private String importe;
	private String estado;
    private String estadoTramite;
    private String url;
    private Long codigoRDS;
    private String claveRDS;
    private char tipo;
    
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getIdPersistencia() {
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}
	public String getLocalizador() {
		return localizador;
	}
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClaveRDS() {
		return claveRDS;
	}
	public void setClaveRDS(String claveRDS) {
		this.claveRDS = claveRDS;
	}
	public Long getCodigoRDS() {
		return codigoRDS;
	}
	public void setCodigoRDS(Long codigoRDS) {
		this.codigoRDS = codigoRDS;
	}
	public Timestamp getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Timestamp fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getEstadoTramite() {
		return estadoTramite;
	}
	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}
	public String getDui() {
		return dui;
	}
	public void setDui(String dui) {
		this.dui = dui;
	}
    public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}

    
}
