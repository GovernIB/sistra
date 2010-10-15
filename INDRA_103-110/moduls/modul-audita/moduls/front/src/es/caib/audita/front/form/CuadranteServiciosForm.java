package es.caib.audita.front.form;

import org.apache.struts.validator.ValidatorForm;

public class CuadranteServiciosForm extends ValidatorForm
{
	private String desde = null;
	private String opcion = null;
	private String evento = null;
	private String detalle = null;
	public String getDesde() {
		return desde;
	}
	public void setDesde(String desde) {
		this.desde = desde;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public String getOpcion() {
		return opcion;
	}
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
