package es.caib.zonaper.front.form;

public class DetalleElementoForm extends InitForm
{
	private Long codigo;
	private String tipo;
	private boolean expediente;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public boolean isExpediente() {
		return expediente;
	}
	public void setExpediente(boolean existeExpe) {
		this.expediente = existeExpe;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	
	
}
