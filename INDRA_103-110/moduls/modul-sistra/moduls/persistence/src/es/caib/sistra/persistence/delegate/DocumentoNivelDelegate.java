package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.Handle;
import javax.naming.NamingException;

import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.persistence.intf.DocumentoNivelFacade;
import es.caib.sistra.persistence.util.DocumentoNivelFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class DocumentoNivelDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarDocumentoNivel(DocumentoNivel documentoNivel,Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().grabarDocumentoNivel(documentoNivel,idTramiteVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public DocumentoNivel obtenerDocumentoNivel(Long idDocumentoNivel) throws DelegateException {
        try {
            return getFacade().obtenerDocumentoNivel(idDocumentoNivel);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
    //public Map listarDocumentoNiveles(Long idDocumento) throws DelegateException {
    public Set listarDocumentoNiveles(Long idDocumento) throws DelegateException {
        try {
            return getFacade().listarNivelesDocumento(idDocumento);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarDocumentoNivel(Long id) throws DelegateException {
        try {
            getFacade().borrarDocumentoNivel(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private Handle facadeHandle;    
    
    private DocumentoNivelFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return DocumentoNivelFacadeUtil.getHome( ).create();
    }

    protected DocumentoNivelDelegate() throws DelegateException {       
    }                  
}

