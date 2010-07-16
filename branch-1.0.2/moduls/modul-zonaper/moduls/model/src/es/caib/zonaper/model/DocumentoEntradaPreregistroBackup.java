package es.caib.zonaper.model;

public class DocumentoEntradaPreregistroBackup
{
    private Long codigo;
    private EntradaPreregistroBackup entradaPreregistroBackup;
    /**
     * Indica si requiere presentación presencial
     */
    private char presencial;
    
    private String identificador;
    private int numeroInstancia=1;
    
    private String descripcion;
    
    // Referencia documento anexado telemáticamente
    private long codigoRDS;
    private String claveRDS;
    
    // Parametros presentación presencial
    private char tipoDocumento;
    private char compulsarDocumento;
    private char fotocopia;
    private char firma;
    private char confirmado;
    
	
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

	
	public char getCompulsarDocumento() {
		return compulsarDocumento;
	}

	
	public void setCompulsarDocumento(char compulsarDocumento) {
		this.compulsarDocumento = compulsarDocumento;
	}

	
	public char getConfirmado() {
		return confirmado;
	}


	public void setConfirmado(char confirmado) {
		this.confirmado = confirmado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public EntradaPreregistroBackup getEntradaPreregistroBackup() {
		return entradaPreregistroBackup;
	}

	
	public void setEntradaPreregistroBackup(EntradaPreregistroBackup entradaPreregistroBackup) {
		this.entradaPreregistroBackup = entradaPreregistroBackup;
	}

	
	public char getFirma() {
		return firma;
	}

	
	public void setFirma(char firma) {
		this.firma = firma;
	}

	
	public char getFotocopia() {
		return fotocopia;
	}

	
	public void setFotocopia(char fotocopia) {
		this.fotocopia = fotocopia;
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

	
	public char getPresencial() {
		return presencial;
	}

	
	public void setPresencial(char presencial) {
		this.presencial = presencial;
	}

	
	public char getTipoDocumento() {
		return tipoDocumento;
	}

	
	public void setTipoDocumento(char tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


}
