package es.indra.util.graficos.generadorGraficos;

import java.util.HashMap;

/**
 * Datos de una petici�n al sistema de Gestion de Datos para
 * resolver los datos de un gr�fico
 */
public class PeticionGrafico {

	/**
	 * Identificador del gr�fico
	 */
	private String idGrafico;
	/**
	 * Parametros del gr�fico
	 */
	private HashMap parametros=new HashMap();	
	
	/**
	 * @return Devuelve idGrafico.
	 */
	public String getIdGrafico() {
		return idGrafico;
	}
	/**
	 * @param idGrafico El idGrafico a establecer.
	 */
	public void setIdGrafico(String idGrafico) {
		this.idGrafico = idGrafico;
	}
	/**
	 * @return Devuelve parametros.
	 */
	public HashMap getParametros() {
		return parametros;
	}
	
	public void addParametro(String as_param,String as_valor){
		this.getParametros().put(as_param,as_valor);
	}
}
