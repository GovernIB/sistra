package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.DocumentoBandeja;
import es.caib.bantel.persistence.intf.DocumentoBandejaFacade;
import es.caib.bantel.persistence.util.DocumentoBandejaFacadeUtil;

/**
 * Business delegate para operar con DocumentoBandeja.
 */
public class DocumentoBandejaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarDocumentoBandeja(DocumentoBandeja tramite,Long idTramite) throws DelegateException {
        try {
            return getFacade().grabarDocumentoBandeja(tramite,idTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public DocumentoBandeja obtenerDocumentoBandeja(Long idDocumentoBandeja) throws DelegateException {
        try {
            return getFacade().obtenerDocumentoBandeja(idDocumentoBandeja);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void borrarDocumentoBandeja(Long id) throws DelegateException {
        try {
            getFacade().borrarDocumentoBandeja(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    
    private DocumentoBandejaFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return DocumentoBandejaFacadeUtil.getHome( ).create();
    }

    protected DocumentoBandejaDelegate() throws DelegateException {
     
    }                  
}

