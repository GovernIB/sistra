package caib_prueba_xml;

import java.io.FileInputStream;

import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;

public class PruebaLecturaOficioRemision {
	

	private static void imprimirOficioRemision (OficioRemision oficioRemision){
		System.out.println ("OFICIO REMISION");
		System.out.println ("Titulo: " + oficioRemision.getTitulo());
		System.out.println ("Texto: " + oficioRemision.getTexto());			
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLOficioRemision factoria = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
			OficioRemision oficioRemision = factoria.crearOficioRemision (new FileInputStream ("moduls/llibreria-xml/moduls/test/oficio_remision_generado.xml"));			
			
			imprimirOficioRemision (oficioRemision);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
