package es.caib.audita.persistence.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.CreateException;

import es.caib.audita.model.AuditConstants;
import es.caib.audita.model.EventoAuditado;
import es.caib.audita.model.Modulo;
import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.audita.persistence.delegate.DelegateException;
import es.caib.audita.persistence.delegate.DelegateUtil;
import es.caib.audita.persistence.util.evento.ParticularidadesVisualizacionFactoria;
import es.caib.audita.persistence.util.evento.ParticularidadesVisualizacionHandler;
import es.indra.util.graficos.generadorGraficos.ConfiguracionGrafico;
import es.indra.util.graficos.generadorGraficos.DatosGrafico;
import es.indra.util.graficos.generadorGraficos.GeneradorGraficos;
import es.indra.util.graficos.generadorGraficos.Linea;

/**
 * Operaciones para generar pagina de inicio 
 *
 * @ejb.bean
 *  name="audita/persistence/CuadroMandoInicioFacade"
 *  jndi-name="es.caib.audita.persistence.CuadroMandoInicioFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *  
 * @ejb.transaction type="RequiresNew"
 * 
 */
public abstract class CuadroMandoInicioFacadeEJB extends QueryEJB
{
	
    protected static String JNDI_SISTRA = "";
	
//    protected static String JNDI_ROLSAC = "";

