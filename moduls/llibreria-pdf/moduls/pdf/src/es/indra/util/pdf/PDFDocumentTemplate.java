package es.indra.util.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodePDF417;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Librería para manejo de PDFs con iText
 *
 */
public class PDFDocumentTemplate {

	private String NombrePlantillaPDF = null;
	private String NombreSalida = null;
	private PdfReader  pdfr;
	private PdfStamper pdfs;
	private OutputStream ostream;
	private AcroFields afields;
	private boolean docCerrado = false;
		
	/* 
	 * Constructor, recibe como parámetro un Blob.
	 */
	public PDFDocumentTemplate (Blob plantillaPdf) throws Exception
	{
		long lon = -1;
		byte [] b;
		
		NombrePlantillaPDF = "";
		lon = plantillaPdf.length();
	    b = plantillaPdf.getBytes(1,(int)lon + 1);
		pdfr = new PdfReader(b);
		iniciarPdf();
	}

	/* 
	 * Constructor, recibe como parámetro un byte [].
	 */
	public PDFDocumentTemplate (byte [] plantillaPdf) throws Exception
	{
		NombrePlantillaPDF = "";
		pdfr = new PdfReader(plantillaPdf);
		iniciarPdf();
	}
	
	
	/* Constructor, recibe como parámetro la ruta completa (path absoluto) del fichero que se
	 * va a tratar. Normalmente esta será la ruta a la plantilla PDF que se quiere utilizar.
	 */
	public PDFDocumentTemplate (String Nombre) throws Exception
	{
		
		NombrePlantillaPDF = Nombre;
		
		pdfr = new PdfReader(NombrePlantillaPDF);
		
		iniciarPdf();
	}
	
	/**
	 * Inicia objeto PDF (Uso interno)
	 * @throws Exception
	 */
	private void iniciarPdf() throws Exception
	{
		ostream = new ByteArrayOutputStream();
		pdfs = new PdfStamper(pdfr, ostream);
		pdfs.getWriter().setPdfVersion(PdfWriter.VERSION_1_3);
		afields = pdfs.getAcroFields();
	}
	
	/**
	 * Obtiene la lista de campos
	 * @return
	 */
	public HashMap getCampos(){
		return afields.getFields();
	}
	
	/**
	 * Permite introducir valores en campos de PDF. Tiene como parámetros un string
	 * que identifica el campo del PDF y un string que contiene el valor a introducir
	 * en el campo.
	 */	
	public void ponerValor (String campo, String valor) throws Exception
	{
		if (!afields.getFields().containsKey(campo)) return;
		if (!afields.setField(campo, valor)) throw new Exception("Error al establecer campo: " + campo + " con valor: " + valor);		
	}

	/** 
	 * Permite introducir valores en campos de PDF mediante una tabla Hash que 
	 * contenga pares (campo, valor). Mejora el acceso cuando se parsea el contenido
	 * de un XML.
	 */	
	public void ponerValor (Map datos) throws Exception
	{
		String clave = null;	
		Set seto = datos.keySet();
		Iterator iterator = seto.iterator();
	
		while (iterator.hasNext()){
			clave = (String)iterator.next();
			String valor = ( datos.get(clave) != null ? 
							 datos.get(clave).toString() : "" );
			ponerValor(clave, valor);
		}		
	}
	
	/** Permite obtener el valor de un campo del PDF. Tiene como parámetro el string
	 * que identifica el campo del PDF del que se quiere obtener el valor.
	 * @param campo
	 * @return
	 */	
	public String getValor (String campo)
	{
		return afields.getField(campo);		
	}

	/**
	 * Método que guarda las modificaciones realizadas al PDF y vuelca el resultado a un
	 * fichero. Es fundamental llamar a este procedimiento cuando se finaliza la modificación del PDF
	 * ya que si no no se generará el PDF de salida. Como parámetro recibo el
	 * nombre del fichero de salida.
	 */
	public void guardar (String Salida, boolean formFlattening) throws Exception
	{		
		NombreSalida = Salida;
		FileOutputStream fout = new FileOutputStream(NombreSalida);
		fout.write(guardarEnMemoria(formFlattening));
		fout.close();				
	}

