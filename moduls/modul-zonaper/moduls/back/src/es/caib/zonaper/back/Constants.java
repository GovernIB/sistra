package es.caib.zonaper.back;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable {

    /**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY = "es.caib.zonaper.back.DEFAULT_LANG";
    
    /**
     * Atributo de sesion donde se guarda el nombre del usuario
     */
    public static final String NOMBRE_USUARIO_KEY = "es.caib.zonaper.back.NOMBRE_USUARIO";
    
    public static final String DEFAULT_LANG = "ca";
    
	public static final String NOMBREFICHERO_KEY = "nombrefichero";
	public static final String DATOSFICHERO_KEY = "datosfichero";
	
	public static final String MESSAGE_KEY = "message";
	public static final String MESSAGE_ACTION_KEY = "messageAction";
	public static final String MESSAGE_ACTION_PARAMS_KEY = "messageActionParams";
	public static final String MESSAGE_ACTION_LABEL_KEY = "messageActionLabelKey";

	public static final String NOMBRE_FICHERO_SELLO = "registre<0>_<1>_<2>.pdf";
	
	public static final String IMPRESION_SELLO_KEY = "es.caib.zonaper.back.SELLO@";
	 
	public static final String CODIGO_PAIS_ESPANYA = "ESP";
	public static final String CODIGO_PROVINCIA_BALEARES = "7";

	/**
	 * Atributo de sesion donde se guarda la url de la aplicacion de registro existente 
	 */	
	public static final String URL_APLICACION_REGISTRO = "es.caib.zonaper.back.urlAplicacionRegistro";
	
}

