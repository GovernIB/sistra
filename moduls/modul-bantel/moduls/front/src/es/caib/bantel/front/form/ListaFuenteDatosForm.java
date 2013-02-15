package es.caib.bantel.front.form;

import org.apache.struts.validator.ValidatorForm;

public class ListaFuenteDatosForm extends ValidatorForm
{
	private int pagina;
	
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
}
