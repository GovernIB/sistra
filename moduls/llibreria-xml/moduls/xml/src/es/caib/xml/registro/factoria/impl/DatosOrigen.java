package es.caib.xml.registro.factoria.impl;

import java.util.Date;
import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;

/** Objeto de DatosOrigen que encapsula el nodo XML DATOS_ORIGEN de los documentos
 * XML de justificante. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class DatosOrigen extends NodoRegistroBase {
	
	// Variables miembro
	private String codigoEntidadRegistralOrigen;
	private String numeroRegistro;
	private Date fechaEntradaRegistro;
	private Character tipoRegistro;
	
	DatosOrigen (){}	
				
	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#getCodigoEntidadRegistralOrigen()
	 */
	public String getCodigoEntidadRegistralOrigen() {
		return codigoEntidadRegistralOrigen;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#setCodigoEntidadRegistralOrigen(java.lang.String)
	 */
	public void setCodigoEntidadRegistralOrigen(
			String codigoEntidadRegistralOrigen)
			throws EstablecerPropiedadException {
		
		// Validar maxima longitud de campo, campo requerido
		validaLongitudCampo("DatosOrigen", "CodigoEntidadRegistralOrigen", codigoEntidadRegistralOrigen, ConstantesAsientoXML.DATOSORIGEN_CODIGOENTIDADREGISTRAL_MAX);
		validaCampoObligatorio("DatosOrigen", "EntidadRegistralOrigen", codigoEntidadRegistralOrigen);
		
		this.codigoEntidadRegistralOrigen = codigoEntidadRegistralOrigen;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#getNumeroRegistro()
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#setNumeroRegistro(java.lang.String)
	 */
	public void setNumeroRegistro(String numeroRegistro)
			throws EstablecerPropiedadException {
		// Validar maxima longitud de campo, campo requerido
		validaLongitudCampo("DatosOrigen", "NumeroRegistro", numeroRegistro, ConstantesAsientoXML.NUMEROREGISTRO_MAX);
		validaCampoNoNulo ("DatosOrigen", "NumeroRegistro", numeroRegistro);		
		// validaFormatoCampo ("DatosOrigen", "NumeroRegistro", numeroRegistro, "^\\d\\d/\\d\\d\\d\\d\\d/\\d\\d\\d\\d$");

		this.numeroRegistro = numeroRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#getFechaEntradaRegistro()
	 */
	public Date getFechaEntradaRegistro() {
		return fechaEntradaRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#setFechaEntradaRegistro(java.util.Date)
	 */
	public void setFechaEntradaRegistro(Date fechaEntradaRegistro)
			throws EstablecerPropiedadException {
		
		this.fechaEntradaRegistro = fechaEntradaRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#getTipoRegistro()
	 */
	public Character getTipoRegistro() {			
		return tipoRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosOrigen#setTipoRegistro(char)
	 */
	public void setTipoRegistro(Character tipoRegistro)
			throws EstablecerPropiedadException {
		// Validar maxima longitud de campo, campo requerido, campo en lista valores
		Character valoresPermitidos[] = {
				new Character (ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA),
				new Character (ConstantesAsientoXML.TIPO_REGISTRO_SALIDA),
				new Character (ConstantesAsientoXML.TIPO_ACUSE_RECIBO),
				new Character (ConstantesAsientoXML.TIPO_ENVIO),
				new Character (ConstantesAsientoXML.TIPO_PREREGISTRO),
				new Character (ConstantesAsientoXML.TIPO_PREENVIO)
		};
		
		validaLongitudCampo("DatosOrigen", "TipoRegistro", tipoRegistro, ConstantesAsientoXML.DATOSORIGEN_TIPOREGISTRO_MAX);
		validaCampoObligatorio("DatosOrigen", "TipoRegistro", tipoRegistro);
		validaCampoConListaValores("DatosOrigen", "TipoRegistro", tipoRegistro, valoresPermitidos);

		this.tipoRegistro = tipoRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		String codigoEntidadRegistralOrigen = null;
		String numeroRegistro = null;		
		String tipoRegistroRaw = null;
		
		// Comprobar codigo entidad registral origen
		codigoEntidadRegistralOrigen = getCodigoEntidadRegistralOrigen ();
		if ( (codigoEntidadRegistralOrigen == null) || (codigoEntidadRegistralOrigen.trim().equals ("")))
			throw new CampoObligatorioException ("DatosOrigen", "CodigoEntidadRegistralOrigen");
		
		// Comprobar numero registro
		numeroRegistro = getNumeroRegistro ();
		if (numeroRegistro == null) throw new CampoObligatorioException ("DatosOrigen", "NumeroRegistro");
		
		// Comprobar fecha entrada registro		
		//if (getFechaEntradaRegistro() == null) throw new CampoObligatorioException ("DatosOrigen", "FechaEntradaRegistro");
		
		// Comprobar tipo de registro		
		if (getTipoRegistro() == null) throw new CampoObligatorioException ("DatosOrigen", "TipoRegistro");								
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof DatosOrigen){
			
			if (obj == null) return false;
			
			DatosOrigen dOrigen = (DatosOrigen) obj;
			
			// Comprobar código entidad registral origen
			String codEntRegOrigen = getCodigoEntidadRegistralOrigen ();
			String codEntRegOrigenExt = dOrigen.getCodigoEntidadRegistralOrigen ();
			
			if ((codEntRegOrigen != null) || (codEntRegOrigenExt != null))
				if ( (codEntRegOrigenExt != null) && (codEntRegOrigen != null) ){
					if (!codEntRegOrigen.equals (codEntRegOrigenExt)) return false;
				}
				else
					if ((codEntRegOrigen != null) || (codEntRegOrigenExt != null)) return false;
			
			// Comprobar fecha entrada registro
			java.util.Date fechaEntReg = getFechaEntradaRegistro ();
			java.util.Date fechaEntRegExt = dOrigen.getFechaEntradaRegistro ();
			
			if ((fechaEntReg != null) || (fechaEntRegExt != null))
				if ( (fechaEntRegExt != null) && (fechaEntReg != null) ){
					if (!fechaEntReg.equals (fechaEntRegExt)) return false;
				}
				else
					if ((fechaEntReg != null) || (fechaEntRegExt != null)) return false;
			
			
			// Comprobar número registro
			String numRegistro = getNumeroRegistro ();
			String numRegistroExt = dOrigen.getNumeroRegistro ();
			
			if ((numRegistro != null) || (numRegistroExt != null))
				if ( (numRegistroExt != null) && (numRegistro != null) ){
					if (!numRegistro.equals (numRegistroExt)) return false;
				}
				else
					if ((numRegistro != null) || (numRegistroExt != null)) return false;
			
			
			// Comprobar tipo registro
			if (getTipoRegistro () != dOrigen.getTipoRegistro ()) return false;
									
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

}
