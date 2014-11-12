package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TramitePersistentePAD implements Serializable {
	
	public final static char AUTENTICACION_CERTIFICADO = 'C';
	public final static char AUTENTICACION_USUARIOPASSWORD = 'U';
	public final static char AUTENTICACION_ANONIMO = 'A';
		
	/**
	 * Para trámites que se ejecutan de forma delegada indica que el usuario que tiene actualmente el trámite 
	 * ha realizado todas sus acciones sobre el paso pero queda que otro usuario presente el trámite
	 */
	public static final String ESTADO_PENDIENTE_DELEGACION_PRESENTACION = "DP";
	
	/**
	 * Para trámites que se ejecutan de forma delegada indica que el usuario que tiene actualmente el trámite 
	 * ha realizado todas sus acciones sobre el paso pero queda que otro delegado firme algun formulario o anexo
	 */
	public static final String ESTADO_PENDIENTE_DELEGACION_FIRMA = "DF";	
	
	/**
	 * Indica que no se generan alertas tramitacion.
	 */
	public static final String ALERTASTRAMITACION_GENERAR_NO = "N";
	
	/**
	 * Indica que se generan alertas tramitacion (se generara por email/sms segun exista email/sms).
	 */
	public static final String ALERTASTRAMITACION_GENERAR_SI = "S";	
	
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
    private Map documentos = new HashMap(0);
    private Map parametrosInicio;
    private String delegado;
	private String estadoDelegacion;
	
	 private String alertasTramitacionGenerar = "N";
     private String alertasTramitacionEmail; 
     private String alertasTramitacionSms;
     
     private String idProcedimiento;
	
    public String getDelegado() {
		return delegado;
	}
	public void setDelegado(String delegado) {
		this.delegado = delegado;
	}
    public Map getDocumentos() {
		return documentos;
	}
	public void setDocumentos(Map documentos) {
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
	public String getIdPersistencia() {
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
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
	public String getTramite() {
		return tramite;
	}
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
	
	/**
	 * Devuelve número de instancias de un documento
	 * @param identificador
	 * @return Numero de instancias
	 */
	public int getNumeroInstanciasDocumento(String identificador){
		int numeroInstancias=0;
    	for (Iterator it = getDocumentos().keySet().iterator();it.hasNext();){
    		String ls_key = (String) it.next();
    		DocumentoPersistentePAD doc = (DocumentoPersistentePAD) getDocumentos().get(ls_key);
    		if (doc.getIdentificador().equals(identificador)) numeroInstancias ++;
    	}
    	return numeroInstancias;
	}
	
	/**
	 * Devuelve número de instancias de un documento con un estado determinado
	 * @param identificador
	 * @return Numero de instancias
	 */
	public int getNumeroInstanciasDocumentoEstado(String identificador,char estado){
		int numeroInstancias=0;
    	for (Iterator it = getDocumentos().keySet().iterator();it.hasNext();){
    		String ls_key = (String) it.next();
    		DocumentoPersistentePAD doc = (DocumentoPersistentePAD) getDocumentos().get(ls_key);
    		if (doc.getIdentificador().equals(identificador) && doc.getEstado() == estado) numeroInstancias ++;
    	}
    	return numeroInstancias;
	}
    
	/**
	 * Devuelve número de instancia máxima de un documento
	 * @param identificador
	 * @return Máximo numero de instancia (0 si no hay)
	 */
	public int getMaximoNumeroInstanciaDocumento(String identificador){
		int max=0;
    	for (Iterator it = getDocumentos().keySet().iterator();it.hasNext();){
    		String ls_key = (String) it.next();
    		DocumentoPersistentePAD doc = (DocumentoPersistentePAD) getDocumentos().get(ls_key);
    		if (doc.getIdentificador().equals(identificador) && (max < doc.getNumeroInstancia())) max = doc.getNumeroInstancia();
    	}
    	return max;
	}
	
	/**
	 * Borra documento reordenando instancias
	 * @param identificador
	 * @param instancia
	 */
	public void borrarDocumento(String identificador,int instancia){			
		// Borramos documento de la colección
		getDocumentos().remove(identificador + "-" + instancia);
		// Reordenamos instancias
		HashMap docsReordenados = new HashMap();
    	for (Iterator it = getDocumentos().keySet().iterator();it.hasNext();){
    		String ls_key = (String) it.next();
    		DocumentoPersistentePAD doc = (DocumentoPersistentePAD) getDocumentos().get(ls_key);
    		if (doc.getIdentificador().equals(identificador) && (instancia < doc.getNumeroInstancia())) {
    			doc.setNumeroInstancia(doc.getNumeroInstancia() - 1);    			
    		}
    		docsReordenados.put(doc.getIdentificador() + "-" + doc.getNumeroInstancia(),doc);
    	}    	
    	getDocumentos().clear();
    	setDocumentos(docsReordenados);    	    	
	}
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public Map getParametrosInicio() {
		return parametrosInicio;
	}
	public void setParametrosInicio(Map parametrosInicio) {
		this.parametrosInicio = parametrosInicio;
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
	public String getEstadoDelegacion() {
		return estadoDelegacion;
	}
	public void setEstadoDelegacion(String estadoDelegacion) {
		this.estadoDelegacion = estadoDelegacion;
	}
	public String getAlertasTramitacionGenerar() {
		return alertasTramitacionGenerar;
	}
	public void setAlertasTramitacionGenerar(String alertasTramitacionGenerar) {
		this.alertasTramitacionGenerar = alertasTramitacionGenerar;
	}
	public String getAlertasTramitacionEmail() {
		return alertasTramitacionEmail;
	}
	public void setAlertasTramitacionEmail(String alertasTramitacionEmail) {
		this.alertasTramitacionEmail = alertasTramitacionEmail;
	}
	public String getAlertasTramitacionSms() {
		return alertasTramitacionSms;
	}
	public void setAlertasTramitacionSms(String alertasTramitacionSms) {
		this.alertasTramitacionSms = alertasTramitacionSms;
	}
	public String getIdProcedimiento() {
		return idProcedimiento;
	}
	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}
        
    
}
