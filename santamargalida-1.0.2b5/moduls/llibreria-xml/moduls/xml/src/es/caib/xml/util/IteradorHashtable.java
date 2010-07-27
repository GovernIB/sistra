package es.caib.xml.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;

class IteradorHashtable implements Iterator, Serializable {
	Iterator iterador = null;
	
	IteradorHashtable (HashtableIterable hash){
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
