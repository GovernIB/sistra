package es.caib.bantel.model;


public class FicheroExportacion  implements java.io.Serializable 
{

    // Fields   
	 private String identificadorTramite;
	 private String nombre;
     private ArchivoFicheroExportacion archivoFicheroExportacion;     
     
	public String getIdentificadorTramite() {
		return identificadorTramite;
	}
	public void setIdentificadorTramite(String codigo) {
		this.identificadorTramite = codigo;
	}	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArchivoFicheroExportacion getArchivoFicheroExportacion() {
		return archivoFicheroExportacion;
	}
	public void setArchivoFicheroExportacion(
			ArchivoFicheroExportacion archivoFicheroExportacion) {
		this.archivoFicheroExportacion = archivoFicheroExportacion;
	}
	   
}
