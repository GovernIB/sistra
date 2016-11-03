package es.indra.util.graficos.xml;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import es.indra.util.graficos.util.FuncionesCadena;



/** Clase que vuelca a fichero un documento DOM **/
public class XmlWriter 
{
	/** Último error */
	private String lastError = null;

    /** Output goes here */
    private PrintWriter out;

    /** Indent level */
    private int indent = 0;

    /** Indentation will be in multiples of basicIndent  */
    private String basicIndent = "  ";
    
    /** Flag que indica si ha de imprimirse solo el cuerpo o también la cabecera **/
    private boolean printBodyOnly = false;

	public XmlWriter()
	{
	}

    /**
     * Echo common attributes of a DOM2 Node and terminate output with an
     * EOL character.
     */
    private void printlnCommon(Node n) 
    {  
       out.print((" nodeName=\"" + n.getNodeName() + "\""));

        String val = n.getNamespaceURI();
        if (val != null) {
            out.print((" uri=\"" + val + "\""));
        }

        val = n.getPrefix();
        if (val != null) {
            out.print((" pre=\"" + val + "\""));
        }

        val = n.getLocalName();
        if (val != null) {
            out.print((" local=\"" + val + "\""));
        }

        val = n.getNodeValue();
        if (val != null) {
            out.print((" nodeValue="));
            if (val.trim().equals("")) {
                // Whitespace
                out.print("[WS]");
            } else {
                out.print(("\"" + n.getNodeValue() + "\""));
            }
        }
        out.println();
    }

    /**
     * Tabulador
     */
    private void outputIndentation() 
    {

        for (int i = 0; i < indent; i++) {
            out.print(basicIndent);
        }

    }

    /** Escribe el nodo recursivamente. */
    private void write(Node node)
    throws Throwable 
    {
        // is there anything to do?
        if (node == null) {
            return;
        }
        int type = node.getNodeType();
        // Indent to the current level before printing anything
        if (type != Node.TEXT_NODE) 
          outputIndentation();


        switch (type) {
            case Node.DOCUMENT_NODE: {
                Document document = (Document)node;
				Node l_child = document.getFirstChild();
				while (l_child != null)
				{
					write(l_child);
					l_child = UtilXml.getNextSibling(l_child);
				}
                
                break;
            }

            case Node.DOCUMENT_TYPE_NODE: {
				if (!this.printBodyOnly)
				{
	                DocumentType doctype = (DocumentType)node;
	                out.print("<!DOCTYPE ");
	                out.print(doctype.getName());
	                String publicId = doctype.getPublicId();
	                String systemId = doctype.getSystemId();
	                if (publicId != null) {
	                    out.print(" PUBLIC '");
	                    out.print(publicId);
	                    out.print("' '");
	                    out.print(systemId);
	                    out.print('\'');
	                }
	                else {
	                    out.print(" SYSTEM '");
	                    out.print(systemId);
	                    out.print('\'');
	                }

	                out.println(">");
	            }
                break;
            }

            case Node.ELEMENT_NODE: {
                   
                out.print('<');
                out.print(node.getNodeName());
                Attr attrs[] = sortAttributes(node.getAttributes());
                for (int i = 0; i < attrs.length; i++) {
                    Attr attr = attrs[i];
                    out.print(' ');
                    out.print(attr.getNodeName());
                    out.print("=\"");
                    out.print(FuncionesCadena.normaliza(attr.getNodeValue()));
                    out.print('"');
                }
                out.print('>');
                

                Node child = UtilXml.getFirstChild(node);
                int cont = 0;
                
                while (child != null) {
                    if (child.getNodeType() == Node.ELEMENT_NODE)
                    {
                      if (cont==0) out.println();
                      cont++;
                    }
                    indent += 2;         
                    write(child);
                    indent -= 2;
                    child = UtilXml.getNextSibling(child);
                  }
                
                if (cont > 0)
                {
                   outputIndentation();
                }
                   
                  
                out.print("</");
                out.print(node.getNodeName());
                out.print(">\n");
                
                
                break;
            }

            case Node.ENTITY_REFERENCE_NODE: {
                    out.print('&');
                    out.print(node.getNodeName());
                    out.print(';');


                break;
            }

            case Node.CDATA_SECTION_NODE: {
                    out.print("<![CDATA[");
                    out.print(node.getNodeValue());
                    out.print("]]>");
                break;
            }

            case Node.TEXT_NODE: {
				  out.print(FuncionesCadena.normaliza(node.getNodeValue()));
                
                break;
            }

            case Node.PROCESSING_INSTRUCTION_NODE: {
            	
				if (!printBodyOnly)
				{
	                out.print("<?");
	                out.print(node.getNodeName());
	                String data = node.getNodeValue();
	                if (data != null && data.length() > 0) {
	                    out.print(' ');
	                    out.print(data);
	                }
	                out.println("?>");
				}                
                break;
            }
        }
    }


