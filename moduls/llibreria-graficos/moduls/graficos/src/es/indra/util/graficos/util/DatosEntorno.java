package es.indra.util.graficos.util;

import java.io.FileInputStream;
import java.util.Properties;

import javax.naming.*;

/**
 * Clase encargada de obtener información de los parámetros 
 * de entorno vía JNDI
 * @author Indra Sistema S.A.
 * @version 1.1
 **/
public class DatosEntorno {
	
	private static String pathAplicacion;
	private static String pathConfiguracion;
	

	private static Object getVariableEntorno(String as_variable)
	{
		try
		{
			InitialContext ic = new InitialContext();
			Context envCtx = (Context) ic.lookup("java:comp/env");						
			Object lo_valor = envCtx.lookup(as_variable);

			return lo_valor;

		}
		catch (Throwable e)
		{
			return null; 
		}
	}
	
	public static String getVariableConfiguracion(String as_variable)
	{
		FileInputStream l_fis = null;
		try
		{
			// Valor del path se calcula automaticamente
			if (as_variable.equals("pathApp")) return pathAplicacion;
			
			// Obtenemos valor variable del fichero de configuracion
			String ls_valor = null;
			if (pathConfiguracion == null){
				pathConfiguracion = (String) getVariableEntorno("pathConfiguracion");
				pathConfiguracion = FuncionesCadena.getPathAbsoluto(pathConfiguracion);
			}
			
			l_fis = new FileInputStream(pathConfiguracion);
			Properties l_props = new Properties();
			l_props.load(l_fis);
			ls_valor = l_props.getProperty(as_variable);
			if (ls_valor == null)
			{
				return null;
			}
			
			// Si es un path devolvemos el path absoluto
			if (as_variable.startsWith("path"))
				ls_valor = FuncionesCadena.getPathAbsoluto(ls_valor);

			return ls_valor;
		}
		catch (Throwable e)
		{
			return null; 
		}
		finally
		{
			if (l_fis != null)
			{
				try
				{
					l_fis.close();
				}
				catch (Throwable ex)
				{
					
				}
			}
		}
	}
		
	/**
	 * @return Devuelve pathAplicacion.
	 */
	public static String getPathAplicacion() {
		return pathAplicacion;
	}
	/**
	 * @param pathAplicacion El pathAplicacion a establecer.
	 */
	public static void setPathAplicacion(String pathAplicacion) {
		DatosEntorno.pathAplicacion = pathAplicacion;
	}
}

