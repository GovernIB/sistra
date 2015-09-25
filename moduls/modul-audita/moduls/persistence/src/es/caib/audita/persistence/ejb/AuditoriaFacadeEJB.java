package es.caib.audita.persistence.ejb;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.CreateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.model.AuditConstants;
import es.caib.audita.model.CuadroMandoDetalle;
import es.caib.audita.model.CuadroMandoInicio;
import es.caib.audita.model.CuadroMandoTablaCruzada;
import es.caib.audita.model.EventoAuditado;
import es.caib.audita.model.InicioPortal;
import es.caib.audita.model.InicioTramitacion;
import es.caib.audita.model.LineaDetalleAccedidos;
import es.caib.audita.model.LineaDetalleTramitados;
import es.caib.audita.model.LineaDetalleUltimos;
import es.caib.audita.model.Modulo;
import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.delegate.CuadroMandoInicioDelegate;
import es.caib.audita.persistence.delegate.DelegateException;
import es.caib.audita.persistence.delegate.DelegateUtil;
import es.caib.audita.persistence.util.Util;
import es.caib.audita.persistence.util.evento.ParticularidadesVisualizacionFactoria;
import es.caib.audita.persistence.util.evento.ParticularidadesVisualizacionHandler;
import es.indra.util.graficos.generadorGraficos.DatosGrafico;

/**
 * Stateless SessionBean que proporciona el interfaz al front de la aplicacion de auditoria
 * del sistema de tramitación para acceder a las estadísticas de cada uno de los eventos 
 *
 * @ejb.bean
 *  name="audita/persistence/AuditoriaFacade"
 *  jndi-name="es.caib.audita.persistence.AuditoriaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *  
 * @ejb.transaction type="Required"
 * 
 */
public abstract class AuditoriaFacadeEJB extends QueryEJB
{
	
//    protected static String JNDI_SISTRA = "";
	
//    protected static String JNDI_ROLSAC = "";

//    private static final String FICHERO_PROPIEDADES = "Auditoria.properties";
	
