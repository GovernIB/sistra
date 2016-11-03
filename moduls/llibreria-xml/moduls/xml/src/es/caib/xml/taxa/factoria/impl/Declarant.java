package es.caib.xml.taxa.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.NodoBase;
import es.caib.xml.ValorFueraListaValoresPermitidosException;
import es.caib.xml.taxa.factoria.ConstantesTaxaXML;

public class Declarant extends NodoBase {
	private String NIF;
	private String codiNIF;
	
	private String nom;
	private String codiNom;
	
	private String telefon;
	private String codiTelefon;
	
	private String FAX;
	private String codiFAX;
	
	private String localitat;
	private String codiLocalitat;
	
	private String provincia;
	private String codiProvincia;
	
	private String codiPostal;
	private String codiCodiPostal;
	
	private Domicili domicili;
	
	
	Declarant() {}


	public String getCodiCodiPostal() {
		return codiCodiPostal;
	}


	public void setCodiCodiPostal(String codiCodiPostal) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_CODI_POSTAL};
		validaCampoConListaValores("Domicili", "CodiCodiPostal", codiCodiPostal, codigoValido);
		
		this.codiCodiPostal = codiCodiPostal;
	}


	public String getCodiFAX() {
		return codiFAX;
	}


	public void setCodiFAX(String codiFAX) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_FAX};
		validaCampoConListaValores("Domicili", "CodiFax", codiFAX, codigoValido);
		
		this.codiFAX = codiFAX;
	}


	public String getCodiLocalitat() {
		return codiLocalitat;
	}


	public void setCodiLocalitat(String codiLocalitat) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_LOCALITAT};
		validaCampoConListaValores("Domicili", "CodiLocalitat", codiLocalitat, codigoValido);
		
		this.codiLocalitat = codiLocalitat;
	}


	public String getCodiNIF() {
		return codiNIF;
	}


	public void setCodiNIF(String codiNIF) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_NIF};
		validaCampoConListaValores("Domicili", "CodiNif", codiNIF, codigoValido);
		
		this.codiNIF = codiNIF;
	}


	public String getCodiNom() {
		return codiNom;
	}


	public void setCodiNom(String codiNom) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_NOM};
		validaCampoConListaValores("Domicili", "CodiNom", codiNom, codigoValido);
		
		this.codiNom = codiNom;
	}


	public String getCodiPostal() {
		return codiPostal;
	}


	public void setCodiPostal(String codiPostal) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_CODI_POSTAL};
		validaCampoConListaValores("Domicili", "CodiCodiPostal", codiCodiPostal, codigoValido);
		
		this.codiPostal = codiPostal;
	}


	public String getCodiProvincia() {
		return codiProvincia;
	}


	public void setCodiProvincia(String codiProvincia) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_PROVINCIA};
		validaCampoConListaValores("Domicili", "CodiProvincia", codiProvincia, codigoValido);
		
		this.codiProvincia = codiProvincia;
	}


	public String getCodiTelefon() {
		return codiTelefon;
	}


	public void setCodiTelefon(String codiTelefon) throws EstablecerPropiedadException {
		this.codiTelefon = codiTelefon;
	}


	public Domicili getDomicili() {
		return domicili;
	}


	public void setDomicili(Domicili domicili)  {
		this.domicili = domicili;
	}


	public String getFAX() {
		return FAX;
	}


	public void setFAX(String fax) {
		FAX = fax;
	}


	public String getLocalitat() {
		return localitat;
	}


	public void setLocalitat(String localitat) {
		this.localitat = localitat;
	}


	public String getNIF() {
		return NIF;
	}


	public void setNIF(String nif) throws EstablecerPropiedadException {
		validaCampoObligatorio ("Declarant", "NIF", nif);
		
		NIF = nif;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) throws EstablecerPropiedadException {
		validaCampoObligatorio ("Declarant", "Nom", nom);
		
		this.nom = nom;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getTelefon() {
		return telefon;
	}


	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio ("Declarant", "NIF", getNIF());
		validaCampoObligatorio ("Declarant", "Nom", getNom ());
		
		if (getDomicili () != null)
			getDomicili ().comprobarDatosRequeridos();
	}

}
