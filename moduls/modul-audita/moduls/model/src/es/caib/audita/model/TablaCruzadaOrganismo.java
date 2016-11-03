package es.caib.audita.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TablaCruzadaOrganismo implements Serializable
{
	private String descripcion;
	private List modelos = new ArrayList();
	private List totales = new ArrayList();
	private int suma = 0;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List getModelos() {
		return modelos;
	}

	public void setModelos(List modelos) {
		this.modelos = modelos;
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
	
	public TablaCruzadaModelo getModelo(String modelo)
	{
		TablaCruzadaModelo tcm = null;
		for(int i=0; i<this.modelos.size(); i++)
		{
			tcm = (TablaCruzadaModelo) this.modelos.get(i);
			if(tcm.getDescripcion().equals(modelo))
			{
				return tcm;
			}
		}
		return null;
	
	}
	
	public void addModelo(TablaCruzadaModelo modelo)
	{
		this.modelos.add(modelo);
	}

	public int getSuma() {
		return suma;
	}

	public void setSuma(int suma) {
		this.suma = suma;
	}


	public void increment(int value)
	{
		this.suma += value;
	}

	
}