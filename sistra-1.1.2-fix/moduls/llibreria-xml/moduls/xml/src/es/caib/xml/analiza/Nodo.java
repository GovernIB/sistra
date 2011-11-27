package es.caib.xml.analiza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Nodo extends Par implements Serializable{
	private List atributos = new ArrayList();
	private String xpath = "";
	
	public Nodo (String nombre, String valor) throws Exception{
		super (nombre, valor);
		setXpath(nombre);
	}
	
	public Nodo (String nombre)throws Exception{
		super (nombre);
		setXpath(nombre);
	}
	
	public Nodo (String nombre, String valor, List atributos)throws Exception{
		super (nombre, valor);
		this.atributos = (atributos != null) ? atributos : new ArrayList ();
		setXpath(nombre);
	}
	
	public List getAtributos (){
		return atributos;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer (super.toString());
		Iterator atIt = atributos.iterator();
		
		str.append ("[");
		while (atIt.hasNext()){
			str.append (atIt.next().toString());
		}
		str.append ("]");
		
		return str.toString();
	}
	
	public int hashCode (){
		return toString().hashCode();
	}
	
	public boolean equals (Object obj){
		if (obj == null) return false;
		
		if (obj instanceof Nodo){
			Nodo nodoExt = (Nodo) obj;
			
			// Comprobar que el nombre y el valor son iguales
			boolean igualesSinAttb = super.equals (obj);			
			if (!igualesSinAttb) return false;
			
			// Comprobar la lista de atributos
			List attbSelf = getAtributos ();
			List attbExt = nodoExt.getAtributos();
			
			return attbSelf.equals (attbExt);			
		}
		
		return super.equals (obj);
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) throws Exception{
		if (xpath == null) throw new Exception("XPath no puede ser nulo");
		this.xpath = xpath;
	}
}
