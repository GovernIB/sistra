package es.caib.audita.modelInterfaz;

import java.io.Serializable;
import java.util.Date;

public class Evento implements Serializable
{
	private long codigo;
	private Date fecha;
	private String tipo;
	private String descripcion;
	private String nivelAutenticacion;
	private String usuarioSeycon;
	private String numeroDocumentoIdentificacion;
	private String nombre;
	private String idioma;
	private String resultado;
	private String modeloTramite;
	private int versionTramite;
	private String idPersistencia;
	private String clave;
	private String procedimiento;
	
	public String getClave()
	{
		return clave;
	}
	public void setClave(String clave)
	{
		this.clave = clave;
	}
	public long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(long codigo)
	{
		this.codigo = codigo;
	}
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public Date getFecha()
	{
		return fecha;
	}
	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}
	public String getIdioma()
	{
		return idioma;
	}
	public void setIdioma(String idioma)
	{
		this.idioma = idioma;
	}
	public String getIdPersistencia()
	{
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia)
	{
		this.idPersistencia = idPersistencia;
	}
	public String getModeloTramite()
	{
		return modeloTramite;
	}
	public void setModeloTramite(String modeloTramite)
	{
		this.modeloTramite = modeloTramite;
	}
	public String getNivelAutenticacion()
	{
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(String nivelAutenticacion)
	{
		this.nivelAutenticacion = nivelAutenticacion;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getNumeroDocumentoIdentificacion()
	{
		return numeroDocumentoIdentificacion;
	}
	public void setNumeroDocumentoIdentificacion(
			String numeroDocumentoIdentificacion)
	{
		this.numeroDocumentoIdentificacion = numeroDocumentoIdentificacion;
	}
	public String getResultado()
	{
		return resultado;
	}
	public void setResultado(String resultado)
	{
		this.resultado = resultado;
	}
	public String getTipo()
	{
		return tipo;
	}
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	public String getUsuarioSeycon()
	{
		return usuarioSeycon;
	}
	public void setUsuarioSeycon(String usuarioSeycon)
	{
		this.usuarioSeycon = usuarioSeycon;
	}
	public int getVersionTramite()
	{
		return versionTramite;
	}
	public void setVersionTramite(int versionTramite)
	{
		this.versionTramite = versionTramite;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	
}
