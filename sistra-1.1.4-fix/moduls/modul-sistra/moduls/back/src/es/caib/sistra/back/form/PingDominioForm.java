package es.caib.sistra.back.form;

import org.apache.struts.action.ActionForm;


public class PingDominioForm extends ActionForm
{
	private String dominio;
	
	private String parametros;

	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	
	

}
