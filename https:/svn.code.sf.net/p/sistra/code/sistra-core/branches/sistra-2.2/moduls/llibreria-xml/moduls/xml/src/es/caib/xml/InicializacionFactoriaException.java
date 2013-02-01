package es.caib.xml;


/** Excepción indicando errores de inicialización de la factoría de creación de objetos XML
 *
 * @author magroig
 *
 */
public class InicializacionFactoriaException extends XMLException {
	
	private String tipoImplementacion = "";
	
	/** Constructor de la excepción
	 * @param msg Cadena descriptiva del error
	 * @param tipoImplementacion Cadena indicando el tipo de implementación subyacente usado por la factoría
	 */
	public InicializacionFactoriaException(String msg, String tipoImplementacion){
		super ("[" + tipoImplementacion + "] " + msg);
		this.tipoImplementacion = tipoImplementacion;
	}

	public InicializacionFactoriaException(String msg, String tipoImplementacion,Throwable e){
		super ("[" + tipoImplementacion + "] " + msg,e);
		this.tipoImplementacion = tipoImplementacion;		
	}
	
	/** Retorna una cadena indicando el tipo de implementación subyacente usado
	 * por la factoria
	 * @return Tipo de implementación usado por la factoría
	 */
	public String getTipoImplementacion() {
		return tipoImplementacion;
	}
	
}
