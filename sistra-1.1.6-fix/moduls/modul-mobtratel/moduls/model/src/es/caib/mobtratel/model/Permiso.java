package es.caib.mobtratel.model;

import java.io.Serializable;

public class Permiso implements Serializable 
{
	public static final int NO = 0;
	public static final int SI = 1;

	/**
	 * Codigo
	 */
    private Long codigo;

	/**
	 * Usuario SEYCON
	 */
	private String usuarioSeycon;
	/**
	 *	Cuenta 
	 */
	private Cuenta cuenta;
	/**
	 *	Permiso para enviar SMS. 0 -> No, 1-> SI
	 */
	private int sms;
	/**
	 *	Permiso para enviar Email. 0 -> No, 1-> SI
	 */
	private int email;
	
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}
	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}
	public int getEmail() {
		return email;
	}
	public void setEmail(int email) {
		this.email = email;
	}
	public int getSms() {
		return sms;
	}
	public void setSms(int sms) {
		this.sms = sms;
	}
	
	

}
