package es.caib.regtel.model;

import java.io.Serializable;

import es.caib.redose.modelInterfaz.ReferenciaRDS;

/**
 * Modeliza el resultado ofrecido por la capa telemática de registro.
 * <br/>
 * Al realizar un registro de entrada (o salida) el registro telemático genera un justificante (asiento sicres en formato XML) 
 * en el que aparece el número y fecha de registro y el resumen de la documentación aportada-
 * <br/>
 * Para evitar interpretar este justificante se ofrecen métodos para acceder al número de registro generado. 
 *
 */
public class ResultadoRegistroTelematico implements Serializable
{
	/**
	 * Resultado registro: número y fecha de registro
	 */
	private ResultadoRegistro resultadoRegistro;
	/**
	 * Referencia RDS del Justificante
	 */
	private ReferenciaRDS referenciaRDSJustificante;
	
	/**
	 * Obtiene resultado registro: número y fecha de registro
	 * @return Resultado registro
	 */
	public ResultadoRegistro getResultadoRegistro() {
		return resultadoRegistro;
	}
	/**
	 * Establece resultado registro: número y fecha de registro
	 * @param resultadoRegistro
	 */
	public void setResultadoRegistro(ResultadoRegistro resultadoRegistro) {
		this.resultadoRegistro = resultadoRegistro;
	}
	/**
	 * Obtiene referencia RDS del justicante generado por registro.
	 * @return Referencia RDS
	 */
	public ReferenciaRDS getReferenciaRDSJustificante() {
		return referenciaRDSJustificante;
	}
	/**
	 * Establece referencia RDS del justicante generado por registro.
	 * @param referenciaRDSJustificante Referencia RDS
	 */
	public void setReferenciaRDSJustificante(ReferenciaRDS referenciaRDSJustificante) {
		this.referenciaRDSJustificante = referenciaRDSJustificante;
	}
	
}
