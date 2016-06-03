package es.caib.sistra.front.formulario;


import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.LiteralesUtil;
import es.caib.sistra.front.util.Util;
import es.caib.sistra.model.ConfiguracionFormulario;
import es.caib.sistra.model.ConfiguracionGestorFlujoFormulario;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.formsconf.factoria.FactoriaObjetosXMLConfForms;
import es.caib.xml.formsconf.factoria.ServicioConfFormsXML;
import es.caib.xml.formsconf.factoria.impl.ConfiguracionForms;
import es.caib.xml.formsconf.factoria.impl.Datos;
import es.caib.xml.formsconf.factoria.impl.Propiedad;


public class GestorFlujoFormularioFORMS implements GestorFlujoFormulario, Serializable
{

	// Propiedades estáticas del manejador
	private static boolean initialized = false; // Solo las inicializará la primera instancia que se cree
	private static Log log = LogFactory.getLog( GestorFlujoFormularioFORMS.class );
	//  - Urls sistra
	private static String URL_OK = null;
	private static String URL_REDIRECCION_OK = null;
	private static String URL_CANCEL = null;
	private static String URL_REDIRECCION_CANCEL = null;
	private static String URL_SISTRA = null;
	private static String CONTEXTO_RAIZ = null;
	// - Nombre parametros
	private static String RESULT_PARAM 	= "es.caib.sistra.front.formulario.result@";
	private static String CANCEL_PARAM 	= "es.caib.sistra.front.formulario.cancelacio@";
	private static String TOKEN_NAME = Constants.GESTOR_FORM_PARAM_TOKEN_LLAMADA;
	private static String TOKEN_NAME_RETORNO = Constants.GESTOR_FORM_PARAM_TOKEN_RETORNO;
	private static String PARAM_XML_DATOS_FIN_NAME = Constants.GESTOR_FORM_PARAM_XML_DATOS_FIN;
	private static String PARAM_XML_DATOS_INICIO_NAME = Constants.GESTOR_FORM_PARAM_XML_DATOS_INI;
	private static String PARAM_XML_DATOS_SINTERMINAR_NAME = Constants.GESTOR_FORM_PARAM_XML_SIN_TERMINAR;

	// TODO rafa: XA REVISAR !!!
	// Propiedades forms
	private static String DEFAULT_PERFIL = "CAIB_AZUL";
	private static String DEFAULT_LAYOUT = "caib";

	// Propiedades de instancia
	// - Id gestor
	private String id;
	// - Configuracion formulario
	private Map initParams;
	// - Almacenamiento datos del formulario
	private long expirationTime;
	private HashMap storingArea = new HashMap();

	// Url server forms
	//private static String urlForms;

	public void init ( Map initParams )
	{
		// Guardamos las propiedades de inicio para las siguientes llamadas
		this.initParams = initParams;

		// Inicializamos propiedades estaticas de la clase (solo la primera instancia que se crea de la clase)
		if ( !initialized )
		{
			try
			{
				Properties propsConfig = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
				String urlSistra = propsConfig.getProperty("sistra.url");
				String contextoSistra = propsConfig.getProperty("sistra.contextoRaiz.front");

				// Urls sistra
				URL_SISTRA 					= urlSistra;
				CONTEXTO_RAIZ 				= contextoSistra;
				URL_OK  					= urlSistra + CONTEXTO_RAIZ +  getParametroConfiguracion( initParams,  "sistra.urlSisTraOK" );
				URL_REDIRECCION_OK 			= urlSistra + CONTEXTO_RAIZ + getParametroConfiguracion( initParams,  "sistra.urlRedireccionOK" );
				URL_CANCEL  				= urlSistra + CONTEXTO_RAIZ + getParametroConfiguracion( initParams,  "sistra.urlSisTraCancel" );
				URL_REDIRECCION_CANCEL 		= urlSistra + CONTEXTO_RAIZ + getParametroConfiguracion( initParams,  "sistra.urlRedireccionCancel" );

			}
			catch ( Exception exc )
			{
				log.error("Excepcion inicializando gestor", exc );
			}
			initialized = true;
		}
	}



