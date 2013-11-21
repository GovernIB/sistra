package es.caib.bantel.model;


public class FiltroConsultaFuenteDatos {
	
	public static final String IGUAL = "=";
	
	public static final String LIKE = "LIKE";
	
	public static final String AND = "AND";
	
	public static final String OR = "OR";
	
	private String campo;
	
	private String operador;
	
	private String conector;

	public String getConector() {
		return conector;
	}

	public void setConector(String conector) {
		this.conector = conector;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}


	
	
	
}
