package es.caib.sistra.persistence.ejb;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.Dominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;

/**
 * SessionBean que sirve para generar las interfaces que deberan cumplir los ejbs de dominios remotos
 *
 * @ejb.bean
 *  name="sistra/persistence/DominioSqlFacade"
 *  jndi-name="es.caib.sistra.persistence.DominioSqlFacade"
 *  type="Stateless"
 *  view-type="remote"
 * @ejb.transaction
 *   type="RequiresNew" 
 */
public abstract class DominioSqlFacadeEJB implements SessionBean {
	
	protected static Log log = LogFactory.getLog(DominioSqlFacadeEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		log.info("ejbCreate: " + this.getClass());
	}
	
	
	/**
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
	public ValoresDominio resuelveDominioSQL(Dominio dominio,List parametros, String url, boolean pDebugEnabled) {
		debug("Dominio SQL", pDebugEnabled);
		// Obtenemos datasource
		DataSource datasource;
		Connection con = null;
		try {
			InitialContext ctx = new InitialContext();
			datasource  = ( javax.sql.DataSource ) ctx.lookup("java:/" + url);
	      } catch( Exception e ) {
	              throw new EJBException( "Error consiguiendo conexion : "+e.toString() );
	      }


	    try{
	    	// Obtenemos conexion
	    	con = datasource.getConnection();
			// Creamos sentencia
		    PreparedStatement stmt = con.prepareStatement(dominio.getSql());

		    // TODO: Incompatible amb drivers Postgresql. Detectar del tipu de base de dades i emprar-ho o no condicionalment.
		    // I documentar com es pot aconseguir el mateix efecte a la configuració per exemple del datasource per evitar
		    // "long running queries" que puguin tombar el servidor de base de dades.
		    //stmt.setQueryTimeout(60);

			// Establecemos parametros
		    ParameterMetaData paramMetaData = stmt.getParameterMetaData();
		    for (int i=0;i<parametros.size();i++){
		    	int type;
		    	try { // Intentar obtenir tipus específic del paràmetre.
		    		type = paramMetaData.getParameterType(i+1);
	    		} catch (SQLException sqle) { // Si no està soportat emprarem VARCHAR ja que és el més compatible
	    			type = Types.VARCHAR;
    			}
	    		stmt.setObject(i+1,(String) parametros.get(i), type);
		    }

		    // Ejecutamos sentencia
		    stmt.execute();
		    ResultSet rs = stmt.getResultSet();

			// Creamos ValoresDominio
			ValoresDominio val = new ValoresDominio();
			int numCols = rs.getMetaData().getColumnCount();
			String ls_valor;
			String ls_columnas[] = new String[numCols];
			for (int i=0;i<numCols;i++){
				ls_columnas[i] = rs.getMetaData().getColumnName(i+1);
			}
			Object l_valor;
			int fila=0;
			while (rs.next()){
				fila = val.addFila();
				for (int i=0;i<numCols;i++){
					l_valor = rs.getObject(i + 1);
					if (!rs.wasNull()){
						ls_valor = l_valor.toString();
					}else{
						ls_valor = null;
					}
					val.setValor(fila,ls_columnas[i],ls_valor);
				}
			}
			debug("Dominio resuelto", pDebugEnabled);
			return val;
	    } catch( Exception e ) {
            throw new EJBException( "Error ejecutando SQL dominio : "+e.toString() );    
	    }finally{
	    	if (con != null){
	    		try{con.close();}catch(Exception e){}
	    	}
	    }
	}
	
	 private void debug(String mensaje, boolean pDebugEnabled) {
	    	if (pDebugEnabled) {
	    		log.debug(mensaje);
	    	}
	    }

}
