package es.caib.audita.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetalleOrganismo implements Serializable
{
	private String titulo;
	private String total = "0";
	private List lineasDetalle = new ArrayList();
	public List getLineasDetalle() {
		return lineasDetalle;
	}
	public void setLineasDetalle(List lineasDetalle) {
		this.lineasDetalle = lineasDetalle;
	}
	
	public void addLineaDetalle(LineaDetalle ld)
	{
		this.lineasDetalle.add(ld);
	}
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
	
	public boolean isLineaDetalle(String modelo)
	{
		for(int i=0; i<lineasDetalle.size(); i++)
		{
			LineaDetalle linea = (LineaDetalle)lineasDetalle.get(i);
			if(linea.getModelo().equals(modelo))
			{
				return true;
			}
		}
		return false;
	}
	
	public LineaDetalle getLineaDetalle(String modelo)
	{
		if(!isLineaDetalle(modelo)) return null;
		for(int i=0; i<lineasDetalle.size(); i++)
		{
			LineaDetalle linea = (LineaDetalle)lineasDetalle.get(i);
			if(linea.getModelo().equals(modelo))
			{
				return linea;
			}
		}
		return null;
	}


	public void increment(int value)
	{
		int i_total = Integer.parseInt(total);
		i_total += value;
		this.total = String.valueOf(i_total);
	}

	
}