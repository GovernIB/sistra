package es.caib.xml.registro.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;

/** Objeto de DireccionCodificada que encapsula el nodo XML DIRECCION_CODIFICADA de los documentos
 * XML de justificante. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class DireccionCodificada extends NodoRegistroBase {
	//Ctes de maxima longitud de campos
	private static final int MAX_LON_CODIGO_PROVINCIA = 2;
	private static final int MAX_LON_NOMBRE_PROVINCIA = 30;
	private static final int MAX_LON_CODIGO_MUNICIPIO = 3;
	private static final int MAX_LON_NOMBRE_MUNICIPIO = 30;
	private static final int MAX_LON_CODIGO_POBLACION = 2;
	private static final int MAX_LON_NOMBRE_POBLACION = 30;
	private static final int MAX_LON_DOMICILIO = 45;
	private static final int MAX_LON_CODIGO_POSTAL = 5;
	private static final int MAX_LON_TELEFONO = 15;
	private static final int MAX_LON_FAX = 15;
	private static final int MAX_LON_PAIS_ORIGEN = 30;
		
	private String codigoProvincia;
	private String nombreProvincia;
	private String codigoMunicipio;
	private String nombreMunicipio;
	private String codigoPoblacion;
	private String nombrePoblacion;
	private String domicilio;
	private String codigoPostal;
	private String telefono;
	private String fax;
	private String paisOrigen;
	
	DireccionCodificada(){}	
	

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getCodigoProvincia()
	 */
	public String getCodigoProvincia() {		
		return this.codigoProvincia;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setCodigoProvincia(java.lang.String)
	 */
	public void setCodigoProvincia(String codigoProvincia)
			throws EstablecerPropiedadException {
		
		validaLongitudCampo ("DireccionCodificada", "CodigoProvincia", codigoProvincia, MAX_LON_CODIGO_PROVINCIA);
		
		this.codigoProvincia = codigoProvincia;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getNombreProvincia()
	 */
	public String getNombreProvincia() {
		return this.nombreProvincia;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setNombreProvincia(java.lang.String)
	 */
	public void setNombreProvincia(String nombreProvincia)
			throws EstablecerPropiedadException {
		
		validaLongitudCampo ("DireccionCodificada", "NombreProvincia", nombreProvincia, MAX_LON_NOMBRE_PROVINCIA);

		this.nombreProvincia = nombreProvincia;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getCodigoMunicipio()
	 */
	public String getCodigoMunicipio() {
		return this.codigoMunicipio;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setCodigoMunicipio(java.lang.String)
	 */
	public void setCodigoMunicipio(String codigoMunicipio)
			throws EstablecerPropiedadException {
		
		validaLongitudCampo ("DireccionCodificada", "CodigoMunicipio", codigoMunicipio, MAX_LON_CODIGO_MUNICIPIO);
		
		this.codigoMunicipio = codigoMunicipio;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getNombreMunicipio()
	 */
	public String getNombreMunicipio() {
		return this.nombreMunicipio;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setNombreMunicipio(java.lang.String)
	 */
	public void setNombreMunicipio(String nombreMunicipio) throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "NombreMunicipio", nombreMunicipio, MAX_LON_NOMBRE_MUNICIPIO);
		
		this.nombreMunicipio = nombreMunicipio;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getCodigoPoblacion()
	 */
	public String getCodigoPoblacion() {
		return this.codigoPoblacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setCodigoPoblacion(java.lang.String)
	 */
	public void setCodigoPoblacion(String codigoPoblacion)
			throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "CodigoPoblacion", codigoPoblacion, MAX_LON_CODIGO_POBLACION);
		
		this.codigoPoblacion = codigoPoblacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getNombrePoblacion()
	 */
	public String getNombrePoblacion() {
		return this.nombrePoblacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setNombrePoblacion(java.lang.String)
	 */
	public void setNombrePoblacion(String nombrePoblacion)
			throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "NombrePoblacion", nombrePoblacion, MAX_LON_NOMBRE_POBLACION);
		
		this.nombrePoblacion = nombrePoblacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getDomicilio()
	 */
	public String getDomicilio() {
		return this.domicilio;
	}

	
	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setDomicilio(java.lang.String)
	 */
	public void setDomicilio(String domicilio) throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "Domicilio", domicilio, MAX_LON_DOMICILIO);
		
		this.domicilio = domicilio;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getCodigoPostal()
	 */
	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setCodigoPostal(java.lang.String)
	 */
	public void setCodigoPostal(String codigoPostal)
			throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "CodigoPostal", codigoPostal, MAX_LON_CODIGO_POSTAL);
		
		this.codigoPostal = codigoPostal;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getTelefono()
	 */
	public String getTelefono() {
		return this.telefono;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setTelefono(java.lang.String)
	 */
	public void setTelefono(String telefono)
			throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "Telefono", telefono, MAX_LON_TELEFONO);
		
		this.telefono = telefono;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getFAX()
	 */
	public String getFAX() {
		return this.fax;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setFAX(java.lang.String)
	 */
	public void setFAX(String FAX) throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "FAX", FAX, MAX_LON_FAX);
		
		this.fax = FAX;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#getPaisOrigen()
	 */
	public String getPaisOrigen() {
		return this.paisOrigen;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DireccionCodificada#setPaisOrigen(java.lang.String)
	 */
	public void setPaisOrigen(String paisOrigen)
			throws EstablecerPropiedadException {
		validaLongitudCampo ("DireccionCodificada", "PaisOrigen", paisOrigen, MAX_LON_PAIS_ORIGEN);

		this.paisOrigen = paisOrigen;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {		
		// No hay ningun campo requerido		
	}

		
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof DireccionCodificada){
			
			if (obj == null) return false;
			
			DireccionCodificada direccion = (DireccionCodificada) obj;
			
			// Comprobar código municipio
			String codigoMunicipio = getCodigoMunicipio ();
			String codigoMunicipioExt = direccion.getCodigoMunicipio ();
			
			if ((codigoMunicipio != null) || (codigoMunicipioExt != null))
				if ( (codigoMunicipioExt != null) && (codigoMunicipio != null) ){
					if (!codigoMunicipio.equals (codigoMunicipioExt)) return false;
				}
				else
					if ((codigoMunicipio != null) || (codigoMunicipioExt != null)) return false;
			
			// Comprobar código población
			String codigoPoblacion = getCodigoPoblacion ();
			String codigoPoblacionExt = direccion.getCodigoPoblacion ();
			
			if ((codigoPoblacion != null) || (codigoPoblacionExt != null))
				if ( (codigoPoblacionExt != null) && (codigoPoblacion != null) ){
					if (!codigoPoblacion.equals (codigoPoblacionExt)) return false;
				}
				else
					if ((codigoPoblacion != null) || (codigoPoblacionExt != null)) return false;
			
			// Comprobar código postal
			String codigoPostal = getCodigoPostal ();
			String codigoPostalExt = direccion.getCodigoPostal ();
			
			if ((codigoPostal != null) || (codigoPostalExt != null))
				if ( (codigoPostalExt != null) && (codigoPostal != null) ){
					if (!codigoPostal.equals (codigoPostalExt)) return false;
				}
				else
					if ((codigoPostal != null) || (codigoPostalExt != null)) return false;

			// Comprobar código provincia
			String codigoProvincia = getCodigoProvincia ();
			String codigoProvinciaExt = direccion.getCodigoProvincia ();
			
			if ((codigoProvincia != null) || (codigoProvinciaExt != null))
				if ( (codigoProvinciaExt != null) && (codigoProvincia != null) ){
					if (!codigoProvincia.equals (codigoProvinciaExt)) return false;
				}
				else
					if ((codigoProvincia != null) || (codigoProvinciaExt != null)) return false;
			
			
			// Comprobar domicilio
			String domicilio = getDomicilio ();
			String domicilioExt = direccion.getDomicilio ();
			
			if ((domicilio != null) || (domicilioExt != null))
				if ( (domicilioExt != null) && (domicilio != null) ){
					if (!domicilio.equals (domicilioExt)) return false;
				}
				else
					if ((domicilio != null) || (domicilioExt != null)) return false;
			
			// Comprobar fax
			String fax = getFAX ();
			String faxExt = direccion.getFAX ();
			
			if ((fax != null) || (faxExt != null))
				if ( (faxExt != null) && (fax != null) ){
					if (!fax.equals (faxExt)) return false;
				}
				else
					if ((fax != null) || (faxExt != null)) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString (){
		StringBuffer str = new StringBuffer ("DireccionCodificada:");
		
		// Codigo Provincia
		str.append ("CodigoProvincia-" + getCodigoProvincia () + ";");
		
		// Nombre Provincia
		str.append ("NombreProvincia-" + getNombreProvincia () + ";");
		
		// Código Municipio
		str.append ("CodigoMunicipio-" + getCodigoMunicipio () + ";");
		
		// Nombre Municipio
		str.append ("NombreMunicipio-" + getNombreMunicipio () + ";");
		
		// Código Poblacion
		str.append ("CodigoPoblacion-" + getCodigoPoblacion () + ";");
		
		// Nombre Población
		str.append ("NombrePoblacion-" + getNombrePoblacion () + ";");
		
		// Domicilio
		str.append ("Domicilio-" + getDomicilio () + ";");
		
		// Código Postal
		str.append ("CodigoPostal-" + getCodigoPostal () + ";");
		
		// Teléfono
		str.append ("Telefono-" + getTelefono () + ";");
		
		// FAX
		str.append ("FAX-" + getFAX () + ";");
		
		// País Origen
		str.append ("PaisOrigen-" + getPaisOrigen () + ";");
		 				
		
		return str.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode (){
		return toString ().hashCode();
	}

}
