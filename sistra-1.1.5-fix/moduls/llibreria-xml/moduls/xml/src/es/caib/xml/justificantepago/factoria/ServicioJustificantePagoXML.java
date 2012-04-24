package es.caib.xml.justificantepago.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.justificantepago.factoria.impl.FactoriaObjetosXMLJustificantePagoImpl;

/** Clase que exporta el servicio de creaci�n de objetos (factor�a
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioJustificantePagoXML {
	
	/** Crea una factor�a con capacidades para crear objetos XML
	 * @return Factor�a que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factor�a
	 */
	public static FactoriaObjetosXMLJustificantePago crearFactoriaObjetosXML () throws InicializacionFactoriaException{
		//TODO implementar factoria JustificantePago
		return new FactoriaObjetosXMLJustificantePagoImpl ();			
	}
}
