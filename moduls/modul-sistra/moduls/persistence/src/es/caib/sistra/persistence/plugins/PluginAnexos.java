package es.caib.sistra.persistence.plugins;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.xml.ConstantesXML;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;

/**
 * Plugin que permite acceder a los datos de los anexos estructurados
 * 
 * ( Solo disponible en script de preenvio) 
 * 
 */
public class PluginAnexos {

	/**
	 * Estado persistencia del trámite
	 */
	private TramitePersistentePAD tramitePersistentePAD; 
		
	/**
	 * Establece estado persistencia
	 */
	public void setEstadoPersistencia(TramitePersistentePAD tramitePAD){
		tramitePersistentePAD = tramitePAD;
	}		
	
	/**
	 * Obtiene el estado del anexo 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public String getEstadoAnexo(String idDocumento,int instancia) throws Exception{
		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);
		if (docPAD == null) throw new Exception("No existe documento " + idDocumento + "-" + instancia);
		return Character.toString(docPAD.getEstado());
	}
	
	/**
	 * Obtiene xml anexo (solo para anexos estructurados)
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @return
	 * @throws Exception
	 */
	public String getXmlAnexo(String idDocumento,int instancia) throws Exception{
		
		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);
		if (docPAD == null) throw new Exception("No existe documento " + idDocumento + "-" + instancia);
		
		if (docPAD.getEstado() != DocumentoPersistentePAD.ESTADO_CORRECTO){
			throw new Exception("El anexo no ha sido aportado");
		}
		
		DocumentoRDS docRDS = DelegateRDSUtil.getRdsDelegate().consultarDocumento(docPAD.getRefRDS());
		
		if (!docRDS.isEstructurado()) throw new Exception("El anexo no es estructurado");
		return new String(docRDS.getDatosFichero(),ConstantesXML.ENCODING);
		
	}
			
}
