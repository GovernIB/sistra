package es.caib.audita.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LineaDetalle implements Serializable
{
	private String titulo;
	private String total;
	private String visualizacion;
	private String modelo;
	private Map totales = new HashMap();
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getVisualizacion() {
		return visualizacion;
	}
	public void setVisualizacion(String visualizacion) {
		this.visualizacion = visualizacion;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void addTotal(String tipo, int value)
	{
		totales.put(tipo, new Integer(value));
	}
	
	public int getTotal(String tipo)
	{
		if(totales.containsKey(tipo))
		{
			Integer i_total = (Integer) totales.get(tipo);
			return i_total.intValue();
		}
		return 0;
	}
	
	public void increment(int value)
	{
		int i_total = (total != null) ? Integer.parseInt(total) : 0;
		i_total += value;
		total = String.valueOf(i_total);
	}

	
}