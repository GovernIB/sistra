package es.caib.sistra.front.form.formulario;

import es.caib.sistra.front.form.DocumentoForm;

public class FirmarFormularioForm extends DocumentoForm
{
	private String firma;

	private String firmaDelegada;

	public String getFirmaDelegada() {
		return firmaDelegada;
	}

	public void setFirmaDelegada(String firmaDelegada) {
		this.firmaDelegada = firmaDelegada;
	}

	/**
	 * @return Returns the firma.
	 */
	public String getFirma()
	{
		return firma;
	}

	/**
	 * @param firma The firma to set.
	 */
	public void setFirma(String firma)
	{
		this.firma = firma;
	}

}
