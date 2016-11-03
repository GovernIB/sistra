package caib_prueba_xml;

import java.io.File;

import es.caib.xml.taxa.factoria.FactoriaObjetosXMLTaxa;
import es.caib.xml.taxa.factoria.ServicioTaxaXML;
import es.caib.xml.taxa.factoria.impl.Declarant;
import es.caib.xml.taxa.factoria.impl.Domicili;
import es.caib.xml.taxa.factoria.impl.Taxa;

public class PruebaLecturaTaxa {
	
	private static void imprimirTaxa (Taxa taxa){
		System.out.println ("TAXA");
		System.out.println ("versio: " + taxa.getVersio());
		System.out.println ("Accio: " + taxa.getAccio());
		System.out.println ("Localizador: " + taxa.getLocalizador());
		System.out.println ("Modelo: " + taxa.getModelo());
		System.out.println ("Idtaxa: " + taxa.getIdtaxa());
		System.out.println ("Codi idtaxa: " + taxa.getCodiIdtaxa());
		System.out.println ("Subconcepte: " + taxa.getSubconcepte());
		System.out.println ("Codi subconcepte: " + taxa.getCodiSubconcepte());
		System.out.println ("Concepte: " + taxa.getConcepte());
		System.out.println ("Codi concepte: " + taxa.getCodiConcepte());
		System.out.println ("Import: " + taxa.getImporte());
		System.out.println ("Codi import: " + taxa.getCodiImporte());
		
		imprimirDeclarant (taxa.getDeclarant());
	}
	
	private static void imprimirDeclarant (Declarant declarant){
		System.out.println ("DECLARANT");
		if (declarant != null){
			System.out.println ("NIF: " + declarant.getNIF());
			System.out.println ("Codi NIF: " + declarant.getCodiNIF());
			System.out.println ("Nom: " + declarant.getNom());
			System.out.println ("Codi Nom: " + declarant.getCodiNom());
			System.out.println ("Telefon: " + declarant.getTelefon());
			System.out.println ("Codi Telefon: " + declarant.getCodiTelefon());
			System.out.println ("Fax: " + declarant.getFAX());
			System.out.println ("Codi Fax: " + declarant.getCodiFAX());
			System.out.println ("Localitat: " + declarant.getLocalitat());
			System.out.println ("Codi Localitat: " + declarant.getCodiLocalitat());
			System.out.println ("Provincia: " + declarant.getProvincia());
			System.out.println ("Codi Provincia: " + declarant.getCodiProvincia());
			System.out.println ("Codi Postal: " + declarant.getCodiPostal());
			System.out.println ("Codi c. postal: " + declarant.getCodiCodiPostal());
			
			imprimirDomicili (declarant.getDomicili());
		}
	}
	
	private static void imprimirDomicili (Domicili d){
		System.out.println ("DOMICILI");
		
		if (d != null){
			// Sigles
			System.out.println ("Sigles: " + d.getSigles());
			System.out.println ("Cod Sigles: " + d.getCodiSigles());
			
			// NomVia
			System.out.println ("NomVia: " + d.getNomVia());
			System.out.println ("Cod NomVia: " + d.getCodiNomVia());
			
			// Numero
			System.out.println ("Numero: " + d.getNumero());
			System.out.println ("Cod Numero: " + d.getCodiNumero());
			
			// Lletra
			System.out.println ("Lletra: " + d.getLletra());
			System.out.println ("Cod Lletra: " + d.getCodiLletra());
			
			// Escala
			System.out.println ("Escala: " + d.getEscala());
			System.out.println ("Cod Escala: " + d.getCodiEscala());
			
			// Pis
			System.out.println ("Pis: " + d.getPis());
			System.out.println ("Cod Pis: " + d.getCodiPis());
			
			// Porta
			System.out.println ("Porta: " + d.getPorta());
			System.out.println ("Cod Porta: " + d.getCodiPorta());
		}				
	}

	public static void main(String[] args) {
		FactoriaObjetosXMLTaxa factoria = null;
		
		try{
			factoria = ServicioTaxaXML.crearFactoriaObjetosXML();
			
			Taxa taxa = factoria.crearTaxa (new File ("moduls/llibreria-xml/moduls/test/taxa_generado.xml"));
			
			imprimirTaxa (taxa);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
