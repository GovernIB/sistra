package es.caib.dbutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import es.caib.dbutils.QueryRunner;

/**
 * @author Cecilio López Mora
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class DbUtilQueryExecutor 
{
	
	protected abstract Connection getConnection() throws SQLException; 
	
	
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#queryForBeanList(java.sql.Connection, java.lang.String, java.lang.Object[], java.lang.Class)
	 */
	protected List queryForBeanList(Connection conn, String sql, Object[] params,
			Class beanClass) throws SQLException 
	{
		BeanListHandler handler = new BeanListHandler( beanClass, DbUtilRowProcessor.instance() );
		QueryRunner run = new QueryRunner();
		return ( List ) run.query( conn, sql, params, handler );
	}
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#queryForBeanList(java.lang.String, java.lang.Class)
	 */
	protected List queryForBeanList(String sql, Class beanClass)
			throws java.sql.SQLException 
	{
		Connection conn = null;
		try
		{
			conn = this.getConnection();
			BeanListHandler handler = new BeanListHandler( beanClass, DbUtilRowProcessor.instance() );
			QueryRunner run = new QueryRunner();
			return ( List ) run.query( conn, sql, handler );
		}
		finally
		{
			//DbUtils.closeQuietly( conn );
			closeConnection( conn );
		}
	}
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#queryForBeanList(java.lang.String, java.lang.Object[], java.lang.Class)
	 */
	protected List queryForBeanList(String sql, Object[] params, Class beanClass)
			throws java.sql.SQLException {
		Connection conn = null;
		try
		{
			conn = this.getConnection();
			return this.queryForBeanList( conn, sql, params, beanClass );
		}
		finally
		{
			//DbUtils.closeQuietly( conn );
			closeConnection( conn );
		}
	}
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#queryForMapList(java.sql.Connection, java.lang.String, java.lang.Object[])
	 */
	protected List queryForMapList(Connection conn, String sql, Object[] params)
			throws java.sql.SQLException 
	{
		MapListHandler handler = new MapListHandler();
		QueryRunner run = new QueryRunner();
		return ( List ) run.query( conn, sql, params, handler );
	}
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#queryForMapList(java.lang.String, java.lang.Object[])
	 */
	protected List queryForMapList(String sql, Object[] params)
			throws java.sql.SQLException {
		Connection conn = null;
		try
		{
			conn = this.getConnection();
			return this.queryForMapList( conn, sql, params );
		}
		finally
		{
			//DbUtils.closeQuietly( conn );
			closeConnection( conn );
		}
	}
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#queryForMapList(java.lang.String)
	 */
	protected List queryForMapList(String sql) throws java.sql.SQLException {
		Connection conn = null;
		try
		{
			conn = this.getConnection();
			MapListHandler handler = new MapListHandler();
			QueryRunner run = new QueryRunner();
			return ( List ) run.query( conn, sql, handler );
		}
		finally
		{
			//DbUtils.closeQuietly( conn );
			closeConnection( conn );
		}
	}
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#update(java.sql.Connection, java.lang.String, java.lang.Object[])
	 */
	protected int update(Connection conn, String sql, Object[] params) 
		throws java.sql.SQLException
	{
			QueryRunner run = new QueryRunner();
			return run.update( conn, sql, params );
	}
	
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#update(java.lang.String, java.lang.Object[])
	 */
	protected int update(String sql, Object[] params) throws SQLException
	{
		Connection conn = null;
		try
		{
			conn = this.getConnection();
			QueryRunner run = new QueryRunner();
			return run.update( conn, sql, params );
		}
		finally
		{
			//DbUtils.closeQuietly( conn );
			closeConnection( conn );
		}
	}
	
	
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#closeConnection(java.sql.Connection)
	 */
	protected void closeConnection(Connection conn) throws java.sql.SQLException 
	{
		/*
		if ( conn!= null && !conn.getAutoCommit() )
		{
			conn.setAutoCommit( true );
		}
		*/
		DbUtils.closeQuietly( conn );
	}
	
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#commit(java.sql.Connection)
	 */
	protected void commit(Connection conn) throws java.sql.SQLException 
	{
		if ( conn == null )
		{
			throw new java.sql.SQLException ( "The database connection to perform commit is null" );
		}
		conn.commit();
	}
	/* (non-Javadoc)
	 * @see es.caib.dbutils.QueryExecutor#rollback(java.sql.Connection)
	 */
	protected void rollback(Connection conn) throws java.sql.SQLException {
		
		if ( conn == null )
		{
			throw new java.sql.SQLException ( "The database connection to perform rollback is null" );
		}
		conn.rollback();
	}
}
