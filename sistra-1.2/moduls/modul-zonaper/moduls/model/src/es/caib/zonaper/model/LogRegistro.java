package es.caib.zonaper.model;

import java.util.Date;
 
public class LogRegistro implements java.io.Serializable{

    // Fields    	
    private LogRegistroId id;
	private Date fechaRegistro;
    private String descripcionError;     
    private String anulado;
    
    // Constructors
    /** default constructor */
    public LogRegistro() {
    }

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LogRegistroId getId() {
		return id;
	}

	public void setId(LogRegistroId id) {
		this.id = id;
	}

	public String getAnulado() {
		return anulado;
	}

	public void setAnulado(String anulado) {
		this.anulado = anulado;
	}

}
