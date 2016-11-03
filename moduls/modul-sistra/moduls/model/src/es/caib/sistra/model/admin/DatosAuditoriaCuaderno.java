package es.caib.sistra.model.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DatosAuditoriaCuaderno implements Serializable
{
	private Set listaDominios 			= new java.util.LinkedHashSet();
	private List listaScriptsAuditar 	= new java.util.ArrayList();
	private boolean auditoriaRequerida 	= false;
	public Set getListaDominios()
	{
		return listaDominios;
	}
	public void setListaDominios(Set listaDominios)
	{
		this.listaDominios = listaDominios;
	}
	public List getListaScriptsAuditar()
	{
		return listaScriptsAuditar;
	}
	public void setListaScriptsAuditar(List listaScriptsAuditar)
	{
		this.listaScriptsAuditar = listaScriptsAuditar;
	}
	public boolean isAuditoriaRequerida()
	{
		return auditoriaRequerida;
	}
	public void setAuditoriaRequerida(boolean auditoriaRequerida)
	{
		this.auditoriaRequerida = auditoriaRequerida;
	}

}
