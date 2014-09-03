package es.caib.audita.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CuadroMandoTablaCruzada implements Serializable {
	
	private List organismos = new ArrayList();
	
	private String titulo = null;
	
	private List totales = new ArrayList();
	
	private int suma = 0;

	public List getOrganismos() {
		return organismos;
	}

	public void setOrganismos(List organismos) {
		this.organismos = organismos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List getTotales() {
		return totales;
	}

	public void setTotales(List totales) {
		this.totales = totales;
	}
	
	public TablaCruzadaOrganismo getOrganismo(String organismo)
	{
		TablaCruzadaOrganismo tco_organismo = null;
		for(int i=0; i<this.organismos.size(); i++)
		{
			tco_organismo = (TablaCruzadaOrganismo) this.organismos.get(i);
			if(tco_organismo.getDescripcion().equals(organismo))
			{
				return tco_organismo;
			}
		}
		return null;
	
	}
	
	public void addOrganismo(TablaCruzadaOrganismo organismo)
	{
		this.organismos.add(organismo);
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
