package es.caib.xml.justificantepago.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.justificantepago.factoria.impl.FactoriaObjetosXMLJustificantePagoImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioJustificantePagoXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLJustificantePago crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		//TODO implementar factoria JustificantePago
		return new FactoriaObjetosXMLJustificantePagoImpl ();			
	}
}
