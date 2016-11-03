package es.caib.xml.datospropios.factoria.impl;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;

public class AlertasTramitacion extends NodoBaseDatosPropios {
	
	private String email;
	private String sms;
		
	AlertasTramitacion (){
		email = null;
		sms = null;	
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		if ( email == null ) throw new CampoObligatorioException ("AlertasTramitacion", "email");							
	}
		
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getSms() {
		return sms;
	}



	public void setSms(String sms) {
		this.sms = sms;
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlertasTramitacion other = (AlertasTramitacion) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (sms == null) {
			if (other.sms != null)
				return false;
		} else if (!sms.equals(other.sms))
			return false;
		return true;
	}

}
