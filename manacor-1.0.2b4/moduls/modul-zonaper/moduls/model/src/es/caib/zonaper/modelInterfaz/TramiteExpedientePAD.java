package es.caib.zonaper.modelInterfaz;

/**
<p>
* Clase que representa un trámite en un expediente de la zona personal
*</p>
* 
*/
public class TramiteExpedientePAD  extends ElementoExpedientePAD{
	/**
	 * Tipo de tramite (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 */	 
	private char tipo;
	/**
	 * Numero de registro / preregistro
	 */
	private String numeroRegistro;
	/**
	 * Descripcion tramite
	 */
	private String descripcion;
	
	/**
	 * Numero de registro (o preregistro)
	 * @return Numero de registro (o preregistro)
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	/**
	 * Numero de registro (o preregistro)
	 * @param numeroRegistro
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	/**
	 * Tipo de tramite (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 * @return Tipo de tramite
	 */
	public char getTipo() {
		return tipo;
	}
	/**
	 * Tipo de tramite (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 * @param tipo Tipo tramite
	 */
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
}
