/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.caib.dbutils;
import java.util.Map;

/**
 * @author u990250
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryReaderFactory 
{
	public static String queryReaderClassName = "es.caib.dbutils.DbUtilQueryReader";
	public static final String QUERY_READER_CLASS_KEY =  "database.QueryReaderFactory.readerClass";
	Class queryReaderClass;
	private static QueryReaderFactory instance;
	private Map initProperties;
	
	private QueryReaderFactory()
	{
		
	}
	
	private QueryReaderFactory( Map initProperties ) throws QueryReaderException
	{
		String className = null;
		try
		{
			if ( initProperties != null )
			{
				className = ( String ) initProperties.get( QUERY_READER_CLASS_KEY );
			}
			className = className == null || ( className != null && "".equals( className.trim() ) ) ?  queryReaderClassName : className ;  
			queryReaderClass = Class.forName( className );
			this.initProperties = initProperties;
		}
		catch ( ClassNotFoundException e )
		{
			throw new QueryReaderException( "Can not create the query reader " + className + ". Please check app configuration", e );
		}
	}
	
	public static QueryReaderFactory getInstance() throws QueryReaderException
	{
		return getInstance( null );
	}
	
	public static QueryReaderFactory getInstance( Map initProperties ) throws QueryReaderException
	{
		if ( instance == null )
		{
			instance = new QueryReaderFactory( initProperties );
		}
		return instance;
	}
	
	public QueryReader getQueryReader() throws QueryReaderException
	{
		try
		{
			QueryReader executor = ( QueryReader ) queryReaderClass.newInstance();
			executor.init( initProperties );
			return executor;
		}
		catch ( Exception e )
		{
			throw new QueryReaderException( "Can not create the query reader. " + queryReaderClass.getClass().getName() + " Please check app configuration", e );
		}
	}
}
