package es.caib.xml.delegacion.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.delegacion.factoria.impl.FactoriaObjetosXMLDelegacionImpl;

/** Clase que exporta el servicio de creaci�n de objetos (factor�a
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioDelegacionXML {
	
	/** Crea una factor�a con capacidades para crear objetos XML
	 * @return Factor�a que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factor�a
	 */
	public static FactoriaObjetosXMLDelegacion crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		return new FactoriaObjetosXMLDelegacionImpl ();
	}
}

