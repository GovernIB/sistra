package es.caib.zonaper.model;
 
public class DocumentoNotificacionTelematica implements java.io.Serializable {

    // Fields    	
     private Long codigo;
     private NotificacionTelematica notificacionTelematica;
     private String identificador;
     private int numeroInstancia=1;
     private String descripcion;     
     private long codigoRDS;
     private String claveRDS;
     
     private Integer orden;
     
    // Constructors
    /** default constructor */
    public DocumentoNotificacionTelematica() {
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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public NotificacionTelematica getNotificacionTelematica() {
		return notificacionTelematica;
	}

	public void setNotificacionTelematica(
			NotificacionTelematica notificacionTelematica) {
		this.notificacionTelematica = notificacionTelematica;
	}

	public int getNumeroInstancia() {
		return numeroInstancia;
	}

	public void setNumeroInstancia(int numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}


}
