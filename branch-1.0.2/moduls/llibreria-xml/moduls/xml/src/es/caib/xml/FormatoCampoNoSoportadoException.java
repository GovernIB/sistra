package es.caib.xml;


/** Excepción para instanciar errores producidos al establecer una
 *  propiedad con un formato no válido 
 * 
 * @author magroig
 *
 */
public class FormatoCampoNoSoportadoException extends
		EstablecerPropiedadException {
	
	private String formato;

	/** Constructor
	 * @param formato Formato que debe cunplir el valor de la propiedad
	 * @param tipoObjeto Ientificación del objeto XML donde se produce el error
	 * @param propiedad Nombre de la propiedad en la cual al cambiar su valor se 
	 * produce el error
	 * @param valor Valor que se intenta asignar a la propiedad
	 */
	public FormatoCampoNoSoportadoException(String formato, String tipoObjeto,
			String propiedad, Object valor) {
		
		super("El valor " + valor.toString() + " asignado a la propiedad "
				+ propiedad + " del objeto " + tipoObjeto + " no tiene un " +
				"formato adecuado", 
				tipoObjeto, propiedad, valor);

	}

	/** Obtiene el formato que debía cumplir el valor de la propiedad
	 * @return Formato que debía cumplir el valor de la propiedad
	 */
	public String getFormato() {
		return formato;
	}

}
