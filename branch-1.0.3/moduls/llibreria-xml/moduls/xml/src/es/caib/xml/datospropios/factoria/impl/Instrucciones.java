package es.caib.xml.datospropios.factoria.impl;

import java.util.Date;

import es.caib.xml.EstablecerPropiedadException;

/** Objeto de Instrucciones que encapsula el nodo XML INSTRUCCIONES de los documentos
 * XML de datos propios. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * Campos requeridos: TextoInstrucciones
 * 
 * @author magroig
 *
 */
public class Instrucciones extends NodoBaseDatosPropios  {
			
	private String textoInstrucciones;
	private DocumentosEntregar documentosEntregar;
	private Date fechaTopeEntrega;
	private String textoFechaTopeEntrega;
	private String identificadorPersistencia;
	private String habilitarNotificacionTelematica;
	private String habilitarAvisos;
	private String avisoSMS;
	private String avisoEmail;
	
	Instrucciones (){
		textoInstrucciones = null;
		documentosEntregar = null;
		fechaTopeEntrega = null;
		identificadorPersistencia = null;
	}
			
	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Instrucciones#getTextoInstrucciones()
	 */
	public String getTextoInstrucciones() {
		return textoInstrucciones;
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Instrucciones#setTextoInstrucciones(java.lang.String)
	 */
	public void setTextoInstrucciones(String textoInstrucciones)
			throws EstablecerPropiedadException {
		
		validaCampoObligatorio("Instrucciones", "TextoInstrucciones", textoInstrucciones);
		
		this.textoInstrucciones = textoInstrucciones;
	}
	
	public Date getFechaTopeEntrega() {			
		return fechaTopeEntrega;		
	}
	
	public void setFechaTopeEntrega(Date fechaTope) {
		this.fechaTopeEntrega = fechaTope;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Instrucciones#getDocumentosEntregar()
	 */
	public DocumentosEntregar getDocumentosEntregar() {				
		return documentosEntregar;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Instrucciones#setDocumentosEntregar(es.caib.xml.datospropios.factoria.DocumentosEntregar)
	 */
	public void setDocumentosEntregar(DocumentosEntregar documentos) throws EstablecerPropiedadException {
		this.documentosEntregar = documentos;
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio("instrucciones", "TextoInstrucciones", getTextoInstrucciones ());
		
		DocumentosEntregar docsEntregar = getDocumentosEntregar ();
		if (docsEntregar != null) docsEntregar.comprobarDatosRequeridos();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof Instrucciones){
			
			if (obj == null) return false;
			
			Instrucciones inst = (Instrucciones) obj;
			
			// Comprobar Texto Instrucciones
			if (!objetosIguales (getTextoInstrucciones(), inst.getTextoInstrucciones())) return false;
			
			// Comprobar Documentos Entregar
			if (!objetosIguales (getDocumentosEntregar(), inst.getDocumentosEntregar())) return false;
			
			// Comprobar Fecha Tope
			if (!objetosIguales (getFechaTopeEntrega(), inst.getFechaTopeEntrega())) return false;
			
			// Comprobar Texto Fecha Tope
			if (!objetosIguales (getTextoFechaTopeEntrega(), inst.getTextoFechaTopeEntrega())) return false;
			
			// Comprobar Id Persistencia
			if (!objetosIguales (getIdentificadorPersistencia(), inst.getIdentificadorPersistencia())) return false;
			
			// Comprobar aviso sms
			if (!objetosIguales (getAvisoSMS(), inst.getAvisoSMS())) return false;

			// Comprobar aviso email
			if (!objetosIguales (getAvisoEmail(), inst.getAvisoEmail())) return false;

			// Comprobar habilitar avisos
			if (!objetosIguales (getHabilitarAvisos(), inst.getHabilitarAvisos())) return false;
			
			//  Comprobar habilitar notificacion
			if (!objetosIguales (getHabilitarNotificacionTelematica(), inst.getHabilitarNotificacionTelematica())) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

	public String getIdentificadorPersistencia() {
		return identificadorPersistencia;
	}

	public void setIdentificadorPersistencia(String identificadorPersistencia) {
		this.identificadorPersistencia = identificadorPersistencia;
	}

	public String getTextoFechaTopeEntrega() {
		return textoFechaTopeEntrega;
	}

	public void setTextoFechaTopeEntrega(String textoFechaTopeEntrega) {
		this.textoFechaTopeEntrega = textoFechaTopeEntrega;
	}

	public String getAvisoEmail() {
		return avisoEmail;
	}

	public void setAvisoEmail(String avisoEmail) {
		this.avisoEmail = avisoEmail;
	}

	public String getAvisoSMS() {
		return avisoSMS;
	}

	public void setAvisoSMS(String avisoSMS) {
		this.avisoSMS = avisoSMS;
	}

	public String getHabilitarAvisos() {
		return habilitarAvisos;
	}

	public void setHabilitarAvisos(String habilitarAvisos) {
		this.habilitarAvisos = habilitarAvisos;
	}

	public String getHabilitarNotificacionTelematica() {
		return habilitarNotificacionTelematica;
	}

	public void setHabilitarNotificacionTelematica(
			String habilitarNotificacionTelematica) {
		this.habilitarNotificacionTelematica = habilitarNotificacionTelematica;
	}

	

}
