package es.caib.zonaper.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class TramitePersistente implements java.io.Serializable {
    
	public final static char AUTENTICACION_CERTIFICADO = 'C';
	public final static char AUTENTICACION_USUARIOPASSWORD = 'U';
	public final static char AUTENTICACION_ANONIMO = 'A';
	
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
     private Set documentos = new HashSet(0);     
     private String parametrosInicio;
     
     
     private final static String SEPARADOR_PARAMETROS_INICIO = "#-@";
     
    // Constructors
    /** default constructor */
    public TramitePersistente() {
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Set getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}	

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public String getUsuario() {
		return usuario;
	}	

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
	public void addDocumento(DocumentoPersistente doc) {
		doc.setTramitePersistente(this);    	
        documentos.add(doc);
    }

    public void removeDocumento(DocumentoPersistente doc) {    	
    	documentos.remove(doc);    	
    }

	public String getIdPersistencia() {
		return idPersistencia;
	}

	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

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
    
	public void setParametrosInicioMap(Map parametrosInicio) throws Exception{
		this.setParametrosInicio(this.serializarMap(parametrosInicio));		
	}
		
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

	public Timestamp getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Timestamp fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getUsuarioFlujoTramitacion() {
		return usuarioFlujoTramitacion;
	}

	public void setUsuarioFlujoTramitacion(String usuarioFlujoTramitacion) {
		this.usuarioFlujoTramitacion = usuarioFlujoTramitacion;
	}
}
