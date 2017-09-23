package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.Dominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.intf.DominioSqlFacade;
import es.caib.sistra.persistence.util.DominioSqlFacadeUtil;

/**
 * Business delegate para operar con DominioSql.
 */
public class DominioSqlDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	public ValoresDominio resuelveDominioSQL(Dominio dominio,List parametros, String url, boolean pDebugEnabled) throws DelegateException {
        try {
            return getFacade().resuelveDominioSQL(dominio, parametros, url, pDebugEnabled);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private DominioSqlFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return DominioSqlFacadeUtil.getHome().create();
    }

    protected DominioSqlDelegate() throws DelegateException {        
    }                  
}

