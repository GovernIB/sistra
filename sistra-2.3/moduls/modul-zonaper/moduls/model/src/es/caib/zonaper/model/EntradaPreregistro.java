package es.caib.zonaper.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class EntradaPreregistro implements java.io.Serializable, Entrada {
    
	
	// Fields    	
     private Long codigo;
     private String numeroPreregistro;
     private Date fecha;
     private Date fechaCaducidad;
     private String idPersistencia;
     private char tipo;
     private char nivelAutenticacion;
     private String usuario;
     private String descripcionTramite;          
     private long codigoRdsAsiento;
     private String claveRdsAsiento;     
     private long codigoRdsJustificante;
     private String claveRdsJustificante;
     private String numeroRegistro = null;
     private Date fechaConfirmacion = null;
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
     private char confirmadoAutomaticamente='N';
 	// Opciones de aviso movilidad
    private String habilitarAvisos;
 	private String avisoSMS;
 	private String avisoEmail;
 	// Opciones de notificacion telematica
 	private String habilitarNotificacionTelematica;
 	// Tramite subsanacion
 	private String subsanacionExpedienteCodigo;
 	private Long subsanacionExpedienteUA;
 	// Alertar tramitacion
 	private String alertasTramitacionGenerar="N";
    private String alertasTramitacionEmail; 
    private String alertasTramitacionSms;
    private Date alertasTramitacionFechaUltima;
    
     // Constructors
    /** default constructor */
    public EntradaPreregistro() {
    }
    
    // Methods
	public void addDocumento(DocumentoEntradaPreregistro doc) {
		doc.setEntradaPreregistro(this);    	
        documentos.add(doc);
    }

    public void removeDocumento(DocumentoEntradaPreregistro doc) {    	
    	documentos.remove(doc);    	
    }

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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

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

	public String getIdPersistencia() {
		return idPersistencia;
	}

	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
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

	public void setFechaConfirmacion(Date fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}

	public String getNumeroPreregistro() {
		return numeroPreregistro;
	}

	public void setNumeroPreregistro(String numeroPreregistro) {
		this.numeroPreregistro = numeroPreregistro;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
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

	public char getConfirmadoAutomaticamente() {
		return confirmadoAutomaticamente;
	}

	public void setConfirmadoAutomaticamente(char confirmadoAutomaticamente) {
		this.confirmadoAutomaticamente = confirmadoAutomaticamente;
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

	public String getAlertasTramitacionGenerar() {
		return alertasTramitacionGenerar;
	}

	public void setAlertasTramitacionGenerar(String alertasTramitacionGenerar) {
		this.alertasTramitacionGenerar = alertasTramitacionGenerar;
	}

	public String getAlertasTramitacionEmail() {
		return alertasTramitacionEmail;
	}

	public void setAlertasTramitacionEmail(String alertasTramitacionEmail) {
		this.alertasTramitacionEmail = alertasTramitacionEmail;
	}

	public String getAlertasTramitacionSms() {
		return alertasTramitacionSms;
	}

	public void setAlertasTramitacionSms(String alertasTramitacionSms) {
		this.alertasTramitacionSms = alertasTramitacionSms;
	}

	public Date getAlertasTramitacionFechaUltima() {
		return alertasTramitacionFechaUltima;
	}

	public void setAlertasTramitacionFechaUltima(Date alertasTramitacionFechaUltima) {
		this.alertasTramitacionFechaUltima = alertasTramitacionFechaUltima;
	}

    
}
