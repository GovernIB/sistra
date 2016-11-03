package es.caib.sistra.plugins.gestorDocumental.impl.mock;

import java.io.Serializable;
import java.util.List;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.UsoRDS;

/**
 * Constants globals.
 */
public class Constants implements Serializable 
{
	/**
     * Atributo donde se guarda la carpeta Raiz del Repositorio Documental
     */
    public static final String DIRECTORIO_RAIZ 	= "/GestorDocumentalMock";
    
    /**
     * Atributo donde se guarda la carpeta Bandeja de entrada
     */
	public static final String BANDEJA_ENTRADA		= "Bandeja_Entrada";
	
	 /**
     * Atributo donde se guarda la carpeta Bandeja de salida
     */
	public static final String BANDEJA_SALIDA		= "Bandeja_Salida";

	
}
