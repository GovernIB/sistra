package es.caib.xml;


/** Excepci�n pensada para ser lanzada cuando se intenta establecer un campo
 *  con un valor demasiado grande
 *  
 * @author magroig
 *
 */
public class DesbordamientoCampoException extends EstablecerPropiedadException {
	
	private Object maxValorPermitido;
	
	/** Constructor
	 * @param tipoObjeto Descripci�n o nombre del objeto XML sobre el que se produce la excepci�n
	 * @param propiedad Nombre de la propiedad que genera la excepci�n 
	 * @param valor Valor que se intenta asignar a la propiedad
	 * @param maxValorPermitido Valor m�s grande que admite la propiedad en cuesti�n
	 */
	public DesbordamientoCampoException (String tipoObjeto, String propiedad, Object valor, Object maxValorPermitido){
		super ("La propiedad " + propiedad + " del objeto " + tipoObjeto + " admite como valor m�ximo " +
				maxValorPermitido.toString() + ", mientras que el valor especificado es " + valor.toString(), 
				tipoObjeto, propiedad, valor);
		this.maxValorPermitido = maxValorPermitido;
	}

	/** Retorna el m�ximo valor permitido para el campo por el que se ha generado la excepci�n
	 * @return M�ximo valor permitido para el campo
	 */
	public Object getMaxValorPermitido() {
		return maxValorPermitido;
	}
	
	
}
