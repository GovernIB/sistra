package es.caib.consola.model;

/**
 * Unidad administrativa.
 * @author rsanz
 *
 */
public class UnidadAdministrativa {

	/**
	 * Codigo.
	 */
	private String codigo;
	/**
	 * Descripcion.
	 */
	private String descripcion;
	/**
	 * Codigo padre (null si root).
	 */
	private String codigoPadre;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigoPadre() {
		return codigoPadre;
	}
	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}
	/**
	 * Constructor.
	 */
	public UnidadAdministrativa() {
		super();
	}
	/**
	 * @param codigo
	 * @param descripcion
	 * @param codigoPadre
	 */
	public UnidadAdministrativa(String codigo, String descripcion,
			String codigoPadre) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.codigoPadre = codigoPadre;
	}
	
	
	
	
	
}
