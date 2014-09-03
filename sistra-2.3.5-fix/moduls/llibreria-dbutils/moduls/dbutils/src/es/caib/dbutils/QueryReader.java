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
public interface QueryReader 
{
	
	/**
	 * Inicializa las propiedades necesarias para obtener las queries
	 * @param initProperties
	 */
	public void init( Map initProperties ) throws QueryReaderException;
	
	/**
	 * Obtiene el SQL correspondiente a una key para un determinado recurso ( el recurso puede
	 * corresponder a un fichero: una distribución lógica puede ser un fichero por DAO.
	 * @param strDaoResourceName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	
	public String getQuery( String strDaoResourceName, String key ) throws QueryReaderException;
	
	/**
	 * Obtiene el SQL correspondiente a una key para un determinado DAO Class
	 * @param daoClass
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getQuery( Class daoClass, String key ) throws QueryReaderException;
}
