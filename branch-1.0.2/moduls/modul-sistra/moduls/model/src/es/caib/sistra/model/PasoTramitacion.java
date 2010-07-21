package es.caib.sistra.model;

import java.io.Serializable;

/**
 *	Información relativa a un paso de tramitación 
 */
public class PasoTramitacion  implements Serializable{
		
	/**
	 * Se ha completado totalmente el paso
	 */
	public static final String ESTADO_COMPLETADO = "S";
	/**
	 * El usuario que tiene actualmente el trámite debe realizar acciones sobre el paso
	 */
	public static final String ESTADO_PENDIENTE = "N";
	/**
	 * El usuario que tiene actualmente el trámite ha realizado todas sus acciones sobre el paso pero 
	 * queda que otros usuarios realicen otras acciones
	 */
	public static final String ESTADO_PENDIENTE_FLUJO = "F";
	
	
	public static final int PASO_PASOS = 0;
	public static final int PASO_DEBESABER = 1;	
	public static final int PASO_RELLENAR = 2;
	public static final int PASO_PAGAR = 3;
	public static final int PASO_ANEXAR = 4;	
	public static final int PASO_REGISTRAR = 6;	
	//public static final int PASO_JUSTIFICANTE = 7;  YA NO EXISTE
	public static final int PASO_FINALIZAR = 8;
	public static final int PASO_IMPRIMIR = 9;  // Resultado asistente
	
		
	private int tipoPaso;
	
	//private boolean completado=false;
	// TriEstado: Completado (S) / Pendiente (N) / Pendiente Flujo (F)
	private String completado=ESTADO_PENDIENTE;
	
	
//	 CAMPOS PARA TEXTOS DE PRESENTACIÓN
	
	/**
	 * Indica cual es la key del fichero de mensajes que contiene el texto de la pestaña del paso
	 */
	private String claveTab;
	/**
	 * Indica cual es la key del fichero de mensajes que contiene el titulo que aparecera en la pagina de pasos  
	 */
	private String claveTitulo;
	/**
	 * Indica cual es la key del fichero de mensajes que contiene el cuerpo del texto
	 */
	private String claveCuerpo;
		
	public int getTipoPaso() {
		return tipoPaso;
	}
	public void setTipoPaso(int tipoPaso) {
		this.tipoPaso = tipoPaso;
	}
	public String getClaveCuerpo()
	{
		return claveCuerpo;
	}
	public void setClaveCuerpo(String claveCuerpo)
	{
		this.claveCuerpo = claveCuerpo;
	}
	public String getClaveTitulo()
	{
		return claveTitulo;
	}
	public void setClaveTitulo(String claveTitulo)
	{
		this.claveTitulo = claveTitulo;
	}
	public String getClaveTab()
	{
		return claveTab;
	}
	public void setClaveTab(String claveTab)
	{
		this.claveTab = claveTab;
	}
	public String getCompletado() {
		return completado;
	}
	public void setCompletado(String completado) {
		this.completado = completado;
	}
	
}
