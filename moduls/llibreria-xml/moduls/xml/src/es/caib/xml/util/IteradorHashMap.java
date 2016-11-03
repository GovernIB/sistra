package es.caib.xml.util;

import java.util.Iterator;

class IteradorHashMap implements Iterator {
	Iterator iterador = null;
	
	IteradorHashMap (HashMapIterable hash){
		iterador = hash.lista.iterator();
	}
	

	public boolean hasNext() {		
		return iterador.hasNext ();
	}

	public Object next() {		
		return iterador.next();
	}

	// No permitiremos eliminar elementos desde nuestro iterador
	public void remove() {
		throw new UnsupportedOperationException ();
	}

}
