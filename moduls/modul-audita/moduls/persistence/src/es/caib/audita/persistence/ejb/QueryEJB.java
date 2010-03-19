package es.caib.audita.persistence.ejb;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.dbutils.DbUtilQueryExecutor;
import es.caib.dbutils.QueryReader;
import es.caib.dbutils.QueryReaderException;
import es.caib.dbutils.QueryReaderFactory;

/**
 * Bean con la funcionalidad básica para interactuar con Base de datos.
 *
 */
public abstract class QueryEJB extends DbUtilQueryExecutor implements SessionBean
{
	protected static Log log = LogFactory.getLog(QueryEJB.class);
	// TODO : Obtenerlo de configuracion
	private static String JNDI_NAME = "java:/es.caib.audita.db";
	private QueryReader queryReader;
    protected SessionContext ctx = null;

    public void setSessionContext(SessionContext ctx) 
    {
        this.ctx = ctx;
        try
        {
        	queryReader = QueryReaderFactory.getInstance().getQueryReader();
        }
        catch ( QueryReaderException exc )
        {
        	log.error( exc );
        }
    }
    
    public void ejbCreate() throws CreateException 
    {
        log.info("ejbCreate: " + this.getClass());
    }

	public void ejbRemove() throws EJBException, RemoteException
	{
		log.info("ejbRemove: " + this.getClass());
	}
	
	protected String getQuery( String propertyName ) throws SQLException
	{
		try
		{
			return getQueryReader().getQuery( getClass(), propertyName );
		}
		catch( QueryReaderException exc )
		{
			log.error( exc );
			throw new SQLException( exc.getMessage() );
		}
	}
	
	protected Connection getConnection() throws SQLException
	{
		// TODO : El initial context y el datasource se pueden cachear
		InitialContext ctx = null;
		try
		{
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup(JNDI_NAME);
			return ds.getConnection();
		}
		catch ( NamingException exc )
		{
			log.error( exc );
			throw new SQLException( exc.getMessage() );
		}
	}

	protected Connection getConnection(String name) throws SQLException
	{
		// TODO : El initial context y el datasource se pueden cachear
		InitialContext ctx = null;
		try
		{
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup(name);
			return ds.getConnection();
		}
		catch ( NamingException exc )
		{
			log.error( exc );
			throw new SQLException( exc.getMessage() );
		}
	}

	protected List queryForBeanList(Connection conn, String sql, Object[] params,
			Class beanClass) throws SQLException 
	{
		String query = this.getQuery( sql );
		log.debug( "Querying [" + sql + "] for class [" + beanClass.getName() + "]. SQL: ["  + query + "]" + " PARAMS " + params );
		return super.queryForBeanList( conn, query, params, beanClass );
	}
	
	protected List queryForBeanList(String sql, Class beanClass)
	throws java.sql.SQLException 
	{
		String query = this.getQuery( sql );
		log.debug( "Querying [" + sql + "] for class [" + beanClass.getName() + "]. SQL: ["  + query + "]" );
		return super.queryForBeanList(  query, beanClass );
	}
	
	protected List queryForMapList(Connection conn, String sql, Object[] params)
	throws java.sql.SQLException 
	{
		String query = this.getQuery( sql );
		log.debug( "Querying [" + sql + "] for Map. SQL: ["  + query + "]" + " PARAMS " + logParams ( params ) );
		return super.queryForMapList( conn, query, params );
	}
	
	protected List queryForMapList(String sql) throws java.sql.SQLException 
	{
		String query = this.getQuery( sql );
		log.debug( "Querying [" + sql + "] for Map. SQL: ["  + query + "]" );
		return super.queryForMapList( query );
	}
	
	/**
	 * Realiza el update sql pero la consulta se le pasa como parametro, no lo coge
	 * del fichero de properties
	 * @param sql
	 * @param params
	 * @return
	 * @throws java.sql.SQLException
	 */

	protected int updateConstructed(Connection conn, String sql, Object[] params) throws java.sql.SQLException 
	{
		log.debug( "updateConstructed: Querying [" + sql + "]" );
		return super.update( conn, sql, params );
	}
	
	
	/**
	 * Realiza la consulta sql pero la consulta se le pasa como parametro, no lo coge
	 * del fichero de properties
	 * @param sql
	 * @param params
	 * @return
	 * @throws java.sql.SQLException
	 */

	protected List queryForMapListConstructed(Connection conn, String sql, Object[] params) throws java.sql.SQLException 
	{
		log.debug( "queryForMapListConstructed: Querying [" + sql + "]" );
		return super.queryForMapList( conn, sql, params );
	}
	
	/**
	 * Realiza la consulta sql pero la consulta se le pasa como parametro, no lo coge
	 * del fichero de properties
	 * @param sql
	 * @param params
	 * @return
	 * @throws java.sql.SQLException
	 */

	protected List queryForMapListConstructed(String sql, Object[] params) throws java.sql.SQLException 
	{
		log.debug( "Querying [" + sql + "]" );
		return super.queryForMapList( sql, params );
	}


	
	protected int update(Connection conn, String sql, Object[] params) 
	throws java.sql.SQLException
	{
		String query = this.getQuery( sql );
		log.debug( "Updating [" + sql + "]. SQL: ["  + query + "]" + " PARAMS " + params );
		return super.update( conn, query, params );
	}
	
	protected int update(String sql, Object[] params) throws SQLException
	{
		String query = this.getQuery( sql );
		log.debug( "Updating [" + sql + "]. SQL: ["  + query + "]" + " PARAMS " + params );
		return super.update( query, params );
	}
	
	protected Long obtenerValorSecuencia( String key ) throws SQLException
	{
		List resultadoConsulta = this.queryForMapList( key );
		if ( resultadoConsulta != null && resultadoConsulta.size() == 1 )
		{
			Map row = ( Map ) resultadoConsulta.get( 0 );
			return new Long( ( ( BigDecimal ) row.get( "NEXTVAL" ) ).longValue() );
		}
		throw new SQLException( "No se puede obtener el valor de la secuencia " + key  );
	}
	
	private String logParams( Object [] params )
	{
		StringBuffer sbReturn = new StringBuffer( "{" );
		for ( int i = 0; i < params.length; i++ )
		{
			sbReturn.append( params[i] );
			if ( i != params.length - 1 )
			{
				sbReturn.append( "," );
			}
		}
		sbReturn.append( "}" );
		return sbReturn.toString();
	}
	
	protected QueryReader getQueryReader()
	{
		return queryReader;
	}
}
