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
	
	/**
	 * 	ESTADOS DE UN EXPEDIENTE: Solicitud enviada.
	 */
	public final static String ESTADO_SOLICITUD_ENVIADA = "SE";
	/**
	 * 	ESTADOS DE UN EXPEDIENTE: Solicitud enviada pendiente preregistro.
	 */
	public final static String ESTADO_SOLICITUD_ENVIADA_PENDIENTE_DOCUMENTACION_PRESENCIAL = "SP";
	/**
	 * 	ESTADOS DE UN EXPEDIENTE: Aviso pendiente.
	 */
	public final static String ESTADO_AVISO_PENDIENTE = "AP";
	/**
	 * 	ESTADOS DE UN EXPEDIENTE: Aviso recibido.
	 */
	public final static String ESTADO_AVISO_RECIBIDO = "AR";
	/**
	 * 	ESTADOS DE UN EXPEDIENTE: Notificacion pendiente.
	 */
	public final static String ESTADO_NOTIFICACION_PENDIENTE = "NP";
	/**
	 * 	ESTADOS DE UN EXPEDIENTE: Notificacion recibida.
	 */
	public final static String ESTADO_NOTIFICACION_RECIBIDA = "NR";
	/**
	 * 	ESTADOS DE UN EXPEDIENTE: Notificacion rechazada.
	 */
	public final static String ESTADO_NOTIFICACION_RECHAZADA = "NX";
	/**
	 * EXPRESION REGULAR PARA IDENTIFICADOR EXPEDIENTE.
	 */
	public final static String REGEXP_IDENTIFICADOREXPEDIENTE = "^[A-Z|a-z|0-9|/|-]{1,50}$";
	/**
	 * TIPO FIRMA ACUSE: CERTIFICADO.
	 */
	public final static String TIPOFIRMAACUSE_CERTIFICADO = "CER";
	/**
	 * TIPO FIRMA ACUSE: CLAVE.
	 */
	public final static String TIPOFIRMAACUSE_CLAVE = "CLA";
	
}
