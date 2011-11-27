package es.indra.util.pdf;

/**
 * 
 * Objeto para incrustar en pdf
 *
 */
public class ObjectStamp {
	/**
	 * Página en la que se muestra el texto (si 0 entonces se muestra en todas)
	 */
	private int page=0;
	/**
	 * Indica si se muestra encima del contenido o por debajo
	 */
	private boolean overContent=true;
	/**
	 * Coordenada X
	 */
	private float x;
	/**
	 * Coordenada Y
	 */
	private float y;
	/**
	 * Rotacion
	 */
	private float rotation;
	public boolean isOverContent() {
		return overContent;
	}
	public void setOverContent(boolean overContent) {
		this.overContent = overContent;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
}
