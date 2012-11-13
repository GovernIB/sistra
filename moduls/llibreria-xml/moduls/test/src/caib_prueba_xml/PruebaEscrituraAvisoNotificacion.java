package caib_prueba_xml;

import java.io.File;

import es.caib.xml.ConstantesXML;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;

public class PruebaEscrituraAvisoNotificacion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FactoriaObjetosXMLAvisoNotificacion factoria = null;
		
		System.out.println ("Creando factoria");
		
		try {			
			factoria = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
			
			factoria.setEncoding(ConstantesXML.ENCODING);
			factoria.setIndentacion (true);
			
			AvisoNotificacion avisoNotificacion = factoria.crearAvisoNotificacion();
			
			avisoNotificacion.setTitulo("Titulo");
			avisoNotificacion.setTexto("Texto");
			avisoNotificacion.setTextoSMS("Texto SMS");
			avisoNotificacion.setAcuseRecibo(new Boolean(false));
			avisoNotificacion.setAccesiblePorClave(new Boolean(false));
			avisoNotificacion.setFirmaPorClave(new Boolean(true));
			
			avisoNotificacion.getExpediente().setIdentificadorExpediente("xxx");
			avisoNotificacion.getExpediente().setUnidadAdministrativa("yy");
			avisoNotificacion.getExpediente().setClaveExpediente("zzz");
			
																				
			// Guardar documento en fichero
			System.out.println ("Escribiendo en consola"); 
			factoria.guardarAvisoNotificacion (avisoNotificacion, new File ("moduls/llibreria-xml/moduls/test/aviso_notificacion_generado.xml"));
			System.out.println (factoria.guardarAvisoNotificacion (avisoNotificacion)); 
			System.out.println ("Terminado correctamente"); 
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

}
