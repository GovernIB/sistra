package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.Date;

/**
 * Obtiene informacion acerca de un preregistro no confirmado
 * 
 */
public class PreregistroPAD implements Serializable{
	
	private Long codigoPreregistro;
	private String numeroPreregistro;
	private String identificadorTramite;
	private Date fechaPreregistro;	
	private Date fechaCaducidad;
	private String nif;
	private String nombre;			
	private String asunto;
	private String numeroRegistro;
	private Date fechaConfirmacion;
	private String identificadorProcedimiento;
	
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public Long getCodigoPreregistro() {
		return codigoPreregistro;
	}
	public void setCodigoPreregistro(Long codigoPreregistro) {
		this.codigoPreregistro = codigoPreregistro;
	}
	public Date getFechaPreregistro() {
		return fechaPreregistro;
	}
	public void setFechaPreregistro(Date fechaPreregistro) {
		this.fechaPreregistro = fechaPreregistro;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumeroPreregistro() {
		return numeroPreregistro;
	}
	public void setNumeroPreregistro(String numeroPreregistro) {
		this.numeroPreregistro = numeroPreregistro;
	}
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public Date getFechaConfirmacion() {
		return fechaConfirmacion;
	}
	public void setFechaConfirmacion(Date fechaRegistro) {
		this.fechaConfirmacion = fechaRegistro;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getIdentificadorTramite() {
		return identificadorTramite;
	}
	public void setIdentificadorTramite(String codigoTramite) {
		this.identificadorTramite = codigoTramite;
	}
	public String getIdentificadorProcedimiento() {
		return identificadorProcedimiento;
	}
	public void setIdentificadorProcedimiento(String identificadorProcedimiento) {
		this.identificadorProcedimiento = identificadorProcedimiento;
	}
	
		
	
}
