package es.caib.redose.back;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable 
{
    /**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY = "es.caib.redose.back.DEFAULT_LANG";

    public static final String DEFAULT_LANG 		= "ca";	
    
    public static String DEFAULT_FORMATEADOR = "es.caib.redose.persistence.formateadores.FormateadorPdfFormularios";
}
