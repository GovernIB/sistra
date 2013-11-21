package es.caib.zonaper.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class RegistroExterno implements java.io.Serializable{
    
	
	// Fields    	
     private Long codigo;
     private char nivelAutenticacion;
     private String usuario;
     private String descripcionTramite;
     private String numeroRegistro;
     private Date fecha;     
     private long codigoRdsAsiento;
     private String claveRdsAsiento;     
     private long codigoRdsJustificante;
     private String claveRdsJustificante;
     private Set documentos = new HashSet(0);     
     private String idioma;
     private String nifRepresentante;
     private String nombreRepresentante;
     private String nifRepresentado;
     private String nombreRepresentado;
 	
	// Constructors
    /** default constructor */
    public RegistroExterno() {
    }
    
    // Methods
	public void addDocumento(DocumentoRegistro doc) {
		doc.setRegistroExterno(this);    	
        documentos.add(doc);
    }

    public void removeDocumento(DocumentoRegistro doc) {    	
    	documentos.remove(doc);    	
    }

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getClaveRdsAsiento()
	 */
	public String getClaveRdsAsiento() {
		return claveRdsAsiento;
	}

	public void setClaveRdsAsiento(String claveRdsAsiento) {
		this.claveRdsAsiento = claveRdsAsiento;
	}

	public String getClaveRdsJustificante() {
		return claveRdsJustificante;
	}

	public void setClaveRdsJustificante(String claveRdsJustificante) {
		this.claveRdsJustificante = claveRdsJustificante;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getCodigo()
	 */
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getCodigoRdsAsiento()
	 */
	public long getCodigoRdsAsiento() {
		return codigoRdsAsiento;
	}

	public void setCodigoRdsAsiento(long codigoRdsAsiento) {
		this.codigoRdsAsiento = codigoRdsAsiento;
	}

	public long getCodigoRdsJustificante() {
		return codigoRdsJustificante;
	}

	public void setCodigoRdsJustificante(long codigoRdsJustificante) {
		this.codigoRdsJustificante = codigoRdsJustificante;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.Entrada#getDocumentos()
	 */
	public Set getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Metodo por compatibilidad con EntradaPrerregistro
	 */
	public Date getFechaConfirmacion()
	{
		return null;
	}

	/**
	 * Metodo por compatibilidad con EntradaPrerregistro
	 */
	public String getNumeroPreregistro()
	{
		return null;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getNifRepresentante() {
		return nifRepresentante;
	}

	public void setNifRepresentante(String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}

	public String getNombreRepresentado() {
		return nombreRepresentado;
	}

	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}

	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}
}
