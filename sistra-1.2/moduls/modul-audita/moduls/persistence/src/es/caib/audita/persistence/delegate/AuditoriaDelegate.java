package es.caib.audita.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.audita.model.CuadroMandoDetalle;
import es.caib.audita.model.CuadroMandoInicio;
import es.caib.audita.model.CuadroMandoTablaCruzada;
import es.caib.audita.persistence.intf.AuditoriaFacade;
import es.caib.audita.persistence.util.AuditoriaFacadeUtil;
import es.indra.util.graficos.generadorGraficos.DatosGrafico;

public class AuditoriaDelegate implements StatelessDelegate
{
	
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public DatosGrafico obtenerDatosGrafico(String tipoEvento,Date fecha,String modo) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerDatosGrafico(tipoEvento,fecha,modo);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public List obtenerListaEventos(String idioma) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerListaEventos(idioma);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public List obtenerListaEventosGrafico(String idioma) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerListaEventosGrafico(idioma);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public List obtenerCuadroMandoIntervaloTemporal(String idioma, int anyoInicio, int anyoFinal ) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerCuadroMandoIntervaloTemporal( idioma, anyoInicio, anyoFinal );
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public List obtenerCuadroMandoIntervaloTemporal( String idioma, int anyoInicio, int mesInicio, int anyoFinal, int mesFinal ) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerCuadroMandoIntervaloTemporal( idioma, anyoInicio,mesInicio, anyoFinal, mesFinal );
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public List obtenerCuadroMandoIntervaloTemporal( String idioma, Date fechaInicio, Date fechaFinal )  throws DelegateException
	{
		try 
		{
			return getFacade().obtenerCuadroMandoIntervaloTemporal( idioma, fechaInicio, fechaFinal );
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public CuadroMandoDetalle obtenerCuadroMandoDetalle(String idioma, String tipoEvento,String modo,Date fechaInicio, Date fechaFinal )  throws DelegateException
	{
		try 
		{
			return getFacade().obtenerCuadroMandoDetalle(  idioma, tipoEvento, modo, fechaInicio,  fechaFinal);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public CuadroMandoTablaCruzada obtenerCuadroMandoTablaCruzada(String idioma, String tipoEvento,String modo,Date fecha)  throws DelegateException
	{
		try 
		{
			return getFacade().obtenerCuadroMandoTablaCruzada(idioma, tipoEvento, modo, fecha);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}

	public List obtenerListaEventosDetalle(String idioma) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerListaEventosDetalle(idioma);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}

	public CuadroMandoInicio obtenerCuadroMandoInicio(String idioma)  throws DelegateException
	{
		try 
		{
			return getFacade().obtenerCuadroMandoInicio(idioma);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	

	public void generaCuadroMandoInicio()  throws DelegateException
	{
		try 
		{
			 getFacade().generaCuadroMandoInicio();
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
		


	public byte[] obtenerGraficoFichero(String fichero)  throws DelegateException
	{
		try 
		{
			return getFacade().obtenerGraficoFichero(fichero);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}

	
	public List obtenerAuditoria(Date fechaIni,Date fechaFin,char nivelAutenticacion,String autenticacion)throws DelegateException
	{
		try 
		{
			return getFacade().obtenerAuditoria(fechaIni,fechaFin,nivelAutenticacion,autenticacion);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}


	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    
    private AuditoriaFacade getFacade() throws NamingException,CreateException,RemoteException 
    {
    	return AuditoriaFacadeUtil.getHome().create();
    }
    
    protected AuditoriaDelegate()  throws DelegateException 
    {
       
    }

}
