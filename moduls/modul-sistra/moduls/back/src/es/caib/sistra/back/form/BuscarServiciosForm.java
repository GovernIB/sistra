package es.caib.sistra.back.form;

import org.apache.struts.validator.ValidatorForm;

public class BuscarServiciosForm extends ValidatorForm
{
	private String idCampo;
	
	private String filtro;

	public String getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(String idCampo) {
		this.idCampo = idCampo;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	
}
