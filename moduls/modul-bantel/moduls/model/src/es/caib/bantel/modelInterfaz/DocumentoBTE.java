package es.caib.bantel.modelInterfaz;

import java.io.Serializable;

/**
 * Contiene la informaci�n de un documento presentado en la tramitaci�n.
 * Un documento puede tener informaci�n referente a su presentaci�n telem�tica si 
 * el documento ha sido anexado telem�ticamente y/o puede tener informaci�n referente 
 * a su presentaci�n presencial si para el documento en cuesti�n se ha debido llevar a 
 * cabo alguna acci�n presencial (entrega original, compulsa, fotocopia, etc.)
 *
 */
public class DocumentoBTE  implements Serializable {

	/**
	 * Nombre del documento
	 */
	private String nombre;
		
   /**
    *  Datos presentaci�n telem�tica
    */
   private DatosDocumentoTelematico presentacionTelematica;
    
   /**
	 * Datos presentaci�n presencial
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
    *  Datos presentaci�n telem�tica
    */
	public DatosDocumentoTelematico getPresentacionTelematica() {
		return presentacionTelematica;
	}
	/**
    *  Datos presentaci�n telem�tica
    */
	public void setPresentacionTelematica(DatosDocumentoTelematico ficheroAsociado) {
		this.presentacionTelematica = ficheroAsociado;
	}
	
	/**
	 * Datos presentaci�n presencial
	 */ 
	public DatosDocumentoPresencial getPresentacionPresencial() {
		return presentacionPresencial;
	}

	/**
	 * Datos presentaci�n presencial
	 */ 
	public void setPresentacionPresencial(
			DatosDocumentoPresencial presentacionPresencial) {
		this.presentacionPresencial = presentacionPresencial;
	}

}
