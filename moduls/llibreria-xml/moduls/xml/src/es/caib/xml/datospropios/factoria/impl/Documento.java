package es.caib.xml.datospropios.factoria.impl;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;

/** Objeto de Dato que encapsula el nodo XML DOCUMENTO de los documentos
 * XML de datos propios. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding). 
 * 
 * @author magroig
 *
 */
public class Documento extends NodoBaseDatosPropios {
			
	private Character tipo ;
	private Boolean firmar;
	private Boolean compulsar;
	private Boolean fotocopia;
	private String titulo;
	private String identificador;
	
	Documento (){
		tipo = null;
		firmar = new Boolean(false);
		compulsar = new Boolean(false);
		fotocopia = new Boolean(false);
		titulo = null;
		identificador = null;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#getTipo()
	 * Si no hay tipo, se deuvuelve el caracter vacio '\0'
	 */
	public Character getTipo() {					
		return tipo;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#setTipo(char)
	 */
	public void setTipo(Character tipo) throws EstablecerPropiedadException {
		String valoresValidos[] = {
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_PAGO
		};
		
		validaCampoObligatorio("Documento", "Tipo", "" + tipo.charValue());
		validaCampoConListaValores("Documento", "Tipo", "" + tipo.charValue(), valoresValidos);
		
		this.tipo = tipo;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#isFirmar()
	 */
	public Boolean isFirmar() {	
		return firmar;
	}
	 
	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#setFirmar(boolean)
	 */
	public void setFirmar(Boolean firmar) throws EstablecerPropiedadException {
		Character tipo = getTipo ();
		
		// Comprobar que se ha establecido el tipo
		if (tipo == null) {
			throw new EstablecerPropiedadException ("Se debe establecer el tipo antes de poder " +
					"establecer propiedades de Firmar, Compulsar y/o Fotocopia", "Documento", 
					"Firmar", firmar);					
		}
		
		// Solo se puede establecer la firma para documentos del tipo J , F y G
		if ( (tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE) &&
				(tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO) &&
				(tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE) ){
			throw new EstablecerPropiedadException ("No se puede establecer la propiedad Firmar para el tipo de documento " + tipo,
					"Documento", "Firma", firmar);
		}	
		
		this.firmar = firmar;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#isCompulsar()
	 */
	public Boolean isCompulsar() {
		return compulsar;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#setCompulsar(boolean)
	 */
	public void setCompulsar(Boolean compulsar)
			throws EstablecerPropiedadException {
		
		Character tipo = getTipo ();
		
		// Comprobar que se ha establecido el tipo
		if (tipo == null) {
			throw new EstablecerPropiedadException ("Se debe establecer el tipo antes de poder " +
					"establecer propiedades de Firmar, Compulsar y/o Fotocopia", "Documento", 
					"Comoulsar", compulsar);					
		}
		
		// Solo se puede establecer la compulsa para documentos del tipo A
		if (tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO){
			throw new EstablecerPropiedadException ("No se puede establecer la propiedad Compulsar para el tipo de documento " + tipo,
					"Documento", "Compulsar", compulsar);
		}
		
		this.compulsar = compulsar;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#isFotocopia()
	 */
	public Boolean isFotocopia() {	
		return fotocopia;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#setFotocopia(boolean)
	 */
	public void setFotocopia(Boolean fotocopia)
			throws EstablecerPropiedadException {

		Character tipo = getTipo ();
		
		// Comprobar que se ha establecido el tipo
		if (tipo == null) {
			throw new EstablecerPropiedadException ("Se debe establecer el tipo antes de poder " +
					"establecer propiedades de Firmar, Compulsar y/o Fotocopia", "Documento", 
					"Fotocopia", fotocopia);					
		}
		
		// Solo se puede establecer la fotocopia para documentos del tipo A
		if (tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO){
			throw new EstablecerPropiedadException ("No se puede establecer la propiedad Fotocopia para el tipo de documento " + tipo,
					"Documento", "Fotocopia", fotocopia);
		}
		
		this.fotocopia = fotocopia;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#getTitulo()
	 */
	public String getTitulo() {
		return titulo;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#setTitulo(java.lang.String)
	 */
	public void setTitulo(String titulo) throws EstablecerPropiedadException {
		validaCampoObligatorio("Documento", "Titulo", titulo);
		
		this.titulo = titulo;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#getReferenciaRDS()
	 */
	public String getIdentificador() {		
		return identificador;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Documento#setReferenciaRDS(es.caib.xml.datospropios.factoria.ReferenciaRDS)
	 */
	public void setIdentificador(String identificador)
			throws EstablecerPropiedadException {				
		
		this.identificador = identificador;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		
		// ---------- Validar tipo
		Character tipo = getTipo ();
		
		if (tipo == null) throw new CampoObligatorioException ("Documento", "Tipo");
		
		String valoresTipoValidos[] = {
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE,
				"" + ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_PAGO
		};
		validaCampoConListaValores("Documento", "Tipo", "" + tipo.charValue(), valoresTipoValidos);
		
		

		/* TODO Revisar validaciones ya que los parámetros siempre se establecen al generar datos propios
		// ---------- Validar Firmar
		// Si la firma esta establecida, el tipo de documento debe ser J / F / A
		if ( (this.firmar != null) && 
				(tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE) &&
				(tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO) &&
				(tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO)){
			throw new EstablecerPropiedadException ("No se puede establecer la firma si el tipo de documento no es (J,F,A)",
					"Documento", "Firmar", null);
		}		
		// Si el tipo de documento es J ó F, la firma debe estar establecida
		if ( ( (tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE)  || (tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO) ) && (firmar == null) ){
			throw new CampoObligatorioException ("Documento", "Firmar");
		}
		
		// ---------- Validar Compulsar		
		//	Si la compulsa esta establecida, el tipo de documento debe ser A
		if ( (compulsar != null) && (tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO) ){
			throw new EstablecerPropiedadException ("No se puede establecer la compulsa si el tipo de documento no es A",
					"Documento", "Compulsar", null);
		}		
		// Si el tipo de documento es A, la compulsa debe estar establecida
		if ( (tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO) && (compulsar == null) ){
			throw new CampoObligatorioException ("Documento", "Compulsar");
		}
		
		// ---------- Validar Fotocopia	
		//	Si la fotocopia esta establecida, el tipo de documento debe ser A
		if ( (fotocopia != null) && (tipo.charValue() != ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO) ){
			throw new EstablecerPropiedadException ("No se puede establecer la fotocopia si el tipo de documento no es A",
					"Documento", "Fotocopia", null);
		}		
		//	Si el tipo de documento es A, la fotocopia debe estar establecida
		if ( (tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO) && (fotocopia == null) ){
			throw new CampoObligatorioException ("Documento", "Fotocopia");
		}
		*/
		
		// ---------- Validar Titulo
		validaCampoObligatorio("Documento", "Titulo", getTitulo());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof Documento){
			
			if (obj == null) return false;
			
			Documento documento = (Documento) obj;
			
			// Tipo
			if (!objetosIguales(getTipo (), documento.getTipo())) return false;
			
			// Firma
			if (!objetosIguales (isFirmar (), documento.isFirmar())) return false;			
			
			// Compulsa
			if (!objetosIguales (isFirmar(), documento.isFirmar())) return false;			
			
			// Fotocopia
			if (!objetosIguales (isFotocopia (), documento.isFotocopia())) return false;			
			
			// Titulo
			if (!objetosIguales (getTitulo (), documento.getTitulo ())) return false;			
			
			// Referencia RDS
			if (!objetosIguales (getIdentificador (), documento.getIdentificador())) return false;
									
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

	public Boolean getCompulsar() {
		return compulsar;
	}

	public Boolean getFirmar() {
		return firmar;
	}

	public Boolean getFotocopia() {
		return fotocopia;
	}
	
}
