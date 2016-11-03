package es.caib.sistra.model;

public class TraEspecTramiteNivel  implements Traduccion {


    // Fields    
     private String instruccionesInicio;
     private String mensajeInactivo;
     private String instruccionesFin;
     private String instruccionesEntrega;
	 private String mensajeFechaLimiteEntregaPresencial;     
     
     
	public String getInstruccionesFin() {
		return instruccionesFin;
	}
	public void setInstruccionesFin(String instruccionesFin) {
		this.instruccionesFin = instruccionesFin;
	}
	public String getInstruccionesInicio() {
		return instruccionesInicio;
	}
	public void setInstruccionesInicio(String instruccionesInicio) {
		this.instruccionesInicio = instruccionesInicio;
	}	
	public String getMensajeInactivo() {
		return mensajeInactivo;
	}
	public void setMensajeInactivo(String mensajeInactivo) {
		this.mensajeInactivo = mensajeInactivo;
	}
	public String getInstruccionesEntrega() {
		return instruccionesEntrega;
	}
	public void setInstruccionesEntrega(String instruccionesEntrega) {
		this.instruccionesEntrega = instruccionesEntrega;
	}
	public String getMensajeFechaLimiteEntregaPresencial() {
		return mensajeFechaLimiteEntregaPresencial;
	}
	public void setMensajeFechaLimiteEntregaPresencial(
			String mensajeFechaLimiteEntregaPresencial) {
		this.mensajeFechaLimiteEntregaPresencial = mensajeFechaLimiteEntregaPresencial;
	}	
}
