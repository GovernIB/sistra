package es.caib.sistra.persistence.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.CheckBox;
import org.ibit.rol.form.model.ComboBox;
import org.ibit.rol.form.model.FileBox;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Patron;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.model.RadioButton;
import org.ibit.rol.form.model.Salida;
import org.ibit.rol.form.model.TextBox;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;

import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.model.admin.CuadernoCarga;
import es.caib.sistra.model.admin.DatosAuditoriaCuaderno;
import es.caib.sistra.model.admin.ElementoAuditoriaDominio;
import es.caib.sistra.model.admin.ElementoAuditoriaScript;
import es.caib.sistra.model.admin.FicheroCuaderno;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.util.AuditoriaUtil;
import es.caib.sistra.persistence.util.ScriptUtil;

/**
 * SessionBean para operaciones de otros módulos con Sistra
 *
 * @ejb.bean
 *  name="sistra/persistence/AuditoriaCuadernoFacade"
 *  jndi-name="es.caib.sistra.persistence.AuditoriaCuadernoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public class AuditoriaCuadernoFacadeEJB implements SessionBean
{
	
	private static Log _log = LogFactory.getLog( AuditoriaCuadernoFacadeEJB.class );
	//private static String EXPRESION = "((DominioSistraPluginCache)||(DominioSistraPlugin)||(PLUGIN_DOMINIOS\\.crearDominio)){1}\\(\\s*'(\\w+)'[^)]*\\)";
	private static String EXPRESION = "(DominioSistraPluginCache||DominioSistraPlugin||PLUGIN_DOMINIOS\\.crearDominio)\\(\\s*'(\\w+)'[^)]*\\)";
	private static Pattern PATTERN = Pattern.compile( EXPRESION );
	private static String EXPRESION1 = "(DominioSistraPlugin.*)\\(\\s*'(\\w+)'[^)]*\\)";
	private static String EXPRESION2 = "(PLUGIN_DOMINIOS\\.crearDominio)\\(\\s*'(\\w+)'[^)]*\\)";
	private static Pattern PATTERN1 = Pattern.compile( EXPRESION1 );
	private static Pattern PATTERN2 = Pattern.compile( EXPRESION2 );
	private static int SCRIPTS_POSITION = 0;
	private static int DOMINIOS_POSITION = 1;
	
	//private static Object[][] metodosAuditoria = new Object[][] 
	private static String [] metodosCampoFormulario = new String []{ 	
		"expresionAutocalculo", "expresionPostProceso", "expresionValoresPosibles", 
		"expresionValidacion",  "expresionDependencia" };  
  	private static Map propsAuditoria = new HashMap();
  	static 
	{ 
		propsAuditoria.put( Pantalla.class , new String []{ "expresion" } );
		propsAuditoria.put(  PropiedadSalida.class, new String []{ "valor" } );
		propsAuditoria.put(  CheckBox.class, metodosCampoFormulario );
		propsAuditoria.put(  ComboBox.class, metodosCampoFormulario );
		propsAuditoria.put(  FileBox.class, metodosCampoFormulario );
		propsAuditoria.put(  ListBox.class, metodosCampoFormulario );
		propsAuditoria.put(  RadioButton.class, metodosCampoFormulario );
		propsAuditoria.put(  TextBox.class, metodosCampoFormulario );
		propsAuditoria.put(  Patron.class,new String []{  "codigo" } );
		propsAuditoria.put(  DatoJustificante.class,new String []{  "visibleScript", "valorCampoScript" } );
		propsAuditoria.put(  EspecTramiteNivel.class, new String []{ "campoRdoNif", "campoRteNif", "campoRdoNom", "campoRteNom", 
									"campoCodigoPais", "campoCodigoProvincia", 
									"campoCodigoLocalidad", "validacionInicioScript" } );
		propsAuditoria.put(  DocumentoNivel.class, new String []{ "flujoTramitacionScript",  
								"formularioDatosInicialesScript", "formularioConfiguracionScript", 
								"formularioPlantillaScript", "formularioValidacionPostFormScript", 
								"pagoCalcularPagoScript", "formularioModificacionPostFormScript", 
								"firmante", "obligatorioScript" }  );
		
	}
	
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {

	}
	
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException
	{
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException
	{
		// TODO Auto-generated method stub

	}

	public void ejbActivate() throws EJBException, RemoteException
	{
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException
	{
		// TODO Auto-generated method stub

	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.audit}"
     */
	public boolean auditoriaRequerida( Long codigoCuadernoCarga ) throws Exception
	{
		_log.debug( "Comprobando si se requiere auditoria para el cuaderno de carga " + codigoCuadernoCarga );
			CuadernoCarga cuadernoCarga = obtenerCuadernoCarga( codigoCuadernoCarga );
			Set ficherosAuditoria = cuadernoCarga.getFicheros();
			if ( ficherosAuditoria == null )
			{
				return false;
			}
			Set dominiosCuaderno = obtenerListaDominiosCuaderno( cuadernoCarga );
			for( Iterator it = ficherosAuditoria.iterator(); it.hasNext(); )
			{
				FicheroCuaderno fichero = ( FicheroCuaderno ) it.next();
				Object entidadActual = obtenerEntidadActualPersistente( fichero );
				
				// Si no existe
				if ( entidadActual == null ) 
				{
					if  ( _log.isDebugEnabled() )
					{
						_log.debug( "No existe la entidad en bd para el fichero del cuaderno " + fichero.getNombre() );
					}
					return true;
				}
				// Existe y es trámite
				if ( fichero.isTramite() )
				{
					if ( _log.isDebugEnabled() )
					{
						_log.debug( "Auditando tramite para el fichero del cuaderno " + fichero.getNombre() );
					}
					String idTramite = obtenerIdEntidad( fichero.getNombre() );
					TramiteVersion tramiteVersion = ( TramiteVersion ) entidadActual;
					// Si no tiene ninguna versión, se necesita auditoria
					if ( tramiteVersion == null )
					{
						if ( _log.isDebugEnabled() )
						{
							_log.debug( "No existe la versión " + tramiteVersion.getVersion() + " para el tramite " + idTramite  );
						}
						return true;
					}
					
					DatosAuditoriaCuaderno datosAuditoriaTramite = auditarTramiteVersion( idTramite, TramiteVersion.fromXml( fichero.getContenido() ), tramiteVersion,  dominiosCuaderno );
					if ( datosAuditoriaTramite.isAuditoriaRequerida() )
					{
						_log.debug( "Auditoria requerida para el fichero " + fichero.getNombre() + " (tramite version existente: " + idTramite +  ": v "+ tramiteVersion.getVersion() +")" );
						return true;
					}
				}
				if ( fichero.isForm() )
				{
					if ( _log.isDebugEnabled() )
					{
						_log.debug( "Auditando formulario para el fichero del cuaderno " + fichero.getNombre() );
					}
					Formulario formularioACargar = Formulario.fromXml( fichero.getContenido() );
					Formulario formularioActual = ( Formulario ) entidadActual;
					// Se compara el formulario existente con el formulario
					// TODO rutina que recibe como entrada dos formularios y devuelve:
					// -> Lista de scripts nuevos o modificados
					// -> Lista de dominios nuevos o modificados por cuaderno de carga

					DatosAuditoriaCuaderno datosAuditoriaFormulario = auditarFormulario( formularioACargar, formularioActual,  dominiosCuaderno );
					if ( datosAuditoriaFormulario.isAuditoriaRequerida() )
					{
						_log.debug( "Auditoria requerida para el fichero " + fichero.getNombre() + "(formulario" + formularioActual.getModelo() + ")" );
						return true;
					}
				}
			}
		return false;
	}
	
	
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.audit}"
     */
	public DatosAuditoriaCuaderno auditoriaCuaderno( Long codigoCuadernoCarga ) throws Exception
	{ 
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Auditando cuaderno "  + codigoCuadernoCarga );
		}
		CuadernoCarga cuadernoCarga = obtenerCuadernoCarga( codigoCuadernoCarga );
		DatosAuditoriaCuaderno datosAuditoria = new DatosAuditoriaCuaderno();
		Set dominiosCuaderno = obtenerListaDominiosCuaderno( cuadernoCarga );
		// Se recorren los ficheros del cuaderno para extraer sus scripts y dominios utilizados y comparar con los
		// tramites, formularios o dominios existentes
		for ( Iterator it = cuadernoCarga.getFicheros().iterator(); it.hasNext(); )
		{
			FicheroCuaderno ficheroCuaderno = ( FicheroCuaderno ) it.next();
			Object entidadActual = obtenerEntidadActualPersistente( ficheroCuaderno ); 
			if ( ficheroCuaderno.isTramite() )
			{
				String idTramite = this.obtenerIdEntidad( ficheroCuaderno.getNombre() );
				//Tramite tramiteActual = ( Tramite ) entidadActual;
				//TramiteVersion tramiteVersion = obtenerUltimaVersion( tramiteActual );
				TramiteVersion tramiteVersion = ( TramiteVersion ) entidadActual;
				DatosAuditoriaCuaderno datosAuditoriaTramite = auditarTramiteVersion( idTramite, TramiteVersion.fromXml( ficheroCuaderno.getContenido() ), tramiteVersion, dominiosCuaderno );
				datosAuditoria.getListaScriptsAuditar().addAll( datosAuditoriaTramite.getListaScriptsAuditar() );
				datosAuditoria.getListaDominios().addAll( datosAuditoriaTramite.getListaDominios() );
				if ( _log.isDebugEnabled() ) _log.debug( "Lista de dominios despues de auditar tramite: " + datosAuditoria.getListaDominios() );
			}
			if ( ficheroCuaderno.isForm() )
			{
				Formulario formularioActual = ( Formulario ) entidadActual;
				Formulario formularioAuditoria = Formulario.fromXml( ficheroCuaderno.getContenido());
				DatosAuditoriaCuaderno datosAuditoriaFormulario = auditarFormulario( formularioAuditoria, formularioActual, dominiosCuaderno );
				datosAuditoria.getListaScriptsAuditar().addAll( datosAuditoriaFormulario.getListaScriptsAuditar() );
				datosAuditoria.getListaDominios().addAll( datosAuditoriaFormulario.getListaDominios() );
				if ( _log.isDebugEnabled() ) _log.debug( "Añadiendo dominios de formulario " + formularioAuditoria.getModelo() + ": " + datosAuditoria.getListaDominios() );
			}
			if ( ficheroCuaderno.isDominio() )
			{
				Dominio dominioActual = ( Dominio ) entidadActual;
				Dominio dominio = Dominio.fromXml( ficheroCuaderno.getContenido() );
				datosAuditoria.getListaDominios().add( auditarDominio( dominio, dominioActual ) );
			}
		}
		
		// Por ultimo, se recorren los dominios del cuaderno y se compara con la lista de auditoria de dominios
		// Si no existen en la lista de auditoria de cuaderno se trata de un dominio no utilizado
		// en el resto de ficheros del cuaderno. ( ESTADO_NUEVO_EN_CUADERNO o ESTADO_EXISTENTE_MODIFICADO_EN_CUADERNO )
		//datosAuditoria.getListaDominios().addAll( auditarScriptsCuaderno( dominiosCuaderno, datosAuditoria.getListaDominios() ) );
		
		if ( _log.isDebugEnabled() ) _log.debug( "Lista de dominios: " + datosAuditoria.getListaDominios() );
		
		return datosAuditoria;
	}
	
	private CuadernoCarga obtenerCuadernoCarga( Long codigoCuadernoCarga ) throws Exception
	{
		return DelegateUtil.getCuadernoCargaDelegate().obtenerCuadernoCarga( codigoCuadernoCarga );
	}
	
	private Set obtenerListaDominiosCuaderno( CuadernoCarga cuadernoCarga )
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Obteniendo lista de dominios del cuaderno " + cuadernoCarga.getDescripcion() );
		}
		LinkedHashSet setDominios = new LinkedHashSet();
		if ( cuadernoCarga.getFicheros() != null)
		{
			for ( Iterator it = cuadernoCarga.getFicheros().iterator(); it.hasNext(); )
			{
				FicheroCuaderno fichero = ( FicheroCuaderno ) it.next();
				if ( fichero.isDominio() )
				{
					setDominios.add( Dominio.fromXml( fichero.getContenido() ) );
				}
			}
		}
		return setDominios;
	}
	
	/**
	 * Para un trámite, dominio o formulario, devuelve su versión actual persistente 
	 * @param ficheroCuaderno
	 * @return TramiteVersion, Dominio o Formulario según el tipo del fichero del cuaderno de carga
	 * @throws Exception
	 */
	private Object obtenerEntidadActualPersistente( FicheroCuaderno ficheroCuaderno ) throws Exception
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Obteniendo entidad persistente para fichero " + ficheroCuaderno.getNombre() );
		}
		if ( ficheroCuaderno.isTramite() )
		{
			Object[] tupla = ( Object[] ) obtenerIdVersionTramite( ficheroCuaderno.getNombre() );
			
			return obtenerTramiteVersionCompleto( (String ) tupla[0], (( Integer ) tupla[1] ).intValue() );
		}
		if ( ficheroCuaderno.isForm() )
		{
			Formulario formulario = Formulario.fromXml( ficheroCuaderno.getContenido() );
			if ( formulario != null )
			{
				return obtenerFormulario( formulario.getModelo(), formulario.getVersion(), true );
			}
		}
		if ( ficheroCuaderno.isDominio() )
		{
			return obtenerDominio( obtenerIdEntidad( ficheroCuaderno.getNombre() ) );
		}
		return null;
	}
	
	private Formulario obtenerFormulario( String modelo, int version, boolean completo ) throws org.ibit.rol.form.persistence.delegate.DelegateException
	{
		FormularioDelegate formDelegate = org.ibit.rol.form.persistence.delegate.DelegateUtil.getFormularioDelegate();
		Formulario formulario = formDelegate.obtenerFormulario( modelo, version );
		if ( completo && formulario != null )
		{
			formulario = formDelegate.obtenerFormularioCompleto( formulario.getId() );
		}
		return formulario;
	}
	
	
	/* NOT USED
	private TramiteVersion obtenerUltimaVersion ( Tramite tramite ) throws DelegateException
	{
		if ( tramite == null )
		{
			return null;
		}
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Obteniendo ultima version para tramite " + tramite.getIdentificador() );
		}
		Set versiones = tramite.getVersiones();
		TramiteVersion tramiteVersion = null;
		if ( versiones != null )
		{
			for ( Iterator it = versiones.iterator(); it.hasNext(); )
			{
				tramiteVersion = ( TramiteVersion ) it.next();
				
			}
		}
		// Si tiene alguna versión, devolvemos la última versión completa
		if ( tramiteVersion != null )
		{
			if ( _log.isDebugEnabled() )
			{
				_log.debug( "Obtenida version " + tramiteVersion.getVersion() +  " para tramite " + tramite.getIdentificador() );
			}
			tramiteVersion = DelegateUtil.getTramiteVersionDelegate().obtenerTramiteVersionCompleto( tramite.getIdentificador(), tramiteVersion.getVersion() );
		}
		return tramiteVersion;
	}
	*/
	private TramiteVersion obtenerTramiteVersionCompleto( String identificador, int version ) throws DelegateException
	{
		return DelegateUtil.getTramiteVersionDelegate().obtenerTramiteVersionCompleto( identificador, version );
	}
	
	private Dominio obtenerDominio( String identificadorDominio ) throws DelegateException
	{
		return DelegateUtil.getDominioDelegate().obtenerDominio( identificadorDominio );
	}
	
	private ElementoAuditoriaDominio auditarDominio( Dominio dominio, Dominio dominioExistente ) throws DelegateException
	{
		ElementoAuditoriaDominio datosAuditoria = new ElementoAuditoriaDominio();
		datosAuditoria.setNombre( dominio.getIdentificador() );
		if ( dominioExistente == null )
		{
			datosAuditoria.setEstado( ElementoAuditoriaDominio.ESTADO_NUEVO_EN_CUADERNO );
		}
		else
		{
			datosAuditoria.setEstado( ElementoAuditoriaDominio.ESTADO_EXISTENTE_MODIFICADO_EN_CUADERNO );
			datosAuditoria.setDominio( dominio );
		}
		return datosAuditoria;
	}
	
	/**
	 * Audita la versión de un trámite
	 * @param versionExistente
	 * @param versionAAuditar
	 * @return
	 */
	private DatosAuditoriaCuaderno auditarTramiteVersion( String idTramite, TramiteVersion versionAAuditar, TramiteVersion versionExistente, Set dominiosCuaderno ) throws Exception
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Auditando version de tramite " );
			_log.debug( "Dominios incluidos en el cuaderno de carga " + dominiosCuaderno  );
		}
		Object[] datosVersionCuaderno  = inspectTramiteVersion( idTramite, versionAAuditar );
		Map scriptsVersionCuaderno = ( Map ) datosVersionCuaderno[SCRIPTS_POSITION];
		Set domsVersionCuaderno = ( Set ) datosVersionCuaderno[ DOMINIOS_POSITION ];
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "scripts version tramite cuaderno  " + scriptsVersionCuaderno );
			_log.debug( "dominios utilizados por el tramite cuaderno " + domsVersionCuaderno );
		}

		
		Object[] datosVersionExistente = null;
		Map scriptsVersionExistente = null;
		if ( versionExistente != null )
		{
			datosVersionExistente = inspectTramiteVersion( idTramite, versionExistente );
			scriptsVersionExistente = ( Map ) datosVersionExistente[SCRIPTS_POSITION];
			if ( _log.isDebugEnabled() )
			{
				_log.debug( "scripts version tramite existente  " + scriptsVersionExistente );
			}
		}
		return auditarEntidad( scriptsVersionCuaderno, domsVersionCuaderno, scriptsVersionExistente, dominiosCuaderno );
	}
	
	/**
	 * Audita un formulario
	 * @param formularioAAuditar
	 * @param formularioExistente
	 * @param dominiosCuaderno
	 * @return
	 * @throws Exception
	 */
	private DatosAuditoriaCuaderno auditarFormulario( Formulario formularioAAuditar, Formulario formularioExistente,  Set dominiosCuaderno ) throws Exception
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Auditando formulario " + formularioAAuditar.getModelo() );
			_log.debug( "Dominios incluidos en el cuaderno de carga " + dominiosCuaderno  );
		}
		Object[] datosFormularioCuaderno = inspectFormulario( formularioAAuditar );
		Map scriptsFormularioCuaderno = ( Map ) datosFormularioCuaderno[ SCRIPTS_POSITION ];
		Set domsFormularioCuaderno = ( Set ) datosFormularioCuaderno [ DOMINIOS_POSITION ];
		
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "scripts formulario cuaderno  " + scriptsFormularioCuaderno );
			_log.debug( "dominios utilizados por el formulario cuaderno " + domsFormularioCuaderno );
		}
		
		Object[] datosFormularioExistente = null;
		Map scriptsFormularioExistente = null;
		if ( formularioExistente != null )
		{
			datosFormularioExistente = inspectFormulario( formularioExistente );
			scriptsFormularioExistente = ( Map ) datosFormularioExistente[SCRIPTS_POSITION] ;
			if ( _log.isDebugEnabled() )
			{
				_log.debug( "scripts formulario existente  " + scriptsFormularioExistente );
			}
		}
		return auditarEntidad( scriptsFormularioCuaderno, domsFormularioCuaderno, scriptsFormularioExistente, dominiosCuaderno );
	}

	/**
	 * audita un trámite o un formulario, devolviendo la lista de scripts nuevos o modificados así como la lista de dominios utilizados
	 * en relación al trámite o formulario ya existentes.
	 * @param scriptsCuaderno
	 * @param dominiosRequeridos
	 * @param scriptsExistentes
	 * @param dominiosCuaderno
	 * @return DatosAuditoriaCuaderno
	 * @throws Exception
	 */
	private DatosAuditoriaCuaderno auditarEntidad( Map scriptsCuaderno, Set dominiosRequeridos, Map scriptsExistentes, Set dominiosCuaderno ) throws Exception
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Auditando entidad" );
		}
		DatosAuditoriaCuaderno datosAuditoria = new DatosAuditoriaCuaderno();
		Set listaDatosDominio = new LinkedHashSet();
		List listaDatosScript  = new ArrayList();
		// Analizamos dominios 
		for ( Iterator it = dominiosRequeridos.iterator(); it.hasNext(); )
		{
			String idDominio = ( String ) it.next();
			ElementoAuditoriaDominio elementoDominio = new ElementoAuditoriaDominio();
			Dominio dominioCuaderno = null;
			Dominio dominioExistente = obtenerDominio( idDominio ); 
			elementoDominio.setNombre( idDominio );
			// Comprobamos si viene en el cuaderno de carga
			for ( Iterator it2 = dominiosCuaderno.iterator(); it2.hasNext(); )
			{
				Dominio dominio = ( Dominio ) it2.next();
				if ( idDominio.equals( dominio.getIdentificador() ) )
				{
					dominioCuaderno = dominio;
				}
			}
			//  Comprobamos si el dominio existe ya en el sistema
			if (  dominioExistente != null )
			{
				// El dominio existe y no se modifica en el cuaderno
				if ( dominioCuaderno == null )
				{
					if ( _log.isDebugEnabled() )
					{
						_log.debug( "Dominio " + idDominio + " EXISTE" );
					}
					elementoDominio.setEstado( ElementoAuditoriaDominio.ESTADO_EXISTENTE );
					elementoDominio.setDominio( dominioExistente );
				}
				// El dominio existe y se modifica en el cuaderno de carga
				else
				{
					if ( _log.isDebugEnabled() )
					{
						_log.debug( "Dominio " + idDominio + " EXISTE Y ES MODIFICADO EN EL CUADERNO" );
					}
					datosAuditoria.setAuditoriaRequerida( true );
					elementoDominio.setEstado( ElementoAuditoriaDominio.ESTADO_EXISTENTE_MODIFICADO_EN_CUADERNO );
					elementoDominio.setDominio( dominioCuaderno );
				}
			}
			// Dominio nuevo
			else
			{
				// El dominio es nuevo y no se trae en el cuaderno
				if ( dominioCuaderno == null )
				{
					if ( _log.isDebugEnabled() )
					{
						_log.debug( "Dominio " + idDominio + " NUEVO" );
					}
					datosAuditoria.setAuditoriaRequerida( true );
					elementoDominio.setEstado( ElementoAuditoriaDominio.ESTADO_NUEVO );
				}
				// El dominio es nuevo y se trae en el cuaderno
				else
				{
					if ( _log.isDebugEnabled() )
					{
						_log.debug( "Dominio " + idDominio + " NUEVO INCLUIDO EN EL CUADERNO" );
					}
					datosAuditoria.setAuditoriaRequerida( true );
					elementoDominio.setEstado( ElementoAuditoriaDominio.ESTADO_NUEVO_EN_CUADERNO );
					elementoDominio.setDominio( dominioCuaderno );
				}
			}
			listaDatosDominio.add( elementoDominio );
		}
		// Analizamos scripts
		for ( Iterator it = scriptsCuaderno.keySet().iterator(); it.hasNext(); )
		{
			String key = ( String ) it.next();
			String scriptCuaderno	= ( String ) scriptsCuaderno.get( key );
			String estado = null;
			//ElementoAuditoriaScript elementoAuditoriaScript = new ElementoAuditoriaScript();
			ElementoAuditoriaScript elementoAuditoriaScript = null;
			
			// Si no existen scripts para la entidad, se marca como "script nuevo"
			if ( scriptsExistentes == null )
			{
				if ( _log.isDebugEnabled() )
				{
					_log.debug( "Script " + key + " NUEVO" );
				}
				datosAuditoria.setAuditoriaRequerida( true );
				estado = ElementoAuditoriaScript.ESTADO_NUEVO;
			}
			else
			{
				if ( !scriptsExistentes.containsKey( key ) )
				{
					if ( _log.isDebugEnabled() )
					{
						_log.debug( "Script " + key + " NUEVO" );
					}
					datosAuditoria.setAuditoriaRequerida( true );
					estado = ElementoAuditoriaScript.ESTADO_NUEVO;
				}
				else
				{
					String scriptExistente 	= ( String ) scriptsExistentes.get( key );
					if ( !scriptCuaderno.equals( scriptExistente ) ) 
					{
						if ( _log.isDebugEnabled() )
						{
							_log.debug( "Script " + key + " MODIFICADO EN EL CUADERNO" );
						}
						datosAuditoria.setAuditoriaRequerida( true );
						estado = ElementoAuditoriaScript.ESTADO_MODIFICADO;
					}
					else
					{
						if ( _log.isDebugEnabled() )
						{
							_log.debug( "Script " + key + " YA EXISTENTE Y NO MODIFICADO EN EL CUADERNO" );
						}
						continue;
					}
				}
			}
			if ( estado != null )
			{
				elementoAuditoriaScript = new ElementoAuditoriaScript();
				elementoAuditoriaScript.setContenidoScript( scriptCuaderno );
				elementoAuditoriaScript.setEstado( estado );
				elementoAuditoriaScript.setDescripcionKey( AuditoriaUtil.getDescriptionKey( key ) );
				elementoAuditoriaScript.setNombre( AuditoriaUtil.getNombre( key ) );
				listaDatosScript.add( elementoAuditoriaScript );
			}
		}
		datosAuditoria.setListaDominios( listaDatosDominio );
		datosAuditoria.setListaScriptsAuditar( listaDatosScript );
		return datosAuditoria;
	}
	 
	
	/**
	 * 
	 * @param tramiteVersion
	 * @return
	 * @throws Exception
	 */
	private Object[] inspectTramiteVersion( String idTramite, TramiteVersion tramiteVersion ) throws Exception
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Inspect tramiteVersion"  );
		}
		EspecTramiteNivel especificacionesGenericas = tramiteVersion.getEspecificaciones();
		LinkedHashMap lhmScripts = new LinkedHashMap();
		LinkedHashSet setDominios = new LinkedHashSet();
		//String idTramite = tramiteVersion.getTramite().getIdentificador();
		int version = tramiteVersion.getVersion();
		
		inspectEntidad( AuditoriaUtil.getIdTraVer( idTramite, version ), especificacionesGenericas, lhmScripts,  setDominios);
		List datosJustificante = especificacionesGenericas.getDatosJustificante();
		for ( Iterator it = datosJustificante.iterator(); it.hasNext(); )
		{
			DatoJustificante datoJustificante = ( DatoJustificante ) it.next();
			if ( datoJustificante.getTipo() == DatoJustificante.TIPO_CAMPO )
			{
				inspectEntidad( AuditoriaUtil.getIdTraVerJust( idTramite, version, datoJustificante.getTipo(), datoJustificante.getOrden()), 
						datoJustificante, lhmScripts, setDominios );
			}
		}
		
		for (Iterator it = tramiteVersion.getNiveles().iterator();it.hasNext();)
		{
    		TramiteNivel tn  = (TramiteNivel) it.next();
    		EspecTramiteNivel especTramiteNivel = tn.getEspecificaciones();
    		inspectEntidad( AuditoriaUtil.getIdTraNivel( idTramite, version, tn.getNivelAutenticacion() ), especTramiteNivel, lhmScripts, setDominios );
    		List datosJustificanteNivel = especTramiteNivel.getDatosJustificante();
    		for ( Iterator it1 = datosJustificanteNivel.iterator(); it1.hasNext(); )
    		{
    			DatoJustificante datoJustificante = ( DatoJustificante ) it1.next();
    			inspectEntidad( AuditoriaUtil.getIdTraNivelJust( idTramite, version, tn.getNivelAutenticacion(), datoJustificante.getTipo(), datoJustificante.getOrden()),datoJustificante, lhmScripts, setDominios );
    		}
    	}
		for (Iterator it = tramiteVersion.getDocumentos().iterator();it.hasNext();){
    		Documento documento = (Documento) it.next();
    		for ( Iterator it2 = documento.getNiveles().iterator(); it2.hasNext(); )
    		{
    			DocumentoNivel docNivel = ( DocumentoNivel ) it2.next();
    			inspectEntidad( AuditoriaUtil.getIdDoc( idTramite, version, docNivel.getDocumento().getIdentificador(), docNivel.getNivelAutenticacion() ), docNivel, lhmScripts, setDominios );
    		}
    	}
		return new Object[] { lhmScripts, setDominios };
	}
	
	/**
	 * 
	 * @param formulario
	 * @return
	 * @throws Exception
	 */
	private Object[] inspectFormulario( Formulario formulario ) throws Exception
	{
		LinkedHashMap lhmScripts = new LinkedHashMap();
		LinkedHashSet listaDominios = new LinkedHashSet();
		for (int i = 0; i < formulario.getPantallas().size(); i++) 
		{
	         Pantalla pantalla = (Pantalla)formulario.getPantallas().get(i);
	         String idPantalla = AuditoriaUtil.getIdPantalla( formulario.getModelo(), formulario.getVersion(), pantalla.getNombre() );
	         inspectEntidad( idPantalla, pantalla, lhmScripts, listaDominios );
	         for (int j = 0; j < pantalla.getCampos().size(); j++) 
	         {
	        	 Campo campo =  (Campo)pantalla.getCampos().get(j);
	        	 campo.getTipoValor();
	        	 inspectEntidad( AuditoriaUtil.getIdCampoPantalla( formulario.getModelo(),formulario.getVersion(), pantalla.getNombre(), campo.getTipoValor(), campo.getNombreLogico() ), campo, lhmScripts, listaDominios );
	        	 Patron patron = campo.getPatron();
	        	 if ( patron != null )
	        	 {
	        		 inspectEntidad( AuditoriaUtil.getIdPatronCampo( formulario.getModelo(),formulario.getVersion(), pantalla.getNombre(), campo.getTipoValor(), campo.getNombreLogico(), patron.getNombre() ), patron, lhmScripts, listaDominios );
	        	 }
	         }
		}
		for (int i = 0; i < formulario.getSalidas().size(); i++) 
		{
            Salida salida = (Salida) formulario.getSalidas().get(i);
            salida.getPunto().getNombre();
            for (Iterator iterator = salida.getPropiedades().iterator(); iterator.hasNext();) 
            {
                PropiedadSalida propiedad = (PropiedadSalida) iterator.next();
                if ( propiedad.isExpresion() )
                {
                	inspectEntidad( AuditoriaUtil.getIdPropSalida( formulario.getModelo(), formulario.getVersion(), salida.getPunto().getNombre(), propiedad.getNombre() ), propiedad, lhmScripts, listaDominios );
                }
            }
        }
		return new Object[] { lhmScripts, listaDominios };
	}
	
	
	/**
	 * Metodo que ejecuta los métodos que pueden contener scripts para la entidad.
	 * Analiza la entidad y obtiene los scripts utilizados, así como los dominios que son necesarios
	 * para la ejecución de dichos scripts
	 * @param identificador
	 * @param object
	 * @param listaAuditoria
	 * @param dominios
	 * @throws Exception
	 */
	private void inspectEntidad( String identificador, Object object, Map listaAuditoria, Set dominios ) throws Exception
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Inspecting " + identificador + "; class " + object.getClass() );
		}
		String[] propiedadesAuditar = ( String [] ) propsAuditoria.get( object.getClass() );
		for ( int i = 0; i < propiedadesAuditar.length; i++ )
		{
			String nombrePropiedad = propiedadesAuditar[i];
			String methodName = "get" + StringUtils.capitalise( nombrePropiedad );
			Object objValPropiedad = MethodUtils.invokeMethod( object, methodName, null );
			String script = null;  
			if ( objValPropiedad == null )
			{
				continue;
			}
			
			if ( objValPropiedad instanceof byte[] )
			{
				script = ScriptUtil.scriptToString( ( byte [] ) objValPropiedad );
			}
			else
			{
				script = ( String ) objValPropiedad;
			}
			
			if ( _log.isDebugEnabled() )
			{
				_log.debug( identificador + " property " + nombrePropiedad + " has value " + script );
			}
			
			listaAuditoria.put( identificador + "-" + nombrePropiedad, script );
			dominios.addAll ( obtenerListaDominiosUtilizados( script ) ); 
			if ( _log.isDebugEnabled() )
			{
				_log.debug( "Dominios utilizados tras el paso por el identificador " + identificador + ": " + dominios );
			}
		}
		
		
			
		//propsAuditoria
	}
	
	private Set obtenerListaDominiosUtilizados( String script )
	{
		LinkedHashSet set = new LinkedHashSet();
		
		Matcher matcher = PATTERN1.matcher( script );
		while ( matcher.find() )
		{
			set.add( matcher.group( 2 ) );
		}
		
		Matcher matcher2 = PATTERN2.matcher( script );
		while ( matcher2.find() )
		{
			set.add( matcher2.group( 2 ) );
		}
		
		return set;
	}
	
	private String obtenerIdEntidad( String nomFicXml )
	{
		String idTramite = null;
		if ( nomFicXml != null )
		{
			nomFicXml = removeExtension( nomFicXml );
			String [] partsNom = nomFicXml.split( "\\-" );
			if ( partsNom.length > 1 )
			{
				idTramite = partsNom[1];
			}
			else
			{
				idTramite = partsNom[0];
			}
		}
		return idTramite;
	}
	
	private Object[] obtenerIdVersionTramite( String nomFicXml ) throws Exception
	{
		String idTramite = null;
		int verTramite = 0;
		if ( nomFicXml != null )
		{
			nomFicXml = removeExtension( nomFicXml );
			String [] partsNom = nomFicXml.split( "\\-" );
			if ( partsNom.length == 3 )
			{
				idTramite = partsNom[1];
				verTramite = Integer.parseInt( partsNom[2], 10 );
			}
			else
			{
				throw new Exception( "El tramite  se importó con un nombre de fichero incorrecto (" + nomFicXml + ")" );
			}
		}
		return new Object [] { idTramite, new Integer( verTramite ) };
	}

	
	private String removeExtension( String fileName )
	{
		return fileName.replaceAll( "\\..*$", "" );
	}
	
	

}
