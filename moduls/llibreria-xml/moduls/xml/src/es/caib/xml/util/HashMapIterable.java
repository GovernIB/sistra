package es.caib.xml.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

/** 
 * HashMap que ademas, implementa la interfaz Iterator para poder
 * recorrer sus elementos en el mismo orden en el que fueron insertados
 * 
 * @author mangel
 *
 */
public class HashMapIterable extends LinkedHashMap implements Serializable {

	public Iterator iterator() {
		return super.keySet().iterator();
	}

}
