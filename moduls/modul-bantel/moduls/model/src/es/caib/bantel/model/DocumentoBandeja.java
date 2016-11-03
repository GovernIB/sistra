package es.caib.bantel.model;

// TODO: Evaluar cambiar pk para que no tire de secuencia

public class DocumentoBandeja implements java.io.Serializable {
	
    // Fields    	
     private Long codigo;
     private TramiteBandeja tramite;
     private String presencial;
     private String nombre;
     
     private String identificador;
     private int numeroInstancia=1;
     private String modelo;
     private int version;     
     private Long rdsCodigo;
     private String rdsClave;     
     
     // Parametros presentación presencial
     private String tipoDocumento;
     private String compulsarDocumento;
     private String fotocopia;
     private String firma;
    

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Constructors
    /** default constructor */
    public DocumentoBandeja() {
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

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getNumeroInstancia() {
		return numeroInstancia;
	}

	public void setNumeroInstancia(int numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}

	public String getRdsClave() {
		return rdsClave;
	}

	public void setRdsClave(String rdsClave) {
		this.rdsClave = rdsClave;
	}

	public Long getRdsCodigo() {
		return rdsCodigo;
	}

	public void setRdsCodigo(Long rdsCodigo) {
		this.rdsCodigo = rdsCodigo;
	}

	public TramiteBandeja getTramite() {
		return tramite;
	}

	public void setTramite(TramiteBandeja tramite) {
		this.tramite = tramite;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCompulsarDocumento() {
		return compulsarDocumento;
	}

	public void setCompulsarDocumento(String compulsarDocumento) {
		this.compulsarDocumento = compulsarDocumento;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getFotocopia() {
		return fotocopia;
	}

	public void setFotocopia(String fotocopia) {
		this.fotocopia = fotocopia;
	}

	public String getPresencial() {
		return presencial;
	}

	public void setPresencial(String presencial) {
		this.presencial = presencial;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

   
}
