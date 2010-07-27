package es.caib.sistra.model;

public class DatoJustificante  extends Traducible {
	
	public final static char TIPO_BLOQUE = 'B';
	public final static char TIPO_CAMPO = 'C';	
	
	
    // Fields    
     private Long codigo;
     private EspecTramiteNivel especTramiteNivel;
     private char tipo;
     private int orden;
     private String referenciaCampo;
     private byte[] valorCampoScript;
     private byte[] visibleScript;
          
     
    // Constructors
    /** default constructor */
    public DatoJustificante() {
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getReferenciaCampo() {
		return referenciaCampo;
	}

	public void setReferenciaCampo(String referenciaCampo) {
		this.referenciaCampo = referenciaCampo;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public EspecTramiteNivel getEspecTramiteNivel() {
		return especTramiteNivel;
	}

	public void setEspecTramiteNivel(EspecTramiteNivel especTramiteNivel) {
		this.especTramiteNivel = especTramiteNivel;
	}

	public byte[] getVisibleScript() {
		return visibleScript;
	}

	public void setVisibleScript(byte[] visibleScript) {
		this.visibleScript = visibleScript;
	}
    
    public void addTraduccion(String lang, TraDatoJustificante traduccion) {
        setTraduccion(lang, traduccion);
    }

	public byte[] getValorCampoScript() {
		return valorCampoScript;
	}

	public void setValorCampoScript(byte[] valorCampoScript) {
		this.valorCampoScript = valorCampoScript;
	}
	
}
