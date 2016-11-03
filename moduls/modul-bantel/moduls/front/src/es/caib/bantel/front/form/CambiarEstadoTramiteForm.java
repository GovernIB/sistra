package es.caib.bantel.front.form;

public class CambiarEstadoTramiteForm extends InitForm
{
	private Long codigo;
	private char estado;
	private char estadoOld;

	public Long getCodigo()
	{
		return codigo;
	}

	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}

	public char getEstado()
	{
		return estado;
	}

	public void setEstado(char estado)
	{
		this.estado = estado;
	}

	public char getEstadoOld() {
		return estadoOld;
	}

	public void setEstadoOld(char estadoOld) {
		this.estadoOld = estadoOld;
	}
}
