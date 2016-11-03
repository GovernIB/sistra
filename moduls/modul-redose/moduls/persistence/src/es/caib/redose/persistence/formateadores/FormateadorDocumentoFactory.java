package es.caib.redose.persistence.formateadores;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FormateadorDocumentoFactory
{
	protected static Log log = LogFactory.getLog(FormateadorDocumentoFactory.class);
	private static FormateadorDocumentoFactory instance;
	private HashMap hsmClases;
	
	public static FormateadorDocumentoFactory getInstance()
	{
		return instance != null ? instance : ( instance = new FormateadorDocumentoFactory() );
	}
	
	private FormateadorDocumentoFactory()
	{
		hsmClases = new HashMap();
	}
	
	public FormateadorDocumento getFormateador( String formateadorClassName ) throws ClassNotFoundException, IllegalAccessException, Exception
	{
		FormateadorDocumento formateador = ( FormateadorDocumento ) hsmClases.get( formateadorClassName );
		if ( formateador != null ) return formateador;
		Class formateadorClazz = Class.forName( formateadorClassName );
		
		if ( !FormateadorDocumento.class.isAssignableFrom( formateadorClazz ) )
		{
			throw new Exception( "La clase " + formateadorClassName + " no implementa al interfaz " + FormateadorDocumento.class.getName() );
		}
		formateador = ( FormateadorDocumento ) formateadorClazz.newInstance();
		hsmClases.put( formateadorClassName, formateador );
		return formateador;
	}
}
