package es.caib.sistra.plugins.pagos;

import java.util.Map;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * 
 * Interfaz con la pasarela de pagos
 *
 */
public interface PluginPagosIntf  extends PluginSistraIntf {
	
	/**
	 * Inicia sesión de pagos contra la pasarela de pagos
	 * 
	 * @param datosPago Datos del pago
	 * @param sesionSistra Datos para el retorno a SISTRA. Al retornar de la pasarela de pagos
	 * se invocará a la función comprobarEstadoSesionPago para actualizar el asistente de tramitación.
	 * @return Datos para redirigir la sesión a la pasarela de pagos
	 * @throws Exception
	 */
	public SesionPago iniciarSesionPago(DatosPago datosPago, SesionSistra sesionSistra) throws Exception; 	
	
	/**
	 * Retoma sesión de pagos existente en la pasarela de pagos
	 * 
	 * @param localizador Localizador de la sesión de pagos
	 * @param sesionSistra Datos para el retorno a SISTRA. Al retornar de la pasarela de pagos
	 * se invocará a la función comprobarEstadoSesionPago para actualizar el asistente de tramitación.
	 * @return Datos para redirigir la sesión a la pasarela de pagos. Si no existe sesión con dicho localizador debe devolver null.
	 * @throws Exception
	 */
	public SesionPago reanudarSesionPago(String localizador, SesionSistra sesionSistra) throws Exception;
	
	/**
	 * Comprueba estado de una sesión de pagos
	 * 
	 * @param localizador Localizador de la sesión de pagos
	 * @return Estado sesión de pago
	 * @throws Exception
	 */
	public EstadoSesionPago comprobarEstadoSesionPago(String localizador) throws Exception;
	
	/**
	 * Indica al plugin de pagos que puede eliminar la información referente a la sesión de pagos
	 * <br/>
	 * El plugin entonces podrá eliminar los datos de la sesión:
	 * <ul>
	 * <li>Pago en curso: cancelará el proceso de pago</li>
	 * <li>Pago pendiente confirmación: no se permitirá finalizar la sesión</li>
	 * <li>Pago confirmado: dará por acabado el proceso de pago</li>
	 * 
	 * @param localizador Localizador de la sesión de pagos
	 * @throws Exception
	 */
	public void finalizarSesionPago(String localizador) throws Exception;
	
	/**
	 * Permite consultar el importe de una tasa
	 * @param idTasa identificador de la tasa
	 * @param paramTasa Map con parámetros para el cáclulo de la tasa
	 * @return Importe en cents de la tasa (Ej: 10,20€ = 1020 cents)
	 * @throws Exception
	 */
	public long consultarImporteTasa(String idTasa, Map paramTasa) throws Exception;
	
}
