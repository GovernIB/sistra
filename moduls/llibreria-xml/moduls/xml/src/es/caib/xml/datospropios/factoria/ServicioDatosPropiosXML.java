package es.caib.xml.datospropios.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.datospropios.factoria.impl.FactoriaObjetosXMLDatosPropiosImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioDatosPropiosXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLDatosPropios crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLDatosPropiosImpl ();
	}
}