	/**
	 *  Método que guarda las modificaciones realizadas al PDF y vuelca el resultado a un
	 * array de bytes. Es fundamental llamar a este procedimiento cuando se finaliza la modificación del PDF
	 * ya que si no no se generará el PDF de salida. Sólo se puede llanar a uno de los dos guardar, o se llama
	 * al anterior que guarda en fichero o se llama a este que devuelve un array de bytes. En caso de que
	 * sea necesario obtener el array de bytes y luego guardar en un fichero, se realizará furea de esta clase
	 * volcando el array de bytes a un FileOutputStream.
	 * 
	 * @param formFlattening Indica si hace el flatenning, que implica que deja los campos del formulario no editables
	 *  en un futuro (para proteger completamente de la edicion).
	 * 
	 */
	public byte[] guardarEnMemoria (boolean formFlattening) throws Exception
	{
		try{
			if (!docCerrado)
			{			
				if (formFlattening) {
					pdfs.setFormFlattening(true);
				}
				pdfs.close();
				docCerrado = true;
			}
			return ((ByteArrayOutputStream) ostream).toByteArray();
		} finally
		{
			if (ostream != null){ 	try	{ostream.close();}catch (Throwable ex){} }
		}
	}
	
	/**
	 * Método que bloquea mediante encriptación el PDF de salida. Este método permite
	 * únicamente la impresión del mismo. Si es necesario llamar al método,
	 * este debe ser invocado antes de asignar campos al mismo (es conveniente
	 * llamar a este métoco nada mas crear el objeto GLibPDF)
	 */
	public void establecerSoloImpresion () throws Exception
	{
		//pdfs.setEncryption(null, null, PdfWriter.AllowPrinting, true);
		// Introducimos encriptación de 40 bits para que sea compatible con Acrobat 4.0
		// La encriptación de 128 sólo es compatible con 5.0 o superior
		
		pdfs.setEncryption(null, null, PdfWriter.AllowCopy | PdfWriter.AllowPrinting, PdfWriter.STRENGTH40BITS);
		
	}


	/**
	 *  Método que permite introducir un código de barras code128 que represente el texto
	 * que se pasa como parámetro. Se debe indicar la página del PDF donde se debe introducir
	 * el código (normlamente la página 1) y la posición absoluta X e Y dentro de la página.
	 */
	public void establecerBarCode (int Pagina, String texto, int XPos, int YPos) throws Exception
	{		
			Barcode128 code128 = new Barcode128();
		
			code128.setSize(12f);
			code128.setBaseline(12f);
			code128.setCode(texto);

			Image img = code128.createImageWithBarcode(pdfs.getOverContent(Pagina), null, null);
		
			img.setAbsolutePosition(XPos, YPos);
			//Se hace un poco mas pequeño en la escala X para que no ocupe tanto
			img.scalePercent(75, 100);
		
			pdfs.getOverContent(Pagina).addImage(img);

	}

	/**
	 * Método que permite introducir un código de barras de nube de puntos que represente el texto
	 * que se pasa como parámetro. Se debe indicar la página del PDF donde se debe introducir
	 * el código (normlamente la página 1) y la posición absoluta X e Y dentro de la página.
	 */
	public void establecerBarCodeNP (int Pagina, String texto, int XPos, int YPos) throws Exception
	{
			BarcodePDF417 code417 = new BarcodePDF417();	
			code417.setText(texto);	
			Image img = code417.getImage();
			img.setAbsolutePosition(XPos, YPos);
			//Inicialmente lo dejamos a la misma escala. Falta comprobar si es necesario aumentarla o
			//disminuirla.
			img.scalePercent(100, 100);	
			pdfs.getOverContent(Pagina).addImage(img);						
	}
			
	/**
	 * @return Devuelve pdfs.
	 */
	public PdfStamper getPdfs() {
		return pdfs;
	}

