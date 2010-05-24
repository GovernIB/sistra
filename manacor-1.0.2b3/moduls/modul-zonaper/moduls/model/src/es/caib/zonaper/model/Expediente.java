package es.caib.zonaper.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Expediente implements Serializable 
{
	//	ESTADOS DE UN EXPEDIENTE
	public final static String ESTADO_SOLICITUD_ENVIADA = "SE";
	public final static String ESTADO_AVISO_PENDIENTE = "AP";
	public final static String ESTADO_AVISO_RECIBIDO = "AR";
	public final static String ESTADO_NOTIFICACION_PENDIENTE = "NP";
	public final static String ESTADO_NOTIFICACION_RECIBIDA = "NR";
	
	
	private Long codigo;
	private String idExpediente;
	private String claveExpediente;
	private String idioma;
	private Timestamp fecha;
	private Timestamp fechaConsulta;
	private String descripcion;
	private String seyconCiudadano;
	private String nifRepresentado;
	private String nombreRepresentado;
	private String usuarioSeycon;
	private Long unidadAdministrativa;
	private String numeroEntradaBTE;	
	
	// Opciones de aviso movilidad
	private String habilitarAvisos;
	private String avisoSMS;
	private String avisoEmail;
	
	// Campos calculados a partir de los elementos
	private Date fechaInicio;
	private Date fechaFin;
	private String estado;
	
	private Set elementos = new HashSet(0);
	
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public String getIdExpediente()
	{
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente)
	{
		this.idExpediente = idExpediente;
	}
	public String getSeyconCiudadano()
	{
		return seyconCiudadano;
	}
	public void setSeyconCiudadano(String seyconCiudadano)
	{
		this.seyconCiudadano = seyconCiudadano;
	}
	public Timestamp getFecha()
	{
		return fecha;
	}
	public void setFecha(Timestamp fecha)
	{
		this.fecha = fecha;
	}	
	public String getUsuarioSeycon()
	{
		return usuarioSeycon;
	}
	public void setUsuarioSeycon(String usuarioSeycon)
	{
		this.usuarioSeycon = usuarioSeycon;
	}
	public Long getUnidadAdministrativa()
	{
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(Long unidadAdministrativa)
	{
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public Timestamp getFechaConsulta()
	{
		return fechaConsulta;
	}
	public void setFechaConsulta(Timestamp fechaConsulta)
	{
		this.fechaConsulta = fechaConsulta;
	}
	public String getClaveExpediente() {
		return claveExpediente;
	}
	public void setClaveExpediente(String claveExpediente) {
		this.claveExpediente = claveExpediente;
	}
	public String getNumeroEntradaBTE() {
		return numeroEntradaBTE;
	}
	public void setNumeroEntradaBTE(String numeroEntradaBTE) {
		this.numeroEntradaBTE = numeroEntradaBTE;
	}

	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public Set getElementos() {
		return elementos;
	}
	public void setElementos(Set elementos) {
		this.elementos = elementos;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public void addElementoExpediente(ElementoExpediente e){
		// Añadimos a lista de elementos
		e.setExpediente(this);
		getElementos().add(e);
		
		// Actualizamos expediente: estado y fecha inicio/fin
		if (e.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA))
			setEstado(ESTADO_SOLICITUD_ENVIADA);
		else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO))
			setEstado(ESTADO_SOLICITUD_ENVIADA);
		else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE))
			setEstado(ESTADO_AVISO_PENDIENTE);
		else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION))
			setEstado(ESTADO_NOTIFICACION_PENDIENTE);							
		if (getElementos().size() == 1) this.setFechaInicio(e.getFecha());
		setFechaFin(e.getFecha());		
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
	public String getAvisoEmail() {
		return avisoEmail;
	}
	public void setAvisoEmail(String avisoEmail) {
		this.avisoEmail = avisoEmail;
	}
	public String getAvisoSMS() {
		return avisoSMS;
	}
	public void setAvisoSMS(String avisoSMS) {
		this.avisoSMS = avisoSMS;
	}
	public String getHabilitarAvisos() {
		return habilitarAvisos;
	}
	public void setHabilitarAvisos(String habilitarAvisos) {
		this.habilitarAvisos = habilitarAvisos;
	}
		
	
	
		
}
