package es.caib.audita.persistence.util.evento;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.model.AuditConstants;
import es.caib.audita.model.CuadroMandoDetalle;
import es.caib.audita.model.CuadroMandoTablaCruzada;
import es.caib.audita.model.DetalleOrganismo;
import es.caib.audita.model.LineaDetalle;
import es.caib.audita.model.TablaCruzadaModelo;
import es.caib.audita.model.TablaCruzadaOrganismo;
import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.audita.persistence.util.Util;
import es.indra.util.graficos.generadorGraficos.DatosGrafico;
import es.indra.util.graficos.generadorGraficos.Linea;

public class CuadroMandoDetalleHandler extends
		ParticularidadesVisualizacionHandlerImp
{
	protected static Log log = LogFactory.getLog(CuadroMandoDetalleHandler.class);
	
	protected static String JNDI_SISTRA = "";
	
	private static final String FICHERO_PROPIEDADES = "CuadroMandoDetalleHandler.properties";
	
	
	public CuadroMandoDetalle obtenerCuadroMandoDetalle() {
		this.getDateFinal();
		this.getDateInicial();
		String ls_tipo = this.getTipoEvento();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String ls_fechaInicio = dateFormat.format(this.getDateInicial());
		String ls_fechaFinal = null;
		CuadroMandoDetalle result =  null;
		Connection con = null;
		if(this.getDateFinal() != null)
		{
		   ls_fechaFinal = dateFormat.format(this.getDateFinal());
		}
		else
		{
			if(this.getModo().equals(AuditConstants.ANUAL))
			{
				int li_anyo = Integer.valueOf(ls_fechaInicio.substring(6,10)).intValue();
				ls_fechaFinal = "01/01/" + (li_anyo + 1);
			}
			else if(this.getModo().equals(AuditConstants.MENSUAL))
			{
				int li_mes = Integer.valueOf(ls_fechaInicio.substring(3,5)).intValue();
				int li_anyo = Integer.valueOf(ls_fechaInicio.substring(6,10)).intValue();
				if(li_mes == 12)
				{
					li_mes = 1;
					li_anyo++;
				}
				else
				{
					li_mes++;
				}
				ls_fechaFinal = "01/" + Util.DosCifras(li_mes) + "/" + li_anyo;
			}
			else
			{
			   ls_fechaFinal = Util.getNextDay(ls_fechaInicio);
			}
		}
		try
		{
			List lstResultConsulta;
			con = this.getConnection();

			lstResultConsulta = getListDetalleAudit(ls_tipo, ls_fechaInicio, ls_fechaFinal);

			result = createCuadroMandoDetalle(getIdioma(), lstResultConsulta);
			

			lstResultConsulta = this.queryForMapList( con, CuadroMandoDetalleHandler.class, "sql.select.tipoEvento" , new Object[]{ ls_tipo });
			Map mResult = ( Map ) lstResultConsulta.get(0);

			result.setTitulo((String) mResult.get( (getIdioma().equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "descripcion_ca" : "descripcion" ));

			result.setTemporal(createTituloTemporal());
			return result;
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
		return null;
	}
	
	protected List getListDetalleAudit(String tipo, String fechaInicio, String fechaFinal)
	{
		List lstResultConsulta;
		String ls_query = "sql.select.detalle.audit";
		Connection con = null;
		try
		{
			con = this.getConnection();
			if(fechaFinal == null)
			{
				ls_query += ".desde";
				lstResultConsulta = this.queryForMapList( con, CuadroMandoDetalleHandler.class, ls_query , new Object[]{ tipo, fechaInicio });
			}
			else
			{
				lstResultConsulta = this.queryForMapList( con, CuadroMandoDetalleHandler.class, ls_query , new Object[]{ tipo, fechaInicio, fechaFinal });
			}
			return lstResultConsulta;
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
		return null;
	}
	
	
	public DatosGrafico obtenerDatosGrafico()
	{
		return getDatosGrafico();
	}

	
	public CuadroMandoTablaCruzada obtenerCuadroMandoTablaCruzada(String tipoEvento,String modo,Date fecha, String idioma){
		return getCuadroMandoTablaCruzada(tipoEvento,modo,fecha, idioma);
	}
	
	private String createTituloTemporal()
	{
		String result = "";
		SimpleDateFormat dateFormat =  null;
		String ls_desde, ls_hasta, ls_anyo;
		if(getIdioma().equals(ConstantesAuditoria.LANGUAGE_CATALAN))
		{
			ls_desde = "Des de";
			ls_anyo = "Any";
			ls_hasta = "Fins";
		}
		else
		{
			ls_desde = "Desde";
			ls_anyo = "Año";
			ls_hasta = "Hasta";
		}
		if(AuditConstants.ANUAL.equals(this.getModo()))
		{
			dateFormat = new SimpleDateFormat("yyyy");
			result = ls_anyo + ": " + dateFormat.format(this.getDateInicial()); 
			if(this.getDateFinal() != null)
			{
				result += " " + ls_hasta + ": " + dateFormat.format(this.getDateFinal());
				result = ls_desde + " " + result;
			}
		}
		else if(AuditConstants.MENSUAL.equals(this.getModo()))
		{
			SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
			int mes = Integer.parseInt(dateFormatMonth.format(this.getDateInicial()));
			result = "Mes: " + Util.getDescripcionMesEntero(getIdioma(),mes) + " " + ls_anyo + ":"  + dateFormatYear.format(this.getDateInicial()); 
			if(this.getDateFinal() != null)
			{
				mes = Integer.parseInt(dateFormatMonth.format(this.getDateFinal()));
				result += " " + ls_hasta + " Mes: " + Util.getDescripcionMesEntero(getIdioma(),mes) + " " + ls_anyo + ":" + dateFormatYear.format(this.getDateFinal()); 
				result = ls_desde + " " + result;
			}
		}
		else if(AuditConstants.DIARIO.equals(this.getModo()))
		{
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			result = "Dia: " + dateFormat.format(this.getDateInicial()); 
			if(this.getDateFinal() != null)
			{
				result += " " + ls_hasta + ": " + dateFormat.format(this.getDateFinal());
				result = ls_desde + " " + result;
			}
		}
		return result;
	}
	
	protected CuadroMandoDetalle createCuadroMandoDetalle(String idioma, List lst)
	{
		CuadroMandoDetalle result = new CuadroMandoDetalle();
		Connection con = null;
		try{
			con = this.getConnection(getJndiSistra());
			List lista = this.queryForMapList(con, CuadroMandoDetalleHandler.class,"sql.select.detalle.modelo",new Object[]{ idioma});
			for(int i=0; i<lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				String modelo = (String)mResult.get("aud_modtra");
				Map mResultModelo = getDatosModelo(lista,modelo);
				String descripcion = "";
				String organismo = "";
				if(mResultModelo != null)
				{
					descripcion = (String) mResultModelo.get("descripcion");
					organismo = (String) mResultModelo.get("organismo");
				}
				if(!organismo.equals(""))
				{	
					DetalleOrganismo do_organismo = result.getOrganismo(organismo);
					if(do_organismo == null)
					{
						do_organismo = new DetalleOrganismo();
						do_organismo.setTitulo(organismo);
						result.add(do_organismo);
					}
					Number total = (Number) mResult.get("total");
					LineaDetalle ld = new LineaDetalle();
					ld.setTitulo(descripcion);
					ld.setTotal(String.valueOf(total.intValue()));
					do_organismo.addLineaDetalle(ld);
					do_organismo.increment(total.intValue());
					result.increment(total.intValue());
				}
			}

			return result;
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
			return result;
		}
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	
	protected Map getDatosModelo(List lista, String modelo)
	{
		for(int i=0; i<lista.size(); i++)
		{
			Map mResult = ( Map ) lista.get(i);
			
			String ls_modelo = (String) mResult.get("modelo");
			if(modelo.equals(ls_modelo))
			{
				return mResult;
			}
		}
		return null;

	}
	



	
//	public DatosGrafico getCuadroMando_Grafico(String as_opcion,String as_anyo,String as_mes, String as_dia,String as_elemento){

	
	private DatosGrafico getDatosGrafico()
	{
		int li_inicioCont=0;			// Inicio iteración 
		int li_finCont=0;				// Fin iteración	
		//					A(Año -> muestra meses año)
		//					M(Mes -> muestra dias mes)
		//					D(Día -> muestra horas dia)
		//					
		String ls_opcionConsulta=null;	// Opcion consulta: en funcion del tipo de grafico y opcion pasada como parametro 
		// establecemos opcion a consultar			
		String ls_txtopcion = null;		// Texto para el titulo que indica linea de tiempo
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ls_desde = sdf.format(this.getDateInicial());
		String as_anyo = ls_desde.substring(6,10);
		String as_mes = ls_desde.substring(3,5);
		int li_mes = Integer.parseInt(as_mes);
		int li_anyo = Integer.parseInt(as_anyo);
		String as_dia = ls_desde.substring(0,2);
		List lines = new ArrayList();
		Linea linea = new Linea();
//		linea.setColor(Color.RED);
		String escalaTiempo = "";
		
		
		
		// Establecemos limites contador,tipo gráfico y opcion consulta			
		if (this.getModo().equals("mensual")){
			// Mes -> - Contador desde dia 1 hasta ultimo dia mes
			ls_opcionConsulta = "D";
			li_inicioCont = 1;
			li_finCont=Util.UltimoDia(as_anyo,as_mes);
			ls_txtopcion="Día";
			escalaTiempo = DatosGrafico.DIAS;
		}else if (this.getModo().equals("diario"))
		{
			// Dia -> - Contador desde hora 0 hasta hora 23
			li_inicioCont = 0;
			li_finCont=23;		
			ls_opcionConsulta = "DH";
			ls_txtopcion="Hora";
			escalaTiempo = DatosGrafico.HORAS;
		}else if (this.getModo().equals("anual")){  
			// Año -> - Contador desde Enero del año pasado 
			ls_opcionConsulta = "M";
			li_inicioCont = 1;
			li_finCont = 12;	
			ls_txtopcion="Mes";
			escalaTiempo = DatosGrafico.MESES;

		}else if (this.getModo().equals("inicio")){  
			// Año -> - Contador desde Mes año pasado hasta Mes indicado 
			ls_opcionConsulta = "MA";
			li_inicioCont = 0;
			li_finCont = 12;	
			ls_txtopcion="Mes";
			escalaTiempo = DatosGrafico.MESES;

		}else {
			return null;
		}
		linea.setTitulo("Número de elementos por " + ls_txtopcion);

		Connection con = null;
		try{
			con = this.getConnection();
			
			// Realizamos las iteraciones oportunas para calcular los datos
			String ls_anyoConsulta=null;String ls_mesConsulta=null;String ls_diaConsulta=null;String ls_horaConsulta=null;
			for (int i=li_inicioCont;i<=li_finCont;i++){
				
				// Reseteamos parametros consulta
				ls_anyoConsulta=null;ls_mesConsulta=null;ls_diaConsulta=null;ls_horaConsulta=null;
				
				// Establecemos parametros consulta
				if (ls_opcionConsulta.equals("A")){									
					ls_anyoConsulta=Integer.toString(i);																			
				}else if (ls_opcionConsulta.equals("M")){
					ls_anyoConsulta=as_anyo;
					ls_mesConsulta= Util.DosCifras(i);
				}else if (ls_opcionConsulta.equals("MA")){ // Meses el Ultimo Año
				   	if (li_mes + i > 12)
				   	{
						ls_mesConsulta= String.valueOf(Util.DosCifras(li_mes + i - 12));
						ls_anyoConsulta=String.valueOf(li_anyo);
				   	}
				   	else
				   	{
						ls_mesConsulta= String.valueOf(Util.DosCifras(li_mes + i));
						ls_anyoConsulta=String.valueOf(li_anyo - 1);
				   	}
				}else if (ls_opcionConsulta.equals("D")){
					ls_anyoConsulta=as_anyo;
					ls_mesConsulta= as_mes;
					ls_diaConsulta=Util.DosCifras(i);			
				}else if (ls_opcionConsulta.equals("DH")){
					ls_anyoConsulta=as_anyo;
					ls_mesConsulta=as_mes;
					ls_diaConsulta=as_dia;
					ls_horaConsulta=Util.DosCifras(i);
				}else if (ls_opcionConsulta.equals("MH")){					
					ls_anyoConsulta=as_anyo;
					ls_mesConsulta=as_mes;					
					ls_horaConsulta=Util.DosCifras(i);
				}else if (ls_opcionConsulta.equals("AH")){
					ls_anyoConsulta=as_anyo;									
					ls_horaConsulta=Util.DosCifras(i);
				}else{
					return null;					
				}
				String ls_datos = "0";
				String ls_fecha = getFecha(ls_horaConsulta,ls_diaConsulta,ls_mesConsulta,ls_anyoConsulta);
				String ls_sqlFecha = getCuadroMandoSqlFecha("AUD_FECHA",ls_opcionConsulta, ls_anyoConsulta, ls_mesConsulta, ls_diaConsulta, ls_horaConsulta, null);
				String ls_sql = "select  count(*)  total from aud_audit where (aud_audit.aud_result = 'S') and aud_audit.aud_tipo = ? " + ls_sqlFecha;
				List lst = null;
				lst = this.queryForMapListConstructed(con, ls_sql,  new Object[]{ this.getTipoEvento() });
				Map mResult = ( Map ) lst.get(0);
				Number total = ( Number ) mResult.get( "total" );
				ls_datos = total.toString();
				// Añadimos dato a vector de valores				
				linea.addValor(ls_fecha,ls_datos);
			}
			
			
			// Generamos Datos Grafico
			DatosGrafico l_datosGrafico = new DatosGrafico();
			try {
				l_datosGrafico.setTipoGrafico(DatosGrafico.TIEMPO);
				l_datosGrafico.setEscalaTiempo(escalaTiempo);
				l_datosGrafico.setTituloGrafico("");
			} catch (Exception e) {
				
				e.printStackTrace();
				return null;
			}	
			
			
			lines.add(linea);
			l_datosGrafico.setLines((ArrayList) lines);
			return l_datosGrafico;	
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		/*
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		*/
	}
	
	private String getFecha(String horaConsulta,String diaConsulta, String mesConsulta,String anyoConsulta)
	{
		String result = "";
		String minutosSegundos = "0000";
		result += anyoConsulta;
		if(mesConsulta == null)
		{
			result += "010100" + minutosSegundos; // Ponemos las 00:00 horas del dia 01 de Enero
			return result;
		}
		if(diaConsulta == null)
		{
			result += mesConsulta + "0100" + minutosSegundos; // Ponemos las 00:00 horas del dia 01 
			return result;
		}
		if(horaConsulta == null)
		{
			result += mesConsulta + diaConsulta + "00" + minutosSegundos; // Ponemos las 00:00 horas del dia 01 
			return result;
		}
		return result += mesConsulta + diaConsulta + horaConsulta + minutosSegundos;
	}
	
	private String getCuadroMandoSqlFecha(String as_campoSQL,String as_opcion,String as_anyo,String as_mes,String as_dia,String as_hora,String as_hasta){
		String ls_sql = "";
		
		if (as_hasta != null && as_hasta.length() <= 0) as_hasta = null;
		
		// Modo Año				
		if (as_opcion.equals("A")){
			if (as_anyo == null) {
				return null;
			}
			if (!as_anyo.equals("T")){
				if (as_hasta == null)
					ls_sql = " AND TO_CHAR(" + as_campoSQL + ",'YYYY') = '" + as_anyo + "'";
				else					
					ls_sql = " AND TO_NUMBER(TO_CHAR(" + as_campoSQL + ",'YYYY')) >= " + as_anyo + 
							 " AND TO_NUMBER(TO_CHAR(" + as_campoSQL + ",'YYYY')) <= " + as_hasta ;
			}
			return ls_sql;
		}
		
		// Modo Mes
		if (as_opcion.equals("M") || as_opcion.equals("MA")){
			if (as_anyo == null) {
				return null;
			}
			if (as_mes == null) {
				return null;
			}
			if (as_hasta == null)
				ls_sql =" AND TO_CHAR(" + as_campoSQL + ",'MM/YYYY') = '" + as_mes + "/" + as_anyo + "'";
			else{
				String ls_mesH =  as_hasta.substring(0,2);
				String ls_anyoH = as_hasta.substring(3,7);
								
				if (ls_mesH.equals("12")) {
					ls_mesH = "01"; ls_anyoH = Integer.toString(Integer.parseInt(ls_anyoH) + 1);
				}else{
					ls_mesH = Integer.toString(Integer.parseInt(ls_mesH) + 1);
					if (ls_mesH.length() == 1) ls_mesH = "0" + ls_mesH;
				}
								
				ls_sql = " AND TO_DATE(TO_CHAR(" + as_campoSQL + ",'DD/MM/YYYY'),'DD/MM/YYYY') >= TO_DATE('01/" + as_mes + "/" + as_anyo + "','DD/MM/YYYY') " +
						 " AND TO_DATE(TO_CHAR(" + as_campoSQL + ",'DD/MM/YYYY'),'DD/MM/YYYY') <  TO_DATE('01/" + ls_mesH+ "/" + ls_anyoH+ "','DD/MM/YYYY') " ;	
			}
			return ls_sql;
		}
		
		// Modo Dia
		if (as_opcion.equals("D")){
			if (as_dia == null) {
				return null;
			}	
			if (as_hasta == null)
				ls_sql = " AND TO_CHAR(" + as_campoSQL + ",'DD/MM/YYYY') = '" + as_dia + "/" + as_mes + "/" + as_anyo + "'";
			else
				ls_sql = " AND TO_DATE(TO_CHAR(" + as_campoSQL + ",'DD/MM/YYYY'),'DD/MM/YYYY') >= TO_DATE('" + as_dia + "/" + as_mes + "/" + as_anyo +  "','DD/MM/YYYY') " +
				 		 " AND TO_DATE(TO_CHAR(" + as_campoSQL + ",'DD/MM/YYYY'),'DD/MM/YYYY') <= TO_DATE('" + as_hasta + "','DD/MM/YYYY') " ;	
					
			return ls_sql;
		}
		
		// Modo DiaHora
		if (as_opcion.equals("DH")){
			if ( (as_dia == null) || (as_hora == null)){
				return null;
			}	
			ls_sql = " AND TO_CHAR(" + as_campoSQL + ",'DD/MM/YYYY HH24') = '" + as_dia + "/" + as_mes + "/" + as_anyo + " " + as_hora + "'";	
			return ls_sql;
		}	
		
		// Modo MesHora
		if (as_opcion.equals("MH")){
			if ( (as_anyo == null) || (as_hora == null) || (as_mes == null)){
				return null;
			}	
			ls_sql = " AND TO_CHAR(" + as_campoSQL + ",'MM/YYYY HH24') = '" + as_mes + "/" + as_anyo + " " + as_hora + "'";	
			return ls_sql;
		}
		
		// Modo AñoHora
		if (as_opcion.equals("AH")){
			if ( (as_anyo == null) || (as_hora == null)){
				return null;
			}	
			if (!as_anyo.equals("T")){
				ls_sql = " AND TO_CHAR(" + as_campoSQL + ",'YYYY HH24') = '" + as_anyo + " " + as_hora + "'";
			}else{
				ls_sql = " AND TO_CHAR(" + as_campoSQL + ",'HH24') = '" + as_hora + "'";
			}
			return ls_sql;
		}
		
		return null;
		
	}
	
	protected String getJndiSistra()
	{
		if(JNDI_SISTRA.equals(""))
		{
			Properties props = new Properties();
			try {
				props.load(this.getClass().getResourceAsStream(FICHERO_PROPIEDADES));
				JNDI_SISTRA = "java:/" + props.getProperty("datasource.sistra");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return JNDI_SISTRA;
	}
	
	public CuadroMandoTablaCruzada getCuadroMandoTablaCruzada(String tipoEvento,String modo,Date fecha, String idioma)
	{
		CuadroMandoTablaCruzada cuadroMando = null;
		
		String ls_qry_tipo = "";
		String ls_tituloCuadroMando = "";
		if(AuditConstants.ANUAL.equals(modo))
		{
			ls_tituloCuadroMando = (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "Estadístiques anuals" : "Estadísticas anuales";
			ls_qry_tipo = "anual";
		}
		else if(AuditConstants.MENSUAL.equals(modo))
		{
			ls_tituloCuadroMando = (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "Estadístiques mensuals" : "Estadísticas mensuales";
			ls_qry_tipo = "mensual";
		}
		List lstResultConsulta;
		String ls_query = "sql.select.tablaCruzada." + ls_qry_tipo;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ls_desde = sdf.format(fecha);
		String ls_hasta = getFechaHasta(modo,fecha);
		Connection con = null;
		try {
			
			con = this.getConnection();
			lstResultConsulta = this.queryForMapList( con, CuadroMandoDetalleHandler.class, ls_query , new Object[]{ tipoEvento, ls_desde, ls_hasta });
			cuadroMando = construyeCuadroMandoTablaCruzada(idioma, lstResultConsulta, modo);
			cuadroMando.setTitulo(ls_tituloCuadroMando);
			return cuadroMando;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return cuadroMando;
		}
		/*
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		*/
	}
	
	private CuadroMandoTablaCruzada construyeCuadroMandoTablaCruzada(String idioma, List lst, String modo)
	{
		CuadroMandoTablaCruzada result = new CuadroMandoTablaCruzada();
		
		List totales = initTotales(modo);

		result.setTotales(totales);
		
		Connection con = null;
		try{
			con = this.getConnection(getJndiSistra());
			List lista = this.queryForMapList(con, CuadroMandoDetalleHandler.class,"sql.select.detalle.modelo",new Object[]{ idioma});
			for(int i=0; i<lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				String modelo = (String)mResult.get("modelo");
				Map mResultModelo = getDatosModelo(lista,modelo);
				String descripcion = "";
				String organismo = "";
				if(mResultModelo != null)
				{
					descripcion = (String) mResultModelo.get("descripcion");
					organismo = (String) mResultModelo.get("organismo");
				}
				if(!organismo.equals(""))
				{	
					TablaCruzadaOrganismo tco = result.getOrganismo(organismo);
					if(tco == null)
					{
						totales = initTotales(modo);
						tco = new TablaCruzadaOrganismo();
						tco.setDescripcion(organismo);
						tco.setTotales(totales);
						result.addOrganismo(tco);
					}
					TablaCruzadaModelo tcm = tco.getModelo(descripcion);
					if(tcm == null)
					{
						totales = initTotales(modo);
						tcm = new TablaCruzadaModelo();
						tcm.setDescripcion(descripcion);
						tcm.setTotales(totales);
						tco.addModelo(tcm);
						
					}
					String key = (String) mResult.get("key");
					if(AuditConstants.ANUAL.equals(modo))
					{
						key = Util.getDescripcionMes(getIdioma(), key);
					}
					Number total = (Number) mResult.get("total");
					
					{
						int i_total = total.intValue();
						tcm.increment(key,i_total);
						tcm.increment(i_total);
						tco.increment(key,i_total);
						tco.increment(i_total);
						result.increment(i_total);
						result.increment(key,i_total);
					}
				}
			}

			return result;
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
			return result;
		}
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private List initTotales(String modo)
	{
		List result = new ArrayList();
		if(AuditConstants.ANUAL.equals(modo))
		{
			for(int i=0; i<12; i++)
			{
				Map map = new HashMap();
				map.put(Util.getDescripcionMes(getIdioma(), i+1), "0");
				result.add(map);
			}
		}else if(AuditConstants.MENSUAL.equals(modo))
		{
			for(int i=1; i<=31; i++)
			{
				Map map = new HashMap();
				map.put(Util.DosCifras(i), "0");
				result.add(map);
			}
		}
			
		return result;
	}
	
	private String getFechaHasta(String modo, Date fecha)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		String ls_fecha = sdf.format(fecha);
		int anyo = Integer.parseInt(ls_fecha.substring(3,7));
		int mes = Integer.parseInt(ls_fecha.substring(0,2));
		if(AuditConstants.ANUAL.equals(modo))
		{
			return "01/01/" + (anyo + 1);
		}else if(AuditConstants.MENSUAL.equals(modo))
		{
			if(mes == 12)
			{
				mes = 1;
				anyo++;
			}
			else
			{
				mes++;
			}
			return "01/" + Util.DosCifras(mes) + "/" + anyo;
		}
		
		return "";
	}
	




}