	public boolean continuarCancelacion(String token, boolean debug)
	{
		if (debug) {
			log.debug( "DEBUGFORM: GestorFormularios.continuarCancelacion" );
		}
		Boolean cancelacio = ( Boolean ) storingArea.get( CANCEL_PARAM + token );
		cancelacio = cancelacio == null ? Boolean.FALSE : cancelacio;
		storingArea.remove( CANCEL_PARAM + token );
		storingArea.clear();
		storingArea = null;

		if (debug) {
			log.debug("DEBUGFORM: GestorFormularios.continuarCancelacion [GF:" + this.getId() + " - TF:" + token + "]" );
		}

		return cancelacio.booleanValue();
	}


	public String cancelarFormulario()
	{
		log.debug( "DEBUGFORM: GestorFormularios.cancelarFormulario" );
		String token = Util.generateToken();
		storingArea.remove( RESULT_PARAM );
		storingArea.put( CANCEL_PARAM + token, Boolean.TRUE );

		log.debug("DEBUGFORM: GestorFormularios.cancelarFormulario [GF:" + this.getId() + " - TF:" + token + "]" );

		return token;
	}

	public ResultadoProcesoFormulario continuarTramitacion(String token, boolean debug)
	{
		if (debug) {
			log.debug( "DEBUGFORM: GestorFormularios.continuarTramitacion" );
		}
		ResultadoProcesoFormulario resultado =
			( ResultadoProcesoFormulario ) storingArea.get( RESULT_PARAM  + token );
		storingArea.clear();
		storingArea = null;

		if (debug) {
			log.debug("DEBUGFORM: GestorFormularios.continuarTramitacion [GF:" + this.getId() + " - TF:" + token + "]" );
		}
		return resultado;
	}

	public String guardarDatosFormulario(String xmlInicial, String xmlActual, boolean guardadoSinFinalizar)
	{
		try
		{
			log.debug( "DEBUGFORM: GestorFormularios.guardarDatosFormulario" );

			// Recupera resultado formulario y actualiza datos
	        ResultadoProcesoFormulario resultado = ( ResultadoProcesoFormulario ) storingArea.get( RESULT_PARAM );
	        resultado.setXmlActual( xmlActual );
	        resultado.setXmlInicial( xmlInicial );
	        resultado.setGuardadoSinFinalizar(guardadoSinFinalizar);

	        // Almacena resultado añadiendole un token
	        String token = Util.generateToken();
	        storingArea.remove( RESULT_PARAM );
	        storingArea.put( RESULT_PARAM  + token, resultado );

	        log.debug("DEBUGFORM: GestorFormularios.guardarDatosFormulario [GF:" + this.getId() + " - TF:" + token + "]" );

	        return token;
		}
		catch ( Exception exc )
		{
			log.error("Excepcion guardando datos formulario", exc );
			return null;
		}
	}

