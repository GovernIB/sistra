package es.caib.xml.registro.factoria.impl;

import java.util.Date;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.ValorFueraListaValoresPermitidosException;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;

/** Objeto de DatosAsunto que encapsula el nodo XML DATOS_ASUNTO de los documentos
 * XML de justificante. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class DatosAsunto extends NodoRegistroBase {
	
	private Date fechaAsunto;
	private String idiomaAsunto;
	private String tipoAsunto;
	private String extractoAsunto;
	private String codigoOrganoDestino;
	private String descripcionOrganoDestino;
	private String codigoUnidadAdministrativa;
	private String identificadorTramite;
	
	
	public DatosAsunto (){}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#getIdiomaAsunto()
	 */
	public String getIdiomaAsunto() {
		return this.idiomaAsunto;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#setIdiomaAsunto(java.lang.String)
	 */
	public void setIdiomaAsunto(String idiomaAsunto)
			throws EstablecerPropiedadException {
		// validar longitud de camo, campo requerido, campo en lista de valores
		String valoresPermitidos[] = {
			ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_CA,
			ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_ES,
			ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_EN
		};
		validaLongitudCampo("DatosAsunto", "IdiomaAsunto", idiomaAsunto, ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_MAX);
		validaCampoObligatorio ("DatosAsunto", "IdiomaAsunto", idiomaAsunto);
		validaCampoConListaValores ("DatosAsunto", "IdiomaAsunto", idiomaAsunto, valoresPermitidos);
		
		this.idiomaAsunto = idiomaAsunto;	
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#getTipoAsunto()
	 */
	public String getTipoAsunto() {
		return this.tipoAsunto;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#setTipoAsunto(java.lang.String)
	 */
	public void setTipoAsunto(String tipoAsunto)
			throws EstablecerPropiedadException {
		// Validar longitud de campo, campo obligatorio
		validaLongitudCampo("DatosAsunto", "TipoAsunto", tipoAsunto, ConstantesAsientoXML.DATOSASUNTO_TIPO_ASUNTO_MAX);
		validaCampoObligatorio ("DatosAsunto", "TipoAsunto", tipoAsunto);
		
		this.tipoAsunto = tipoAsunto;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#getExtractoAsunto()
	 */
	public String getExtractoAsunto() {
		return this.extractoAsunto;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#setExtractoAsunto(java.lang.String)
	 */
	public void setExtractoAsunto(String extractoAsunto)
			throws EstablecerPropiedadException {
		// Validar longitud de campo
		validaLongitudCampo("DatosAsunto", "ExtractoAsunto", extractoAsunto, ConstantesAsientoXML.DATOSASUNTO_EXTRACTO_ASUNTO_MAX);
		
		this.extractoAsunto = extractoAsunto;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#getCodigoOrganoDestino()
	 */
	public String getCodigoOrganoDestino() {
		return this.codigoOrganoDestino;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.DatosAsunto#setCodigoOrganoDestino(java.lang.String)
	 */
	public void setCodigoOrganoDestino(String codigoOrganoDestino)
			throws EstablecerPropiedadException {
		// Validar longitud de campo, campo requerido
		//validaLongitudCampo("DatosAsunto", "CodigoOrganoDestino", codigoOrganoDestino, ConstantesAsientoXML.DATOSASUNTO_CODIGO_ORGANO_DESTINO_MAX);
		validaCampoObligatorio ("DatosAsunto", "CodigoOrganoDestino", codigoOrganoDestino);
		
		this.codigoOrganoDestino = codigoOrganoDestino;
	}
	
	public String getIdentificadorTramite() {
		return this.identificadorTramite;
	}

	public void setIdentificadorTramite(String identificadorTramite) throws EstablecerPropiedadException {
		this.identificadorTramite = identificadorTramite;
	}

	public String getCodigoUnidadAdministrativa() {
		return this.codigoUnidadAdministrativa;
	}

	public void setCodigoUnidadAdministrativa(String codigoUnidadAdministrativa) throws EstablecerPropiedadException {
		validaCampoNoNulo ("DatosAsunto", "CodigoUnidadAdministrativa", codigoUnidadAdministrativa);		
		
		this.codigoUnidadAdministrativa = codigoUnidadAdministrativa;		
	}


	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		String idiomaAsuntoRaw = null;
		String tipoAsunto = null;
		String extractoAsunto = null;
		String codigoOrganoDestino = null;
		
		// Comprobar idioma asunto
		idiomaAsuntoRaw = getIdiomaAsunto ();
		if (idiomaAsuntoRaw == null) throw new CampoObligatorioException ("DatosAsunto", "IdiomaAsunto");
		
		idiomaAsuntoRaw = idiomaAsuntoRaw.trim ();
		if (idiomaAsuntoRaw.equals("")) throw new CampoObligatorioException ("DatosAsunto", "IdiomaAsunto");
		if ( (!idiomaAsuntoRaw.equals("" + ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_CA)) &&
				(!idiomaAsuntoRaw.equals("" + ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_ES)) && 
				 (!idiomaAsuntoRaw.equals("" + ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_EN))
			)
		{
			String valoresPermitidos[] = {
				"" + ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_CA,
				"" + ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_ES,
				"" + ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_EN
			};
			throw new ValorFueraListaValoresPermitidosException ("DatosAsunto", "IdiomaAsunto", idiomaAsuntoRaw, valoresPermitidos);
		}
		
		// Comprobar tipo de asunto
		tipoAsunto = getTipoAsunto ();
		if ( (tipoAsunto == null) || (tipoAsunto.trim().equals("")))
			throw new CampoObligatorioException ("DatosAsunto", "TipoAsunto");
		
		// Comprobar extracto asunto
		extractoAsunto = getExtractoAsunto ();
		if ( (extractoAsunto == null) || (extractoAsunto.trim().equals("")))
			throw new CampoObligatorioException ("DatosAsunto", "ExtractoAsunto");
		
		// Comprobar codigo organo destino
		codigoOrganoDestino = getCodigoOrganoDestino ();
		if ( (codigoOrganoDestino == null) || (codigoOrganoDestino.trim().equals ("")))
			throw new CampoObligatorioException ("DatosAsunto", "CodigoOrganoDestino");
		
		// Comprobar código unidad administrativa
		String codigoUnidadAdministrativa  = getCodigoUnidadAdministrativa();
		if ( (codigoUnidadAdministrativa == null) || (codigoUnidadAdministrativa.trim().equals("")))
			throw new CampoObligatorioException ("DatosAsunto", "CodigoUnidadAdministrativa");
		
		// Comprobar IdentificadorTramite
//		validaCampoNoNulo ("DatosAsunto", "IdentificadorTramite", getIdentificadorTramite());
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof DatosAsunto){
			
			if (obj == null) return false;
			
			DatosAsunto datosAsunto = (DatosAsunto) obj;
			
			// Comprobar fecha asunto
			java.util.Date fechaAsunto = getFechaAsunto();
			java.util.Date fechaAsuntoExt = datosAsunto.getFechaAsunto ();
			
			if ((fechaAsunto != null) || (fechaAsuntoExt != null))
				if ( (fechaAsuntoExt != null) && (fechaAsunto != null) ){
					if (!fechaAsunto.equals (fechaAsuntoExt)) return false;
				}
				else
					if ((fechaAsunto != null) || (fechaAsuntoExt != null)) return false;
			
			// Comprobar idioma asunto
			String idiomaAsunto = getIdiomaAsunto ();
			String idiomaAsuntoExt = datosAsunto.getIdiomaAsunto();
			
			if ((idiomaAsunto != null) || (idiomaAsuntoExt != null))
				if ( (idiomaAsuntoExt != null) && (idiomaAsunto != null) ){
					if (!idiomaAsunto.equals (idiomaAsuntoExt)) return false;
				}
				else
					if ((idiomaAsunto != null) || (idiomaAsuntoExt != null)) return false;
			
			// Comprobar tipo asunto
			String tipoAsunto = getTipoAsunto ();
			String tipoAsuntoExt = datosAsunto.getTipoAsunto();
			
			if ((tipoAsunto != null) || (tipoAsuntoExt != null))
				if ( (tipoAsunto != null) && (tipoAsunto != null) ){
					if (!tipoAsunto.equals (tipoAsunto)) return false;
				}
				else
					if ((tipoAsunto != null) || (tipoAsuntoExt != null)) return false;
			
			// Comprobar extracto asunto
			String extracto = getExtractoAsunto ();
			String extractoExt = datosAsunto.getExtractoAsunto();
			
			if ((extracto != null) || (extractoExt != null))
				if ( (extractoExt != null) && (extracto != null) ){
					if (!extracto.equals (extractoExt)) return false;
				}
				else
					if ((extracto != null) || (extractoExt != null)) return false;
			
			// Comprobar codigo organo destino
			String codigoOrgano = getCodigoOrganoDestino ();
			String codigoOrganoExt = datosAsunto.getCodigoOrganoDestino();
			
			if ((codigoOrgano != null) || (codigoOrganoExt != null))
				if ( (codigoOrganoExt != null) && (codigoOrgano != null) ){
					if (!codigoOrgano.equals (codigoOrganoExt)) return false;
				}
				else
					if ((codigoOrgano != null) || (codigoOrganoExt != null)) return false;
			
			// Comprobar descripcion organo destino
			if (!objetosIguales (getDescripcionOrganoDestino(), datosAsunto.getDescripcionOrganoDestino())) return false;
			
			
			// Comprobar codigo unidad administrativa
			if (!objetosIguales (getCodigoUnidadAdministrativa(), datosAsunto.getCodigoUnidadAdministrativa())) return false;
			
			// Comprobar identificador tramite
			if (!objetosIguales (getIdentificadorTramite(), datosAsunto.getIdentificadorTramite())) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

	public String getDescripcionOrganoDestino() {
		return descripcionOrganoDestino;
	}

	public void setDescripcionOrganoDestino(String descripcionOrganoDestino) {
		this.descripcionOrganoDestino = descripcionOrganoDestino;
	}

	public Date getFechaAsunto() {
		return fechaAsunto;
	}

	public void setFechaAsunto(Date fechaAsunto) {
		this.fechaAsunto = fechaAsunto;
	}


	
}
