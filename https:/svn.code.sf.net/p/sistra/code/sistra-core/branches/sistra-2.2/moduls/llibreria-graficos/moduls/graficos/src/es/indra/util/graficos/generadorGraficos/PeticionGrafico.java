package es.indra.util.graficos.generadorGraficos;

import java.util.HashMap;

/**
 * Datos de una petición al sistema de Gestion de Datos para
 * resolver los datos de un gráfico
 */
public class PeticionGrafico {

	/**
	 * Identificador del gráfico
	 */
	private String idGrafico;
	/**
	 * Parametros del gráfico
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
