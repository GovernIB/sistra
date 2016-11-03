package es.caib.zonaper.model;

import java.io.Serializable;
import java.util.Date;

public class Persona implements Serializable
{
	public static String TIPO_PERSONA_JURIDICA = "J";
	public static String TIPO_PERSONA_FISICA = "F";
	
	private Long codigo; // identificador unico en la base de datos.
	private String usuarioSeycon;
	private String documentoIdLegal;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String tipoPersona = TIPO_PERSONA_FISICA; // F Fisica, J Juridica
	private String direccion;
    private String codigoPostal;
    private String provincia;
    private String municipio;
    private String telefonoFijo;	
	private String telefonoMovil;
	private String email;
	private boolean habilitarAvisosExpediente=false;
	private String habilitarDelegacion="N";
	private String modificacionesUsuarioSeycon;
	private String modificacionesDocumentoIdLegal;
	
	private Date fechaAlta;
	private Date fechaUltimaMod;
	
	public String getApellido1()
	{
		return apellido1;
	}
	public void setApellido1(String apellido1)
	{
		this.apellido1 = apellido1;
	}
	public String getApellido2()
	{
		return apellido2;
	}
	public void setApellido2(String apellido2)
	{
		this.apellido2 = apellido2;
	}
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getTipoPersona()
	{
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona)
	{
		this.tipoPersona = tipoPersona;
	}
	public String getUsuarioSeycon()
	{
		return usuarioSeycon;
	}
	public void setUsuarioSeycon(String usuarioSeycon)
	{
		this.usuarioSeycon = usuarioSeycon;
	}
	public void setPersonaJuridica(boolean isPersonaJuridica)
	{
		setTipoPersona( isPersonaJuridica ?  TIPO_PERSONA_JURIDICA : TIPO_PERSONA_FISICA );
	}
	public boolean isPersonaJuridica()
	{
		return TIPO_PERSONA_JURIDICA.equalsIgnoreCase( getTipoPersona() );
	}
	public void setPersonaFisica( boolean isPersonaFisica )
	{
		setPersonaJuridica( !isPersonaFisica );
	}
	public boolean isPersonaFisica()
	{
		return !isPersonaJuridica();
	}
	public String getDocumentoIdLegal()
	{
		return documentoIdLegal;
	}
	public void setDocumentoIdLegal(String documentoIdLegal)
	{
		this.documentoIdLegal = documentoIdLegal;
	}
	public Date getFechaAlta()
	{
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta)
	{
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaUltimaMod()
	{
		return fechaUltimaMod;
	}
	public void setFechaUltimaMod(Date fechaUltimaMod)
	{
		this.fechaUltimaMod = fechaUltimaMod;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isHabilitarAvisosExpediente() {
		return habilitarAvisosExpediente;
	}
	public void setHabilitarAvisosExpediente(boolean habilitarAvisosExpediente) {
		this.habilitarAvisosExpediente = habilitarAvisosExpediente;
	}
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefonoFijo() {
		return telefonoFijo;
	}
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	public String getHabilitarDelegacion() {
		return habilitarDelegacion;
	}
	public void setHabilitarDelegacion(String habilitarDelegacion) {
		this.habilitarDelegacion = habilitarDelegacion;
	}
	public String getModificacionesUsuarioSeycon() {
		return modificacionesUsuarioSeycon;
	}
	public void setModificacionesUsuarioSeycon(String modificacionesUsuarioSeycon) {
		this.modificacionesUsuarioSeycon = modificacionesUsuarioSeycon;
	}
	public String getModificacionesDocumentoIdLegal() {
		return modificacionesDocumentoIdLegal;
	}
	public void setModificacionesDocumentoIdLegal(
			String modificacionesDocumentoIdLegal) {
		this.modificacionesDocumentoIdLegal = modificacionesDocumentoIdLegal;
	}
	
	
}
