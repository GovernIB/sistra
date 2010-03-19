package caib_prueba_xml;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.analiza.Par;
import es.caib.xml.util.HashMapIterable;

public class PruebaLecturaAnalizador {

	/**
	 * @param args
	 */
	public static void main(String[] argv) throws Exception {
		
    	Analizador analiza = new Analizador ();
    	HashMapIterable hti = analiza.analizar (new FileInputStream("moduls/llibreria-xml/moduls/test/analizador_generado.xml"),ConstantesXML.ENCODING);
    	
    	Iterator itHti = hti.iterator();
    	while (itHti.hasNext()){
    		Object o = itHti.next();
    		if (o instanceof List){
    			List lista = (List) o;
    			for (Iterator it = lista.iterator();it.hasNext();){
    				printNodo((Nodo) it.next());
    			}    			
    		}else{
    			printNodo((Nodo) o);
    		}    		  	
    	}		
    	
    }
	
	
	private static void printNodo(Nodo n){
		System.out.println (n.getXpath() + " --> " + n.getValor());
		if (n.getAtributos() != null ){
			for (Iterator it=n.getAtributos().iterator();it.hasNext();){
				Par at = (Par)it.next();
				System.out.println (" --------------> " + at.getNombre() + " = " + at.getValor()); 
			}
		}  
	}
	
}