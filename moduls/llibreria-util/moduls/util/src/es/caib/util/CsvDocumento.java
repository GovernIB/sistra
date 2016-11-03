package es.caib.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modeliza documento CSV.
 * Empieza con fila 0.
 * 
 * @author rsanz
 *
 */
public class CsvDocumento {

	private String[] columnas;
	
	private List filas = new ArrayList();


	public int addFila() {
		filas.add(new HashMap());
		return filas.size() - 1;
	}
	
	public int getNumeroFilas() {
		return filas.size();
	}
	
	public void setValor(int fila, String columna, String valor) throws Exception {
		checkExisteDato(fila, columna);
		Map valores = (Map) filas.get(fila);
		valores.put(columna, valor);
	}

	public String getValor(int fila, String columna) throws Exception {
		checkExisteDato(fila, columna);
		Map valores = (Map) filas.get(fila);
		return (String) valores.get(columna);
	}
	

	public String[] getColumnas() {
		return columnas;
	}

	public void setColumnas(String[] columnas) {
		this.columnas = columnas;
	}

	public List getFilas() {
		return filas;
	}

	public void setFilas(List filas) {
		this.filas = filas;
	}
	
	public String print() throws Exception {
		StringBuffer sb = new StringBuffer(8192);
		for (int fila = 0; fila < getNumeroFilas(); fila++) {
			sb.append("FILA: " + fila + "\n");
			for (int columna = 0; columna < getColumnas().length; columna++) {
			String col = getColumnas()[columna];
			sb.append("	> COLUMNA: " + col + " = " + getValor(fila, col) + "\n");	
			}
		}
		return sb.toString();
		
	}
	
	private void checkExisteDato(int fila, String columna) throws Exception {
		if (fila >= filas.size()) {
			throw new Exception("No existe fila " + fila);
		}
		boolean enc = false;
		for (int i = 0; i < columnas.length; i++) {
			if (columnas[i].equals(columna)) {
				enc = true;
				break;
			}
		}
		if (!enc) {
			throw new Exception("No existe columna " + columna);
		}
	}
		
}
