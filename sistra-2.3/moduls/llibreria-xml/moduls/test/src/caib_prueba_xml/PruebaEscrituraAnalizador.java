package caib_prueba_xml;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Generador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.analiza.Par;
import es.caib.xml.util.HashMapIterable;

public class PruebaEscrituraAnalizador {

	/**
	 * @param args
	 */
	public static void main(String[] argv) throws Exception {
		/*
		<?xml version='1.0' encoding='UTF-8'?>
		<nodo_raiz>
			<nodo1>
				<nodo1.1>Texto del nodo 1.1</nodo1.1>
				<nodo1.2>Texto del nodo 1.2</nodo1.2>				
			</nodo1>			
			<nodo2>Texto del nodo 2</nodo2>
		</nodo_raiz>
		*/
			
		
		Generador generador = new Generador ();
		generador.setEncoding(ConstantesXML.ENCODING);
		
		Nodo nodo1_1 = new Nodo("/nodo_raiz/nodo1/nodo1.1", "SÍ");
		Nodo nodo1_1_bis = new Nodo("/nodo_raiz/nodo1/nodo1.1", "NO");
		Nodo nodo1_2 = new Nodo("/nodo_raiz/nodo1/nodo1.2", "Juan Ibañez");
		Nodo nodo2 = new Nodo("/nodo_raiz/nodo2", "Texto del nodo 2");
				
		List listaNodo1_1 = new ArrayList();
		listaNodo1_1.add(nodo1_1);
		listaNodo1_1.add(nodo1_1_bis);
		
		HashMapIterable hojas = new HashMapIterable();
		hojas.put(nodo1_1.getXpath(),listaNodo1_1);
		
		hojas.put(nodo2.getXpath(),nodo2);
		hojas.put(nodo1_2.getXpath(),nodo1_2);
		
		String xmlGen = generador.generarXML(hojas);  						
    	System.out.println ("XML generado\n" + xmlGen);
    	
    	FileOutputStream fos = new FileOutputStream("moduls/llibreria-xml/moduls/test/analizador_generado.xml");
    	ByteArrayInputStream bis = new ByteArrayInputStream(xmlGen.getBytes(ConstantesXML.ENCODING));
		copy(bis,fos);
		fos.close();
		bis.close();
			
    }
	
	
	private static int copy(InputStream input, OutputStream output) throws IOException{
	      byte buffer[] = new byte[4096];
	      int count = 0;
	      for(int n = 0; -1 != (n = input.read(buffer));)
	      {
	          output.write(buffer, 0, n);
	          count += n;
	      }
	
	      return count;
	  }

}