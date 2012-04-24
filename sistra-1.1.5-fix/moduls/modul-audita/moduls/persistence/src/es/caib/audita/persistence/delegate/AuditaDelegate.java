package es.caib.audita.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.intf.AuditaFacade;
import es.caib.audita.persistence.util.AuditaFacadeUtil;

public class AuditaDelegate implements StatelessDelegate
{
	
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public Map obtenerDescripcionEventos( String idioma ) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerDescripcionEventos(idioma);
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	 public List obtenerAuditoria( Date fechaIni,Date fechaFin,char nivelAutenticacion,String autenticacion ) throws DelegateException
	{
		try 
		{
			return getFacade().obtenerAuditoria( fechaIni,fechaFin,nivelAutenticacion,autenticacion );
		}
		catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	 
	 public Long logEvento( Evento eventoAuditado )throws DelegateException
		{
			try 
			{
				return getFacade().logEvento( eventoAuditado );
			}
			catch (Exception e) {
		        throw new DelegateException(e);
		    }
		}
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    
    private AuditaFacade getFacade() throws NamingException,CreateException,RemoteException 
    {
    	return AuditaFacadeUtil.getHome().create();
    }
    
    protected AuditaDelegate()  throws DelegateException 
    {
       
    }

}
