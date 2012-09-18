package es.caib.mobtratel.model;

import java.io.Serializable;

public class Cuenta implements Serializable 
{
	public static final int  DEFECTO = 1;
	
	/**
	 * Id de la cuenta
	 */
	private String codigo;
	/**
	 *	Nombre de la cuenta 
	 */
	private String nombre;
	/**
	 *	Cuenta email utilizada para los envios de tipo Email 
	 */
	private String email;
	/**
	 *	Cuenta PROVATO utilizada para los envios de tipo SMS 
	 */
	private String sms;
	/**
	 *	Cuenta por defecto. 0 -> No, 1-> SI 
	 */
	private int defecto;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getDefecto() {
		return defecto;
	}
	public void setDefecto(int defecto) {
		this.defecto = defecto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	

}