	public String irAFormulario( ConfiguracionGestorFlujoFormulario confGestorForm,
								 DocumentoFront formulario,
								 TramiteFront informacionTramite,
								 Map parametrosRetorno  )
	{

		log.debug( "DEBUGFORM: GestorFormularios.irAFormulario" );

		// Inicializamos urls gestor formulario
		String urlGestor = null;
		String URL_TRAMITACION_FORMULARIO = null;
		String URL_REDIRECCION_FORMULARIO = null;

		try{
			urlGestor = confGestorForm.getGestorFormulario().getUrlGestor();
			urlGestor = StringUtil.replace(urlGestor,"@sistra.url@",URL_SISTRA);

			URL_TRAMITACION_FORMULARIO 	= confGestorForm.getGestorFormulario().getUrlTramitacionFormulario();
			URL_REDIRECCION_FORMULARIO 	= confGestorForm.getGestorFormulario().getUrlRedireccionFormulario();

			// Reemplazamos urls que pueden llevar parametrizada la url
			URL_TRAMITACION_FORMULARIO = StringUtil.replace(URL_TRAMITACION_FORMULARIO,"@forms.server@",urlGestor);
			URL_TRAMITACION_FORMULARIO = StringUtil.replace(URL_TRAMITACION_FORMULARIO,"@sistra.contextoRaiz@",CONTEXTO_RAIZ);
			URL_REDIRECCION_FORMULARIO 	= StringUtil.replace(URL_REDIRECCION_FORMULARIO,"@forms.server@",urlGestor);
			URL_REDIRECCION_FORMULARIO 	= StringUtil.replace(URL_REDIRECCION_FORMULARIO,"@sistra.contextoRaiz@",CONTEXTO_RAIZ);

			log.debug( "URL_TRAMITACION_FORMULARIO:" + URL_TRAMITACION_FORMULARIO);
		}catch(Exception ex){
			 log.error("Error obteniendo urls gestor formulario '" + confGestorForm.getGestorFormulario().getIdentificador() + "'");
             return null;
		}

		Locale locale = informacionTramite.getDatosSesion().getLocale();

		HttpClientParams paramsHttp = new HttpClientParams();
		paramsHttp.setConnectionManagerTimeout(30 * 1000); // Esperamos 30 seg a conectar con Forms
		paramsHttp.setSoTimeout(30 * 1000);
        HttpClient client = new HttpClient(paramsHttp);
        PostMethod method = new PostMethod(URL_TRAMITACION_FORMULARIO);
        try
        {
        	// Genera xml de configuracion
        	String xmlConfiguracion = obtenerXMLConfiguracion(confGestorForm.getPropiedad("tituloAplicacion"),
    				formulario,
    				confGestorForm.getPropiedad("modelo"),
    				Integer.parseInt(confGestorForm.getPropiedad("version")),
    				locale,
    				informacionTramite,
    				confGestorForm.getConfiguracionFormulario(),
    				parametrosRetorno );

        	// Vemos si hay que pasar por proxy
    		String proxyHost = System.getProperty("http.proxyHost");
    		if (proxyHost != null && !"".equals(proxyHost)) {
    			if (!validateNonProxyHosts(URL_TRAMITACION_FORMULARIO)) {
    				log.debug("Estableciendo autenticacion para proxy");
    				int port = Integer.parseInt(System
    						.getProperty("http.proxyPort"));
    				client.getHostConfiguration().setProxy(proxyHost, port);
    		        Credentials credentials = new UsernamePasswordCredentials(System.getProperty("http.proxyUser"), System.getProperty("http.proxyPassword"));
    		        AuthScope authScope = new AuthScope(proxyHost, port);
    		        client.getState().setProxyCredentials(authScope, credentials);
    			}
    		}

    		// Realiza peticion y obtiene token de redireccion
	        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        method.getParams().setContentCharset("UTF-8");
	        method.addParameter("xmlData", confGestorForm.getDatosActualesFormulario());
	        method.addParameter("xmlConfig",
	        			xmlConfiguracion );
	        method.addParameter("debugEnabled", Boolean.toString(informacionTramite.isDebugEnabled()));
            int status = client.executeMethod(method);
            if (status != HttpStatus.SC_OK) {
                log.error("Error iniciando tramite: " + status);
                return null;
            }
            String token= method.getResponseBodyAsString();

            // Verificamos que el token sea valido (q no se devuelva pagina error, etc.)
            if (StringUtils.isBlank(token) || !Pattern.matches("[a-zA-Z0-9\\-_]{1,100}", token)) {
            	throw new Exception("Token no valido: " + token);
            }

            // Almacena en el gestor de formularios el formulario inicial
            ResultadoProcesoFormulario resultado = new ResultadoProcesoFormulario();
            resultado.setFormulario( formulario );
            storingArea.put( RESULT_PARAM, resultado );

            // Generamos url de redireccion y la retornamos
            Map params = new HashMap();
            params.put(TOKEN_NAME, token);
            String url = this.appendParametersToURL( URL_REDIRECCION_FORMULARIO, params);

            log.debug("DEBUGFORM: GestorFormularios.irAFormulario [GF:" + this.getId() + "]" );

            return url;

        } catch (Exception e) {
        	log.error( "Error conectando con gestor formularios: " + e.getMessage(),e );
            return null;
        } finally {
            method.releaseConnection();
        }
	}

