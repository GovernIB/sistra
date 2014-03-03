package es.caib.redose.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Contenedor de trabajos de migracion
 *
 */
public class MigracionExportWorks {

	public final static String KEY_MIGRACION_WORKS = "es.caib.redose.migracion-works-session-key";
	private final static int CLEAN_PERIOD = 3600000;  // 1 HORA
	
	private Map trabajos = new HashMap();
	
	
	public MigracionExportWork getMigracionExportWork(String id){
		synchronized (trabajos) {
			// Proceso limpieza de trabajos caducados
			cleanOldWorks(id);		
			// Obtiene trabajo
			return (MigracionExportWork) trabajos.get(id);
		}
	}
	
	
	public void removeMigracionExportWork(String id){
		synchronized (trabajos) {
			// Proceso limpieza de trabajos caducados
			cleanOldWorks(id);
			// Borra trabajo
			try{trabajos.remove(id);}catch(Exception ex){}
		}
	}
	
	public void addWork(MigracionExportWork work){
		synchronized (trabajos) {
			// Proceso limpieza de trabajos caducados
			cleanOldWorks(null);
			// Añade trabajo
			trabajos.put(work.getId(),work);		
		}
	}
	
	
	private void cleanOldWorks(String filterId){
		Date ahora = new Date();
		String id;
		MigracionExportWork work;
		for (Iterator it=trabajos.keySet().iterator();it.hasNext();){
			id = (String) it.next();
			if (filterId != null && id.equals(filterId)) continue;
			work = (MigracionExportWork) trabajos.get(id);
			if ((ahora.getTime() - work.getLastAccess().getTime()) > CLEAN_PERIOD)
				trabajos.remove(id);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
