package caib_prueba_xml;

import java.io.FileInputStream;

import es.caib.xml.delegacion.factoria.FactoriaObjetosXMLDelegacion;
import es.caib.xml.delegacion.factoria.ServicioDelegacionXML;
import es.caib.xml.delegacion.factoria.impl.AutorizacionDelegacion;

public class PruebaLecturaDelegacion {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLDelegacion factoria = ServicioDelegacionXML.crearFactoriaObjetosXML();
			factoria.setEncoding("UTF-8");
			factoria.setIndentacion(true);
			
			AutorizacionDelegacion envio = factoria.crearAutorizacionDelegacion(new FileInputStream ("moduls/llibreria-xml/moduls/test/delegacion_generado.xml"));					
			System.out.println("Leido XML");
			
			
			String xml = factoria.guardarAutorizacionDelegacion(envio);
			System.out.println(xml);
						
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
