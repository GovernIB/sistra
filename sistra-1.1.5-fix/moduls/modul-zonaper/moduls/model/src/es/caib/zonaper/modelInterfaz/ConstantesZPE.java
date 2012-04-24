package es.caib.zonaper.modelInterfaz;

/**
 * Constantes Zona Personal
 *
 */
public class ConstantesZPE {
	
	/**
	 * Delegacion - Variable de sesion donde se guardara el perfil de acceso: ciudadano ("CIUDADANO") /  delegado entidad ("DELEGADO")
	 */	  
	public static String DELEGACION_PERFIL_ACCESO_KEY = "es.caib.zonaper.filter.PERFIL_ACCESO";
	/**
	 * Delegacion - Variable de sesion donde se guardara la entidad cuando se accede en el perfil de delegado entidad
	 */ 
	public static String DELEGACION_PERFIL_ACCESO_DELEGADO_ENTIDAD_KEY = "es.caib.zonaper.filter.ACCESO_DELEGADO_ENTIDAD";

	/**
	 * Delegacion - Perfil acceso ciudadano
	 */
	public static final String DELEGACION_PERFIL_ACCESO_CIUDADANO = "CIUDADANO";
	
	/**
	 * Delegacion - Perfil acceso delegado
	 */
	public static final String DELEGACION_PERFIL_ACCESO_DELEGADO = "DELEGADO";
	
	/**
	 * Delegacion - Permiso Representante entidad
	 */
	public final static String DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD = "R";
	
	/**
	 * Delegacion - Permiso rellenar trámites
	 */
	public final static String DELEGACION_PERMISO_RELLENAR_TRAMITE = "T";
	
	/**
	 * Delegacion - Permiso presentar trámites
	 */
	public final static String DELEGACION_PERMISO_PRESENTAR_TRAMITE = "P";
	
	/**
	 * Delegacion - Permiso abrir notificacion telematica
	 */
	public final static String DELEGACION_PERMISO_ABRIR_NOTIFICACION = "N";
	
	/**
	 * Subsanacion - key parameter de acceso a parametros
	 */
	public final static String SUBSANACION_PARAMETER_KEY = "subsanacionkey";
	
	

}
