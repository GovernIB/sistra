package es.caib.sistra.plugins.regtel;

import java.util.List;
import java.util.Map;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.sistra.plugins.PluginSistraIntf;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.Justificante;

/**
 * 
 * Interfaz módulo de registro E/S.
 * <br/>
 * Se encarga de recibir las peticiones desde la capa telemática de registro y actualizar la aplicación de 
 * registro de E/S del organismo.
 * <br/>
 * Esta actualización se engloba dentro de la transacción global del proceso por lo que sería aconsejable el uso
 * de un datasource XA para las operaciones sobre BBDD.
 * 
 */
public interface PluginRegistroIntf extends PluginSistraIntf {

	/**
	 * Realiza apunte registral de registro de entrada. 
	 * <br/>
	 * Se proporciona una clase que permite acceder a los datos del asiento
	 * registral (parámetro asiento). En principio con estos datos serían suficientes
	 * para realizar el apunte registral, no obstante, se ofrecen las referencias en el
	 * RDS tanto del asiento registral como de los anexos. 
	 * 
	 * @param asiento Asiento registral de entrada. Es una clase que permite recorrer el xml del asiento registral.
	 * @param refAsiento Referencia RDS del asiento registral de entrada
	 * @param refAnexos Map con las referencias RDS de los documentos anexos <br/>
	 * 	<ul>
	 * 	<li>key = Id documento especificado en asiento (tag DATOS_ANEXO_DOCUMENTACION.IDENTIFICADOR_DOCUMENTO)</li>
	 *  <li>value = Referencia RDS del documento (clase ReferenciaRDS)</li>
	 *  </ul>
	 * @return ResultadoRegistro Devuelve número y fecha de registro
	 * @throws Exception
	 */
	public ResultadoRegistro registroEntrada(AsientoRegistral asiento,ReferenciaRDS refAsiento,Map refAnexos) throws Exception;
	
	/**
	 * Realiza apunte registral de registro de salida
	 * <br/>
	 * Se proporciona una clase que permite acceder a los datos del asiento
	 * registral (parámetro asiento). En principio con estos datos serían suficientes
	 * para realizar el apunte registral, no obstante, se ofrecen las referencias en el
	 * RDS tanto del asiento registral como de los anexos. 

	 * @param asiento Asiento registral de salida. Es una clase que permite recorrer el xml del asiento registral.
	 * @param refAsiento Referencia RDS del asiento registral de salida
	 * @param refAnexos Map con las referencias RDS de los documentos anexos <br/>
	 * 	<ul>
	 * 	<li>key = Id documento especificado en asiento (tag DATOS_ANEXO_DOCUMENTACION.IDENTIFICADOR_DOCUMENTO)</li>
	 *  <li>value = Referencia RDS del documento (clase ReferenciaRDS)</li>
	 *  </ul>
	 * @return ResultadoRegistro Devuelve número y fecha de registro
	 * @throws Exception
	 */	
	public ResultadoRegistro registroSalida(AsientoRegistral asiento,ReferenciaRDS refAsiento,Map refAnexos) throws Exception;
	
	/**
	 * Confirma un preregistro. Debe realizar un apunte registral indicando que se ha confirmado el preregistro.
	 * Se proporciona una clase que permite acceder a los datos del asiento
	 * registral (parámetro asientoPreregistro ). En principio con estos datos serían suficientes
	 * para realizar el apunte registral, no obstante, se ofrecen las referencias en el
	 * RDS tanto del asiento registral como de los anexos.
	 * 
	 * @param oficina Oficina registral en la que se ha confirmado el preregistro
	 * @param codigoProvincia Código municipio del ciudadano (código INE)
	 * @param codigoMunicipio Código provincia del ciudadano (código INE)
	 * @param descripcionMunicipio Descripción municipio
	 * @param justificantePreregistro Justificante del preregistro
	 * @param refJustificante Referencia RDS del justificante del preregistro
	 * @param refAsiento Referencia RDS del asiento registral del preregistro
	 * @param refAnexos Map con las referencias RDS de los documentos anexos <br/>
	 * 	<ul>
	 * 	<li>key = Id documento especificado en asiento (tag DATOS_ANEXO_DOCUMENTACION.IDENTIFICADOR_DOCUMENTO)</li>
	 *  <li>value = Referencia RDS del documento (clase ReferenciaRDS)</li>
	 *  </ul>
	 * @return ResultadoRegistro Devuelve número y fecha de registro 
	 * @throws Exception
	 */
	public ResultadoRegistro confirmarPreregistro(String oficina,String codigoProvincia,String codigoMunicipio,String descripcionMunicipio,Justificante justificantePreregistro,ReferenciaRDS refJustificante,ReferenciaRDS refAsiento,Map refAnexos) throws Exception;												  
	
	/**
	 * Obtiene lista de oficinas registrales
	 * @return List Lista de oficinas registro
	 * @see OficinaRegistro
	 */
	public List obtenerOficinasRegistro();
	
	/**
	 * Obtiene lista de oficinas registrales para los que el usuario de registro tiene permiso
	 * @param usuario Identificador usuario
	 * @return List Lista de oficinas registro
	 * @see OficinaRegistro
	 */
	public List obtenerOficinasRegistroUsuario(String usuario);
	
	/**
	 * Obtiene tipos de asunto
	 * @return List Lista de tipos de asunto
	 * @see TipoAsunto
	 */
	public List obtenerTiposAsunto();
	
	/**
	 * Obtiene lista de servicios destinatarios
	 * @return List Lista de servicios destinatarios
	 * @see ServicioDestinatario
	 */
	public List obtenerServiciosDestino();
		
}
