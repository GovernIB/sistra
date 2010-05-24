package es.caib.bantel.model;

import java.io.Serializable;

public class CriteriosBusquedaTramite implements Serializable
{
	public static final char TODOS = 'T';
	
	private int anyo;
	private int mes;
	private String usuarioNif;
	private String usuarioNombre;
	private char tipo = TODOS;
	private char procesada = TODOS;
	private char nivelAutenticacion = TODOS;
	private String identificadorTramite;
		
	public String getIdentificadorTramite()
	{
		return identificadorTramite;
	}
	public void setIdentificadorTramite(String identificadorTramite)
	{
		this.identificadorTramite = identificadorTramite;
	}
	public char getProcesada()
	{
		return procesada;
	}
	public void setProcesada(char procesada)
	{
		this.procesada = procesada;
	}
	public char getTipo()
	{
		return tipo;
	}
	public void setTipo(char tipo)
	{
		this.tipo = tipo;
	}
	public String getUsuarioNif()
	{
		return usuarioNif;
	}
	public void setUsuarioNif(String usuarioNif)
	{
		this.usuarioNif = usuarioNif;
	}
	public String getUsuarioNombre()
	{
		return usuarioNombre;
	}
	public void setUsuarioNombre(String usuarioNombre)
	{
		this.usuarioNombre = usuarioNombre;
	}
	public void setAnyo(int anyo)
	{
		this.anyo = anyo;
	}
	public void setMes(int mes)
	{
		this.mes = mes;
	}
	public int getAnyo()
	{
		return anyo;
	}
	public int getMes()
	{
		return mes;
	}
	public char getNivelAutenticacion()
	{
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(char nivelAutenticacion)
	{
		this.nivelAutenticacion = nivelAutenticacion;
	}	
}
