package es.caib.sistra.model;


public class MensajeTramite  extends Traducible {


    // Fields    
     private Long codigo;
     private TramiteVersion tramiteVersion;
     private String identificador;
     
    // Constructors
    /** default constructor */
    public MensajeTramite() {
    }

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

	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	public void setTramiteVersion(TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}
    
	 public void addTraduccion(String lang, TraMensajeTramite traduccion) {
        setTraduccion(lang, traduccion);
    }   
    
}
