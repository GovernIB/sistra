package es.indra.util.pdf;

import java.awt.Color;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class PDFDocument {
	
	public static final String TYPE_HEAD_NORMAL = "NORMAL";
	public static final String TYPE_HEAD_LARGE = "LARGE";
	public static final String TYPE_HEAD_SMALL = "SMALL";	
	/**
	 * Url de logo
	 */
	private String imagen;
	/**
	 * Content de imagen de logo
	 */
	private byte[] imagenContent;	
	/**
	 * Titulo del documento
	 */
	private String titulo;
	/**
	 * Indica si reserva sitio para el sello
	 */
	private boolean sello; 
	/**
	 * Texto a mostrar en el sello
	 */
	private String textoSello; 
	/**
	 * Texto de cabecera
	 */
	private String cabecera=null;
	/**
	 * Texto de pie de página
	 */
	private String pie=null;
	/**
	 * Barcode
	 */
	private String barcode;
	
	/**
	 * Objetos para creación PDF
	 */
	private Vector secciones = new Vector();
	private PdfWriter writer;
	private Document document; 
	private ContextPDF context;
	

	protected String alineacion=null;
	protected boolean paginar=false;
	protected String tipocabecera=TYPE_HEAD_NORMAL;
	protected boolean noCabecera=false;

	
	
	public PDFDocument(String titulo) {
		super();
		this.imagen = null;
		this.titulo = titulo;
		this.context = new ContextPDF();
		context.initFonts();
	}
	
	public PDFDocument(String imagen, String titulo) {
		super();
		this.imagen = imagen;
		this.titulo = titulo;
		this.context = new ContextPDF();
		context.initFonts();
	}

	/**
	 * Constructor para indicar paginacion
	 * @param imagen
	 * @param titulo
	 * @param paginar
	 */
	public PDFDocument(String imagen, String titulo, boolean paginar) {
		super();
		this.imagen = imagen;
		this.titulo = titulo;
		this.paginar = paginar;
		this.context = new ContextPDF();
		context.initFonts();
	}
	
	/**
	 * Constructor para no tener cabecera
	 * @param imagen
	 * @param titulo
	 * @param paginar
	 */
	public PDFDocument(String titulo, boolean paginar, boolean noCabecera) {
		super();
		this.noCabecera = noCabecera;
		this.titulo = titulo;
		this.paginar = paginar;
		this.context = new ContextPDF();
		context.initFonts();
	}
	

	
	public PDFDocument(String imagen, String titulo, String barcode) {
		super();
		this.imagen = imagen;
		this.titulo = titulo;
		this.barcode = barcode;
		this.context = new ContextPDF();
		context.initFonts();
	}
	
	public PDFDocument(byte[] imagen, String titulo) {
		super();
		this.imagenContent = imagen;
		this.titulo = titulo;
		this.context = new ContextPDF();
		context.initFonts();
	}
	
	/**
	 * Constructor para paginar
	 * @param imagen
	 * @param titulo
	 * @param paginar
	 */
	public PDFDocument(byte[] imagen, String titulo, boolean paginar) {
		super();
		this.imagenContent = imagen;
		this.titulo = titulo;
		this.paginar = paginar;
		this.context = new ContextPDF();
		context.initFonts();
	}
	
	public PDFDocument(byte[] imagen, String titulo, String barcode) {
		super();
		this.imagenContent = imagen;
		this.titulo = titulo;
		this.barcode = barcode;
		this.context = new ContextPDF();
		context.initFonts();
	}
	

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	public List getSecciones() {
		return secciones;
	}

	public void setSecciones(Vector secciones) {
		this.secciones = secciones;
	}
	
	public void addSeccion(Seccion seccion)
	{
		this.secciones.add(seccion);
	}
	
	private void writeCabecera() throws Exception
	{
		float[] widths;
		if(this.imagen == null && this.imagenContent == null)
		{
			widths = new float[1];
			widths[0] = 10f;
		}
		else
		{
			widths = new float[2];
			widths[0] = 1f;
			widths[1] = 4f;
		}
		
		separador(20f);
		
		PdfPTable table;
		table = new PdfPTable(widths);
		
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.setWidthPercentage(this.isSello()?62:100);	
		// Imagen en formato URL
		if(this.imagen != null)
		{			
			Image image = Image.getInstance(imagen);
			table.addCell(image);
		}
		// Imagen en formato byte
		if(this.imagenContent != null)
		{			
			Image image = Image.getInstance(imagenContent);
			table.addCell(image);
		}
		
		PdfPCell cell = new PdfPCell(new Phrase(14, titulo, context.getFont("Titulo1")));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(this.isSello()?60f:50f);			
		table.addCell(cell);					
		document.add(table);
		
		
		// Rectangulo para sello
		if (this.isSello()){
            PdfContentByte cb = writer.getDirectContent();                                    
            
            int width = 202;
            int height = 92;            
            float posX = PageSize.A4.width() - 30 ;
            float posY = PageSize.A4.height() - 40;
            Rectangle rectangle = new Rectangle(posX,posY,posX - width,posY - height);
            rectangle.setBorderColor(Color.BLACK);               
            rectangle.enableBorderSide(Rectangle.LEFT);
            rectangle.enableBorderSide(Rectangle.RIGHT);
            rectangle.enableBorderSide(Rectangle.TOP);
            rectangle.enableBorderSide(Rectangle.BOTTOM);                        
            rectangle.setBorderWidth(1);
            //rectangle.setBackgroundColor(new Color(223,223,223));            
            cb.rectangle(rectangle);                               
            
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf,7);
            cb.setColorStroke(Color.BLACK);
            cb.setTextMatrix(30, 30);  				 
            String textoReservado = this.getTextoSello();
            //cb.showTextAligned(Element.ALIGN_LEFT, textoReservado ,(posX + width) - (textoReservado.length() * 2) , posY ,0);
            cb.showTextAligned(Element.ALIGN_LEFT, textoReservado ,posX - (width / 2) - (textoReservado.length() * 2), posY - 8 ,0);
            cb.endText();
		}		
		separador(20f);
	}
	
	private void writeCabecera2() throws Exception {
		float[] widths;
		if(this.imagen == null && this.imagenContent == null) {
			widths = new float[1];
			widths[0] = 10f;
		} else {
			widths = new float[2];
			widths[0] = 1f;
			widths[1] = 10f;
		}			
		
		if ((alineacion!=null) && (alineacion.equals("H"))) {
			if(this.imagen == null && this.imagenContent == null) {
				widths = new float[1];
				widths[0] = 15f;
			} else {
				widths = new float[2];
				widths[0] = 1f;
				widths[1] = 15f;
			}			
		}
		
		separador(2f);
		
		PdfPTable table;
		table = new PdfPTable(widths);
		
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.setWidthPercentage(this.isSello()?62:100);	
		// Imagen en formato URL
		if(this.imagen != null)
		{			
			Image image = Image.getInstance(imagen);
			table.addCell(image);
		}
		// Imagen en formato byte
		if(this.imagenContent != null)
		{			
			Image image = Image.getInstance(imagenContent);
			table.addCell(image);
		}
		
		PdfPCell cell = new PdfPCell(new Phrase(14, titulo, context.getFont("Titulo1")));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(this.isSello()?60f:50f);			
		table.addCell(cell);
		document.add(table);
		
		
		// Rectangulo para sello
		if (this.isSello()){
            PdfContentByte cb = writer.getDirectContent();                                    
            
            int width = 202;
            int height = 92;            
            float posX = PageSize.A4.width() - 30 ;
            float posY = PageSize.A4.height() - 40;
            Rectangle rectangle = new Rectangle(posX,posY,posX - width,posY - height);
            rectangle.setBorderColor(Color.BLACK);               
            rectangle.enableBorderSide(Rectangle.LEFT);
            rectangle.enableBorderSide(Rectangle.RIGHT);
            rectangle.enableBorderSide(Rectangle.TOP);
            rectangle.enableBorderSide(Rectangle.BOTTOM);                        
            rectangle.setBorderWidth(1);
            //rectangle.setBackgroundColor(new Color(223,223,223));            
            cb.rectangle(rectangle);                               
            
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf,7);
            cb.setColorStroke(Color.BLACK);
            cb.setTextMatrix(30, 30);  				 
            String textoReservado = this.getTextoSello();
            //cb.showTextAligned(Element.ALIGN_LEFT, textoReservado ,(posX + width) - (textoReservado.length() * 2) , posY ,0);
            cb.showTextAligned(Element.ALIGN_LEFT, textoReservado ,posX - (width / 2) - (textoReservado.length() * 2), posY - 8 ,0);
            cb.endText();
		}		
		separador(10f);
	}
	
	public void separador(float height) throws Exception{
		Font font = new Font(Font.HELVETICA, 8, Font.ITALIC, Color.GRAY);
		PdfPTable sep = new PdfPTable(1);		
		PdfPCell cell = new PdfPCell(new Phrase(14, "", font));
		cell.setBorder(0);
		cell.setMinimumHeight(height);
		sep.addCell(cell);
		document.add(sep);
	}
	
	public void generate(OutputStream os) throws Exception
	{
		document = new Document(PageSize.A4, 36, 36, 36, 50);
		writer = PdfWriter.getInstance(document,os);		
		writer.setEncryption(null, null, PdfWriter.AllowCopy | PdfWriter.AllowPrinting, PdfWriter.STRENGTH40BITS);
		writer.setPageEvent(new EndPage(this.getCabecera(),this.getPie(),this.getBarcode(),this.isPaginar()));		
		document.open();				
		//Pintar la cabecera que proceda: No pitar, cabecera normal o cabecera pequeña
		if(!noCabecera) {
			if (tipocabecera.equals(TYPE_HEAD_SMALL)) writeCabecera2();
			else writeCabecera();
		}

		
		for(int i=0; i<secciones.size(); i++)
		{
			Seccion obj = (Seccion)secciones.get(i);
			obj.write(this);
		}		
		
		document.close();
	}

	public void generate(OutputStream os, String orientacion) throws Exception
	{
		alineacion=orientacion;
		if (orientacion.equals("H")) 
			document = new Document(PageSize.A4.rotate(), 36, 36, 36, 50);
		else 
			document = new Document(PageSize.A4, 36, 36, 36, 50);
		writer = PdfWriter.getInstance(document,os);		
		writer.setEncryption(null, null,PdfWriter.AllowCopy | PdfWriter.AllowPrinting, PdfWriter.STRENGTH40BITS);
		writer.setPageEvent(new EndPage(this.getCabecera(),this.getPie(),this.getBarcode(),this.isPaginar()));		
		document.open();				
		//Pintar la cabecera que proceda: No pitar, cabecera normal o cabecera pequeña
		if(!noCabecera) {
			if (tipocabecera.equals(TYPE_HEAD_SMALL)) writeCabecera2();
			else writeCabecera();
		}
		
		for(int i=0; i<secciones.size(); i++)
		{
			Seccion obj = (Seccion)secciones.get(i);
			obj.write(this);
		}		
		
		document.close();
	}
	
	public ContextPDF getContext() {
		return context;
	}

	public void setContext(ContextPDF context) {
		this.context = context;
	}

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getCabecera() {
		return cabecera;
	}

	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}

	public String getPie() {
		return pie;
	}

	public void setPie(String pie) {
		this.pie = pie;
	}

	/**
	 * Evento para cabecera y pie de páginas
	 */
	public class EndPage extends PdfPageEventHelper {
	    
		private String cabecera = null;
		private String pie = null;
		private String barCode = null;
		private boolean paginar = false;

		/**
		 * Template para la paginacion
		 */
		protected PdfTemplate tplTotal;
		protected BaseFont bFont;
		
		public EndPage(String cabecera,String pie,String barCode,boolean paginar){
			super();
			this.cabecera = cabecera;
			this.pie = pie;
			this.barCode = barCode;
			this.paginar=paginar;
		}	
					
		public void onEndPage(PdfWriter writer, Document document) {	        
            if (paginar) {
            	onFinPagina2(writer, document);
            } else {
				Rectangle page = document.getPageSize();
	            
	            Font font = new Font(Font.HELVETICA, 7, Font.ITALIC, Color.GRAY);
	            
	            if (cabecera != null){
		            PdfPTable head = new PdfPTable(1);	            
		            head.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		            head.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);	            
		            Paragraph bloque = new Paragraph(new Chunk(cabecera,font));
		            head.addCell(bloque);
		            head.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
		            head.writeSelectedRows(0, -1, document.leftMargin(), page.height() - document.topMargin() + head.getTotalHeight(),writer.getDirectContent());
	            }            
	            
	            // Texto de pie y código de barras
	            if (pie == null) pie = "";
	        	float[] widths = {4f, 1f};		         
	        	
	        	PdfPTable foot = new PdfPTable(widths);	            	            
	            foot.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	            foot.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	           
	            
	            PdfPCell cell = new PdfPCell(new Phrase(14, "", font));    		
	    		cell.setMinimumHeight(15f);
	    		cell.setBorder(0);
	            foot.addCell(cell);
	            foot.addCell(cell);
	            
	            Paragraph bloque = new Paragraph(new Chunk(pie,font));
	            foot.addCell(bloque);	           
	            foot.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
	            if (barCode != null){
		            foot.addCell(new Phrase(new Chunk(generateBarCode(), 0, 0)));	            	            	           
	            }else{
	            	foot.addCell(new Phrase(new Chunk("",font)));
	            }
	            
	            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),writer.getDirectContent()); 
            }
	    }
		
		
		private void onFinPagina2(PdfWriter writer, Document document) {
			Rectangle page = document.getPageSize();
            PdfContentByte cb = writer.getDirectContent();
            
            Font font = new Font(Font.HELVETICA, 7, Font.ITALIC, Color.GRAY);
            
            
            if (cabecera != null){
	            PdfPTable head = new PdfPTable(1);	            
	            head.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	            head.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);	            
	            Paragraph bloque = new Paragraph(new Chunk(cabecera,font));
	            head.addCell(bloque);
	            head.addCell(new Phrase(new Chunk("",font)));
	            head.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
	            head.writeSelectedRows(0, -1, document.leftMargin(), page.height() - document.topMargin() + head.getTotalHeight(),writer.getDirectContent());
            }            
            
            // Texto de pie y código de barras
            if (pie == null) pie = "";
        	
            
            /** VRS: cambio para añadir paginacion */
            cb.saveState();
            String text = pie + "  -  Pág. " + writer.getPageNumber() + " de ";
    		float textBase = document.bottom() - 20;
    		float textSize = bFont.getWidthPoint(text, 7);
    		
    		
    		cb.saveState();
    		cb.beginText();
    		cb.setFontAndSize(bFont, 7);
    		
    		
			float adjust = bFont.getWidthPoint("0", 7);
			cb.setTextMatrix(document.right() - textSize - adjust, textBase);
			cb.setColorFill(Color.GRAY);
			cb.showText(text);
			cb.endText();
			cb.addTemplate(tplTotal, document.right() - adjust, textBase);
    		
    		cb.restoreState();
    		

		}
		
		private Image generateBarCode()
		{
			try {
			Barcode128 code128 = new Barcode128();
			
			code128.setSize(12f);
			code128.setBaseline(12f);
			code128.setCode(barcode);

			Image img = code128.createImageWithBarcode(writer.getDirectContent(), null, null);
			img.scalePercent(50, 50);
		
					
			//img.setAbsolutePosition(PageSize.A4.width() - img.width() - 10,10);			
    		//document.add(img);
			
			return img;
			}catch (Exception ex){
				ex.printStackTrace();
				return null;
			}
		}
		
		
		public void onOpenDocument(PdfWriter writer, Document document) {
			if (paginar) {
				tplTotal = writer.getDirectContent().createTemplate(100, 100);
				tplTotal.setBoundingBox(new Rectangle(-20, -20, 100, 100));
				try {
					bFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
				} catch (Exception e) {
					throw new ExceptionConverter(e);
				}
			}
		}

		
		public void onCloseDocument(PdfWriter writer, Document document) {
			if (paginar) {
				tplTotal.beginText();
				tplTotal.setFontAndSize(bFont, 7);
				tplTotal.setColorFill(Color.GRAY);
				tplTotal.setTextMatrix(0, 0);
				tplTotal.showText(String.valueOf(writer.getPageNumber() - 1));
				tplTotal.endText();
			}
		}


	}

	public boolean isSello() {
		return sello;
	}

	public void setSello(boolean sello) {
		this.sello = sello;
	}

	public String getTextoSello() {
		return textoSello;
	}

	public void setTextoSello(String textoSello) {
		this.textoSello = textoSello;
	}

	public boolean isPaginar() {
		return paginar;
	}

	public void setPaginar(boolean paginar) {
		this.paginar = paginar;
	}

	public String getTipocabecera() {
		return tipocabecera;
	}

	public void setTipocabecera(String tipocabecera) {
		this.tipocabecera = tipocabecera;
	}
	

}
