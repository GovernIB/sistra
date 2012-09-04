/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.caib.dbutils;

import java.sql.Connection;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;

/**
 * @author u990250
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface QueryExecutor 
{
	/**
	 * Devuelve una conexi�n jdbc. S�lo es necesario usarlo si se quiere controlar la transaccionalidad
	 * de varias operaciones. En dicho caso queda bajo la responsabilidad del m�dulo llamante cerrar la conexi�n
	 * de forma adecuada
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;
	/**
	 * Ejecuta una consulta y devuelve una lista de objetos Map con el resultado de la consulta
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List queryForMapList( Connection con, String sql, Object[] params ) throws SQLException;
	
	/**
	 * Ejecuta una consulta y devuelve una lista de objetos Map con el resultado de la consulta
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List queryForMapList( String sql, Object[] params ) throws SQLException;
	/**
	 * Ejecuta una consulta y devuelve una lista de objetos Map con el resultado de la consulta
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List queryForMapList( String sql ) throws SQLException;
	/**
	 * Ejecuta una consulta e intenta mapearla a una lista de objetos de la clase indicada por el par�metro
	 * "beanClass"
	 * @param con
	 * @param sql
	 * @param params
	 * @param beanClass
	 * @return
	 * @throws SQLException
	 */
	public List queryForBeanList( Connection con, String sql, Object[] params, Class beanClass ) throws SQLException;
	/**
	 * Ejecuta una consulta e intenta mapearla a una lista de objetos de la clase indicada por el par�metro
	 * "beanClass"
	 * @param sql
	 * @param params
	 * @param beanClass
	 * @return
	 * @throws SQLException
	 */
	public List queryForBeanList( String sql, Object[] params, Class beanClass ) throws SQLException;
	/**
	 * Ejecuta una consulta e intenta mapearla a una lista de objetos de la clase indicada por el par�metro
	 * "beanClass"
	 * @param sql
	 * @param beanClass
	 * @return
	 * @throws SQLException
	 */
	public List queryForBeanList( String sql, Class beanClass ) throws SQLException;
	/**
	 * Ejecuta una consulta e intenta mapearla a una lista de objetos de la clase indicada por el par�metro
	 * "beanClass"
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int update( Connection con, String sql, Object[] params ) throws SQLException;
	/**
	 * Ejecuta una consulta e intenta mapearla a una lista de objetos de la clase indicada por el par�metro
	 * "beanClass"
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int update( String sql, Object[] params ) throws SQLException;
	
	/**
	 * Ejecuta un commit sobre la conexi�n. El metodo llamante debe cerrar la conexi�n.
	 * @param conn
	 * @throws SQLException
	 * 
	 **/
	 
	public void commit( Connection conn ) throws SQLException;
	
	
	/**
	 * Ejecuta un rollback sobre la conexi�n. El metodo llamante debe cerrar la conexi�n.
	 * @param conn
	 * @throws SQLException
	 **/
	
	public void rollback( Connection conn ) throws SQLException;
	 
	
	
	/***
	 * Cierra una conexi�n tan s�lo en el caso de que no sea nula
	 * @param conn
	 * @throws SQLException
	 **/
	public void closeConnection( Connection conn ) throws SQLException;
	
}
