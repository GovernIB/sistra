package es.indra.util.pdf;

import java.util.List;
import java.util.Vector;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;

public class Subseccion implements PDFObject {

	private String titulo;
	
	private Vector campos = new Vector();
	
	

	public Vector getCampos() {
		return campos;
	}

	public void setCampos(Vector campos) {
		this.campos = campos;
	}
	
	public void addCampo(Propiedad campo)
	{
		campo.setPadding(60f);
		this.campos.add(campo);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Subseccion() {
		super();
		this.titulo = "";
	}

	public Subseccion(String titulo) {
		super();
		this.titulo = titulo;
	}

	private void writeTitulo(PDFDocument document, PdfPTable tabla)
	{
		if(titulo.equals("")) return;
		float[] widths = new float[2];
		widths[0] = 40f;
		widths[1] = 60f;
		PdfPTable innerTable = new PdfPTable(widths);
		PdfPCell cell = new PdfPCell(new Phrase(10,titulo,document.getContext().getFont("Titulo2")));
		cell.setBorder(Rectangle.NO_BORDER);
		innerTable.addCell(cell);
		cell = new PdfPCell(new Phrase(10,"",document.getContext().getDefaultFont()));
		cell.setBorder(Rectangle.RIGHT);
		innerTable.addCell(cell);
		cell = new PdfPCell(innerTable);
		cell.setPaddingLeft(30f);
		cell.setBorder(Rectangle.LEFT);
		cell.setColspan(2);
		tabla.addCell(cell);
	}

	public void write(PDFDocument document, PdfPTable tabla) throws DocumentException {
		writeTitulo(document,tabla);
		
		for(int i=0; i<campos.size(); i++)
		{
			Propiedad prop = (Propiedad)campos.get(i);
			prop.write(document,tabla);
		}
	}

}
