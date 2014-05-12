package es.caib.zonaper.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import es.caib.util.StringUtil;

public class TramitePersistente implements java.io.Serializable {
  	
	// Fields    	
     private Long codigo;
     private String idPersistencia;
     private String tramite;
     private int version;
     private String descripcion;
     private char nivelAutenticacion;
     private String usuario;
     private String usuarioFlujoTramitacion;
     private Timestamp fechaCreacion;
     private Timestamp fechaModificacion;  
     private Timestamp fechaCaducidad;
     private String idioma;
     private Set documentos = new HashSet(0);     
     private String parametrosInicio;
     private String delegado;
     private String estadoDelegacion;
     
     private String alertasTramitacionGenerar;
     private String alertasTramitacionEmail; 
     private String alertasTramitacionSms;
     private Date alertasTramitacionFechaUltima;
     
     private String idProcedimiento;
     
    // Constructors
    /** default constructor */
    public TramitePersistente() {
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Set getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}	

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public String getUsuario() {
		return usuario;
	}	

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
	public void addDocumento(DocumentoPersistente doc) {
		doc.setTramitePersistente(this);    	
        documentos.add(doc);
    }

    public void removeDocumento(DocumentoPersistente doc) {    	
    	documentos.remove(doc);    	
    }

	public String getIdPersistencia() {
		return idPersistencia;
	}

	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	private String getParametrosInicio() {
		return parametrosInicio;
	}

	private void setParametrosInicio(String parametrosInicio) {
		this.parametrosInicio = parametrosInicio;
	}	    
    
	public void setParametrosInicioMap(Map parametrosInicio) throws Exception{
		this.setParametrosInicio(StringUtil.serializarMap(parametrosInicio));		
	}
		
	public Map getParametrosInicioMap() throws Exception {
		return (this.getParametrosInicio()!=null?StringUtil.deserializarMap(this.getParametrosInicio()):null);
	}

	public Timestamp getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Timestamp fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getUsuarioFlujoTramitacion() {
		return usuarioFlujoTramitacion;
	}

	public void setUsuarioFlujoTramitacion(String usuarioFlujoTramitacion) {
		this.usuarioFlujoTramitacion = usuarioFlujoTramitacion;
	}

	public String getDelegado() {
		return delegado;
	}

	public void setDelegado(String delegado) {
		this.delegado = delegado;
	}

	public String getEstadoDelegacion() {
		return estadoDelegacion;
	}

	public void setEstadoDelegacion(String estadoDelegacion) {
		this.estadoDelegacion = estadoDelegacion;
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

	public String getIdProcedimiento() {
		return idProcedimiento;
	}

	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}
}
