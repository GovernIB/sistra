package caib_prueba_xml;

import java.io.FileInputStream;

import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;

public class PruebaLecturaAvisoNotificacion {
	

	private static void imprimirAvisoNotificacion (AvisoNotificacion avisoNotificacion){
		System.out.println ("AVISO NOTIFICACION");
		System.out.println ("Titulo: " + avisoNotificacion.getTitulo());
		System.out.println ("Texto: " + avisoNotificacion.getTexto());
		System.out.println ("Texto SMS: " + avisoNotificacion.getTextoSMS());
		System.out.println ("Acuse: " + avisoNotificacion.getAcuseRecibo());
		System.out.println ("Plazo: " + avisoNotificacion.getPlazo());
		System.out.println ("Accesible clave: " + avisoNotificacion.getAccesiblePorClave());
		System.out.println ("Expediente: ");
		System.out.println ("	- Unidad admin: " + avisoNotificacion.getExpediente().getUnidadAdministrativa());
		System.out.println ("	- Id expedient: " + avisoNotificacion.getExpediente().getIdentificadorExpediente());
		System.out.println ("	- Clave expedi: " + avisoNotificacion.getExpediente().getClaveExpediente());
		System.out.println ("	- Titulo expedi: " + avisoNotificacion.getExpediente().getTituloExpediente());
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLAvisoNotificacion factoria = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
			AvisoNotificacion avisoNotificacion = factoria.crearAvisoNotificacion (new FileInputStream ("moduls/llibreria-xml/moduls/test/aviso_notificacion_generado.xml"));			
			imprimirAvisoNotificacion (avisoNotificacion);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
