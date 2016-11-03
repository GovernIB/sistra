package es.caib.audita.persistence.util.evento;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParticularidadesVisualizacionFactoria
{
	private Map registroHandlers = new HashMap();
	private static ParticularidadesVisualizacionFactoria instance;
	private ParticularidadesVisualizacionFactoria()
	{
		
	}
	
	public static ParticularidadesVisualizacionFactoria getInstance()
	{
		return ( instance == null ? instance = new ParticularidadesVisualizacionFactoria() : instance );   
	}
	
	public ParticularidadesVisualizacionHandler getHandler( String handlerName, Connection con, String modo, String tipoEvento, Date dateInicial, Date dateFinal, String idioma ) throws Exception
	{
		Class clazz = ( Class ) registroHandlers.get( handlerName );
		if ( clazz == null )
		{
			clazz = Class.forName( handlerName );
			if ( !ParticularidadesVisualizacionHandler.class.isAssignableFrom( clazz ) )
			{
				throw new Exception( "La clase " + clazz.getName() + " no implementa el interfaz " + ParticularidadesVisualizacionHandler.class.getName() );
			}
			registroHandlers.put( handlerName, clazz );
		}
		ParticularidadesVisualizacionHandler object = ( ParticularidadesVisualizacionHandler ) clazz.newInstance();
		object.init( con,  modo, tipoEvento, dateInicial, dateFinal, idioma);
		return object;
	}
	
	
}
