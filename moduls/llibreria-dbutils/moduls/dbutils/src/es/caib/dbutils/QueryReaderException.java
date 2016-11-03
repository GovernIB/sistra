/*
 * Created on 04-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.caib.dbutils;

/**
 * @author u990250
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryReaderException extends Exception {
	
	private Exception underlying;
	
	public QueryReaderException()
	{
		super();
	}
	
	public QueryReaderException( String message)
	{
		super( message );
	}
	
	public QueryReaderException( String message, Exception cause )
	{
		super( message );
		this.underlying = cause;
	}

	public Exception getUnderlying()
	{
		return underlying;
	}

	public void setUnderlying(Exception underlying)
	{
		this.underlying = underlying;
	}


}
