package es.caib.xml.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/** 
 * HashMap que ademas, implementa la interfaz Iterator para poder
 * recorrer sus elementos en el mismo orden en el que fueron insertados
 * 
 * @author mangel
 *
 */
public class HashMapIterable extends HashMap implements Serializable {
	List lista = new ArrayList ();	
	 
	public Object put (Object key, Object value) throws NullPointerException {
		Object anterior = super.put (key, value);
		
		if (anterior != null){
			lista.remove (anterior);
		}
		
		lista.add (value);
		
		return anterior;
	}
		
	
	public Object putAtIndex (Object key, Object value, int index){
		if ( (index < 0) || (index > lista.size())){
			throw new IndexOutOfBoundsException ();
		}
		
		if ( (key == null) || (value == null) ){
			throw new NullPointerException ();
		}
		
		Object anterior = super.put (key, value);
		
		if (anterior != null){
			lista.remove (anterior);
		}
		
		lista.add (index, value);
						
		return anterior;						
	}
	
	public Object remove (Object key) throws NullPointerException {
		Object anterior = super.remove (key);
		
		if (anterior != null) lista.remove (anterior);
		
		return null;
	}
	
	public Iterator iterator (){
		return new IteradorHashMap (this);
	}
	
}
