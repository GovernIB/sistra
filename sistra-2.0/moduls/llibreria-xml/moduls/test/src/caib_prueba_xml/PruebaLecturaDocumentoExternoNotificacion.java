package caib_prueba_xml;

import java.io.FileInputStream;

import es.caib.xml.documentoExternoNotificacion.factoria.FactoriaObjetosXMLDocumentoExternoNotificacion;
import es.caib.xml.documentoExternoNotificacion.factoria.ServicioDocumentoExternoNotificacionXML;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.DocumentoExternoNotificacion;

public class PruebaLecturaDocumentoExternoNotificacion {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLDocumentoExternoNotificacion factoria = ServicioDocumentoExternoNotificacionXML.crearFactoriaObjetosXML();
			factoria.setEncoding("UTF-8");
			factoria.setIndentacion(true);
			
			DocumentoExternoNotificacion documentoExternoNotificacion = factoria.crearDocumentoExternoNotificacion(new FileInputStream ("moduls/llibreria-xml/moduls/test/documentoExternoNotificacion_generado.xml"));					
			System.out.println("Leido XML");
			
			
			String xml = factoria.guardarDocumentoExternoNotificacion(documentoExternoNotificacion);
			System.out.println(xml);
						
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
