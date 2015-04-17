/**
 * 
 * @author Indra
 */
package es.caib.zkutils;

/**
 * Class ConstantesWeb.
 */
public final class ConstantesZK {
    // Locales

    /**
     * Instancia un nuevo constantes web de ConstantesWeb.
     */
    private ConstantesZK() {
    };
    
    // --- Eventos
    /** Evento POST MODIFICACION. */
    public static final String EVENTO_POST_MODIFICACION = "onPostModificacion";
    /** Evento POST ALTA. */
    public static final String EVENTO_POST_ALTA = "onPostAlta";
    /** Evento POST CONFIRMAR BORRADO. */
    public static final String EVENTO_POST_CONFIRMAR_BORRADO = "onPostConfirmarBorrado";    
    /** Evento SELECCION SELECTOR. */
    public static final String EVENTO_SELECCION_SELECTOR = "onSeleccionSelector";  
    
    // --- Seleccion grids/combo
    /** Valor selected index si no hay elemento seleccionado. */
    public static final int SINSELECCION = -1;
    /** Texto a mostrar si no hay elemento seleccionado. */
    public static final String SINSELECCION_LABEL = "error.noSeleccion";

    /**
     * Atributo constante LOCALE_CASTELLANO.
     */
    public static final String LOCALE_CASTELLANO = "es_ES";

    /**
     * Atributo constante LOCALE_VALENCIANO.
     */
    public static final String LOCALE_VALENCIANO = "ca_ES";

    // Formato fechas

    /**
     * Atributo constante MENOSUNO.
     */
    public static final int MENOSUNO = -1;

    /**
     * Atributo constante CERO.
     */
    public static final int CERO = 0;

    /**
     * Atributo constante UNIDAD.
     */
    public static final int UNIDAD = 1;

    /**
     * Atributo constante UNCHECKED.
     */
    public static final String UNCHECKED = "unchecked";
    
    /**
     * Atributo constante DOS de ConstantesWeb.
     */
    public static final int DOS = 2;
}
