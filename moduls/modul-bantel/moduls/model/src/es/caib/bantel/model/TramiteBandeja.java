package es.caib.bantel.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class TramiteBandeja implements java.io.Serializable {
    
	// Fields    	
     private Long codigo;               
     private String numeroEntrada;
     private String claveAcceso;
     private Timestamp fecha;
     private char tipo;
     private char procesada;
     private Date fechaInicioProcesamiento;
     private String resultadoProcesamiento;
     private Date fechaProcesamiento;
     private Procedimiento procedimiento;
     private String identificadorTramite;
     private int versionTramite;
     private Long unidadAdministrativa;
     private char nivelAutenticacion;     
     private String descripcionTramite;     
     private Long codigoRdsAsiento;
     private String claveRdsAsiento;
     private Long codigoRdsJustificante;
     private String claveRdsJustificante;
     private String numeroRegistro;
     private Date fechaRegistro;
     private String numeroPreregistro;
     private Date fechaPreregistro;
     private String usuarioSeycon;
     private String usuarioNif;
     private String usuarioNombre;
     private String representadoNif;
     private String representadoNombre;
   	 private String delegadoNif;
 	 private String delegadoNombre;
     private String idioma;
     private String tipoConfirmacionPreregistro;
     // Opciones de aviso movilidad
    private String habilitarAvisos;
  	private String avisoSMS;
  	private String avisoEmail;
  	// Opciones de notificacion telematica
     private String habilitarNotificacionTelematica;
     private char firmada;
    // Tramite subsanacion
 	private String subsanacionExpedienteId;
 	private Long subsanacionExpedienteUA;
 	
     
     private Set documentos = new HashSet(0);     
     
    // Constructors
    /** default constructor */
    public TramiteBandeja() {
    }

	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public Long getCodigoRdsAsiento() {
		return codigoRdsAsiento;
	}

	public void setCodigoRdsAsiento(Long codigoRdsAsiento) {
		this.codigoRdsAsiento = codigoRdsAsiento;
	}

	public Long getCodigoRdsJustificante() {
		return codigoRdsJustificante;
	}

	public void setCodigoRdsJustificante(Long codigoRdsJustificante) {
		this.codigoRdsJustificante = codigoRdsJustificante;
	}

	public String getDescripcionTramite() {
		return descripcionTramite;
	}


	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}


	public Set getDocumentos() {
		return documentos;
	}


	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}


	public Timestamp getFecha() {
		return fecha;
	}


	public void setFecha(Timestamp fecha) {
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

	public char getProcesada() {
		return procesada;
	}


	public void setProcesada(char procesada) {
		this.procesada = procesada;
	}


	public char getTipo() {
		return tipo;
	}


	public void setTipo(char tipo) {
		this.tipo = tipo;
	}


	public String getUsuarioNif() {
		return usuarioNif;
	}


	public void setUsuarioNif(String usuarioNif) {
		this.usuarioNif = usuarioNif;
	}


	public String getUsuarioNombre() {
		return usuarioNombre;
	}


	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}

	
	public int getVersionTramite() {
		return versionTramite;
	}


	public void setVersionTramite(int versionTramite) {
		this.versionTramite = versionTramite;
	}
	
	public void addDocumento(DocumentoBandeja doc) {
		doc.setTramite(this);    	
        documentos.add(doc);
    }

    public void removeDocumento(DocumentoBandeja doc) {    	
    	documentos.remove(doc);    	
    }

	public String getNumeroEntrada() {
		return numeroEntrada;
	}

	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}

	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}

	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}

	public String getRepresentadoNif() {
		return representadoNif;
	}

	public void setRepresentadoNif(String representadoNif) {
		this.representadoNif = representadoNif;
	}

	public String getRepresentadoNombre() {
		return representadoNombre;
	}

	public void setRepresentadoNombre(String representadoNombre) {
		this.representadoNombre = representadoNombre;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Date getFechaPreregistro() {
		return fechaPreregistro;
	}

	public void setFechaPreregistro(Date fechaPreregistro) {
		this.fechaPreregistro = fechaPreregistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNumeroPreregistro() {
		return numeroPreregistro;
	}

	public void setNumeroPreregistro(String numeroPreregistro) {
		this.numeroPreregistro = numeroPreregistro;
	}	

	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	public String getResultadoProcesamiento() {
		return resultadoProcesamiento;
	}

	public void setResultadoProcesamiento(String resultadoProcesamiento) {
		this.resultadoProcesamiento = resultadoProcesamiento;
	}

	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}

	public String getTipoConfirmacionPreregistro() {
		return tipoConfirmacionPreregistro;
	}

	public void setTipoConfirmacionPreregistro(String tipoConfirmacionPreregistro) {
		this.tipoConfirmacionPreregistro = tipoConfirmacionPreregistro;
	}

	public String getHabilitarNotificacionTelematica() {
		return habilitarNotificacionTelematica;
	}

	public void setHabilitarNotificacionTelematica(
			String habilitarNotificacionTelematica) {
		this.habilitarNotificacionTelematica = habilitarNotificacionTelematica;
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

	public char getFirmada() {
		return firmada;
	}

	public void setFirmada(char firmada) {
		this.firmada = firmada;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getDelegadoNif() {
		return delegadoNif;
	}

	public void setDelegadoNif(String delegadoNif) {
		this.delegadoNif = delegadoNif;
	}

	public String getDelegadoNombre() {
		return delegadoNombre;
	}

	public void setDelegadoNombre(String delegadoNombre) {
		this.delegadoNombre = delegadoNombre;
	}

	public String getSubsanacionExpedienteId() {
		return subsanacionExpedienteId;
	}

	public void setSubsanacionExpedienteId(String subsanacionExpedienteId) {
		this.subsanacionExpedienteId = subsanacionExpedienteId;
	}

	public Long getSubsanacionExpedienteUA() {
		return subsanacionExpedienteUA;
	}

	public void setSubsanacionExpedienteUA(Long subsanacionExpedienteUA) {
		this.subsanacionExpedienteUA = subsanacionExpedienteUA;
	}

	public Procedimiento getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(Procedimiento procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getIdentificadorTramite() {
		return identificadorTramite;
	}

	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}

	public Date getFechaInicioProcesamiento() {
		return fechaInicioProcesamiento;
	}

	public void setFechaInicioProcesamiento(Date fechaInicioProcesamiento) {
		this.fechaInicioProcesamiento = fechaInicioProcesamiento;
	}



}
