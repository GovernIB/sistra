package es.caib.zonaper.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Parametros subsanacion
 *
 */
public class ParametrosSubsanacion implements Serializable 
{	
	private String codigo;
	private Long expedienteUnidadAdministrativa;
	private String expedienteCodigo;
	private String parametros;
	private Date fecha;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getExpedienteCodigo() {
		return expedienteCodigo;
	}
	public void setExpedienteCodigo(String expedienteCodigo) {
		this.expedienteCodigo = expedienteCodigo;
	}
	public Long getExpedienteUnidadAdministrativa() {
		return expedienteUnidadAdministrativa;
	}
	public void setExpedienteUnidadAdministrativa(
			Long expedienteUnidadAdministrativa) {
		this.expedienteUnidadAdministrativa = expedienteUnidadAdministrativa;
	}
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
		
	
	
}
