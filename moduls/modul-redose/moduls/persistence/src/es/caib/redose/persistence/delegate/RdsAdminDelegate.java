package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.intf.RdsAdminFacade;
import es.caib.redose.persistence.util.RdsAdminFacadeUtil;

/**
 * Business delegate para operar con RDS.
 */
public class RdsAdminDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	 public List listarDocumentosSinUsos() throws DelegateException{
    	try {
            return getFacade().listarDocumentosSinUsos();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	    
    /**
     * Eliminar documento del RDS
     */
    public void eliminarDocumento(ReferenciaRDS refRds)  throws DelegateException{	
    	 try {
             getFacade().eliminarDocumento(refRds);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
    }
   
    public List listarDocumentosPendientesConsolidar()  throws DelegateException{
    	try {
            return getFacade().listarDocumentosPendientesConsolidar();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarDocumentosBorrados(Date fecha) throws DelegateException{
    	try {
            return getFacade().listarDocumentosBorrados(fecha);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void eliminarDocumentoDefinitivamente(ReferenciaRDS refRds) throws DelegateException{    
    	try {
            getFacade().eliminarDocumentoDefinitivamente(refRds);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarVersionesCustodiaParaBorrar() throws DelegateException{    
    	try {
            return getFacade().listarVersionesCustodiaParaBorrar();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void eliminaVersionDocumentoCustodia(String codigoVersion)  throws DelegateException{    
    	try {
            getFacade().eliminaVersionDocumentoCustodia(codigoVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarFicherosExternosParaBorrar() throws DelegateException{    
    	try {
            return getFacade().listarFicherosExternosParaBorrar();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void eliminaFicheroExterno(String referenciaFic) throws DelegateException{    
    	try {
            getFacade().eliminaFicheroExterno(referenciaFic);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void consolidarDocumentoCustodia(ReferenciaRDS refRDS) throws DelegateException{    
    	try {
            getFacade().consolidarDocumentoCustodia(refRDS);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private RdsAdminFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return RdsAdminFacadeUtil.getHome().create();
    }

    protected RdsAdminDelegate() throws DelegateException {       
    }                  
}
