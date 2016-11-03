package es.caib.xml.datospropios.factoria.impl;

import java.util.ArrayList;
import java.util.List;

import es.caib.xml.EstablecerPropiedadException;

public class FormulariosJustificante extends NodoBaseDatosPropios {
	
	private List formularios;
		
	public List getFormularios() {
		return formularios;
	}



	public void setFormularios(List formularios) {
		this.formularios = formularios;
	}



	FormulariosJustificante (){
		formularios = new ArrayList();	
	}
	
	

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
		
		if (! (obj instanceof FormulariosJustificante) ) {
			 return false;			
		}	
		
			 
		FormulariosJustificante dato = (FormulariosJustificante) obj;
			
		if (this.getFormularios() == null && dato.getFormularios() != null) {
			return false;
		}
		
		if (this.getFormularios() != null) {
				if (dato.getFormularios() == null) {
					return false;
				}
				
				if (this.getFormularios().size() != dato.getFormularios().size()) {
					return false;
				}
				
				for (int i = 0; i < this.getFormularios().size(); i++) {
					if (!this.getFormularios().get(i).equals(dato.getFormularios().get(i))) {
						return false;
					}
				}
		}
		
		return true;
		
	}



	



	
}
