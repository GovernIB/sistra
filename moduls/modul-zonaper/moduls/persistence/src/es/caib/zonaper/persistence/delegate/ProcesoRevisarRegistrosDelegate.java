package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.LogRegistro;
import es.caib.zonaper.model.RegistroExternoPreparado;
import es.caib.zonaper.persistence.intf.ProcesoRevisarRegistrosFacade;
import es.caib.zonaper.persistence.util.ProcesoRevisarRegistrosFacadeUtil;

public class ProcesoRevisarRegistrosDelegate implements StatelessDelegate
{
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public void revisarRegistro( LogRegistro logReg)  throws DelegateException {
		try
		{
			getFacade().revisarRegistro( logReg ) ;
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	public void revisarRegistroExternoPreparado( RegistroExternoPreparado r) throws DelegateException {
		try
		{
			getFacade().revisarRegistroExternoPreparado( r ) ;
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
     private ProcesoRevisarRegistrosFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ProcesoRevisarRegistrosFacadeUtil.getHome( ).create();
    }
    
    protected ProcesoRevisarRegistrosDelegate()throws DelegateException 
    {       
    }

}
