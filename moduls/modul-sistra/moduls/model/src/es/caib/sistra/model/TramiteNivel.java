package es.caib.sistra.model;

import java.io.Serializable;

public class TramiteNivel  implements TramiteEspecificado, Serializable {

	public final static char AUTENTICACION_CERTIFICADO = 'C';
	public final static char AUTENTICACION_USUARIOPASSWORD = 'U';
	public final static char AUTENTICACION_ANONIMO = 'A';
	
	
    // Fields    
     private Long codigo;
     private EspecTramiteNivel especificaciones;
     private TramiteVersion tramiteVersion;
     private String nivelAutenticacion;
     

    // Constructors
    /** default constructor */
    public TramiteNivel() {
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public EspecTramiteNivel getEspecificaciones() {
		return especificaciones;
	}

	public void setEspecificaciones(EspecTramiteNivel especificaciones) {
		this.especificaciones = especificaciones;
	}
	
	public String getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	public void setNivelAutenticacion(String nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	public void setTramiteVersion(TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}		
	    
	public void setCurrentLang(String currentLang) {
        especificaciones.setCurrentLang(currentLang);
    }	
	
}
