package es.caib.regtel.model;

import java.util.HashMap;
import java.util.Map;

import es.caib.redose.modelInterfaz.ReferenciaRDS;

/**
 * Referencia del asiento registral en el RDS con todos sus anexos
 * 
 */
public class ReferenciaRDSAsientoRegistral {

	/**
	 * Referencia RDS del asiento registral
	 */
	private ReferenciaRDS asientoRegistral;
	
	/**
	 * Referencia RDS de los anexos
	 */
	private Map anexos = new HashMap();

	/**
	 * Referencia RDS de los anexos
	 */
	public Map getAnexos() {
		return anexos;
	}
	
	/**
	 * Referencia RDS de los anexos
	 */
	public void setAnexos(Map anexos) {
		this.anexos = anexos;
	}

	/**
	 * Referencia RDS del asiento registral
	 */
	public ReferenciaRDS getAsientoRegistral() {
		return asientoRegistral;
	}

	/**
	 * Referencia RDS del asiento registral
	 */
	public void setAsientoRegistral(ReferenciaRDS asientoRegistral) {
		this.asientoRegistral = asientoRegistral;
	}
	
	
}
