package es.indra.util.pdf;

import java.awt.Color;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

/**
 * 
 * Texto para realizar stamp en un pdf
 *
 */
public class TextoStamp extends ObjectStamp{
	/**
	 * Texto a mostrar
	 */
	private String texto;
	/**
	 * Fuente
	 */
	private String fontName=BaseFont.HELVETICA;
	/**
	 * Tamaño fuente
	 */
	private int fontSize=10;
	/**
	 * Estilo fuente
	 */
	private int fontStyle=Font.NORMAL;
	/**
	 * Color fuente
	 */
	private Color fontColor=Color.BLACK;
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public Color getFontColor() {
		return fontColor;
	}
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
}
