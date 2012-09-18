package es.caib.audita.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CuadroMandoDetalle implements Serializable {
	
	private String titulo;
	private String temporal;
	private List detalleOrganismos = new ArrayList();
	private int total = 0;
	
	public List getDetalleOrganismos() {
		return detalleOrganismos;
	}
	public void setDetalleOrganismos(List detalleOrganismos) {
		this.detalleOrganismos = detalleOrganismos;
	}
	public String getTemporal() {
		return temporal;
	}
	public void setTemporal(String temporal) {
		this.temporal = temporal;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void increment(int value)
	{
		total += value;
	}
	public int getTotal() {
		return total;
	}

	public DetalleOrganismo getOrganismo(String organismo)
	{
		DetalleOrganismo do_organismo = null;
		for(int i=0; i<this.detalleOrganismos.size(); i++)
		{
			do_organismo = (DetalleOrganismo) this.detalleOrganismos.get(i);
			if(do_organismo.getTitulo().equals(organismo))
			{
				return do_organismo;
			}
		}
		return null;
	
	}
	
	public void add(DetalleOrganismo organismo)
	{
		this.detalleOrganismos.add(organismo);
	}
	
}
