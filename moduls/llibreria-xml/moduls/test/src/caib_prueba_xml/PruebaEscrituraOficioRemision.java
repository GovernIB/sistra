package caib_prueba_xml;

import java.io.File;
import java.util.LinkedHashMap;

import es.caib.xml.ConstantesXML;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.oficioremision.factoria.impl.TramiteSubsanacion;

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
			
			OficioRemision oficioRemision = factoria.crearOficioRemision();
			
			oficioRemision.setTitulo("Titulo");
			oficioRemision.setTexto("Texto");
			
			TramiteSubsanacion ts = factoria.crearTramiteSubsanacion();
			ts.setDescripcionTramite("Tramite subsanacion");
			ts.setIdentificadorTramite("ID");
			ts.setVersionTramite(new Integer(1));
			
			ts.setParametrosTramite(new LinkedHashMap<String,String>());
			ts.getParametrosTramite().put("param1","valor1");
			ts.getParametrosTramite().put("param2","valor2");
			ts.getParametrosTramite().put("param3","valor3");
			
			oficioRemision.setTramiteSubsanacion(ts);
																				
			// Guardar documento en fichero
			System.out.println ("Escribiendo en consola"); 
			factoria.guardarOficioRemision (oficioRemision, new File ("moduls/llibreria-xml/moduls/test/oficio_remision_generado.xml"));
			System.out.println (factoria.guardarOficioRemision (oficioRemision)); 
			
			System.out.println ("Terminado correctamente"); 
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

}
