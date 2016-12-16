package es.caib.sistra.front.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.InitForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.DatosSesion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
/**
 * @struts.action
 *  name="initForm"
 *  path="/protected/init"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="inicioAnonimo" path=".inicioAnonimo"
 *  
 * @struts.action-forward 
 *  name="nuevoTramite" path="/protected/nuevoTramite.do" 
 *  
 * @struts.action-forward
 *  name="cargarTramite" path="/protected/cargarTramite.do"
 *  
 * @struts.action-forward
 *  name="tramitesAlmacenados" path=".tramitesAlmacenados"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 *  
 *  
 */

public class InitAction extends BaseAction
{
	
	/**
	 * Interfaz de entrada al sistema de tramitacion.
	 * 
	 * Como primer paso comprobará que el método de autenticación del usuario existe para el trámite.
	 * 
	 * Si trata de un trámite reducido se saltará siempre la página inicial que permite crear uno nuevo o retomar
	 * uno existente: siempre trámite nuevo
	 * 
	 * Después, redirigirá la petición según el esquema de autenticacion
	 * del usuario de la aplicación, de la siguiente forma:
	 * 
	 *  1º Usuarios autenticados
	 *  	a. Se recibe identificador de persistencia del trámite.
	 *  	   En este caso se carga el trámite en el estado en el que se encuentre.  	
	 *  	b. No se recibe identificador de persistencia.
	 *  	   En este caso, se realizará una consulta sobre la zona personal del usuario para consultar si existen
	 *  	   instancias no finalizadas del mismo trámite. 
	 *  	   Tras realizar esta consulta se presentará una pantalla que contendrá
	 *  	   la instancia o instancias del trámite y la opción de crear uno nuevo, en caso de que el
	 *  	   resultado de la consulta sea positivo, o en caso contrario, la pantalla inicial de un nuevo trámite.	
	 *  
	 *  2º Usuarios  o anónimos (autenticación usuario anónimo ) -> typeAuth = "A"
	 *  	- Si llega un identificador de persistencia, se cargará el trámite correspondiente 
	 *  	- En caso contrario se presentará una pantalla  con un formulario para dar al usuario de la aplicación 
	 *  	  las siguientes opciones:
	 *  		* Cargar un tramite persistente mediante el identificador de trámite persistente
	 *  		* Comenzar un nuevo trámite.
	 *  
	 *  
	 */
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		// Comprobamos si se quiere autenticar el enlace del portal
		if (request.getParameter("autenticaPortal")!=null){
			String url = request.getParameter("url");
			ActionForward redirectForward = new ActionForward();
			redirectForward.setRedirect(true);
			redirectForward.setPath(url);
			return redirectForward;
		}
		
		// Se inicializa el lenguaje.
 		InitForm initForm = ( InitForm ) form;
 		Locale localeLogin = (Locale) request.getSession().getAttribute(Constants.KEY_LANGUAGE_LOGIN);
 		if (localeLogin != null){
 			if (!"es".equals(localeLogin.getLanguage()) && !"ca".equals(localeLogin.getLanguage()) && !"en".equals(localeLogin.getLanguage())  ) {
				setLocale(request, new Locale( "es" ) );
			} else {
				setLocale(request, localeLogin );
			} 			
 		}
 		if ( localeLogin == null && initForm != null && initForm.getLanguage() != null )
 		{
 			if (!"es".equals(initForm.getLanguage()) && !"ca".equals(initForm.getLanguage()) && !"en".equals(initForm.getLanguage())  ) {
 				setLocale(request, new Locale( "es" ) );
 			} else {
 				setLocale(request, new Locale( initForm.getLanguage() ) );
 			}
 		}
 		
 		if ( request.getAttribute( Constants.NO_REINIT ) != null && ( ( Boolean )request.getAttribute( Constants.NO_REINIT ) ).booleanValue() )
 		{
 			initForm.setIdPersistencia( null );
 		}
 		
		//Obtenemos la identidad del usuario.
		char modoAutenticacionUsuario = this.getMetodoAutenticacion( request );
 		
		// Se instancia el delegado para realizar interacciones con el trámite ( sólo en caso necesario )
		InstanciaDelegate delegate = null;
		if ( initForm.getID_INSTANCIA() == null )
		{
	 		
			// Comprobamos si funcionamos en modo delegado
			String perfilAcceso = (String) request.getSession().getAttribute(ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY);
			String nifEntidad = null;
			if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(perfilAcceso) ){
				nifEntidad = (String) request.getSession().getAttribute(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO_ENTIDAD_KEY);
			}	
			
			delegate = DelegateUtil.getInstanciaDelegate( true );
			delegate.create( initForm.getModelo(), initForm.getVersion(),modoAutenticacionUsuario,
						getLocale( request ),obtenerParametrosInicio(request),
						perfilAcceso, nifEntidad);
			
