package es.caib.sistra.back;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable
{
	/** Contexto raiz sistra */
	public static final String CONTEXTO_RAIZ_BACK = "es.caib.sistra.contextRootBack";

    /**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY = "es.caib.sistra.back.DEFAULT_LANG";

    /**
     * Lenguage por defecto
     */
    public static final String DEFAULT_LANG 		= "ca";


    /**
     * Atributo de sesion para almacenar el fichero xml de importacion que se esta tratando
     */
    public static final String XML_IMPORTACION_KEY 		= "es.caib.sistra.back.XML_IMPORTACION";

    /**
     * Atributo de contexto donde se indica si son obligatorias las notificaciones para los avisos
     */
    public static final String AVISOS_OBLIGATORIOS_NOTIFICACIONES = "es.caib.sistra.back.avisosObligatoriosNotificaciones";

    /**
     * Download fichero.
     */
	public static final String NOMBREFICHERO_KEY = "nombrefichero";
	public static final String DATOSFICHERO_KEY = "datosfichero";
}
