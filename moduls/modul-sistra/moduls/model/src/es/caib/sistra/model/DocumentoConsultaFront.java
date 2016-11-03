package es.caib.sistra.model;

/**
 * 
 * Objeto que se utiliza para pasar al front la información 
 * de un documento resultado de una consulta
 *
 */
public class DocumentoConsultaFront {
	
	public final static char TIPO_DOCUMENTO_BIN = 'B';
	public final static char TIPO_DOCUMENTO_URL = 'U';

	private char tipo;
	private String nombre;
	private String urlEnlace;
	private boolean urlVentanaNueva;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isUrlVentanaNueva() {
		return urlVentanaNueva;
	}
	public void setUrlVentanaNueva(boolean urlVentanaNueva) {
		this.urlVentanaNueva = urlVentanaNueva;
	}
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public String getUrlEnlace() {
		return urlEnlace;
	}
	public void setUrlEnlace(String urlEnlace) {
		this.urlEnlace = urlEnlace;
	}
	
}
