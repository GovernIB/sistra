package es.caib.sistra.admin.form;

import org.apache.struts.validator.ValidatorForm;

public class CuadernoCargaForm extends ValidatorForm
{
	private Long codigo;
	private String descripcion;
	private char estadoAuditoria;
	private String comentarioAuditoria;
	private char importado;
	private String rawFechaCarga;
	
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	public String getComentarioAuditoria()
	{
		return comentarioAuditoria;
	}
	public void setComentarioAuditoria(String comentarioAuditoria)
	{
		this.comentarioAuditoria = comentarioAuditoria;
	}
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public char getEstadoAuditoria()
	{
		return estadoAuditoria;
	}
	public void setEstadoAuditoria(char estadoAuditoria)
	{
		this.estadoAuditoria = estadoAuditoria;
	}
	public char getImportado()
	{
		return 'S' == importado  ? importado : 'N';
	}
	public void setImportado(char importado)
	{
		this.importado = importado;
	}
	public String getRawFechaCarga()
	{
		return rawFechaCarga;
	}
	public void setRawFechaCarga(String rawFechaCarga)
	{
		this.rawFechaCarga = rawFechaCarga;
	}
}
