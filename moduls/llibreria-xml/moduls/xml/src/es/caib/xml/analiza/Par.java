package es.caib.xml.analiza;

import java.io.Serializable;

public class Par implements Serializable{
	private String nombre;
	private StringBuffer valor = new StringBuffer ("");
	
	public Par (String nombre, String valor){
		this.nombre = nombre;
		setValor (valor);
	}
	
	Par (String nombre){
		this (nombre, null);
	}
	
	public String toString (){
		return (nombre + "-" + valor.toString());
	}
	
	public int hashCode (){
		return toString().hashCode();
	}

	public String getValor() {
		return valor.toString();
	}

	public void setValor(String valor) {
		if (valor != null) this.valor = new StringBuffer (valor);
		else this.valor = new StringBuffer ("");
	}
	
	public void appendValor (String valor) {
		if (valor != null) this.valor.append (valor);
	}

	public String getNombre() {
		return nombre;
	}
	
	public boolean equals (Object obj){
		if (obj == null) return false;
		
		if (obj instanceof Par){
			Par par = (Par) obj;
			return ( (getNombre ().equals(par.getNombre())) && getValor().equals (par.getValor()));
		}
		
		return super.equals (obj);
	}
	 
}
