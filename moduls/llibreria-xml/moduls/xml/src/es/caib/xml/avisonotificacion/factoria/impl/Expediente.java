package es.caib.xml.avisonotificacion.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;

/** Objeto de Expediente
 * 
 * @author magroig
 *
 */
public class Expediente extends NodoBaseAvisoNotificacion  {
			
	private String unidadAdministrativa;
	private String identificadorExpediente;
	private	String claveExpediente;
	private	String tituloExpediente;
	
	
	Expediente (){
		unidadAdministrativa = null;
		identificadorExpediente = null;
		claveExpediente = null;
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio("Expediente", "unidadAdministrativa", this.getUnidadAdministrativa());
		validaCampoObligatorio("Expediente", "identificadorExpediente", this.getIdentificadorExpediente());
		validaCampoObligatorio("Expediente", "claveExpediente", this.getClaveExpediente());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof Expediente){
			
			if (obj == null) return false;
			
			Expediente inst = (Expediente) obj;
						
			if (!objetosIguales (getIdentificadorExpediente(), inst.getIdentificadorExpediente())) return false;
			if (!objetosIguales (getUnidadAdministrativa(), inst.getUnidadAdministrativa())) return false;
			if (!objetosIguales (getClaveExpediente(), inst.getClaveExpediente())) return false;
			if (!objetosIguales (getTituloExpediente(), inst.getTituloExpediente())) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}


		public String getClaveExpediente() {
		return claveExpediente;
	}


	public void setClaveExpediente(String claveExpediente) {
		this.claveExpediente = claveExpediente;
	}


	public String getIdentificadorExpediente() {
		return identificadorExpediente;
	}


	public void setIdentificadorExpediente(String identificadorExpediente) {
		this.identificadorExpediente = identificadorExpediente;
	}


	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}


	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}


	public String getTituloExpediente() {
		return tituloExpediente;
	}


	public void setTituloExpediente(String tituloExpediente) {
		this.tituloExpediente = tituloExpediente;
	}

}
