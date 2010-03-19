package es.caib.sistra.front.form;

import org.apache.struts.upload.FormFile;

public class AnexarDocumentoForm  extends DocumentoForm
{
	private FormFile datos;
	private String descPersonalizada;
	private String extensiones;
	private int tamanyoMaximo;
	private String firma;
	
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
}
