package es.caib.sistra.model.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class CuadernoCarga implements Serializable
{
	// Estado auditoria
	public static final char PENDIENTE_ENVIO = 'I';
	public static final char PENDIENTE_AUDITAR = 'P';
	public static final char NO_REQUIERE_AUDITORIA = 'N';
	public static final char AUDITADO = 'A';
	public static final char RECHAZADO = 'R';
	
	// Importado / no importado
	public static final char IMPORTADO = 'S';
	public static final char NO_IMPORTADO = 'N';
	
	private Long codigo;
	private String descripcion;
	/**
	 * Fecha / hora alta del cuaderno de carga,
	 */
	private Timestamp fechaAlta;
	/**
	 * Fecha / hora prevista de carga
	 */
	private Timestamp fechaCarga;
	private Timestamp fechaEnvio;
	
	/**
	 * Indica el estado de la auditoria : 
	 * I : Pendiente de envio
	 * P : Pendiente de auditar
	 * N : No requiere auditoria
	 * A : Auditado
	 * R : Rechazado
	 */
	private char estadoAuditoria = PENDIENTE_ENVIO;
	private Timestamp fechaAuditoria;
	private String comentarioAuditoria;
	/**
	 * Indica si el tramite se ha importado tras la auditoria ( S / N )
	 */
	private char importado = NO_IMPORTADO;
	private Set ficheros = new HashSet(0);
	
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
	public Timestamp getFechaAlta()
	{
		return fechaAlta;
	}
	public void setFechaAlta(Timestamp fechaAlta)
	{
		this.fechaAlta = fechaAlta;
	}
	public Timestamp getFechaAuditoria()
	{
		return fechaAuditoria;
	}
	public void setFechaAuditoria(Timestamp fechaAuditoria)
	{
		this.fechaAuditoria = fechaAuditoria;
	}
	public void setEstadoAuditoria(char estadoAuditoria)
	{
		this.estadoAuditoria = estadoAuditoria;
	}
	public void setImportado(char importado)
	{
		this.importado = importado;
	}
	
	public Set getFicheros()
	{
		return ficheros;
	}
	public void setFicheros(Set ficheros)
	{
		this.ficheros = ficheros;
	}
	public void addFichero( FicheroCuaderno fichero )
	{
		fichero.setCuadernoCarga( this );
		this.ficheros.add( fichero );
	}
	public void removeFichero( FicheroCuaderno fichero )
	{
		this.ficheros.remove( fichero );
	}
	public char getEstadoAuditoria()
	{
		return estadoAuditoria;
	}
	public char getImportado()
	{
		return importado;
	}
	public Timestamp getFechaCarga()
	{
		return fechaCarga;
	}
	public void setFechaCarga(Timestamp fechaCarga)
	{
		this.fechaCarga = fechaCarga;
	}
	public Timestamp getFechaEnvio()
	{
		return fechaEnvio;
	}
	public void setFechaEnvio(Timestamp fechaEnvio)
	{
		this.fechaEnvio = fechaEnvio;
	}
	
}
