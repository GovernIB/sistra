package es.caib.audita.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.audita.persistence.intf.CuadroMandoInicioFacade;
import es.caib.audita.persistence.util.CuadroMandoInicioFacadeUtil;

public class CuadroMandoInicioDelegate implements StatelessDelegate
{
	
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public void generaEstadisticasMasAccedidos() throws DelegateException
	{
		try 
		{
			getFacade().generaEstadisticasMasAccedidos();
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public void generaEstadisticasMasTramitados() throws DelegateException
	{
		try 
		{
			getFacade().generaEstadisticasMasTramitados();
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public void generaEstadisticasPortal(String desde, String hasta) throws DelegateException
	{
		try 
		{
			getFacade().generaEstadisticasPortal( desde, hasta);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public void generaEstadisticasTramitacion(String desde, String hasta) throws DelegateException
	{
		try 
		{
			getFacade().generaEstadisticasTramitacion( desde,  hasta);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public void generaEstadisticasServicios() throws DelegateException
	{
		try 
		{
			getFacade().generaEstadisticasServicios();
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	public void generaEstadisticasUltimosAlta() throws DelegateException
	{
		try 
		{
			getFacade().generaEstadisticasUltimosAlta();
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    
    private CuadroMandoInicioFacade getFacade() throws NamingException,CreateException,RemoteException 
    {
    	return CuadroMandoInicioFacadeUtil.getHome().create();
    }
    
    protected CuadroMandoInicioDelegate()  throws DelegateException 
    {
       
    }

}
