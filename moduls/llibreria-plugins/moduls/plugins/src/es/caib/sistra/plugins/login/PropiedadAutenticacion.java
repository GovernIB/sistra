 package es.caib.sistra.plugins.login;



/**
 * Propiedad autenticacion.
 *
 * @author Indra
 *
 */
public class PropiedadAutenticacion {

	/** Propiedad. */
	private String propiedad;

	/** Tipo. */
	private TypePropiedad tipo;

	/** Valor. */
	private String valor;

	/** Permite indicar si se deberia mostar o no al usuario. */
	private boolean mostrar;

	/**
	 * Constructor.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @param tipo
	 *                      tipo
	 * @param valor
	 *                      valor
	 * @param mostrar
	 *                      mostrar
	 */
	public PropiedadAutenticacion(final String propiedad, final TypePropiedad tipo, final String valor,
			final boolean mostrar) {
		super();
		this.propiedad = propiedad;
		this.tipo = tipo;
		this.valor = valor;
		this.mostrar = mostrar;
	}

	/**
	 * Constructor.
	 */
	public PropiedadAutenticacion() {
		super();
	}

	/**
	 * Metodo de acceso a propiedad.
	 *
	 * @return propiedad
	 */
	public String getPropiedad() {
		return propiedad;
	}

	/**
	 * Metodo para establecer propiedad.
	 *
	 * @param propiedad
	 *                      propiedad a establecer
	 */
	public void setPropiedad(final String propiedad) {
		this.propiedad = propiedad;
	}

	/**
	 * Metodo de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypePropiedad getTipo() {
		return tipo;
	}

	/**
	 * Metodo para establecer tipo.
	 *
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final TypePropiedad tipo) {
		this.tipo = tipo;
	}

	/**
	 * Metodo de acceso a valor.
	 *
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Metodo para establecer valor.
	 *
	 * @param valor
	 *                  valor a establecer
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * Metodo de acceso a mostrar.
	 *
	 * @return mostrar
	 */
	public boolean isMostrar() {
		return mostrar;
	}

	/**
	 * Metodo para establecer mostrar.
	 *
	 * @param mostrar
	 *                    mostrar a establecer
	 */
	public void setMostrar(final boolean mostrar) {
		this.mostrar = mostrar;
	}

}
