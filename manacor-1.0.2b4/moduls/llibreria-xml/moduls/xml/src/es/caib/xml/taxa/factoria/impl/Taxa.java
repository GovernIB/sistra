package es.caib.xml.taxa.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.NodoBase;
import es.caib.xml.taxa.factoria.ConstantesTaxaXML;

public class Taxa extends NodoBase {		
	private String idtaxa;
	private String codiIdtaxa;
	
	private String subconcepte;
	private String codiSubconcepte;
	
	private String importe;
	private String codiImporte;
	
	private String concepte;
	private String codiConcepte;
	
	private String versio;
	private String accio;
	private String localizador;
	private String modelo;
	private Declarant declarant;	

	Taxa() {}

	public String getAccio() {
		return accio;
	}

	public void setAccio(String accio) throws EstablecerPropiedadException {
		validaCampoObligatorio ("Taxa", "Accio", accio);
		
		String valoresPermitidos[] = {ConstantesTaxaXML.ACCIO_IMPRIMIR,
				ConstantesTaxaXML.ACCIO_PAGAR};
		validaCampoConListaValores ("Taxa", "Accio", accio, valoresPermitidos);
		
		
		this.accio = accio;
	}

	public String getCodiConcepte() {
		return codiConcepte;
	}

	public void setCodiConcepte(String codiConcepte) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_CONCEPTE};
		validaCampoConListaValores("Taxa", "CodiConcepte", codiConcepte, codigoValido);
		
		this.codiConcepte = codiConcepte;
	}

	public String getCodiIdtaxa() {
		return codiIdtaxa;
	}

	public void setCodiIdtaxa(String codiIdtaxa) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_IDTAXA};
		validaCampoConListaValores("Taxa", "CodiIdtaxa", codiIdtaxa, codigoValido);
		
		this.codiIdtaxa = codiIdtaxa;
	}

	public String getCodiImporte() {
		return codiImporte;
	}

	public void setCodiImporte(String codiImporte) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_IMPORT};
		validaCampoConListaValores("Taxa", "CodiImporte", codiImporte, codigoValido);
		
		this.codiImporte = codiImporte;
	}

	public String getCodiSubconcepte() {
		return codiSubconcepte;
	}

	public void setCodiSubconcepte(String codiSubconcepte) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_SUBCONCEPTE};
		validaCampoConListaValores("Taxa", "CodiSubconcepte", codiSubconcepte, codigoValido);
		
		this.codiSubconcepte = codiSubconcepte;
	}

	public String getConcepte() {
		return concepte;
	}

	public void setConcepte(String concepte) throws EstablecerPropiedadException {
		validaCampoObligatorio("Taxa", "Concepte", concepte);
		
		// Los campos (importe, concepte) y el campo subconcepte son excluyentes
		if (getSubconcepte() != null){
			throw new EstablecerPropiedadException ("No se puede establecer el campo concepte, pues se " +
					"ha establecido el campo subconcepte", "Taxa", "Concepte", concepte);
		}
		
		this.concepte = concepte;
	}

	public Declarant getDeclarant() {
		return declarant;
	}

	public void setDeclarant(Declarant declarant) throws EstablecerPropiedadException {
		validaCampoObligatorio ("Taxa", "Declarant", declarant);
		
		this.declarant = declarant;
	}

	public String getIdtaxa() {
		return idtaxa;
	}

	public void setIdtaxa(String idtaxa) throws EstablecerPropiedadException {
		validaCampoObligatorio ("Taxa", "Idtaxa", idtaxa);
		
		this.idtaxa = idtaxa;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) throws EstablecerPropiedadException {
		validaCampoObligatorio("Taxa", "Importe", importe);
		
		// Los campos (importe, concepte) y el campo subconcepte son excluyentes
		if (getSubconcepte() != null){
			throw new EstablecerPropiedadException ("No se puede establecer el campo importe, pues se " +
					"ha establecido el campo subconcepte", "Taxa", "Importe", importe);
		}
		
		this.importe = importe;
	}

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) throws EstablecerPropiedadException {
		validaCampoObligatorio ("Taxa", "Modelo", modelo);
		
		this.modelo = modelo;
	}

	public String getSubconcepte() {
		return subconcepte;
	}

	public void setSubconcepte(String subconcepte) throws EstablecerPropiedadException {
		if (subconcepte != null){
			// El campo subconcepte y los campos (importe, concepte) son excluyentes
			if ( (getImporte() != null) || (getConcepte() != null) ){
				throw new EstablecerPropiedadException ("No se puede establecer el campo subconcepte, pues se " +
						"ha establecido el campo importe y/o el campo concepte", "Taxa", "Subconcepte", subconcepte);
			}
		}
		
		this.subconcepte = subconcepte;
	}

	public String getVersio() {
		return versio;
	}

	public void setVersio(String versio) throws EstablecerPropiedadException {
		validaCampoObligatorio("Taxa", "Versio", versio);
								
		this.versio = versio;
	}
	
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio ("Taxa", "Accio", getAccio ());
		validaCampoObligatorio ("Taxa", "Declarant", getDeclarant());
		validaCampoObligatorio ("Taxa", "Idtaxa", getIdtaxa ());
		validaCampoObligatorio ("Taxa", "Modelo", getModelo ());
		validaCampoObligatorio("Taxa", "Versio", getVersio ());
		
		// Si no se ha establecido el campo subconcepte, se deben haber establecido
		// los campos importe y concepte
		if (getSubconcepte() == null){
			validaCampoObligatorio ("Taxa", "Importe", getImporte ());
			validaCampoObligatorio ("Taxa", "Concepte", getConcepte ());
		}
		
		if (getDeclarant () != null){
			getDeclarant ().comprobarDatosRequeridos();
		}
	}
}
