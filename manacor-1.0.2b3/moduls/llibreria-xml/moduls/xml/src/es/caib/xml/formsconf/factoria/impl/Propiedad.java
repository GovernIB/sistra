package es.caib.xml.formsconf.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;

public class Propiedad extends NodoBaseConfForms {
	private String nombre;
	private String valor;
	
	Propiedad (){}
	

	public void setNombre(String nombre) throws EstablecerPropiedadException {
		validaCampoNoNulo("Propiedad", "Nombre", nombre);
		
		this.nombre = nombre;
	}

	public String getNombre() {		
		return nombre;
	}

	public void setValor(String valor) throws EstablecerPropiedadException {
		validaCampoNoNulo("Propiedad", "Valor", valor);
		
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoNoNulo("Propiedad", "Nombre", getNombre ());
		validaCampoNoNulo("Propiedad", "Valor", getValor ());
	}
	
	public boolean equals (Object obj){
		if (obj instanceof Propiedad){						
			if (obj == null) return false;
			
			Propiedad propiedad = (Propiedad)  obj;
			
			// Nombre
			String nombre 		= getNombre ();
			String nombreExt 	= propiedad.getNombre ();
			
			if (!objetosIguales (nombre, nombreExt)) return false;
			
			// Valor
			String valor		= getValor ();
			String valorExt		= propiedad.getValor();
			
			if (!objetosIguales (valor, valorExt)) return false;
			
			// OK asumimos objetos equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

}
