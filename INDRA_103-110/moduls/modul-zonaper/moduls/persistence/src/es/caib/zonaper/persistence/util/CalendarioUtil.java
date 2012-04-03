package es.caib.zonaper.persistence.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * 
 * Contiene logica para calcular rangos de fecha teniendo en cuenta los dias inhabiles del calendario. 
 *  
 *
 */
public class CalendarioUtil {

	private static Log log = LogFactory.getLog(CalendarioUtil.class);
	
	private static CalendarioUtil calendario;
	private static String directorioCalendario;
	private static Map anyos = new HashMap();
	
	private CalendarioUtil(){
		
	}
	
	public static CalendarioUtil getInstance() throws Exception{		
		if (calendario == null){
			calendario = new CalendarioUtil();
			directorioCalendario = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("notificaciones.calendarioDiasFestivos");
			if (StringUtils.isEmpty(directorioCalendario)) throw new Exception("No se ha especificado directorio para calendario dias festivos");
			if (!directorioCalendario.endsWith("/")) {
				directorioCalendario = directorioCalendario + "/";
			}
		}
		return calendario;
	}			
	
	
	/**
	 * Calcula fecha fin tras un plazo teniendo en cuenta los dias inhabiles (fin de semana y festivos).
	 * 
	 */
	public Date calcularPlazoFinNotificacion(Date fechaInicio, int plazo, boolean primerDiaHabil, boolean siguientesDiasHabiles) throws Exception{
		
		Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(fechaInicio);
		
        int anyo = calendar1.get(Calendar.YEAR);
        int mes  = calendar1.get(Calendar.MONTH) + 1;
        List festivos = obtenerDiasFestivosMes(anyo, mes);
        
        int numDias = 0;
        boolean primer = true;
        while (numDias != plazo) {
        
        	// Añadimos un dia
        	calendar1.add(Calendar.DATE, 1);
        	
        	// Cargamos calendario festivos si se cambia de mes        	
        	if (calendar1.get(Calendar.YEAR) != anyo || mes  != (calendar1.get(Calendar.MONTH) + 1)) {
        		 anyo = calendar1.get(Calendar.YEAR);
        	     mes  = calendar1.get(Calendar.MONTH) + 1;
        	     festivos = obtenerDiasFestivosMes(anyo, mes);
        	}
        	
        	// Comprobamos si es inhabil (fin de semana / festivo)
            if ( (primer && primerDiaHabil) || siguientesDiasHabiles) {
            	int diaSemana = calendar1.get(Calendar.DAY_OF_WEEK);
                int diaMes = calendar1.get(Calendar.DAY_OF_MONTH);
	            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY && !festivos.contains(Integer.toString(diaMes))) {
	            	primer = false;
	            	numDias++;
	            }
            } else {
            	primer = false;
            	numDias++;
            }
        }	
			
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.MINUTE,59);
        calendar1.set(Calendar.SECOND,59);
        
		return calendar1.getTime();
	}
	
	
	public  List obtenerDiasFestivosMes(int anyo, int mes) throws Exception{
		Properties props = (Properties) anyos.get(Integer.toString(anyo));
		if (props == null) {
			String fileAnyo = directorioCalendario + anyo + ".properties";
			try {
				props = new Properties();
				props.load(new FileInputStream(fileAnyo));
				anyos.put(Integer.toString(anyo), props);
			} catch (Exception e) {
				throw new Exception("No se ha especificado directorio para calendario dias festivos para año " + anyo, e); 
			}
		}
		
		String festivosMes = props.getProperty("mes." + mes);
		if (festivosMes == null) {
			throw new Exception("No se ha especificado directorio para calendario dias festivos para año " + anyo + " mes " + mes);
		}
		String[] listaDias = festivosMes.split(",");
		
		
		List festivos = new ArrayList();		
		for (int i = 0; i < listaDias.length; i++) {
			festivos.add(listaDias[i]);
		}
		
		return festivos;
	}		
	
}
