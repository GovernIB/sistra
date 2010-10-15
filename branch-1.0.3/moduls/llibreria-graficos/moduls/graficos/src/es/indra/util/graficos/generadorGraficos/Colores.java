package es.indra.util.graficos.generadorGraficos;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Clase para manejar los colores
 */
public class Colores  
{
  private static HashMap listaColores = null;
  private static HashMap listaColoresGradiente = null;
  
  public Colores()
  {
  }
  
  private static void populate()
  {
    // Establecemos lista colores
    listaColores = new HashMap();
    listaColores.put("AZUL", new Color(3825613));//Color.blue);
    listaColores.put("VERDE",new Color(8190976));//Color.green);
    listaColores.put("ROJO",new Color(16728128));//Color.red);
    listaColores.put("GRIS",Color.LIGHT_GRAY);  
    listaColores.put("NARANJA",Color.orange);
    listaColores.put("ROSA",Color.pink);
    listaColores.put("AMARILLO",Color.yellow);
    listaColores.put("CYAN",Color.cyan);
    
    // Colores gradiente
    listaColoresGradiente = new HashMap();
    listaColoresGradiente.put("AZUL",new Color(139));
    listaColoresGradiente.put("VERDE",new Color(25600));
    listaColoresGradiente.put("ROJO",new Color(9118499));
    listaColoresGradiente.put("GRIS",new Color(6052956));
    listaColoresGradiente.put("NARANJA",new Color(13789470));
    listaColoresGradiente.put("ROSA",new Color(9124450));
    listaColoresGradiente.put("AMARILLO",new Color(15658496));
    listaColoresGradiente.put("CYAN",new Color(7120589));    
  }
  
  public static Color getColor(String key)
  {    
    if (listaColores == null) populate();    
    return (Color) listaColores.get(key.toUpperCase().trim());
  }
  
  public static Color getColorGradiente(String key)
  {    
    if (listaColores == null) populate();    
    return (Color) listaColoresGradiente.get(key.toUpperCase().trim());
  }
  
  public static String[] getColores()
  {     
     if (listaColores == null) populate(); 
     String [] ls_colores;
     ls_colores = new String[listaColores.size()];         
     Iterator it = listaColores.keySet().iterator();     
     int i=0;
     while (it.hasNext())
     {
        ls_colores[i] = (String) it.next();
        i++;
     }
     return ls_colores;
  
  }
}