package es.caib.regtel.model;

import java.io.Serializable;

import es.caib.redose.modelInterfaz.ReferenciaRDS;

/**
 * Modeliza el resultado ofrecido por la capa telem�tica de registro.
 * <br/>
 * Al realizar un registro de entrada (o salida) el registro telem�tico genera un justificante (asiento sicres en formato XML) 
 * en el que aparece el n�mero y fecha de registro y el resumen de la documentaci�n aportada-
 * <br/>
 * Para evitar interpretar este justificante se ofrecen m�todos para acceder al n�mero de registro generado. 
 *
 */
public class ResultadoRegistroTelematico implements Serializable
{
	/**
	 * Resultado registro: n�mero y fecha de registro
	 */
	private ResultadoRegistro resultadoRegistro;
	/**
	 * Referencia RDS del Justificante
	 */
	private ReferenciaRDS referenciaRDSJustificante;
	
	/**
	 * Obtiene resultado registro: n�mero y fecha de registro
	 * @return Resultado registro
	 */
	public ResultadoRegistro getResultadoRegistro() {
		return resultadoRegistro;
	}
	/**
	 * Establece resultado registro: n�mero y fecha de registro
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
