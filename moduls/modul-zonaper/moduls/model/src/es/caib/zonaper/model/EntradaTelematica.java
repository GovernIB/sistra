package es.caib.zonaper.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class EntradaTelematica implements java.io.Serializable, Entrada {
    
	
	// Fields    	
     private Long codigo;
     private String idPersistencia;
     private char tipo;
     private char nivelAutenticacion;
     private String usuario;
     private String descripcionTramite;
     private String numeroRegistro;
     private Date fecha;     
     private long codigoRdsAsiento;
     private String claveRdsAsiento;     
     private long codigoRdsJustificante;
     private String claveRdsJustificante;
     private Set documentos = new HashSet(0);     
     private String idioma;
     private String nifRepresentante;
     private String nombreRepresentante;
     private String nifRepresentado;
     private String nombreRepresentado;
     private String nifDelegado;
     private String nombreDelegado;
     private String procedimiento;
     private String tramite;
     private Integer version;
 	// Opciones de aviso movilidad
    private String habilitarAvisos;
 	private String avisoSMS;
 	private String avisoEmail;
 	// Opciones notificacion telematica
 	private String habilitarNotificacionTelematica;
//  Tramite subsanacion
 	private String subsanacionExpedienteCodigo;
 	private Long subsanacionExpedienteUA;
 	
     public String getIdPersistencia() {
		return idPersistencia;
	}

	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}

	// Constructors
    /** default constructor */
    public EntradaTelematica() {
    }
    
    // Methods
	public void addDocumento(DocumentoEntradaTelematica doc) {
		doc.setEntradaTelematica(this);    	
        documentos.add(doc);
    }

    public void removeDocumento(DocumentoEntradaTelematica doc) {    	
    	documentos.remove(doc);    	
    }

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getClaveRdsAsiento()
	 */
	public String getClaveRdsAsiento() {
		return claveRdsAsiento;
	}

	public void setClaveRdsAsiento(String claveRdsAsiento) {
		this.claveRdsAsiento = claveRdsAsiento;
	}

	public String getClaveRdsJustificante() {
		return claveRdsJustificante;
	}

	public void setClaveRdsJustificante(String claveRdsJustificante) {
		this.claveRdsJustificante = claveRdsJustificante;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getCodigo()
	 */
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getCodigoRdsAsiento()
	 */
	public long getCodigoRdsAsiento() {
		return codigoRdsAsiento;
	}

	public void setCodigoRdsAsiento(long codigoRdsAsiento) {
		this.codigoRdsAsiento = codigoRdsAsiento;
	}

	public long getCodigoRdsJustificante() {
		return codigoRdsJustificante;
	}

	public void setCodigoRdsJustificante(long codigoRdsJustificante) {
		this.codigoRdsJustificante = codigoRdsJustificante;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getDocumentos()
	 */
	public Set getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Metodo por compatibilidad con EntradaPrerregistro
	 */
	public Date getFechaConfirmacion()
	{
		return null;
	}

	/**
	 * Metodo por compatibilidad con EntradaPrerregistro
	 */
	public String getNumeroPreregistro()
	{
		return null;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getNifRepresentante() {
		return nifRepresentante;
	}

	public void setNifRepresentante(String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}

	public String getNombreRepresentado() {
		return nombreRepresentado;
	}

	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}

	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public String getHabilitarNotificacionTelematica() {
		return habilitarNotificacionTelematica;
	}

	public void setHabilitarNotificacionTelematica(
			String habilitarNotificacionTelematica) {
		this.habilitarNotificacionTelematica = habilitarNotificacionTelematica;
	}

	public String getNifDelegado() {
		return nifDelegado;
	}

	public void setNifDelegado(String nifDelegado) {
		this.nifDelegado = nifDelegado;
	}

	public String getNombreDelegado() {
		return nombreDelegado;
	}

	public void setNombreDelegado(String nombreDelegado) {
		this.nombreDelegado = nombreDelegado;
	}

	public String getSubsanacionExpedienteCodigo() {
		return subsanacionExpedienteCodigo;
	}

	public void setSubsanacionExpedienteCodigo(String subsanacionExpedienteCodigo) {
		this.subsanacionExpedienteCodigo = subsanacionExpedienteCodigo;
	}

	public Long getSubsanacionExpedienteUA() {
		return subsanacionExpedienteUA;
	}

	public void setSubsanacionExpedienteUA(Long subsanacionExpedienteUA) {
		this.subsanacionExpedienteUA = subsanacionExpedienteUA;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

}
