package es.caib.bantel.modelInterfaz;

import java.io.Serializable;

/**
 * Contiene la información de un documento presentado en la tramitación.
 * Un documento puede tener información referente a su presentación telemática si 
 * el documento ha sido anexado telemáticamente y/o puede tener información referente 
 * a su presentación presencial si para el documento en cuestión se ha debido llevar a 
 * cabo alguna acción presencial (entrega original, compulsa, fotocopia, etc.)
 *
 */
public class DocumentoBTE  implements Serializable {

	/**
	 * Nombre del documento
	 */
	private String nombre;
		
   /**
    *  Datos presentación telemática
    */
   private DatosDocumentoTelematico presentacionTelematica;
    
   /**
	 * Datos presentación presencial
	 */ 
   private DatosDocumentoPresencial presentacionPresencial;
   
    // Constructors
    /** default constructor */
    public DocumentoBTE() {
    }
    
    /**
	 * Nombre del documento
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Nombre del documento
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
    *  Datos presentación telemática
    */
	public DatosDocumentoTelematico getPresentacionTelematica() {
		return presentacionTelematica;
	}
	/**
    *  Datos presentación telemática
    */
	public void setPresentacionTelematica(DatosDocumentoTelematico ficheroAsociado) {
		this.presentacionTelematica = ficheroAsociado;
	}
	
	/**
	 * Datos presentación presencial
	 */ 
	public DatosDocumentoPresencial getPresentacionPresencial() {
		return presentacionPresencial;
	}

	/**
	 * Datos presentación presencial
	 */ 
	public void setPresentacionPresencial(
			DatosDocumentoPresencial presentacionPresencial) {
		this.presentacionPresencial = presentacionPresencial;
	}

}
