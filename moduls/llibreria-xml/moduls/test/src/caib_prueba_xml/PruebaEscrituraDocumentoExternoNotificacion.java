package caib_prueba_xml;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import es.caib.xml.documentoExternoNotificacion.factoria.FactoriaObjetosXMLDocumentoExternoNotificacion;
import es.caib.xml.documentoExternoNotificacion.factoria.ServicioDocumentoExternoNotificacionXML;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.DocumentoExternoNotificacion;

public class PruebaEscrituraDocumentoExternoNotificacion {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLDocumentoExternoNotificacion factoria = ServicioDocumentoExternoNotificacionXML.crearFactoriaObjetosXML();
			factoria.setEncoding("UTF-8");
			
			DocumentoExternoNotificacion documentoExternoNotificacion = factoria.crearDocumentoExternoNotificacion();		
			factoria.setIndentacion(true);
			
			documentoExternoNotificacion.setNombre("nombre");
			documentoExternoNotificacion.setUrl("http://www.google.es");
			
			String xml = factoria.guardarDocumentoExternoNotificacion(documentoExternoNotificacion);
			System.out.println(xml);
			
			DocumentoExternoNotificacion c = factoria.crearDocumentoExternoNotificacion(new ByteArrayInputStream(xml.getBytes("UTF-8")));	
			factoria.guardarDocumentoExternoNotificacion(c,new FileOutputStream("moduls/llibreria-xml/moduls/test/documentoExternoNotificacion_generado.xml"));			
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
