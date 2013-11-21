package es.caib.xml;


/** Excepción pensada para ser lanzada cuando se intenta establecer un campo
 *  con un valor demasiado grande
 *  
 * @author magroig
 *
 */
public class DesbordamientoCampoException extends EstablecerPropiedadException {
	
	private Object maxValorPermitido;
	
	/** Constructor
	 * @param tipoObjeto Descripción o nombre del objeto XML sobre el que se produce la excepción
	 * @param propiedad Nombre de la propiedad que genera la excepción 
	 * @param valor Valor que se intenta asignar a la propiedad
	 * @param maxValorPermitido Valor más grande que admite la propiedad en cuestión
	 */
	public DesbordamientoCampoException (String tipoObjeto, String propiedad, Object valor, Object maxValorPermitido){
		super ("La propiedad " + propiedad + " del objeto " + tipoObjeto + " admite como valor máximo " +
				maxValorPermitido.toString() + ", mientras que el valor especificado es " + valor.toString(), 
				tipoObjeto, propiedad, valor);
		this.maxValorPermitido = maxValorPermitido;
	}

	/** Retorna el máximo valor permitido para el campo por el que se ha generado la excepción
	 * @return Máximo valor permitido para el campo
	 */
	public Object getMaxValorPermitido() {
		return maxValorPermitido;
	}
	
	
}
