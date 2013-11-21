/**
 * 
 */
package es.caib.xml;

import java.io.Serializable;
import java.util.regex.Pattern;

/** Clase base con utilidades comunes para la implementacion de todos
 *  los objetos que modelizan un nodo XML
 * @author magroig
 *
 */
public abstract class NodoBase implements Serializable {

	/** Valida que no se intenta establecer un valor a una propiedad cuya longitud
	 *  exceda la máxima permitida por el campo
	 *  
	 * @param tipoObjeto Identificador del tipo de objeto XML
	 * @param nombreCampo Nombre de la propiedad o campo
	 * @param valorCampo Valor de la propiedad que se intenta establecer
	 * @param maximaLongitudCampo Máxima longitud del campo permitida
	 * 
	 * @throws DesbordamientoCampoException Si el valor del campo excede el máximo valor permitido para el campo
	 */
	protected void validaLongitudCampo (String tipoObjeto, String nombreCampo, Object valorCampo, int maximaLongitudCampo) 
		throws DesbordamientoCampoException 
	{		
		//Para validar que el valor no sea nulo, usar validación validaCampoObligatorio
		if (valorCampo == null) return;
		
		if (valorCampo.toString().length() > maximaLongitudCampo)
			throw new DesbordamientoCampoException (tipoObjeto, nombreCampo, valorCampo, new Integer(maximaLongitudCampo));				
	}
	
	/** Valida que no se intente establecer un valor nulo o vacío a una propiedad
	 *  requerida en un objeto XML
	 *  
	 * @param tipoObjeto Identificador del tipo de objeto
	 * @param nombreCampo Nombre de la propiedad que se intenta establecer
	 * @param valorCampo Valor de la propiedad que se intenta asignar
	 * 
	 * @throws CampoObligatorioException El campo es obligatorio, y se el pasa un valor nulo, o
	 * no se ha establecido un valor para el campo, o el tipo del campo es String
	 * y el valor es una cadena de carácter/es vacía/os
	 */
	protected void validaCampoObligatorio (String tipoObjeto, String nombreCampo, Object valorCampo) throws CampoObligatorioException {
		if ( (valorCampo == null) || 
				( (valorCampo instanceof String) && ( ((String) valorCampo).trim().equals("")) )
			)
		{
			throw new CampoObligatorioException (tipoObjeto, nombreCampo);
		}		
	}
	
	/** Valida que no se intente establecer un valor nulo (VACÍO SI ES PERMITIDO) a una propiedad
	 *  requerida en un objeto XML
	 *  
	 * @param tipoObjeto Identificador del tipo de objeto
	 * @param nombreCampo Nombre de la propiedad que se intenta establecer
	 * @param valorCampo Valor de la propiedad que se intenta asignar
	 * 
	 * @throws CampoObligatorioException No se ha establecido un valor para el 
	 * campo, o el valor es un valor nulo
	 */
	protected void validaCampoNoNulo (String tipoObjeto, String nombreCampo, Object valorCampo) throws CampoObligatorioException {
		if (valorCampo == null){
			throw new CampoObligatorioException (tipoObjeto, nombreCampo);
		}		
	}
	
	/** Valida que el valor de la propiedad se encuentre en una lista de valores
	 *  permitidos para esa propiedad
	 *  
	 * @param tipoObjeto Identificador del tipo de objeto
	 * @param nombreCampo Nombre de la propiedad o campo
	 * @param valorCampo Valor de la propiedad que se intenta establecer
	 * @param listaValoresPermitidos Lista de valores que son aceptables como valor para la propiedad
	 * 
	 * @throws ValorFueraListaValoresPermitidosException El valor suministrado
	 * para el campo no es uno de los permitidos
	 */
	protected void validaCampoConListaValores (String tipoObjeto, String nombreCampo, Object valorCampo, Object listaValoresPermitidos[])
		throws ValorFueraListaValoresPermitidosException 
	{
		// Para validar que el valor no sea nulo, usar validación validaCampoObligatorio
		if (valorCampo == null) return;
		
		for (int i = 0; i < listaValoresPermitidos.length; i++){
			if (valorCampo.equals(listaValoresPermitidos[i])){
				// OK, el valor esta en la lista de valores permitidos
				return;
			}
		}
		
		// El valor no esta en la lista de valores permitidos, lanzar excepción
		throw new ValorFueraListaValoresPermitidosException (tipoObjeto, nombreCampo, valorCampo, listaValoresPermitidos);
		
	}
	
	
	/** Valida que el valor de un campo cumple con un determinado formato
	 * @param tipoObjeto Identificador del tipo de objeto
	 * @param nombreCampo Nombre de la propiedad o campo
	 * @param valorCampo Valor de la propiedad que se intenta establecer
	 * @param formato Formato que debe cumplir la propiedad
	 * @throws FormatoCampoNoSoportadoException El valor no cumple con el formato
	 *  dado
	 */
	protected void validaFormatoCampo (String tipoObjeto, String nombreCampo, Object valorCampo, String formato) throws FormatoCampoNoSoportadoException{
		//	Para validar que el valor no sea nulo, usar validación validaCampoObligatorio
		if ( (valorCampo == null) || (valorCampo.toString ().trim().equals(""))) return;
		
		Pattern patron = Pattern.compile (formato);
		if (!patron.matcher(valorCampo.toString()).matches())
			throw new FormatoCampoNoSoportadoException (formato, tipoObjeto, nombreCampo, valorCampo);
	}
				
	
	/** Funcion para simplificar la implementación de los métodos equals de los objetos de datos.
	 * Retorna true si dos objetos son 'equivalentes'
	 * @param obj1 Primer objeto a comparar 
	 * @param obj2 Segundo objeto a comparar
	 * @return Verdadero si los objetos son 'equivalentes'
	 */
	protected static boolean objetosIguales (Object obj1, Object obj2){
		if ((obj1 != null) || (obj2 != null))
			if ( (obj1 != null) && (obj2 != null) ){
				if (!obj1.equals (obj2)) return false;
			}
			else
				if ((obj1 != null) || (obj2 != null)) return false;
		
		return true;
	}

}
