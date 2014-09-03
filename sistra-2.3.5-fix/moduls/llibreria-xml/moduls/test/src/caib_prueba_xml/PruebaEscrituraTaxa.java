package caib_prueba_xml;


import java.io.File;

import es.caib.xml.taxa.factoria.ConstantesTaxaXML;
import es.caib.xml.taxa.factoria.FactoriaObjetosXMLTaxa;
import es.caib.xml.taxa.factoria.ServicioTaxaXML;
import es.caib.xml.taxa.factoria.impl.Declarant;
import es.caib.xml.taxa.factoria.impl.Domicili;
import es.caib.xml.taxa.factoria.impl.Taxa;

public class PruebaEscrituraTaxa {

	public static void main(String[] args) {
		FactoriaObjetosXMLTaxa factoria = null;
		
		try{
			factoria = ServicioTaxaXML.crearFactoriaObjetosXML();
			factoria.setIndentacion (true);
			
			Taxa taxa = factoria.crearTaxa ();
			taxa.setVersio ("1");
			taxa.setAccio (ConstantesTaxaXML.ACCIO_PAGAR);
			taxa.setModelo ("el modelo");
			taxa.setIdtaxa ("el id de taxa");			
			//taxa.setSubconcepte("");
			taxa.setImporte("3000 de importe");
			taxa.setConcepte("concepte");
			
			Declarant declarant = factoria.crearDeclarant();
			declarant.setNIF ("11111111H");
			declarant.setNom ("Miguel Angel Garcia");
			
			declarant.setTelefon ("Telefon");
			declarant.setFAX ("FAX");
			declarant.setLocalitat("Localitat");
			declarant.setProvincia("Provincia");
			declarant.setCodiPostal("Codi Postal");
			
			Domicili domicili = factoria.crearDomicili();
				
			domicili.setSigles ("Sigles");			
			domicili.setNomVia ("Av del Cid");
			domicili.setNumero("numero");
			domicili.setLletra("Lletra");
			domicili.setEscala("Escala");
			domicili.setPis("Pis");
			domicili.setPorta("porta");
			
			declarant.setDomicili (domicili);
			
			
			taxa.setDeclarant (declarant);
			
			factoria.guardarTaxa(taxa, new File ("moduls/llibreria-xml/moduls/test/taxa_generado.xml"));
			System.out.println (factoria.guardarTaxa (taxa));
			
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
