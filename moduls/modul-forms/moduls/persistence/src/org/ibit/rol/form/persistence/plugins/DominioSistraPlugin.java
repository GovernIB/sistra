package org.ibit.rol.form.persistence.plugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.mozilla.javascript.NativeArray;

import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.intf.SistraFacade;
import es.caib.sistra.persistence.intf.SistraFacadeHome;
import es.caib.util.EjbUtil;


public class DominioSistraPlugin extends ValorPosiblePlugin
{
	private static Log log = LogFactory.getLog( DominioSistraPlugin.class );

	private String nombreDominio;
	private NativeArray params;

	/**
	 * MODIFICACION: TODOS LOS ACCESOS SE CACHEARAN DURANTE 30 segs.
	 * 			NO HAY DIFERENCIA ENTRE DOMINIOSISTRAPLUGINCACHE Y DOMINIOSISTRAPLUGIN
	 */
	private boolean caching = true;

	//private static String urlSistra = null;
	private String idKeyColumn,valueKeyColumn,parentKeyColumn,defaultValue;

	private Boolean inicializado = null;
	private String urlSistra = null; // Url sistra en caso de que este en servidor distinto de la plataforma


	/**
	 * Recupera valores de un dominio. El resto de funciones de recuperar valores invocara a esta usando menos parametros.
	 *
	 * @param nombreDominio Identificador del dominio
	 * @param params Parametros del dominio
	 * @param idKeyColumn Columna que se empleara como codigo para montar los ValoresPosibles
	 * @param valueKeyColumn Columna que se empleara como valor para montar los ValoresPosibles
	 * @param parentKeyColumn Permite establecer jerarquía entre los valores indicando que columna se utiliza para establecer el padre (el valor de esta columna será null para roots). Se utiliza para controles de selección en árbol.
	 * @param defaultValue Valor que será marcado como valor por defecto
	 */
	public DominioSistraPlugin( String nombreDominio, NativeArray params,String idKeyColumn,String valueKeyColumn,String parentKeyColumn, String defaultValue)
	{
		this.nombreDominio = nombreDominio;
		this.idKeyColumn=idKeyColumn;
		this.valueKeyColumn=valueKeyColumn;
		this.parentKeyColumn=parentKeyColumn;
		this.defaultValue = defaultValue;
		this.params = params;
		if ( inicializado == null )
		{
			try
			{
				// Obtenemos propiedades configuracion
				Properties config = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();

				// Establecemos tiempo particularizado de tiempo en cache para dominios sistra
				this.tiempoEnCache = Long.parseLong(config.getProperty("tiempoEnCache"));
				// Url modulo sistra para resolucion de dominios (en caso de que este en otro servidor)
				this.urlSistra = config.getProperty("dominio.sistra.plugin.url");

			}
			catch( Exception exc )
			{
				log.error( "Error obteniendo configuracion del plugin", exc );
			}
		}
	}

	public DominioSistraPlugin( String nombreDominio, NativeArray params,String idKeyColumn,String valueKeyColumn,String parentKeyColumn)
	{
		this(nombreDominio,params,idKeyColumn,valueKeyColumn,parentKeyColumn,null);
	}

	public DominioSistraPlugin( String nombreDominio, NativeArray params,String idKeyColumn,String valueKeyColumn)
	{
		this( nombreDominio,params,idKeyColumn,valueKeyColumn,null);
	}

	public DominioSistraPlugin( String nombreDominio,String idKeyColumn,String valueKeyColumn )
	{
		this( nombreDominio,null,idKeyColumn,valueKeyColumn);
	}


	public DominioSistraPlugin( String nombreDominio,NativeArray params)
	{
		this( nombreDominio,params,null,null);
	}

	/**
	 * Recupera lista de valores posibles (todos los del dominio)
	 *
	 * @return
	 * @throws Exception
	 */
	public Object execute() throws Exception
	{
		return execute("es");
	}

	/**
	 * Recupera valor posible (1 valor posible)
	 */
	public Object execute(int fila) throws Exception
	{
		return execute("es",fila);
	}

