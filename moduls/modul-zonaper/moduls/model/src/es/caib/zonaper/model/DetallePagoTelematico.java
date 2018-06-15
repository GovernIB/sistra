package es.caib.zonaper.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Obtiene datos del Pago Telematico 
 * 
 */

public class DetallePagoTelematico implements Serializable{
	
	private String idPlugin;
	private String idPersistencia;
    private Timestamp fecha;
    private String idioma;
    private String localizador;
    private String dui;
    private String estadoPlataforma;
    private String estadoPortal;
    private String url;
    private String nombre;
    private String tasa;
    private String importe;
	private String concepto;
    private String codigoPostal;
    private String nif;
    private char tipo;
    
	public String getEstadoPlataforma() {
		return estadoPlataforma;
	}
	public void setEstadoPlataforma(String estadoPlataforma) {
		this.estadoPlataforma = estadoPlataforma;
	}
	public String getEstadoPortal() {
		return estadoPortal;
	}
	public void setEstadoPortal(String estadoPortal) {
		this.estadoPortal = estadoPortal;
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
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTasa() {
		return tasa;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
    public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getDui() {
		return dui;
	}
	public void setDui(String dui) {
		this.dui = dui;
	}
	public String getIdPlugin() {
		return idPlugin;
	}
	public void setIdPlugin(String idPlugin) {
		this.idPlugin = idPlugin;
	}
	

    
}
