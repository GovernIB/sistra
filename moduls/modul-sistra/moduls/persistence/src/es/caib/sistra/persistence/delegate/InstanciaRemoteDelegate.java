package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.sistra.model.InstanciaBean;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.intf.TramiteProcessor;
import es.caib.sistra.persistence.util.TramiteProcessorUtil;
import es.caib.sistra.plugins.firma.FirmaIntf;


public class InstanciaRemoteDelegate implements InstanciaDelegate
{

	public void create(String tramite, int version,char nivel, Locale idioma,Map parametrosInicio,  String perfilAcceso, String nifEntidad)
			throws DelegateException
	{
		try
		{
			TramiteProcessor remote = TramiteProcessorUtil.getHome().create( tramite, version,nivel, idioma, parametrosInicio,   perfilAcceso,  nifEntidad );
			remoteHandle = remote.getHandle();
		} catch (CreateException e) {
	        throw new DelegateException(e);
	    } catch (RemoteException e) {
	        throw new DelegateException(e);
	    } catch (NamingException e) {
	        throw new DelegateException(e);
	    }


	}



	public InstanciaBean obtenerInstanciaBean() throws DelegateException
	{
		try
		{
			return getRemote().obtenerInstanciaBean();
		}
		catch ( RemoteException e )
		{
            throw new DelegateException(e);
        }
	}



