package es.caib.redose.model;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Contenedor de trabajos de migracion (solo puede existir un trabajo).
 *
 */
public class MigracionExportWorks {

	private final static String KEY_MIGRACION_WORKS = "es.caib.redose.migracion-works-session-key";
	private final static int CLEAN_PERIOD = 2 * 60 * 1000;  // 15 minutos
	
	private MigracionExportWork trabajoActual = null;
	
	private static MigracionExportWorks works = null;
	
	public static MigracionExportWorks getInstance(HttpServletRequest request) {
		//MigracionExportWorks works = (MigracionExportWorks) request.getSession().getServletContext().getAttribute(MigracionExportWorks.KEY_MIGRACION_WORKS);
		if (works == null) {
			works = new MigracionExportWorks();
		//	request.getSession().getServletContext().setAttribute(MigracionExportWorks.KEY_MIGRACION_WORKS,works);
		}
		return works;
	}
	
	
	public MigracionExportWork getMigracionExportWork(String id) throws Exception{
		if (trabajoActual != null && !trabajoActual.getId().equals(id)) {
			throw new Exception("No existe trabajo con ese id");			
		}
		return trabajoActual;				
	}
	
	public boolean existeTrabajoIniciado(){
		boolean res = false;
		if (trabajoActual != null && trabajoActual.isIniciado()) {
			// Comprobamos si ha caducado
			if (estaCaducadoTrabajoActual()) {
				trabajoActual = null;
			} else {
				res = true;
			}			
		}		
		return res;
	}
	
	
	public void removeMigracionExportWork(String id){		
		if (trabajoActual != null && trabajoActual.getId().equals(id)) {
			trabajoActual = null;
		} 	
	}
	
	public void addWork(MigracionExportWork work) throws Exception{
		if (trabajoActual != null && trabajoActual.isIniciado() && !estaCaducadoTrabajoActual()) {
			// Comprobamos si ha caducado			
			throw new Exception("Existe un trabajo iniciado");
		}
		trabajoActual = work;			
	}
	
	
	private boolean estaCaducadoTrabajoActual() {
		boolean res = false;
		Date ahora = new Date();
		if ((ahora.getTime() - trabajoActual.getLastAccess().getTime()) > CLEAN_PERIOD) {
			res = true;
		}
		return res;
	}	

}
