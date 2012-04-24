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
    private Character tipoDocumento;
    private Character compulsarDocumento;
    private Character fotocopia;
    private Character firma;
    private Character confirmado;
    
	
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

	
	public Character getCompulsarDocumento() {
		return compulsarDocumento;
	}

	
	public void setCompulsarDocumento(Character compulsarDocumento) {
		this.compulsarDocumento = compulsarDocumento;
	}

	
	public Character getConfirmado() {
		return confirmado;
	}


	public void setConfirmado(Character confirmado) {
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

	
	public Character getFirma() {
		return firma;
	}

	
	public void setFirma(Character firma) {
		this.firma = firma;
	}

	
	public Character getFotocopia() {
		return fotocopia;
	}

	
	public void setFotocopia(Character fotocopia) {
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

	
	public Character getTipoDocumento() {
		return tipoDocumento;
	}

	
	public void setTipoDocumento(Character tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


}