	private Log log = LogFactory.getLog( AuditoriaFacadeEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true" 
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
	private List obtenerListaTiposEvento(String modulo)
	{
		List lst = null;
		try
		{
			lst = this.queryForMapList( "sql.select.eventos", new Object[]{ modulo } );
		}
		catch( Exception exc )
		{			
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		return lst;
	}

	private List obtenerListaModulos()
	{
		List lst = null;
		try
		{
			lst = this.queryForMapList( "sql.select.modulos");
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		return lst;
	}
	

	private EventoAuditado construyeAuditoriaTipoEvento(String idioma,  Map resultadoConsulta , String modo, Date fechaInicio, Date fechaFinal )
	{
		EventoAuditado eventoAuditado = new EventoAuditado();
		String ls_tipo = (String) resultadoConsulta.get("tip_tipo");
		eventoAuditado.setTipo(ls_tipo);
		String ls_descripcion = ( String ) resultadoConsulta.get( (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "tip_descca" : "tip_desc" );

		eventoAuditado.setDescripcion(ls_descripcion);
		String ls_ayuda = ( String ) resultadoConsulta.get( (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "tip_ayudac" : "tip_ayuda" );
		eventoAuditado.setAyuda(ls_ayuda);
		Number orden = ( Number ) resultadoConsulta.get( "tip_orden" );
		eventoAuditado.setOrden(orden.intValue());
		String ls_opciones = (String) resultadoConsulta.get("tip_prpcls");
		if(ls_opciones == null)
		{
			return eventoAuditado;
		}
		eventoAuditado.setOpcionesVisualizacion(ls_opciones);
		int index = ls_opciones.indexOf(AuditConstants.TOTAL);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String ls_fechaInicio = dateFormat.format(fechaInicio);
		String ls_fechaFinal = "";
		if(fechaFinal != null)
		{
			ls_fechaFinal = dateFormat.format(fechaFinal);
		}
		
		String ls_query = "";
		
		/* Obtenemos estadisticas total */
		
		if(index != -1)
		{
			List lst = null;
			try
			{
				ls_query = "sql.select.total";
				if(fechaFinal == null)
				{
					lst = this.queryForMapList( ls_query + ".desde", new Object[]{ ls_tipo, ls_fechaInicio } );
				}
				else
				{
					lst = this.queryForMapList( ls_query, new Object[]{ ls_tipo, ls_fechaInicio, ls_fechaFinal } );
				}
				Map mResult = ( Map ) lst.get(0);
				Number total = ( Number ) mResult.get( "total" );
				eventoAuditado.setTotal(total.intValue());
			}
			catch( Exception exc )
			{
				log.error("Excepcion: " + exc.getMessage(), exc);
			}
		}
		
		/* Obtenemos estadisticas por idiomas */
		
		
		index = ls_opciones.indexOf(AuditConstants.TOTAL_IDIOMAS);
		if(index != -1)
		{
			List lst = null;
			try
			{
				LinkedHashMap totalIdioma = new LinkedHashMap();
				ls_query = "sql.select.idiomas";
				if(fechaFinal == null)
				{
					ls_query += ".desde";
					lst = this.queryForMapList( ls_query , new Object[]{ ls_tipo, ls_fechaInicio } );
				}
				else
				{
					lst = this.queryForMapList( ls_query, new Object[]{ ls_tipo, ls_fechaInicio, ls_fechaFinal } );
				}
				for(int i=0; i<lst.size(); i++)
				{
					Map mResult = ( Map ) lst.get(i);
					String ls_idioma = ( String ) mResult.get( "idioma" );
					Number total = ( Number ) mResult.get( "total" );
					totalIdioma.put(ls_idioma,total);
				}
				eventoAuditado.setTotalesIdioma(totalIdioma);
				
			}
			catch( Exception exc )
			{
				log.error("Excepcion: " + exc.getMessage(), exc);
			}
		}
		

		/* Obtenemos estadisticas por nivel de autenticación */
		
		
		index = ls_opciones.indexOf(AuditConstants.TOTAL_NIVEL_AUTH);
		if(index != -1)
		{
			List lst = null;
			try
			{
				LinkedHashMap totalNivelAutenticacion = new LinkedHashMap();
				ls_query = "sql.select.nivel";
				if(fechaFinal == null)
				{
					ls_query += ".desde";
					lst = this.queryForMapList( ls_query, new Object[]{ ls_tipo, ls_fechaInicio } );
				}
				else
				{
					lst = this.queryForMapList( ls_query, new Object[]{ ls_tipo, ls_fechaInicio, ls_fechaFinal } );
				}
				// Anonimo
				totalNivelAutenticacion.put(String.valueOf(AuditConstants.MODO_AUTENTICACION_ANONIMO),
						                    getTotal(lst,String.valueOf(AuditConstants.MODO_AUTENTICACION_ANONIMO)));
				// Certificado
				totalNivelAutenticacion.put(String.valueOf(AuditConstants.MODO_AUTENTICACION_CERTIFICADO),
	                    getTotal(lst,String.valueOf(AuditConstants.MODO_AUTENTICACION_CERTIFICADO)));
				// Usuario
				totalNivelAutenticacion.put(String.valueOf(AuditConstants.MODO_AUTENTICACION_USUARIO),
	                    getTotal(lst,String.valueOf(AuditConstants.MODO_AUTENTICACION_USUARIO)));
				/*
				for(int i=0; i<lst.size(); i++)
				{
					Map mResult = ( Map ) lst.get(i);
					String ls_nivel = ( String ) mResult.get( "nivel" );
					Number total = ( Number ) mResult.get( "total" );
					totalNivelAutenticacion.put(ls_nivel.trim(),total);
				}
				*/
				eventoAuditado.setTotalesNivelAutenticacion(totalNivelAutenticacion);
				
			}
			catch( Exception exc )
			{
				log.error("Excepcion: " + exc.getMessage(), exc);
			}
		}
		
		

		/* Obtenemos estadisticas evento especial */
		
		
		index = ls_opciones.indexOf(AuditConstants.PARTICULARIDADES_VISUALIZACION);
		if(index != -1)
		{
			String ls_handler = (String) resultadoConsulta.get("tip_handlr");
			if((ls_handler != null) && (!ls_handler.equals("")))
			{
				Connection con = null;
				try
				{
					con = this.getConnection();
					ParticularidadesVisualizacionHandler handler = ParticularidadesVisualizacionFactoria.getInstance().getHandler( ls_handler, con, modo, ls_tipo, fechaInicio, fechaFinal, idioma);
					eventoAuditado.setCodigoVisualizacionParticular( handler.obtenerCodigoVisualizacion() );
					return eventoAuditado;
				}
				catch( Exception exc )
				{
					log.error("Excepcion: " + exc.getMessage(), exc);
				}
				finally
				{
					if(con != null)
					{
						try {
							con.close();
						} catch (SQLException e) {
							log.error("Excepcion: " + e.getMessage(), e);
						}
					}
				}

			}
		}
		return eventoAuditado;



	}


	
	/**
	 * Obtiene el cuadro de mando para el intervalo temporal contenido entre dos fechas.
	 * Devuelve una lista List de objetos de tipo es.caib.audita.model.Modulo
	 */
	protected List getCuadroMandoIntervaloTemporal( String idioma, String modo, Date fechaInicio, Date fechaFinal )
	{
		List lstReturn = new ArrayList();
		List eventosAuditados = null;
		Modulo modulo;
		List modulos = obtenerListaModulos();
		
		// 1º Recuperamos los modulos y se iteran
		//Modulo modulo1 = new Modulo();
		
		//List lstModulos = this.queryForMapList( "query" );
		// 2º Obtenemos para cada modulo los tipos de evento
		
		for(int i=0; i<modulos.size(); i++)
		{
			eventosAuditados = new ArrayList();
			modulo = new Modulo(); 
			Map mResult = ( Map ) modulos.get(i);
			String ls_nombre = ( String ) mResult.get( "mod_modul" );
			modulo.setModulo(ls_nombre);
			Number orden =   (Number) mResult.get( "mod_orden" );
			modulo.setOrden(orden.intValue());
			String ls_descripcion = ( String ) mResult.get( (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "mod_descca" : "mod_desc" );
			modulo.setDescripcion(ls_descripcion);
			
			// Recuperamos tipos de evento
			List lstTiposEventos = this.obtenerListaTiposEvento(ls_nombre);
			for(int j=0; j<lstTiposEventos.size();j++)
			{
				Map mEvento = (Map) lstTiposEventos.get(j);
				
				// Si el evento no tiene propiedades de visualizacion no se muestra
				String ls_opciones = (String) mEvento.get("tip_prpcls");
				if (ls_opciones==null||ls_opciones.equals("")) continue;
				
				EventoAuditado evento = construyeAuditoriaTipoEvento(idioma, mEvento, modo, fechaInicio,fechaFinal);
				eventosAuditados.add(evento);
			}

			Collections.sort(eventosAuditados, 
	    		    		 new Comparator(){
	            				public int compare(Object o1, Object o2)
	            				{
	            					EventoAuditado e1 = (EventoAuditado) o1;
	            					EventoAuditado e2 = (EventoAuditado) o2;
	            					return e1.getOrden()- e2.getOrden();
	            				}});

			modulo.setEventosAuditados(eventosAuditados);
			lstReturn.add(modulo);
		}

		Collections.sort(lstReturn, 
	    		 new Comparator(){
   				public int compare(Object o1, Object o2)
   				{
   					Modulo m1 = (Modulo) o1;
   					Modulo m2 = (Modulo) o2;
   					return m1.getOrden()- m2.getOrden();
   				}});
		
		return lstReturn;

	}
	
	private BigDecimal getTotal(List lista, String tipo)
	{
		for(int i=0; i<lista.size(); i++)
		{
			Map mResult = ( Map ) lista.get(i);
			String clave = ( String ) mResult.get( "nivel" );
			if(clave.equals(tipo))
			{
				Number total = (Number) mResult.get( "total" );
				return new BigDecimal(total.longValue());
			}
		}
		return BigDecimal.valueOf(0);
	}
	

	
	
//	protected String getJndiSistra()
//	{
//		if(JNDI_SISTRA.equals(""))
//		{
//			Properties props = new Properties();
//			try {
//				props.load(this.getClass().getResourceAsStream(FICHERO_PROPIEDADES));
//				AuditoriaFacadeEJB.JNDI_SISTRA = "java:/" + props.getProperty("datasource.sistra");
//			} catch (IOException e) {
//				log.error("Excepcion: " + e.getMessage(), e);
//			}
//
//		}
//		return AuditoriaFacadeEJB.JNDI_SISTRA;
//	}
//	
	
//	protected String getJndiRolsac()
//	{
//		if(JNDI_ROLSAC.equals(""))
//		{
//			Properties props = new Properties();
//			try {
//				props.load(this.getClass().getResourceAsStream(FICHERO_PROPIEDADES));
//				AuditoriaFacadeEJB.JNDI_ROLSAC = "java:/" + props.getProperty("datasource.rolsac");
//			} catch (IOException e) {
//				log.error("Excepcion: " + e.getMessage(), e);
//			}
//
//		}
//		return AuditoriaFacadeEJB.JNDI_ROLSAC;
//	}
	

//----------------------------------------------------------------
//----------- METODOS EJB ----------------------------------------
//----------------------------------------------------------------
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public List obtenerListaEventosGrafico(String idioma)
	{
		List eventos = new ArrayList();
		List lst = null;
		try
		{
			lst = this.queryForMapList( "sql.select.tiposEventos.grafico");
			for(int i=0; i<lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				String ls_tipo = ( String ) mResult.get( "tipo" );
				String ls_desc = ( String ) mResult.get( (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "descripcion_ca" : "descripcion" );
				HashMap map = new HashMap();
				map.put(ls_tipo,ls_desc);
				eventos.add(map);
			}
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		return eventos;
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public DatosGrafico obtenerDatosGrafico(String tipoEvento,Date fecha,String modo)
	{
		String ls_handler = "es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler";
		DatosGrafico datos = null;
		Connection con = null;
		try
		{
			con = this.getConnection();
			ParticularidadesVisualizacionHandler handler = 
				ParticularidadesVisualizacionFactoria.getInstance().getHandler( ls_handler, con, modo, tipoEvento, fecha, null, null );
			datos = handler.obtenerDatosGrafico();
			return datos;
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					log.error("Excepcion: " + e.getMessage(), e);
				}
			}
		}

		return datos;
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public List obtenerCuadroMandoIntervaloTemporal(String idioma, int anyoInicio, int anyoFinal )
	{
		// Nos protegemos de la primera vez que se llama
		if (anyoInicio == 0)
		{
			Calendar fecha = Calendar.getInstance();
		    anyoInicio = fecha.get(Calendar.YEAR);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date fechaInicio = null;
		Date fechaFinal = null;
		try {
			fechaInicio = dateFormat.parse(Integer.toString(anyoInicio));
			if(anyoFinal != 0)
			{
				fechaFinal = dateFormat.parse(Integer.toString(anyoFinal));
			}
		} catch (ParseException e) {
			log.error("Excepcion: " + e.getMessage(), e);
		}
		return getCuadroMandoIntervaloTemporal(idioma, AuditConstants.ANUAL, fechaInicio,fechaFinal);

	}
	
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public List obtenerCuadroMandoIntervaloTemporal( String idioma, int anyoInicio, int mesInicio, int anyoFinal, int mesFinal )
	{
		// Nos protegemos de la primera vez que se llama
		if ((anyoInicio == 0) || (mesInicio == 0))
		{
			Calendar fecha = Calendar.getInstance();
		    anyoInicio = fecha.get(Calendar.YEAR);
		    mesInicio = fecha.get(Calendar.MONTH) + 1;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
		Date fechaInicio = null;
		Date fechaFinal = null;
		try {
			fechaInicio = dateFormat.parse(mesInicio + "/" + anyoInicio);
			if((anyoFinal != 0) && (mesFinal != 0))
			{
			   fechaFinal = dateFormat.parse(mesFinal + "/" + anyoFinal);
			}
		} catch (ParseException e) {
			log.error("Excepcion: " + e.getMessage(), e);
		}
		return getCuadroMandoIntervaloTemporal(idioma, AuditConstants.MENSUAL, fechaInicio,fechaFinal);
	}
	
	/**
	 * Obtiene el cuadro de mando para el intervalo temporal contenido entre dos fechas.
	 * Devuelve una lista List de objetos de tipo es.caib.audita.model.Modulo
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public List obtenerCuadroMandoIntervaloTemporal(String idioma, Date fechaInicio, Date fechaFinal )
	{
		return getCuadroMandoIntervaloTemporal(idioma, AuditConstants.DIARIO, fechaInicio,fechaFinal);
	}
	
	private String obtenerHandler(String evento)
	{
		String handler = null;
		List lst = null;
		

	//	Connection con = null;
		try
		{
//			con = this.getConnection();
			lst = this.queryForMapList( "sql.select.handler", new Object[]{ evento } );
			Map mResult = ( Map ) lst.get(0);
			handler = ( String ) mResult.get( "handler" );
			return handler;
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
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
					
					log.error("Excepcion: " + e.getMessage(), e);
				}
			}
		}
		*/
	}
	
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public CuadroMandoDetalle obtenerCuadroMandoDetalle(String idioma, String tipoEvento,String modo,Date fechaInicio, Date fechaFinal )
	{
		String ls_handler = obtenerHandler(tipoEvento);
		if((ls_handler == null) || (ls_handler.equals("")))
		{
			return null;
		}
		Connection con = null;
		try
		{
			con = this.getConnection();
			ParticularidadesVisualizacionHandler handler = ParticularidadesVisualizacionFactoria.getInstance().getHandler( ls_handler, con, modo, tipoEvento, fechaInicio, fechaFinal, idioma );
			return handler.obtenerCuadroMandoDetalle();
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					log.error("Excepcion: " + e.getMessage(), e);
				}
			}
		}

		return null;
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public CuadroMandoTablaCruzada obtenerCuadroMandoTablaCruzada(String idioma, String tipoEvento,String modo,Date fecha)
	{
		String ls_handler = obtenerHandler(tipoEvento);
		if((ls_handler == null) || (ls_handler.equals("")))
		{
			return null;
		}
		Connection con = null;
		try
		{
			con = this.getConnection();
			ParticularidadesVisualizacionHandler handler = ParticularidadesVisualizacionFactoria.getInstance().getHandler( ls_handler, con, modo, tipoEvento, fecha, null, idioma );
			return handler.obtenerCuadroMandoTablaCruzada(tipoEvento,modo,fecha, idioma);
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					log.error("Excepcion: " + e.getMessage(), e);
				}
			}
		}

		return null;
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public List obtenerListaEventos(String idioma)
	{
		List eventos = new ArrayList();
		List lst = null;
		try
		{
			lst = this.queryForMapList( "sql.select.tiposEventos");
			for(int i=0; i<lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				String ls_tipo = ( String ) mResult.get( "tipo" );
				String ls_desc = ( String ) mResult.get( (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "descripcion_ca" : "descripcion" );
				HashMap map = new HashMap();
				map.put(ls_tipo,ls_desc);
				eventos.add(map);
			}
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		return eventos;
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public List obtenerListaEventosDetalle(String idioma)
	{
		List eventos = new ArrayList();
		List lst = null;
		try
		{
			lst = this.queryForMapList( "sql.select.tiposEventos.detalle");
			for(int i=0; i<lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				String ls_tipo = ( String ) mResult.get( "tipo" );
				String ls_desc = ( String ) mResult.get( (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "descripcion_ca" : "descripcion" );
				HashMap map = new HashMap();
				map.put(ls_tipo,ls_desc);
				eventos.add(map);
			}
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		return eventos;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public void generaCuadroMandoInicio()
	{
		// Buscamos fechas para historial
		String ls_fcHasta = null;
		String ls_fcDesde = null;
		try {
			ls_fcHasta = Util.getFechaActual("DD/MM/YYYY");
			ls_fcDesde = ls_fcHasta.substring(0,6) + (Integer.parseInt(ls_fcHasta.substring(6).trim()) - 1);			
		} catch (Throwable e) {
			log.error("Error obteniendo intervalo de fechas",e);
			log.error("Excepcion: " + e.getMessage(), e);
		}			

		log.info("Comienza generar cuadro mando inicio...");
		CuadroMandoInicioDelegate cmd = DelegateUtil.getCuadroMandoInicioDelegate();
		try{
			log.info("Generacion cuadro mando inicio: generaEstadisticasServicios");
			cmd.generaEstadisticasServicios();
			log.info("Generacion cuadro mando inicio: generaEstadisticasPortal");
			cmd.generaEstadisticasPortal(ls_fcDesde, ls_fcHasta);
			log.info("Generacion cuadro mando inicio: generaEstadisticasTramitacion");
			cmd.generaEstadisticasTramitacion(ls_fcDesde, ls_fcHasta);
			log.info("Generacion cuadro mando inicio: generaEstadisticasMasTramitados");
			cmd.generaEstadisticasMasTramitados();
			log.info("Generacion cuadro mando inicio: generaEstadisticasMasAccedidos");
			cmd.generaEstadisticasMasAccedidos();
			log.info("Generacion cuadro mando inicio: generaEstadisticasUltimosAlta");
			cmd.generaEstadisticasUltimosAlta();
		} catch (DelegateException e) {
			log.error("Error generando datos para pagina inicio",e);
			log.error("Excepcion: " + e.getMessage(), e);
		}finally{
			log.info("Fin generacion cuadro mando inicio");
		}

	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public CuadroMandoInicio obtenerCuadroMandoInicio(String idioma)
	{

		CuadroMandoInicio cuadroMando = new CuadroMandoInicio();
		List lst = null;
		Connection con = null;
		try
		{
			con = this.getConnection();
			

			/* Consultamos las estadisticas de las tramitaciones*/
			lst = this.queryForMapList(con, "sql.select.cuadroInicio", new Object[] {} );
			if (lst == null || lst.size() <= 0) return cuadroMando;
			Map mResult = ( Map ) lst.get(0);
			String ls_valor = (String) mResult.get("fecha");
			cuadroMando.setUltimaActualizacion(ls_valor);
			String historial = getHistorial(idioma, ls_valor);
			cuadroMando.setHistorial(historial);

			/* Portal */

			InicioPortal portal = new InicioPortal();
//			Number valor = (Number)mResult.get("ini_ressrv");
//			portal.setServiciosActivos(valor.intValue());
			Number valor = (Number)mResult.get("ini_ressrt");
			portal.setServiciosActivosTelematicos(valor.intValue());
			valor = (Number)mResult.get("ini_rescrt");
			portal.setNivelCertificados(valor.intValue());
			valor = (Number)mResult.get("ini_resusu");
			portal.setNivelUsuario(valor.intValue());
			valor = (Number)mResult.get("ini_resan");
			portal.setNivelAnonimos(valor.intValue());
			valor = (Number)mResult.get("ini_resreg");
			portal.setEnvioRegistro(valor.intValue());
			valor = (Number)mResult.get("ini_resbd");
			portal.setEnvioBandeja(valor.intValue());
			valor = (Number)mResult.get("ini_rescs");
			portal.setEnvioConsulta(valor.intValue());
			valor = (Number)mResult.get("ini_respag");
			portal.setDocumentoTipoPago(valor.intValue());

//			valor = (Number)mResult.get("ini_ptlsrv");
//			portal.setServicios(valor.intValue());
//			valor = (Number)mResult.get("ini_ptlsrt");
//			portal.setServiciosTelematicos(valor.intValue());
			valor = (Number)mResult.get("ini_ptlbz");
			portal.setAccesosBuzon(valor.intValue());
			cuadroMando.setPortal(portal);
			
			/* Tramitacion */
			InicioTramitacion tramitacion = new InicioTramitacion();
			valor = (Number)mResult.get("ini_restmx");
			tramitacion.setNumeroMaximoTramites(valor.intValue());
			ls_valor = (String) mResult.get("ini_resfmx");
			tramitacion.setFechaMaximoTramites(ls_valor);
			valor = (Number)mResult.get("ini_trarr");
			tramitacion.setTramitesTelematicos(valor.intValue());
			valor = (Number)mResult.get("ini_trapr");
			tramitacion.setTramitesPreRegistro(valor.intValue());
			cuadroMando.setTramitacion(tramitacion);
			
			/* Los más tramitados */
			LineaDetalleTramitados tramitados = null;
			for(int i=0; i<5; i++)
			{
				ls_valor = (String) mResult.get(((idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "ini_mtrtc" : "ini_mtrt") + (i+1));
				if(ls_valor == null)
				{
					break;
				}
				tramitados = new LineaDetalleTramitados();
				tramitados.setModelo(ls_valor);
				ls_valor = (String) mResult.get("ini_mtro" + (i+1));
				tramitados.setOrganismo(ls_valor);
				valor = (Number)mResult.get("ini_mtrn" + (i+1));
				tramitados.setValor(valor.intValue());
				cuadroMando.addDetalleTramitados(tramitados);
			}
			

			/* Los más Accedidos */
			LineaDetalleAccedidos accedidos = null;
			for(int i=0; i<5; i++)
			{
				ls_valor = (String) mResult.get(((idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "ini_mactc" : "ini_mact") + (i+1));
				if(ls_valor == null)
				{
					break;
				}
				accedidos = new LineaDetalleAccedidos();
				accedidos.setModelo(ls_valor);
				ls_valor = (String) mResult.get("ini_maco" + (i+1));
				accedidos.setOrganismo(ls_valor);
				valor = (Number)mResult.get("ini_macn" + (i+1));
				accedidos.setValor(valor.intValue());
				cuadroMando.addDetalleAccedidos(accedidos);
			}

			/* Los Ultimos dados de Alta */
			LineaDetalleUltimos ultimos = null;
			for(int i=0; i<5; i++)
			{
				ls_valor = (String) mResult.get(((idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "ini_usatc" : "ini_usat") + (i+1));
				if(ls_valor == null)
				{
					break;
				}
				ultimos = new LineaDetalleUltimos();
				ultimos.setModelo(ls_valor);
				ls_valor = (String) mResult.get("ini_usao" + (i+1));
				ultimos.setOrganismo(ls_valor);
				ls_valor = (String) mResult.get("ini_usaf" + (i+1));
				ultimos.setFecha(ls_valor);
				valor = (Number)mResult.get("ini_usan" + (i+1));
				ultimos.setValor(valor.intValue());
				cuadroMando.addDetalleUltimos(ultimos);
			}
			
			return cuadroMando;
			

		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
			return null;
		}	
		finally
		{
			if(con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					log.error("Excepcion: " + e.getMessage(), e);
				}
			}
		}

	}
	
	private String getHistorial(String idioma, String fecha)
	{
		String ls_mes = Util.getDescripcionMes(idioma,fecha.substring(3,5));
		int i_anyo = Integer.parseInt(fecha.substring(6,10));
		String desde = (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "des de" : "desde";
		String hasta = (idioma.equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "fins" : "hasta";
		String result = "(" + desde +" "+ ls_mes + " " + (i_anyo - 1) + " " + hasta + " " + ls_mes + " " + i_anyo + ")";
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */

	public byte[] obtenerGraficoFichero(String fichero)
	{
		try {
			/* Obtenemos path imagenes */
			Properties props = recuperaConfiguracion();
			String pathImages = props.getProperty("pathImages");			
			FileInputStream fis = new FileInputStream(pathImages + fichero);
			byte[] buffer= new byte[8024];
			fis.read(buffer);
			return buffer;
		} catch (Exception ex) {
			log.error("Exception obteniendo grafico de fichero: " + fichero,ex);
			return null;
		}
	}
	
	/**
	 * 
	 * Obtiene auditoria de un tramite anónimo o un usuario autenticado entre dos fechas.
	 * Devuelve lista de eventos auditados.
	 * 
	 * @param fechaIni Fecha inicio
	 * @param fechaFin Fecha fin
	 * @param nivelAutenticacion Nivel autenticacion (A/C/U)
	 * @param autenticacion Identificador autenticación: Si A -> Clave persistencia / Si C/U-> Usuario seycon
	 * @return Lista de eventos. Null si error.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.helpdesk}"
	 */
	public List obtenerAuditoria(Date fechaIni,Date fechaFin,char nivelAutenticacion,String autenticacion)
	{
		List eventos = new ArrayList();		
		try
		{	String sql = "sql.select.auditoria.anonimo";
			List lst;
			if (nivelAutenticacion != 'A')	
			{
				sql="sql.select.auditoria.autenticado";
				String stFcIni = Util.getFecha(new Timestamp(fechaIni.getTime()),"DD/MM/YYYY HH24:MI:SS");
				String stFcFin = Util.getFecha(new Timestamp(fechaFin.getTime()),"DD/MM/YYYY HH24:MI:SS");
				lst = this.queryForMapList(sql,new Object[]{ stFcIni,stFcFin,Character.toString(nivelAutenticacion),autenticacion });
			}
			else
			{
				lst = this.queryForMapList(sql,new Object[]{ Character.toString(nivelAutenticacion),autenticacion });
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			
			for(int i=0; i<lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				
				Evento evento = new Evento();				
				evento.setCodigo(Long.parseLong(mResult.get( "AUD_CODI" ).toString()));
				evento.setFecha(sdf.parse((String) mResult.get( "fecha" )));
				evento.setTipo(mResult.get( "AUD_TIPO" ).toString());
				evento.setDescripcion((String) mResult.get( "AUD_DESC" ));
				evento.setNivelAutenticacion((String) mResult.get( "AUD_NIVAUT" ));
				evento.setUsuarioSeycon((String) mResult.get( "AUD_SEYCON" ));
				evento.setNumeroDocumentoIdentificacion((String) mResult.get( "AUD_NIF" ));
				evento.setNombre((String) mResult.get( "AUD_NOMBRE" ));
				evento.setIdioma((String) mResult.get( "AUD_IDI" ));
				evento.setResultado((String) mResult.get( "AUD_RESULT" ));
				evento.setModeloTramite((String) mResult.get( "AUD_MODTRA" ));
				evento.setVersionTramite((mResult.get( "AUD_VERTRA" )!=null?Integer.parseInt(mResult.get( "AUD_VERTRA" ).toString()):0));
				evento.setIdPersistencia((String) mResult.get( "AUD_IDPER"));
				evento.setClave((String) mResult.get( "AUD_CLAVE" ));
				eventos.add(evento);
			}
			
			return eventos;
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
			return null;
		}
		
	}
	
	/**
	 * Recupera configuracion del modulo
	 * @throws DelegateException 
	 */
	private Properties recuperaConfiguracion() throws DelegateException{
		return DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
	}
	


}
