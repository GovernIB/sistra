package es.caib.sistra.front.formulario;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FactoriaGestorFlujoFormulario
{

	private HashMap hsmTiposGestoresFlujo;
	private static FactoriaGestorFlujoFormulario instance = null;
	private static Log log = LogFactory.getLog( FactoriaGestorFlujoFormulario.class );

	private FactoriaGestorFlujoFormulario()
	{
		hsmTiposGestoresFlujo = new HashMap();
	}

	public static FactoriaGestorFlujoFormulario getInstance()
	{
		if ( instance != null )
			return instance;
		return instance = new FactoriaGestorFlujoFormulario();
	}

	public GestorFlujoFormulario obtenerGestorFlujoFormulario ( String tipoGestor ) throws Exception
	{
		// Buscamos clase implementadora
		Class clazz = ( Class ) hsmTiposGestoresFlujo.get( tipoGestor );
		if ( clazz == null )
		{
			clazz = Class.forName( tipoGestor );
			if ( !GestorFlujoFormulario.class.isAssignableFrom( clazz ) )
			{
				throw new Exception( "La clase " + clazz.getName() + " no implementa el interfaz " + GestorFlujoFormulario.class.getName() );
			}
			hsmTiposGestoresFlujo.put( tipoGestor, clazz );

		}

		// Creamos una nueva instancia y la inicializamos
		GestorFlujoFormulario instancia = ( GestorFlujoFormulario ) clazz.newInstance();
		instancia.init(getConfigurationForClass(clazz));
		return instancia;
	}

	private Map getConfigurationForClass( Class clazz ) throws Exception
	{
		Properties result = new Properties();
		String resourceName = clazz.getName();
		resourceName = resourceName.substring( resourceName.lastIndexOf( '.' ) + 1 );
		resourceName +=  ".properties";
		result.load( clazz.getResourceAsStream( resourceName ) );
		return result;
	}



}
