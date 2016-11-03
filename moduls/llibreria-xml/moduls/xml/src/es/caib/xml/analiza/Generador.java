package es.caib.xml.analiza;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.org.apache.xpath.internal.XPathAPI;

import es.caib.xml.ConstantesXML;
import es.caib.xml.util.HashMapIterable;

/** Clase que permite generar el contenido xml a partir de una hashtable
 * (iterable o con orden) cuyas claves son las rutas xpath de los nodos
 * y sus valores objetos de tipo Nodo con los datos de los nodos xml
 * @author mangel
 *
 */
public class Generador {	
	private final static boolean DEFAULT_IDENTACION = true;
	private final static int DEFAULT_CARACTERES_IDENTACION = 4;
	
	private DocumentBuilderFactory dbFactory = null;
    private DocumentBuilder docBuilder = null;
    private String encoding = ConstantesXML.ENCODING;
    private boolean identacion = DEFAULT_IDENTACION;
    private int caracteresIdentacion = DEFAULT_CARACTERES_IDENTACION;
	
	public Generador() throws ParserConfigurationException{
		// Inicializamos factor√≠a (xerces) de creaci√≥n de documentos XML (DOM)
		dbFactory = DocumentBuilderFactoryImpl.newInstance();
	    docBuilder = dbFactory.newDocumentBuilder();
	}

	public int getCaracteresIdentacion() {
		return caracteresIdentacion;
	}

	public void setCaracteresIdentacion(int caracteresIdentacion) {
		this.caracteresIdentacion = caracteresIdentacion;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isIdentacion() {
		return identacion;
	}

	public void setIdentacion(boolean identacion) {
		this.identacion = identacion;
	}
	
	public String generarXML (HashMapIterable nodosHojas) throws Exception{
		StringWriter strWriter = new StringWriter ();
		generarXML (nodosHojas, strWriter);
		
		return strWriter.toString();				 
	}
	
	public void generarXML (HashMapIterable nodosHojas, File fichero) throws Exception{
		generarXML (nodosHojas, new FileWriter (fichero));
	}
	
	private void generarXML (HashMapIterable nodosHojas, Writer writer) throws Exception{
		Document doc = docBuilder.newDocument();
		
		// Recrear el documento a partir de la info de los nodos hoja 
		// (junto con su ruta xpath)
		Iterator itHojas = nodosHojas.iterator();
		while (itHojas.hasNext()){
			
			Object o = itHojas.next();
			
			if (o instanceof List){
				List lista = (List) o;
				for (Iterator itLista = lista.iterator();itLista.hasNext();)
					crearNodos (doc, (Nodo) itLista.next());
			}else if (o instanceof Nodo){
				crearNodos (doc, (Nodo) o);
			}else{
				throw new Exception("Tipo no esperado: " + o.getClass().getName());
			}			
		}
		
		// Escribir el documento creado
		escribirDocumento (doc, writer);		
	}
	
	private void crearNodos (Document doc, Nodo nodo) throws Exception{
		
		// Construimos rama hasta el padre /ROOT/../PADRE/HOJA
		String xpath = nodo.getXpath();
		StringTokenizer st = new StringTokenizer (xpath.substring(0,xpath.lastIndexOf("/")), "/");
		String xpathNext="";
		String nombreNodo;
		Element elementoPadre = null;
		
		while (st.hasMoreTokens()){
			
			// Nombre nodo y xpath del nodo actual en la rama
			nombreNodo = st.nextToken();
			xpathNext = xpathNext + "/" + nombreNodo;
		
			// Buscamos si esta creado nodo
			Node nodoNext = XPathAPI.selectSingleNode(doc,xpathNext);
			
			// Si esta creado pasamos a siguiente nivel
			if (nodoNext != null){
				elementoPadre = (Element) nodoNext;	
				continue;
			}else{			
			// Si no esta creado lo creamos
				if (elementoPadre == null){
					// Nodo raiz
					elementoPadre = doc.createElement (nombreNodo);	
					doc.appendChild(elementoPadre);
				}else{
					// Ramas
					Element nodoAux = doc.createElement (nombreNodo);
					elementoPadre.appendChild (nodoAux);
					elementoPadre = nodoAux;
				}				
			}
		}
			
		// Creamos hoja
		nombreNodo = xpath.substring(xpath.lastIndexOf("/")+1);
		Element nodoAux = doc.createElement(nombreNodo);
		elementoPadre.appendChild (nodoAux);
		elementoPadre = nodoAux;
		
		// AÒadimos atributos
		if (!nodo.getAtributos().isEmpty()){
			Iterator itAttb = nodo.getAtributos().iterator();
			while (itAttb.hasNext()){
				Par attb = (Par) itAttb.next();				
				elementoPadre.setAttribute(attb.getNombre(), attb.getValor());
			}
		}			
		
		// AÒadimos valor
		Node nodoTexto = doc.createTextNode (nodo.getValor());			
		elementoPadre.appendChild (nodoTexto);		
	}
	
	
	private void escribirDocumento (Document doc, Writer writer) throws IOException {
		XMLSerializer xmlSerializer = new XMLSerializer();	    
		OutputFormat outFormat = new OutputFormat();

	    outFormat.setEncoding (getEncoding ());
	    outFormat.setVersion("1.0");
	    outFormat.setIndenting (isIdentacion ());
	    outFormat.setIndent (getCaracteresIdentacion ());

	    xmlSerializer.setOutputCharStream (writer);
	    xmlSerializer.setOutputFormat (outFormat);
	    xmlSerializer.serialize (doc);
	    writer.close();
	}

}
