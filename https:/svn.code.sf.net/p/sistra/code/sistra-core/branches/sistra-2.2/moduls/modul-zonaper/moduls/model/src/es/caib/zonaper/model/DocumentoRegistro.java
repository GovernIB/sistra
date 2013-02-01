package es.caib.zonaper.model;
 
public class DocumentoRegistro implements java.io.Serializable, DocumentoEntrada {

    // Fields    	
     private Long codigo;
     private RegistroExterno registroExterno;
     private String identificador;
     private int numeroInstancia=1;
     private String descripcion;     
     private long codigoRDS;
     private String claveRDS;
     
    // Constructors
    /** default constructor */
    public DocumentoRegistro() {
    }

	public String getClaveRDS() {
		return claveRDS;
	}

	public void setClaveRDS(String claveRDS) {
		this.claveRDS = claveRDS;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public long getCodigoRDS() {
		return codigoRDS;
	}

	public void setCodigoRDS(long codigoRDS) {
		this.codigoRDS = codigoRDS;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public RegistroExterno getRegistroExterno() {
		return registroExterno;
	}

	public void setRegistroExterno(RegistroExterno registroExterno) {
		this.registroExterno = registroExterno;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getNumeroInstancia() {
		return numeroInstancia;
	}

	public void setNumeroInstancia(int numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}

}
