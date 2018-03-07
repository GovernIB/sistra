package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.model.TramitePersistenteBackup;
import es.caib.zonaper.persistence.intf.TramitePersistenteFacade;
import es.caib.zonaper.persistence.util.TramitePersistenteFacadeUtil;

/**
 * Business delegate para operar con TramitePersistente.
 */
public class TramitePersistenteDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public String grabarTramitePersistente(TramitePersistente tramite) throws DelegateException {
        try {
            return getFacade().grabarTramitePersistente(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public TramitePersistente obtenerTramitePersistenteBackup(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerTramitePersistenteBackup(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public TramitePersistente obtenerTramitePersistente(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerTramitePersistente(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarTramitePersistentesUsuario() throws DelegateException {
        try {
            return getFacade().listarTramitePersistentesUsuario();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        }
    }
    
    public List listarTramitePersistentesUsuario(Date fecha) throws DelegateException {
        try {
            return getFacade().listarTramitePersistentesUsuario(fecha);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        }
    }

    public List listarTramitePersistentesEntidadDelegada(String nifEntidad) throws DelegateException {
        try {
            return getFacade().listarTramitePersistentesEntidadDelegada(nifEntidad);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public int numeroTramitesPersistentesUsuario() throws DelegateException {
        try {
            return getFacade().numeroTramitesPersistentesUsuario();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        }
    }

    public int numeroTramitesPersistentesAnonimos(Date fechaInicial, Date fechaFinal, String modelo) throws DelegateException {
        try {
            return getFacade().numeroTramitesPersistentesAnonimos(fechaInicial,fechaFinal,modelo);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        }
    }

    public List listarTramitesPersistentes(Date fechaInicial, Date fechaFinal, String modelo, String nivelAutenticacion) throws DelegateException {
        try {
            return getFacade().listarTramitesPersistentes(fechaInicial,fechaFinal,modelo,nivelAutenticacion);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        }
    }

    public List listarTramitesPersistentesBackup(Date fechaInicial, Date fechaFinal, String modelo, String nivelAutenticacion) throws DelegateException {
        try {
            return getFacade().listarTramitesPersistentesBackup(fechaInicial,fechaFinal,modelo,nivelAutenticacion);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        }
    }

    public List listarTramitePersistentesUsuario(String tramite,int version) throws DelegateException {
        try {
            return getFacade().listarTramitePersistentesUsuario(tramite,version);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        }
    }

    public void borrarTramitePersistente(String id) throws DelegateException {
        try {
            getFacade().borrarTramitePersistente(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarDocumentosTramitePersistente(String id) throws DelegateException {
        try {
            getFacade().borrarDocumentosTramitePersistente(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void backupTramitePersistente( TramitePersistente tramitePersistente ) throws DelegateException
    {
    	try
    	{
    		getFacade().backupTramitePersistente( tramitePersistente );
    	}
    	catch (Exception e)
	    {
	        throw new DelegateException(e);
	    }
    }

    public List listarTramitePersistentesCaducados( Date fecha, int maxTram ) throws DelegateException
    {
    	try
    	{
    		return getFacade().listarTramitePersistentesCaducados( fecha, maxTram );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarTramitePersistenteBackup( TramitePersistenteBackup tramitePersistenteBackup ) throws DelegateException{
    	try
    	{
    		getFacade().borrarTramitePersistenteBackup( tramitePersistenteBackup );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarTramitePersistentesBackup( Date fecha, int maxTram ) throws DelegateException{
    	try
    	{
    		return getFacade().listarTramitePersistentesBackup( fecha, maxTram );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /**
     * Obtiene documento persistente
     *
     * @param codigo del documento
     * @return Documento relacionado con el codigo entrado
     *
     */
	public DocumentoPersistente obtenerDocumentoTramitePersistente(Long codigo) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDocumentoTramitePersistente(  codigo );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}

	 public void actualizarInfoDelegacionDocumentoTramitePersistente(Long codigo, String estadoDelegacion,
	    		String firmantes, String firmantesPendientes) throws DelegateException	{
		 try
			{
			 	getFacade().actualizarInfoDelegacionDocumentoTramitePersistente(codigo, estadoDelegacion,
			    		firmantes, firmantesPendientes);
			}
			catch( Exception e )
			{
				throw new DelegateException( e );
			}
	 }

	 public void actualizarInfoDelegacionTramitePersistente(String idPersistencia, String estadoDelegacion)throws DelegateException	{
		 try
			{
			 	getFacade().actualizarInfoDelegacionTramitePersistente(idPersistencia, estadoDelegacion);
			}
			catch( Exception e )
			{
				throw new DelegateException( e );
			}
	 }

	 public boolean existeTramitePersistente(String id) throws DelegateException	{
		 try
			{
			 	return getFacade().existeTramitePersistente(id);
			}
			catch( Exception e )
			{
				throw new DelegateException( e );
			}
	 }

	 public void avisoPagoTelematicoFinalizado(String idPersistencia) throws DelegateException	{
		 try
			{
			 	getFacade().avisoPagoTelematicoFinalizado(idPersistencia);
			}
			catch( Exception e )
			{
				throw new DelegateException( e );
			}
	 }

	 public List obtenerTramitesPendienteAvisoPagoTelematicoFinalizado() throws DelegateException	{
		 try
			{
			 	return getFacade().obtenerTramitesPendienteAvisoPagoTelematicoFinalizado();
			}
			catch( Exception e )
			{
				throw new DelegateException( e );
			}
	 }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private TramitePersistenteFacade getFacade() throws NamingException,CreateException,RemoteException {
    	return TramitePersistenteFacadeUtil.getHome( ).create();
    }

    protected TramitePersistenteDelegate() throws DelegateException {

    }
}

