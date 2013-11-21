/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.caib.dbutils;

import java.util.Map;


import org.apache.commons.dbutils.QueryLoader;

/**
 * @author u990250
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbUtilQueryReader implements QueryReader 
{
	
	private static String DEFAULT_RESOURCE_EXTENSION = ".properties";
	private String resourceExtension = DEFAULT_RESOURCE_EXTENSION;
	
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryReader#init(java.util.Map)
	 */
	public void init(Map initProperties) throws QueryReaderException 
	{
		if ( initProperties != null )
		{
			resourceExtension = ( String ) initProperties.get( "database.DbUtilQueryReader.RESOURCE_EXTENSION" );
			resourceExtension = resourceExtension == null || ( resourceExtension != null && "".equals( resourceExtension.trim() ) ) ?  DEFAULT_RESOURCE_EXTENSION : resourceExtension ;
		}
	}

	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryReader#getQuery(java.lang.String, java.lang.String)
	 */
	public String getQuery(String strDaoResourceName, String key)
			throws QueryReaderException 
	{
		try
		{
			Map queryMap = QueryLoader.instance().load( strDaoResourceName );
			return ( String ) queryMap.get( key );
		}
		catch ( java.io.IOException exc )
		{
			throw new QueryReaderException( "Can't retrieve the queries file " + strDaoResourceName, exc );
		}
	}
	
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryReader#getQuery(java.lang.Class, java.lang.String)
	 */
	public String getQuery(Class daoClass, String key) throws QueryReaderException 
	{
		String resourceName = getRealResourceName( daoClass.getName() ); 
		return getQuery( resourceName, key );
	}
	
	private String getRealResourceName( String resourceName )
	{
		resourceName = resourceName.replaceAll( "Facade.*$", "" );
		resourceName = resourceName.replaceAll( "\\.test\\.", ".ejb." );
		return "/" + resourceName.replaceAll( "\\.", "/" ) + resourceExtension;
	}

}
