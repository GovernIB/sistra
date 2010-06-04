package es.caib.xml.analiza;

import java.util.ArrayList;
import java.util.List;

public class Prueba  {
	public static void main (String args[]) throws Exception{
		List attb1 = new ArrayList ();
		List attb2 = new ArrayList ();
		
		attb1.add (new Par ("atb1", "valor1"));
		attb2.add (new Par ("atb1", "valor1"));
		
		Nodo nodo1 = new Nodo ("nodo1", "valor1", attb1);
		Nodo nodo2 = new Nodo ("nodo1", "valor1", attb1);
		
		System.out.println ("Iguales? " + ((nodo1.equals(nodo2)) ? "SI": "NO"));
	}
}