	/**
	 * Recupera valor para una columna
	 * @param fila
	 * @param columna
	 * @return
	 * @throws Exception
	 */
	public String retrieveColumn(int fila,String columna) throws Exception{
		return retrieveColumn("es",fila,columna);
	}

	/**
	 * Recupera el número de elementos
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public int getNumeroElementos() throws Exception{
			return ((ValorPosible[]) execute()).length;
	}

	// -----------------------------------------------------------------------------
	//	ESTAS FUNCIONES SE MANTIENEN POR COMPATIBILIDAD CON FORMS PERO EL PARAMETRO
	//	LANG NO SE UTILIZA
	// -----------------------------------------------------------------------------

	/**
	 * Recupera lista de valores posibles (todos los del dominio)
	 *
	 * @param lang (No se utiliza)
	 * @return
	 * @throws Exception
	 */
	public Object execute(String lang) throws Exception
	{
		// Obtenemos valores dominio
		ValoresDominio vd = getValoresDominio(lang,this.caching);

		// Convertimos a valores posibles
		ValorPosible[] valorsPosibles = valoresDominioToValoresPosible(vd,idKeyColumn,valueKeyColumn,parentKeyColumn,lang,defaultValue);

		// Devolvemos valores posibles
		return valorsPosibles;
	}

	/**
	 * Recupera valor posible (1 valor posible)
	 *
	 * @param lang
	 * @param fila
	 * @return
	 * @throws Exception
	 */
	public Object execute(String lang, int fila) throws Exception
	{
		// Obtenemos valores dominio
		ValoresDominio vd = getValoresDominio(lang,this.caching);

		// Convertimos a valores posibles
		ValorPosible[] valorsPosibles = valoresDominioToValorPosible(vd,idKeyColumn,valueKeyColumn,fila,lang);

		// Devolvemos valores posibles
		return valorsPosibles;
	}

	/**
	 * Recupera el número de elementos
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public int getNumeroElementos(String lang) throws Exception{
        // Obtenemos valores dominio
		ValoresDominio vd = getValoresDominio(lang,this.caching);
		return vd.getNumeroFilas();
	}

	/**
	 * Recupera valor columna
	 *
	 * @param lang
	 * @param fila
	 * @param columna
	 * @return
	 * @throws Exception
	 */
	public String retrieveColumn(String lang,int fila,String columna) throws Exception{
		// Obtenemos valores dominio
		ValoresDominio vd = getValoresDominio(lang,this.caching);
		if (fila > vd.getNumeroFilas())	return "";

		// Obtenemos valor columna
		String val = vd.getValor(fila,columna);
		if (val == null) return "";
		return val;
	}

	// -------------------------------------------------------------------------------------------
	//	FUNCIONES AUXILIARES
	// -------------------------------------------------------------------------------------------
	/* NOT USED
	private Properties getConfigurationForClass( Class clazz ) throws Exception
	{
		Properties result = new Properties();
		String resourceName = clazz.getName();
		resourceName = resourceName.substring( resourceName.lastIndexOf( '.' ) + 1 );
		resourceName +=  ".properties";
		result.load( clazz.getResourceAsStream( resourceName ) );
		return result;
	}
	*/

