package es.caib.sistra.persistence.delegate;

import java.util.Locale;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

import es.caib.sistra.model.InstanciaBean;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.intf.TramiteProcessorLocal;
import es.caib.sistra.persistence.util.TramiteProcessorUtil;
import es.caib.sistra.plugins.firma.FirmaIntf;

public class InstanciaLocalDelegate implements InstanciaDelegate
{

	public void create(String tramite, int version, char nivel, Locale idioma,Map parametrosInicio, String perfilAcceso, String nifEntidad)
			throws DelegateException
	{
		try
		{
			local = TramiteProcessorUtil.getLocalHome().create( tramite, version, nivel, idioma, parametrosInicio,  perfilAcceso,  nifEntidad );
		} catch (CreateException e) {
	        throw new DelegateException(e);
	    } catch (EJBException e) {
	        throw new DelegateException(e);
	    } catch (NamingException e) {
	        throw new DelegateException(e);
	    }

	}

	public InstanciaBean obtenerInstanciaBean() throws DelegateException
	{
		try 
        {
        	return local.obtenerInstanciaBean();
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront informacionInicial() throws DelegateException
	{
        try 
        {
        	return local.informacionInicial();
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public RespuestaFront obtenerTramitesPersistencia(String tramite,int version) throws DelegateException
	{
        try 
        {
        	return local.obtenerTramitesPersistencia(tramite,version);
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
		
	public RespuestaFront iniciarTramite() throws DelegateException
	{
        try 
        {
        	return local.iniciarTramite();
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront cargarTramite(String idPersistencia)
			throws DelegateException
	{
        try 
        {
        	return local.cargarTramite(idPersistencia );
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public RespuestaFront pasoActual() throws DelegateException
	{
        try 
        {
        	return local.pasoActual();
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront siguientePaso() throws DelegateException
	{
        try 
        {
        	return local.siguientePaso();
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront anteriorPaso() throws DelegateException
	{
        try 
        {
        	return local.anteriorPaso();
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront irAPaso(int paso) throws DelegateException
	{
        try 
        {
        	return local.irAPaso( paso );
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront irAFormulario(String identificador, int instancia)
			throws DelegateException
	{
        try 
        {
        	return local.irAFormulario( identificador, instancia );
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront guardarFormulario(String identificador,
			int instancia, String datosAnteriores, String datosNuevos)
			throws DelegateException
	{
        try 
        {
        	return local.guardarFormulario( identificador, instancia, datosAnteriores, datosNuevos );
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront borrarAnexo(String identificador, int instancia)
			throws DelegateException
	{
        try 
        {
        	return local.borrarAnexo( identificador, instancia );
        } 
        catch (EJBException e) {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront anexarDocumento(String identificador, int instancia,
			byte[] datosDocumento, String nomFichero, String extension,
			String descPersonalizada,FirmaIntf firma, boolean firmaDelegada) throws DelegateException
	{
        try 
        {
        	return local.anexarDocumento( identificador, instancia, datosDocumento, nomFichero, extension, descPersonalizada, firma, firmaDelegada );
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	

	public RespuestaFront irAPago(String identificador, int instancia, String urlRetorno, String urlMantenimientoSesion) throws DelegateException
	{
		try 
        {
			return local.irAPago( identificador, instancia, urlRetorno, urlMantenimientoSesion );
        } 
		catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	public RespuestaFront confirmarPago(String identificador, int instancia) throws DelegateException
	{
		try 
        {
			return local.confirmarPago( identificador, instancia);
        }
		catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public RespuestaFront anularPago(String identificador, int instancia) throws DelegateException
	{
		try 
        {
			return local.anularPago( identificador, instancia);
        }
		catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public RespuestaFront borrarTramite() throws DelegateException
	{
        try 
        {
        	return local.borrarTramitePersistencia();
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public RespuestaFront borrarTramite( String idPersistencia ) throws DelegateException
	{
        try 
        {
        	return local.borrarTramitePersistencia( idPersistencia );
        } 
        catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public RespuestaFront registrarTramite(String asiento, FirmaIntf firma)
			throws DelegateException
	{
        try 
        {
        	return local.registrarTramite( asiento, firma );
        	
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public RespuestaFront mostrarDocumento( String idDocumento, int instancia) throws DelegateException {
		   try 
	        {
	        	return local.mostrarDocumento( idDocumento, instancia) ;
	        	
	        } catch (EJBException e) 
	        {
	            throw new DelegateException(e);
	        }
	}

	public RespuestaFront mostrarJustificante() throws DelegateException {
		   try 
	        {
	        	return local.mostrarJustificante();
	        	
	        } catch (EJBException e) 
	        {
	            throw new DelegateException(e);
	        }
	}
	
	public RespuestaFront firmarFormulario(String identificador,int instancia,FirmaIntf firma,boolean firmaDelegada) throws DelegateException 
	{
		try
		{
			return local.firmarFormulario( identificador, instancia, firma, firmaDelegada );
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	public RespuestaFront remitirFlujoTramitacion() throws DelegateException{
		try
		{
			return local.remitirFlujoTramite();
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	
	
	public RespuestaFront mostrarDocumentoConsulta(int numDoc) throws DelegateException
	{
		try
		{
			return local.mostrarDocumentoConsulta( numDoc );
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	public RespuestaFront irAFirmarFormulario(String identificador, int instancia) throws DelegateException {
		try
		{
			return local.irAFirmarFormulario( identificador, instancia);
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	public String obtenerUrlFin() throws DelegateException {
		try
		{
			return local.obtenerUrlFin();
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}

	public String obtenerIdPersistencia() throws DelegateException  {
		try
		{
			return local.obtenerIdPersistencia();
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}

	public void habilitarNotificacion(boolean habilitarNotificacion, String emailAviso, String smsAviso) throws DelegateException {
		try
		{
			local.habilitarNotificacion(habilitarNotificacion, emailAviso, smsAviso);
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	
	public void finalizarSesionPago(String identificador, int instancia) throws DelegateException  {
		try
		{
			local.finalizarSesionPago(identificador, instancia);
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
		
	}
	
	public RespuestaFront mostrarFormularioDebug(String idDocumento, int instancia) throws DelegateException {
		try
		{
			return local.mostrarFormularioDebug(idDocumento, instancia);
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	public RespuestaFront remitirDelegacionPresentacionTramite()  throws DelegateException{
		try
		{
			return local.remitirDelegacionPresentacionTramite();
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	public RespuestaFront remitirDelegacionFirmaDocumentos()  throws DelegateException{
		try
		{
			return local.remitirDelegacionFirmaDocumentos();
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	

	public void resetHabilitarNotificacion() throws DelegateException {
		try
		{
			local.resetHabilitarNotificacion();
		}
		catch( EJBException e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void destroy()
	{
		try {
            if (local != null) {
                local.remove();
            }
        } catch (EJBException e) {
            ;
        } catch (RemoveException e) {
            ;
        }

	}
	
	
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private TramiteProcessorLocal local;

    protected InstanciaLocalDelegate() 
    {
    }


	
	
	
}
