package org.ibit.rol.form.model;

public class ListaElementos extends Campo {
	
	public boolean isIndexed() {
		return false;
	}
	
	private boolean anchuraMaxima;

	public boolean isAnchuraMaxima() {
		return anchuraMaxima;
	}

	public void setAnchuraMaxima(boolean anchuraMaxima) {
		this.anchuraMaxima = anchuraMaxima;
	}
	
	

	
}
