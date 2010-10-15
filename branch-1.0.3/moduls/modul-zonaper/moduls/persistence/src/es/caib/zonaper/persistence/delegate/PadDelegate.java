package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
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
	
	public List obtenerTramitesPersistentesUsuario(String tramite,int version) throws DelegateException {
        try {
            return getFacade().obtenerTramitesPersistentesUsuario(tramite,version);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public  String obtenerEstadoTramiteUsuario(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerEstadoTramiteUsuario(idPersistencia) ;
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public String obtenerEstadoTramiteAnonimo(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerEstadoTramiteAnonimo(idPersistencia);
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
        
	public void borrarTramitePersistente(String idPersistencia) throws DelegateException {
        try {
            getFacade().borrarTramitePersistente(idPersistencia);            
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
	
	public Date obtenerAcuseRecibo(String numeroRegistro )throws DelegateException
	{
		try
		{
			return getFacade().obtenerAcuseRecibo( numeroRegistro );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
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