    /** Devuelve una lista ordenada de atributos. */
    private Attr[] sortAttributes(NamedNodeMap attrs) {

        int len = (attrs != null) ? attrs.getLength() : 0;
        Attr array[] = new Attr[len];
        for (int i = 0; i < len; i++) {
            array[i] = (Attr)attrs.item(i);
        }
        for (int i = 0; i < len - 1; i++) {
            String name = array[i].getNodeName();
            int index = i;
            for (int j = i + 1; j < len; j++) {
                String curName = array[j].getNodeName();
                if (curName.compareTo(name) < 0) {
                    name = curName;
                    index = j;
                }
            }
            if (index != i) {
                Attr temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }
        }

        return array;

    } // sortAttributes(NamedNodeMap):Attr[]

    public int render(Object[] args, OutputStream salida) throws Exception 
    {
        String filename = null;
        boolean validation = false;

        boolean ignoreWhitespace = false;
        boolean ignoreComments = false;
        boolean putCDATAIntoText = false;
        boolean createEntityRefs = false;
        Document parDoc = null;
        Node parNode = null;
        String destino = null;
        String indentation = "  ";

        for (int i = 0; i < args.length; i++) {
            if ((args[i]).toString().equals("-v")) {
                validation = true;
            } else if (args[i].toString().equals("-ws")) {
                ignoreWhitespace = true;
                indentation = "";
            } else if (args[i].toString().startsWith("-co")) {
                ignoreComments = true;
            } else if (args[i].toString().startsWith("-cd")) {
                putCDATAIntoText = true;
            } else if (args[i].toString().startsWith("-e")) {
                createEntityRefs = true;
            } else if (args[i].toString().startsWith("-body")) {
                printBodyOnly = true;
            } else if (args[i].toString().startsWith("-t")) {

                destino = (String) args[i+1];
                i++;
            } else {
                String clase = args[i].getClass().getName();
 
                if (clase.equals("java.lang.String"))
                  filename = (String) args[i];
                else 
                if (clase.endsWith("Document") || clase.endsWith("DocumentImpl"))
                  parDoc = (Document) args[i];
                else
                if (clase.endsWith("Node") || clase.endsWith("NodeImpl") || 
                	clase.endsWith("Element") || clase.endsWith("ElementImpl"))
                  parNode = (Node) args[i];
            }
        }
        // Step 3: parse the input file
        Document doc = null;
        try {
            if (filename != null)
            {
              // Step 1: create a DocumentBuilderFactory and configure it
              DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();

              // Set namespaceAware to true to get a DOM Level 2 tree with nodes
              // containing namesapce information.  This is necessary because the
              // default value from JAXP 1.0 was defined to be false.
              dbf.setNamespaceAware(true);

              // Optional: set various configuration options
              dbf.setValidating(validation);
              dbf.setIgnoringComments(ignoreComments);
              dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
              dbf.setCoalescing(putCDATAIntoText);
              // The opposite of creating entity ref nodes is expanding them inline
              dbf.setExpandEntityReferences(!createEntityRefs);

              // At this point the DocumentBuilderFactory instance can be saved
              // and reused to create any number of DocumentBuilder instances
              // with the same configuration options.

              // Step 2: create a DocumentBuilder that satisfies the constraints
              // specified by the DocumentBuilderFactory
              DocumentBuilder db = null;
              try {
                db = dbf.newDocumentBuilder();
              } catch (ParserConfigurationException pce) {
                setLastError(FuncionesCadena.getElementoTraza(getClass(), pce) + 
							" Error volcando documento: "+pce.toString());
                return -1;
              }

              doc = db.parse(new File(filename));
            }
            else if (parDoc != null)
            {

			  doc = parDoc;
            }
            
              // Print out the DOM tree
		      OutputStreamWriter outWriter =
        	    new OutputStreamWriter(salida, FuncionesCadena.getCharset());
 	          out = new PrintWriter(outWriter,true);
			  basicIndent = indentation;
 	          if (parNode != null)
				  write(parNode);
			  else 
			  	  write(doc);
		      outWriter.flush();
		      outWriter.close();
			  return 0;

        } catch (SAXException ex) {
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex) + 
						" Error volcando documento: "+ex.toString());
			return -1;
        } catch (IOException ex) {
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex) + 
						" Error volcando documento: "+ex.toString());
			return -1;
        } catch (Throwable ex) {
			setLastError(FuncionesCadena.getElementoTraza(getClass(), ex) + 
						" Error volcando documento: "+ex.toString());
			return -1;
		}

    }

	
	/**
	 * @return
	 */
	public String getLastError()
	{
		return lastError;
	}

	/**
	 * @param a_string
	 */
	private void setLastError(String a_string)
	{
		lastError = a_string;
	}

}