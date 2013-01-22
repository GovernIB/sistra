package es.caib.zonaper.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class TramitePersistenteBackup
{
	private final static String SEPARADOR_PARAMETROS_INICIO = "#-@";
	
	// Fields    	
    private Long codigo;
    private String idPersistencia;
    private String tramite;
    private int version;
    private String descripcion;
    private char nivelAutenticacion;
    private String usuario;
    private String usuarioFlujoTramitacion;    
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;  
    private Timestamp fechaCaducidad;
    private String idioma;
    private Set documentosBackup = new HashSet(0);     
    private String parametrosInicio;
    private String delegado;
    private String estadoDelegacion;
    
	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getCodigo()
	 */
	public Long getCodigo() {
		return codigo;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setCodigo(java.lang.Long)
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getDocumentos()
	 */
	public Set getDocumentosBackup() {
		return documentosBackup;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setDocumentos(java.util.Set)
	 */
	public void setDocumentosBackup(Set documentos) {
		this.documentosBackup = documentos;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getFechaCreacion()
	 */
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setFechaCreacion(java.sql.Timestamp)
	 */
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getFechaModificacion()
	 */
	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setFechaModificacion(java.sql.Timestamp)
	 */
	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}	

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getTramite()
	 */
	public String getTramite() {
		return tramite;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setTramite(java.lang.String)
	 */
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getNivelAutenticacion()
	 */
	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setNivelAutenticacion(char)
	 */
	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getUsuario()
	 */
	public String getUsuario() {
		return usuario;
	}	

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setUsuario(java.lang.String)
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getVersion()
	 */
	public int getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setVersion(int)
	 */
	public void setVersion(int version) {
		this.version = version;
	}
    
	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#addDocumento(es.caib.zonaper.model.DocumentoPersistente)
	 */
	public void addDocumentoBackup(DocumentoPersistenteBackup doc) {
		doc.setTramitePersistenteBackup(this);    	
        documentosBackup.add(doc);
    }

    /* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#removeDocumento(es.caib.zonaper.model.DocumentoPersistente)
	 */
    public void removeDocumentoBackup(DocumentoPersistenteBackup doc) {    	
    	documentosBackup.remove(doc);    	
    }

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getIdPersistencia()
	 */
	public String getIdPersistencia() {
		return idPersistencia;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setIdPersistencia(java.lang.String)
	 */
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getDescripcion()
	 */
	public String getDescripcion()
	{
		return descripcion;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setDescripcion(java.lang.String)
	 */
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	private String getParametrosInicio() {
		return parametrosInicio;
	}

	private void setParametrosInicio(String parametrosInicio) {
		this.parametrosInicio = parametrosInicio;
	}	    
    
	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setParametrosInicioMap(java.util.Map)
	 */
	public void setParametrosInicioMap(Map parametrosInicio) throws Exception{
		this.setParametrosInicio(this.serializarMap(parametrosInicio));		
	}
		
	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getParametrosInicioMap()
	 */
	public Map getParametrosInicioMap() throws Exception {
		return (this.getParametrosInicio()!=null?this.deserializarMap(this.getParametrosInicio()):null);
	}
	
	private String serializarMap(Map map) throws Exception{		
		if (map == null) return null;		
		String str="";		
		boolean primer = true;
		String name,value;
		for (Iterator it = map.keySet().iterator();it.hasNext();){
			name = (String) it.next();				
			if (!primer) {
				str = str + SEPARADOR_PARAMETROS_INICIO;
			}else{
				primer = false;
			}
			
			if (map.get(name) != null) 
				value = (String) map.get(name);
			else
				value ="";	
			
			str = str +  name + SEPARADOR_PARAMETROS_INICIO + value;
		}
		return str;
	}
	
	private Map deserializarMap(String mapStr) throws Exception{
		if (mapStr == null || mapStr.length() <= 0) return null;
		HashMap map = new HashMap();
		StringTokenizer st = new StringTokenizer(mapStr,SEPARADOR_PARAMETROS_INICIO);		
		String key,value;
		while (st.hasMoreElements()){
			key = (String) st.nextElement();			
			if (st.hasMoreElements()) value = (String) st.nextElement();
				else value=null;
			map.put(key,value);
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getFechaCaducidad()
	 */
	public Timestamp getFechaCaducidad() {
		return fechaCaducidad;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setFechaCaducidad(java.sql.Timestamp)
	 */
	public void setFechaCaducidad(Timestamp fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#getIdioma()
	 */
	public String getIdioma() {
		return idioma;
	}

	/* (non-Javadoc)
	 * @see es.caib.zonaper.model.ITramitePersistente#setIdioma(java.lang.String)
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getUsuarioFlujoTramitacion() {
		return usuarioFlujoTramitacion;
	}

	public void setUsuarioFlujoTramitacion(String usuarioFlujoTramitacion) {
		this.usuarioFlujoTramitacion = usuarioFlujoTramitacion;
	}

	public String getDelegado() {
		return delegado;
	}

	public void setDelegado(String delegado) {
		this.delegado = delegado;
	}

	public String getEstadoDelegacion() {
		return estadoDelegacion;
	}

	public void setEstadoDelegacion(String estadoDelegacion) {
		this.estadoDelegacion = estadoDelegacion;
	}
}
