package es.caib.zonaper.helpdesk.front.form;

import org.apache.struts.validator.ValidatorForm;

public class ConsultaTramitacionForm extends ValidatorForm
{
		
	private String fecha;
	private String horaInicial;
	private String minInicial;
	private String horaFinal;
	private String minFinal;
	private String usuarioNif;
	private String clavePersistencia;
	private String idioma;
	private char nivelAutenticacion;
	
	public String getClavePersistencia() {
		return clavePersistencia;
	}
	public void setClavePersistencia(String clavePersistencia) {
		this.clavePersistencia = clavePersistencia;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
	public String getHoraInicial() {
		return horaInicial;
	}
	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}
	public String getUsuarioNif() {
		return usuarioNif;
	}
	public void setUsuarioNif(String usuarioNif) {
		this.usuarioNif = usuarioNif;
	}
	public String getMinFinal() {
		return minFinal;
	}
	public void setMinFinal(String minFinal) {
		this.minFinal = minFinal;
	}
	public String getMinInicial() {
		return minInicial;
	}
	public void setMinInicial(String minInicial) {
		this.minInicial = minInicial;
	}
	

}
