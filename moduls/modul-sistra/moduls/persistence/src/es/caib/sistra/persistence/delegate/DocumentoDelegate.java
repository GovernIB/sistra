package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.Documento;
import es.caib.sistra.persistence.intf.DocumentoFacade;
import es.caib.sistra.persistence.util.DocumentoFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class DocumentoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarDocumento(Documento documento,Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().grabarDocumento(documento,idTramiteVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Documento obtenerDocumento(Long idDocumento) throws DelegateException {
        try {
            return getFacade().obtenerDocumento(idDocumento);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Set listarDocumentos(Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().listarDocumentos(idTramiteVersion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarDocumento(Long id) throws DelegateException {
        try {
            getFacade().borrarDocumento(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private DocumentoFacade getFacade() throws NamingException,RemoteException,CreateException {     	
    	return DocumentoFacadeUtil.getHome().create();    	    	   
    }

    protected DocumentoDelegate() throws DelegateException {    
    }
    
}

