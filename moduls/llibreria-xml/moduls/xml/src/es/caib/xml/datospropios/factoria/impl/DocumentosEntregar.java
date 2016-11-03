package es.caib.xml.datospropios.factoria.impl;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;

/** Objeto de Dato que encapsula el nodo XML DOCUMENTOS_ENTREGAR de los documentos
 * XML de datos propios. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class DocumentosEntregar extends NodoBaseDatosPropios {
	
	private List lstDocumento;
	
	DocumentosEntregar (){
		lstDocumento = new ArrayList ();
	}
			
	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.DocumentosEntregar#getDocumento()
	 */
	public List getDocumento() {			
		return lstDocumento;
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		Iterator itListaDocumentos = lstDocumento.iterator();
		
		boolean existeJustificante=false,existeFormularioJustificante=false;
		while (itListaDocumentos.hasNext()){
			Object obj =  itListaDocumentos.next();
			
			if (obj instanceof Documento){ 
				((Documento) obj).comprobarDatosRequeridos();
				if (((Documento) obj).getTipo().charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE) existeJustificante = true;
				if (((Documento) obj).getTipo().charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE) existeFormularioJustificante = true;
			}
			else {
				// Error, un Documento no tiene el tipo esperado!
				throw new EstablecerPropiedadException ("El objeto no es del tipo Documento", "DocumentosEntregar", "Documento", obj);
			}
		}		
		
		if (existeFormularioJustificante && existeJustificante) throw new EstablecerPropiedadException ("No puede haber un justificante y un formulario justificante", "DocumentosEntregar", "Documento", "");
		if (!existeFormularioJustificante && !existeJustificante) throw new EstablecerPropiedadException ("No hay justificante ni formulario justificante", "DocumentosEntregar", "Documento", "");		
	}
		
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof DocumentosEntregar){
			
			if (obj == null) return false;
			
			DocumentosEntregar documentosEnt = (DocumentosEntregar) obj;
			
			List documentos = getDocumento();
			List documentosExt = documentosEnt.getDocumento();
			Iterator itDocumentos = documentos.iterator();
			Iterator itDocumentosExt = documentosExt.iterator();
			
			if (itDocumentos.hasNext() != itDocumentosExt.hasNext()) return false;
			
			while (itDocumentos.hasNext()){
				Documento documento = (Documento) itDocumentos.next();
				Documento documentoExt = (Documento) itDocumentosExt.next();
				
				if (!documento.equals (documentoExt)) return false;
				if (itDocumentos.hasNext() != itDocumentosExt.hasNext()) return false;
			}												
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

}
