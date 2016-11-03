package es.caib.redose.modelInterfaz;

import java.util.Date;

/**
 * Clase que modeliza un UsoRDS de un Documento para la comunicaci�n de otros m�dulos con el RDS.
 * 
 *  Un UsoRDS indica el uso que esta realizando en el sistema de un documento (por ejemplo al insertar en registro un documento se crea un uso indicando ese registro).
 *  Un documento debe tener asociados uno o m�s usos. 
 *  Un documento sin usos indicar�a que el documento no se utiliza en el sistema y puede ser borrado.
 *  <br/>
 *  En un UsoRDS se especifica:
 *  <ul>
 *   <li> Tipo de uso
 *   <li> Referencia RDS del documento en cuesti�n
 *   <li> Referencia de la aplicaci�n al uso(depende de Tipo Uso, por ejemplo para un registro de entrada ser�a el n�mero de registro) 
 *  </ul>
 *  
 *  Los diferentes usos posibles son (ver ConstantesRDS): 
 *  <ul>
 *   <li> Tipo de uso documentos PAD
 *   <li> Tipo de uso para documentos de un tr�mite en persistencia
 *   <li> Tipo de uso para documentos asociados a un registro de entrada
 *   <li> Tipo de uso para documentos asociados a un registro de salida
 *   <li> Tipo de uso para documentos asociados a un preregistro
 *   <li> Tipo de uso para documentos asociados a un envio
 *   <li> Tipo de uso para documentos asociados a bandeja
 *   <li> Tipo de uso para documentos asociados a preregistro
 *   <li> Tipo de uso para documentos asociados a un expediente 
 *  </ul>

 */
 
public class UsoRDS  implements java.io.Serializable {
	
	/**
	 * Tipo de uso
	 */
	private String tipoUso;	
	/**
	 * Referencia RDS del documento
	 */
	private ReferenciaRDS referenciaRDS;
	/**
	 * Referencia de la aplicaci�n al uso(depende de Tipo Uso)
	 */
	private String referencia;
	/**
	 * Fecha registro(para usos de tipo RTE,RTS,ENV,PRE indica la fecha de registro a establecer en el sello)
	 */
	private Date fechaSello;
	/**
	 * Fecha en la que se inserto el uso
	 */
	private Date fechaUso;
	
	// Getters / Setters	
	/**
	 * Obtiene referencia RDS
	 */
	public ReferenciaRDS getReferenciaRDS() {
		return referenciaRDS;
	}
	/**
	 * Establece referencia RDS
	 * @param referenciaRDS Referencia RDS
	 */
	public void setReferenciaRDS(ReferenciaRDS referenciaRDS) {
		this.referenciaRDS = referenciaRDS;
	}
	/**
	 * Obtiene tipo de uso
	 * @return Tipo uso
	 */
	public String getTipoUso() {
		return tipoUso;
	}
	/**
	 * Establece tipo de uso
	 * @param tipoUso Tipo uso
	 */
	public void setTipoUso(String tipoUso) {
		this.tipoUso = tipoUso;
	}
	/**
	 * Obtiene referencia aplicaci�n
	 * @return Referencia aplicaci�n
	 */
	public String getReferencia() {
		return referencia;
	}
	/**
	 * Establece referencia aplicaci�n
	 * @param referencia Referencia aplicaci�n
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	/**
	* Fecha registro(para usos de tipo RTE,RTS,ENV,PRE indica la fecha de registro a establecer en el sello)
	*/
	public Date getFechaSello() {
		return fechaSello;
	}
	/**
	* Fecha registro(para usos de tipo RTE,RTS,ENV,PRE indica la fecha de registro a establecer en el sello)
	*/
	public void setFechaSello(Date fechaRegistro) {
		this.fechaSello = fechaRegistro;
	}
	public Date getFechaUso() {
		return fechaUso;
	}
	public void setFechaUso(Date fechaUso) {
		this.fechaUso = fechaUso;
	}
	
	
}
