package es.caib.audita.persistence.util.evento;

import java.io.Serializable;
import java.util.Date;

import es.caib.audita.model.CuadroMandoDetalle;
import es.caib.audita.model.CuadroMandoTablaCruzada;
import es.indra.util.graficos.generadorGraficos.DatosGrafico;

public interface ParticularidadesVisualizacionHandler extends Serializable
{
	/**
	 *  Este m�todo inicializa el Handler con los datos necesarios
	 * 
	 * @param connection
	 * @param modo
	 * @param tipoEvento
	 * @param dateInicial
	 * @param dateFinal
	 */
	public void init( java.sql.Connection connection, String modo, String tipoEvento, Date dateInicial, Date dateFinal, String idioma );
	
	/**
	 * Este m�todo devuelve una cadena (innerHTML) que se pintar� en el Cuadro de Mando
	 * @return String
	 */
	public String obtenerCodigoVisualizacion();
	
	/**
	 * 
	 * Este m�todo devuelve una instancia de CuadroMandoDetalle que se obtiene a partir de los 
	 * datos que se han insertado con el m�todo init
	 * 
	 * @return CuadroMandoDetalle
	 */
	public CuadroMandoDetalle obtenerCuadroMandoDetalle();
	/**
	 * 
	 * @param tipoEvento
	 * @param modo
	 * @param fecha
	 * @return
	 */
	public CuadroMandoTablaCruzada obtenerCuadroMandoTablaCruzada(String tipoEvento,String modo,Date fecha, String idioma);
	
	/**
	 * 
	 * Este m�todo devuelve una instancia de DatosGrafico que se obtiene a partir de los datos
	 * que se han insertado con el m�todo init
	 * 
	 * @return DatosGrafico
	 */
	public DatosGrafico obtenerDatosGrafico();
}
