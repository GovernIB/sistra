package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

/**
 * 
 * Obtiene datos de la persona almacenadas en la PAD 
 * 
 */
public class PersonaPAD implements Serializable{
	
	/**
	 * CODIGOS DE ERRORES DE VALIDACION
	 */
	public static final int ERROR_ORDEN_PARTICULAS_FALTA_NOMBRE = 1;
	public static final int ERROR_ORDEN_PARTICULAS_FALTA_APELLIDO1 = 2;
	public static final int ERROR_NUMERO_PARTICULAS_DEFECTO = 3;
	public static final int ERROR_NUMERO_PARTICULAS_EXCESO = 4;
	public static final int ERROR_PARTICULAS_NOMBRE_ERRONEAS = 5;
	public static final int ERROR_EMAIL = 6;
	public static final int ERROR_TELEFONO_MOVIL = 7;
	public static final int ERROR_USUARIO_SEYCON_INCORRECTO = 8;
	public static final int ERROR_NIF_INCORRECTO = 9;

	
	private String usuarioSeycon;
	private String nif;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private boolean personaJuridica;
	private String direccion;
    private String codigoPostal;
    private String provincia;
    private String municipio;
    private String telefonoFijo;
	private String telefonoMovil;
	private String email;
	private boolean habilitarAvisosExpediente=false;
	private boolean habilitarDelegacion=false;
	
	
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}
	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}
	public boolean isPersonaJuridica()
	{
		return personaJuridica;
	}
	public void setPersonaJuridica(boolean personaJuridica)
	{
		this.personaJuridica = personaJuridica;
	}
	public boolean isPersonaFisica()
	{
		return !isPersonaJuridica();
	}
	public void setPersonaFisica(boolean personaFisica)
	{
		setPersonaJuridica( !personaFisica );
	}
	
	public String getNombreCompleto() {
		return 
			((getNombre()!=null)?getNombre():"") +
			((getApellido1()!=null)?" " + getApellido1():"") + 
			((getApellido2()!=null)?" " + getApellido2():"");
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
	public boolean isHabilitarDelegacion() {
		return habilitarDelegacion;
	}
	public void setHabilitarDelegacion(boolean habilitarDelegacion) {
		this.habilitarDelegacion = habilitarDelegacion;
	}
	
}
