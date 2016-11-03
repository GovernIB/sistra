package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.Date;

/**
 * Resumen expediente PAD: cabecera del expediente sin sus elementos.
 * 
 */
public class ResumenExpedientePAD implements Serializable {
	
	/**
	 * Id procedimiento.
	 */
	private String identificadorProcedimiento;
	/**
	 * Id expediente.
	 */
	private String identificadorExpediente;
	/**
	 * Codigo unidad administrativa.
	 */
	private Long unidadAdministrativa;
	/**
	 * Clave acceso expediente.
	 */
	private String claveExpediente=null;
	/**
	 * Estado expediente (ver ConstantesZPE).
	 */
	private String estado;
	/**
	 * Fecha inicio expediente.
	 */
	private Date fechaInicio;
	/**
	 * Fecha ultima actualizacion.
	 */
	private Date fechaUltimaActualizacion;
	/**
	 * Idioma expediente.
	 */
	private String idioma="ca";
	/**
	 * Descripcion.
	 */
	private String descripcion;
	/**
	 * Indica si es autenticado.
	 */
	private boolean autenticado = true;
	/**
	 * Id usuario.
	 */
	private String identificadorUsuario;
	/**
	 * Nif representante.
	 */
	private String nifRepresentante;
	/**
	 * Nombre representante.
	 */
	private String nombreRepresentante;
	/**
	 * Nif representado.
	 */
	private String nifRepresentado;
	/**
	 * Nombre representado.
	 */
	private String nombreRepresentado;
	/**
	 * Numero de entrada que genera expediente.
	 */
	private String numeroEntradaBTE;
	/**
	 * Id gestor.
	 */
	private String identificadorGestor;
	/**
	 * Avisos movilidad. 
	 */
	private ConfiguracionAvisosExpedientePAD configuracionAvisos = new ConfiguracionAvisosExpedientePAD();

	public String getIdentificadorProcedimiento() {
		return identificadorProcedimiento;
	}

	public void setIdentificadorProcedimiento(String identificadorProcedimiento) {
		this.identificadorProcedimiento = identificadorProcedimiento;
	}

	public String getIdentificadorExpediente() {
		return identificadorExpediente;
	}

	public void setIdentificadorExpediente(String identificadorExpediente) {
		this.identificadorExpediente = identificadorExpediente;
	}

	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	public String getClaveExpediente() {
		return claveExpediente;
	}

	public void setClaveExpediente(String claveExpediente) {
		this.claveExpediente = claveExpediente;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	public String getIdentificadorUsuario() {
		return identificadorUsuario;
	}

	public void setIdentificadorUsuario(String identificadorUsuario) {
		this.identificadorUsuario = identificadorUsuario;
	}

	public String getNifRepresentante() {
		return nifRepresentante;
	}

	public void setNifRepresentante(String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getNombreRepresentado() {
		return nombreRepresentado;
	}

	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}

	public String getNumeroEntradaBTE() {
		return numeroEntradaBTE;
	}

	public void setNumeroEntradaBTE(String numeroEntradaBTE) {
		this.numeroEntradaBTE = numeroEntradaBTE;
	}

	public ConfiguracionAvisosExpedientePAD getConfiguracionAvisos() {
		return configuracionAvisos;
	}

	public void setConfiguracionAvisos(
			ConfiguracionAvisosExpedientePAD configuracionAvisos) {
		this.configuracionAvisos = configuracionAvisos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	public String getIdentificadorGestor() {
		return identificadorGestor;
	}

	public void setIdentificadorGestor(String identificadorGestor) {
		this.identificadorGestor = identificadorGestor;
	}
	
}
