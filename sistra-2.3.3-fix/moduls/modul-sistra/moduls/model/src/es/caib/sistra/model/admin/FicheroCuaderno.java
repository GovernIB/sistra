package es.caib.sistra.model.admin;

import java.io.Serializable;

public class FicheroCuaderno implements Serializable
{
	public static String TIPO_TRAMITE = "T";
	public static String TIPO_FORMULARIO = "F";
	public static String TIPO_DOMINIO = "D";
	private CuadernoCarga cuadernoCarga;
	private Long codigo;
	private String tipo = "T"; // T -> Tramite F -> Formulario D -> Dominio
	private String nombre;
	private byte[] contenido;
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	public byte[] getContenido()
	{
		return contenido;
	}
	public void setContenido(byte[] contenido)
	{
		this.contenido = contenido;
	}
	public CuadernoCarga getCuadernoCarga()
	{
		return cuadernoCarga;
	}
	public void setCuadernoCarga(CuadernoCarga cuadernoCarga)
	{
		this.cuadernoCarga = cuadernoCarga;
	}
	public String getTipo()
	{
		return tipo;
	}
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	} 
	public boolean isTramite()
	{
		return TIPO_TRAMITE.equals( tipo );
	}
	public boolean isDominio()
	{
		return TIPO_DOMINIO.equals( tipo );
	}
	public boolean isForm()
	{
		return TIPO_FORMULARIO.equals( tipo );
	}
	public String getIdentifier()
	{
		String idTramite = null;
		
		if ( nombre != null )
		{
			idTramite = nombre.replaceAll( "\\..*$", "" );
			String [] partsNom = idTramite.split( "\\-" );
			if ( partsNom.length == 2 )
			{
				idTramite = partsNom[1];
			}
			else if ( partsNom.length == 3 ) 
			{
				idTramite = partsNom[1] + " v" + partsNom[2];
			}
		}
		return idTramite;
	}
	
}