	/**
	 *
	 * Convierte ValoresDominio a lista de ValorPosible
	 *
	 * @param vd
	 * @param columKey
	 * @param columValue
	 * @param lang
	 * @return
	 */
	private ValorPosible[] valoresDominioToValoresPosible(ValoresDominio vd,String columKey,String columValue,String parentKeyColumn, String lang, String defaultValue){

		String keyCol = columKey;
		String valCol = columValue;

		// 	Si no se han establecido las vbles keys cogemos dos columnas cualquiera
		if (StringUtils.isEmpty(idKeyColumn) || StringUtils.isEmpty(valueKeyColumn)){
			if (vd != null ){
				if (vd.getNumeroFilas()>0){
					Map columns = (Map) vd.getFilas().get(0);
					Iterator keys = columns.keySet().iterator();
					keyCol = (String) keys.next();
					if (keys.hasNext()) valCol = (String) keys.next();
					else valCol = keyCol;
				}
			}
		}



		ValorPosible[] valorsPosibles = null;
		if ( vd != null )
		{
			int iSize = vd.getNumeroFilas();
			valorsPosibles = new ValorPosible[ iSize ];
			for ( int i = 1; i <= iSize; i++ )
			{
				if (StringUtils.isEmpty(parentKeyColumn)){
					valorsPosibles[i - 1] = this.crearValorPosible( vd.getValor( i, keyCol ), lang, vd.getValor( i, valCol ) );
				}else{
					valorsPosibles[i - 1] = this.crearValorPosible( vd.getValor( i, keyCol ), lang, vd.getValor( i, valCol ), vd.getValor( i, parentKeyColumn ));
				}
				// Comprobamos si es el valor x defecto
				if (defaultValue!= null && defaultValue.equals(valorsPosibles[i - 1].getValor())){
					valorsPosibles[i - 1].setDefecto(true);
				}
			}
		}
		return valorsPosibles;
	}

	/**
	 *
	 * Convierte ValoresDominio a ValorPosible
	 *
	 * @param vd
	 * @param columKey
	 * @param columValue
	 * @param lang
	 * @return
	 */
	private ValorPosible[] valoresDominioToValorPosible(ValoresDominio vd,String columKey,String columValue,int fila,String lang){
		ValorPosible[] valorsPosibles = null;
		if ( vd != null )
		{
			if (vd.getNumeroFilas() < fila) return valorsPosibles;
			valorsPosibles = new ValorPosible[1];
			valorsPosibles[0] = this.crearValorPosible( vd.getValor(fila,columKey), lang, vd.getValor( fila, columValue ) );
		}
		return valorsPosibles;
	}

	/**
	 * Obtiene ValoresDominio bien del EJB o bien de la cache si procede
	 *
	 * @param lang
	 * @param caching
	 * @return
	 * @throws Exception
	 */
	private ValoresDominio getValoresDominio(String lang,boolean caching) throws Exception{
		// Controlamos lista de parametros
		List lstParams = new ArrayList();
		if ( params != null )
		{
			Object [] ids = params.getIds();
			for ( int i = 0; i < ids.length; i++ )
			{
				Object valorParametro = params.get( (( Integer ) ids[i] ).intValue() , this.params );
				lstParams.add( valorParametro.toString() );
			}
		}

		// Comprobamos si esta cacheado
		String cacheKey = null;
		if ( caching )
		{
			cacheKey = nombreDominio + lstParams.hashCode() + lang;
			ValoresDominio cached = (ValoresDominio) getFromCache(cacheKey);
	        if (cached != null) {
	        	log.debug(cacheKey + " - obtenido de cache");
	        	return cached;
	        }
		}

		// Obtenemos valores dominio del EJB
		SistraFacade ejb = null;
		try{
			SistraFacadeHome home = (SistraFacadeHome) EjbUtil.lookupHome(
					SistraFacadeHome.JNDI_NAME,
					(urlSistra == null),
					urlSistra,
					SistraFacadeHome.class);
			ejb = home.create();
		}catch (Exception ex){
			ex.printStackTrace();
			throw new Exception("Error accediendo a Sistema de Tramitación: " + ex.getMessage(),ex);
		}

		// Recuperamos valor dominio
		ValoresDominio dom;
		try{
			dom =  ejb.obtenerDominio( nombreDominio , lstParams, false );
		}catch (Exception ex){
			dom = null;
		}

		// Cacheamos valores dominio
		if ( dom != null && !dom.isError() && caching )
		{
			saveToCache( cacheKey, dom );
		}

		// Si es nulo creamos objeto para evitar excepciones
		if (dom == null){
			log.warn("Dominio " + nombreDominio + " devuelve nulo");
			dom = new ValoresDominio();
		}

		return dom;
	}

	protected boolean isCaching() {
		return caching;
	}

	protected void setCaching(boolean caching) {
		this.caching = caching;
	}


}
