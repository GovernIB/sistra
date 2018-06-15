package es.caib.zonaper.helpdesk.front.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.text.NumberFormat;

import javax.naming.InitialContext;

import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.zonaper.helpdesk.front.Constants;

public class Util
{
	public final static long DIA = 60 * 60 * 24 * 1000 ; // en milisegundos

	private static String[] meses_es = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
	private static String[] meses_ca = {"Gen", "Feb", "Mar", "Abr", "Mag", "Jun", "Jul", "Ago", "Set", "Oct", "Nov", "Des"};
	private static String[] mesesEntero_es = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	private static String[] mesesEntero_ca = {"Gener", "Febrer", "Març", "Abril", "Maig", "Juny", "Juliol", "Agost", "Setembre", "Octubre", "Novembre", "Desembre"};
	public static String[] splitString( String str )
    {
    	int iSize = str.length();
    	String [] arrResult = new String[ iSize ];
    	for ( int i = 0; i < iSize; i++ )
    	{
    		arrResult[ i ] = str.substring( i, i + 1 );  
    	}
    	return arrResult;
    }
    
    public static String concatArrString( String[] arrStr )
    {
    	StringBuffer sb = new StringBuffer();
    	for ( int i = 0; i < arrStr.length; i++ )
    	{
    		sb.append( arrStr[i] );
    	}
    	return sb.toString();
    }
    
    /**
     * Devuelve el último dia del mes
     * @param as_anyo
     * @param as_mes
     * @return
     */
    public static int UltimoDia(String as_anyo, String as_mes){
    	int li_feb=28; 
    	if (((Integer.parseInt(as_anyo)%4==0) && (Integer.parseInt(as_anyo)%100!=0)) || (Integer.parseInt(as_anyo)%400==0)) { 
    		li_feb = 29; 
    	} 
    	switch(Integer.parseInt(as_mes)) { 
    	case 1: return 31;
    	case 2: return li_feb; 
    	case 3: return 31; 
    	case 4: return 30; 
    	case 5: return 31; 
    	case 6: return 30; 
    	case 7: return 31; 
    	case 8: return 31; 
    	case 9: return 30; 
    	case 10: return 31; 
    	case 11: return 30; 
    	case 12: return 31; 
    	} 
    	return 0;
    }
	
	public static String DosCifras(int ai_valor){
		String ls_dato; 
		if (ai_valor < 10) ls_dato="0"+Integer.toString(ai_valor);
			else ls_dato = Integer.toString(ai_valor);
		return ls_dato;
	}

	
	public static String getDescripcionMes(String idioma, String as_mes){
		  try{	
		  	int li_mes;
		  	if (as_mes.startsWith("0")){
		  		li_mes = Integer.parseInt(as_mes.substring(1));
		  	}else{
		  		li_mes = Integer.parseInt(as_mes);
		  	}
			return getDescripcionMes(idioma, li_mes);
		  }catch(Throwable e){			 
			  return null;
		  }						
	 }
	
