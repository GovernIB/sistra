package es.caib.bantel.model;


public class FicheroExportacion  implements java.io.Serializable 
{

    // Fields   
	 private String codigo;
     private byte[] datos;     
     private Tramite tramite;
     
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public byte[] getDatos() {
		return datos;
	}
	public void setDatos(byte[] datos) {
		this.datos = datos;
	}
	public Tramite getTramite() {
		return tramite;
	}
	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}



   
}
