package es.indra.util.graficos.generadorGraficos;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Linea con los datos de una linea del grafico
 */
public class Linea {
	private String titulo;
	private ArrayList valoresX = new ArrayList();
	private ArrayList valoresY = new ArrayList();
  private Color color;
	
	public void addValor(String x,String y){
		valoresX.add(x);
		valoresY.add(y);
	}
		
	/**
	 * @return Devuelve titulo.
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo El titulo a establecer.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public int getTotalValores(){
		return valoresX.size();
	}
	
	public String getValorX(int i){
		return (String) valoresX.get(i);
	}
	
	public String getValorY(int i){
		return (String) valoresY.get(i);
	}
	
	public void deleteValor(int i){
		valoresX.remove(i);
		valoresY.remove(i);
	}


  public void setColor(Color color)
  {    
    this.color = color;
  }


  public Color getColor()
  {
    return color;
  }


  public void setValoresX(ArrayList valoresX)
  {
    this.valoresX = valoresX;
  }


  public ArrayList getValoresX()
  {
    return valoresX;
  }


  public void setValoresY(ArrayList valoresY)
  {
    this.valoresY = valoresY;
  }


  public ArrayList getValoresY()
  {
    return valoresY;
  }
	
}
