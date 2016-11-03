package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.IndiceElemento;
import es.caib.zonaper.persistence.intf.IndiceElementoFacade;
import es.caib.zonaper.persistence.util.IndiceElementoFacadeUtil;

public class IndiceElementoDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	 public IndiceElemento obtenerIndiceElemento(Long id) throws DelegateException
	{
		try
		{
			return getFacade().obtenerIndiceElemento( id );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	 public Long grabarIndiceElemento(IndiceElemento indiceElemento) throws DelegateException
	{
		try
		{
			return getFacade().grabarIndiceElemento( indiceElemento );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
		
	
	
	 public void borrarIndiceElemento(Long id)  throws DelegateException
	{
		try
		{
			getFacade().borrarIndiceElemento( id );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	 public List buscarIndice(String nif, String palabraClave) throws DelegateException
	{
		try
		{
			return getFacade().buscarIndice(nif, palabraClave);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
	 
	 public void borrarIndicesElemento(String tipoElemento,
				Long codigoElemento) throws DelegateException
				{
			try
			{
				getFacade().borrarIndicesElemento(tipoElemento, codigoElemento);
			}
			catch (Exception e) 
			{
	            throw new DelegateException(e);
	        }
		}	
	 
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private IndiceElementoFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return IndiceElementoFacadeUtil.getHome( ).create();
    }
    
    protected IndiceElementoDelegate() throws DelegateException {       
    }      
}
