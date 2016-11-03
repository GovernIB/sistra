package es.caib.pagosMOCK.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.pagosMOCK.persistence.intf.PagosMOCKFacade;
import es.caib.pagosMOCK.persistence.util.PagosMOCKFacadeUtil;
import es.caib.sistra.plugins.pagos.DatosPago;
import es.caib.sistra.plugins.pagos.EstadoSesionPago;
import es.caib.sistra.plugins.pagos.SesionPago;
import es.caib.sistra.plugins.pagos.SesionSistra;

/**
 * Business delegate para operar con asistente pagos
 */
public class PagosDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public SesionPago iniciarSesionPago(DatosPago arg0, SesionSistra arg1) throws DelegateException{
    	try {
            return getFacade().iniciarSesionPago(arg0,arg1);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public SesionPago reanudarSesionPago(String arg0, SesionSistra arg1) throws DelegateException{
    	try {
            return getFacade().reanudarSesionPago(arg0,arg1);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public EstadoSesionPago comprobarEstadoSesionPago(String arg0) throws DelegateException{
    	try {
            return getFacade().comprobarEstadoSesionPago(arg0);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public void finalizarSesionPago(String arg0) throws DelegateException{
    	try {
            getFacade().finalizarSesionPago(arg0);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public long consultarImporteTasa(String idTasa) throws DelegateException{
    	try {
            return getFacade().consultarImporteTasa(idTasa);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private PagosMOCKFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return PagosMOCKFacadeUtil.getHome().create();
    }

    protected PagosDelegate() throws DelegateException {       
    }

	               
}
