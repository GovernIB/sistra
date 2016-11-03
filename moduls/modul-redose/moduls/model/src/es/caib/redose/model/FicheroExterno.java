package es.caib.redose.model;
// Generated 07-mar-2006 18:09:27 by Hibernate Tools 3.1.0 beta3

import java.util.Date;

public class FicheroExterno  implements java.io.Serializable {

    // Fields    
     private String referenciaExterna;
     private Long idDocumento;
     private Date fechaReferencia;
     private String borrar;
     private Long idUbicacion;
     

    // Constructors

    /** default constructor */
    public FicheroExterno() {
    }


	public String getBorrar() {
		return borrar;
	}


	public void setBorrar(String borrar) {
		this.borrar = borrar;
	}


	public String getReferenciaExterna() {
		return referenciaExterna;
	}


	public void setReferenciaExterna(String codigo) {
		this.referenciaExterna = codigo;
	}


	public Long getIdDocumento() {
		return idDocumento;
	}


	public void setIdDocumento(Long documento) {
		this.idDocumento = documento;
	}


	public Date getFechaReferencia() {
		return fechaReferencia;
	}


	public void setFechaReferencia(Date fecha) {
		this.fechaReferencia = fecha;
	}


	public Long getIdUbicacion() {
		return idUbicacion;
	}


	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
    
}