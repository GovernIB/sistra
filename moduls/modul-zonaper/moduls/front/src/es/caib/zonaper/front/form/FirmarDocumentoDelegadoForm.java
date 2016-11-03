package es.caib.zonaper.front.form;

import org.apache.struts.validator.ValidatorForm;


public class FirmarDocumentoDelegadoForm extends ValidatorForm
{
	private String firma;
	private String documentoB64;
	private String identificador;
	

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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDocumentoB64() {
		return documentoB64;
	}

	public void setDocumentoB64(String documentoB64) {
		this.documentoB64 = documentoB64;
	}

	
}
