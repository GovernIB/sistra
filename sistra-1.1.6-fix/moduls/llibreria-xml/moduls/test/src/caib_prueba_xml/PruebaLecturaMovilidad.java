package caib_prueba_xml;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import es.caib.xml.movilidad.factoria.FactoriaObjetosXMLMovilidad;
import es.caib.xml.movilidad.factoria.ServicioMovilidadXML;
import es.caib.xml.movilidad.factoria.impl.Envio;

public class PruebaLecturaMovilidad {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLMovilidad factoria = ServicioMovilidadXML.crearFactoriaObjetosXML();
			factoria.setEncoding("UTF-8");
			factoria.setIndentacion(true);
			
			Envio envio = factoria.crearEnvio(new FileInputStream ("moduls/llibreria-xml/moduls/test/movilidad_generado.xml"));					
			System.out.println("Leido XML");
			
			
			String xml = factoria.guardarEnvio(envio);
			System.out.println(xml);
						
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
