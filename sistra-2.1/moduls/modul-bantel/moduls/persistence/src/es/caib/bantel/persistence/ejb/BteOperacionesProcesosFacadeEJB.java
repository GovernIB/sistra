package es.caib.bantel.persistence.ejb;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import es.caib.bantel.model.CriteriosBusquedaTramite;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.modelInterfaz.ExcepcionBTE;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;

/**
 * SessionBean que implementa operaciones de la gestión de proceso de avisos que se quieren ejecutar en una transacción nueva.
 * 
 * @ejb.bean
 *  name="bantel/persistence/BteOperacionesProcesosFacade"
 *  jndi-name="es.caib.bantel.persistence.BteOperacionesProcesosFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="RequiresNew"
 * 
 * 
 */
public abstract class BteOperacionesProcesosFacadeEJB implements SessionBean  {

	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {	
	}
	
    
	/**
     * Marca un procedimiento como avisado.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void marcarAvisoRealizado(String idProcedimiento,
			Date ahora) throws ExcepcionBTE {
    	try {
			DelegateUtil.getTramiteDelegate().avisoRealizado(idProcedimiento, ahora);
		} catch (DelegateException e) {
			throw new ExcepcionBTE("Excepcion marcando aviso realizado para procedimiento " + idProcedimiento, e);			
		}    	
    }
    
    /**
     * Marca entradas como caducadas.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void marcarEntradasCaducadas(String idProcedimiento,
			Date fechaLimite) throws ExcepcionBTE {
    	try {
			// Marcamos como procesadas con error las que han caducado
			CriteriosBusquedaTramite criterios = new CriteriosBusquedaTramite();
			criterios.setIdentificadorProcedimiento(idProcedimiento);
			criterios.setProcesada(ConstantesBTE.ENTRADA_NO_PROCESADA.charAt(0));
			criterios.setFechaInicioProcesamientoMaximo(fechaLimite);
			TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
			delegate.procesarEntradas(criterios,ConstantesBTE.ENTRADA_PROCESADA_ERROR, "Ha pasado el limite maximo establecido para avisarse al backoffice");
    	} catch (DelegateException e) {
			throw new ExcepcionBTE("Excepcion marcando entradas como caducadas para procedimiento " + idProcedimiento, e);			
		}  
    }	

    
    
}
