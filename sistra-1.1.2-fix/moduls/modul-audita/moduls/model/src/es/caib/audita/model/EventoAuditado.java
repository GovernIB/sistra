package es.caib.audita.model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventoAuditado implements Serializable
{
	private String descripcion;
	private int total;
	private Map totalesIdioma = new LinkedHashMap();
	//private Map totalesNivelAutenticacion = new LinkedHashMap();
	private Map totalesNivelAutenticacion = new Hashtable();
	private String ayuda;
	private String opcionesVisualizacion;
	private String codigoVisualizacionParticular;
	private String tipo;
	
	private int orden;
	
	public String getAyuda()
	{
		return ayuda;
	}
	public void setAyuda(String ayuda)
	{
		this.ayuda = ayuda;
	}
	public String getCodigoVisualizacionParticular()
	{
		return codigoVisualizacionParticular;
	}
	public void setCodigoVisualizacionParticular(
			String codigoVisualizacionParticular)
	{
		this.codigoVisualizacionParticular = codigoVisualizacionParticular;
	}
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public String getOpcionesVisualizacion()
	{
		return opcionesVisualizacion;
	}
	public void setOpcionesVisualizacion(String opcionesVisualizacion)
	{
		this.opcionesVisualizacion = opcionesVisualizacion;
	}
	public int getTotal()
	{
		return total;
	}
	public void setTotal(int total)
	{
		this.total = total;
	}
	public Map getTotalesIdioma()
	{
		return totalesIdioma;
	}
	public void setTotalesIdioma(LinkedHashMap totalIdioma)
	{
		this.totalesIdioma = totalIdioma;
	}
	public void setTotalIdioma( String lang, int total )
	{
		totalesIdioma.put( lang, new Integer( total ) );
	}
	public int getTotalIdioma( String lang )
	{
		Integer intTotalIdioma = ( Integer ) totalesIdioma.get( lang );
		return intTotalIdioma != null ? intTotalIdioma.intValue() : 0;
	}
	public Map getTotalesNivelAutenticacion()
	{
		return totalesNivelAutenticacion;
	}
	public void setTotalesNivelAutenticacion(LinkedHashMap totalNivelAutenticacion)
	{
		this.totalesNivelAutenticacion = totalNivelAutenticacion;
	}
	public void setTotalNivelAutenticacion( char nivelAutenticacion, int total )
	{
		totalesNivelAutenticacion.put( String.valueOf(nivelAutenticacion), new Integer( total ) );
	}
	public int getTotalNivelAutenticacion( char nivelAutenticacion )
	{
		Integer intTotalIdioma = ( Integer ) totalesNivelAutenticacion.get( String.valueOf(nivelAutenticacion) );
		return intTotalIdioma != null ? intTotalIdioma.intValue() : 0;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
	
	
}
