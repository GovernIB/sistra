package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.zonaper.modelInterfaz.DetalleNotificacionesProcedimiento;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaExpedientePAD;
import es.caib.zonaper.modelInterfaz.PaginaPAD;
import es.caib.zonaper.modelInterfaz.ParametrosTramiteSubsanacionPAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.PreregistroPAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.intf.PadFacade;
import es.caib.zonaper.persistence.util.PadFacadeUtil;

/**
 * Interfaz para operar con la PAD
 */
public class PadDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public TramitePersistentePAD obtenerTramitePersistente(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerTramitePersistente(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public List obtenerTramitesPersistentesUsuario() throws DelegateException {
        try {
            return getFacade().obtenerTramitesPersistentesUsuario();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerTramitesPersistentesUsuario(boolean filtroPersistentes) throws DelegateException {
        try {
            return getFacade().obtenerTramitesPersistentesUsuario(filtroPersistentes);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerTramitesPersistentesUsuario(String tramite,int version) throws DelegateException {
        try {
            return getFacade().obtenerTramitesPersistentesUsuario(tramite,version);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
		
    public List obtenerTramitesPersistentesEntidadDelegada(String nifEntidad)throws DelegateException {
        try {
            return getFacade().obtenerTramitesPersistentesEntidadDelegada(nifEntidad);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List obtenerTramitesPersistentesEntidadDelegada(String nifEntidad, boolean filtroPersistentes)throws DelegateException {
        try {
            return getFacade().obtenerTramitesPersistentesEntidadDelegada(nifEntidad, filtroPersistentes);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public String obtenerEstadoTramite(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerEstadoTramite(idPersistencia) ;
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
	public String grabarTramitePersistente(TramitePersistentePAD obj) throws DelegateException {
        try {
            return getFacade().grabarTramitePersistente(obj);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
	public void borrarTramitePersistente(String idPersistencia, boolean backup) throws DelegateException {
        try {
            getFacade().borrarTramitePersistente(idPersistencia, backup);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
		
	public void logPad(ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos)  throws DelegateException{
		try {
            getFacade().logPad(refAsiento,refJustificante,refDocumentos);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	public void confirmarPreregistro( Long codigoPreregistro, String oficina, String codProvincia, String codMunicipio, String descMunicipio ) throws DelegateException
	{
		try
		{
			getFacade().confirmarPreregistro( codigoPreregistro, oficina, codProvincia, codMunicipio, descMunicipio);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	
	public void confirmacionAutomaticaPreenvio(String idPersistencia, String numeroPreregistro,Timestamp fechaPreregistro) throws DelegateException
	{
		try
		{
			getFacade().confirmacionAutomaticaPreenvio( idPersistencia,  numeroPreregistro,  fechaPreregistro);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	
	public void confirmarPreenvioAutomatico( Long codigoPreregistro, String oficina, String codProvincia, String codMunicipio, String descMunicipio )throws DelegateException
	{
		try
		{
			getFacade().confirmarPreenvioAutomatico( codigoPreregistro, oficina,  codProvincia, codMunicipio, descMunicipio);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	/**
     * Metodo para confirmar un prerregistro que no se ha confirmado en punto de registro
     * (se ha presentado por registro normal o en otro lugar) y ha llegado al gestor
     *
     */
    public void confirmarPreregistroIncorrecto( Long codigoPreregistro,String numeroRegistro,Timestamp fechaRegistro) throws DelegateException
	{
		try
		{
			getFacade().confirmarPreregistroIncorrecto( codigoPreregistro, numeroRegistro, fechaRegistro);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
    
	
    public PreregistroPAD obtenerInformacionPreregistro( String numeroPreregistro) throws DelegateException
	{
		try
		{
			return getFacade().obtenerInformacionPreregistro(numeroPreregistro);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}      
		
	public PersonaPAD obtenerDatosPersonaPADporUsuario( String usuario ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDatosPersonaPADporUsuario( usuario );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public boolean existePersonaPADporUsuario( String usuario ) throws DelegateException
	{
		try
		{
			return getFacade().existePersonaPADporUsuario( usuario );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public PersonaPAD obtenerDatosPersonaPADporNif( String nif ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDatosPersonaPADporNif( nif );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	/* NO SE USA
	public PersonaPAD altaPersona( PersonaPAD personaPAD ) throws DelegateException
	{
		try
		{
			return getFacade().altaPersona( personaPAD );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	*/
	public void actualizarCodigoUsuario( String usuOld, String usuNew)  throws DelegateException
	{
		try
		{
			getFacade().actualizarCodigoUsuario(  usuOld, usuNew );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Date obtenerAcuseRecibo(String entidad, String numeroRegistro )throws DelegateException
	{
		try
		{
			return getFacade().obtenerAcuseRecibo( entidad, numeroRegistro );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public DetalleNotificacionesProcedimiento obtenerDetalleNotificacionesProcedimiento(String idProcedimiento,Date desde, Date hasta) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDetalleNotificacionesProcedimiento(idProcedimiento, desde, hasta);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public void logRegistro(String entidad, char tipo, String numeroRegistro, Date fechaRegistro, String error) throws DelegateException{
		try{
			getFacade().logRegistro(entidad,tipo,numeroRegistro,fechaRegistro,error);
		}catch (Exception e){
            throw new DelegateException(e);
        }
	}
	
	public void logRegistroExternoPreparado(ReferenciaRDS referenciaAsiento, Map referenciaAnexos, String identificadorPersistencia, int diasPersistencia) throws DelegateException{
		try{
			getFacade().logRegistroExternoPreparado(referenciaAsiento, referenciaAnexos, identificadorPersistencia, diasPersistencia);
		}catch (Exception e){
            throw new DelegateException(e);
        }
	}
	
	public boolean esDelegado() throws DelegateException	
	{
		try
		{
			return getFacade().esDelegado( );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public ParametrosTramiteSubsanacionPAD recuperaParametrosTramiteSubsanacion(String key) throws DelegateException	
	{
		try
		{
			return getFacade().recuperaParametrosTramiteSubsanacion(key );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	/**
     * Obtiene permisos de delegacion sobre la entidad del usuario autenticado
     * 
     * @param nif nif/cif entidad
     * @return permisos entidad
     * 
     */
	public String obtenerPermisosDelegacion(String nifEntidad)throws DelegateException	
	{
		try
		{
			return getFacade().obtenerPermisosDelegacion( nifEntidad);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	/**
     * Obtiene permisos de delegacion sobre la entidad del nif indicado
     * 
     * @param nif nif/cif entidad
     * @param nif nif usuario
     * @return permisos entidad
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String obtenerPermisosDelegacion(String nifEntidad, String nifUsuario) throws DelegateException	
	{
		try
		{
			return getFacade().obtenerPermisosDelegacion( nifEntidad, nifUsuario);			
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	/**
     * Indica si la entidad tiene habilitada la delegacion
     * 
     * @param nif nif/cif entidad
     * @return boolean
     * 
     */
	public boolean habilitadaDelegacion(String nifEntidad) throws DelegateException	
	{
		try
		{
			return getFacade().habilitadaDelegacion( nifEntidad);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	/**
     * Indica que se ha remitido el tramite para la firma de documentos
     * 
     * @param idPersistencia
     * 
     */
	public void avisarPendienteFirmarDocumentos(String idPersistencia) throws DelegateException	
	{
		try
		{
			getFacade().avisarPendienteFirmarDocumentos( idPersistencia);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	
	/**
     * Indica que se ha remitido el tramite para su presentacion
     * 
     * @param idPersistencia
     * 
     */
	public void avisarPendientePresentacionTramite(String idPersistencia) throws DelegateException	
	{
		try
		{
			getFacade().avisarPendientePresentacionTramite( idPersistencia);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public PaginaPAD busquedaPaginadaExpedientesGestor(FiltroBusquedaExpedientePAD filtro, int numPagina, int longPagina) throws DelegateException	
	{
		try
		{
		 return	getFacade().busquedaPaginadaExpedientesGestor( filtro, numPagina, longPagina);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void enviarSmsVerificarMovil(String idPersistencia, String idProcedimiento, String movil, String codigoSms, String idioma, int minutosCaducidad) throws DelegateException	
	{
		try
		{
		 	getFacade().enviarSmsVerificarMovil(idPersistencia, idProcedimiento, movil, codigoSms, idioma, minutosCaducidad);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void logSmsVerificarMovil(String idPersistencia, String movil, String codigoSms) throws DelegateException	
	{
		try
		{
		 	getFacade().logSmsVerificarMovil(idPersistencia, movil, codigoSms);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public List obtenerProcedimientosUsuario(String lang, Date fecha) throws DelegateException	
	{
		try
		{
		 	return getFacade().obtenerProcedimientosUsuario(lang, fecha);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	 public List obtenerTramitesIdUsuario() throws DelegateException	
		{
			try
			{
			 	return getFacade().obtenerTramitesIdUsuario();
			}
			catch( Exception e )
			{
				throw new DelegateException( e );
			}
		}
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private PadFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return PadFacadeUtil.getHome( ).create();
    }

    protected PadDelegate() throws DelegateException {
     
    }                  
}

