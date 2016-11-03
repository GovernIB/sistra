package es.caib.xml.registro.factoria.impl;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;

/** Objeto de DatosDestino que encapsula el nodo XML DATOS_DESTINO de los documentos
 * XML de justificante. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class DatosDestino extends NodoRegistroBase {
	// Ctes de maxima longitud de campos
	private static final int MAX_LON_CODIGO_ENTIDAD_REGISTRAL_DESTINO = 2;
	private static final int MAX_LON_DECODIFICACION_ENTIDAD_REGISTRAL_DESTINO = 250;
	
	DatosDestino(){}
	
	private String codigoEntidadRegistralDestino;
	private String decodificacionEntidadRegistralDestino;

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosDestino#getCodigoEntidadRegistralDestino()
	 */
	public String getCodigoEntidadRegistralDestino() {		
		return this.codigoEntidadRegistralDestino;
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosDestino#getCodigoEntidadRegistralDestino()
	 */
	public void setCodigoEntidadRegistralDestino(String codigoEntidadRegistralDestino) {		
		this.codigoEntidadRegistralDestino = codigoEntidadRegistralDestino;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosDestino#getDecodificacionEntidadRegistralDestino()
	 */
	public String getDecodificacionEntidadRegistralDestino() {		
		return this.decodificacionEntidadRegistralDestino;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosDestino#setDecodificacionEntidadRegistralDestino(java.lang.String)
	 */
	public void setDecodificacionEntidadRegistralDestino(String decodificacion) throws EstablecerPropiedadException {
		// Validar maxima longitud de campo
		validaLongitudCampo("DatosDestino", "DecodificacionEntidadRegistralDestino", decodificacion, MAX_LON_DECODIFICACION_ENTIDAD_REGISTRAL_DESTINO);
		
		this.decodificacionEntidadRegistralDestino = decodificacion;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		String codigoEntidadRegistralDestino = getCodigoEntidadRegistralDestino ();
		
		if ( (codigoEntidadRegistralDestino == null) || (codigoEntidadRegistralDestino.trim().equals("")))
			throw new CampoObligatorioException ("DatosDestino", "CodigoEntidadRegistralDestino");		
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof DatosDestino){
			
			if (obj == null) return false;
			
			DatosDestino datosDestino = (DatosDestino) obj;
			
			// Comprobar codigo entidad registral destino
			String codEntRegDestino = getCodigoEntidadRegistralDestino ();
			String codEntRegDestinoExt = datosDestino.getCodigoEntidadRegistralDestino ();
			
			if ((codEntRegDestino != null) || (codEntRegDestinoExt != null))
				if ( (codEntRegDestinoExt != null) && (codEntRegDestino != null) ){
					if (!codEntRegDestino.equals (codEntRegDestinoExt)) return false;
				}
				else
					if ((codEntRegDestino != null) || (codEntRegDestinoExt != null)) return false;
			
			// Comprobar decodificacion entidad registral destino
			String deEntRegDestino = getDecodificacionEntidadRegistralDestino();
			String deEntRegDestinoExt = datosDestino.getDecodificacionEntidadRegistralDestino();
			
			if ((deEntRegDestino != null) || (deEntRegDestinoExt != null))
				if ( (deEntRegDestinoExt != null) && (deEntRegDestino != null) ){
					if (!deEntRegDestino.equals (deEntRegDestinoExt)) return false;
				}
				else
					if ((deEntRegDestino != null) || (deEntRegDestinoExt != null)) return false;
						
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

}
