package es.indra.util.pdf;

import com.lowagie.text.pdf.BaseFont;

/**
 * 
 * Texto para realizar stamp de un barcode en un pdf
 *
 */
public class BarcodeStamp extends ObjectStamp{
	
	public static final int BARCODE_128 = 0;
	public static final int BARCODE_PDF417 = 1;
	
	/**
	 * Tipo de barcode
	 */
	private int tipo =  BARCODE_128;
	
	/**
	 * Fuente
	 */
	private String fontName=BaseFont.HELVETICA;
	/**
	 * Tamaño fuente
	 */
	private int fontSize=14;
	
	/**
	 * Texto a mostrar
	 */
	private String texto;
	/**
	 * Escala X
	 */
	private Float xScale=null;
	
	/**
	 * Escala Y
	 */
	private Float yScale=null;
	
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Float getXScale() {
		return xScale;
	}
	public void setXScale(Float scale) {
		xScale = scale;
	}
	public Float getYScale() {
		return yScale;
	}
	public void setYScale(Float scale) {
		yScale = scale;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
}
