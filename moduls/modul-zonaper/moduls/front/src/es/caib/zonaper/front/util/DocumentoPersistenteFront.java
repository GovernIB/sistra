package es.caib.zonaper.front.util;

import java.util.List;


import es.caib.zonaper.model.DocumentoPersistente;

public class DocumentoPersistenteFront extends DocumentoPersistente  {

	private String descripcionDocumento;
	private List delegacionFirmantesHTML;
	
	public String getDescripcionDocumento() {
		return descripcionDocumento;
	}

	public void setDescripcionDocumento(String descripcionDocumento) {
		this.descripcionDocumento = descripcionDocumento;
	}

	public List getDelegacionFirmantesHTML(){
		return delegacionFirmantesHTML;
	}

	public void setDelegacionFirmantesHTML(List delegacionFirmantesHTML) {
		this.delegacionFirmantesHTML = delegacionFirmantesHTML;
	}
	
}
