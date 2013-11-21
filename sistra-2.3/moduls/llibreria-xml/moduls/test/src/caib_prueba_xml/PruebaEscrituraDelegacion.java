package caib_prueba_xml;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import es.caib.xml.delegacion.factoria.FactoriaObjetosXMLDelegacion;
import es.caib.xml.delegacion.factoria.ServicioDelegacionXML;
import es.caib.xml.delegacion.factoria.impl.AutorizacionDelegacion;

public class PruebaEscrituraDelegacion {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLDelegacion factoria = ServicioDelegacionXML.crearFactoriaObjetosXML();
			factoria.setEncoding("UTF-8");

			
			AutorizacionDelegacion delegacion = factoria.crearAutorizacionDelegacion();		
			factoria.setIndentacion(true);
			
			delegacion.setEntidadId("11111111H");
			delegacion.setEntidadNombre("Jose Perez Garcia");
			delegacion.setDelegadoId("22222222F");
			delegacion.setDelegadoNombre("Carlos Gutierrez Gomez");
			delegacion.setPermisos("RPT");
			delegacion.setFechaInicio("20081225");
			delegacion.setFechaFin("20111225");
			
			String xml = factoria.guardarAutorizacionDelegacion(delegacion);
			System.out.println(xml);
			
			AutorizacionDelegacion c = factoria.crearAutorizacionDelegacion(new ByteArrayInputStream(xml.getBytes("UTF-8")));	
			factoria.guardarAutorizacionDelegacion(c,new FileOutputStream("moduls/llibreria-xml/moduls/test/delegacion_generado.xml"));			
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
