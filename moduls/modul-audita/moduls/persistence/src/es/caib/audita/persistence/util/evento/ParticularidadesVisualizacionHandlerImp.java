package es.caib.audita.persistence.util.evento;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.model.CuadroMandoDetalle;
import es.caib.audita.model.CuadroMandoTablaCruzada;
import es.caib.dbutils.DbUtilQueryExecutor;
import es.caib.dbutils.QueryReader;
import es.caib.dbutils.QueryReaderException;
import es.caib.dbutils.QueryReaderFactory;
import es.indra.util.graficos.generadorGraficos.DatosGrafico;

public abstract class ParticularidadesVisualizacionHandlerImp  extends DbUtilQueryExecutor implements
		ParticularidadesVisualizacionHandler
{
	private Connection conn;
	private String modo;
	private String idioma;
	private String tipoEvento;
	private Date dateInicial;
	private Date dateFinal;

	protected static Log log = LogFactory.getLog( ParticularidadesVisualizacionHandlerImp.class );

	public void init(Connection conn, String modo, String tipoEvento, Date dateInicial,
			Date dateFinal, String idioma)
	{
		this.conn = conn;
		this.modo = modo;
		this.idioma = idioma;
		this.dateInicial = dateInicial;
		this.dateFinal = dateFinal;
		this.tipoEvento = tipoEvento;
	}

	protected Connection getConnection() throws SQLException
	{
		return conn;
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


	protected List queryForBeanList(Connection conn, Class propertiesClass, String sql, Object[] params,
			Class beanClass) throws SQLException
	{
		String query = this.getQuery( sql, propertiesClass );
		return super.queryForBeanList( conn, query, params, beanClass );
	}

	protected List queryForBeanList(String sql, Class propertiesClass, Class beanClass)
	throws java.sql.SQLException
	{
		String query = this.getQuery( sql, propertiesClass );
		return super.queryForBeanList(  query, beanClass );
	}

	protected List queryForMapList(Connection conn, Class propertiesClass, String sql, Object[] params)
	throws java.sql.SQLException
	{
		String query = this.getQuery( sql , propertiesClass);
		return super.queryForMapList( conn, query, params );
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
		return super.queryForMapList( sql, params );
	}


	protected List queryForMapList(String sql , Class propertiesClass) throws java.sql.SQLException
	{
		String query = this.getQuery( sql , propertiesClass);
		return super.queryForMapList( query );
	}

	protected int update(Connection conn, Class propertiesClass , String sql, Object[] params)
	throws java.sql.SQLException
	{
		String query = this.getQuery( sql , propertiesClass);
		return super.update( conn, query, params );
	}

	protected int update(String sql, Class propertiesClass, Object[] params) throws SQLException
	{
		String query = this.getQuery( sql , propertiesClass);
		return super.update( query, params );
	}

	protected QueryReader getQueryReader() throws QueryReaderException
	{
		return QueryReaderFactory.getInstance().getQueryReader();
	}

	protected String getQuery( String propertyName, Class propertiesClass ) throws SQLException
	{
		try
		{
			return getQueryReader().getQuery( propertiesClass, propertyName );
		}
		catch( QueryReaderException exc )
		{
			log.error( exc );
			throw new SQLException( exc.getMessage() );
		}
	}

	protected Date getDateFinal()
	{
		return dateFinal;
	}

	protected void setDateFinal(Date dateFinal)
	{
		this.dateFinal = dateFinal;
	}

	protected Date getDateInicial()
	{
		return dateInicial;
	}

	protected void setDateInicial(Date dateInicial)
	{
		this.dateInicial = dateInicial;
	}

	protected String getModo()
	{
		return modo;
	}

	protected void setModo(String modo)
	{
		this.modo = modo;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}




	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	// -----------------------------------------------------------
	// --	FUNCIONES A SOBREESCRIBIR EN LAS IMPLEMENTACIONES ----
	// -----------------------------------------------------------
	public CuadroMandoDetalle obtenerCuadroMandoDetalle(){
		return null;
	}

	public String obtenerCodigoVisualizacion(){
		return null;
	}

	public DatosGrafico obtenerDatosGrafico()
	{
		return null;
	}

	public CuadroMandoTablaCruzada obtenerCuadroMandoTablaCruzada(String tipoEvento,String modo,Date fecha, String idioma){
		return null;
	}

}
