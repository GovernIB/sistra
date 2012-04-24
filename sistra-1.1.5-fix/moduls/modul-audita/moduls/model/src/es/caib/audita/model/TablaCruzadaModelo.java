package es.caib.audita.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TablaCruzadaModelo implements Serializable
{
	private String descripcion;
	private List totales = new ArrayList();
	private int suma = 0;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public int getSuma() {
		return suma;
	}

	public void setSuma(int suma) {
		this.suma = suma;
	}
	
	public void increment(int valor)
	{
		this.suma += valor;
	}


	public List getTotales() {
		return totales;
	}

	public void setTotales(List totales) {
		this.totales = totales;
	}

	public void increment(String key, int value)
	{
		for(int i=0; i<this.totales.size(); i++)
		{
			Map datos = (Map) totales.get(i);
			if(datos.containsKey(key))
			{
				int i_total = Integer.parseInt((String) datos.get(key));
				i_total += value;
				datos.put(key,String.valueOf(i_total));
				return;
			}
		}
	}
	

	
}