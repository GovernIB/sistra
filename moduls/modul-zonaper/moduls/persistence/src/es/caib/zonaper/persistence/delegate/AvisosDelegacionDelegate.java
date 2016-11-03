package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.persistence.intf.AvisosDelegacionFacade;
import es.caib.zonaper.persistence.util.AvisosDelegacionFacadeUtil;

public class AvisosDelegacionDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public void avisarPendientePresentacionTramite(String idPersistencia) throws DelegateException
	{
		try
		{
			getFacade().avisarPendientePresentacionTramite(idPersistencia);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public void avisarPendienteFirmarDocumentos(String idPersistencia)throws DelegateException	
	{
		try
		{
			getFacade().avisarPendienteFirmarDocumentos( idPersistencia);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void avisarRechazoDocumento(String idPersistencia,Long codigo)throws DelegateException	
	{
		try
		{
			getFacade().avisarRechazoDocumento( idPersistencia, codigo);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void avisarContinuarTramite(String idPersistencia) throws DelegateException
	{
		try{    		 
    		getFacade().avisarContinuarTramite(idPersistencia);    	
    	}catch (Exception ex){
    		throw new DelegateException(ex);
    	}          
	}

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private AvisosDelegacionFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return AvisosDelegacionFacadeUtil.getHome( ).create();
    }
    
    protected AvisosDelegacionDelegate() throws DelegateException {       
    }      
}
