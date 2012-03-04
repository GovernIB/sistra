package es.caib.sistra.back;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable 
{
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
}