	private String obtenerXMLConfiguracion( String tituloAplicacion,DocumentoFront formulario,String modelo, int version, Locale locale,
											TramiteFront informacionTramite, ConfiguracionFormulario configuracionFormulario,
											Map parametrosRetorno ) throws Exception
	{
		FactoriaObjetosXMLConfForms factory = ServicioConfFormsXML.crearFactoriaObjetosXML();
		factory.setEncoding( ConstantesXML.ENCODING);

		ConfiguracionForms objXmlConfiguracion = factory.crearConfiguracionForms();

		// Datos
		Datos datos = factory.crearDatos();
		datos.setIdioma( locale.getLanguage() );
		datos.setModelo( modelo );
		datos.setVersion( new Integer(version) );
		datos.setCodigoPerfil( DEFAULT_PERFIL );
		datos.setLayout( DEFAULT_LAYOUT );
		datos.setGuardarSinTerminar(formulario.isFormularioGuardarSinTerminar());
		datos.setUrlSisTraOK( this.appendParametersToURL( URL_OK, parametrosRetorno )  );
		datos.setUrlRedireccionOK( this.appendParametersToURL( URL_REDIRECCION_OK, parametrosRetorno )  );
		datos.setUrlSisTraCancel( this.appendParametersToURL( URL_CANCEL, parametrosRetorno )  );
		datos.setUrlRedireccionCancel( this.appendParametersToURL( URL_REDIRECCION_CANCEL, parametrosRetorno )  );
		datos.setUrlSisTraMantenimientoSesion( Util.generaUrlMantenimientoSesion((String) parametrosRetorno.get(TOKEN_NAME)) );

		datos.setNomParamTokenRetorno( TOKEN_NAME_RETORNO );
		datos.setNomParamXMLDatosFin( PARAM_XML_DATOS_FIN_NAME );
		datos.setNomParamXMLDatosIni( PARAM_XML_DATOS_INICIO_NAME );
		datos.setNomParamXMLSinTerminar(PARAM_XML_DATOS_SINTERMINAR_NAME);
		objXmlConfiguracion.setDatos( datos );


		// Propiedades: Pasos tramite, Título Aplicación, Nombre usuario, Nombre Formulario y Nombre Trámite

		// - Circuito reducido
		Propiedad propiedadReducido = factory.crearPropiedad();
		propiedadReducido.setNombre( "circuitoReducido" );
		propiedadReducido.setValor( Boolean.toString(informacionTramite.isCircuitoReducido()));
		objXmlConfiguracion.getPropiedades().put(propiedadReducido.getNombre(),propiedadReducido);


		// - Pasos tramite (no pasamos paso Pasos)
		List pasosStr = new ArrayList();
		List lstPasos = informacionTramite.getPasos();
		for ( int i = 0; i < lstPasos.size(); i++ )
		{
			PasoTramitacion paso = ( PasoTramitacion ) lstPasos.get( i );
			if (paso.getTipoPaso() != PasoTramitacion.PASO_PASOS) {
				Util.establecerTituloYCuerpo( informacionTramite, paso );
				pasosStr.add(LiteralesUtil.getLiteral(locale.getLanguage(), paso.getClaveTab()));
			}
		}
		Propiedad propiedadPasosTramite = factory.crearPropiedad();
		propiedadPasosTramite.setNombre( "pasosTramite" );
		propiedadPasosTramite.setValor(StringUtil.serializarList(pasosStr));
		objXmlConfiguracion.getPropiedades().put(propiedadPasosTramite.getNombre(),propiedadPasosTramite);

		// - Paso actual (descontamos paso Pasos)
		Propiedad propiedadPasoActual = factory.crearPropiedad();
		propiedadPasoActual.setNombre( "pasoActual" );
		propiedadPasoActual.setValor( (informacionTramite.getPasoActual() - 1)+ "");
		objXmlConfiguracion.getPropiedades().put(propiedadPasoActual.getNombre(),propiedadPasoActual);

		Propiedad propiedadTituloAplicacion = factory.crearPropiedad();
		propiedadTituloAplicacion.setNombre( "aplicacion" );
		propiedadTituloAplicacion.setValor(tituloAplicacion);
		objXmlConfiguracion.getPropiedades().put(propiedadTituloAplicacion.getNombre(),propiedadTituloAplicacion);

		if (informacionTramite.getDatosSesion().getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO) {
			Propiedad propiedadNombreUsuario = factory.crearPropiedad();
			propiedadNombreUsuario.setNombre( "usuario" );
			propiedadNombreUsuario.setValor( LiteralesUtil.getLiteral(locale.getLanguage(), "datosUsuario.anonimo") );
			objXmlConfiguracion.getPropiedades().put(propiedadNombreUsuario.getNombre(),propiedadNombreUsuario);

			Propiedad propiedadClaveTramitacion = factory.crearPropiedad();
			propiedadClaveTramitacion.setNombre( "claveTramitacion" );
			propiedadClaveTramitacion.setValor( informacionTramite.getIdPersistencia() );
			objXmlConfiguracion.getPropiedades().put(propiedadClaveTramitacion.getNombre(),propiedadClaveTramitacion);
		} else {
			Propiedad propiedadNombreUsuario = factory.crearPropiedad();
			propiedadNombreUsuario.setNombre( "usuario" );
			propiedadNombreUsuario.setValor( informacionTramite.getDatosSesion().getNombreCompletoUsuario() );
			objXmlConfiguracion.getPropiedades().put(propiedadNombreUsuario.getNombre(),propiedadNombreUsuario);
		}

		Propiedad propiedadNombreTramite = factory.crearPropiedad();
		propiedadNombreTramite.setNombre( "tramite" );
		propiedadNombreTramite.setValor( informacionTramite.getDescripcion() );
		objXmlConfiguracion.getPropiedades().put(propiedadNombreTramite.getNombre(),propiedadNombreTramite);

		Propiedad propiedadNombreFormulario = factory.crearPropiedad();
		propiedadNombreFormulario.setNombre( "formulario" );
		propiedadNombreFormulario.setValor( formulario.getDescripcion() );
		objXmlConfiguracion.getPropiedades().put(propiedadNombreFormulario.getNombre(),propiedadNombreFormulario);


		// Propiedades específicas establecidas por script
		for (Iterator it=configuracionFormulario.getPropiedades().keySet().iterator();it.hasNext();){
			String nombrePropiedad = (String) it.next();
			String valorPropiedad = (String)configuracionFormulario.getPropiedades().get(nombrePropiedad);

			Propiedad propiedadEspecifica = factory.crearPropiedad();
			propiedadEspecifica.setNombre( nombrePropiedad );
			propiedadEspecifica.setValor( valorPropiedad );
			objXmlConfiguracion.getPropiedades().put(propiedadEspecifica.getNombre(),propiedadEspecifica);
		}


		// Bloqueo de campos
		configuracionFormulario.getCamposReadOnly();
		ArrayList arlCamposReadOnly = configuracionFormulario.getCamposReadOnly();
		for ( int i = 0; i < arlCamposReadOnly.size(); i++ )
		{
			String xpathBloqueo = ( String ) arlCamposReadOnly.get( i );
			objXmlConfiguracion.getBloqueo().add(xpathBloqueo);
		}

		// Generamos XML
		return factory.guardarConfiguracionForms( objXmlConfiguracion );

	}

