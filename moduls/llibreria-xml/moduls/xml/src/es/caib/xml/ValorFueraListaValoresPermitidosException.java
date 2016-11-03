package es.caib.xml;


/** Excepción que modeliza el error producido al intentar establecer una
 * propiedad cuyo valor no es uno de la lista de valores permitidos para
 * dicha propiedad
 * 
 * @author magroig
 *
 */
public class ValorFueraListaValoresPermitidosException extends
		EstablecerPropiedadException {
	
	Object listaValoresPermitidos[];

	/** Constructor de la excepción
	 * @param tipoObjeto Identificador del tipo del objeto problemático
	 * @param propiedad Propiedad (campo) que origina el problema
	 * @param valor Valor que no es válido
	 * @param listaValoresPermitidos Conjunto de valores permitidos para
	 * el campo
	 */
	public ValorFueraListaValoresPermitidosException(String tipoObjeto,
			String propiedad, Object valor, Object listaValoresPermitidos[]) {					
		
		super("Se ha intentado establecer la propiedad " + propiedad + 
				" del objeto " + tipoObjeto + " con el valor " + valor.toString() +
				" que no es uno de los valores permitridos para esta propiedad", 
				tipoObjeto, propiedad, valor);
		
		this.listaValoresPermitidos = listaValoresPermitidos; 
	}

	/** Retorna el conjunto de valores permitidos para el campo
	 * @return Valores permitidos para el campo
	 */
	public Object[] getListaValoresPermitidos() {
		return listaValoresPermitidos;
	}

}
