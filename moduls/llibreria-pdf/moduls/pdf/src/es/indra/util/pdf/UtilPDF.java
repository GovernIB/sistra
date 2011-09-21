package es.indra.util.pdf;

import java.awt.Color;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodePDF417;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class UtilPDF {
	
	
	
	/**
	 * Concatena dos Pdfs
	 * @param pdfOut Outputstream al pdf de salida
	 * @param pdfIn InputStreams de los pdfs a concatenar
	 * @param pdfIn2
	 */
	public static void concatenarPdf(OutputStream pdfOut,InputStream [] pdfIn) throws Exception{
		if (pdfIn.length < 2) {
            throw new Exception("Debe concatenar al menos 2 PDFs");
        }
		
		// Realizamos copia PDFs        
        int f = 0;
        
        PdfCopyFields  writer = new PdfCopyFields(pdfOut);
        int permission=0;
        while (f < pdfIn.length) {
            // we create a reader for a certain document
            PdfReader reader = new PdfReader(pdfIn[f]);            
            // Establecemos permisos del primer documento
            if (f==0){
            	permission = reader.getPermissions();
            	if (permission != 0){
            		writer.setEncryption(null, null,permission, PdfWriter.STRENGTH40BITS);
            	}
            }
                        
            writer.addDocument(reader);
                        
            f++;
        }
        
        writer.close();
            
     }
	
	
	/**
	 * 
	 * @param pdfOut
	 * @param pdfIn
	 * @param pagesToDelete
	 * @throws Exception
	 */
	public static void extractPages(OutputStream pdfOut,InputStream pdfIn, int[] pages) throws Exception{
		
		if (pages.length <= 0) {
            throw new Exception("Debe eliminar al menos una página");
        }
			
		List pagesToKeep = new ArrayList();
		for (int i=0;i<pages.length;i++){
			pagesToKeep.add(new Integer(pages[i]));
		}
		
		
        PdfCopyFields  writer = new PdfCopyFields(pdfOut);
        int permission=0;
      
        PdfReader reader = new PdfReader(pdfIn);
        permission = reader.getPermissions();
        if (permission != 0){
        	writer.setEncryption(null, null,permission, PdfWriter.STRENGTH40BITS);
        }
	    writer.addDocument(reader,pagesToKeep);
        
        writer.close();
            
     }
	
	/*	ESTA VERSION NO GESTIONA BIEN CUANDO SE DUPLICAN FORMULARIOS
	 * 
	 * 
	 * Concatena dos Pdfs
	 * @param pdfOut Outputstream al pdf de salida
	 * @param pdfIn InputStreams de los pdfs a concatenar
	 * @param pdfIn2
	 
	public static void concatenarPdf(OutputStream pdfOut,InputStream [] pdfIn) throws Exception{
		if (pdfIn.length < 2) {
            throw new Exception("Debe concatenar al menos 2 PDFs");
        }
		
		// Realizamos copia PDFs
        int pageOffset = 0;
        ArrayList master = new ArrayList();
        int f = 0;
        
        Document document = null;
        PdfCopy  writer = null;
        int permission=0;
        while (f < pdfIn.length) {
            // we create a reader for a certain document
            PdfReader reader = new PdfReader(pdfIn[f]);
            
            // Establecemos permisos del primer documento
            if (f==0){
            	permission = reader.getPermissions();                                                
            }
            
            reader.consolidateNamedDestinations();
            // we retrieve the total number of pages
            int n = reader.getNumberOfPages();
            List bookmarks = SimpleBookmark.getBookmark(reader);
            if (bookmarks != null) {
                if (pageOffset != 0)
                    SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
                master.addAll(bookmarks);
            }
            pageOffset += n;
            
            if (f == 0) {
                // step 1: creation of a document-object
                document = new Document(reader.getPageSizeWithRotation(1));
                // step 2: we create a writer that listens to the document
                writer = new PdfCopy(document, pdfOut);
                // step 3: set permission 
                writer.setEncryption(null, null, permission, PdfWriter.STRENGTH40BITS);
                // step 4: we open the document
                document.open();
            }
            // step 4: we add content
            PdfImportedPage page;
            for (int i = 0; i < n; ) {
                ++i;
                page = writer.getImportedPage(reader, i);
                writer.addPage(page);
            }
            PRAcroForm form = reader.getAcroForm();
            if (form != null)
                writer.copyAcroForm(reader);
            f++;
        }
        if (master.size() > 0)
            writer.setOutlines(master);
        
        // step 5: we close the document
        document.close();    
     }
	*/
	
	/**
	 * Realiza stamp de los objetos indicados
	 * 
	 * @param pdfOut Pdf salida
	 * @param pdfIn Pdf entrada
	 * @param objects Objetos a realizar el stamp
	 * @throws Exception
	 */
	public static void stamp(OutputStream pdfOut,InputStream pdfIn,ObjectStamp [] objects) throws Exception {	        		   	
	
		PdfReader reader = new PdfReader(pdfIn);
		
		
		int permission = reader.getPermissions();
        int nPages = reader.getNumberOfPages();
        PdfStamper stamp = new PdfStamper(reader, pdfOut);

        // Volvemos a establecer los permisos que tuviera
        if (permission != 0) {
        	stamp.setEncryption(null, null, permission, PdfWriter.STRENGTH40BITS);
        }

        PdfContentByte under;
        PdfContentByte over;
        PdfContentByte content;
        
        for (int i=1;i<=nPages;i++){            
            under = stamp.getUnderContent(i);            
            over = stamp.getOverContent(i);
            for (int j = 0;j<objects.length;j++){
            	if (objects[j].getPage() == 0 || objects[j].getPage() == i){            		
	            	if (objects[j].isOverContent()){
	            		content = over;
	            	}else{
	            		content = under;
	            	}
	            	
	            	if (objects[j]  instanceof ImageStamp){
	            		ImageStamp imgStamp = (ImageStamp) objects[j];
	            		Image img = Image.getInstance(imgStamp.getImagen());	       	            		
		            	img.setAbsolutePosition(imgStamp.getX(),imgStamp.getY());
		            	img.setRotation(imgStamp.getRotation());
		            	
		            	if (imgStamp.getXScale() != null && imgStamp.getYScale() != null){
		            		if (imgStamp.isScalePerCent()){
		            			img.scalePercent(imgStamp.getXScale().floatValue(),imgStamp.getYScale().floatValue());
		            		}else{
		            			img.scaleToFit(imgStamp.getXScale().floatValue(),imgStamp.getYScale().floatValue());
		            		}
		            	}
		            	
		            	content.addImage(img);
	            	}
	            	
	            	if (objects[j]  instanceof BarcodeStamp){
	            		
	            		Image img = null;
	            		BarcodeStamp barcodeStamp = (BarcodeStamp) objects[j];
	            		
	            		switch ( barcodeStamp.getTipo()  ){
	            			case BarcodeStamp.BARCODE_128:
	            				Barcode128 code128 = new Barcode128();	 
	            				BaseFont bf = BaseFont.createFont(barcodeStamp.getFontName(), BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	            				code128.setFont(bf);
	    	        			code128.setSize(barcodeStamp.getFontSize());
	    	        			code128.setBaseline(barcodeStamp.getFontSize());
	    	        			code128.setCode(barcodeStamp.getTexto());
	    	        			img = code128.createImageWithBarcode(over, null, null);
	    	        			break;
	            			case BarcodeStamp.BARCODE_PDF417:
	            				BarcodePDF417 code417 = new BarcodePDF417();	
	    	        			code417.setText(barcodeStamp.getTexto());	
	    	        			img = code417.getImage();
	    	        			break;
	    	        		default:
	    	        			throw new Exception("Barcode especificado no soportado");
	            		}
	            		
	            		img.setAbsolutePosition(barcodeStamp.getX(),barcodeStamp.getY());
	            		img.setRotation(barcodeStamp.getRotation());
	            			            		
	            		if (barcodeStamp.getXScale() != null && barcodeStamp.getYScale() != null){
		            		img.scalePercent(barcodeStamp.getXScale().floatValue(),barcodeStamp.getYScale().floatValue());
		            	}
		            		          			            		
	            		content.addImage(img);
	            	}
	        				
	        		
	            	if (objects[j]  instanceof TextoStamp){
	            		BaseFont bf = BaseFont.createFont(((TextoStamp)objects[j]).getFontName(), BaseFont.CP1252, BaseFont.NOT_EMBEDDED);	            			            		
		            	content.beginText();		            	
		            	content.setColorFill(((TextoStamp)objects[j]).getFontColor());
		            	content.setFontAndSize(bf,((TextoStamp)objects[j]).getFontSize());
		            	content.setTextMatrix(30, 30);  				        	
		            	content.showTextAligned(Element.ALIGN_LEFT, ((TextoStamp)objects[j]).getTexto(),
		            			objects[j].getX(), objects[j].getY(), objects[j].getRotation());
		            	content.endText();
		            	
	            	}
	            	
	            	if (objects[j] instanceof NumeroPaginaStamp){
	            		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		            	content.beginText();
		            	content.setFontAndSize(bf,10);
		            	content.setTextMatrix(30,30);  	
		            	String texto = i + "/" + nPages;
		            	content.showTextAligned(Element.ALIGN_CENTER, texto,
		            			objects[j].getX(), objects[j].getY(), objects[j].getRotation());
		            	content.endText();
	            	}
	            	
	            	if (objects[j] instanceof SelloEntradaStamp){
		            	int width = 202;
		                int height = 92;            
		                float posX = objects[j].getX() ;
		                float posY = objects[j].getY();
		                Rectangle rectangle = new Rectangle(posX,posY,posX - width,posY - height);
		                rectangle.setBorderColor(Color.BLACK);               
		                rectangle.enableBorderSide(Rectangle.LEFT);
		                rectangle.enableBorderSide(Rectangle.RIGHT);
		                rectangle.enableBorderSide(Rectangle.TOP);
		                rectangle.enableBorderSide(Rectangle.BOTTOM);                        
		                rectangle.setBorderWidth(1);           
		                content.rectangle(rectangle);                               
		                
		                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		                content.beginText();
		                content.setFontAndSize(bf,7);
		                content.setColorStroke(Color.BLACK);
		                content.setTextMatrix(30, 30);  				 
		                String textoReservado = "Registre d'entrada";
		                content.showTextAligned(Element.ALIGN_LEFT, textoReservado ,posX - (width / 2) - (textoReservado.length() * 2), posY - 8 ,0);
		                content.endText();
	            	}
            	}
            }	             	     	           
        }    
        stamp.close();		
	}

	/**
	 * Genera un pdf vacío
	 * @param os
	 * @throws Exception
	 */
	public static void generateBlankPdf(OutputStream os) throws Exception
	{
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document,os);
		writer.setEncryption(null, null, PdfWriter.AllowPrinting, PdfWriter.STRENGTH40BITS);
		document.open();		
		document.newPage();
		writer.setPageEmpty(false);   					
		document.close();
	}
	
	/**
	 * Obtiene numero de paginas de un pdf
	 * @param pdfIn
	 * @return
	 * @throws Exception
	 */
	public static int getNumberOfPages(InputStream pdfIn) throws Exception{
		PdfReader reader = new PdfReader(pdfIn);
		int n = reader.getNumberOfPages();
		return n;		
	}
	
	/**
	 * Establece permisos pdf a solo impresion
	 * @param pdfOut Outputstream al pdf de salida con los permisos aplicados
	 * @param pdfIn InputStreams del pdf sobre el que se deben aplicar los permisos
	 */
	public static void establecerSoloImpresion(OutputStream pdfOut,InputStream pdfIn) throws Exception{
		PdfReader reader = new PdfReader(pdfIn);
		PdfEncryptor.encrypt(reader, pdfOut, null,null, PdfWriter.AllowPrinting, false);		
    }
	
}
