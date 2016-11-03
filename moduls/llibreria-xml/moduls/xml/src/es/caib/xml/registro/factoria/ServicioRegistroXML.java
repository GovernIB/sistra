package es.caib.xml.registro.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.registro.factoria.impl.FactoriaObjetosXMLRegistroImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioRegistroXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLRegistro crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLRegistroImpl ();
	}
}
