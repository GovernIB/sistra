package es.indra.util.pdf;

/**
 * 
 * Imagen para realizar stamp en un pdf
 *
 */
public class ImageStamp extends ObjectStamp{
	
	/**
	 * Imagen a mostrar
	 */
	private byte[] imagen;
	
	/**
	 * Indica si al escalar debe realizar scalePerCent en lugar de scaleToFit
	 */
	private boolean scalePerCent=false;
	
	/**
	 * Escala X
	 */
	private Float xScale=null;
	
	/**
	 * Escala Y
	 */
	private Float yScale=null;
	
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public Float getXScale() {
		return xScale;
	}
	public void setXScale(Float scale) {
		xScale = scale;
	}
	public Float getYScale() {
		return yScale;
	}
	public void setYScale(Float scale) {
		yScale = scale;
	}
	public boolean isScalePerCent() {
		return scalePerCent;
	}
	public void setScalePerCent(boolean scalePerCent) {
		this.scalePerCent = scalePerCent;
	}
	
	
}