    private static final String FICHERO_PROPIEDADES = "Auditoria.properties";
	
    
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
			exc.printStackTrace();
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
			exc.printStackTrace();
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
				exc.printStackTrace();
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
					totalIdioma.put(ls_idioma,new BigDecimal(total.toString()));
				}
				eventoAuditado.setTotalesIdioma(totalIdioma);
				
			}
			catch( Exception exc )
			{
				exc.printStackTrace();
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
					BigDecimal total = ( BigDecimal ) mResult.get( "total" );
					totalNivelAutenticacion.put(ls_nivel.trim(),total);
				}
				*/
				eventoAuditado.setTotalesNivelAutenticacion(totalNivelAutenticacion);
				
			}
			catch( Exception exc )
			{
				exc.printStackTrace();
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
					exc.printStackTrace();
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
				return new BigDecimal(total.doubleValue());
			}
		}
		return BigDecimal.valueOf(0);
	}
	
	private String getSqlIn(List tramites)
	{
		String ls_sql = " ( ";
		for(int i=0; i<tramites.size(); i++)
		{
			Number tramite = (Number) tramites.get(i);
			ls_sql += "'" + tramite.toString() + "'";
			if(i != (tramites.size()-1))
			{
				ls_sql += " , ";
			}
		}
		ls_sql += " )";
		return ls_sql;

	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public void generaEstadisticasServicios()
	{
		List lst = null;
//		Connection conRsc = null;
		Connection conSistra = null;	
		Connection con = null;
		int servicios = 0;
		int serviciosTelematicos = 0;
		int certificados = 0;
		int usuarios = 0;
		int anonimos = 0;
		int registro = 0;
		int bandeja = 0;
		int consulta = 0;
		int pagos = 0;
		int maxTramites = 0;
		ArrayList tramites = new ArrayList();
		
		try
		{
			con = this.getConnection();

//			conRsc = this.getConnection(getJndiRolsac());

			conSistra = this.getConnection(getJndiSistra());
			
			/* Consultamos los Servicios Telemáticos*/
//			lst = this.queryForMapList(conRsc, "sql.select.servicios.catalogo", new Object[] {} );
//			Map mResult = ( Map ) lst.get(0);
//			BigDecimal total = ( BigDecimal ) mResult.get( "total" );
//			servicios = total.intValue();

			/* Cuando este en ROLSAC hay que descomentarlo
			lst = this.queryForMapList(conRsc, "sql.select.servicios.catalogo.telematicos", new Object[] {} );
			*/
			lst = this.queryForMapList(conSistra, "sql.select.servicios.catalogo.telematicos.sistra", new Object[] {} );
			Map mResult = ( Map ) lst.get(0);
			Number total = ( Number ) mResult.get( "total" );
			serviciosTelematicos = total.intValue();

			/* Recogemos los servicios activos */
			/* Cuando este en ROLSAC hay que descomentarlo
			lst = this.queryForMapList(conRsc, "sql.select.servicios.catalogo.activos", new Object[] {} );
			*/
			lst = this.queryForMapList(conSistra, "sql.select.servicios.catalogo.activos.sistra", new Object[] {} );
			for(int i=0; i<lst.size(); i++)
			{
				mResult = ( Map ) lst.get(i);
				tramites.add(( Number ) mResult.get( "tramite" ));
				
			}
			
			String ls_sql = getSql("sql.select.servicios.autenticacion.certificado");
			if(tramites.size() != 0)
			{
				ls_sql += getSqlIn(tramites);
				lst = this.queryForMapListConstructed(conSistra,ls_sql,new Object[] {});
				mResult = ( Map ) lst.get(0);
				total = ( Number ) mResult.get( "total" );
				certificados = total.intValue();
			}
			/* Consultamos los Servicios con Nivel de Autenticación Usuario*/
			ls_sql = getSql("sql.select.servicios.autenticacion.usuario");
			if(tramites.size() != 0)
			{
				ls_sql += getSqlIn(tramites);
				lst = this.queryForMapListConstructed(conSistra,ls_sql,new Object[] {});
				mResult = ( Map ) lst.get(0);
				total = ( Number ) mResult.get( "total" );
				usuarios = total.intValue();
			}
			/* Consultamos los Servicios con Nivel de Autenticación Anonimo*/
			ls_sql = getSql("sql.select.servicios.autenticacion.anonimo");
			if(tramites.size() != 0)
			{
				ls_sql += getSqlIn(tramites);
				lst = this.queryForMapListConstructed(conSistra,ls_sql,new Object[] {});
				mResult = ( Map ) lst.get(0);
				total = ( Number ) mResult.get( "total" );
				anonimos = total.intValue();
			}
			/* Consultamos los Servicios con Envio a Registro*/
			ls_sql = getSql("sql.select.servicios.envio.registro");
			if(tramites.size() != 0)
			{
				ls_sql += getSqlIn(tramites);
				lst = this.queryForMapListConstructed(conSistra,ls_sql,new Object[] {});
				mResult = ( Map ) lst.get(0);
				total = ( Number ) mResult.get( "total" );
				registro = total.intValue();
			}
			/* Consultamos los Servicios con Envio a Bandeja*/
			ls_sql = getSql("sql.select.servicios.envio.bandeja");
			if(tramites.size() != 0)
			{
				ls_sql += getSqlIn(tramites);
				lst = this.queryForMapListConstructed(conSistra,ls_sql,new Object[] {});
				mResult = ( Map ) lst.get(0);
				total = ( Number ) mResult.get( "total" );
				bandeja = total.intValue();
			}
			/* Consultamos los Servicios con Envio a Consulta*/
			ls_sql = getSql("sql.select.servicios.envio.consulta");
			if(tramites.size() != 0)
			{
				ls_sql += getSqlIn(tramites);
				lst = this.queryForMapListConstructed(conSistra,ls_sql,new Object[] {});
				mResult = ( Map ) lst.get(0);
				total = ( Number ) mResult.get( "total" );
				consulta = total.intValue();
			}
			/* Consultamos los Servicios con Pagos*/
			ls_sql = getSql("sql.select.servicios.pagos");
			if(tramites.size() != 0)
			{
				ls_sql += getSqlIn(tramites);
				lst = this.queryForMapListConstructed(conSistra,ls_sql,new Object[] {});
				mResult = ( Map ) lst.get(0);
				total = ( Number ) mResult.get( "total" );
				pagos = total.intValue();
			}
			/* Consultamos el numero maximo de Trámites realizados*/
			lst = this.queryForMapList("sql.select.servicios.maximoConcurrentes", new Object[] {} );
			mResult = ( Map ) lst.get(0);
			total = ( Number ) mResult.get( "total" );
			String ls_dia =  (String) mResult.get("dia");
			String ls_hora;
			ls_hora = ls_dia.substring(11);
			if (ls_hora.startsWith("0")) ls_hora = ls_hora.substring(1);
			int li_hora = Integer.parseInt(ls_hora);
			li_hora ++;
			if (li_hora == 24) li_hora = 0;
			ls_hora = Integer.toString(li_hora);
			if (li_hora < 10) ls_hora = "0" + ls_hora;						
			ls_dia = ls_dia + "h - " + ls_hora + "h";
			
			maxTramites = total.intValue();

			/* borramos los datos que hubiera */
			this.update(con, "sql.delete", null);
			
			/* insertamos los datos */
			Object params[] = 
				new Object[] 
				{ new Integer(servicios), new Integer(serviciosTelematicos), new Integer(certificados), new Integer(usuarios), 
				  new Integer(anonimos), new Integer(registro), new Integer(bandeja),
				  new Integer(consulta), new Integer(pagos), new Integer(maxTramites), ls_dia};
			this.update( con, "sql.insert.servicios", params );

		}
		catch( Exception exc )
		{
			exc.printStackTrace();
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
//			if(conRsc != null)
//			{
//				try {
//					conRsc.close();
//				} catch (SQLException e) {
//					
//					e.printStackTrace();
//				}
//			}
			if(conSistra != null)
			{
				try {
					conSistra.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public void generaEstadisticasPortal(String desde, String hasta)
	{
		List lst = null;
		int serviciosTelematicos = 0;
		int servicios = 0;
		int buzon = 0;
		Connection con = null;
		try
		{
			con = this.getConnection();

			/* Consultamos el acceso a  Servicios Telemáticos*/
//			lst = this.queryForMapList(con, "sql.select.portal.servicios.telematicos", new Object[] { desde, hasta} );
//			Map mResult = ( Map ) lst.get(0);
//			BigDecimal total = ( BigDecimal ) mResult.get( "total" );
//			serviciosTelematicos = total.intValue();

			/* Consultamos el acceso a  Servicios Telemáticos*/
//			lst = this.queryForMapList(con, "sql.select.portal.servicios", new Object[] { desde, hasta} );
//			mResult = ( Map ) lst.get(0);
//			total = ( BigDecimal ) mResult.get( "total" );
//			servicios = total.intValue();

			/* Consultamos el acceso a la Zona Personal*/
			lst = this.queryForMapList(con, "sql.select.portal.accesoBuzon", new Object[] { desde, hasta} );
			Map mResult = ( Map ) lst.get(0);
			Number total = ( Number ) mResult.get( "total" );
			buzon = total.intValue();
			
			/* insertamos los datos */
			Object params[] = 
				new Object[] 
				{ new Integer(servicios), new Integer(serviciosTelematicos), new Integer(buzon)};
			this.update(con,  "sql.insert.portal", params );
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d_hasta = sdf.parse(hasta);
			
			/* Generamos la grafica de Acceso a Servicios de Catalogo */
//			DatosGrafico datos = obtenerDatosGrafico("SERCAT",d_hasta,"inicio");
//			datos.setFormat(DatosGrafico.MESES,"MMM");
//			ConfiguracionGrafico configuracion = new ConfiguracionGrafico();
//			configuracion.setAlto(125);
//			configuracion.setAncho(250);
//			configuracion.setColorSeries("#f08900-#27953B");
//			configuracion.setLeyenda(false);
			
			/* Obtenemos path imagenes */
			Properties props = recuperaConfiguracion();
			String pathImages = props.getProperty("pathImages");
			
//			configuracion.setFichero(pathImages + "graficaservicios.jpg");

			/* Generamos la grafica de Servicios Telemáticos */
			
//			DatosGrafico datosPre = obtenerDatosGrafico("SERPAE",d_hasta,"inicio");
//			Linea linea = datosPre.getLinea(0);
//			List lineas = datos.getLines();
//			lineas.add(linea);
//			datos.setLines((ArrayList) lineas);
//			GeneradorGraficos.generarImagen(datos,configuracion);
			


			/* Generamos la grafica de Accesos a Zona Personal*/
			DatosGrafico datos = obtenerDatosGrafico("AZPER",d_hasta,"inicio");
			ConfiguracionGrafico configuracion = new ConfiguracionGrafico();
			configuracion.setAlto(125);
			configuracion.setAncho(250);
			configuracion.setLeyenda(false);
			configuracion.setColorSeries("#9547F4");
			configuracion.setFichero(pathImages + "graficazonapersonal.jpg");
			datos.setFormat(DatosGrafico.MESES,"MMM");
			GeneradorGraficos.generarImagen(datos,configuracion);

		}
		catch( Exception exc )
		{
			exc.printStackTrace();
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
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public void generaEstadisticasTramitacion(String desde, String hasta)
	{
		List lst = null;
		int serviciosTelematicos = 0;
		int serviciosPreregistro = 0;
		Connection con = null;
		try
		{
			con = this.getConnection();

			/* Tramites Realizados Telemáticamente*/
			lst = this.queryForMapList(con, "sql.select.tramitacion.telematicos", new Object[] { desde, hasta} );
			Map mResult = ( Map ) lst.get(0);
			Number total = ( Number ) mResult.get( "total" );
			serviciosTelematicos = total.intValue();

			/* Tramites con Pre-Registro*/
			lst = this.queryForMapList(con, "sql.select.tramitacion.preregistro", new Object[] { desde, hasta} );
			mResult = ( Map ) lst.get(0);
			total = ( Number ) mResult.get( "total" );
			serviciosPreregistro = total.intValue();

			
			/* insertamos los datos */
			Object params[] = 
				new Object[] 
				{ new Integer(serviciosTelematicos), new Integer(serviciosPreregistro)};
			this.update(con,  "sql.insert.tramitacion", params );
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d_hasta = sdf.parse(hasta);
			
			/* Obtenemos path imagenes */
			Properties props = recuperaConfiguracion();
			String pathImages = props.getProperty("pathImages");			
			
			/* Generamos la grafica de los tramites realizados telemáticamente */
			DatosGrafico datos = obtenerDatosGrafico("ENVTRA",d_hasta,"inicio");
			datos.setFormat(DatosGrafico.MESES,"MMM");
			ConfiguracionGrafico configuracion = new ConfiguracionGrafico();
			configuracion.setAlto(125);
			configuracion.setAncho(250);
			configuracion.setColorSeries("#f08900");
			configuracion.setLeyenda(false);
			configuracion.setFichero(pathImages+"graficatramites.jpg");
			GeneradorGraficos.generarImagen(datos,configuracion);

			/* Generamos la grafica de los tramites realizados con pre-registro*/
			datos = obtenerDatosGrafico("PRETRA",d_hasta,"inicio");
			datos.setFormat(DatosGrafico.MESES,"MMM");
			configuracion.setColorSeries("#27953B");
			configuracion.setFichero(pathImages+"graficatramitespreregistro.jpg");
			GeneradorGraficos.generarImagen(datos,configuracion);


		}
		catch( Exception exc )
		{
			exc.printStackTrace();
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

	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public void generaEstadisticasMasTramitados()
	{
		List lst = null;
		Connection con = null;
		Connection conSistra = null;
		

		try
		{
			con = this.getConnection();
			conSistra = this.getConnection(getJndiSistra());
			List lista = this.queryForMapList(conSistra,"sql.select.datos.modelo",new Object[] {});

			/* Consultamos los más tramitados*/
			lst = this.queryForMapList(con, "sql.select.tramitados", new Object[] {} );
			int max = 0;			
			Object [] params = new Object[5*4];
			String ls_sql = "update aud_inici set ";
			String ls_sqlField;
			for(int i=0; i< lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				String modelo = (String)mResult.get("modelo");
				Map mResultModelo = getDatosModelo(lista,modelo);
				String descripcion = "";
				String descripcion_ca = "";
				String organismo = "";
				
				if(mResultModelo == null){
					continue;
				}				
				

				// Cogemos descripcion en Castellano
				
				Map mResultModeloLanguage = getDatosModelo(lista,modelo, ConstantesAuditoria.LANGUAGE_CASTELLANO);
				if(mResultModeloLanguage != null)
				{
					descripcion = (String) mResultModeloLanguage.get("descripcion");
				}
				else
				{
					descripcion = null;
				}

				// Cogemos descripcion en Catalán
				
				mResultModeloLanguage = getDatosModelo(lista,modelo, ConstantesAuditoria.LANGUAGE_CATALAN);
				if(mResultModeloLanguage != null)
				{
					descripcion_ca = (String) mResultModeloLanguage.get("descripcion");
				}
				else
				{
					descripcion_ca = null;
				}

				if((descripcion == null) && (descripcion_ca == null))
				{
					descripcion = (String) mResultModelo.get("modelo");
					descripcion_ca = descripcion;
				}
				else
				{
					if(descripcion_ca == null)
					{
						descripcion_ca = descripcion;
					}
					else if(descripcion == null)
					{
						descripcion = descripcion_ca;
					}
				}

				organismo = (String) mResultModelo.get("organismo");				
				Number total = (Number) mResult.get("total");

				params[(max * 4)] = descripcion;
				params[(max * 4) + 1] = descripcion_ca;
				params[(max * 4) + 2] = organismo;
				params[(max * 4) + 3] = new Integer(total.intValue());
				
				ls_sqlField = "ini_mtrt" + (max+1) + " = ?, ini_mtrtc" + (max+1) + " = ?, ini_mtro" + (max+1) + " = ?, " +
				                     "ini_mtrn" + (max+1) + " = ?";								
				ls_sqlField += ",";								
				ls_sql += ls_sqlField;
				
				max ++;
				if (max == 5) break;
			}
			
			if (max > 0){
				Object [] parametros = new Object[max*4];
				for (int i=0;i< (max*4);i++){
					parametros[i] = params[i];
				}
				ls_sql = ls_sql.substring(0,ls_sql.length() - 1);			
				this.updateConstructed(con,ls_sql,parametros);
			}
			

		}
		catch( Exception exc )
		{
			exc.printStackTrace();
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
			if(conSistra != null)
			{
				try {
					conSistra.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}

		
	}
	
	private Map getDatosModelo(List lista, String modelo)
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
	
	private Map getDatosModelo(List lista, String modelo, String idioma)
	{
		for(int i=0; i<lista.size(); i++)
		{
			Map mResult = ( Map ) lista.get(i);
			
			String ls_modelo = (String) mResult.get("modelo");
			String ls_idioma = (String) mResult.get("idioma");
			if(modelo.equals(ls_modelo) && idioma.equals(ls_idioma))
			{
				return mResult;
			}
		}
		return null;

	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public void generaEstadisticasMasAccedidos()
	{
		List lst = null;
		Connection con = null;
		Connection conSistra = null;

		try
		{
			con = this.getConnection();
			conSistra = this.getConnection(getJndiSistra());
			List lista = this.queryForMapList(conSistra,"sql.select.datos.modelo",new Object[] {});

			/* Consultamos los más tramitados*/
			lst = this.queryForMapList(con, "sql.select.accedidos", new Object[] { } );
			int max = 0;			
			Object [] params = new Object[5*4];
			String ls_sql = "update aud_inici set ";
			for(int i=0; i<lst.size(); i++)
			{
				Map mResult = ( Map ) lst.get(i);
				String modelo = (String)mResult.get("modelo");
				
				// Cogemos modelo en Castellano
				Map mResultModelo = getDatosModelo(lista,modelo);
				String descripcion = "";
				String organismo = "";
				String descripcion_ca = "";
				if(mResultModelo == null) continue;
				
				// Cogemos descripcion en Castellano
				
				Map mResultModeloLanguage = getDatosModelo(lista,modelo, ConstantesAuditoria.LANGUAGE_CASTELLANO);
				if(mResultModeloLanguage != null)
				{
					descripcion = (String) mResultModeloLanguage.get("descripcion");
				}
				else
				{
					descripcion = null;
				}

				// Cogemos descripcion en Catalán
				
				mResultModeloLanguage = getDatosModelo(lista,modelo, ConstantesAuditoria.LANGUAGE_CATALAN);
				if(mResultModeloLanguage != null)
				{
					descripcion_ca = (String) mResultModeloLanguage.get("descripcion");
				}
				else
				{
					descripcion_ca = null;
				}

				if((descripcion == null) && (descripcion_ca == null))
				{
					descripcion = (String) mResultModelo.get("modelo");
					descripcion_ca = descripcion;
				}
				else
				{
					if(descripcion_ca == null)
					{
						descripcion_ca = descripcion;
					}
					else if(descripcion == null)
					{
						descripcion = descripcion_ca;
					}
				}
				
				organismo = (String) mResultModelo.get("organismo");				
				Number total = (Number) mResult.get("total");

				params[(max * 4)] = descripcion;
				params[(max * 4) + 1] = descripcion_ca;
				params[(max * 4) + 2] = organismo;
				params[(max * 4) + 3] = new Integer(total.intValue());
				
				String ls_sqlField = "ini_mact" + (max+1) + " = ?, ini_mactc" + (max+1) + " = ?, ini_maco" + (max+1) + " = ?, " +
				                     "ini_macn" + (max+1) + " = ?";
				ls_sqlField += ",";
				ls_sql += ls_sqlField;
				
				max++;
				if (max == 5) break;
				
			}

			if (max > 0){
				Object [] parametros = new Object[max*4];
				for (int i=0;i< (max*4);i++){
					parametros[i] = params[i];
				}
				ls_sql = ls_sql.substring(0,ls_sql.length() - 1);			
				this.updateConstructed(con,ls_sql,parametros);
			}		
			

		}
		catch( Exception exc )
		{
			exc.printStackTrace();
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
			if(conSistra != null)
			{
				try {
					conSistra.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission unchecked = "true"
	 */
	public void generaEstadisticasUltimosAlta()
	{
		List lst = null;
		Connection con = null;
		Connection conSistra = null;
//		Connection conRolsac = null;

		try
		{
			con = this.getConnection();
			conSistra = this.getConnection(getJndiSistra());
//			conRolsac = this.getConnection(getJndiRolsac());
			
			/* Consultamos los datos de los modelos y organismo*/
			List lista = this.queryForMapList(conSistra,"sql.select.dadosAlta.datos",new Object[] {});
			
			/* Consultamos la lista de los ultimos dados de alta*/
			/* Cuando este en ROLSAC hay que descomentarlo
			List listaRolsac = this.queryForMapList(conRolsac,"sql.select.dadosAlta.fecha",new Object[] {});
			*/
			List listaRolsac = this.queryForMapList(conSistra,"sql.select.dadosAlta.fecha.sistra",new Object[] {});

			/* Consultamos las estadisticas de las tramitaciones*/
			lst = this.queryForMapList(con, "sql.select.dadosAlta.valores", new Object[] {} );
			
			int max = 0;			
			Object [] params = new Object[5*5];
			String ls_sql = "update aud_inici set ";
			for(int i=0; i<listaRolsac.size(); i++)
			{
				Map mResult = ( Map ) listaRolsac.get(i);
				String rs_modelo = (String)mResult.get("modelo");
				String fecha = (String) mResult.get("fecha");
				Number version = (Number)  mResult.get("version");
				Map mDatosModelo = getDatosModelo(lista,rs_modelo);
				String descripcion = "";
				String descripcion_ca = "";
				String organismo = "";
				String st_modelo = null;
				
				if(mDatosModelo == null) continue;

				organismo = (String) mDatosModelo.get("organismo");
				st_modelo = (String) mDatosModelo.get("modelo");
				
				// Cogemos descripcion en Castellano
				
				Map mResultModeloLanguage = getDatosModelo(lista,st_modelo, ConstantesAuditoria.LANGUAGE_CASTELLANO);
				if(mResultModeloLanguage != null)
				{
					descripcion = (String) mResultModeloLanguage.get("descripcion");
				}
				else
				{
					descripcion = null;
				}

				// Cogemos descripcion en Catalán
				
				mResultModeloLanguage = getDatosModelo(lista,st_modelo, ConstantesAuditoria.LANGUAGE_CATALAN);
				if(mResultModeloLanguage != null)
				{
					descripcion_ca = (String) mResultModeloLanguage.get("descripcion");
				}
				else
				{
					descripcion_ca = null;
				}

				if((descripcion == null) && (descripcion_ca == null))
				{
					descripcion = (String) mDatosModelo.get("modelo");
					descripcion_ca = descripcion;
				}
				else
				{
					if(descripcion_ca == null)
					{
						descripcion_ca = descripcion;
					}
					else if(descripcion == null)
					{
						descripcion = descripcion_ca;
					}
				}

				//descripcion = (String) mDatosModelo.get("descripcion");
				descripcion += " ( Versión " + version.intValue() + " )";
				descripcion_ca += " ( Versió " + version.intValue() + " )";

				
				
				Map mValoresModelo = getEstadisticasModelo(lst,st_modelo);
				int i_total = 0;
				if(mValoresModelo != null)
				{
					Number total = (Number) mValoresModelo.get("total");
					i_total = total.intValue();
				}

				params[(i * 5)] = descripcion;
				params[(i * 5) + 1] = descripcion_ca;
				params[(i * 5) + 2] = organismo;
				params[(i * 5) + 3] = new Integer(i_total);
				params[(i * 5) + 4] = fecha;
				
				String ls_sqlField = "ini_usat" + (max+1) + " = ?, ini_usatc" + (max+1) + " = ?, ini_usao" + (max+1) + " = ?, " +
				                     "ini_usan" + (max+1) + " = ?, ini_usaf" + (max+1) + " = ?";
				ls_sqlField += ",";
				ls_sql += ls_sqlField;
				
				max++;
				if (max == 5) break;

			}
			
			if (max > 0){
				Object [] parametros = new Object[max*5];
				for (int i=0;i< (max*5);i++){
					parametros[i] = params[i];
				}
				ls_sql = ls_sql.substring(0,ls_sql.length() - 1);			
				this.updateConstructed(con,ls_sql,parametros);
			}		

			

		}
		catch( Exception exc )
		{
			exc.printStackTrace();
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
			if(conSistra != null)
			{
				try {
					conSistra.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
//			if(conRolsac != null)
//			{
//				try {
//					conRolsac.close();
//				} catch (SQLException e) {
//					
//					e.printStackTrace();
//				}
//			}
		}

	}
	
	private Map getEstadisticasModelo(List lista, String modelo)
	{
		if(modelo == null) return null;
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
	
	protected String getJndiSistra()
	{
		if(JNDI_SISTRA.equals(""))
		{
			Properties props = new Properties();
			try {
				props.load(this.getClass().getResourceAsStream(FICHERO_PROPIEDADES));
				CuadroMandoInicioFacadeEJB.JNDI_SISTRA = "java:/" + props.getProperty("datasource.sistra");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return CuadroMandoInicioFacadeEJB.JNDI_SISTRA;
	}
	
	
//	protected String getJndiRolsac()
//	{
//		if(JNDI_ROLSAC.equals(""))
//		{
//			Properties props = new Properties();
//			try {
//				props.load(this.getClass().getResourceAsStream(FICHERO_PROPIEDADES));
//				CuadroMandoInicioFacadeEJB.JNDI_ROLSAC = "java:/" + props.getProperty("datasource.rolsac");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
//		return CuadroMandoInicioFacadeEJB.JNDI_ROLSAC;
//	}
	
	private DatosGrafico obtenerDatosGrafico(String tipoEvento,Date fecha,String modo)
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
			exc.printStackTrace();
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

		return datos;
	}
	
	private String getSql(String parametro) throws IOException
	{
		Properties props = new Properties();
		String ls_file = "Auditoria.properties";
		props.load(this.getClass().getResourceAsStream(ls_file));
		return props.getProperty(parametro);
	}
	
	/**
	 * Recupera configuracion del modulo
	 * @throws DelegateException 
	 */
	private Properties recuperaConfiguracion() throws DelegateException{
		return DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
	}
	


}
