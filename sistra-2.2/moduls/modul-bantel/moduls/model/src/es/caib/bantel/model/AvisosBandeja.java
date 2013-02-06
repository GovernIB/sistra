package es.caib.bantel.model;

import java.io.Serializable;
import java.util.Date;

public class AvisosBandeja implements Serializable
{
	
	public final static String AVISO_GESTOR = "GESTOR";
	public final static String AVISO_MONITORIZACION = "MONITORIZACION";
	
	private String identificador;
	private Date fechaUltimoAviso;
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Date getFechaUltimoAviso() {
		return fechaUltimoAviso;
	}
	public void setFechaUltimoAviso(Date fechaUltimoAviso) {
		this.fechaUltimoAviso = fechaUltimoAviso;
	}


}
