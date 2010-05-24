package caib_prueba_xml;

import java.io.File;

import es.caib.xml.ConstantesXML;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;

public class PruebaEscrituraOficioRemision {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FactoriaObjetosXMLOficioRemision factoria = null;
		
		System.out.println ("Creando factoria");
		
		try {			
			factoria = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
			
			factoria.setEncoding(ConstantesXML.ENCODING);
			factoria.setIndentacion (true);
			
			OficioRemision avisoNotificacion = factoria.crearOficioRemision();
			
			avisoNotificacion.setTitulo("Titulo");
			avisoNotificacion.setTexto("Texto");
			
																				
			// Guardar documento en fichero
			System.out.println ("Escribiendo en consola"); 
			factoria.guardarOficioRemision (avisoNotificacion, new File ("moduls/llibreria-xml/moduls/test/oficio_remision_generado.xml"));
			System.out.println (factoria.guardarOficioRemision (avisoNotificacion)); 
			
			System.out.println ("Terminado correctamente"); 
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

}
