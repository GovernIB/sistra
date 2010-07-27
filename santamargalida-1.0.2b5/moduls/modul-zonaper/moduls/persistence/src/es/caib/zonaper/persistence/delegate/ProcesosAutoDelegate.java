package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.persistence.intf.ProcesosAutoFacade;
import es.caib.zonaper.persistence.util.ProcesosAutoFacadeUtil;

public class ProcesosAutoDelegate implements StatelessDelegate
{
    /* ========================================================= */
    /* ======================== M�TODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public void actualizaEstadoExpediente(Long id) throws DelegateException {
		try
		{
			getFacade().actualizaEstadoExpediente(id );
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	public void avisoCreacionElementoExpediente(ElementoExpediente ele) throws DelegateException {
		try
		{
			getFacade().avisoCreacionElementoExpediente(ele);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
     private ProcesosAutoFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ProcesosAutoFacadeUtil.getHome( ).create();
    }
    
    protected ProcesosAutoDelegate()throws DelegateException 
    {       
    }

}
