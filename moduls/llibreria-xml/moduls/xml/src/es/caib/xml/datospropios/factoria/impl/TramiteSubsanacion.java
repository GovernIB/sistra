package es.caib.xml.datospropios.factoria.impl;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;

public class TramiteSubsanacion extends NodoBaseDatosPropios {
	
	private String expedienteCodigo;
	private Long expedienteUnidadAdministrativa;
		
	TramiteSubsanacion (){
		expedienteCodigo = null;
		expedienteUnidadAdministrativa = null;	
	}
	
	

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		if ( expedienteCodigo == null ) throw new CampoObligatorioException ("TramiteSubsanacion", "expedienteCodigo");
		if ( expedienteUnidadAdministrativa == null ) throw new CampoObligatorioException ("TramiteSubsanacion", "expedienteUnidadAdministrativa");						
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof TramiteSubsanacion){
			
			if (obj == null) return false;
			 
			TramiteSubsanacion dato = (TramiteSubsanacion) obj;
			
			if (!this.getExpedienteCodigo().equals(dato.getExpedienteCodigo())){
				return false;
			}
			
			if (!this.getExpedienteUnidadAdministrativa().equals(dato.getExpedienteUnidadAdministrativa())){
				return false;
			}
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}



	public String getExpedienteCodigo() {
		return expedienteCodigo;
	}



	public void setExpedienteCodigo(String expedienteCodigo) {
		this.expedienteCodigo = expedienteCodigo;
	}



	public Long getExpedienteUnidadAdministrativa() {
		return expedienteUnidadAdministrativa;
	}



	public void setExpedienteUnidadAdministrativa(
			Long expedienteUnidadAdministrativa) {
		this.expedienteUnidadAdministrativa = expedienteUnidadAdministrativa;
	}



	
}
