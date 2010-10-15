package es.indra.util.graficos.generadorGraficos;


import java.awt.Color;
import java.util.Vector;
import java.util.ArrayList;

import es.indra.util.graficos.util.FuncionesCadena;

public class ConfiguracionGrafico 
{
 /**
   * Indica el nombre del fichero a generar
   */
  private String fichero;
  /**
   * Alto imagen
   */
  private int alto;
  /**
   * Ancho imagen
   */
  private int ancho;
  /**
   * Colores de las series
   */
  private Vector colorSeries = new Vector();  
  /**
   * Indica si se genera la leyenda
   */
  private boolean leyenda;
  /**
   * Indica el valor máximo del eje y
   */
  private String maximoY;/**
   * Indica el valor mínimo del eje y
   */
  private String minimoY;
  /**
   * Indica el valor máximo del eje x
   */
  private String maximoX;/**
   * Indica el valor mínimo del eje x
   */
  private String minimoX;  
  
  public ConfiguracionGrafico()
  {
  }


  public void setAlto(int alto)
  {
    this.alto = alto;
  }


  public int getAlto()
  {
    return alto;
  }


  public void setAncho(int ancho)
  {
    this.ancho = ancho;
  }


  public int getAncho()
  {
    return ancho;
  }


  public void setLeyenda(boolean leyenda)
  {
    this.leyenda = leyenda;
  }


  public boolean isLeyenda()
  {
    return leyenda;
  }


  public void setFichero(String fichero)
  {
    this.fichero = fichero;
  }


  public String getFichero()
  {
    return fichero;
  }
  
  /**
   * Establece los colores de las series
   * @param as_coloresSeries RGB de cada serie en el formato ColorSerie1-ColorSerie2...
   * Los colores permitidos son:  AZUL-VERDE-ROJO-GRIS-NARANJA-ROSA-AMARILLO-CYAN
   */
  public void setColorSeries(String as_coloresSeries)
  {
    try{
      String ls_rgbs[] = FuncionesCadena.split(as_coloresSeries,"-");
      int li_rgb;
      this.getColorSeries().clear();
      for (int i = 0; i < ls_rgbs.length ; i++)
      {          
          this.getColorSeries().add(ls_rgbs[i]);
      }
    }catch(Exception e)
    {
      // No establecemos colores
    } catch (Throwable e) {
		// TODO Bloque catch generado automáticamente
		e.printStackTrace();
	}
    
  }


  public Vector getColorSeries()
  {
    return colorSeries;
  }


  public void setMaximoY(String maximoY)
  {
    this.maximoY = maximoY;
  }


  public String getMaximoY()
  {
    return maximoY;
  }


  public void setMinimoY(String minimoY)
  {
    this.minimoY = minimoY;
  }


  public String getMinimoY()
  {
    return minimoY;
  }


  public void setMaximoX(String maximoX)
  {
    this.maximoX = maximoX;
  }


  public String getMaximoX()
  {
    return maximoX;
  }


  public void setMinimoX(String minimoX)
  {
    this.minimoX = minimoX;
  }


  public String getMinimoX()
  {
    return minimoX;
  }

  
}