package es.caib.sistra.plugins.gestionDocumental;

import java.util.List;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * <p>
 * Plugin para consolidar los documentos en un gestor documental. <br/>
 * Este plugin consolidará los documentos asociados a: trámites, notificaciones y eventos de expediente.<br/>
 * A nivel de trámite los documentos se consolidaran una vez se registre el trámite o se confirme para trámites con preregistro. <br/>
 * </p>
 * <p>
 * Los usos de un documento indicarán  donde se esta usando el documento (registro entrada, bandeja telematica, registro salida, etc.). 
 * Cada uso contendrá la referencia asociada al tipo de uso (p.e. para un uso de tipo de registro de entrada tendremos como referencia 
 * el número de registro de entrada. <br/>
 * Los usos pueden utilizarse para organizar los documentos en el gestor documental.
 * </p>
 * <p>
 * Los casos posibles son:
 * <ul>
 * <li><strong>Documentos asociados a trámites</strong></li>
 * <li>Documento perteneciente a un trámite sistra de tipo registro:
 * 		<ul>
 * 			<li>Uso para documentos asociados a un registro telemático de entrada (ConstantesRDS.TIPOUSO_REGISTROENTRADA)</li>
 * 			<li>Uso para documentos asociados a una entrada en la bandeja telemática (ConstantesRDS.TIPOUSO_BANDEJA)</li> 
 * 		</ul>
 * </li>
 * <li>Documento perteneciente a un trámite sistra de tipo envío:
 * 		<ul>
 * 			<li>Uso para documentos asociados a un envío (ConstantesRDS.TIPOUSO_ENVIO)</li>
 * 			<li>Uso para documentos asociados a una entrada en la bandeja telemática (ConstantesRDS.TIPOUSO_BANDEJA)</li> 
 * 		</ul>
 * </li>
 * <li>Documento perteneciente a un trámite sistra de tipo preregistro:
 * 		<ul>
 * 			<li>Uso para documentos asociados a un preregistro (ConstantesRDS.TIPOUSO_PREREGISTRO)</li>
 * 			<li>Uso asociado a una entrada en el registro (siempre que se haya confirmado correctamente en el registro) (ConstantesRDS.TIPOUSO_REGISTROENTRADA)</li>
 * 			<li>Uso para documentos asociados a una entrada en la bandeja telemática (ConstantesRDS.TIPOUSO_BANDEJA)</li> 
 * 		</ul>
 * </li> 
 * <li>Documento perteneciente a un trámite NO sistra, es decir, un registro telemático de entrada realizado por otra aplicación distinta a sistra:
 * 		<ul>
 * 			<li>Uso para documentos asociados a un registro telemático de entrada (ConstantesRDS.TIPOUSO_REGISTROENTRADA)</li> 
 * 		</ul>
 * </li>
 * </ul> 
 <ul>
 <li><strong>Documentos asociados a expedientes y notificaciones</strong></li>
 * <li>Documento perteneciente a un evento de expediente:
 * 		<ul>
 * 			<li>Uso para documentos asociados a un evento de expediente (ConstantesRDS.TIPOUSO_EXPEDIENTE)</li>
 * 		</ul>
 * </li>
 * <li>Documento perteneciente a una notificación:
 * 		<ul>
 * 			<li>Uso para documentos asociados a un registro telemático de salida (ConstantesRDS.TIPOUSO_REGISTROSALIDA)</li> 
 * 		</ul>
 * </li>
 * </ul>
 *  
 * </p>
 * 
 */
public interface PluginGestorDocumentalIntf extends PluginSistraIntf{

	/**
	 * 
	 * Inserta documento en el gestor documental. El proceso de inserción deberá controlar que el documento pueda
	 * ser intentar consolidado más de una vez (debería sobreescribir la versión anterior).
	 * 
	 * @param documento Documento RDS con el contenido del documento y sus firmas asociadas
	 * @param usos Usos del documento. Indica donde se esta usando el documento (registro entrada, bandeja telematica, registro salida, etc.)
	 * @return Identificador de documento en el gestor documental
	 */
	public String consolidarDocumento(DocumentoRDS documento, List usos) throws Exception;

}		