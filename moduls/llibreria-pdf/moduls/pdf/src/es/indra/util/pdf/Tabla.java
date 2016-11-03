package es.indra.util.pdf;

import java.awt.Color;
import java.util.Vector;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class Tabla implements PDFObject {

	private Vector columnas;
	
	private Vector campos;

	// Permite establecer el tamaño de las columnas (en porcentaje)
	private int[] tamanyoColumnas;
	
	// Permite establecer si se muestran las cabeceras
	private boolean mostrarCabeceras=true;
	
	// Ancho cols calculadas (uso interno)
	private float[] anchoColumnas;		

	public Tabla(Vector columnas, Vector campos) {
		super();
		this.columnas = columnas;
		this.campos = campos;
	}
	
	public Tabla(Vector columnas, Vector campos,int[] tamanyoColumnas) {
		super();
		this.columnas = columnas;
		this.campos = campos;
		this.tamanyoColumnas = tamanyoColumnas;
	}

	public Vector getCampos() {
		return campos;
	}

	public void setCampos(Vector campos) {
		this.campos = campos;
	}



	public Vector getColumnas() {
		return columnas;
	}

	public void setColumnas(Vector columnas) {
		this.columnas = columnas;
	}


	private PdfPCell createCell(String texto, Color color, int alignment, Font font)
	{
		PdfPCell cell = new PdfPCell(new Phrase(texto,font));
		cell.setHorizontalAlignment(alignment);
		cell.setBackgroundColor(color);
		cell.setPaddingLeft(10f);
		//this.tabla.addCell(cell);
		return cell;
	}

	private PdfPCell createCell(String texto)
	{
		PdfPCell cell = new PdfPCell(new Phrase(texto));
		cell.setPaddingLeft(10f);
		cell.setHorizontalAlignment(Rectangle.LEFT);
		//this.tabla.addCell(cell);
		return cell;
	}

	private PdfPCell createCell(String texto,int border)
	{
		PdfPCell cell = new PdfPCell(new Phrase(texto));
		cell.setBorder(border);
		cell.setPaddingLeft(10f);
		//this.tabla.addCell(cell);
		return cell;
	}

	private PdfPCell createCell(String texto,Font font)
	{
		PdfPCell cell = new PdfPCell(new Phrase(texto,font));
		cell.setPaddingLeft(10f);
		//this.tabla.addCell(cell);
		return cell;
	}

	private PdfPCell createCell(String texto, int alignment, Font font ,int border)
	{
		PdfPCell cell = new PdfPCell(new Phrase(texto,font));
		cell.setHorizontalAlignment(alignment);
		cell.setPaddingLeft(10f);
		//this.tabla.addCell(cell);
		return cell;
	}

	
	private void writeColumns(PDFDocument document, PdfPTable tb)
	{
		PdfPTable tabla = new PdfPTable(anchoColumnas);		
		tabla.setWidthPercentage(100);
//		this.tabla.setSplitRows(true);
		
		Font font = document.getContext().getDefaultFont();
		PdfPCell cell;
		cell = createCell("",Rectangle.LEFT | Rectangle.RIGHT);
		tabla.addCell(cell);
		for(int i=0; i<columnas.size(); i++)
		{
			cell = createCell((String)columnas.get(i),document.getContext().getColor(204,204,204),
					  Rectangle.ALIGN_CENTER,font);
			tabla.addCell(cell);
		}
		cell = createCell("",Rectangle.LEFT | Rectangle.RIGHT);
		tabla.addCell(cell);
		addTabla(tb,tabla);
	}
	
	private void writeCampos(PDFDocument document, PdfPTable tb)
	{
		PdfPTable tabla = new PdfPTable(anchoColumnas);
		PdfPCell cell;
		tabla.setWidthPercentage(100);
		Font font = document.getContext().getDefaultFont();
		for(int i=0; i<campos.size();i++)
		{
			cell = createCell("",Rectangle.LEFT | Rectangle.RIGHT);
			tabla.addCell(cell);
			Vector cp = (Vector)campos.get(i);
			for(int j=0; j<cp.size(); j++)
			{
				cell = createCell((String)cp.get(j),font);
				tabla.addCell(cell);
			}
			cell = createCell("",Rectangle.LEFT | Rectangle.RIGHT);
			tabla.addCell(cell);
		}
		addTabla(tb,tabla);
	}
	
	private void close(PDFDocument document, PdfPTable tb) throws DocumentException
	{
		PdfPTable tabla = new PdfPTable(anchoColumnas);
		PdfPCell cell;
		cell = createCell("",Rectangle.LEFT);
		tabla.addCell(cell);
		for(int i=0; i<columnas.size(); i++)
		{
			cell = createCell("",Rectangle.TOP);
			tabla.addCell(cell);
		}
		cell = createCell("",Rectangle.RIGHT);
		tabla.addCell(cell);
		addTabla(tb,tabla);
	}

	private void calculateWidth()
	{
		boolean errorPercent=false;
		
		anchoColumnas = new float[columnas.size() + 2];
		anchoColumnas[0] = 3f;
		anchoColumnas[columnas.size()+1] = 3f;
		
		// Si establecemos porcentaje tamaño lo repartimos según porcentaje. Debe estar correctamente repartido:
		// (suma de todas columnas = 100%)
		int total=0;
		if (this.tamanyoColumnas != null && this.tamanyoColumnas.length > 0 ){
			for (int i=0;i<tamanyoColumnas.length;i++) total += tamanyoColumnas[i];
			if (this.tamanyoColumnas.length != columnas.size() || total != 100){
				errorPercent = true;
			}else{					
				float totalc=0;
				for (int i=0;i<tamanyoColumnas.length - 1;i++){
					anchoColumnas[i+1] = (tamanyoColumnas[i] *  90f)/ 100f;
					totalc=+anchoColumnas[i+1];
				}
				anchoColumnas[this.tamanyoColumnas.length] = 90f - totalc;
			}			
		}
		
		// Si no se establece porcentaje tamaño lo repartimos equitativamente
		// Si no se establece correctamente el porcentaje, también
		if (this.tamanyoColumnas == null || this.tamanyoColumnas.length == 0 || errorPercent ){		
			float widthColumns = 90/columnas.size();			
			for(int i=1; i<= columnas.size(); i++)
			{
				anchoColumnas[i] = widthColumns;
			}			
		}
	}
	
	private void addTabla(PdfPTable tablaParent, PdfPTable tabla)
	{
		PdfPCell cell = new PdfPCell(tabla);
		cell.setColspan(4);
		cell.setBorder(Rectangle.NO_BORDER);
		tablaParent.addCell(cell);
	}



	public void write(PDFDocument document, PdfPTable tabla) throws DocumentException {
		
		calculateWidth();
		if (isMostrarCabeceras())
			writeColumns(document,tabla);
		
		writeCampos(document,tabla);
	
		close(document, tabla);
		
		//document.getDocument().add(cell);
	}

	public boolean isMostrarCabeceras() {
		return mostrarCabeceras;
	}

	public void setMostrarCabeceras(boolean mostrarCabeceras) {
		this.mostrarCabeceras = mostrarCabeceras;
	}

}
