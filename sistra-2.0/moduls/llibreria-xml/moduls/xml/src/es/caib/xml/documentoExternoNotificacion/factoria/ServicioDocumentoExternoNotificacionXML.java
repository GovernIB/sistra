package es.caib.xml.documentoExternoNotificacion.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.FactoriaObjetosXMLDocumentoExternoNotificacionImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioDocumentoExternoNotificacionXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLDocumentoExternoNotificacion crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLDocumentoExternoNotificacionImpl ();
	}
}

