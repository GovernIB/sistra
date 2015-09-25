package es.caib.audita.persistence.util.evento;

import java.util.List;

import java.util.Map;

public class TestVisualizacionHandler extends
		ParticularidadesVisualizacionHandlerImp
{
 
	public String obtenerCodigoVisualizacion()
	{
		this.getDateFinal();
		this.getDateInicial();
		this.getModo();
		try
		{
			List lstResultConsulta = this.queryForMapList( "sql.select.test" );
			Map mResult = ( Map ) lstResultConsulta.get( 0 );
			return ( String ) mResult.get( "test" );
		}
		catch( Exception exc )
		{
			log.error("Excepcion: " + exc.getMessage(), exc);
		}
		return null;
	}

}
