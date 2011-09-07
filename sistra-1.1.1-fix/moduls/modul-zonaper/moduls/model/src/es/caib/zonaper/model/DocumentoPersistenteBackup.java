package es.caib.zonaper.model;


public class DocumentoPersistenteBackup
{
    // Fields    	
    private Long codigo;
    private TramitePersistenteBackup tramitePersistenteBackup;
    private String identificador;
    private int numeroInstancia=1;
    private char estado;
    private Long rdsCodigo;
    private String rdsClave;
    private String nombreFicheroAnexo;
    
    
	public Long getCodigo() {
		return codigo;
	}

	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	
	public char getEstado() {
		return estado;
	}

	
	public void setEstado(char estado) {
		this.estado = estado;
	}

	
	public String getIdentificador() {
		return identificador;
	}

	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	
	public String getNombreFicheroAnexo() {
		return nombreFicheroAnexo;
	}

	
	public void setNombreFicheroAnexo(String nombreFicheroAnexo) {
		this.nombreFicheroAnexo = nombreFicheroAnexo;
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

	
	public TramitePersistenteBackup getTramitePersistenteBackup() {
		return tramitePersistenteBackup;
	}

	
	public void setTramitePersistenteBackup(TramitePersistenteBackup tramitePersistenteBackup) {
		this.tramitePersistenteBackup = tramitePersistenteBackup;
	}


}
