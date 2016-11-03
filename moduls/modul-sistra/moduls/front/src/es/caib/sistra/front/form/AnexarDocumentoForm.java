package es.caib.sistra.front.form;

import org.apache.struts.upload.FormFile;

public class AnexarDocumentoForm  extends DocumentoForm
{
	private FormFile datos;
	private String descPersonalizada;
	private String extensiones;
	private int tamanyoMaximo;
	private String firma;
	private String documentoFirmar;
	private String firmaDelegada;
	
	
	public FormFile getDatos()
	{
		return datos;
	}
	public void setDatos(FormFile datos)
	{
		this.datos = datos;
	}
	public String getDescPersonalizada()
	{
		return descPersonalizada;
	}
	public void setDescPersonalizada(String descPersonalizadas)
	{
		this.descPersonalizada = descPersonalizadas;
	}
	public String getExtensiones() {
		return extensiones;
	}
	public void setExtensiones(String extensiones) {
		this.extensiones = extensiones;
	}
	public int getTamanyoMaximo() {
		return tamanyoMaximo;
	}
	public void setTamanyoMaximo(int tamanyoMaximo) {
		this.tamanyoMaximo = tamanyoMaximo;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	} 
	public String getDocumentoFirmar() {
		return documentoFirmar;
	}
	public void setDocumentoFirmar(String documentoFirmar) {
		this.documentoFirmar = documentoFirmar;
	} 
	public String getFirmaDelegada() {
		return firmaDelegada;
	}
	public void setFirmaDelegada(String firmaDelegada) {
		this.firmaDelegada = firmaDelegada;
	} 
	
}
