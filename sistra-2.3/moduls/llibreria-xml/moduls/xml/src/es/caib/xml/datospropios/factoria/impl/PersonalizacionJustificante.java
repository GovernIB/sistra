package es.caib.xml.datospropios.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;

public class PersonalizacionJustificante extends NodoBaseDatosPropios {
	
	
	private Boolean ocultarClaveTramitacion;
	
	private Boolean ocultarNifNombre;
	
	

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {								
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		
		if (obj == null) {
			return false;
		}
		
		if (! (obj instanceof PersonalizacionJustificante) ) {
			 return false;			
		}	
		
			 
		PersonalizacionJustificante dato = (PersonalizacionJustificante) obj;
		
		if (!objetosIguales(this.getOcultarClaveTramitacion(), dato.getOcultarClaveTramitacion())) {
			return false;
		}
		if (!objetosIguales(this.getOcultarNifNombre(), dato.getOcultarNifNombre())) {
			return false;
		}	
		
		return true;
		
	}

	public Boolean getOcultarClaveTramitacion() {
		return ocultarClaveTramitacion;
	}

	public void setOcultarClaveTramitacion(Boolean ocultarClaveTramitacion) {
		this.ocultarClaveTramitacion = ocultarClaveTramitacion;
	}

	public Boolean getOcultarNifNombre() {
		return ocultarNifNombre;
	}

	public void setOcultarNifNombre(Boolean ocultarNifNombre) {
		this.ocultarNifNombre = ocultarNifNombre;
	}
}
