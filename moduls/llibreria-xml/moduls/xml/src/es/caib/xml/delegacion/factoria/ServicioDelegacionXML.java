package es.caib.xml.delegacion.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.delegacion.factoria.impl.FactoriaObjetosXMLDelegacionImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioDelegacionXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLDelegacion crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLDelegacionImpl ();
	}
}

