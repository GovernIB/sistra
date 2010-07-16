package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

/**
 * <p>
 * Representa a un documento perteneciente a un elemento de un expediente en la zona personal.
 * </p>
 * <p>
 * Cuando se va a crear un documento (alta expediente o alta evento expediente) existen 2 posibilidades:
 * <ul>
 * <li>Referenciar un documento existente en RDS, asignando las propiedades: codigoRDS y claveRDS</li>
 * <li>La creaci�n de un nuevo documento en RDS, para lo cual se deber�n asignar las propiedades:
 * 		<ul>
 * 			<li>nombre: nombre del fichero con su extensi�n (ej: anexo1.pdf)</li>
 * 			<li>titulo: titulo descriptivo del documento</li>
 * 			<li>contenido fichero: contenido en bytes del fichero</li>  			
 * 			<li>modelo RDS: modelo de documento definido en el RDS</li>
 * 			<li>version RDS: versi�n del modelo de documento definido en el RDS</li>
 * 		</ul>
 * </li>
 * </ul>
 * </p>
 *<p>
 * Cuando se trate de una operaci�n de consulta (consulta expediente) s�lo se devolver�n las propiedades:
 * <ul>
 * <li>codigoRDS y claveRDS</li>
 * <li>titulo documento</li>
 * </ul>
 *</p>
 *
 */
public class DocumentoExpedientePAD implements Serializable
{
	private Long codigoRDS;
	private String claveRDS;
	private boolean estructurado;
	private String nombre;
	private String titulo;
	private byte[] contenidoFichero;	
	private String modeloRDS;
	private int versionRDS = 1;			
	
	/**
	 * Array de bytes con el contenido del fichero 
	 * @return
	 */
	public byte[] getContenidoFichero()
	{
		return contenidoFichero;
	}
	/**
	 * Establece el contenido del fichero 
	 * @param contenidoFichero
	 */
	public void setContenidoFichero(byte[] contenidoFichero)
	{
		this.contenidoFichero = contenidoFichero;
	}
	/** 
	 * Devuelve el t�tulo del documento
	 * @return
	 */
	public String getTitulo()
	{
		return titulo;
	}
	/** Establece el t�tulo del documento
	 * @param titulo
	 */
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	/**
	 * Devuelve la clave RDS del documento
	 * @return
	 */
	public String getClaveRDS()
	{
		return claveRDS;
	}
	/**
	 * Establece la clave RDS del documento 
	 * @param claveRDS
	 */
	public void setClaveRDS(String claveRDS)
	{
		this.claveRDS = claveRDS;
	}
	/**
	 * Devuelve el c�digo RDS del documento
	 * @return
	 */
	public Long getCodigoRDS()
	{
		return codigoRDS;
	}
	/**
	 * Establece el c�digo RDS del documento. Si se establece, es obligatorio rellenar tambi�n la clave RDS del documento
	 * @param codigoRDS
	 */
	public void setCodigoRDS(Long codigoRDS)
	{
		this.codigoRDS = codigoRDS;
	}
	/**
	 * Indica si es un documento estructurado y por tanto se va a generar como resultado de interpretar su contenido .xml con una plantilla
	 * (para crear documento en el RDS)
	 * @return
	 */
	public boolean isEstructurado()
	{
		return estructurado;
	}
	/**
	 * Establece si es un documento estructurado
	 * @param estructurado
	 */
	public void setEstructurado(boolean estructurado)
	{
		this.estructurado = estructurado;
	}
	/**
	 * Obtiene el modelo RDS del documento
	 * @return
	 */
	public String getModeloRDS()
	{
		return modeloRDS;
	}
	/**
	 * Establece el modelo RDS del documento
	 * S�lo es significativo si no se referencia un documento ya existente en RDS mediante c�digo y clave
	 * @return
	 */
	public void setModeloRDS(String modeloRDS)
	{
		this.modeloRDS = modeloRDS;
	}
	/**
	 * Obtiene el nombre del documento
	 * @return
	 */
	public String getNombre()
	{
		return nombre;
	}
	/**
	 * Establece el nombre del documento
	 * @param nombre
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	/**
	 * Obtiene la versi�n RDS del documento
	 * @return
	 */
	public int getVersionRDS()
	{
		return versionRDS;
	}
	/**
	 * Establece la versi�n RDS del documento
	 * S�lo es significativo si no se referencia un documento ya existente en RDS mediante c�digo y clave RDS
	 * En tal caso, si no se asigna, se coger� por defecto la versi�n 1.
	 * @param versionRDS
	 */
	public void setVersionRDS(int versionRDS)
	{
		this.versionRDS = versionRDS;
	}
	
}
