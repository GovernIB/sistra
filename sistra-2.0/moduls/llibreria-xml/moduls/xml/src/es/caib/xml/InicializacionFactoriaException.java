package es.caib.xml;


/** Excepci�n indicando errores de inicializaci�n de la factor�a de creaci�n de objetos XML
 *
 * @author magroig
 *
 */
public class InicializacionFactoriaException extends XMLException {
	
	private String tipoImplementacion = "";
	
	/** Constructor de la excepci�n
	 * @param msg Cadena descriptiva del error
	 * @param tipoImplementacion Cadena indicando el tipo de implementaci�n subyacente usado por la factor�a
	 */
	public InicializacionFactoriaException(String msg, String tipoImplementacion){
		super ("[" + tipoImplementacion + "] " + msg);
		this.tipoImplementacion = tipoImplementacion;
	}

	public InicializacionFactoriaException(String msg, String tipoImplementacion,Throwable e){
		super ("[" + tipoImplementacion + "] " + msg,e);
		this.tipoImplementacion = tipoImplementacion;		
	}
	
	/** Retorna una cadena indicando el tipo de implementaci�n subyacente usado
	 * por la factoria
	 * @return Tipo de implementaci�n usado por la factor�a
	 */
	public String getTipoImplementacion() {
		return tipoImplementacion;
	}
	
}
