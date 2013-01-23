package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.persistence.intf.ProcesosAutoFacade;
import es.caib.zonaper.persistence.util.ProcesosAutoFacadeUtil;

public class ProcesosAutoDelegate implements StatelessDelegate
{
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public void actualizaEstadoExpediente(Long id) throws DelegateException {
		try
		{
			getFacade().actualizaEstadoExpediente(id );
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	public void actualizaEstadoExpedienteDelElementoExpediente(String tipoElementoExpediente,Long codigoElementoExpediente) throws DelegateException {
		try
		{
			getFacade().actualizaEstadoExpedienteDelElementoExpediente(tipoElementoExpediente,codigoElementoExpediente );
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	public String avisoCreacionElementoExpediente(ElementoExpediente ele) throws DelegateException {
		try
		{
			return getFacade().avisoCreacionElementoExpediente(ele);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}

	public void revisarRegistrosEfectuados() throws DelegateException {
		try{
			getFacade().revisarRegistrosEfectuados();
		}catch (Exception e) {
			throw new DelegateException(e);
        }
	}
	
	public void controlEntregaNotificaciones() throws DelegateException {
		try{
			getFacade().controlEntregaNotificaciones();
		}catch (Exception e) {
			throw new DelegateException(e);
        }
	}
	
	public void actualizarExpedienteTramiteSubsanacion(Long codigoEntrada, String tipoEntrada) throws DelegateException {
		try{
			getFacade().actualizarExpedienteTramiteSubsanacion(codigoEntrada, tipoEntrada);
		}catch (Exception e) {
			throw new DelegateException(e);
        }
	}
	
	public void procesaTramitesCaducados() throws DelegateException {
		try{
			getFacade().procesaTramitesCaducados();
		}catch (Exception e) {
			throw new DelegateException(e);
        }
	}
	
	public void procesaEliminarTramitesBackup() throws DelegateException {
		try{
			getFacade().procesaEliminarTramitesBackup();
		}catch (Exception e) {
			throw new DelegateException(e);
        }
	}
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
     private ProcesosAutoFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ProcesosAutoFacadeUtil.getHome( ).create();
    }
    
    protected ProcesosAutoDelegate()throws DelegateException 
    {       
    }

	

}
