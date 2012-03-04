package es.caib.xml.datospropios.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;


/** Objeto de Dato que encapsula el nodo XML DATOS_PROPIOS de los documentos
 * XML de datos propios. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class DatosPropios extends NodoBaseDatosPropios  {
			
	private Instrucciones instrucciones;
	private Solicitud solicitud;
	
	DatosPropios (){
		instrucciones = null;
		solicitud = null;
	}
	

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.DatosPropios#getInstrucciones()
	 */
	public Instrucciones getInstrucciones() {			
		return instrucciones;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.DatosPropios#setInstrucciones(es.caib.xml.datospropios.factoria.Instrucciones)
	 */
	public void setInstrucciones(Instrucciones instrucciones)
			throws EstablecerPropiedadException {
		
		validaCampoObligatorio("DatosPropios", "Instrucciones", instrucciones);
		
		this.instrucciones = instrucciones;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.DatosPropios#getSolicitud()
	 */
	public Solicitud getSolicitud() {			
		return solicitud;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.DatosPropios#setSolicitud(es.caib.xml.datospropios.factoria.Solicitud)
	 */
	public void setSolicitud(Solicitud solicitud)
			throws EstablecerPropiedadException {
													
		this.solicitud = solicitud;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {		
		Solicitud solicitud = getSolicitud ();
		
		if (solicitud != null) solicitud.comprobarDatosRequeridos();
		
		Instrucciones instrucciones = getInstrucciones ();
		validaCampoObligatorio("DatosPropios", "Instrucciones", instrucciones);
		instrucciones.comprobarDatosRequeridos();		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof DatosPropios){
			
			if (obj == null) return false;
			
			DatosPropios datosPropios = (DatosPropios) obj;
			
			// Comprobar instrucciones
			objetosIguales(getInstrucciones(), datosPropios.getInstrucciones());
			
			// Comprobar Solicitud
			Solicitud solicitud = getSolicitud ();
			Solicitud solicitudExt = datosPropios.getSolicitud ();
			
			if ((solicitud != null) || (solicitudExt != null))
				if ( (solicitudExt != null) && (solicitud != null) ){
					if (!solicitud.equals (solicitudExt)) return false;
				}
				else
					if ((solicitud != null) || (solicitudExt != null)) return false;
			
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}	
}
