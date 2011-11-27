package es.caib.xml.movilidad.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.movilidad.factoria.impl.FactoriaObjetosXMLMovilidadImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioMovilidadXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLMovilidad crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLMovilidadImpl ();
	}
}

