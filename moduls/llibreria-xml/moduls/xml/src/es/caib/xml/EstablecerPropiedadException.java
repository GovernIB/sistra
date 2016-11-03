package es.caib.xml;



/** Excepción que modeliza un error al establecer una propiedad de un
 * objeto XML
 * 
 * @author magroig
 *
 */
public class EstablecerPropiedadException extends XMLException {
	private String tipoObjeto;
	private String propiedad;
	private Object valor;
	
	/** Construye la excepción a partir del mensaje de error subyacente, una
	 * constante describiendo el tipo de objeto XML en el que se ha provocado
	 * la excepción, y la propiedad y el valor afectados
	 * @param msg Mensaje de error subyacente
	 * @param tipoObjeto Cte identificando el objeto XML afectado
	 * @param propiedad Nombre de propiedad afectada
	 * @param valor Valor que se ha intentado establecer
	 */
	public EstablecerPropiedadException(String msg, String tipoObjeto, String propiedad, Object valor){
		super ("Se ha producido un error al establecer la propiedad " +
				propiedad + " con el valor " + ((valor != null) ? valor.toString() : "Null") + " del tipo de objeto " + tipoObjeto +
				( ( (msg != null) && (msg.trim().length() > 0) ) ? 
						" [" + msg + "]"
						:
						"" ));
		
		this.tipoObjeto = tipoObjeto;
		this.propiedad = propiedad;
		this.valor = valor;
	}

	/** Obtiene la propiedad afectada
	 * @return Propiedad afectada
	 */
	public String getPropiedad() {
		return propiedad;
	}

	/** Obtiene una cte identificando al objetro afectado por la excepción
	 * @return cte identificando al objetro afectado por la excepción
	 */
	public String getTipoObjeto() {
		return tipoObjeto;
	}

	/** Obtiene el valor que se ha intentado establecer
	 * @return Valor que se ha intentado establecer
	 */
	public Object getValor() {
		return valor;
	}

}
