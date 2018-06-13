package es.caib.bantel.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import es.caib.bantel.model.Traducible;
import es.caib.bantel.model.TraProcedimiento;

public class Procedimiento extends Traducible implements Serializable{
	
	// Tipo de acceso al BackOffice para realizar los avisos
	public final static char ACCESO_EJB='E';
	public final static char ACCESO_WEBSERVICE='W';
	
	// localizacionEJB
	public final static char EJB_REMOTO = 'R';
	public final static char EJB_LOCAL  = 'L';
	
	// tipoAccesoEJB
	public final static char AUTENTICACION_SIN 	= 'N';
	public final static char AUTENTICACION_ESTANDAR 	= 'S';
	public final static char AUTENTICACION_ORGANISMO 	= 'C';
		
	private Long codigo;
	private String identificador;

	private String entidad;
	private String idProcExt;
	private char inmediata = 'N';
	private char periodica = 'N';
	//private Long intervaloInforme;
	private char tipoAcceso=ACCESO_EJB;
	private String url;
	private String versionWS;
	private String soapActionWS;
	private char localizacionEJB = EJB_LOCAL;
	private char autenticacionEJB = AUTENTICACION_SIN;
	private String jndiEJB;
	private String usr;
	private String pwd;
	private String rolAcceso;
	private Date ultimoAviso;	
	private byte[] errores;
	private Set gestores = new HashSet(0);
	private Long unidadAdministrativa;
	private String permitirSms = "S";
	private String avisarNotificaciones = "N";
	private String permitirPlazoNotificacionesVariable = "N";
	
	private String oficinaRegistro;
	private String organoRegistro;
	private String accesoClaveDefecto = "N";
	
	/**
	 * Indica remitente para avisos procedimiento.
	 */
	private String remitenteAvisosProcedimiento;
	/**
	 * Indica email respuesta para avisos procedimiento.
	 */
	private String emailRespuestaAvisosProcedimiento;
	
	/**
	 * Indica email para envío de incidencias
	 */
	private String emailIncidencias;
	

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public String getIdProcExt() {
		return idProcExt;
	}
	public void setIdProcExt(String idProcExt) {
		this.idProcExt = idProcExt;
	}
	
/*	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}*/
	public Set getGestores() {
		return gestores;
	}
	public void setGestores(Set gestores) {
		this.gestores = gestores;
	}
	public char getInmediata() {
		return inmediata;
	}
	public void setInmediata(char inmediata) {
		this.inmediata = inmediata;
	}

	public char getPeriodica() {
		return periodica;
	}
	public void setPeriodica(char periodica) {
		this.periodica = periodica;
	}
	/*public Long getIntervaloInforme() {
		return intervaloInforme;
	}
	public void setIntervaloInforme(Long intervaloInforme) {
		this.intervaloInforme = intervaloInforme;
	}	*/
	public String getRolAcceso() {
		return rolAcceso;
	}
	public void setRolAcceso(String rolAcceso) {
		this.rolAcceso = rolAcceso;
	}
	public String getJndiEJB() {
		return jndiEJB;
	}
	public void setJndiEJB(String name) {
		jndiEJB = name;
	}
	public char getLocalizacionEJB() {
		return localizacionEJB;
	}
	public void setLocalizacionEJB(char localizacionEJB) {
		this.localizacionEJB = localizacionEJB;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public char getTipoAcceso() {
		return tipoAcceso;
	}
	public void setTipoAcceso(char tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersionWS() {
		return versionWS;
	}
	public void setVersionWS(String versionWS) {
		this.versionWS = versionWS;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public char getAutenticacionEJB() {
		return autenticacionEJB;
	}
	public void setAutenticacionEJB(char autenticacionEJB) {
		this.autenticacionEJB = autenticacionEJB;
	}	
	
	
	public boolean avisosEnabled(){
		return (this.getPeriodica() == 'S');
	}
	public Date getUltimoAviso() {
		return ultimoAviso;
	}
	public void setUltimoAviso(Date ultimoAviso) {
		this.ultimoAviso = ultimoAviso;
	}	
	public byte[] getErrores() {
		return errores;
	}
	public void setErrores(byte[] errores) {
		this.errores = errores;
	}
	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public String getPermitirSms() {
		return permitirSms;
	}
	public void setPermitirSms(String permitirSms) {
		this.permitirSms = permitirSms;
	}
	public String getAvisarNotificaciones() {
		return avisarNotificaciones;
	}
	public void setAvisarNotificaciones(String avisarNotificaciones) {
		this.avisarNotificaciones = avisarNotificaciones;
	}
	public String getSoapActionWS() {
		return soapActionWS;
	}
	public void setSoapActionWS(String soapActionWS) {
		this.soapActionWS = soapActionWS;
	}
	public String getPermitirPlazoNotificacionesVariable() {
		return permitirPlazoNotificacionesVariable;
	}
	public void setPermitirPlazoNotificacionesVariable(
			String permitirPlazoNotificacionesVariable) {
		this.permitirPlazoNotificacionesVariable = permitirPlazoNotificacionesVariable;
	}
	public String getOficinaRegistro() {
		return oficinaRegistro;
	}
	public void setOficinaRegistro(String oficinaRegistro) {
		this.oficinaRegistro = oficinaRegistro;
	}
	public String getOrganoRegistro() {
		return organoRegistro;
	}
	public void setOrganoRegistro(String organoRegistro) {
		this.organoRegistro = organoRegistro;
	}
	public String getAccesoClaveDefecto() {
		return accesoClaveDefecto;
	}
	public void setAccesoClaveDefecto(String accesoClaveDefecto) {
		this.accesoClaveDefecto = accesoClaveDefecto;
	}
	public String getRemitenteAvisosProcedimiento() {
		return remitenteAvisosProcedimiento;
	}
	public void setRemitenteAvisosProcedimiento(String remitenteAvisosProcedimiento) {
		this.remitenteAvisosProcedimiento = remitenteAvisosProcedimiento;
	}
	public String getEmailRespuestaAvisosProcedimiento() {
		return emailRespuestaAvisosProcedimiento;
	}
	public void setEmailRespuestaAvisosProcedimiento(
			String emailRespuestaAvisosProcedimiento) {
		this.emailRespuestaAvisosProcedimiento = emailRespuestaAvisosProcedimiento;
	}
	public String getEmailIncidencias() {
		return emailIncidencias;
	}
	public void setEmailIncidencias(String emailIncidencias) {
		this.emailIncidencias = emailIncidencias;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

    public void addTraduccion(String lang, TraProcedimiento traduccion) {
        setTraduccion(lang, traduccion);
    }
	
}
