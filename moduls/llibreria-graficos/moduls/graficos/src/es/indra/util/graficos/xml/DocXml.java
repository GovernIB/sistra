package es.indra.util.graficos.xml;

// JAXP packages


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Clob;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import es.indra.util.graficos.util.CapturaError;
import es.indra.util.graficos.util.Conversion;
import es.indra.util.graficos.util.FuncionesCadena;



public class DocXml
{	
	private  Document xml = null;
	private boolean validarXML = false;
	private String	 error = "";
	
	public DocXml(){

	}

	/** Método para parsear un InputStream en un Arbol DOM 
	 * @param is InputStream a parsear
	 * @return int 0 --> sin errores, otro caso  **/
	public int parse(InputStream is)
	{
		try
		{
	        // Configurar el arbol DOM a crear
	        DocumentBuilderFactory dbf =
	            DocumentBuilderFactory.newInstance();
	
	        dbf.setNamespaceAware(true);
	
	        // Configurar varias opciones de configuración
	        dbf.setValidating(validarXML);
	        dbf.setIgnoringComments(false);
	        dbf.setIgnoringElementContentWhitespace(true);
	        dbf.setCoalescing(true);
	        dbf.setExpandEntityReferences(false);
	
	        // Crear un DocumentBuilder que satisfaga la condiciones de
	        // el DocumentBuilderFactory
	        DocumentBuilder db = null;
	        db = dbf.newDocumentBuilder();
	
	         // Paso 3: Parsear el fichero de entrada
	         xml = db.parse(is);
	         return 0;
        } 
        catch (Throwable ex) 
        {
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar convertir a documento XML una entrada de tipo InputStream. "+
								ex.toString());	
            return -1;
        }

	}


	public int parse(String as_xml){
		try{
			DocumentBuilderFactory l_dbf = DocumentBuilderFactory.newInstance();

        	// Configurar varias opciones de configuración
    	    l_dbf.setValidating(validarXML);
	        l_dbf.setIgnoringComments(false);
        	l_dbf.setIgnoringElementContentWhitespace(true);
    	    l_dbf.setCoalescing(true);
	        l_dbf.setExpandEntityReferences(false);	        	        
	        			
			//if (true) {error = as_xml;return -1;}
			ByteArrayInputStream l_bis = new ByteArrayInputStream(as_xml.getBytes(FuncionesCadena.getCharset()));
			
			DocumentBuilder l_db = l_dbf.newDocumentBuilder();
			Document l_xml = l_db.parse(l_bis);
			xml = l_xml;
			return 0;
		} catch (Throwable ex){
			xml = null;			
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar convertir a documento XML una entrada de tipo String. "+
								ex.toString() + "\n XML a parsear: " + as_xml);	
			return -1;
		}	
	}
	
	public int valida(String as_espDTD, String as_root, boolean ab_reemplazar)
	{
		int li_resultado = 0;
		try
		{
	        // Paso 1: Crear un DocumentBuilderFactory y configurarlo
	        DocumentBuilderFactory l_dbf =
	            DocumentBuilderFactory.newInstance();
	
        	// Configurar varias opciones de configuración
    	    l_dbf.setValidating(true);
	        l_dbf.setIgnoringComments(false);
        	l_dbf.setIgnoringElementContentWhitespace(true);
    	    l_dbf.setCoalescing(true);
	        l_dbf.setExpandEntityReferences(false);			

	
	        // Paso 2: Crear un DocumentBuilder que satisfaga la condiciones de
	        //         el DocumentBuilderFactory
	        DocumentBuilder l_db = null;
            l_db = l_dbf.newDocumentBuilder();

	        String ls_codificacion ="<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"no\"?>\n";
			String ls_doctype = "<!DOCTYPE "+ as_root +" SYSTEM "+" \""+as_espDTD+"\" >\n";

			String ls_elemento = elementToString();

	         // Paso 3: Crear Documento vacío de datos
	        Document l_xml = l_db.parse(new InputSource(new StringReader(ls_codificacion+ls_doctype+ls_elemento)));

	        if (ab_reemplazar)
	        	xml = l_xml;
	        
	        return li_resultado;
		}

		catch (Throwable ex)
		{
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar validar un documento XML contra su DTD. "+
								ex.toString());	
			li_resultado = -1;
			return li_resultado;
		}
	}
	
	public int parse(Blob a_xml){
		try{
			DocumentBuilderFactory l_dbf = DocumentBuilderFactory.newInstance();
	        l_dbf.setNamespaceAware(true);
	
	        // Configurar varias opciones de configuración
	        l_dbf.setValidating(validarXML);
	        l_dbf.setIgnoringComments(true);
	        l_dbf.setIgnoringElementContentWhitespace(true);
	        l_dbf.setCoalescing(true);
	        l_dbf.setExpandEntityReferences(false);
	        			DocumentBuilder l_db = l_dbf.newDocumentBuilder();
						
			long lon = a_xml.length();
			byte [] b;
			b = a_xml.getBytes(1,(int)lon + 1);
			Conversion l_conv = new Conversion();
			String ls_xml = l_conv.BytestoString(b);
			Document l_xml = l_db.parse(new InputSource(new StringReader(ls_xml)));						
			xml = l_xml;
			return 0;
		} catch (Throwable ex){
			xml = null;
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar convertir a documento XML una entrada de tipo BLOB. "+
								ex.toString());	
			return -1;
		}	
	}
	
	public int parse(Clob a_xml){
		try{
			DocumentBuilderFactory l_dbf = DocumentBuilderFactory.newInstance();
	        l_dbf.setNamespaceAware(true);
	
	        // Configurar varias opciones de configuración
	        l_dbf.setValidating(validarXML);
	        l_dbf.setIgnoringComments(true);
	        l_dbf.setIgnoringElementContentWhitespace(true);
	        l_dbf.setCoalescing(true);
	        l_dbf.setExpandEntityReferences(false);
	        			DocumentBuilder l_db = l_dbf.newDocumentBuilder();
						
			long lon = a_xml.length();
			
			String ls_xml = a_xml.getSubString(1,(int)lon + 1);

			Document l_xml = l_db.parse(new InputSource(new StringReader(ls_xml)));						
			xml = l_xml;
			return 0;
		} catch (Throwable ex){
			xml = null;
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar convertir a documento XML una entrada de tipo CLOB. "+
								ex.toString());	
			return -1;
		}	
	}

  /** Método para parsear un InputStream en un Arbol DOM 
   * @param is InputStream a parsearf
   * @return int 0 --> sin errores, otro caso  **/
  public int parse(File a_archivo)
  {
	  	try
	  	{
	        // Configurar el arbol DOM a crear
	        DocumentBuilderFactory dbf =
	            DocumentBuilderFactory.newInstance();
	
	        dbf.setNamespaceAware(true);
	
	        // Configurar varias opciones de configuración
	        dbf.setValidating(validarXML);
	        dbf.setIgnoringComments(true);
	        dbf.setIgnoringElementContentWhitespace(true);
	        dbf.setCoalescing(true);
	        dbf.setExpandEntityReferences(false);
	
	        // Crear un DocumentBuilder que satisfaga la condiciones de
	        // el DocumentBuilderFactory
	        DocumentBuilder db = null;
	        db = dbf.newDocumentBuilder();
	
	        // Paso 3: Parsear el fichero de entrada
         	xml = db.parse(a_archivo);
			return 0;
        } 
        catch (Throwable ex) 
        {
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar convertir a documento XML una entrada de tipo Archivo. "+
								ex.toString());	
            return -1;
        }

  }	
	

  public String toString()
  {
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        String result = null;
    try
    {
		Object[] l_pars = new Object[1];
		l_pars[0] = xml;
		XmlWriter l_writer = new XmlWriter();
		if (l_writer.render(l_pars, oStream) == -1)
		{
			setLastError((new CapturaError()).getTraza()[0] + 
						" Error convirtiendo documento xml a cadena de texto. " +
						"\n" + l_writer.getLastError());
			result = null;   		
		}
		else
		{
			oStream.flush();
			result = oStream.toString(FuncionesCadena.getCharset());
			oStream.close();
		}
    }
    catch (Throwable ex)
    {
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar convertir un documento XML a una cadena de texto. "+
								ex.toString());	
            result = null;            
    }
   
    return(result);
  }


  public String elementToString()
  {
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        String result = null;
    try
    {
		Object[] l_pars = new Object[2];
		l_pars[0] = "-body";
		l_pars[1] = xml;
		XmlWriter l_writer = new XmlWriter();
		if (l_writer.render(l_pars, oStream) == -1)
		{
			setLastError((new CapturaError()).getTraza()[0] + 
						" Error convirtiendo documento xml a cadena de texto. " +
						"\n" + l_writer.getLastError());
			result = null;   		
		}
		else
		{
			oStream.flush();
			result = oStream.toString(FuncionesCadena.getCharset());
			oStream.close();
		}

    }
    catch (Throwable ex)
    {
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
			 					" Error al intentar convertir un elemento de un documento XML a una cadena de texto. "+
								ex.toString());	
            result = null;            
    }

    return(result);
  }

  public String toString(Node a_nodo)
  {
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		String result = null;
	try
	{
		Object[] l_pars = new Object[1];
		l_pars[0] = a_nodo;
		XmlWriter l_writer = new XmlWriter();
		if (l_writer.render(l_pars, oStream) == -1)
		{
			setLastError((new CapturaError()).getTraza()[0] + 
						" Error convirtiendo documento xml a cadena de texto. " +
						"\n" + l_writer.getLastError());
			result = null;   		
		}
		else
		{
			oStream.flush();
			result = oStream.toString(FuncionesCadena.getCharset());
			oStream.close();
		}

	}
	catch (Throwable ex)
	{
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex)+
								" Error al intentar convertir un elemento de un documento XML a una cadena de texto. "+
								ex.toString());	
			result = null;            
	}
	
	return(result);
  }		
	public String getLastError(){
		return error;
	}


	/**
	 * Sets the error.
	 * @param error The error to set
	 */
	public void setLastError(String as_error) {
		this.error = as_error+ "\n";
	}

	/**
	 * Returns the validarXML.
	 * @return boolean
	 */
	public boolean isValidarXML() {
		return validarXML;
	}

	/**
	 * Sets the validarXML.
	 * @param validarXML The validarXML to set
	 */
	public void setValidarXML(boolean validarXML) {
		this.validarXML = validarXML;
	}




	/**
	 * Returns the xml.
	 * @return Document
	 */
	public Document getXml() {
		return xml;
	}

	/**
	 * Sets the xml.
	 * @param xml The xml to set
	 */
	public int setXml(Document xml) {
		this.xml = xml;
		return 0;
	}
}