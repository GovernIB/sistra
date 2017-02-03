package es.caib.bantel.back.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UtilArbol {

	
	/**
	 * Prepara el arbol rellenando los id padres y reordenando nodos.
	 * @param nodos Nodos
	 */
	public static List prepararArbol(List nodos) {
		
		List result = null;
		
		if (nodos != null) {
			
			// Establece id padres
			for (Iterator it = nodos.iterator(); it.hasNext();) {
				NodoArbol n = (NodoArbol) it.next();							
				if (n.getParentId() != null) {										
					NodoArbol np = getNodo(nodos, n.getParentId());
					n.setIdCampoPadre(np.getIdCampo());			
				}				
			}
			
			// Reordena nodos
			result = new ArrayList();
			insertaHijos(nodos, result, null);
			
		}
		
		return result;
	}

	public static NodoArbol getNodo(List nodos, String id) {
		NodoArbol nodo = null;
		if (nodos != null && id != null) {
			for (Iterator it = nodos.iterator(); it.hasNext();) {
				NodoArbol n = (NodoArbol) it.next();
				if (n.getId().equals(id)) {
					nodo = n;
					break;
				}
			}
		}
		return nodo;
	}
	
	private static List<NodoArbol> getHijos(List nodos, NodoArbol nodoPadre) {
		List<NodoArbol> result = new ArrayList<NodoArbol>();
		for (Iterator it = nodos.iterator(); it.hasNext();) {
			NodoArbol n = (NodoArbol) it.next();
			if (nodoPadre != null) {
				if (nodoPadre.getId().equals(n.getParentId())) {
					result.add(n);
				}
			} else {
				if (n.getParentId() == null) {
					result.add(n);
				}
			}
		}
		return result;
	}
	
	private static void insertaHijos(List nodos, List<NodoArbol> resultado, NodoArbol nodoPadre) {
		List<NodoArbol> nodosHijo = getHijos(nodos, nodoPadre);
		if (nodosHijo != null) {
			for (NodoArbol n : nodosHijo) {
				resultado.add(n);
				insertaHijos(nodos, resultado, n);
			}		
		}
	}
	
	
	
	
}
