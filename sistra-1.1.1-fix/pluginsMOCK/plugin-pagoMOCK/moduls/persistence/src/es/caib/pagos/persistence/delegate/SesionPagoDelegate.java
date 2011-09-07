package es.caib.pagos.persistence.delegate;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

import es.caib.pagos.persistence.intf.SesionPagosFacadeLocal;
import es.caib.pagos.persistence.util.SesionPagosFacadeUtil;
import es.caib.sistra.plugins.pagos.DatosPago;

public class SesionPagoDelegate implements Delegate 
{

	public void create(String token)
			throws DelegateException
	{
		try
		{
			local = SesionPagosFacadeUtil.getLocalHome().create(token);
		} catch (CreateException e) {
	        throw new DelegateException(e);
	    } catch (EJBException e) {
	        throw new DelegateException(e);
	    } catch (NamingException e) {
	        throw new DelegateException(e);
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
	
	public DatosPago obtenerDatosPago() throws DelegateException
	{
		try 
        {
        	return local.obtenerDatosPago();
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public String obtenerUrlRetornoSistra() throws DelegateException
	{
		try 
        {
        	return local.obtenerUrlRetornoSistra();
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}
	
	public int realizarPago(String modoPago,String numeroTarjeta,String fechaCaducidad,String codigoVerificacion) throws DelegateException
	{
		try 
        {
        	return local.realizarPago(modoPago,numeroTarjeta, fechaCaducidad, codigoVerificacion);
        } catch (EJBException e) 
        {
            throw new DelegateException(e);
        }
	}

	
	
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private SesionPagosFacadeLocal local;

    protected SesionPagoDelegate() 
    {
    }
	
}
