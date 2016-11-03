package es.indra.util.pdf;

import java.awt.Color;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;

public class Seccion {
	
	private String apartado;
	
	private String titulo;
	
	private Vector campos = new Vector();
	
	private PdfPTable tabla;
	
	private boolean keepTogether = false;
	
	private boolean splitLate = false;
	
	private String _fuentetitulo; //fuente que se aplica en el titulo de la seccion

	public static final String F_TITULO2="Titulo2";
	public static final String F_NORMAL="Normal";
	public static final String F_ARIAL9="Arial9";
	public static final String F_ARIAL9BOLD="Arial9Bold";
	public static final String F_ARIAL11="Arial11";
	public static final String F_ARIAL11BOLD="Arial11Bold";
	public static final String F_ARIAL8="Arial8";
	public static final String F_ARIAL8BOLD="Arial8Bold";
	
	public Seccion(String apartado, String titulo) {
		super();
		// TODO Apéndice de constructor generado automáticamente
		this.apartado = apartado;
		this.titulo = titulo;
		_fuentetitulo="Titulo2";
	}

	/**
	 * 
	 * @param apartado
	 * @param titulo
	 */
	public Seccion(String apartado, String titulo, String fuentetitol) {
		super();
		// TODO Apéndice de constructor generado automáticamente
		this.apartado = apartado;
		this.titulo = titulo;
		_fuentetitulo=fuentetitol;
	}
	
	public String getApartado() {
		return apartado;
	}

	public void setApartado(String apartado) {
		this.apartado = apartado;
	}

	public List getCampos() {
		return campos;
	}

	public void setCampos(Vector campos) {
		this.campos = campos;
	}

	public PdfPTable getTabla() {
		return tabla;
	}

	public void setTabla(PdfPTable tabla) {
		this.tabla = tabla;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void addCampo(PDFObject campo)
	{
		this.campos.add(campo);
	}
	
	public void setKeepTogether(boolean keep)
	{
		this.keepTogether = keep;
	}

	public void setSplitLate(boolean split)
	{
		this.splitLate = split;
	}


	private void writeTitulo(PDFDocument document) throws DocumentException
	{
		float[] widths = {0.5f, 9.5f};
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		PdfPCell cell = new PdfPCell(new Phrase(12,apartado,document.getContext().getFont(_fuentetitulo)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xCC, 0xCC, 0xCC));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(12,titulo,document.getContext().getFont(_fuentetitulo)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingLeft(20f);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(1,""));
		cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(1,""));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		table.addCell(cell);
		this.tabla = table;
		this.tabla.setKeepTogether(keepTogether);
		this.tabla.setSplitLate(splitLate);
	}
	
	private void close(PDFDocument document) throws DocumentException
	{
		PdfPCell cell = new PdfPCell(new Phrase(15,""));
		cell.setColspan(2);
		cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.RIGHT);
		tabla.addCell(cell);
		cell = new PdfPCell(new Phrase(15,""));
		cell.setBorder(Rectangle.TOP);
		cell.setColspan(2);
		tabla.addCell(cell);
		document.getDocument().add(tabla);
	}

	public void write(PDFDocument document) throws DocumentException {

		writeTitulo(document);
		
		for(int i=0; i<campos.size(); i++)
		{
			PDFObject obj = (PDFObject)campos.get(i);
			obj.write(document,tabla);
		}

		close(document);
		
	}

	public boolean isKeepTogether() {
		return keepTogether;
	}

	public boolean isSplitLate() {
		return splitLate;
	}
	
	

}
