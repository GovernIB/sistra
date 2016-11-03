package es.caib.zonaper.persistence.util;

import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;

/**
 * Helper con utilidades para la gestion de expedientes 
 */
public class ExpedienteHelper {

	/**
	 * Inicializa campos de expediente a partir de una entrada de la BTE 
	 * 
	 * @param TramiteBTE Entrada bandeja telemática
	 * @return
	 */
	public static ExpedientePAD inicializarExpediente(TramiteBTE entradaBTE){
		ExpedientePAD expe = new ExpedientePAD();		
		expe.setNumeroEntradaBTE(entradaBTE.getNumeroEntrada());
		expe.setDescripcion(entradaBTE.getDescripcionTramite());
		expe.setAutenticado(entradaBTE.getNivelAutenticacion() != 'A');
		if (expe.isAutenticado()) expe.setIdentificadorUsuario(entradaBTE.getUsuarioSeycon());
		expe.setIdioma(entradaBTE.getIdioma());
		expe.setNifRepresentado(entradaBTE.getRepresentadoNif());
		expe.setNombreRepresentado(entradaBTE.getRepresentadoNombre());
		expe.setUnidadAdministrativa(entradaBTE.getUnidadAdministrativa());		
		return expe;		
	}
	
}
