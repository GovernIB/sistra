package es.caib.pagos.persistence.ejb;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.pagos.model.SesionPagoMOCK;
import es.caib.pagos.model.TokenAccesoMOCK;
import es.caib.pagos.persistence.util.DatabaseMOCK;
import es.caib.sistra.plugins.pagos.ConstantesPago;
import es.caib.sistra.plugins.pagos.DatosPago;

/**
 * SessionBean que implementa la logica de una sesion de pagos
 *
 * @ejb.bean
 *  name="pagos/persistence/SesionPagosFacade"
 *  jndi-name="es.caib.pagos.persistence.PagosFacade"
 *  type="Stateful"
 *  view-type="local" 
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * 
 *
 */
public class SesionPagosFacadeEJB implements SessionBean {

	private static Log log = LogFactory.getLog(PagosFacadeEJB.class);
	
	private SesionPagoMOCK sesionPago;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.user}"
     */
	public void ejbCreate(String token) throws CreateException {
		try{
			TokenAccesoMOCK tokenAcceso = DatabaseMOCK.obtenerTokenAcceso(token);
			if (tokenAcceso == null) throw new CreateException("No existe token de acceso");
			if (tokenAcceso.getTiempoLimite().before(new Date())) {			
				throw new CreateException("Se ha sobrepasado el tiempo limite para el token de acceso");
			}
			sesionPago = DatabaseMOCK.obtenerSesionPago(tokenAcceso.getLocalizador());
			if (sesionPago == null){
				throw new CreateException("No se encuentra la sesion de pago asociada al token de acceso");
			}
		}finally{
			// Eliminamos token de acceso
			DatabaseMOCK.borrarTokenAcceso(token);
		}
	}
	
	
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {		
	}

	public void ejbRemove() throws EJBException, RemoteException {
	}

	public void ejbActivate() throws EJBException, RemoteException {					
	}

	public void ejbPassivate() throws EJBException, RemoteException {	
	}
	
	/**
	 * Realiza el pago telematico y devuelve la url de retorno a sistra
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
	public int realizarPago(String modoPago,String numeroTarjeta,String fechaCaducidad,String codigoVerificacion) {
		try{
			log.debug("Realizar pago");
			if(modoPago != null){
				Date fc = new Date();
				if (modoPago.equals("T")){
					sesionPago.getEstadoPago().setTipo(ConstantesPago.TIPOPAGO_TELEMATICO);
				}else{
					sesionPago.getEstadoPago().setTipo(ConstantesPago.TIPOPAGO_PRESENCIAL);
				}
				sesionPago.getEstadoPago().setEstado(ConstantesPago.SESIONPAGO_PAGO_CONFIRMADO);
				sesionPago.getEstadoPago().setFechaPago(new Date());
				sesionPago.getEstadoPago().setIdentificadorPago("NRC"+ fc.getTime());
			
				DatabaseMOCK.guardarSesionPago(sesionPago);
			
				// Devuelve codigo resultado 0 -> ok
				return 0;
			}else{
				return 1;
			}
		}catch (Exception ex){
			log.error("Exception finalizando sesion pago",ex);
			throw new EJBException("Exception comprobando estado pago",ex);
		}			
	}
	
	/**
	 * Obtiene datos del pago
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
	public DatosPago obtenerDatosPago() {
		try{
			log.debug("Obtener datos pago");
			return sesionPago.getDatosPago();	
		}catch (Exception ex){
			log.error("Exception finalizando sesion pago",ex);
			throw new EJBException("Exception comprobando estado pago",ex);
		}	
	}

	/**
	 * Obtiene url de retorno a sistra
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
	 */
	public String obtenerUrlRetornoSistra(){
		try{
			log.debug("Obtener url retorno sistra");
			return sesionPago.getSesionSistra().getUrlRetornoSistra();
		}catch (Exception ex){
			log.error("Exception finalizando sesion pago",ex);
			throw new EJBException("Exception comprobando estado pago",ex);
		}
	}
		
}
