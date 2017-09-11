package es.caib.xml.registro.factoria.impl;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;

/** Objeto de DatosInteresado que encapsula el nodo XML DATOS_INTERESADO de los documentos
 * XML de justificante. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class DatosInteresado extends NodoRegistroBase {
	
	// Variables miembro
	private Character 			nivelAutenticacion;
	private String 				usuarioSeycon;
	private String 				tipoInteresado;
	private Character			tipoIdentificacion;
	private String 				numeroIdentificacion;
	private String 				formatoDatosInteresado;
	private String 				identificacionInteresado;
	private IdentificacionInteresadoDesglosada identificacionInteresadoDesglosada;
	public IdentificacionInteresadoDesglosada getIdentificacionInteresadoDesglosada() {
		return identificacionInteresadoDesglosada;
	}

	public void setIdentificacionInteresadoDesglosada(
			IdentificacionInteresadoDesglosada identificacionInteresadoDesglosada) {
		this.identificacionInteresadoDesglosada = identificacionInteresadoDesglosada;
	}

	private DireccionCodificada direccionCodificada;
	
	
	DatosInteresado(){}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#getTipoInteresado()
	 */
	public String getTipoInteresado() {
		return this.tipoInteresado;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#setTipoInteresado(java.lang.String)
	 */
	public void setTipoInteresado(String tipoInteresado)
			throws EstablecerPropiedadException {
		
		// Avlidar longitud maxima, campo obligatrio y valor en lista
		String valoresPermitidos[] = {ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE, ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO, ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO};
		validaLongitudCampo("DatosInteresado", "TipoInteresado", tipoInteresado, ConstantesAsientoXML.DATOSINTERESADO_TIPOINTERESADO_MAX);
		validaCampoObligatorio ("DatosInteresado", "TipoInteresado", tipoInteresado);
		validaCampoConListaValores ("DatosInteresado", "TipoInteresado", tipoInteresado, valoresPermitidos);
				
		this.tipoInteresado = tipoInteresado;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#getTipoIdentificacion()
	 */
	public Character getTipoIdentificacion() {
		return this.tipoIdentificacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#setTipoIdentificacion(java.lang.Character)
	 */
	public void setTipoIdentificacion(Character tipoIdentificacion)
			throws EstablecerPropiedadException {
		
		// Validar longitud de campo y valor en lista
		Character valoresPermitidos[] = {new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF),				
				new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF),
				new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE),
				new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_PASAPORTE)};
		
		validaLongitudCampo("DatosInteresado", "TipoIdentificacion", tipoIdentificacion, ConstantesAsientoXML.DATOSINTERESADO_TIPOIDENTIFICACION_MAX);
		validaCampoConListaValores ("DatosInteresado", "TipoIdentificacion", tipoIdentificacion, valoresPermitidos);
		
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#getNumeroIdentificacion()
	 */
	public String getNumeroIdentificacion() {
		return this.numeroIdentificacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#setNumeroIdentificacion(java.lang.String)
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) throws EstablecerPropiedadException {
		// Validar longitud de campo
		validaLongitudCampo("DatosInteresado", "NumeroIdentificacion", numeroIdentificacion, ConstantesAsientoXML.DATOSINTERESADO_NUMERO_IDENTIFICACION_MAX);
		
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#getFormatoDatosInteresado()
	 */
	public String getFormatoDatosInteresado() {
		return this.formatoDatosInteresado;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#setFormatoDatosInteresado(java.lang.String)
	 */
	public void setFormatoDatosInteresado(String formatoDatosInteresado)
			throws EstablecerPropiedadException {
		// Validar longitud de campo, campo obligatorio y valor en lista
		String valoresPermitidos[] = {ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM};
		validaLongitudCampo ("DatosInteresado", "FormatoDatosInteresado", formatoDatosInteresado, ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_MAX);
		validaCampoObligatorio ("DatosInteresado", "FormatoDatosInteresado", formatoDatosInteresado);
		validaCampoConListaValores ("DatosInteresado", "FormatoDatosInteresado", formatoDatosInteresado, valoresPermitidos);
		
		this.formatoDatosInteresado = formatoDatosInteresado;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#getIdentificacionInteresado()
	 */
	public String getIdentificacionInteresado() {
		return this.identificacionInteresado;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#setIdentificacionInteresado(java.lang.String)
	 */
	public void setIdentificacionInteresado(String identificacionInteresado)
			throws EstablecerPropiedadException {
		
		// Validar longitud del campo y campo obligatorio 
		validaLongitudCampo("DatosInteresado", "IdentificacionInteresado", identificacionInteresado, ConstantesAsientoXML.DATOSINTERESADO_IDENTIFICACIONINTERESADO_MAX);
		//validaCampoObligatorio ("DatosInteresado", "IdentificacionInteresado", identificacionInteresado);
		
		this.identificacionInteresado = identificacionInteresado;
	}
		
	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#getDireccionCodificada()
	 */
	public DireccionCodificada getDireccionCodificada() {			
		return direccionCodificada;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosInteresado#setDireccionCodificada(es.caib.xml.registro.factoria.DireccionCodificada)
	 */
	public void setDireccionCodificada(DireccionCodificada direccionCodificada) throws EstablecerPropiedadException {				
		this.direccionCodificada = direccionCodificada;
	}
	
	public Character getNivelAutenticacion() {				
		return this.nivelAutenticacion;		
	}

	public void setNivelAutenticacion(Character nivelAutenticacion) throws EstablecerPropiedadException {		
		Character valoresPermitidos[] = {	new Character (ConstantesAsientoXML.NIVEL_AUTENTICACION_ANONIMO),
									  		new Character (ConstantesAsientoXML.NIVEL_AUTENTICACION_CERTIFICADO),
									  		new Character (ConstantesAsientoXML.NIVEL_AUTENTICACION_USUARIO)};
		validaCampoConListaValores ("DatosInteresado", "NivelAutenticacion", nivelAutenticacion, valoresPermitidos);		
		
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public String getUsuarioSeycon() {
		return this.usuarioSeycon;
	}

	public void setUsuarioSeycon(String usuarioSeycon) throws EstablecerPropiedadException {
		this.usuarioSeycon = usuarioSeycon;	
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		String tipoInteresado = null;
		String formatoDatosInteresado = null;
		String identificacionInteresado = null;
		String procedenciaGeografica = null;
		
		tipoInteresado = getTipoInteresado ();
		if ( (tipoInteresado == null) || (tipoInteresado.trim().equals ("")) )
			throw new CampoObligatorioException ("DatosInteresado", "TipoInteresado");
		
		formatoDatosInteresado = getFormatoDatosInteresado ();
		if ( (formatoDatosInteresado == null) || (formatoDatosInteresado.trim().equals ("")) )
			throw new CampoObligatorioException ("DatosInteresado", "FormatoDatosInteresado");
						
		identificacionInteresado = getIdentificacionInteresado ();
		if ( (identificacionInteresado == null) || (identificacionInteresado.trim().equals ("")) )
			throw new CampoObligatorioException ("DatosInteresado", "IdentificacionInteresado");
		
		identificacionInteresadoDesglosada = getIdentificacionInteresadoDesglosada();
		identificacionInteresadoDesglosada.comprobarDatosRequeridos();
							
	}
			
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof DatosInteresado){
			
			if (obj == null) return false;
			
			DatosInteresado dInteresado = (DatosInteresado) obj;	
			
			// Comprobar id desglosada
			IdentificacionInteresadoDesglosada idDesglosada = getIdentificacionInteresadoDesglosada();
			IdentificacionInteresadoDesglosada idDesglosadaExt = dInteresado.getIdentificacionInteresadoDesglosada();
				
			if ((idDesglosada != null) || (idDesglosadaExt != null))
				if ( (idDesglosadaExt != null) && (idDesglosada != null) ){
					if (!idDesglosada.equals (idDesglosadaExt)) return false;
				}
				else
					if ((idDesglosada != null) || (idDesglosadaExt != null)) return false;
			
			// Comprobar dirección codificada
			DireccionCodificada dirCodificada = getDireccionCodificada ();
			DireccionCodificada dirCodificadaExt = dInteresado.getDireccionCodificada();
				
			if ((dirCodificada != null) || (dirCodificadaExt != null))
				if ( (dirCodificadaExt != null) && (dirCodificada != null) ){
					if (!dirCodificada.equals (dirCodificadaExt)) return false;
				}
				else
					if ((dirCodificada != null) || (dirCodificadaExt != null)) return false;			
			
			// Comprobar formato datos interesado
			String fmtDatosInt = getFormatoDatosInteresado ();
			String fmtDatosIntExt = dInteresado.getFormatoDatosInteresado ();
			
			if ((fmtDatosInt != null) || (fmtDatosIntExt != null))
				if ( (fmtDatosIntExt != null) && (fmtDatosInt != null) ){
					if (!fmtDatosInt.equals (fmtDatosIntExt)) return false;
				}
				else
					if ((fmtDatosInt != null) || (fmtDatosIntExt != null)) return false;
			
			
			// Comprobar identificación interesado
			String idInteresado = getIdentificacionInteresado ();
			String idInteresadoExt = dInteresado.getIdentificacionInteresado ();
			
			if ((idInteresado != null) || (idInteresadoExt != null))
				if ( (idInteresadoExt != null) && (idInteresado != null) ){
					if (!idInteresado.equals (idInteresadoExt)) return false;
				}
				else
					if ((idInteresado != null) || (idInteresadoExt != null)) return false;
			
			
			// Comprobar numero identificacion
			String numeroId = getNumeroIdentificacion ();
			String numeroIdExt = getNumeroIdentificacion ();
			
			if ((numeroId != null) || (numeroIdExt != null))
				if ( (numeroIdExt != null) && (numeroId != null) ){
					if (!numeroId.equals (numeroIdExt)) return false;
				}
				else
					if ((numeroId != null) || (numeroIdExt != null)) return false;
			
												
			// Comprobar tipo de identificación
			Character tipoIdentificacion = getTipoIdentificacion ();
			Character tipoIdentificacionExt = dInteresado.getTipoIdentificacion ();
			
			if ((tipoIdentificacion != null) || (tipoIdentificacionExt != null))
				if ( (tipoIdentificacionExt != null) && (tipoIdentificacion != null) ){
					if (!tipoIdentificacion.equals (tipoIdentificacionExt)) return false;
				}
				else
					if ((tipoIdentificacion != null) || (tipoIdentificacionExt != null)) return false;
			
			// Comprobar tipo de interesado
			String tipoInteresado = getTipoInteresado ();
			String tipoInteresadoExt = dInteresado.getTipoInteresado ();
			
			if ((tipoInteresado != null) || (tipoInteresadoExt != null))
				if ( (tipoInteresadoExt != null) && (tipoInteresado != null) ){
					if (!tipoInteresado.equals (tipoInteresadoExt)) return false;
				}
				else
					if ((tipoInteresado != null) || (tipoInteresadoExt != null)) return false;						
			
			
			if (!objetosIguales (getNivelAutenticacion(), dInteresado.getNivelAutenticacion())) return false;
			
			if (!objetosIguales (getUsuarioSeycon(), dInteresado.getUsuarioSeycon())) return false;
			// OK los dos objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}		
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode (){					
		return toString().hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString (){
		StringBuffer str = new StringBuffer ("DatosInteresado:");
		
		// Tipo de interesado	
		str.append ("TipoInteresado-" + getTipoInteresado () + ";");
		
		// Tipo de identificación
		Character tipoIdentificacion = getTipoIdentificacion ();
		str.append ("TipoIdentificacion-" + ((tipoIdentificacion != null) ?  tipoIdentificacion.toString() : "") + ";");
		
		// Número de identificación
		str.append ("NumeroIdentificacion-" + getNumeroIdentificacion() + ";");
		
		// Formato datos interesado
		str.append ("FormatoDatosInteresado-" + getFormatoDatosInteresado () +  ";");
		
		// Identificación inetersado
		str.append ("IdentificacionInteresado-" + getIdentificacionInteresado() + ";");

		// Identificacion interesado desglosada
		IdentificacionInteresadoDesglosada identDesglosada = getIdentificacionInteresadoDesglosada ();
		str.append ("IdentificacionInteresadoDesglosada-" + ( (identDesglosada != null) ? identDesglosada.toString() : "") +  ";");
								
		// Dirección codificada
		DireccionCodificada direccion = getDireccionCodificada ();
		str.append ("DireccionCodificada-" + ( (direccion != null) ? direccion.toString() : "") +  ";");
		
		// Nivel autenticacion
		Character nivelAutenticacion = getNivelAutenticacion ();
		str.append ("NivelAutenticacion-" + ( (nivelAutenticacion != null) ? nivelAutenticacion.toString() : "") +  ";");
		
		// Usuario seycon
		String usuarioSeycon = getUsuarioSeycon ();
		str.append ("UsuarioSeycon-" + ( (usuarioSeycon != null) ? usuarioSeycon : "") +  ";");
		
		return str.toString();
	}

}
