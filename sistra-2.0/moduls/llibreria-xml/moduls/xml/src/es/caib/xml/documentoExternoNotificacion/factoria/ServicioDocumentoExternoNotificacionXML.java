package es.caib.xml.documentoExternoNotificacion.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.FactoriaObjetosXMLDocumentoExternoNotificacionImpl;

/** Clase que exporta el servicio de creaci�n de objetos (factor�a
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioDocumentoExternoNotificacionXML {
	
	/** Crea una factor�a con capacidades para crear objetos XML
	 * @return Factor�a que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factor�a
	 */
	public static FactoriaObjetosXMLDocumentoExternoNotificacion crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLDocumentoExternoNotificacionImpl ();
	}
}

