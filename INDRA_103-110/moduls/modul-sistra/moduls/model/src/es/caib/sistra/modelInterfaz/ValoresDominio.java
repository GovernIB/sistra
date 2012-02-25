package es.caib.sistra.modelInterfaz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que modeliza los valores de un dominio
 */
public class ValoresDominio  implements Serializable{
	
	/**
	 * Filas de valores
	 */
	private List filas = new ArrayList();
	
	/**
	 * Indica si ha habido error
	 */
	private boolean error;
	
	/**
	 * Texto error
	 */
	private String descripcionError;
	
	/**
	 * Añade una fila de valores
	 * @return Numero de fila añadida
	 */
	public int addFila(){
		HashMap fila = new HashMap();
		filas.add(fila);
		return filas.size();
	}
	
	/**
	 * Establece el valor de una columna en una fila
	 * @param numfila Numero de fila (empiezan desde 1)
	 * @param cod Nombre columna
	 * @param val Valor
	 */
	public void setValor(int numfila,String cod,String val){
		HashMap fila = (HashMap) filas.get(numfila - 1);
		fila.put(cod.toUpperCase(),val);			
	}
	
	/**
	 * Obtiene el valor de una columna en una fila.
	 * Si el valor del campo es nulo se devuelve cadena vacía.
	 * @param numfila Numero de fila (empiezan desde 1)
	 * @param cod Nombre columna
	 * @return Valor columna para la fila seleccionada
	 */
	public String getValor(int numfila,String cod){
		HashMap fila = (HashMap) filas.get(numfila - 1);
		String valor = (String) fila.get(cod.toUpperCase());
		if (valor == null) return "";
		return valor;
	}
		
	/**
	 * Obtiene numero de filas
	 * @return Numero de filas
	 */
	public int getNumeroFilas(){
		return filas.size();
	}
	
	/**
	 * Imprime el dominio por la consola
	 *
	 */
	public void print(){
		for (int i=0;i<filas.size();i++){
			HashMap fila = (HashMap) filas.get(i);
			String ls_fila="";
			for (Iterator it = fila.keySet().iterator();it.hasNext();){
				String ls_key = (String) it.next();
				ls_fila += "[" + ls_key.toUpperCase() + "=" + fila.get(ls_key.toUpperCase()) + "]  ";				
			}
			System.out.println(ls_fila);
		}
	}

	/**
	 * Obtiene List con las filas
	 * @return List
	 */
	public List getFilas() {
		return filas;
	}

	/**
	 * Establece lista
	 * @param List con las filas
	 */
	public void setFilas(List pfilas) {
		// Aseguramos que los codigos de columnas esten en mayusculas
		resetear();
		if (pfilas != null) {
			int numfila;
			for (int i=0;i<pfilas.size();i++){
				HashMap fila = (HashMap) pfilas.get(i);
				numfila = addFila();
				for (Iterator it = fila.keySet().iterator();it.hasNext();){
					String ls_key = (String) it.next();
					setValor(numfila, ls_key.toUpperCase(), (String) fila.get(ls_key.toUpperCase()));							
				}				
			}
		}	
	}

	/**
	 * Descripcion del error
	 * @return
	 */
	public String getDescripcionError() {
		return descripcionError;
	}

	/**
	 * Descripcion del error
	 * @param descripcionError
	 */
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	/**
	 * Indica si hay error
	 * @return
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Indica si hay error
	 * @param error
	 */
	public void setError(boolean error) {
		this.error = error;
	}
	
	/**
	 * Resetea valores.
	 */
	public void resetear() {
		this.filas = new ArrayList();
	}
		
	
}
