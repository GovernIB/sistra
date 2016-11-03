package es.caib.sistra.model;

import java.util.HashMap;

/**
 * Parámetros para el mensaje
 * 
 */
public class ParametrosMensaje {
	
	/**
	 * Accion con la que enlaza
	 */
	private String action=null;
	/**
	 * Target para indicar si se abre en ventana nueva 
	 */
	private String target=null;
	/**
	 * Parametros para la acción
	 */
	private HashMap parametros = new HashMap();
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public HashMap getParametros() {
		return parametros;
	}
	public void setParametros(HashMap parametros) {
		this.parametros = parametros;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
}