	private String appendParametersToURL( String URL, Map params )
	{

		String strReturn = URL;
		for ( Iterator it = params.keySet().iterator(); it.hasNext(); )
		{
			String paramName = ( String ) it.next();
			String paramValue =( String ) params.get( paramName );
			strReturn = appendParameterToURL( strReturn, paramName, paramValue );
		}
		return strReturn;
		//return URL;
	}


	private String appendParameterToURL( String URL, String paramName, String paramValue )
	{
		StringBuffer url = new StringBuffer(URL);
	    url.append( URL.indexOf('?') == -1 ? '?' : '&');
	    url.append(paramName);
	    url.append('=');
	    url.append(paramValue);
	    return url.toString();
	}

	public long getExpirationTime()
	{
		return this.expirationTime;
	}

	public void setExpirationTime(long expirationTime)
	{
		this.expirationTime = expirationTime;
	}

	private String getParametroConfiguracion( Map initParams, String key ) throws Exception
	{
		String value = ( String ) initParams.get( key );
		if ( value == null )
		{
			throw new Exception( "La propiedad <" + key + "> no está definida en el fichero de propiedades GestorFlujoFormularioFORMS.properties" );
		}
		return value;
	}

	/**
	 * Busca els host de la url indicada dentro de la propiedad http.nonProxyHosts de la JVM
	 * @param url Endpoint del ws
	 * @return true si el host esta dentro de la propiedad, fals en caso contrario
	 */
	private boolean validateNonProxyHosts(String url) throws Exception{
		String nonProxyHosts = System.getProperty("http.nonProxyHosts");
	    boolean existe = false;
	    URL urlURL;
		try {
		    if(nonProxyHosts != null && !"".equals(nonProxyHosts)){
    			urlURL = new URL(url);
    			String[] nonProxyHostsArray = nonProxyHosts.split("\\|");
    			for (int i = 0; i < nonProxyHostsArray.length; i++) {
    				String a = nonProxyHostsArray[i].replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");;
    				if (urlURL.getHost().matches(a)) {
    					existe = true;
    					break;
    				}
    			}
		    }
		} catch (MalformedURLException e) {
			log.error("Error al validar los nonProxyHost "+e.getCause(), e);
			throw e;
		}
		return existe;
	}



	public String getId() {
		return id;
	}



	public void setId(String idGestor) {
		this.id = idGestor;
	}

}
