package es.caib.zonaper.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class NotificacionTelematica implements java.io.Serializable,ElementoExpedienteItf {
    
	// Fields    	
     private Long codigo;
     private String usuarioSeycon;
     private String numeroRegistro;
     private Date fechaRegistro;
     private long codigoRdsAsiento;
     private String claveRdsAsiento;
     private long codigoRdsJustificante;
     private String claveRdsJustificante;
     private long codigoRdsAviso;
     private String claveRdsAviso;
     private String tituloAviso;
     private long codigoRdsOficio;
     private String claveRdsOficio;
     private String idioma;
     private String nifRepresentante;
     private String nombreRepresentante;
     private String nifRepresentado;
     private String nombreRepresentado;
     private long codigoRdsAcuse;
     private String claveRdsAcuse;
     private Date fechaAcuse;
     private boolean firmarAcuse;
     private String gestorSeycon;
     private Date fechaFinPlazo;
     private boolean rechazada;
     
     private String tramiteSubsanacionDescripcion;
     private String tramiteSubsanacionIdentificador;
     private Integer tramiteSubsanacionVersion;
     private String tramiteSubsanacionParametros;
     
     private String identificadorPersistencia;
     
     private boolean accesiblePorClave;
     private boolean firmarPorClave;
     private String tipoFirmaAcuse; // CERT / CLA
     
     private Set documentos = new HashSet(0);  
                
	// Constructors
    /** default constructor */
    public NotificacionTelematica() {
    }
    
    // Methods
	public void addDocumento(DocumentoNotificacionTelematica doc) {
		doc.setNotificacionTelematica(this);    	
        documentos.add(doc);
    }

    public void removeDocumento(DocumentoNotificacionTelematica doc) {    	
    	documentos.remove(doc);    	
    }

	public String getClaveRdsAcuse() {
		return claveRdsAcuse;
	}

	public void setClaveRdsAcuse(String claveRdsAcuse) {
		this.claveRdsAcuse = claveRdsAcuse;
	}

	public String getClaveRdsAsiento() {
		return claveRdsAsiento;
	}

	public void setClaveRdsAsiento(String claveRdsAsiento) {
		this.claveRdsAsiento = claveRdsAsiento;
	}

	public String getClaveRdsAviso() {
		return claveRdsAviso;
	}

	public void setClaveRdsAviso(String claveRdsAviso) {
		this.claveRdsAviso = claveRdsAviso;
	}

	public String getClaveRdsJustificante() {
		return claveRdsJustificante;
	}

	public void setClaveRdsJustificante(String claveRdsJustificante) {
		this.claveRdsJustificante = claveRdsJustificante;
	}

	public String getClaveRdsOficio() {
		return claveRdsOficio;
	}

	public void setClaveRdsOficio(String claveRdsOficio) {
		this.claveRdsOficio = claveRdsOficio;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public long getCodigoRdsAcuse() {
		return codigoRdsAcuse;
	}

	public void setCodigoRdsAcuse(long codigoRdsAcuse) {
		this.codigoRdsAcuse = codigoRdsAcuse;
	}

	public long getCodigoRdsAsiento() {
		return codigoRdsAsiento;
	}

	public void setCodigoRdsAsiento(long codigoRdsAsiento) {
		this.codigoRdsAsiento = codigoRdsAsiento;
	}

	public long getCodigoRdsAviso() {
		return codigoRdsAviso;
	}

	public void setCodigoRdsAviso(long codigoRdsAviso) {
		this.codigoRdsAviso = codigoRdsAviso;
	}

	public long getCodigoRdsJustificante() {
		return codigoRdsJustificante;
	}

	public void setCodigoRdsJustificante(long codigoRdsJustificante) {
		this.codigoRdsJustificante = codigoRdsJustificante;
	}

	public long getCodigoRdsOficio() {
		return codigoRdsOficio;
	}

	public void setCodigoRdsOficio(long codigoRdsOficio) {
		this.codigoRdsOficio = codigoRdsOficio;
	}

	public Set getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public Date getFechaAcuse() {
		return fechaAcuse;
	}

	public void setFechaAcuse(Date fechaAcuse) {
		this.fechaAcuse = fechaAcuse;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getTituloAviso() {
		return tituloAviso;
	}

	public void setTituloAviso(String tituloAviso) {
		this.tituloAviso = tituloAviso;
	}

	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}

	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}

	public boolean isFirmarAcuse() {
		return firmarAcuse;
	}

	public void setFirmarAcuse(boolean firmarAcuse) {
		this.firmarAcuse = firmarAcuse;
	}

	public String getGestorSeycon() {
		return gestorSeycon;
	}

	public void setGestorSeycon(String gestorSeycon) {
		this.gestorSeycon = gestorSeycon;
	}	
    
	public String getTramiteSubsanacionDescripcion() {
		return tramiteSubsanacionDescripcion;
	}

	public void setTramiteSubsanacionDescripcion(
			String tramiteSubsanacionDescripcion) {
		this.tramiteSubsanacionDescripcion = tramiteSubsanacionDescripcion;
	}

	public String getTramiteSubsanacionIdentificador() {
		return tramiteSubsanacionIdentificador;
	}

	public void setTramiteSubsanacionIdentificador(
			String tramiteSubsanacionIdentificador) {
		this.tramiteSubsanacionIdentificador = tramiteSubsanacionIdentificador;
	}

	public String getTramiteSubsanacionParametros() {
		return tramiteSubsanacionParametros;
	}

	public void setTramiteSubsanacionParametros(String tramiteSubsanacionParametros) {
		this.tramiteSubsanacionParametros = tramiteSubsanacionParametros;
	}

	public Integer getTramiteSubsanacionVersion() {
		return tramiteSubsanacionVersion;
	}

	public void setTramiteSubsanacionVersion(Integer tramiteSubsanacionVersion) {
		this.tramiteSubsanacionVersion = tramiteSubsanacionVersion;
	}

	public Date getFechaFinPlazo() {
		return fechaFinPlazo;
	}

	public void setFechaFinPlazo(Date fechaFinPlazo) {
		this.fechaFinPlazo = fechaFinPlazo;
	}

	public boolean isRechazada() {
		return rechazada;
	}

	public void setRechazada(boolean rechazada) {
		this.rechazada = rechazada;
	}

	public String getIdentificadorPersistencia() {
		return identificadorPersistencia;
	}

	public void setIdentificadorPersistencia(String identificadorPersistencia) {
		this.identificadorPersistencia = identificadorPersistencia;
	}

	public boolean isFirmarPorClave() {
		return firmarPorClave;
	}

	public void setFirmarPorClave(boolean firmarPorClave) {
		this.firmarPorClave = firmarPorClave;
	}

	public boolean isAccesiblePorClave() {
		return accesiblePorClave;
	}

	public void setAccesiblePorClave(boolean accesiblePorClave) {
		this.accesiblePorClave = accesiblePorClave;
	}

	public String getTipoFirmaAcuse() {
		return tipoFirmaAcuse;
	}

	public void setTipoFirmaAcuse(String tipoFirmaAcuse) {
		this.tipoFirmaAcuse = tipoFirmaAcuse;
	}	
    
}
