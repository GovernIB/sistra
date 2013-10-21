package es.caib.audita.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.intf.LoggerEventoFacade;
import es.caib.audita.persistence.util.LoggerEventoFacadeUtil;

public class LoggerEventoDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public Long logEvento( Evento eventoAuditado ) throws DelegateException
	{
		try
		{
			return getFacade().logEvento( eventoAuditado );
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	public Long logEventoTxNew( Evento eventoAuditado ) throws DelegateException
	{
		try
		{
			return getFacade().logEventoTxNew( eventoAuditado );
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

	private LoggerEventoFacade getFacade() throws NamingException,CreateException,RemoteException 
    {
    	return LoggerEventoFacadeUtil.getHome().create();
    }
    
    protected LoggerEventoDelegate()  throws DelegateException 
    {
        
    }
}
