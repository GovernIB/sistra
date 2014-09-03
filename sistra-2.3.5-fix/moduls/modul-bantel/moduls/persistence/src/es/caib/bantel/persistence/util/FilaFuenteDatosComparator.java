package es.caib.bantel.persistence.util;

import java.util.Comparator;

import es.caib.bantel.model.FilaFuenteDatos;

public class FilaFuenteDatosComparator implements Comparator {
	    
	private String idCampoOrden;
	
	public FilaFuenteDatosComparator(String pIdCampoOrden) {
		idCampoOrden = pIdCampoOrden;
	}
	
	public int compare(Object o1, Object o2) {
		
		FilaFuenteDatos f1 = (FilaFuenteDatos) o1;
		FilaFuenteDatos f2 = (FilaFuenteDatos) o2;
		
		String valor1 = FuenteDatosUtil.getValor(f1, idCampoOrden);
		String valor2 = FuenteDatosUtil.getValor(f2, idCampoOrden);
		
		int res;
		if (valor1 == null && valor2 != null) {
			res = -1;
		} else if (valor1 != null && valor2 == null) {
			res = 1;
		} else if (valor1 == null && valor2 == null) {
			res = 0;
		} else {
			res = valor1.compareTo(valor2);
		}
		
		return res;
	}
	
}