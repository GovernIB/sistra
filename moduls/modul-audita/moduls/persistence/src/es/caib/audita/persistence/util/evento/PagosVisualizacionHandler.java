package es.caib.audita.persistence.util.evento;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import es.caib.audita.model.AuditConstants;
import es.caib.audita.model.CuadroMandoDetalle;
import es.caib.audita.model.DetalleOrganismo;
import es.caib.audita.model.LineaDetalle;
import es.caib.audita.modelInterfaz.ConstantesAuditoria;

public class PagosVisualizacionHandler extends
		CuadroMandoDetalleHandler
{
	
	protected List getListDetalleAudit(String tipo, String fechaInicio, String fechaFinal)
	{
		List lstResultConsulta;
		String ls_query = "sql.select.detalle.audit";
		Connection con = null;
		try
		{
			con = this.getConnection();
			lstResultConsulta = this.queryForMapList( con, PagosVisualizacionHandler.class, ls_query , new Object[]{ tipo, fechaInicio, fechaFinal });
			return lstResultConsulta;
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		return null;
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
				String modelo = (String)mResult.get("modelo");
				String tipo = (String)mResult.get("tipo");
				Map mResultModelo = getDatosModelo(lista,modelo);
				String descripcion = "";
				String organismo = "";
				BigDecimal codigo = null;
				if(mResultModelo != null)
				{
					descripcion = (String) mResultModelo.get("descripcion");
					organismo = (String) mResultModelo.get("organismo");
					codigo = (BigDecimal) mResultModelo.get("codigo");
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
					BigDecimal total = (BigDecimal) mResult.get("total");

					LineaDetalle ld = do_organismo.getLineaDetalle(modelo);
					boolean existLinea = (ld == null) ? false : true;
					if(!existLinea)
					{
						ld = new LineaDetalle();
						ld.setTitulo(descripcion);
						ld.setModelo(modelo);
//						ld.setVisualizacion(getVisualizacionPagos(codigo,listaEventosPago));
					}
					ld.increment(total.intValue());
					if(tipo != null)
					{
						ld.addTotal(tipo,total.intValue());
					}
					if(!existLinea)
					{
						do_organismo.addLineaDetalle(ld);
					}
					do_organismo.increment(total.intValue());
					result.increment(total.intValue());
				}
			}
			
			result = refreshVisualizacionPagos(result);
			
			

			return result;
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
			return result;
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

	private CuadroMandoDetalle refreshVisualizacionPagos(CuadroMandoDetalle cuadroMando)
	{
		List organismos = cuadroMando.getDetalleOrganismos();
		for(int i=0; i<organismos.size(); i++)
		{
			DetalleOrganismo do_organismo = (DetalleOrganismo) organismos.get(i);
			List lineas = do_organismo.getLineasDetalle();
			for(int j=0; j<lineas.size(); j++)
			{
				LineaDetalle linea = (LineaDetalle) lineas.get(j);
				String visualizacion = getVisualizacionPagos(linea);
				linea.setVisualizacion(visualizacion);
			}
		}
		return cuadroMando;
	}
	
	private String getVisualizacionPagos(LineaDetalle linea)
	{
		String result = "";
		
		String telematico = (getIdioma().equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "Telemàtic" : "Telemático" ;
		String presencial = "Presencial";

		// Telematico
		result += "<img src=\"./images/cuadromando/i_pago_t.gif\"  title=\"" + telematico + "\" style=\"vertical-align:middle;\"> &nbsp;" + 
                  linea.getTotal(AuditConstants.TELEMATICO) +  "&nbsp;";; 

		// Presencial
		result += "<img src=\"./images/cuadromando/i_pago_p.gif\"  title=\"" + presencial + "\" style=\"vertical-align:middle;\"> &nbsp;" + 
        		  linea.getTotal(AuditConstants.PRESENCIAL) +  "&nbsp;";; 

		return result;
	}
	

	
	public String obtenerCodigoVisualizacion()
	{
		this.getDateFinal();
		this.getDateInicial();
		String ls_tipo = this.getTipoEvento();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String ls_fechaInicio = dateFormat.format(this.getDateInicial());
		String ls_fechaFinal = "";
		if(this.getDateFinal() != null)
		{
		   ls_fechaFinal = dateFormat.format(this.getDateFinal());
		}
		String result = "";
		Connection con = null;
		try
		{
			List lstResultConsulta;
			String ls_query = "sql.select.registro";
			con = this.getConnection();
			if(this.getDateFinal() == null)
			{
				ls_query += ".desde";
				lstResultConsulta = this.queryForMapList( con, PagosVisualizacionHandler.class, ls_query , new Object[]{ ls_tipo, ls_fechaInicio });
			}
			else
			{
				lstResultConsulta = this.queryForMapList( con, PagosVisualizacionHandler.class, ls_query , new Object[]{ ls_tipo, ls_fechaInicio, ls_fechaFinal });
			}
			
			String telematico = (getIdioma().equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "Telemàtic" : "Telemático" ;
			String presencial = (getIdioma().equals(ConstantesAuditoria.LANGUAGE_CATALAN)) ? "Presencial" : "Presencial" ;

			// Telemático
			result += "<img src=\"./images/cuadromando/i_pago_t.gif\"  title=\"" + telematico + "\" style=\"vertical-align:middle;\"> &nbsp;" + 
                      getTotal(lstResultConsulta,String.valueOf(AuditConstants.TELEMATICO)) +
                      "&nbsp;"; 
			// Presencial
			result += "<img src=\"./images/cuadromando/i_pago_p.gif\" title=\"" + presencial + "\"  style=\"vertical-align:middle;\"> &nbsp;" + 
                      getTotal(lstResultConsulta,String.valueOf(AuditConstants.PRESENCIAL))+
                      "&nbsp;"; 

			return result;
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
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

		return result;
	}
	
	private int getTotal(List lista, String tipo)
	{
		for(int i=0; i<lista.size(); i++)
		{
			Map mResult = ( Map ) lista.get(i);
			String clave = ( String ) mResult.get( "clave" );
			if(clave == null)
			{
				return 0;
			}
			if(clave.equals(tipo))
			{
				BigDecimal total = (BigDecimal) mResult.get( "total" );
				return total.intValue();
			}
		}
		return 0;
	}

}
