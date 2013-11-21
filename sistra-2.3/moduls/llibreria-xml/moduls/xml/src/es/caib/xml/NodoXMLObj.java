package es.caib.xml;

import java.io.Serializable;

/** Interface base para todas las interfaces que encapsulan la funcionalidad
 *  de gestionar los datos de un nodo XML 
 *  
 * @author magroig
 *
 */
public interface NodoXMLObj extends Serializable {
	/** Comprueba si un determinado objeto de datos XML tiene o no todos los
	 *  campos requeridos
	 * @throws EstablecerPropiedadException Hay un problema con el valor (o
	 *  el valor no especificado) de uno de los campos del documento
	 */
	public void comprobarDatosRequeridos () throws EstablecerPropiedadException;
}