	public RespuestaFront informacionInicial() throws DelegateException
	{
        try
        {
        	return getRemote().informacionInicial();
        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}


	public RespuestaFront obtenerTramitesPersistencia(String tramite,int version) throws DelegateException
	{
		 try
	        {
	        	return getRemote().obtenerTramitesPersistencia(tramite,version);
	        } catch (RemoteException e)
	        {
	            throw new DelegateException(e);
	        }
	}

	public RespuestaFront iniciarTramite() throws DelegateException
	{
        try
        {
        	return getRemote().iniciarTramite(  );
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront cargarTramite( String idPersistencia)
			throws DelegateException
	{
        try
        {
        	return getRemote().cargarTramite(  idPersistencia );
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront pasoActual() throws DelegateException
	{
        try
        {
        	return getRemote().pasoActual();
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront siguientePaso() throws DelegateException
	{
        try
        {
        	return getRemote().siguientePaso();
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront anteriorPaso() throws DelegateException
	{
        try
        {
        	return getRemote().anteriorPaso();
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront irAPaso(int paso) throws DelegateException
	{
        try
        {
        	return getRemote().irAPaso( paso );
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront irAFormulario(String identificador, int instancia)
			throws DelegateException
	{
        try
        {
        	return getRemote().irAFormulario( identificador, instancia );
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront guardarFormulario(String identificador,
			int instancia, String datosAnteriores, String datosNuevos, boolean guardadoSinFinalizar)
			throws DelegateException
	{
        try
        {
        	return getRemote().guardarFormulario( identificador, instancia, datosAnteriores, datosNuevos, guardadoSinFinalizar );
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront borrarAnexo(String identificador, int instancia)
			throws DelegateException
	{
        try
        {
        	return getRemote().borrarAnexo( identificador, instancia );
        }
        catch (RemoteException e) {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront anexarDocumento(String identificador,int instancia,String descPersonalizada, FirmaIntf firma, boolean firmaDelegada) throws DelegateException
	{
        try
        {
        	return getRemote().anexarDocumento( identificador, instancia, descPersonalizada, firma, firmaDelegada );
        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront uploadAnexo(String identificador,int instancia,byte[] datosDocumento,String nomFichero,String extension,String descPersonalizada) throws DelegateException
	{
        try
        {
        	return getRemote().uploadAnexo(identificador, instancia, datosDocumento, nomFichero, extension, descPersonalizada);
        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront downloadAnexo(String identificador,int instancia) throws DelegateException
	{
        try
        {
        	return getRemote().downloadAnexo( identificador, instancia);
        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}



	public RespuestaFront irAPago(String identificador, int instancia, String urlRetorno, String urlMantenimientoSesion) throws DelegateException
	{
		try
        {
			return getRemote().irAPago( identificador, instancia, urlRetorno, urlMantenimientoSesion);
        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront confirmarPago(String identificador, int instancia) throws DelegateException
	{
		try
        {
			return getRemote().confirmarPago( identificador, instancia );
        }
		catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront anularPago(String identificador, int instancia) throws DelegateException
	{
		try
        {
			return getRemote().anularPago( identificador, instancia );
        }
		catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront borrarTramite() throws DelegateException
	{
        try
        {
        	return getRemote().borrarTramitePersistencia();
        }
        catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront registrarTramite(String asiento, FirmaIntf firma)
			throws DelegateException
	{
        try
        {
        	return getRemote().registrarTramite( asiento, firma );

        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront mostrarDocumento( String idDocumento, int instancia) throws DelegateException {
		try
        {
        	return getRemote().mostrarDocumento( idDocumento,  instancia);

        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}
	
	public DocumentoRDS recuperaInfoDocumento( String idDocumento, int instancia) throws DelegateException {
		try
        {
        	return getRemote().recuperaInfoDocumento(idDocumento, instancia) ;

        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront mostrarJustificante() throws DelegateException {
		try
        {
        	return getRemote().mostrarJustificante();

        } catch (RemoteException e)
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront firmarFormulario(String identificador,int instancia,FirmaIntf firma,boolean firmaDelegada) throws DelegateException
	{
		try
		{
			return getRemote().firmarFormulario( identificador, instancia, firma, firmaDelegada );
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public RespuestaFront remitirFlujoTramitacion() throws DelegateException{
		try
		{
			return getRemote().remitirFlujoTramite();
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}



	public RespuestaFront mostrarDocumentoConsulta(int numDoc) throws DelegateException
	{
		try
		{
			return getRemote().mostrarDocumentoConsulta( numDoc );
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public RespuestaFront irAFirmarFormulario(String identificador, int instancia) throws DelegateException {
		try
		{
			return getRemote().irAFirmarFormulario( identificador, instancia );
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public String obtenerUrlFin() throws DelegateException {
		try
		{
			return getRemote().obtenerUrlFin();
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public String obtenerIdPersistencia() throws DelegateException {
		try
		{
			return getRemote().obtenerIdPersistencia();
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public void habilitarNotificacion(boolean habilitarNotificacion, String emailAviso, String smsAviso)  throws DelegateException {
		try
		{
			 getRemote().habilitarNotificacion(habilitarNotificacion, emailAviso, smsAviso);
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public void finalizarSesionPago(String identificador, int instancia) throws DelegateException{
		try
		{
			getRemote().finalizarSesionPago(identificador, instancia);
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}

	}


	public RespuestaFront mostrarFormularioDebug(String idDocumento, int instancia) throws DelegateException {
		try
		{
			return getRemote().mostrarFormularioDebug(idDocumento, instancia);
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}

	}

	public RespuestaFront remitirDelegacionPresentacionTramite()  throws DelegateException {
		try
		{
			return getRemote().remitirDelegacionPresentacionTramite();
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}

	}

	public RespuestaFront remitirDelegacionFirmaDocumentos()  throws DelegateException{
		try
		{
			return getRemote().remitirDelegacionFirmaDocumentos();
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}

	}


	public void resetHabilitarNotificacion() throws DelegateException {
		try
		{
			getRemote().resetHabilitarNotificacion();
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public RespuestaFront finalizarTramite() throws DelegateException {
		try
		{
			return getRemote().finalizarTramite();
		}
		catch( RemoteException e )
		{
			throw new DelegateException( e );
		}
	}

	public boolean isDebugEnabled() throws DelegateException {
		try
		{
			return getRemote().isDebugEnabled();
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public boolean verificarMovil(String smsCodigo) throws DelegateException {
		try
		{
			return getRemote().verificarMovil(smsCodigo);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void resetCodigoSmsVerificarMovil() throws DelegateException {
		try
		{
			getRemote().resetCodigoSmsVerificarMovil();
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}

	
	public RespuestaFront obtenerInfoTramite() throws DelegateException {
		try
		{
			return getRemote().obtenerInfoTramite();
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
		
	}

	public void destroy()
	{
        try
        {
            getRemote().remove();
        } catch (RemoteException e) {
            ;
        } catch (RemoveException e) {
            ;
        }
    }

	 /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private Handle remoteHandle;

    private TramiteProcessor getRemote() throws RemoteException {
        return (TramiteProcessor) remoteHandle.getEJBObject();
    }

    protected InstanciaRemoteDelegate() {
    }









}
