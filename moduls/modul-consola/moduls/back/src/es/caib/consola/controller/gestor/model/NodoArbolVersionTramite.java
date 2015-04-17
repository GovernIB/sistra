package es.caib.consola.controller.gestor.model;

/**
 * Nodo arbol.
 * @author rsanz
 *
 */
public class NodoArbolVersionTramite {
	
	/**
	 * Tipo nodo.
	 */
	private TypeNodoArbolVersionTramite tipo;
	
	/**
	 * Si procede identificador dato.
	 */
	private Long identificador;

	public TypeNodoArbolVersionTramite getTipo() {
		return tipo;
	}

	public void setTipo(TypeNodoArbolVersionTramite tipo) {
		this.tipo = tipo;
	}

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public NodoArbolVersionTramite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NodoArbolVersionTramite(TypeNodoArbolVersionTramite tipo,
			Long identificador) {
		super();
		this.tipo = tipo;
		this.identificador = identificador;
	} 
	
}
