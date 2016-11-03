package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.admin.DatosAuditoriaCuaderno;
import es.caib.sistra.persistence.intf.AuditoriaCuadernoFacade;
import es.caib.sistra.persistence.util.AuditoriaCuadernoFacadeUtil;

public class AuditoriaCuadernoDelegate implements StatelessDelegate
{
	
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public boolean auditoriaRequerida( Long codigoCuadernoCarga ) throws DelegateException
	{
		 try 
		 {
			 return getFacade().auditoriaRequerida( codigoCuadernoCarga ); 
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	public DatosAuditoriaCuaderno auditoriaCuaderno( Long codigoCuadernoCarga ) throws DelegateException
	{
		 try 
		 {
			 return getFacade().auditoriaCuaderno( codigoCuadernoCarga );
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
	
	private AuditoriaCuadernoFacade getFacade() throws NamingException,RemoteException,CreateException {
		return AuditoriaCuadernoFacadeUtil.getHome().create();
	}
	
	protected AuditoriaCuadernoDelegate() throws DelegateException
	{
		
	}
	
}
