package caib_prueba_xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Generador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.analiza.formdoc.AnalizadorDoc;
import es.caib.xml.analiza.formdoc.AnalizadorDocException;
import es.caib.xml.util.HashMapIterable;

/**
 * Clase para probar el funcionamiento de AnalizadorDoc
 * con el documento FormularioProcedimiento.docx (passwd 1234). 
 *  
 * @author ihdelpino
 *
 */
public class PruebaAnalizadorDoc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inputFilename = "moduls/llibreria-xml/moduls/test/FormularioProcedimiento.docx";
		try {
			System.out.println("Inicio prueba" );
			String[] aux = inputFilename.split("\\.");
			System.out.println("Fichero: " + inputFilename);
			System.out.println("Tipo fichero: " + StringUtil.getExtension(inputFilename));
			FileInputStream contenido = new FileInputStream(inputFilename);
			AnalizadorDoc analizador = new AnalizadorDoc();
			HashMapIterable resultado = analizador.analizar(contenido, aux[1]);
			imprimirResultado(resultado);
			String xml = getXML(resultado);
			System.out.println("XML: ");
			System.out.println(xml);
			System.out.println("Fin prueba");
		} catch (FileNotFoundException e) {
			System.out.println("El fichero " + inputFilename + " no existe.");
		} catch (AnalizadorDocException a) {
			System.out.println(a.getMessage());
		}

	}
	
	/**
	 * Muestra el contenido del análisis
	 * @param resultado
	 */
	private static void imprimirResultado(HashMapIterable resultado) {
		Iterator it = resultado.iterator();
		while (it.hasNext()) {
			Nodo n = (Nodo)it.next();
			System.out.println("XPath: " + n.getXpath());
			System.out.println("Valor: " + n.getValor());
		}
	}
	
	/**
	 * Obtiene un XML a partir de los datos del documento
	 * @param resultado
	 * @return
	 */
	private static String getXML(HashMapIterable resultado) {
		try {
			Generador generador = new Generador ();
			generador.setEncoding(ConstantesXML.ENCODING);
			return generador.generarXML(resultado);
		} catch (Exception e) {
			//TODO
			return "Eror al generar el XML : " + e.getMessage();
		}

	}

}
