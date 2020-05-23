package es.caib.sistra.plugins.login;

/**
 * Tipo propiedad.
 *
 * @author Indra
 *
 */
public enum TypePropiedad {
	/**
	 * String.
	 */
	TEXTO("TEXTO"),
	/**
	 * Fecha (formato DD/MM/YYYY HH24:MI:SS).
	 */
	FECHA("FECHA"),
	/**
	 * XML B64.
	 */
	XML_B64("XMLB64");

	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypePropiedad(final String value) {
		stringValue = value;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Método para From string de la clase TypeEstadoDocumento.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type estado documento
	 */
	public static TypePropiedad fromString(final String text) {
		TypePropiedad respuesta = null;
		if (text != null) {
			for (final TypePropiedad b : TypePropiedad.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