			// Aun no existiria ninguna informacion referente al tramite, simplemente se ha creado el delegado.
			// Registramos la instancia asociada al procesador del trámite
	        InstanciaManager.registrarInstancia( request, delegate );
	        // almacenamos el ID_INSTANCIA de la instancia de proceso de tramitacion en el formulario para su posterior uso
	        initForm.setID_INSTANCIA( InstanciaManager.getIdInstancia( request ) );
		}
		else
		{
			delegate = InstanciaManager.recuperarInstancia( request );
		}

        // Dispondremos en los parámetros de vuelta información
		// acerca de los tipos de autenticacion disponibles para el tramite, asi como la descripción del trámite
        RespuestaFront respuestaFront = null;
        respuestaFront = delegate.informacionInicial();
        
        // Recuperamos parametros respuesta
        HashMap hsmParametros = respuestaFront.getParametros();
        
        
        // Obtenemos de los parametros de vuelta de la llamada a infoLogin
        // la descripcion y los dias de persistencia del tramite, para mostrarlos en la cabecera.
        String descripcion 		= ( String ) hsmParametros.get( Constants.DESCRIPCION_TRAMITE_PARAMS_KEY );
        String diaspersistencia = ( String ) hsmParametros.get( Constants.DIAS_PERSISTENCIA_PARAMS_KEY );
        String identificador = ( String ) hsmParametros.get( "identificador" );
        // Idem para los datos de sesion
        DatosSesion datosSesion = ( DatosSesion ) hsmParametros.get( Constants.DATOS_SESION_PARAMS_KEY ); 
        
        request.setAttribute( Constants.DESCRIPCION_TRAMITE_PARAMS_KEY, descripcion + " (" + identificador + ")");
        request.setAttribute( Constants.DIAS_PERSISTENCIA_PARAMS_KEY, diaspersistencia );
        request.setAttribute( Constants.DATOS_SESION_PARAMS_KEY, datosSesion );
        
        // Validamos que el método de autenticación del usuario está configurado para el trámite
        String cadenaNivelesAutenticacion = ( String ) hsmParametros.get( Constants.NIVELES_AUTENTICACION_PARAMS_KEY );
        if ( cadenaNivelesAutenticacion.indexOf( String.valueOf( modoAutenticacionUsuario ) ) < 0 ) 
        {
        	this.setErrorMessage( request, "errors.tramite.modoAuthNoValido" );
        	return mapping.findForward("error");
        }
        
        
        // Si se trata de un trámite reducido, siempre a la pantalla de trámite nuevo
        Boolean isCircuitoReducido = ( Boolean ) hsmParametros.get( Constants.TRAMITE_REDUCIDO_KEY );
        if ( isCircuitoReducido.booleanValue() )
        {
        	return mapping.findForward( "nuevoTramite" );
        }
        
        /*
         * 	MODIFICACION: PARA ANONIMOS YA NO SE MUESTRA PAGINA QUE PERMITE INICIAR O CARGAR. SIEMPRE A NUEVO TRAMITE.
         * 
	        // Si el modo de autenticacion corresponde a un usuario anonimo y no se pasa un id persistencia, 
	        // se le presenta la correspondiente pantalla de inicio anonimo        
	        if ( modoAutenticacionUsuario == Constants.MODO_AUTENTICACION_ANONIMO && StringUtils.isEmpty( initForm.getIdPersistencia() ) )
	        {
	        	return mapping.findForward("inicioAnonimo");
	        }
	    */
        
        // si se ha proporcionado un identificador de persistencia
        // redirigir a la acción que carga un trámite a partir de dicho identificador
        if ( !StringUtils.isEmpty( initForm.getIdPersistencia() ) )
        {
        	return mapping.findForward( "cargarTramite" );
        }
        
        /*
         * MODIFICACION: PARA AUTENTICADOS YA NO SE MUESTRA PAGINA QUE PERMITE INICIAR O CARGAR UN TRAMITE PERSISTENTE.
         * 				SIEMPRE A NUEVO TRAMITE.
         * 
         *
        
	        // Si hemos llegado a este punto del tramite, quiere decir que es un usuario autenticado y que no se ha
	        // especificado un identificador de persistencia. 
	        
	        // para un modelo y una version, obtener los tramites almacenados en el area de persistencia del usuario.
	         respuestaFront = delegate.obtenerTramitesPersistencia( initForm.getModelo(), initForm.getVersion() );
	        // Es una lista de TramitePersistentePad.
	         List lstTramites = ( List ) respuestaFront.getParametros().get( Constants.TRAMITES_PARAMS_KEY );         
	         
	         // Si tiene tramites almacenados, se le redirige a la pantalla donde puede escoger con qué
	         // trámite operar, o por el contrario operar con uno nuevo.
	         if ( lstTramites != null && lstTramites.size() > 0 )
	         {
	        	 this.setRespuestaFront( request, respuestaFront );        	 
	        	 return mapping.findForward( "tramitesAlmacenados" );
	         }
         */
        
        
        if ( this.isSetError( request ) )
 		{
 			return mapping.findForward("error");
 		}
        
         // De lo contrario, se redirige directamente a la acción de iniciar un nuevo trámite
        return mapping.findForward( "nuevoTramite" );
    }
	
	
	private Map obtenerParametrosInicio(HttpServletRequest request) throws Exception{
		Enumeration params = request.getParameterNames();
		String name,value;
		HashMap paramInicio = new HashMap();
		while (params.hasMoreElements()){
			name = (String) params.nextElement();			
			
			// Parametros que no se trasladaran
			if (name.equals("language") || name.equals("modelo") || name.equals("version") ||
				name.equals("autenticacion") || name.equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY) || 
				name.equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO_ENTIDAD_KEY) ) continue;
			
			// Parametros prohibidos
			if (name.equals(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_ID) || name.equals(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_UA)){
				throw new Exception("Parametro " + name + " no permitido");
			}
						
			// Alimentamos parametro
			value = (String) request.getParameter(name);			
			paramInicio.put(name,value);
		}
		return paramInicio;
	}
}
