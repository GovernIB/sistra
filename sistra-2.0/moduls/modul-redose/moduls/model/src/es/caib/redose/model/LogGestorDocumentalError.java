package es.caib.redose.model;

import java.sql.Timestamp;


public class LogGestorDocumentalError  implements java.io.Serializable {


    // Fields    

    private Long codigo;
    private Documento documento;
    private String usuarioSeycon;
    private String descripcionError;
    private byte[] error;
    private Timestamp fecha;
    
	public LogGestorDocumentalError() {
		super();
	}
	
	public LogGestorDocumentalError(Long codigo, Documento documento, String usuarioSeycon, String descripcionError, byte[] error, Timestamp fecha) {
		super();
		this.codigo = codigo;
		this.documento = documento;
		this.usuarioSeycon = usuarioSeycon;
		this.descripcionError = descripcionError;
		this.error = error;
		this.fecha = fecha;
	}

	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getDescripcionError() {
		return descripcionError;
	}
	
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	public byte[] getError() {
		return error;
	}
	
	public void setError(byte[] error) {
		this.error = error;
	}
	
	public Timestamp getFecha() {
		return fecha;
	}
	
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}
	
	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}


}
