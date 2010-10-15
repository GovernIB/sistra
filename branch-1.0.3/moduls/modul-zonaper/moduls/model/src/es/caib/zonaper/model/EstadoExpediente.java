package es.caib.zonaper.model;

import java.io.Serializable;
import java.util.Date;

public class EstadoExpediente implements Serializable
{
	
	// TIPOS DE ELEMENTO DE ESTADO EXPEDIENTE
	public final static String TIPO_ENTRADA_TELEMATICA = "T";
	public final static String TIPO_ENTRADA_PREREGISTRO = "P";
	public final static String TIPO_EXPEDIENTE = "E";
	
	/**
	 * Id compuesto por Tipo + Codigo
	 */
	private String id;	
	/**
	 * Tipo de elemento: T / P / E
	 */
	private String tipo;
	/**
	 * Codigo de elemento
	 */
	private Long codigo;
	/**
	 * Descripcion
	 */
	private String descripcion;
	/**
	 * Fecha inicio
	 */
	private Date fechaInicio;
	/**
	 * Fecha ultima actualizacion
	 */
	private Date fechaActualizacion;
	/**
	 * Estado (ver estados Expediente)
	 */
	private String estado;
	/**
	 * Indica si esta autenticado
	 */
	private String autenticado;
	/**
	 * En caso de estar autenticado indica usuario seycon. En caso de no estar autenticado indica id persistencia.
	 */
	private String user;
	/**
	 * En caso de existir representación indica nif del representado
	 */
	private String nifRepresentado;
	
	/**
	 * Idioma de tramitacion
	 */
	private String idioma;
	
	/**
	 * Codigo de expediente
	 */
	private String codigoExpediente;
	
	/**
	 * Unidad administrativa del expediente
	 */
	private Long unidadAdministrativa;
	
	
	public String getAutenticado() {
		return autenticado;
	}
	public void setAutenticado(String autenticado) {
		this.autenticado = autenticado;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNifRepresentado() {
		return nifRepresentado;
	}
	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String usuarioSeycon) {
		this.user = usuarioSeycon;
	}
	public String getCodigoExpediente() {
		return codigoExpediente;
	}
	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
	
}
