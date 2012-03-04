package es.caib.sistra.model;


public class MensajePlataforma extends Traducible {

    // Fields    	
     private Long codigo;
     private String identificador;
     private char activo;
     
    // Constructors
    /** default constructor */
    public MensajePlataforma() {
    }

    // Property accessors
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

	public void setCurrentLang(String currentLang) {
        super.setCurrentLang(currentLang);          
    }

    public void addTraduccion(String lang, TraMensajePlataforma traduccion) {
        setTraduccion(lang, traduccion);
    }

	public char getActivo() {
		return activo;
	}

	public void setActivo(char activo) {
		this.activo = activo;
	}


}
