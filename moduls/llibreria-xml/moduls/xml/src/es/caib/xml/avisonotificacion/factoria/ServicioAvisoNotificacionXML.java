package es.caib.xml.avisonotificacion.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.avisonotificacion.factoria.impl.FactoriaObjetosXMLAvisoNotificacionImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioAvisoNotificacionXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLAvisoNotificacion crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLAvisoNotificacionImpl ();
	}
}

