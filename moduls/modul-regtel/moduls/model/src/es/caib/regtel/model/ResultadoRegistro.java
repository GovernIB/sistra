package es.caib.regtel.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Modeliza el resultado ofrecido por la capa web de registro: número y fecha de registro
 *
 */
public class ResultadoRegistro implements Serializable
{
	/**
	 * Número de registro
	 */
	String numeroRegistro;
	/**
	 * Fecha de registro (formato yyyyMMddHHmmss)
	 */
	String fechaRegistro;
	
	/**
	 * Fecha de registro (formato yyyyMMddHHmmss)
	 * @return
	 */
	public String getFechaRegistro()
	{
		return fechaRegistro;		
	}
	/**
	 * Fecha de registro (formato yyyyMMddHHmmss)
	 * @param fechaRegistro
	 */
	public void setFechaRegistro(String fechaRegistro)
	{
		this.fechaRegistro = fechaRegistro;		
	}
	/**
	 * Número de registro
	 * @return
	 */
	public String getNumeroRegistro()
	{
		return numeroRegistro;
	}
	/**
	 * Número de registro
	 * @param numeroRegistro
	 */
	public void setNumeroRegistro(String numeroRegistro)
	{
		this.numeroRegistro = numeroRegistro;
	}
	
	public String toString()
	{
		try
		{
			StringBuffer sb = new StringBuffer( "{" );
			Map properties = BeanUtils.describe( this );
			for ( Iterator it = properties.keySet().iterator(); it.hasNext(); )
			{
				Object key = it.next();
				Object value = properties.get( key );
				sb.append( key.toString() ).append( "=" ).append( value != null ? value.toString() : null ).append( it.hasNext() ? ", " : "") ; 
			}
			sb.append( "}" );
			return sb.toString();
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
			return super.toString();
		}
	}
}
