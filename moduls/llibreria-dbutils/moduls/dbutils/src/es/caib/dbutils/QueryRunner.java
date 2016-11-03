/*
 * Created on 05-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.caib.dbutils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author u990250
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryRunner extends org.apache.commons.dbutils.QueryRunner 
{
    /**
     * Fill the <code>PreparedStatement</code> replacement parameters with 
     * the given objects.
     * @param stmt
     * @param params Query replacement parameters; <code>null</code> is a valid
     * value to pass in.
     * @throws SQLException
     */
    protected void fillStatement(PreparedStatement stmt, Object[] params)
        throws SQLException {

        if (params == null) {
            return;
        }

        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                stmt.setObject(i + 1, params[i]);
            } else {
                //stmt.setNull(i + 1, Types.OTHER);
            	if ( params[i] instanceof Integer )
            	{
            		stmt.setNull(i + 1, Types.INTEGER);
            	}
            	else
            	{
            		stmt.setNull(i + 1, Types.NULL);
            	}
            }
        }
    }
}
