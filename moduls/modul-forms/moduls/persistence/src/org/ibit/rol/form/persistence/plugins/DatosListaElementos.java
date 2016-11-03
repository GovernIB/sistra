package org.ibit.rol.form.persistence.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mozilla.javascript.NativeArray;

/**
 * 
 * Variable que me permite insertar elementos en un componente de lista de elementos 
 * al ejecutar expresiones de autorrellenable y autocalculo
 * 
 */
public class DatosListaElementos {
	
	private List elementos = new ArrayList();
	
	/**
	 *  Crea elemento
	 * @return Devuelve indice elemento (1..n)
	 */
	public int addElemento(){
		// Crea espacio para elemento
		elementos.add(new HashMap());
		return elementos.size();
	}
	
	/**
	 * Mete dato para un elemento
	 * @param indiceElemento Indice elemento
	 * @param column Columna
	 * @param data Dato
	 */
	public void setDatoElemento(int indiceElemento,String column,String data){
		if (indiceElemento > elementos.size()) return;
		HashMap dataElemento = (HashMap) elementos.get(indiceElemento - 1);
		dataElemento.put(column,data);		
	}
	
	/**
	 * Mete dato multivaluado para un elemento
	 * @param indiceElemento Indice elemento
	 * @param column Columna
	 * @param data Dato
	 */
	public void setDatoMultivaluadoElemento(int indiceElemento,String column, NativeArray data){
		if (indiceElemento > elementos.size()) return;
		HashMap dataElemento = (HashMap) elementos.get(indiceElemento - 1);
		
		// Pasamos NativeArray a List
		List dataList = new ArrayList();
		Object [] ids = data.getIds();
		for ( int i = 0; i < ids.length; i++ )
		{
			Object valor = data.get( (( Integer ) ids[i] ).intValue() , data ); 
			dataList.add( valor.toString() );
		}
		
		// Guardamos datos		
		dataElemento.put(column,dataList);		
	}
	
	/**
	 * Obtiene dato de elemento
	 */
	public Object getDatoElemento(int indiceElemento, String column) {
		if (indiceElemento > elementos.size()) return null;
		HashMap dataElemento = (HashMap) elementos.get(indiceElemento - 1);
		return dataElemento.get(column);		
	}
	
	/**
	 * Obtiene numero de elementos
	 */
	public int geNumeroElementos() {
		return elementos.size();			
	}

}
