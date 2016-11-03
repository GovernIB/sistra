package es.caib.mobtratel.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import es.caib.mobtratel.modelInterfaz.ConstantesMobtratel;

public class Envio implements Serializable 
{
	
	public static String TIPO_EMAIL = "EMAIL";
	public static String TIPO_SMS = "SMS";
	public static String TIPO_EMAIL_SMS = "AMBOS";

	/**
	 * Identificador del envio
	 */
	private Long codigo;
	/**
	 *	Cuenta 
	 */
	private Cuenta cuenta;
	/**
	 *	Nombre descriptivo del envio
	 */
	private String nombre;
	/**
	 *	Fecha Programacion del Envio
	 */
	private Timestamp fechaProgramacionEnvio;
	/**
	 *	Fecha Envio
	 */
	private Timestamp fechaEnvio;
	/**
	 *	Fecha Caducidad
	 */
	private Timestamp fechaCaducidad;
	/**
	 * Mensaje inmediato. Solo para demo.
	 */
	private boolean inmediato;
	/**
	 *	Estado del envio
	 */
	private int estado = ConstantesMobtratel.ESTADOENVIO_PENDIENTE;
	/**
	 *	Usuario Seycon
	 */
	private String usuarioSeycon;
	/**
	 *	Fecha de Registro
	 */
	private Timestamp fechaRegistro;
	/**
	 * Numero de Destinatarios
	 */
	private int numeroDestinatarios;
	/**
	 * Id procedimiento (opcional).
	 */
	private String idProcedimiento;
	/**
	 * Set de emails a enviar
	 */
	private Set emails = new HashSet();
	/**
	 * Set de smss a enviar
	 */
	private Set smss = new HashSet();
	

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



	public int getEstado() {
		return estado;
	}



	public void setEstado(int estado) {
		this.estado = estado;
	}



	public Timestamp getFechaCaducidad() {
		return fechaCaducidad;
	}



	public void setFechaCaducidad(Timestamp fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}



	public Timestamp getFechaEnvio() {
		return fechaEnvio;
	}



	public void setFechaEnvio(Timestamp fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}



	public Timestamp getFechaProgramacionEnvio() {
		return fechaProgramacionEnvio;
	}



	public void setFechaProgramacionEnvio(Timestamp fechaProgramacionEnvio) {
		this.fechaProgramacionEnvio = fechaProgramacionEnvio;
	}



	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}



	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getNumeroDestinatarios() {
		return numeroDestinatarios;
	}



	public void setNumeroDestinatarios(int numeroDestinatarios) {
		this.numeroDestinatarios = numeroDestinatarios;
	}



	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}



	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}



	public Set getEmails() {
		return emails;
	}



	public void setEmails(Set emails) {
		this.emails = emails;
	}



	public Set getSmss() {
		return smss;
	}



	public void setSmss(Set smss) {
		this.smss = smss;
	}


	public void addEmail(MensajeEmail email)
	{
		email.setEnvio(this);
		this.emails.add(email);
	}

	public void addSms(MensajeSms sms)
	{
		sms.setEnvio(this);
		this.smss.add(sms);
	}



	public String getTipo() {
		if((this.emails.size() != 0) &&
		   (this.smss.size() != 0)) return Envio.TIPO_EMAIL_SMS;
		if(this.emails.size() != 0) return Envio.TIPO_EMAIL;
		if(this.smss.size() != 0) return Envio.TIPO_SMS;
		return "";
	}



	public boolean isInmediato() {
		return inmediato;
	}

	public void setInmediato(boolean inmediato) {
		this.inmediato = inmediato;
	}

	/**
	 * Comprueba si todos los envios email y sms estan completados
	 * @return
	 */
	public boolean isCompletado(){
		for (Iterator it = this.getEmails().iterator();it.hasNext();){
			MensajeEmail me = (MensajeEmail) it.next();
			if (me.getEstado() != ConstantesMobtratel.ESTADOENVIO_ENVIADO && me.getEstado() != ConstantesMobtratel.ESTADOENVIO_CANCELADO) return false;
		}
		for (Iterator it = this.getSmss().iterator();it.hasNext();){
			MensajeSms ms = (MensajeSms) it.next();
			if (ms.getEstado() != ConstantesMobtratel.ESTADOENVIO_ENVIADO && ms.getEstado() != ConstantesMobtratel.ESTADOENVIO_CANCELADO) return false;
		}
		return true;
	}
	
	/**
	 * Comprueba si todos los envios email y sms estan completados
	 * @return
	 */
	public boolean isConError(){
		for (Iterator it = this.getEmails().iterator();it.hasNext();){
			MensajeEmail me = (MensajeEmail) it.next();
			if (me.getEstado() == ConstantesMobtratel.ESTADOENVIO_ERROR) return true;
		}
		for (Iterator it = this.getSmss().iterator();it.hasNext();){
			MensajeSms ms = (MensajeSms) it.next();
			if (ms.getEstado() == ConstantesMobtratel.ESTADOENVIO_ERROR) return true;
		}
		return false;
	}



	public String getIdProcedimiento() {
		return idProcedimiento;
	}



	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}
	
}
