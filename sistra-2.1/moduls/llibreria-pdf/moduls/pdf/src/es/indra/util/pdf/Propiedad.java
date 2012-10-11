package es.indra.util.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;

public class Propiedad implements PDFObject {
	
	private String campo;
	
	private String value;
	
	private float padding;
	
	float[] widths = null;

	private String _fuenteatributo; //fuente que se aplica a la parte atributo
	private String _fuentevalor; //fuente que se aplica a la parte atributo

	public Propiedad(String campo, String value, float[] widths) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = 30f;
		this.widths = widths;
		_fuenteatributo="Titulo2";
		_fuentevalor=null;
	}

	public Propiedad(String campo, String value, float padding) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = padding;
		_fuenteatributo="Titulo2";
		_fuentevalor=null;
	}

	public Propiedad(String campo, String value) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = 30f;
		_fuenteatributo="Titulo2";
		_fuentevalor=null;
	}

	/**
	 * Constructor que se le pasa también la fuente que se quiere aplicar
	 * @param campo
	 * @param value
	 * @param fuenteatributo
	 * @param fuentevalor
	 */
	public Propiedad(String campo, String value, String fuenteatributo, String fuentevalor) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = 30f;
		_fuenteatributo=fuenteatributo;
		_fuentevalor=fuentevalor;
	}


	public String getCampo() {
		return campo;
	}



	public void setCampo(String campo) {
		this.campo = campo;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}


	public void write(PDFDocument document, PdfPTable tabla) throws DocumentException {
		
		if(this.widths == null)
		{
			widths = new float[2];
			widths[0] = 40f;
			widths[1] = 60f;
		}
		PdfPTable innerTable = new PdfPTable(widths);
		String texto = this.campo;
		if(value = null)
		{
			value = "";
		}
		if(!value.equals(""))
		{
			texto += ":";
		}
		PdfPCell cell = new PdfPCell(new Phrase(10,texto,document.getContext().getFont(_fuenteatributo)));
		cell.setBorder(Rectangle.NO_BORDER);
		innerTable.addCell(cell);
		Font fuentevalor = (_fuentevalor==null)?document.getContext().getDefaultFont():document.getContext().getFont(_fuentevalor);
		cell = new PdfPCell(new Phrase(10,value, fuentevalor));
		cell.setBorder(Rectangle.NO_BORDER);
		innerTable.addCell(cell);
		cell = new PdfPCell(innerTable);
		cell.setPaddingLeft(this.padding);
		cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
		cell.setColspan(2);
		tabla.addCell(cell);
		
	}

	public float getPadding() {
		return padding;
	}

	public void setPadding(float padding) {
		this.padding = padding;
	}

}
