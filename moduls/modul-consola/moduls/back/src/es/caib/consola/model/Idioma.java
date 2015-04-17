package es.caib.consola.model;

/**
 * Idiomas de la plataforma.
 * 
 * @author indra
 * 
 */
public final class Idioma {

    /**
     * codigo.
     */
    private String codigo;
    /**
     * Descripción.
     */
    private String descripcion;

    /**
     * Método de acceso a codigo.
     * 
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para settear el campo codigo.
     * 
     * @param pCodigo
     *            the codigo to set
     */
    public void setCodigo(final String pCodigo) {
        codigo = pCodigo;
    }

    /**
     * Método de acceso a descripcion del idioma.
     * 
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para settear el campo descripcion del idioma.
     * 
     * @param pDescripcion
     *            the descripcion to set
     */
    public void setDescripcion(final String pDescripcion) {
        descripcion = pDescripcion;
    }

}
