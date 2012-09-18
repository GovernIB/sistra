package es.caib.xml.oficioremision.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.oficioremision.factoria.impl.FactoriaObjetosXMLOficioRemisionImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioOficioRemisionXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLOficioRemision crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLOficioRemisionImpl ();
	}
}

