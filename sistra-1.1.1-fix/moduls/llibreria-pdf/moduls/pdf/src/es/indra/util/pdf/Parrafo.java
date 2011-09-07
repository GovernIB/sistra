package es.indra.util.pdf;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;

public class Parrafo implements PDFObject {

	private String texto;
	
	public static final int ALIGNMENT_LEFT = 0;

	public static final int ALIGNMENT_CENTER = 1;

	public static final int ALIGNMENT_RIGHT = 2;
	
	private int alignment;
	
	/**
	 * @param texto
	 * @param alignment
	 */
	public Parrafo(String texto, int alignment) {
		super();
		this.texto = texto;
		this.alignment = alignment;
	}

	public Parrafo(String texto) {
		super();
		this.texto = texto;
		this.alignment = Parrafo.ALIGNMENT_LEFT;
	}


	public int getAlignment() {
		return alignment;
	}



	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}



	public String getTexto() {
		return texto;
	}



	public void setTexto(String texto) {
		this.texto = texto;
	}



	public void write(PDFDocument document, PdfPTable tabla) throws DocumentException {
		Font f = document.getContext().getDefaultFont();				
		Paragraph bloque = new Paragraph(new Chunk(texto,f));
		PdfPCell cell = new PdfPCell(bloque);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
		cell.setPaddingLeft(30f);
		cell.setColspan(2);
		tabla.addCell(cell);
	}

	
}
