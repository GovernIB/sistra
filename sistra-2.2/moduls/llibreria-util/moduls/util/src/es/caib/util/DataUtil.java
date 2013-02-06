package es.caib.util;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utilitats per generar dates amb un format determinat
 * als scripts.
 */
public class DataUtil {

    private static final String DEFAULT_FORMAT = "dd/MM/yyyy";

    public static String avui() {
        return avui(DEFAULT_FORMAT);
    }

    public static String avui(String format) {
        return proximDia(0, format);
    }

    public static String dema() {
        return dema(DEFAULT_FORMAT);
    }

    public static String dema(String format) {
        return proximDia(1, format);
    }

    public static String ahir() {
        return ahir(DEFAULT_FORMAT);
    }

    public static String ahir(String format) {
        return proximDia(-1, format);
    }

    public static String proximDia(int dies) {
        return proximDia(dies, DEFAULT_FORMAT);
    }

    public static String proximDia(int dies, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, dies);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static Date data(String data) {
        return data(data, DEFAULT_FORMAT);
    }

    public static Date data(String data, String format) {
        try {
            return new SimpleDateFormat(format).parse(data);
        } catch (Throwable e) {
            return null;
        }
    }

    public static int distancia(String data1, String data2) {
        return distancia(data1, data2, DEFAULT_FORMAT);
    }

    public static int distancia(String data1, String data2, String format) {
        Date d1 = data(data1, format);
        if (d1 == null) return 0;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(d1);

        Date d2 = data(data2, format);
        if (d2 == null) return 0;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(d2);

        int mult = d2.after(d1) ? 1 : -1;
        int distancia = 0;
        while (!calendar1.getTime().equals(calendar2.getTime())) {
            calendar1.add(Calendar.DATE, mult);
            distancia += mult;
        }
        return distancia;
    }

    public static int distanciaHabils(String data1, String data2) {
        return distanciaHabils(data1, data2, DEFAULT_FORMAT);
    }

    public static int distanciaHabils(String data1, String data2, String format) {
        Date d1 = data(data1, format);
        if (d1 == null) return 0;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(d1);

        Date d2 = data(data2, format);
        if (d2 == null) return 0;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(d2);

        int mult = d2.after(d1) ? 1 : -1;
        int distancia = 0;
        while (!calendar1.getTime().equals(calendar2.getTime())) {
            calendar1.add(Calendar.DATE, mult);
            int dia = calendar1.get(Calendar.DAY_OF_WEEK);
            if (dia != Calendar.SATURDAY && dia != Calendar.SUNDAY) {
                distancia += mult;
            }
        }
        return distancia;
    }

    public static String aFormatLlarg(String lang, String data) {
        return aFormatLlarg(lang, data, DEFAULT_FORMAT);
    }

    public static String aFormatLlarg(String lang, String data, String formatLectura) {
        Date date = data(data, formatLectura);
        if (date == null) return "";

        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale(lang));
        String result = df.format(date);
        if ("ca".equals(lang)) {
            result = result.replaceFirst("/ ([ao])", "d'$1").replaceAll("/", "de");
        }
        return result;
    }
    
    /**
	 * Obtiene primer día de la semana a partir del número de semana.
	 * Establece la hora a las 00:00:00 horas
	 **/
	public static Date obtenerPrimeraHora(Date fecha){
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));		
		calendario2.setTime(fecha);
		calendario2.set(Calendar.HOUR_OF_DAY,0);
		calendario2.set(Calendar.MINUTE,0);
		calendario2.set(Calendar.SECOND,0);
		calendario2.set(Calendar.MILLISECOND,0);
		return calendario2.getTime();
	}
	
	/**
	 * Obtiene primer día de la semana a partir del número de semana.
	 * Establece la hora a las 23:59:59 horas
	 **/
	public static Date obtenerUltimaHora(Date fecha){
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));		
		calendario2.setTime(fecha);
		calendario2.set(Calendar.HOUR_OF_DAY,23);
		calendario2.set(Calendar.MINUTE,59);
		calendario2.set(Calendar.SECOND,59);
		calendario2.set(Calendar.MILLISECOND,999);
		return calendario2.getTime();
	}
	
	/**
	 * Obtiene hora actual.
	 * Establece la hora a las <hora actual>:00:00 horas
	 **/
	public static Date obtenerHoraActual(){
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));		
		calendario2.setTime(new Date());		
		calendario2.set(Calendar.MINUTE,0);
		calendario2.set(Calendar.SECOND,0);
		calendario2.set(Calendar.MILLISECOND,0);
		return calendario2.getTime();
	}
	
	/**
	 * Añade horas a la fecha actual.
	 * Si las horas son negativas, se resta.
	 **/
	public static Date sumarHoras(Date fecha, int horas){
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));		
		calendario2.setTime(fecha);		
		calendario2.add(Calendar.HOUR, horas);
		return calendario2.getTime();
	}
	
	/**
	 * Añade minutos a la fecha actual.
	 * Si los minutos son negativos, se resta.
	 **/
	public static Date sumarMinutos(Date fecha, int minutos){
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));		
		calendario2.setTime(fecha);		
		calendario2.add(Calendar.MINUTE, minutos);
		return calendario2.getTime();
	}
	
	/**
	 * Añade dias a la fecha actual.
	 * Si los dias son negativos, se resta.
	 **/
	public static Date sumarDias(Date fecha, int dias){
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));		
		calendario2.setTime(fecha);		
		calendario2.add(Calendar.DAY_OF_MONTH, dias);
		return calendario2.getTime();
	}

	/**
	 * Añade a la fecha dada un numero de unidades del elemento Calendario dado (dias, meses, años, ....)
	 * 
	 * @param elementoCalendario Elemento a sumar (1=año, 2=meses, 3-4=semanas, 
	 *          5=dias, 10=horas, 12=minutos, 13=segundos, 14=milisegundos  )
	 * @param cantidad Unidades a sumar
	 * @param fecha Fecha 
	 * @param format Formato fecha
	 * @return Fecha final
	 */
	public static String fechaSuma(int elementoCalendario, int cantidad, String fecha, String formato) 
    {
	  Date d = data(fecha, formato);
      if (d == null) return fecha;
      
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(d);
      calendar.add(elementoCalendario, cantidad);
      
      return new SimpleDateFormat(formato).format(calendar.getTime());
   }
	
	/**
	 * Añade a la fecha dada un numero de dias.
	 * 
	 * @param diasSumar Dias a sumar
	 * @param data Fecha 
	 * @param format Formato fecha
	 * @return Fecha final
	 */
	public static String fechaSuma(int cantidad, String fecha, String formato) 
    {
		return fechaSuma(Calendar.DAY_OF_MONTH, cantidad, fecha, formato);
	}

	/**
	 * Añade a la fecha actual un numero de unidades del elemento Calendario dado (dias, meses, años, ....)
	 * 
	 * @param elementoCalendario Elemento a sumar (1=año, 2=meses, 3-4=semanas, 
	 *          5=dias, 10=horas, 12=minutos, 13=segundos, 14=milisegundos  )
	 * @param cantidad Unidades a sumar
	 * @param format Formato fecha
	 * @return Fecha final
	 */
	public static String fechaSuma(int elementoCalendario, int cantidad, String formato) 
    {
		return fechaSuma(elementoCalendario, cantidad, avui(formato), formato);
	}
}
