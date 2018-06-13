package es.caib.bantel.persistence.ejb;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import es.caib.bantel.model.CriteriosBusquedaTramite;
import es.caib.bantel.model.DetalleEntradasProcedimiento;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.modelInterfaz.ExcepcionBTE;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;
import es.caib.util.DataUtil;

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
    public void marcarEntradasCaducadas(String identificadorProcedimiento,
			Date fechaLimite) throws ExcepcionBTE {
    	try {
			// Marcamos como procesadas con error las que han caducado
			CriteriosBusquedaTramite criterios = new CriteriosBusquedaTramite();
			criterios.setIdentificadorProcedimiento(identificadorProcedimiento);
			criterios.setProcesada(ConstantesBTE.ENTRADA_NO_PROCESADA.charAt(0));
			criterios.setFechaInicioProcesamientoMaximo(fechaLimite);
			TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
			delegate.procesarEntradas(criterios,ConstantesBTE.ENTRADA_PROCESADA_ERROR, "Ha pasado el limite maximo establecido para avisarse al backoffice");
    	} catch (DelegateException e) {
			throw new ExcepcionBTE("Excepcion marcando entradas como caducadas para procedimiento " + identificadorProcedimiento, e);			
		}  
    }	
    
    
    /**
     * Obtener detalle entradas procedimiento.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public DetalleEntradasProcedimiento obtenerDetalleEntradasProcedimiento(Procedimiento procedimiento,
			Date desde, Date hasta) throws ExcepcionBTE {
    	try {
    		TramiteBandejaDelegate tbd = (TramiteBandejaDelegate) DelegateUtil.getTramiteBandejaDelegate();
	    	
	    	DetalleEntradasProcedimiento de = new DetalleEntradasProcedimiento();
	    	
	    	de.setProcesadasOk(tbd.obtenerTotalEntradasProcedimiento(procedimiento.getIdentificador(),ConstantesBTE.ENTRADA_PROCESADA,desde,hasta));
	    	de.setProcesadasError(tbd.obtenerTotalEntradasProcedimiento(procedimiento.getIdentificador(),ConstantesBTE.ENTRADA_PROCESADA_ERROR,desde,hasta));
	    	de.setNoProcesadas(tbd.obtenerTotalEntradasProcedimiento(procedimiento.getIdentificador(),ConstantesBTE.ENTRADA_NO_PROCESADA,desde,hasta));
	    	
	    	if (procedimiento.getPeriodica() == 'S'){
	    		de.setProcesadasErrorPendientes(tbd.obtenerTotalEntradasProcedimiento(procedimiento.getIdentificador(),ConstantesBTE.ENTRADA_PROCESADA_ERROR,null,hasta));
	    		de.setNoProcesadasPendientes(tbd.obtenerTotalEntradasProcedimiento(procedimiento.getIdentificador(),ConstantesBTE.ENTRADA_NO_PROCESADA,null,desde));
	    	}
	    	
	    	return de;
    	} catch (DelegateException e) {
			throw new ExcepcionBTE("Excepcion obteniendo detalle entradas procedimiento", e);			
		}  
    	 
    }  

    /**
     * Realiza envios a gestores y los marca como avisados.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void realizarEnviosGestores(String tipoAviso, MensajeEnvio enviosEmail,
			 Date ahora) throws ExcepcionBTE {
    	try {
			// Realizamos envios a gestores
    		if (enviosEmail.getEmails() != null && enviosEmail.getEmails().size() > 0){	    		
				enviosEmail.setNombre("Avisos a gestores");
				enviosEmail.setCuentaEmisora(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisosGestores.cuentaEnvio"));
				enviosEmail.setFechaCaducidad(DataUtil.sumarHoras(new Date(), 24)); // Damos 1 día para intentar enviar
				enviosEmail.setInmediato(true);
				DelegateMobTraTelUtil.getMobTraTelDelegate().envioMensaje(enviosEmail);
    		}
			
			// Marcamos aviso como realizado
			DelegateUtil.getAvisosBandejaDelegate().actualizarAviso(tipoAviso, ahora);
			
    	} catch (Exception e) {
			throw new ExcepcionBTE("Excepcion realizando envios a gestores", e);			
		} 
	}
    
    
    
}