	/** 
	 * Establece una imagen en una posición
	 *     
	 * @param	nPagina	Indica el número de página donde se inertará la imagen
	 * @param   a_Imagen Indica el byte[] de la imagen a insertar
	 * @param   a_posX Indica la posición X absoluta dentro del PDF donde se colocará la imagen
	 * @param   a_posY Indica la posición Y absoluta dentro del PDF donde se colocará la imagen
	 */
	public void establecerImagen (int nPagina, byte[] a_Imagen, float a_posX, float a_posY) throws Exception
	{		
		Image imagen = Image.getInstance(a_Imagen);
		imagen.setAbsolutePosition(a_posX, a_posY);
		imagen.setTransparency(new int[] {255, 255,255, 255, 255, 255});
		pdfs.getOverContent(nPagina).addImage(imagen);
	}
	
	/** 
	 * Establece una imagen en una posición indicándole un porcentaje de ampliación/reducción
	 *     
	 * @param	nPagina	Indica el número de página donde se inertará la imagen
	 * @param   a_Imagen Indica el byte[] de la imagen a insertar
	 * @param   a_posX Indica la posición X absoluta dentro del PDF donde se colocará la imagen
	 * @param   a_posY Indica la posición Y absoluta dentro del PDF donde se colocará la imagen
	 */
	public void establecerImagen (int nPagina, byte[] a_Imagen, float a_posX, float a_posY, float af_porcentaje) throws Exception
	{
		Image imagen = Image.getInstance(a_Imagen);
		imagen.scalePercent(af_porcentaje);
		imagen.setAbsolutePosition(a_posX, a_posY);
		imagen.setTransparency(new int[] {255, 255,255, 255, 255, 255});
		pdfs.getOverContent(nPagina).addImage(imagen);
	}

	/** 
	 * Establece una imagen en una posición indicándole el tamaño que ha de tener
	 *     
	 * @param	nPagina	Indica el número de página donde se inertará la imagen
	 * @param   a_Imagen Indica el byte[] de la imagen a insertar
	 * @param   a_posX Indica la posición X absoluta dentro del PDF donde se colocará la imagen
	 * @param   a_posY Indica la posición Y absoluta dentro del PDF donde se colocará la imagen
	 */
	public void establecerImagen (int nPagina, byte[] a_Imagen, float a_posX, float a_posY, float a_width, float a_height) throws Exception
	{	
		Image imagen = Image.getInstance(a_Imagen);
		imagen.scaleAbsolute(a_width, a_height);
		imagen.setAbsolutePosition(a_posX, a_posY);
		imagen.setTransparency(new int[] {255, 255,255, 255, 255, 255});
		pdfs.getOverContent(nPagina).addImage(imagen);
	}
	
	/** 
	 * Establece una imagen de fondo para una pagina
	 *     
	 * @param	nPagina	Indica el número de página donde se inertará la imagen de fondo
	 * @param   a_pathImagen Indica el fichero imagen a insertar (Path absoluto)
	 * @param   a_posX Indica la posición X absoluta dentro del PDF donde se colocará la imagen
	 * @param   a_pathImagen Indica la posición Y absoluta dentro del PDF donde se colocará la imagen
	 */
	public void establecerFondo (int nPagina, String a_pathImagen, float a_posX, float a_posY) throws Exception
	{
		Image imagen = Image.getInstance(a_pathImagen);
		imagen.setAbsolutePosition(a_posX, a_posY);
		imagen.setTransparency(new int[] {255, 255,255, 255, 255, 255});
		pdfs.getUnderContent(nPagina).addImage(imagen);
	}
	
	/** 
	 * Establece una imagen de fondo para todas las paginas
	 *     
	 * @param	nPagina	Indica el número de página donde se inertará la imagen de fondo
	 * @param   a_pathImagen Indica el fichero imagen a insertar (Path absoluto)
	 * @param   a_posX Indica la posición X absoluta dentro del PDF donde se colocará la imagen
	 * @param   a_pathImagen Indica la posición Y absoluta dentro del PDF donde se colocará la imagen
	 */
	public void establecerFondo (String a_pathImagen, float a_posX, float a_posY) throws Exception
	{
		int n = pdfr.getNumberOfPages();
		Image imagen = Image.getInstance(a_pathImagen);
		imagen.setAbsolutePosition(a_posX, a_posY);
		imagen.setTransparency(new int[] {255, 255,255, 255, 255, 255});
		
		for (int i=1;i<=n;i++){
			pdfs.getUnderContent(i).addImage(imagen);
		}
	}
	
}