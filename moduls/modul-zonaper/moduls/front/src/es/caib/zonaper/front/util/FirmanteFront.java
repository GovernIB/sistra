package es.caib.zonaper.front.util;

public class FirmanteFront implements java.io.Serializable  {

	private String nombre;
	private boolean haFirmado;
	
	public boolean isHaFirmado() {
		return haFirmado;
	}
	public void setHaFirmado(boolean haFirmado) {
		this.haFirmado = haFirmado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
