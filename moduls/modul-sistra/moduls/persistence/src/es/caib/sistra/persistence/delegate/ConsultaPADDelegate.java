package es.caib.sistra.persistence.delegate;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.persistence.intf.ConsultaPADEJBLocal;
import es.caib.sistra.persistence.util.ConsultaPADEJBUtil;
import es.caib.zonaper.modelInterfaz.PersonaPAD;

public class ConsultaPADDelegate implements StatelessDelegate
{
	
	public PersonaPAD obtenerDatosPADporNif(String nif) throws DelegateException
	{
		try
		{			
			return getFacade().obtenerDatosPADporNif(nif);					
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	 
	
	public PersonaPAD obtenerDatosPADporUsuarioSeycon(String usuarioSeycon) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDatosPADporUsuarioSeycon(usuarioSeycon);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 
	 }
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    protected ConsultaPADDelegate() 
    {
    }
    
    protected ConsultaPADEJBLocal getFacade() throws CreateException, NamingException {
    	 return ConsultaPADEJBUtil.getLocalHome().create();
    }
    

	
}
