/*
 * Copyright 2000-2002,2004,2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.caib.xml.analiza;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import es.caib.xml.ConstantesXML;
import es.caib.xml.util.HashMapIterable;

/**
 * Provides a complete trace of SAX2 events for files parsed. This is
 * useful for making sure that a SAX parser implementation faithfully
 * communicates all information in the document to the SAX handlers.
 *
 * @author Andy Clark, IBM
 * @author Arnaud Le Hors, IBM
 *
 * @version $Id: Analizador.java,v 1.2 2009/07/29 08:12:15 rsanz Exp $
 */
public class Analizador
    extends DefaultHandler
    implements ContentHandler, DTDHandler, ErrorHandler, // SAX2
               DeclHandler, LexicalHandler // SAX2 extensions               
    {

	String  sb =  null;
	
    //
    // Constants
    //

    // feature ids

    /** Namespaces feature id (http://xml.org/sax/features/namespaces). */
    protected static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";
    
    /** Namespace prefixes feature id (http://xml.org/sax/features/namespace-prefixes). */
    protected static final String NAMESPACE_PREFIXES_FEATURE_ID = "http://xml.org/sax/features/namespace-prefixes";

    /** Validation feature id (http://xml.org/sax/features/validation). */
    protected static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";

    /** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
    protected static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";

    /** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
    protected static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";        
    
    /** Dynamic validation feature id (http://apache.org/xml/features/validation/dynamic). */
    protected static final String DYNAMIC_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/dynamic";
    
    /** Load external DTD feature id (http://apache.org/xml/features/nonvalidating/load-external-dtd). */
    protected static final String LOAD_EXTERNAL_DTD_FEATURE_ID = "http://apache.org/xml/features/nonvalidating/load-external-dtd";    

    /** Lexical handler property id (http://xml.org/sax/properties/lexical-handler). */
    protected static final String LEXICAL_HANDLER_PROPERTY_ID = "http://xml.org/sax/properties/lexical-handler";

    // default settings

    /** Default parser name. */
    protected static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";

    /** Default namespaces support (true). */
    protected static final boolean DEFAULT_NAMESPACES = true;
    
    /** Default namespace prefixes (false). */
    protected static final boolean DEFAULT_NAMESPACE_PREFIXES = false;

    /** Default validation support (false). */
    protected static final boolean DEFAULT_VALIDATION = false;
    
    /** Default load external DTD (true). */
    protected static final boolean DEFAULT_LOAD_EXTERNAL_DTD = true;

    /** Default Schema validation support (false). */
    protected static final boolean DEFAULT_SCHEMA_VALIDATION = false;

    /** Default Schema full checking support (false). */
    protected static final boolean DEFAULT_SCHEMA_FULL_CHECKING = false;
        
    
    /** Default dynamic validation support (false). */
    protected static final boolean DEFAULT_DYNAMIC_VALIDATION = false;
        
    
    private XMLReader parser = null;
    
    // Variables de estado
    private int nivel = 0;
    private Nodo ultimoNodoEmpezado = null;
    private HashMapIterable hojas = null;
    private Hashtable xpathNivel = null;

    
    //
    // Constructors
    //
    public Analizador() throws SAXNotRecognizedException, SAXNotSupportedException{
        boolean namespaces = DEFAULT_NAMESPACES;
        boolean namespacePrefixes = DEFAULT_NAMESPACE_PREFIXES;
        boolean validation = DEFAULT_VALIDATION;
        boolean externalDTD = DEFAULT_LOAD_EXTERNAL_DTD;
        boolean schemaValidation = DEFAULT_SCHEMA_VALIDATION;
        boolean schemaFullChecking = DEFAULT_SCHEMA_FULL_CHECKING;        
        boolean dynamicValidation = DEFAULT_DYNAMIC_VALIDATION;        
        
        // create parser
        try {
            parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
        }
        catch (Exception e) {
            System.err.println("error: Unable to instantiate parser ("+DEFAULT_PARSER_NAME+")");           
        }
        
        // set parser features
        try {
            parser.setFeature(NAMESPACES_FEATURE_ID, namespaces);
        }
        catch (SAXException e) {
            System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
        }
        try {
            parser.setFeature(NAMESPACE_PREFIXES_FEATURE_ID, namespacePrefixes);
        }
        catch (SAXException e) {
            System.err.println("warning: Parser does not support feature ("+NAMESPACE_PREFIXES_FEATURE_ID+")");
        }
        try {
            parser.setFeature(VALIDATION_FEATURE_ID, validation);
        }
        catch (SAXException e) {
            System.err.println("warning: Parser does not support feature ("+VALIDATION_FEATURE_ID+")");
        }
        try {
            parser.setFeature(LOAD_EXTERNAL_DTD_FEATURE_ID, externalDTD);
        }
        catch (SAXNotRecognizedException e) {
            System.err.println("warning: Parser does not recognize feature ("+LOAD_EXTERNAL_DTD_FEATURE_ID+")");
        }
        catch (SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature ("+LOAD_EXTERNAL_DTD_FEATURE_ID+")");
        }
        try {
            parser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, schemaValidation);
        }
        catch (SAXNotRecognizedException e) {
            System.err.println("warning: Parser does not recognize feature ("+SCHEMA_VALIDATION_FEATURE_ID+")");
        }
        catch (SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature ("+SCHEMA_VALIDATION_FEATURE_ID+")");
        }
        try {
            parser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, schemaFullChecking);
        }
        catch (SAXNotRecognizedException e) {
            System.err.println("warning: Parser does not recognize feature ("+SCHEMA_FULL_CHECKING_FEATURE_ID+")");
        }
        catch (SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature ("+SCHEMA_FULL_CHECKING_FEATURE_ID+")");
        }        
        try {
            parser.setFeature(DYNAMIC_VALIDATION_FEATURE_ID, dynamicValidation);
        }
        catch (SAXNotRecognizedException e) {
            System.err.println("warning: Parser does not recognize feature ("+DYNAMIC_VALIDATION_FEATURE_ID+")");
        }
        catch (SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature ("+DYNAMIC_VALIDATION_FEATURE_ID+")");
        }
                
        parser.setContentHandler(this);        
        parser.setProperty("http://xml.org/sax/properties/declaration-handler", this);        
        parser.setProperty("http://xml.org/sax/properties/lexical-handler", this);
    }

    //
    // ContentHandler and DocumentHandler methods
    //

    /** Set document locator. */
    public void setDocumentLocator(Locator locator) {}

    /** Start document. */
    public void startDocument() throws SAXException {} 

    /** Processing instruction. */
    public void processingInstruction(String target, String data)
        throws SAXException {}

    /** Characters. */
    public void characters(char[] ch, int offset, int length)
        throws SAXException {
    	
    	String str = new String (ch, offset, length);
    	
    	/*
    	if (!str.trim().equals ("")){
    		if (ultimoNodoEmpezado.getValor().equals(""))
    			ultimoNodoEmpezado.setValor (str);
    		else
    			ultimoNodoEmpezado.appendValor (str);
    	}
    	    */
    	
    	if (sb != null) sb += str; 
                        
    } 

    /** Ignorable whitespace. */
    public void ignorableWhitespace(char[] ch, int offset, int length)
        throws SAXException {}

    /** End document. */
    public void endDocument() throws SAXException {}

    //
    // ContentHandler methods
    //

    /** Start prefix mapping. */
    public void startPrefixMapping(String prefix, String uri)
        throws SAXException {}

    /** Start element. */
    public void startElement(String uri, String localName, String qname,
                             Attributes attributes) throws SAXException {
		try{	
			ultimoNodoEmpezado = formarNodo (qname, attributes);    	    	
			
			sb = "";
			
			nivel++;
			
			// Calcular el xpath del elemento actual
			String xpathBase = "/";
			if (xpathNivel.containsKey (new Integer (nivel -1))){
				xpathBase = xpathNivel.get (new Integer (nivel -1)).toString() + "/";
			}    	    	
			xpathNivel.put (new Integer (nivel), xpathBase + qname);
		}catch (Exception e){
			throw new SAXException(e);
		}                    
    }

    /** End element. */
    public void endElement(String uri, String localName, String qname)
        throws SAXException {
    	    	    	
    	try{
	    	if (qname.equalsIgnoreCase(ultimoNodoEmpezado.getNombre())){
	    		String xpath = xpathNivel.get (new Integer (nivel)).toString();
	    		ultimoNodoEmpezado.setXpath (xpath);	
	    		
	    		ultimoNodoEmpezado.setValor(sb);
	    		sb=null;
	    		
	    		// Controlamos elementos con mismo XPATH 
	    		if (hojas.containsKey(xpath)){
	    			Object obj = hojas.get(xpath);
	    			if (obj instanceof List){
	    				((List) obj).add(ultimoNodoEmpezado);
	    			}else if (obj instanceof Nodo){
	    				List lista = new ArrayList();
	    				lista.add(obj);
	    				lista.add(ultimoNodoEmpezado);
	    				hojas.put (xpath, lista);
	    			}else{
	    				throw new Exception("Tipo de dato inesperado: " + obj.getClass().getName());
	    			}
	    		}else{
	    			hojas.put (xpath, ultimoNodoEmpezado);
	    		}
	    		
	    	}
	    	
	    	nivel--;
    	}catch (Exception e){
    		throw new SAXException(e);
    	}
    }

    /** End prefix mapping. */
    public void endPrefixMapping(String prefix) throws SAXException {}

    /** Skipped entity. */
    public void skippedEntity(String name) throws SAXException {}

    //
    // DocumentHandler methods
    //

    /** Start element. */
    public void startElement(String name, AttributeList attributes)
        throws SAXException {    	
    }

    /** End element. */
    public void endElement(String name) throws SAXException {    
    }

    //
    // DTDHandler methods
    //

    /** Notation declaration. */
    public void notationDecl(String name, String publicId, String systemId)
        throws SAXException {}

    /** Unparsed entity declaration. */
    public void unparsedEntityDecl(String name,
                                   String publicId, String systemId,
                                   String notationName) throws SAXException {}

    //
    // LexicalHandler methods
    //

    /** Start DTD. */
    public void startDTD(String name, String publicId, String systemId)
        throws SAXException {}

    /** Start entity. */
    public void startEntity(String name) throws SAXException {}

    /** Start CDATA section. */
    public void startCDATA() throws SAXException {}

    /** End CDATA section. */
    public void endCDATA() throws SAXException {}

    /** Comment. */
    public void comment(char[] ch, int offset, int length)
        throws SAXException {}

    /** End entity. */
    public void endEntity(String name) throws SAXException {}

    /** End DTD. */
    public void endDTD() throws SAXException {}

    //
    // DeclHandler methods
    //

    /** Element declaration. */
    public void elementDecl(String name, String contentModel)
        throws SAXException {}

    /** Attribute declaration. */
    public void attributeDecl(String elementName, String attributeName,
                              String type, String valueDefault,
                              String value) throws SAXException {}

    /** Internal entity declaration. */
    public void internalEntityDecl(String name, String text)
        throws SAXException {} 

    /** External entity declaration. */
    public void externalEntityDecl(String name,
                                   String publicId, String systemId)
        throws SAXException {}


    //
    // Protected methods
    //
    
    /** Forma un objeto de tipo Nodo */
    private Nodo formarNodo (String nombre, Attributes saxAttb) throws Exception{
    	List atributos = new ArrayList ((saxAttb.getLength() > 0) ? saxAttb.getLength() : 0);
    	
    	if (saxAttb != null){
    		for (int i = 0; i < saxAttb.getLength(); i++){
    			atributos.add( new Par (saxAttb.getQName(i), saxAttb.getValue(i)));
    		}
    	}
    	
    	return new Nodo (nombre, null, atributos);
    }
    
    public HashMapIterable analizar (InputStream contenidoXML,String encoding) throws SAXException, IOException {
    	ultimoNodoEmpezado = null;
    	hojas = new HashMapIterable ();
    	xpathNivel = new Hashtable ();
    	nivel = 0;
        
    	InputSource is = new InputSource (contenidoXML);
    	if (encoding != null) is.setEncoding(encoding);
        parser.parse(is);
                       
        return hojas;
                        
    }
    
    public HashMapIterable analizar (File ficheroXML) throws FileNotFoundException, SAXException, IOException {
    	return analizar (new FileInputStream (ficheroXML),null);
    }
    
    public HashMapIterable analizar (String contenidoXML) throws SAXException, IOException{
    	String encoding = ConstantesXML.ENCODING;
    	return analizar (new ByteArrayInputStream (contenidoXML.getBytes(encoding)),encoding);
    }

}

