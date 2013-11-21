package es.caib.sistra.plugins.custodia;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * 
 * Interfaz con sistema de custodia de documentos firmados.<br/>
 * 
 * El REDOSE se comunicará a través de este plugin para almacenar los documentos
 * que tengan firma en el sistema de custodia.  
 * 
 * REDOSE llamara a custodiarDocumento cada vez que el documento o sus firmas sean modificadas. 
 * En caso de integraciones no transaccionales esta funcion debe ir devolviendo cada vez un identificador distinto.
 * De esta forma se iran almacenando las distintas versiones de custodia en REDOSE y luego el proceso de borrado de 
 * documentos borrará las versiones antiguas. De esta forma se aseguraría la transaccionalidad de forma que un rollback
 * en la transacción de REDOSE mantendría el documento en custodia equivalente.
 * 
 */
public interface PluginCustodiaIntf extends PluginSistraIntf{

	/**
	 * 
	 * Inserta documento en el sistema de custodia. 
	 * 
	 * @param documento Documento RDS con el contenido del documento y sus firmas asociadas
	 * @return Identificador de documento en custodia
	 */
	public String custodiarDocumento(DocumentoRDS documento) throws Exception;
	
	/**
	 * Elimina documento del sistema de custodia.
	 * En caso de eliminar un documento que no exista no generará excepción, generará un warning. 
	 * 
	 * @param codigoDocumentoCustodia Identificador del documento en custodia
	 * @param firmas
	 * 
	 */
	public void eliminarDocumento(String codigoDocumentoCustodia) throws Exception;
	
	/**
	 * Obtiene url consulta de documento en custodia. 
	 * @param codigoDocumentoCustodia Codigo documento custodia
	 * @return Url de consulta (nulo si no permite)
	 * @throws Exception
	 */
	public String obtenerUrlDocumento(String codigoDocumentoCustodia) throws Exception;
	
}
