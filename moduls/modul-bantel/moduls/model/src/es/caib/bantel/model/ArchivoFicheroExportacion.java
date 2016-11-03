package es.caib.bantel.model;


public class ArchivoFicheroExportacion  implements java.io.Serializable 
{

    // Fields    
     private String codigo;
     private byte[] datos;
     private FicheroExportacion ficheroExportacion;
     
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
	public FicheroExportacion getFicheroExportacion() {
		return ficheroExportacion;
	}
	public void setFicheroExportacion(FicheroExportacion ficheroExportacion) {
		this.ficheroExportacion = ficheroExportacion;
	}


   
}
