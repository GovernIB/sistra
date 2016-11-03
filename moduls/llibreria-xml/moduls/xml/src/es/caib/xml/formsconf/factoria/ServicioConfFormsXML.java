package es.caib.xml.formsconf.factoria;

import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.formsconf.factoria.impl.FactoriaObjetosXMLConfFormsImpl;

/** Clase que exporta el servicio de creación de objetos (factoría
 * de objetos) al exterior
 * 
 * @author magroig
 *
 */
public class ServicioConfFormsXML {
	
	/** Crea una factoría con capacidades para crear objetos XML
	 * @return Factoría que crea objetos XML
	 * @throws InicializacionFactoriaException Se ha proudcido un error inesperado al crear la factoría
	 */
	public static FactoriaObjetosXMLConfForms crearFactoriaObjetosXML () throws InicializacionFactoriaException{		
		return new FactoriaObjetosXMLConfFormsImpl ();
	}
}
