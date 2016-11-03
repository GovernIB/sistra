package es.caib.consola.controller.gestor.model;

/**
 * Informacion plugin.
 */
public final class ModeloVisualizarPlugin {

	// TODO REVISAR
	
    /** Atributo nombre. */
    private String nombre;

    /** Atributo descripcion. */
    private String descripcion;

    /** Atributo nombre padre. */
    private String nombrePadre;

    /** Atributo expandido. */
    private boolean expandido;

    /** Atributo plugin de ModeloVisualizarPlugin. */
    private boolean plugin;

    /** Atributo dato de ModeloVisualizarPlugin. */
    private boolean dato;

    /** Atributo metodo de ModeloVisualizarPlugin. */
    private boolean metodo;

    /** Atributo tipo de ModeloVisualizarPlugin. */
    private String tipo;

    
    // RAFA: METO ESTAS
    /** Nombre fichero de ayuda.*/
    private String ficheroAyuda;
    
    /** Texto que se copiara.*/
    private String textoCopiar;
    
    /**
     * Gets the nombre.
     * 
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     * 
     * @param pNombre
     *            the new nombre
     */
    public void setNombre(final String pNombre) {
        this.nombre = pNombre;
    }

    /**
     * Gets the descripcion.
     * 
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets la descripcion.
     * 
     * @param pDescripcion
     *            .
     */
    public void setDescripcion(final String pDescripcion) {
        this.descripcion = pDescripcion;
    }

    /**
     * Obtiene el nombre del padre.
     * 
     * @return the nombre padre
     */
    public String getNombrePadre() {
        return nombrePadre;
    }

    /**
     * Set El nombre del padre.
     * 
     * @param pNombrePadre
     *            nombre padre.
     */
    public void setNombrePadre(final String pNombrePadre) {
        this.nombrePadre = pNombrePadre;
    }

    /**
     * Chekea si esta expandido.
     * 
     * @return true Si esta expandido.
     */
    public boolean isExpandido() {
        return expandido;
    }

    /**
     * Sets expandido.
     * 
     * @param pExpandido
     *            expandido.
     */
    public void setExpandido(final boolean pExpandido) {
        this.expandido = pExpandido;
    }

    /**
     * Comprueba si es true plugin de ModeloVisualizarPlugin.
     * 
     * @return true, si es plugin
     */
    public boolean isPlugin() {
        return plugin;
    }

    /**
     * Asigna el atributo plugin de ModeloVisualizarPlugin.
     * 
     * @param pPlugin
     *            el nuevo valor para plugin
     */
    public void setPlugin(final boolean pPlugin) {
        this.plugin = pPlugin;
    }

    /**
     * Comprueba si es true dato de ModeloVisualizarPlugin.
     * 
     * @return true, si es dato
     */
    public boolean isDato() {
        return dato;
    }

    /**
     * Asigna el atributo dato de ModeloVisualizarPlugin.
     * 
     * @param pDato
     *            el nuevo valor para dato
     */
    public void setDato(final boolean pDato) {
        this.dato = pDato;
    }

    /**
     * Comprueba si es true metodo de ModeloVisualizarPlugin.
     * 
     * @return true, si es metodo
     */
    public boolean isMetodo() {
        return metodo;
    }

    /**
     * Asigna el atributo metodo de ModeloVisualizarPlugin.
     * 
     * @param pMetodo
     *            el nuevo valor para metodo
     */
    public void setMetodo(final boolean pMetodo) {
        this.metodo = pMetodo;
    }

    /**
     * Obtiene el atributo tipo de ModeloVisualizarPlugin.
     * 
     * @return el atributo tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Asigna el atributo tipo de ModeloVisualizarPlugin.
     * 
     * @param pTipo
     *            el nuevo valor para tipo
     */
    public void setTipo(final String pTipo) {
        this.tipo = pTipo;
    }

	public String getFicheroAyuda() {
		return ficheroAyuda;
	}

	public void setFicheroAyuda(String ficheroAyuda) {
		this.ficheroAyuda = ficheroAyuda;
	}

	public String getTextoCopiar() {
		return textoCopiar;
	}

	public void setTextoCopiar(String textoCopiar) {
		this.textoCopiar = textoCopiar;
	}

}
