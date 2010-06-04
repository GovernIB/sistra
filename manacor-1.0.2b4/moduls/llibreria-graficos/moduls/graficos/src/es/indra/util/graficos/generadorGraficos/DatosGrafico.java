package es.indra.util.graficos.generadorGraficos;

import java.io.Serializable;
import java.util.*;

/**
 * Modeliza los datos de un gráfico devuelto por 
 * el Sistema de Gestion de Datos
 * 
 */
public class DatosGrafico implements Serializable{	

	
  private String tituloGrafico;
	private String tituloEjeX;
	private String tituloEjeY;
	private String tipoGrafico;
	private String escalaTiempo;  
	private ArrayList lines = new ArrayList();
  private String color_barra;
  
  	private HashMap formats = new HashMap();
  
  public static final String LINEAS = "LINEAS";  
	public static final String BARRAS = "BARRAS";
	public static final String TARTA = "TARTA";
	public static final String TIEMPO = "TIEMPO";
  public static final String LISTADO = "LISTADO";
	
	public static final String ANYOS = "ANYO";  
	public static final String HORAS = "HORA";  
	public static final String DIAS = "DIA";
	public static final String MESES = "MES";
	public static final String MINUTOS = "MINUTO";
	public static final String SEGUNDOS = "SEGUNDO";
	
	public Linea nuevaLinea(){
		Linea l_line = new Linea();
		lines.add(l_line);
		return l_line;
	}
	
	public Linea getLinea(int i){
		return (Linea) lines.get(i);
	}
	
	public int getTotalLineas(){
		return lines.size();
	}
	
	/**
	 * @return Devuelve tipoGrafico.
	 */
	public String getTipoGrafico() {
		return tipoGrafico;
	}
	/**
	 * @param tipoGrafico El tipoGrafico a establecer.
	 */
	public void setTipoGrafico(String tipoGrafico) throws Exception {
		if (tipoGrafico.equals(LINEAS) || tipoGrafico.equals(BARRAS) || tipoGrafico.equals(TARTA) || tipoGrafico.equals(TIEMPO) || tipoGrafico.equals(LISTADO))
			this.tipoGrafico = tipoGrafico;
		else
			throw new Exception("Tipo de gráfico " + tipoGrafico + " no soportado");
	}

	/**
	 * @return Devuelve tituloGrafico.
	 */
	public String getTituloGrafico() {
		return tituloGrafico;
	}
	/**
	 * @param tituloGrafico El tituloGrafico a establecer.
	 */
	public void setTituloGrafico(String tituloGrafico) {
		this.tituloGrafico = tituloGrafico;
	}
	/**
	 * @return Devuelve tituloEjeX.
	 */
	public String getTituloEjeX() {
		return tituloEjeX;
	}
	/**
	 * @param tituloEjeX El tituloEjeX a establecer.
	 */
	public void setTituloEjeX(String tituloEjeX) {
		this.tituloEjeX = tituloEjeX;
	}
	/**
	 * @return Devuelve tituloEjeY.
	 */
	public String getTituloEjeY() {
		return tituloEjeY;
	}
	/**
	 * @param tituloEjeY El tituloEjeY a establecer.
	 */
	public void setTituloEjeY(String tituloEjeY) {
		this.tituloEjeY = tituloEjeY;
	}
	

	/**
	 * @return Devuelve escalaTiempo.
	 */
	public String getEscalaTiempo() {
		return escalaTiempo;
	}
	/**
	 * @param escalaTiempo El escalaTiempo a establecer.
	 */
	public void setEscalaTiempo(String escalaTiempo) throws Exception {
		if (escalaTiempo.equals(DatosGrafico.DIAS) || escalaTiempo.equals(DatosGrafico.MESES) || escalaTiempo.equals(DatosGrafico.HORAS) ||
				escalaTiempo.equals(DatosGrafico.ANYOS) || escalaTiempo.equals(DatosGrafico.SEGUNDOS) || escalaTiempo.equals(DatosGrafico.MINUTOS))
			this.escalaTiempo = escalaTiempo;
		else
			throw new Exception("Escala de tiempo " + escalaTiempo + " no soportada");
	}
	
	public void setFormat(String escalaTiempo, String format)
	{
		formats.put(escalaTiempo,format);
	}
	
	public String getFormat(String escalaTiempo)
	{
		return (String) formats.get(escalaTiempo);
	}


  public void setLines(ArrayList lines)
  {
    this.lines = lines;
  }


  public ArrayList getLines()
  {
    return lines;
  }


  public void setColor_barra(String color_barra)
  {
    this.color_barra = color_barra;
  }


  public String getColor_barra()
  {
    return color_barra;
  }




		
}
