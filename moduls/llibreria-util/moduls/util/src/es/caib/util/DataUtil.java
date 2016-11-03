package es.caib.util;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utilitats per generar dates amb un format determinat als scripts.
 */
public class DataUtil {

	public static final String DEFAULT_FORMAT = "dd/MM/yyyy";

	public static final float MILISEGUNDOS_SEGUNDO = 1000F;
	public static final float MILISEGUNDOS_MINUTO = 60 * MILISEGUNDOS_SEGUNDO;
	public static final float MILISEGUNDOS_HORA = 60 * MILISEGUNDOS_MINUTO;
	public static final float MILISEGUNDOS_DIA = 24 * MILISEGUNDOS_HORA;
	public static final float MILISEGUNDOS_ANIO = 365 * MILISEGUNDOS_DIA;

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
			SimpleDateFormat sDF = new SimpleDateFormat(format);
			sDF.setLenient(false);

			return sDF.parse(data);
		} catch (Throwable e) {
			return null;
		}
	}

	public static int distancia(String data1, String data2) {
		return distancia(data1, data2, DEFAULT_FORMAT);
	}

	public static int distancia(String data1, String data2, String format) {
		Date d1 = data(data1, format);
		if (d1 == null)
			return 0;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(d1);

		Date d2 = data(data2, format);
		if (d2 == null)
			return 0;
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
		if (d1 == null)
			return 0;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(d1);

		Date d2 = data(data2, format);
		if (d2 == null)
			return 0;
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

	public static String aFormatLlarg(String lang, String data,
			String formatLectura) {
		Date date = data(data, formatLectura);
		if (date == null)
			return "";

		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale(
				lang));
		String result = df.format(date);
		if ("ca".equals(lang)) {
			result = result.replaceFirst("/ ([ao])", "d'$1").replaceAll("/",
					"de");
		}
		return result;
	}

	/**
	 * Obtiene primer día de la semana a partir del número de semana. Establece
	 * la hora a las 00:00:00 horas
	 **/
	public static Date obtenerPrimeraHora(Date fecha) {
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));
		calendario2.setTime(fecha);
		calendario2.set(Calendar.HOUR_OF_DAY, 0);
		calendario2.set(Calendar.MINUTE, 0);
		calendario2.set(Calendar.SECOND, 0);
		calendario2.set(Calendar.MILLISECOND, 0);
		return calendario2.getTime();
	}

	/**
	 * Obtiene primer día de la semana a partir del número de semana. Establece
	 * la hora a las 23:59:59 horas
	 **/
	public static Date obtenerUltimaHora(Date fecha) {
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));
		calendario2.setTime(fecha);
		calendario2.set(Calendar.HOUR_OF_DAY, 23);
		calendario2.set(Calendar.MINUTE, 59);
		calendario2.set(Calendar.SECOND, 59);
		calendario2.set(Calendar.MILLISECOND, 999);
		return calendario2.getTime();
	}

	/**
	 * Obtiene hora actual. Establece la hora a las <hora actual>:00:00 horas
	 **/
	public static Date obtenerHoraActual() {
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));
		calendario2.setTime(new Date());
		calendario2.set(Calendar.MINUTE, 0);
		calendario2.set(Calendar.SECOND, 0);
		calendario2.set(Calendar.MILLISECOND, 0);
		return calendario2.getTime();
	}

	/**
	 * Añade horas a la fecha actual. Si las horas son negativas, se resta.
	 **/
	public static Date sumarHoras(Date fecha, int horas) {
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));
		calendario2.setTime(fecha);
		calendario2.add(Calendar.HOUR, horas);
		return calendario2.getTime();
	}

	/**
	 * Añade minutos a la fecha actual. Si los minutos son negativos, se resta.
	 **/
	public static Date sumarMinutos(Date fecha, int minutos) {
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));
		calendario2.setTime(fecha);
		calendario2.add(Calendar.MINUTE, minutos);
		return calendario2.getTime();
	}

	/**
	 * Añade dias a la fecha actual. Si los dias son negativos, se resta.
	 **/
	public static Date sumarDias(Date fecha, int dias) {
		GregorianCalendar calendario2 = new GregorianCalendar(new Locale("es"));
		calendario2.setTime(fecha);
		calendario2.add(Calendar.DAY_OF_MONTH, dias);
		return calendario2.getTime();
	}

	/**
	 * Añade a la fecha dada un numero de unidades del elemento Calendario dado
	 * (dias, meses, años, ....)
	 * 
	 * @param elementoCalendario
	 *            Elemento a sumar (1=año, 2=meses, 3-4=semanas, 5=dias,
	 *            10=horas, 12=minutos, 13=segundos, 14=milisegundos )
	 * @param cantidad
	 *            Unidades a sumar
	 * @param fecha
	 *            Fecha
	 * @param format
	 *            Formato fecha
	 * @return Fecha final
	 */
	public static String fechaSuma(int elementoCalendario, int cantidad,
			String fecha, String formato) {
		Date d = data(fecha, formato);
		if (d == null)
			return fecha;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(elementoCalendario, cantidad);

		return new SimpleDateFormat(formato).format(calendar.getTime());
	}

	/**
	 * Añade a la fecha dada un numero de dias.
	 * 
	 * @param diasSumar
	 *            Dias a sumar
	 * @param data
	 *            Fecha
	 * @param format
	 *            Formato fecha
	 * @return Fecha final
	 */
	public static String fechaSuma(int cantidad, String fecha, String formato) {
		return fechaSuma(Calendar.DAY_OF_MONTH, cantidad, fecha, formato);
	}

	/**
	 * Añade a la fecha actual un numero de unidades del elemento Calendario
	 * dado (dias, meses, años, ....)
	 * 
	 * @param elementoCalendario
	 *            Elemento a sumar (1=año, 2=meses, 3-4=semanas, 5=dias,
	 *            10=horas, 12=minutos, 13=segundos, 14=milisegundos )
	 * @param cantidad
	 *            Unidades a sumar
	 * @param format
	 *            Formato fecha
	 * @return Fecha final
	 */
	public static String fechaSuma(int elementoCalendario, int cantidad,
			String formato) {
		return fechaSuma(elementoCalendario, cantidad, avui(formato), formato);
	}

	/**
	 * Devuelve una cadena que representa una fecha con un determinado formato
	 * dado a partir de una fecha Date dada.
	 * 
	 * @param Date
	 * @param String
	 * @return String
	 */
	public static String fechaEnFormato(Date fecha, String formato) {
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		return (fecha == null ? null : formatter.format(fecha));
	} // fechaEnFormato

	/**
	 * Devuelve una fecha a partir de una cadena en un formato dado
	 * 
	 * @param String
	 * @param String
	 * @return Date
	 */
	public static Date fechaEnFormato(String fecha, String formato) {
		return data(fecha, formato);
	} // fechaEnFormato

	/**
	 * Devuelve una cadena con una fecha en el formato dado a partir de otra
	 * cadena en otro formato dado.
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @return Date
	 */
	public static String fechaEnFormato(String fecha, String formatoEntrada,
			String formatoSalida) {
		SimpleDateFormat formatterSalida = new SimpleDateFormat(formatoSalida);

		Date fechaEntrada = fechaEnFormato(fecha, formatoEntrada);

		return formatterSalida.format(fechaEntrada);
	} // fechaEnFormato

	/**
	 * Devuelve la fecha del sistema como objeto Date.
	 * 
	 * @param String
	 * @param String
	 * @return Date
	 */
	public static Date fechaSistema() {
		return new Date();
	} // fechaEnFormato

	/**
	 * Devuelve la fecha del sistema con el formato indicado.
	 * 
	 * @param formato
	 *            : cadena con formato de fecha, p. e.: "dd/MM/yyyy HH:mm:ss"
	 */
	public static String fechaSistema(String formato) {
		SimpleDateFormat df = new SimpleDateFormat(formato);
		String ahoraCadena = df.format(new Date());

		return ahoraCadena;
	} // fechaSistema

	/**
	 * Comprobación de la fecha dada con la fecha actual del sistema. Devuelve
	 * el numero de milisegundos que hay de diferencia entre las dos fechas.
	 * 
	 * @param fecha
	 *            cadena con una fecha en un determinado formato
	 * @param formato
	 *            formato de la fecha dada
	 * @return diferencia en milisegundos entre las dos fechas dadas
	 */
	public static long milisegundosDiferenciaFechas(String fecha, String formato) {
		return milisegundosDiferenciaFechas(fecha, fechaSistema(formato),
				formato);
	} // comparaFechas

	/**
	 * Devuelve el numero de milisegundos que hay de diferencia entre dos fechas
	 * dadas en milisegundos.
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return diferencia en milisegundos entre las dos fechas dadas
	 */
	public static long milisegundosDiferenciaFechas(long fFecha1, long fFecha2) {
		return (fFecha1 - fFecha2);
	} // comparaFechas

	/**
	 * Devuelve el numero de milisegundos que hay de diferencia entre dos
	 * objetos Date dados.
	 * 
	 * @param fecha1
	 *            objeto Date
	 * @param fecha2
	 *            objeto Date
	 * @return diferencia en milisegundos entre las dos fechas dadas
	 */
	public static long milisegundosDiferenciaFechas(Date fFecha1, Date fFecha2) {
		return (fFecha1.getTime() - fFecha2.getTime());
	} // comparaFechas

	/**
	 * Devuelve el numero de milisegundos que hay de diferencia entre dos fechas
	 * dadas en un formato dado.
	 * 
	 * @param fecha1
	 *            cadena con una fecha en un determinado formato
	 * @param fecha1
	 *            cadena con una fecha en un determinado formato
	 * @param formato
	 *            formato de las fechas dadas
	 * @return diferencia en milisegundos entre las dos fechas dadas
	 */
	public static long milisegundosDiferenciaFechas(String fecha1,
			String fecha2, String formato) {
		long fFecha1, fFecha2;

		fFecha1 = fechaEnFormato(fecha1, formato).getTime();
		fFecha2 = fechaEnFormato(fecha2, formato).getTime();

		return (fFecha2 - fFecha1);
	} // comparaFechas

	/**
	 * Comprueba dos fechas dadas en un formato determinado. Devuelve: true si
	 * fecha 1 < fecha2 ; false si fecha 1 >= fecha2 .
	 * 
	 * @param fecha1
	 *            Fecha inicial
	 * @param fecha2
	 *            Fecha final
	 * @param format
	 *            Formato fecha
	 * @return boolean true si fecha 1 < fecha2 ; false si fecha 1 >= fecha2
	 */
	public static boolean fechasDiferencia(String fecha1, String fecha2,
			String formato) {
		Date d1 = data(fecha1, formato);
		Date d2 = data(fecha2, formato);
		if (d1 == null || d2 == null)
			return false;

		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(d1);
		calendar2.setTime(d2);

		return calendar1.before(calendar2);
	} // fechasDiferencia

	/**
	 * Calcula la diferencia entre dos fechas dadas en un deterinado formato,
	 * con decimales y con signo (negativo si fecha1 > fecha2). La unidad del
	 * resultado se indica mediante un parametro numérico que refleja el número
	 * de milisegundos de esa unidad.
	 * 
	 * @param milisegundosUnidadTemporal
	 *            MILISEGUNDOS_SEGUNDO, MILISEGUNDOS_MINUTO, MILISEGUNDOS_HORA,
	 *            MILISEGUNDOS_DIA, MILISEGUNDOS_ANIO
	 * @param fecha1
	 * @param fecha2
	 * @param formato
	 * 
	 * @return
	 */
	public static float fechaDiferencia(float milisegundosUnidadTemporal,
			String fecha1, String fecha2, String formato) {
		float resultado;

		resultado = milisegundosDiferenciaFechas(fecha1, fecha2, formato)
				/ milisegundosUnidadTemporal;

		return resultado;
	}

	public static float fechaDiferencia(float milisegundosUnidadTemporal,
			Date fecha1, Date fecha2) {
		float resultado;

		resultado = milisegundosDiferenciaFechas(fecha1, fecha2)
				/ milisegundosUnidadTemporal;

		return resultado;
	}

	/**
	 * Calcula las horas de diferencia entre dos fechas dadas, con decimales y
	 * con signo (negativo si fecha1 > fecha2).
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @param formato
	 * 
	 * @return
	 */
	public static float horasDiferencia(String fecha1, String fecha2,
			String formato) {
		return fechaDiferencia(MILISEGUNDOS_HORA, fecha1, fecha2, formato);
	}

	/**
	 * comparaFechas : calculo del numero de dias de diferencia entre dos fechas
	 * dadas.
	 * 
	 * @param fecha1
	 *            objeto Date
	 * @param fecha2
	 *            objeto Date
	 * @return numero de dias de diferencia entre las dos fechas dadas
	 */
	public static float diasDiferencia(Date fecha1, Date fecha2) {
		return fechaDiferencia(MILISEGUNDOS_DIA, fecha1, fecha2);
	} // diasDiferencia

	/**
	 * comparaFechas : calculo del numero de dias de diferencia entre dos fechas
	 * dadas
	 * 
	 * @param fecha1
	 *            cadena con una fecha en un determinado formato
	 * @param fecha2
	 *            cadena con una fecha en un determinado formato
	 * @param formato
	 *            formato de las fechas dadas
	 * @return numero de dias de diferencia entre las dos fechas dadas
	 */
	public static float diasDiferencia(String fecha1, String fecha2,
			String formato) {
		return fechaDiferencia(MILISEGUNDOS_DIA, fecha1, fecha2, formato);
	} // diasDiferencia

	/**
	 * comparaFechas : calculo del numero de años de diferencia entre dos fechas
	 * dadas NOTA: 1 año = 365 dias
	 * 
	 * @param fecha1
	 *            cadena con una fecha en un determinado formato
	 * @param fecha1
	 *            cadena con una fecha en un determinado formato
	 * @param formato
	 *            formato de las fechas dadas
	 * @return numero de años de diferencia entre dos fechas dadas
	 */
	public static float aniosDiferencia(String fecha1, String fecha2,
			String formato) {
		return fechaDiferencia(MILISEGUNDOS_ANIO, fecha1, fecha2, formato);
	} // aniosDiferencia

	/**
	 * Realiza el sumatorio de las horas de diferecia entre una lista de duplas
	 * de horas en un formato dado y serializadas en una cadena con 2
	 * separadores. Contempla horario continuo en cuarteto de horarios
	 * (mañana-tarde)(con 2 vacios en el hasta 1 y en el desde 2)
	 * 
	 * @param cadenaHorasIntervalos
	 * @param separadorIntervalos
	 * @param separadorHoras
	 * @param formato
	 * @return
	 */
	public static float sumaHorasIntervalos(String cadenaHorasIntervalos,
			String separadorIntervalos, String separadorHoras, String formato) {
		float resultado = 0;

		String horaDesde1 = "", horaHasta1 = "", horaDesde2 = "", horaHasta2 = "";

		String[] intervalosHoras = cadenaHorasIntervalos.split(
				separadorIntervalos, -1);

		for (int x = 0; x < intervalosHoras.length; x++) {
			String[] intervalo1 = intervalosHoras[x].split(separadorHoras, -1);

			if (intervalo1.length == 2) {
				horaDesde1 = intervalo1[0];
				horaHasta1 = intervalo1[1];

				if (!horaDesde1.trim().equals("")
						&& !horaHasta1.trim().equals(""))
					resultado = resultado
							+ horasDiferencia(horaDesde1, horaHasta1, formato);
			}

			if (++x < intervalosHoras.length) {
				String[] intervalo2 = intervalosHoras[x].split(separadorHoras,
						-1);

				if (intervalo2.length == 2) {
					horaDesde2 = intervalo2[0];
					horaHasta2 = intervalo2[1];

					if (!horaDesde2.trim().equals("")
							&& !horaHasta2.trim().equals(""))
						resultado = resultado
								+ horasDiferencia(horaDesde2, horaHasta2,
										formato);
				}
			}

			// contempla horario continuo en cuarteto de horarios
			// (mañana-tarde)(con 2 vacios en el hasta1 y en el desde2)
			if (!horaDesde1.trim().equals("") && horaHasta1.trim().equals("")
					&& horaDesde2.trim().equals("")
					&& !horaHasta2.trim().equals("")) {
				if (!horaDesde1.trim().equals("")
						&& !horaHasta2.trim().equals(""))
					resultado = resultado
							+ horasDiferencia(horaDesde1, horaHasta2, formato);
			}
		}

		return resultado;
	}

	/**
	 * Verifica si un intervalo de horas dado está incluido en otro intervalo de
	 * horas dado.
	 * 
	 * @param horaInicial1
	 * @param horaFinal1
	 * @param horaInicial2
	 * @param horaFinal2
	 * 
	 * @return true si está incluiso el intervalo, false si no está incluido el
	 *         intervalo o se produce una excepción (p.e: alguna de las fechas
	 *         es vacia)
	 */
	public static boolean intervaloHorasEnIntervaloHoras(String horaInicial1,
			String horaFinal1, String horaInicial2, String horaFinal2,
			String formato) {
		try {
			if (milisegundosDiferenciaFechas(horaInicial2, horaInicial1,
					formato) >= 0
					&& milisegundosDiferenciaFechas(horaFinal1, horaFinal2,
							formato) >= 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifica si un intervalo de horas dado está incluido en una lista de
	 * intervalo de horas dado. Si el intervalo de horas dado está incluido en
	 * al menos uno de los intervalos de la lista: se devuelve la posición del
	 * primer intervalo que lo incluye, en caso contrario se devuelve 0.
	 * 
	 * @param horaInicial
	 * @param horaFinal
	 * @param cadenaHorasIntervalos
	 * @param separadorIntervalos
	 * @param separadorHoras
	 * @param formato
	 * 
	 * @return 0 si no está incluido el intervalo de horas dado en la lista de
	 *         intervalos dada, si existe el intervalo se devuelve la posición
	 *         concreta en la lista
	 */
	public static int intervaloHorasEnListaIntervalosHoras(String horaInicial,
			String horaFinal, String cadenaHorasIntervalos,
			String separadorIntervalos, String separadorHoras, String formato) {
		int posicionIntervalo = 0;

		String horaDesde1 = "", horaHasta1 = "", horaDesde2 = "", horaHasta2 = "";

		String[] intervalosHoras = cadenaHorasIntervalos.split(
				separadorIntervalos, -1);

		for (int x = 0; x < intervalosHoras.length; x++) {
			String[] intervalo1 = intervalosHoras[x].split(separadorHoras, -1);

			if (intervalo1.length == 2) {
				posicionIntervalo++;
				horaDesde1 = intervalo1[0];
				horaHasta1 = intervalo1[1];

				if (intervaloHorasEnIntervaloHoras(horaInicial, horaFinal,
						horaDesde1, horaHasta1, formato)) {
					return posicionIntervalo;
				}
			}

			if (++x < intervalosHoras.length) {
				String[] intervalo2 = intervalosHoras[x].split(separadorHoras,
						-1);

				if (intervalo2.length == 2) {
					posicionIntervalo++;
					horaDesde2 = intervalo2[0];
					horaHasta2 = intervalo2[1];

					if (intervaloHorasEnIntervaloHoras(horaInicial, horaFinal,
							horaDesde2, horaHasta2, formato)) {
						return posicionIntervalo;
					}
				}
			}

			// contempla horario continuo en cuarteto de horarios
			// (mañana-tarde)(con 2 vacios en el hasta1 y en el desde2)
			if (!horaDesde1.trim().equals("") && horaHasta1.trim().equals("")
					&& horaDesde2.trim().equals("")
					&& !horaHasta2.trim().equals("")) {
				posicionIntervalo--;
				if (intervaloHorasEnIntervaloHoras(horaInicial, horaFinal,
						horaDesde1, horaHasta2, formato)) {
					return posicionIntervalo;
				}
			}
		}

		// Si el intervalo dado se sale de la lista de intervalos, se devuelve 0
		return 0;
	}
	
	/**
	 * Verifica si un intervalo de horas dado, considerando mañana y tarde, está
	 * incluido en una lista de intervalo de horas dado. Si el intervalo de
	 * horas dado está incluido en al menos uno de los intervalos de la lista:
	 * se devuelve la posición del primer intervalo que lo incluye, en caso
	 * contrario se devuelve 0.
	 * 
	 * @param horaInicialM
	 * @param horaFinalM
	 * @param horaInicialT
	 * @param horaFinalT
	 * @param cadenaHorasIntervalos
	 * @param separadorIntervalos
	 * @param separadorHoras
	 * @param formato
	 * 
	 * @return 0 si no está incluido el intervalo de horas dado en la lista de
	 *         intervalos dada, si existe el intervalo se devuelve la posición
	 *         concreta en la lista
	 */
	
	public static int intervaloHorasEnListaIntervalosMT (String horaInicialM,
			String horaFinalM, String horaInicialT, String horaFinalT,
			String cadenaHorasIntervalos, String separadorIntervalos,
			String separadorHoras, String formato) {
		
		int resultadoM = -1;
		int resultadoT = -1;
		int resultadoMT = -1;
		
		if (!horaInicialM.trim().equals("") && !horaFinalM.trim().equals("")) {
			resultadoM = intervaloHorasEnListaIntervalosHoras(horaInicialM, horaFinalM,
					cadenaHorasIntervalos, separadorIntervalos, separadorHoras,
					formato);
		}
		if (!horaInicialT.trim().equals("") && !horaFinalT.trim().equals("")) {
			resultadoT = intervaloHorasEnListaIntervalosHoras(horaInicialT, horaFinalT,
					cadenaHorasIntervalos, separadorIntervalos, separadorHoras,
					formato);
		}
		if (!horaInicialM.trim().equals("") && horaFinalM.trim().equals("")
				&& horaInicialT.trim().equals("")
				&& !horaFinalT.trim().equals("")){
			resultadoMT = intervaloHorasEnListaIntervalosHoras(horaInicialM, horaFinalT,
					cadenaHorasIntervalos, separadorIntervalos, separadorHoras,
					formato);
		}
		if (resultadoM == 0 || resultadoT == 0 || resultadoMT == 0) return 0;
		else return Math.max(Math.max(resultadoM, resultadoT), resultadoMT);
	}

}