	public static String getDescripcionMes(String idioma, int ai_mes){
		if((ai_mes < 1) && (ai_mes > 12)) return null;
		if(idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN))
		{
			return meses_ca[ai_mes - 1];
		}
		else
		{
			return meses_es[ai_mes - 1];
		}
	 }
	
	public static String getDescripcionMesEntero(String idioma, int ai_mes){
		if((ai_mes < 1) && (ai_mes > 12)) return null;
		if(idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN))
		{
			return mesesEntero_ca[ai_mes - 1];
		}
		else
		{
			return mesesEntero_es[ai_mes - 1];
		}
	 }
	
	public static String getFechaActual(String as_formato)
	throws Throwable
	{
		  return Util.getFecha(new Timestamp(System.currentTimeMillis()), as_formato) +" ";
	}
	
	/** 
	 * Recoge la fecha contenida en el Timestamp a_datetime y la convierte al 
	 * formato de salida formato
	 * @param Timestamp a_datetime Fecha de entrada
	 * @param String as_formato Formato en el que se devuelve la fecha
	 * @return String Cadena que contiene la fecha contenida en a_datetime con el formato as_formatoOUT
	 **/
	public static String getFecha(Timestamp a_datetime, String as_formato)
	throws Exception
	{
		 	if (a_datetime == null) return "";
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
			l_fecha.setTime(a_datetime);						
									
			String ls_dia = Util.lpad(Integer.toString(l_fecha.get(Calendar.DAY_OF_MONTH)), 2,'0');
			
			
			String ls_mes = Util.lpad(Integer.toString(1 + l_fecha.get(Calendar.MONTH)), 2,'0');
												
			String ls_anyo = Util.lpad(Integer.toString(l_fecha.get(Calendar.YEAR)), 4,'0');					
			
			String ls_hora24 = Util.lpad(Integer.toString(l_fecha.get(Calendar.HOUR_OF_DAY)), 2,'0');
			
			String ls_minuto = Util.lpad(Integer.toString(l_fecha.get(Calendar.MINUTE)), 2,'0');
			
			String ls_segundo = Util.lpad(Integer.toString(l_fecha.get(Calendar.SECOND)), 2,'0');			
			
			String ls_hora = Util.lpad(Integer.toString(l_fecha.get(Calendar.HOUR)), 2,'0');;
			
			//String ls_posMeridian = Integer.toString(l_fecha.get(Calendar.AM_PM));
			
			String ls_res = as_formato.toUpperCase(); 
			ls_res = Util.replace(ls_res, "YYYY", ls_anyo);
			ls_res = Util.replace(ls_res, "MM", ls_mes);
			ls_res = Util.replace(ls_res, "DD", ls_dia);
			ls_res = Util.replace(ls_res, "HH24", ls_hora24);
			ls_res = Util.replace(ls_res, "HH", ls_hora);
			ls_res = Util.replace(ls_res, "MI", ls_minuto);
			ls_res = Util.replace(ls_res, "SS", ls_segundo);
			return ls_res;
	}
	
	/** 
	 * Recoge la cadena as_fecha con el formato de entrada as_formatoIN y la convierte en un 
	 * Timestamp
	 * @param String as_fecha Fecha de entrada
	 * @param String as_formatoIN Formato en el que viene la fecha		 
	 * @return String Cadena que contiene la fecha contenida en as_fecha con el formato as_formatoOUT
	 **/
	public static Timestamp getFecha(String as_fecha, String as_formatoIN)
	throws Throwable
	{				
			if (as_fecha == null) return null;
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
	
			if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MI"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
				l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
				l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));
				l_fecha.set(Calendar.SECOND,0);												
				l_fecha.set(Calendar.MILLISECOND,0);
			}else if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MISS"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
				l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
				l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));												
				l_fecha.set(Calendar.SECOND, Integer.parseInt(as_fecha.substring(12,14)));																
				l_fecha.set(Calendar.MILLISECOND,0);
			}else if (as_formatoIN.equalsIgnoreCase("DD/MM/YYYY"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(0,2)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(3,5)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(6,10)));															
				l_fecha.set(Calendar.HOUR_OF_DAY, 0);
				l_fecha.set(Calendar.MINUTE, 0);												
				l_fecha.set(Calendar.SECOND, 0);																
				l_fecha.set(Calendar.MILLISECOND,0);
			}else {
				// Formato no soportado
				return null;		
			}
										
			Timestamp l_fec = new Timestamp(l_fecha.getTimeInMillis());
			return l_fec;				
	}
	
	  public static String lpad(String as_texto, int ai_longMinima, char ac_relleno)
	  {
	  	if (as_texto == null) return as_texto;
	  	if (as_texto.length() < ai_longMinima)
	  	for (int i=0; i< ai_longMinima - as_texto.length();i++)
	  	{
	  		as_texto = ac_relleno +as_texto;
	  	}
	  	return as_texto;
	  }
	  
	  /**
	   * Método usada para reemplazar todas las ocurrencias de determinada cadena de texto
	   * por otra cadena de texto
	   * @param String s de texto origen
	   * @param String one Fragmento de texto a reemplazar
	   * @param String another Fragmento de texto con el que se reemplaza
	   * @return String Cadena de texto con el reemplazo de cadenas completado
	   **/
	  public static String replace(String s, String one, String another)
	  throws Exception 
	  {
	  // En el String 's' sustituye 'one' por 'another'
	    if (s == null) 
	    {
	    	if (one == null && another != null)
	    	{
	    		return another; 
	    	}
	    	return null;
	    }
	     
	     
	   	
		if (s.length() == 0) 
		{
			if (one != null && one.length() == 0)
			{
				return another; 
			}
			return "";
		} 
		
		if (one == null || one.length()==0)
		{
			return s;
		}

		
		String res = "";
	    int i = s.indexOf(one,0);
	    int lastpos = 0;
	    while (i != -1) {
	      res += s.substring(lastpos,i) + another;
	      lastpos = i + one.length();
	      i = s.indexOf(one,lastpos);
	    }
	    res += s.substring(lastpos);  // the rest
	    return res;
	  }
	  
		public static String getNextDay(String today)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date fecha = sdf.parse(today);
				long ll_fecha = fecha.getTime();
				ll_fecha += DIA;
				fecha.setTime(ll_fecha);
				return sdf.format(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}

		}
		
		public static String convertDate(Date fecha)
		{
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMATO_FECHAS);
			return sdf.format(fecha);
		}
		
		public static Map sortByValue(Map map){ 
			List list = new LinkedList(map.entrySet()); 
			Collections.sort(list, new Comparator() { 
				public int compare(Object o1, Object o2) {
					return ((Comparable) ((Map.Entry) (o1)).getValue()) .compareTo(((Map.Entry) (o2)).getValue());
				}
			}
			);// logger.info(list); 
			Map result = new LinkedHashMap();
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry)it.next(); 
				result.put(entry.getKey(), entry.getValue()); 
			} 
			return result; 
		} 

		public static Map sortByKey(Map map){ 
			List list = new LinkedList(map.entrySet()); 
			Collections.sort(list, new Comparator() { 
				public int compare(Object o1, Object o2) {
					return ((Comparable) ((Map.Entry) (o1)).getKey()) .compareTo(((Map.Entry) (o2)).getKey());
				}
			}
			);// logger.info(list); 
			Map result = new LinkedHashMap();
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry)it.next(); 
				result.put(entry.getKey(), entry.getValue()); 
			} 
			return result; 
		} 
		
		
		public static Map concatena(Map map){
			Map result = new LinkedHashMap();
		    Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
			        Map.Entry pairs = (Map.Entry)it.next();
			        result.put(pairs.getKey(),pairs.getKey() + "-" + pairs.getValue());
			}
			return result;
		} 

		private static String version = null;	
		
		/**
		 * Obtiene version (en web.xml)
		 */
		public static String getVersion(){
			if (version == null) {
				try{
					InitialContext ic = new InitialContext();
					version = (String) ic.lookup("java:comp/env/release.cvs.tag");
				}catch(Exception ex){
					version = null;
				}		
			}
			return version;
		}
		
		public static String importeEnEuros(String importe){
			Float imp = Float.parseFloat(importe) / 100;
			Locale esp = new Locale("es", "ES");
	        NumberFormat formatter = NumberFormat.getCurrencyInstance(esp);
	        return formatter.format(imp);
		}

}
