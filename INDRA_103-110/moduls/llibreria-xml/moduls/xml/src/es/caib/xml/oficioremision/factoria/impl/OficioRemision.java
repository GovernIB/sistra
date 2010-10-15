package es.caib.xml.oficioremision.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;

/** Objeto de Instrucciones que encapsula el nodo XML INSTRUCCIONES de los documentos
 * XML de oficio remision. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * Campos requeridos: TextoInstrucciones
 * 
 * @author magroig
 *
 */
public class OficioRemision extends NodoBaseOficioRemision  {
			
	private String titulo;
	private String texto;
	
	
	OficioRemision (){
		titulo = null;
		texto = null;
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio("AvisoNotificacion", "Titulo", this.getTitulo());
		validaCampoObligatorio("AvisoNotificacion", "Texto", this.getTexto());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof OficioRemision){
			
			if (obj == null) return false;
			
			OficioRemision inst = (OficioRemision) obj;
						
			if (!objetosIguales (getTitulo(), inst.getTitulo())) return false;
			if (!objetosIguales (getTexto(), inst.getTexto())) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
