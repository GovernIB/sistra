package es.caib.redose.persistence.util;

import java.util.Iterator;
import java.util.List;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.UsoRDS;

public class UtilRDS {

	/**
	 * Clona objeto documento RDS excepto propiedades de datos y nombre fichero
	 * @param doc
	 * @return
	 */
	public final static DocumentoRDS cloneDocumentoRDS(DocumentoRDS doc){
		DocumentoRDS docNew = new DocumentoRDS();	
		docNew.setTitulo(doc.getTitulo());
		docNew.setCodigoUbicacion(doc.getCodigoUbicacion());
		docNew.setEstructurado(doc.isEstructurado());		
		docNew.setExtensionFichero(doc.getExtensionFichero());
		docNew.setFechaRDS(doc.getFechaRDS());
		docNew.setFirmas(doc.getFirmas());
		docNew.setHashFichero(doc.getHashFichero());
		docNew.setModelo(doc.getModelo());
		docNew.setNif(doc.getNif());
		docNew.setPlantilla(doc.getPlantilla());
		docNew.setReferenciaRDS(doc.getReferenciaRDS());
		docNew.setUnidadAdministrativa(doc.getUnidadAdministrativa());
		docNew.setUsuarioSeycon(doc.getUsuarioSeycon());
		return docNew;
	}
	
	/**
	 * Obtiene numero de registro/envio/preregistro/preenvio asociado al documento a partir de los usos
	 * @param doc
	 * @return
	 */
	public final static UsoRDS obtenerNumeroEntrada(List usos){
		if (usos == null) return null;		
		UsoRDS usoEntrada = null;
		for (Iterator it=usos.iterator();it.hasNext();){
			UsoRDS uso = (UsoRDS) it.next();
			if (uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_REGISTROENTRADA)){
				usoEntrada = uso; // Seguimos buscando por si hay preregistro				
			}else if (uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_PREREGISTRO)){
				usoEntrada = uso;
				break; // referencia de preregistro tiene preferencia sobre el registro de confirmacion
			}else if (uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_ENVIO)){
				usoEntrada = uso;
				break;
			}		
		}
		return usoEntrada;
	}
	
	
	/**
	 * Obtiene numero de registro salida asociado al documento a partir de los usos
	 * @param doc
	 * @return
	 */
	public final static UsoRDS obtenerNumeroSalida(List usos){
		if (usos == null) return null;
		UsoRDS usoSalida = null;	
		for (Iterator it=usos.iterator();it.hasNext();){
			UsoRDS uso = (UsoRDS) it.next();
			if (uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_REGISTROSALIDA)){
				usoSalida = uso;							
				break;
			}		
		}
		return usoSalida;
	}
}
