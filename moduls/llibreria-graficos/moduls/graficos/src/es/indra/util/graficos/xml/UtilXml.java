package es.indra.util.graficos.xml;

import org.w3c.dom.Node;

/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UtilXml extends Object{





    public static  Node getNextSibling(Node nodo)
    {
      Node nodoNormalizado = nodo.getNextSibling();
      try
      {
        while (nodoNormalizado != null && nodoNormalizado.getNodeType() == Node.TEXT_NODE &&
               nodoNormalizado.getNodeValue().trim().equals(""))
        {
          nodoNormalizado = nodoNormalizado.getNextSibling();
        }
      }
      catch (Throwable e)
      {
      	
      }
      return nodoNormalizado;
    }

    // En caso de que el �rbol XML no fuera can�nico, se crear�an
    // nodos de texto vac�os, para solventar este problema, 
    // las funciones b�sicas de recorrido del �rbol, no procesar�n
    // estas ramas
    public static  Node getFirstChild(Node nodo)
    {
      Node nodoNormalizado = nodo.getFirstChild();
      try
      {
        while (nodoNormalizado != null && nodoNormalizado.getNodeType() == Node.TEXT_NODE &&
               nodoNormalizado.getNodeValue().trim().equals(""))
        {
          nodoNormalizado = nodoNormalizado.getNextSibling();
        }
      }
      catch (Throwable e)
      {
      }
      return nodoNormalizado;
    }
    
    public static boolean isWhiteNode(Node nodo)
    {
      if (nodo.getNodeType() == Node.TEXT_NODE &&
             nodo.getNodeValue().trim().equals(""))
      {
        return true;
      }
      else
        return false;
      
      
    }
    


   
}    