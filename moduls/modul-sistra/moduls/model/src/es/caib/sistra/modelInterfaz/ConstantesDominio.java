package es.caib.sistra.modelInterfaz;

/**
 * Identificadores de dominios genericos utilizados en
 * la plataforma
 */
public class ConstantesDominio {
		
	// DOMINIOS ACCESO A ESTRUCTURA ORGANICA
	/**
	 * Lista de unidades administrativas
	 */
	public final static String DOMINIO_SAC_UNIDADES_ADMINISTRATIVAS = "GESACUNADM";
	/**
	 * Arbol de unidades administrativas
	 */
	public final static String DOMINIO_SAC_ARBOL_UNIDADES_ADMINISTRATIVAS = "GESACARBUA";
	/**
	 * Descripcion de unidad administrativa. Parametrizado por codigo unidad.
	 */
	public final static String DOMINIO_SAC_NOMBRE_UNIDAD_ADMINISTRATIVA = "GESACUADES";
	
	// DOMINIOS ACCESO RDS
	/**
	 * Lista de modelos de documentos del RDS
	 */
	public final static String DOMINIO_RDS_MODELOS = "GERDSMODE";
	/**
	 * Lista de versiones de un modelo de documento del RDS. Parametrizado por codigo modelo.
	 */
	public final static String DOMINIO_RDS_VERSIONES_MODELO = "GERDSVERS";
	
	// DOMINIOS ACCESO A FORMS
	/**
	 * Lista de modelos de formularios
	 */
	public final static String DOMINIO_FORMS_MODELOS = "GEFORMMODE";
	/**
	 * Lista de versiones de un formulario. Parametrizado por código de formulario.
	 */
	public final static String DOMINIO_FORMS_VERSIONES_MODELO = "GEFORMVERS";
	/**
	 * Arbol de formularios y versiones
	 */
	public final static String DOMINIO_FORMS_ARBOL_VERSIONES_MODELO = "GEFORMMOVE";	
	
	// DOMINIOS ACCESO A INFORMACION GEOGRAFICA
	/**
	 * Lista de paises
	 */
	public final static String DOMINIO_GTB_PAISES = "GEPAISES";
	/**
	 * Lista de provincias de España
	 */
	public final static String DOMINIO_GTB_PROVINCIAS = "GEPROVINCI";
	/**
	 * Lista de municipios de una provincia. Parametrizado por codigo provincia.
	 */
	public final static String DOMINIO_GTB_MUNICIPIOS_PROVINCIA	= "GEGMUNICI";
	/**
	 * Descripcion de un municipio. Parametrizado por codigo provincia y por codigo municipio
	 */
	public final static String DOMINIO_GTB_NOMBRE_MUNICIPIO = "GEMUNIDESC";
	
	
	
			
}
