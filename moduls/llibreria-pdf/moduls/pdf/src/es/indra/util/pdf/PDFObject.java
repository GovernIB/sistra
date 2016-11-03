package es.indra.util.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;


public interface PDFObject {
	
	abstract void write(PDFDocument document, PdfPTable tabla) throws DocumentException;

}
