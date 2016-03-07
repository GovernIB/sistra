package es.caib.pagosMOCK.persistence.ejb;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.pagosMOCK.model.SesionPagoMOCK;
import es.caib.pagosMOCK.model.TokenAccesoMOCK;
import es.caib.pagosMOCK.persistence.util.Configuracion;
import es.caib.pagosMOCK.persistence.util.DatabaseMOCK;
import es.caib.pagosMOCK.persistence.util.GeneradorId;
import es.caib.sistra.plugins.pagos.ConstantesPago;
import es.caib.sistra.plugins.pagos.DatosPago;
import es.caib.sistra.plugins.pagos.EstadoSesionPago;
import es.caib.sistra.plugins.pagos.SesionPago;
import es.caib.sistra.plugins.pagos.SesionSistra;

/**
 * SessionBean que implementa la interfaz del asistente
 * de pagos
 *
 * @ejb.bean
 *  name="pagos/persistence/PagosMOCKFacade"
 *  jndi-name="es.caib.pagosMOCK.persistence.PagosMOCKFacade"
 *  type="Stateless"
 *  view-type="remote" 
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * 
 *
 */
public class PagosFacadeEJB implements SessionBean {

	private static Log log = LogFactory.getLog(PagosFacadeEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.todos}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public void ejbCreate() throws CreateException {		
	}
	
	/**
	 * Inicia sesion de pago
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public SesionPago iniciarSesionPago(DatosPago datosPago, SesionSistra sesionSistra) {
		
		try{
			log.debug("Iniciar sesion pago");
			
			// Genera localizador
			String loca = GeneradorId.generarId();
					
			// Guarda datos
			SesionPagoMOCK sesionMOCK = new SesionPagoMOCK();
			sesionMOCK.setLocalizador(loca);
			sesionMOCK.setDatosPago(datosPago);
			sesionMOCK.setSesionSistra(sesionSistra);		
			EstadoSesionPago estado = new EstadoSesionPago();
			estado.setEstado(ConstantesPago.SESIONPAGO_EN_CURSO);
			sesionMOCK.setEstadoPago(estado);		
			
			DatabaseMOCK.guardarSesionPago(sesionMOCK);
			
			// Generamos token para redireccion al asistente de pagos
			TokenAccesoMOCK tokenMOCK = new TokenAccesoMOCK();
			tokenMOCK.setLocalizador(loca);
			tokenMOCK.setTiempoLimite(new Date(System.currentTimeMillis() + 60000L) ); // Valido durante 60 segs
			String token = "TKNAC-" +  GeneradorId.generarId();
			tokenMOCK.setToken(token);
			
			DatabaseMOCK.guardarTokenAcceso(tokenMOCK);
			
			// Devolvemos sesion de pago creada
			SesionPago sesionPago = new SesionPago();
			sesionPago.setLocalizador(loca);
			String contextoRaiz = Configuracion.getInstance().getProperty("sistra.contextoRaiz");
			sesionPago.setUrlSesionPago(contextoRaiz + "/pagosMockFront/init.do?token="+token);
			
			log.debug("Iniciada sesion pago: localizador " + loca + " / token acceso: " + token);
			
			return sesionPago;
		}catch (Exception ex){
			log.error("Exception iniciando pago",ex);
			throw new EJBException("Exception iniciando pago",ex);
		}
	}

	/**
	 * Reanuda sesion de pago existente
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
	 */
	public SesionPago reanudarSesionPago(String localizador, SesionSistra sesionSistra) {
		try{
			log.debug("Reanudar sesion pago: localizador " + localizador);
			
			// Obtenemos sesion de pago
			SesionPagoMOCK sesionMOCK = DatabaseMOCK.obtenerSesionPago(localizador);
			if (sesionMOCK == null) {
				return null;
			}
			if (sesionMOCK.getEstadoPago().getEstado() == ConstantesPago.SESIONPAGO_PAGO_CONFIRMADO){
				throw new Exception("La sesion de pago ya ha sido confirmada"); 
			}
			
			sesionMOCK.setSesionSistra(sesionSistra);
			DatabaseMOCK.guardarSesionPago(sesionMOCK);
			
			// Generamos token para redireccion al asistente de pagos
			TokenAccesoMOCK tokenMOCK = new TokenAccesoMOCK();
			tokenMOCK.setLocalizador(sesionMOCK.getLocalizador());
			tokenMOCK.setTiempoLimite(new Date(System.currentTimeMillis() + 60000L) ); // Valido durante 60 segs
			String token = "TKNAC-" +  GeneradorId.generarId();
			tokenMOCK.setToken(token);
			
			DatabaseMOCK.guardarTokenAcceso(tokenMOCK);
			
			// Devolvemos sesion de pago creada
			SesionPago sesionPago = new SesionPago();
			sesionPago.setLocalizador(sesionMOCK.getLocalizador());
			String contextoRaiz = Configuracion.getInstance().getProperty("sistra.contextoRaiz");
			sesionPago.setUrlSesionPago(contextoRaiz + "/pagosMockFront/init.do?token="+token);
			
			log.debug("Reanudada sesion pago: localizador " + sesionMOCK.getLocalizador() + " / token acceso: " + token);
			return sesionPago;
		}catch (Exception ex){
			log.error("Exception reanudando pago",ex);
			throw new EJBException("Exception reanudando pago",ex);
		}
	}

	/**
	 * Comprueba estado sesion de pago
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name = "${role.auto}"
	 */
	public EstadoSesionPago comprobarEstadoSesionPago(String localizador) {
		try{
			log.debug("Comprobar estado sesion pago: localizador " + localizador);
			EstadoSesionPago estado = new EstadoSesionPago();
			
			// Obtenemos sesion de pago
			SesionPagoMOCK sesionMOCK = DatabaseMOCK.obtenerSesionPago(localizador);
			if (sesionMOCK == null) {
				estado.setEstado(ConstantesPago.SESIONPAGO_NO_EXISTE_SESION);
				return estado;
			}
			
			log.debug("Estado=" + sesionMOCK.getEstadoPago().getEstado() );
			return sesionMOCK.getEstadoPago();
		}catch (Exception ex){
			log.error("Exception comprobando estado pago",ex);
			throw new EJBException("Exception comprobando estado pago",ex);
		}
	}

	/**
	 * Finaliza sesion de pago
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
	 */
	public void finalizarSesionPago(String localizador){
		try{
			log.debug("Finalizar sesion pago");
			DatabaseMOCK.borrarSesionPago(localizador);
			log.debug("Elimnado datos sesion pago");
		}catch (Exception ex){
			log.error("Exception finalizando sesion pago",ex);
			throw new EJBException("Exception comprobando estado pago",ex);
		}
	}
	
	/**
	 * Consulta el importe de una tasa
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
	 */
	public long consultarImporteTasa(String idTasa) {
		log.debug("consultarImporteTasa");
		return 1025;
	}


	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {		
	}

	public void ejbRemove() throws EJBException, RemoteException {	
	}

	public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}
}
