package es.caib.xml;


/** Excepci�n que se utiliza para modelizar el error producido al intentar
 *  establecer una propiedad a valor nulo en un objeto XML, cuando dicha
 *  propiedad es requerida
 *  
 * @author magroig
 *
 */
public class CampoObligatorioException extends EstablecerPropiedadException {

	/** Constructor de la excepci�n
	 * @param tipoObjeto Identificador del tipo de objeto XML
	 * @param propiedad Nombre de la propiedad cuyo establecimiento causa la excepci�n
	 */
	public CampoObligatorioException(String tipoObjeto, String propiedad) {	
		super("La propiedad " + propiedad + " del objeto " + tipoObjeto + " es requerida", 
				tipoObjeto, propiedad, null);		
	}

}
