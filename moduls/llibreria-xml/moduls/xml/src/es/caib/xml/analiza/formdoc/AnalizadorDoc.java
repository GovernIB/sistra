package es.caib.xml.analiza.formdoc;

import java.io.InputStream;

import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;

/**
 * Analiza un formulario implementado mediante un documento (word, odt,...).
 * @author rsanz
 *
 */
public class AnalizadorDoc {

	/**
	 * Analiza documento y obtiene un map con los xpaths de los valores.
	 * @param contenido
	 * @param tipoDocumento tipo documento: docx, odt, ...
	 * @return Map con los xpaths del formulario.
	 */
	public HashMapIterable analizar(InputStream contenido, String tipoDocumento) throws AnalizadorDocException {
	
		// TODO IMPLEMENTAR
	
		// Control tipos validos
		if ("docx".equals(tipoDocumento)) {
			throw new AnalizadorDocException("El tipo de documento " + tipoDocumento + " no es valido");
		}
		
		// Interpretar documento y devolver xpaths
		try {
			Nodo nodo1 = new Nodo("/FORMULARIO/CAMPO1", "VALOR CAMPO 1");
			Nodo nodo2 = new Nodo("/FORMULARIO/CAMPO2", "VALOR CAMPO 2");
			Nodo nodo3 = new Nodo("/FORMULARIO/CAMPO3", "VALOR CAMPO 3");
					
			HashMapIterable hojas = new HashMapIterable();
			hojas.put(nodo1.getXpath(),nodo1);
			hojas.put(nodo2.getXpath(),nodo2);
			hojas.put(nodo3.getXpath(),nodo3);
			
			return hojas;
		} catch (Exception ex) {
			throw new AnalizadorDocException("Se ha producido una excepcion al interpretar documento: " + ex.getMessage(), ex);
		}
		
	}
	
}
