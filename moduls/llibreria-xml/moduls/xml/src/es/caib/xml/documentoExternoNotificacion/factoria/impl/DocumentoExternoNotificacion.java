package es.caib.xml.documentoExternoNotificacion.factoria.impl;

import javax.xml.bind.JAXBException;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.documentoExternoNotificacion.modelo.DOCUMENTOEXTERNONOTIFICACION;
import es.caib.xml.documentoExternoNotificacion.modelo.ObjectFactory;

/** 
 * 
 * Documento externo notificacion
 * 
 * 
 * @author magroig
 *
 */
public class DocumentoExternoNotificacion extends NodoBaseDocumentoExternoNotificacion {
			
	private String nombre;
	private String url;
	
	
	DocumentoExternoNotificacion (){	
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {		
		
		if (getNombre() == null || getNombre().length() == 0)
			throw new EstablecerPropiedadException("No se ha especificado nombre","String","nombre",getNombre());
		
		if (getUrl() == null || getUrl().length() == 0)
			throw new EstablecerPropiedadException("No se ha especificado url","String","url",getUrl());
		
	}
	

	public static DOCUMENTOEXTERNONOTIFICACION toJAXB(DocumentoExternoNotificacion documentoExternoNotificacion) throws JAXBException{
		DOCUMENTOEXTERNONOTIFICACION documentoExternoNotificacionJAXB = null;
		if (documentoExternoNotificacion != null){
			documentoExternoNotificacionJAXB =(new ObjectFactory()).createDOCUMENTOEXTERNONOTIFICACION();		
			documentoExternoNotificacionJAXB.setNOMBRE(documentoExternoNotificacion.getNombre());
			documentoExternoNotificacionJAXB.setURL(documentoExternoNotificacion.getUrl());			
		}
		return documentoExternoNotificacionJAXB;		
	}
	
	
	public static DocumentoExternoNotificacion fromJAXB(DOCUMENTOEXTERNONOTIFICACION documentoExternoNotificacionJAXB) throws JAXBException{
		DocumentoExternoNotificacion documentoExternoNotificacion = null;
		if (documentoExternoNotificacionJAXB != null){
			documentoExternoNotificacion = new DocumentoExternoNotificacion();
			documentoExternoNotificacion.setNombre(documentoExternoNotificacionJAXB.getNOMBRE());
			documentoExternoNotificacion.setUrl(documentoExternoNotificacionJAXB.getURL());				
		}
		return documentoExternoNotificacion;		
	}

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

}
