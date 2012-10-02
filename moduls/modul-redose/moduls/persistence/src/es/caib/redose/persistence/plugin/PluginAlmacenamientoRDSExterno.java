package es.caib.redose.persistence.plugin;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.caib.redose.model.FicheroExterno;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.FicheroExternoDelegate;


/**
 * Plugin de almacenamiento externo. 
 * 
 * Gestiona transaccionalidad del almacenamiento. Los plugins externos extenderán de esta clase.
 * 
 * @author Indra
 *
 */
public abstract class PluginAlmacenamientoRDSExterno implements PluginAlmacenamientoRDS{

	public final byte[] obtenerFichero(Long id) throws Exception {
		// Recupera lista de ficheros del documento
		List listaFics = DelegateUtil.getFicheroExternoDelegate().obtenerListaFicherosExterno(id);
		
		// Se queda con el mas reciente
		if (listaFics == null || listaFics.isEmpty()) {
			throw new Exception("No existe ningun fichero asociado al documento");
		}
		FicheroExterno ficExterno = (FicheroExterno) listaFics.get(0);
		
		// Accedemos al almacenamiento externo para obtener el contenido
		if ("S".equals(ficExterno.getBorrar())) {
			throw new Exception("El fichero mas reciente esta marcado para borrar");
		}
		byte[] content = this.obtenerFicheroExterno(ficExterno.getReferenciaExterna());
		return content;
	}

	public final void guardarFichero(Long id, byte[] datos, MetadaAlmacenamiento metadata) throws Exception {
		// Almacenamos fichero en almacenamiento externo y obtenemos referencia
		String referenciaExterna = this.guardarFicheroExterno(id, datos, metadata);
		
		// Marcamos para borrar los ficheros anteriores
		marcarBorrarFicheros(id);	
		
		// Inserta un nuevo fichero en la tabla de ficheros externos del documento
		// (en caso de rollback desaparecera la referencia de la tabla)
		FicheroExterno ficheroExterno = new FicheroExterno();
		ficheroExterno.setReferenciaExterna(referenciaExterna);
		ficheroExterno.setFechaReferencia(new Date());
		ficheroExterno.setIdDocumento(id);
		ficheroExterno.setBorrar("N");
		DelegateUtil.getFicheroExternoDelegate().grabarFicheroExterno(ficheroExterno);
	}

	public final void eliminarFichero(Long id)  throws Exception {
		marcarBorrarFicheros(id);		
	}

	public final boolean purgarFichero(String referenciaExterna) throws Exception {
		return eliminarFicheroExterno(referenciaExterna);
	}
	
	/**
	 * Guarda fichero en almacenamiento externo y devuelve referencia única.
	 * @param id Id documento externo
	 * @param datos Datos documento
	 * @return Referencia externa (debe ser unica)
	 */
	protected abstract String guardarFicheroExterno(Long id, byte[] datos, MetadaAlmacenamiento metadata) throws Exception;
	
	/**
	 * Obtiene fichero almacenado de forma externa. 
	 * @param referenciaExterna Referencia externa
	 * @return Contenido documento
	 */
	protected abstract byte[] obtenerFicheroExterno(String referenciaExterna) throws Exception;
	
	/**
	 * Elimina fichero almacenado de forma externa. 
	 * @param referenciaExterna Referencia externa
	 * @return Indica si el fichero se ha eliminado o no existe
	 */
	protected abstract boolean eliminarFicheroExterno(String referenciaExterna) throws Exception;
	
	
	private void marcarBorrarFicheros(Long id) throws DelegateException {
		// Marca para borrar los ficheros (seran borrados por proceso periodico)		
		FicheroExternoDelegate ficheroExternoDelegate = DelegateUtil.getFicheroExternoDelegate();
		List listaFics = ficheroExternoDelegate.obtenerListaFicherosExterno(id);
		if (listaFics != null) {
			for (Iterator it = listaFics.iterator(); it.hasNext();) {
				FicheroExterno ficExterno = (FicheroExterno) it.next();
				if ("N".equals(ficExterno.getBorrar())) {
					ficheroExternoDelegate.marcarBorrarFicheroExterno(ficExterno.getReferenciaExterna());
				}
			}
		}
	}
}
