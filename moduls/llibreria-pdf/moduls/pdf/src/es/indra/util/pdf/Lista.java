package es.indra.util.pdf;

import java.util.List;
import java.util.Vector;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ListItem;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;

public class Lista implements PDFObject {

	private Vector campos = new Vector();
	
	private String titulo;
	
	public Lista(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Lista() {
		super();
		this.titulo = "";
	}

	
	public List getCampos() {
		return campos;
	}

	public void setCampos(Vector campos) {
		this.campos = campos;
	}
	
	public void addCampo(String campo)
	{
		this.campos.add(campo);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public void write(PDFDocument document, PdfPTable tabla) throws DocumentException {
		com.lowagie.text.List list = new com.lowagie.text.List(false,10f);
		list.setListSymbol(new Chunk("\u2022"));

		PdfPCell cell = new PdfPCell();
		
		if(!titulo.equals(""))
		{
		   cell.addElement(new Phrase(titulo,document.getContext().getDefaultFont()));
		}

		for(int i=0; i<campos.size(); i++)
		{
			list.add(new ListItem((String)campos.get(i),document.getContext().getDefaultFont()));
		}
		
		cell.addElement(list);
		cell.setPaddingLeft(30f);
		cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
		cell.setColspan(2);
		tabla.addCell(cell);
		
	}

}
