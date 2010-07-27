package es.caib.xml.taxa.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.NodoBase;
import es.caib.xml.taxa.factoria.ConstantesTaxaXML;

public class Domicili extends NodoBase {
	private String sigles;
	private String codiSigles;
	
	private String nomVia;
	private String codiNomVia;
	
	private String numero;
	private String codiNumero;
	
	private String lletra;
	private String codiLletra;
	
	private String escala;
	private String codiEscala;
	
	private String pis;
	private String codiPis;
	
	private String porta;
	private String codiPorta;
	
	Domicili() {}	
	
	public String getCodiEscala() {
		return codiEscala;
	}


	public void setCodiEscala(String codiEscala) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_ESCALA};
		validaCampoConListaValores("Domicili", "CodiEscala", codiEscala, codigoValido);
		
		this.codiEscala = codiEscala;
	}


	public String getCodiLletra() {
		return codiLletra;
	}


	public void setCodiLletra(String codiLletra) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_LLETRA};
		validaCampoConListaValores("Domicili", "CodiLletra", codiLletra, codigoValido);
		
		this.codiLletra = codiLletra;
	}


	public String getCodiNomVia() {
		return codiNomVia;
	}


	public void setCodiNomVia(String codiNomVia) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_NOM_VIA};
		validaCampoConListaValores("Domicili", "CodiNomVia", codiNomVia, codigoValido);
		
		this.codiNomVia = codiNomVia;
	}


	public String getCodiNumero() {
		return codiNumero;
	}


	public void setCodiNumero(String codiNumero) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_NUMERO};
		validaCampoConListaValores("Domicili", "CodiNumero", codiNumero, codigoValido);
		
		this.codiNumero = codiNumero;
	}


	public String getCodiPis() {
		return codiPis;
	}


	public void setCodiPis(String codiPis) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_PIS};
		validaCampoConListaValores("Domicili", "Codipis", codiPis, codigoValido);
		
		this.codiPis = codiPis;
	}


	public String getCodiPorta() {
		return codiPorta;
	}


	public void setCodiPorta(String codiPorta) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_PORTA};
		validaCampoConListaValores("Domicili", "CodiPorta", codiPorta, codigoValido);
		
		this.codiPorta = codiPorta;
	}


	public String getCodiSigles() {
		return codiSigles;
	}


	public void setCodiSigles(String codiSingles) throws EstablecerPropiedadException {
		String codigoValido[] = {ConstantesTaxaXML.CODI_SINGLES};
		validaCampoConListaValores("Domicili", "CodiSingles", codiSingles, codigoValido);
		
		this.codiSigles = codiSingles;
	}


	public String getEscala() {
		return escala;
	}


	public void setEscala(String escala) {
		this.escala = escala;
	}


	public String getLletra() {
		return lletra;
	}


	public void setLletra(String lletra) {
		this.lletra = lletra;
	}


	public String getNomVia() {
		return nomVia;
	}


	public void setNomVia(String nomVia) throws EstablecerPropiedadException {
		validaCampoObligatorio("Domicili", "NomVia", nomVia);
		
		this.nomVia = nomVia;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getPis() {
		return pis;
	}


	public void setPis(String pis) {
		this.pis = pis;
	}


	public String getPorta() {
		return porta;
	}


	public void setPorta(String porta) {
		this.porta = porta;
	}


	public String getSigles() {
		return sigles;
	}


	public void setSigles(String singles) {
		this.sigles = singles;
	}
	
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		// validaCampoObligatorio("Domicili", "NomVia", getNomVia ());
	}


}
