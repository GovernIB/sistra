package es.caib.mobtratel.front.util;


/**
 * Métodos de utilidad.
 */
public class DateUtils {
	
	
	public static boolean validaHoraMinutos(String hora)
	{
		if((hora != null) && (!hora.equals("")))
		{
			String h = hora.substring(0,2);
			String m = hora.substring(3,5); 
			int horaI = Integer.valueOf(h).intValue();
			if(horaI > 23) return false;
			int minI = Integer.valueOf(m).intValue();
			if(minI > 59) return false;
		}
		return true;
	}



}
