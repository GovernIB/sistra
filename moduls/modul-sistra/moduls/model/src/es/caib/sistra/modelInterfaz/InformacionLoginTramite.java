package es.caib.sistra.modelInterfaz;

import java.io.Serializable;

/**
 * Información necesaria para el proceso de login
 * de inicio de un trámite
 */
public class InformacionLoginTramite implements Serializable{
	
	/**
	 * Descripcion trámite
	 */
	private String descripcionTramite;
	/**
	 * Niveles autenticación. Combinación de las letras A,U,C (Anónimo, Usuario , Certificado)
	 */
	private String nivelesAutenticacion;
	/**
	 * Indica si se permite inicio por defecto
	 */
	private boolean inicioAnonimoDefecto=false;
	
	/**
	 * Descripcion trámite
	 * @return Descripcion trámite
	 */
	public String getDescripcionTramite() {
		return descripcionTramite;
	}
	/**
	 * Descripcion trámite
	 * @param descripcionTramite Descripcion trámite
	 */
	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}
	/**
	 * Indica si se permite inicio por defecto
	 * @return true/false
	 */
	public boolean isInicioAnonimoDefecto() {
		return inicioAnonimoDefecto;
	}
	/**
	 * Indica si se permite inicio por defecto
	 * 
	 * @param inicioAnonimoInmediato true/false
	 */
	public void setInicioAnonimoDefecto(boolean inicioAnonimoInmediato) {
		this.inicioAnonimoDefecto = inicioAnonimoInmediato;
	}
	/**
	 * Niveles autenticación. Combinación de las letras A,U,C (Anónimo, Usuario , Certificado)
	 * @return Niveles autenticación
	 */
	public String getNivelesAutenticacion() {
		return nivelesAutenticacion;
	}
	/**
	 * Niveles autenticación. Combinación de las letras A,U,C (Anónimo, Usuario , Certificado)
	 * @param nivelesAutenticacion Niveles autenticación
	 */
	public void setNivelesAutenticacion(String nivelesAutenticacion) {
		this.nivelesAutenticacion = nivelesAutenticacion;
	}
	
	

}
