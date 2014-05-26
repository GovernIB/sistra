package es.caib.bantel.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Contenedor de trabajos de exportacion
 *
 */
public class CSVExportWorks {

	public final static String KEY_CSV_WORKS = "es.caib.bantel.csv-works-session-key";
	private final static int CLEAN_PERIOD = 3600000;  // 1 HORA
	
	private Map trabajos = new HashMap();
	
	
	public CSVExport getCSVExport(String id){
		// Proceso limpieza de trabajos caducados
		cleanOldWorks(id);		
		// Obtiene trabajo
		return (CSVExport) trabajos.get(id);
	}
	
	
	public void removeCSVExport(String id){
		// Proceso limpieza de trabajos caducados
		cleanOldWorks(id);
		// Borra trabajo
		try{trabajos.remove(id);}catch(Exception ex){}		
	}
	
	public void addCSVExport(CSVExport csv){
		// Proceso limpieza de trabajos caducados
		cleanOldWorks(null);
		// Añade trabajo
		trabajos.put(csv.getId(),csv);		
	}
	
	
	private void cleanOldWorks(String filterId){
		Date ahora = new Date();
		String id;
		CSVExport work;
		for (Iterator it=trabajos.keySet().iterator();it.hasNext();){
			id = (String) it.next();
			if (filterId != null && id.equals(filterId)) continue;
			work = (CSVExport) trabajos.get(id);
			if ((ahora.getTime() - work.getLastAccess().getTime()) > CLEAN_PERIOD)
				trabajos.remove(id);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
