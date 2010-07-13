package es.caib.bantel.modelInterfaz;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Contiene la informaci�n de una entrada en la Bandeja Telem�tica de Entrada.
 * Presenta los principales datos recogidos en el asiento registral y la lista de documentos asociados 
 * a la entrada. 
 */
public class TramiteBTE implements Serializable {
	
	/**
	 * N�mero de entrada en la BTE
	 */
	private String numeroEntrada;
	/**
	 * C�digo interno en la BTE
	 */
	private Long codigoEntrada;	
	/**
	 * C�digo de unidad administrativa destino (Unidad Administrativa SAC)
	 */
	private Long unidadAdministrativa;	
	/**
	 * Fecha de entrada en la BTE
	 */
	private Timestamp fecha;
	/**
	 * Tipo de entrada (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 */	 
	private char tipo;
	/**
	 * Indica si la entrada ha sido firmada digitalmente
	 */
	private boolean firmadaDigitalmente;
	/**
	 * Indica si ha sido procesada por el BackOffice (ver ConstantesBTE)
	 */
	private char procesada;         
	/**
	 * Identificador del tr�mite
	 */
	private String identificadorTramite;
	/**
	 * Versi�n tr�mite
	 */
	private int versionTramite;
	/**
	 * Nivel de autenticaci�n con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	private char nivelAutenticacion;  
	/**
	 * Usuario Seycon con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	private String usuarioSeycon;
	/**
	 * Descripci�n tr�mite
	 */
	private String descripcionTramite;   
	/**
	 * C�digo de la Referencia RDS del Asiento 
	 */
	private long codigoReferenciaRDSAsiento;
	/**
	 * Clave de la Referencia RDS del Asiento 
	 */
	private String claveReferenciaRDSAsiento;
	/**
	 * C�digo de la Referencia RDS del Justificante 
	 */
	private long codigoReferenciaRDSJustificante;
	/**
	 * Clave de la Referencia RDS del Justificante 
	 */
	private String claveReferenciaRDSJustificante;
	/**
	 * N�mero de registro 
	 */
	private String numeroRegistro;
	/**
	 * Fecha de Registro
	 */
	private Date fechaRegistro;
	/**
	 * N�mero de preregistro (para esquema de PreRegistro)
	 */
	private String numeroPreregistro;
	/**
	 * Fecha de preregistro (para esquema de PreRegistro)
	 */
	private Date fechaPreregistro;
	/**
	 * Nif usuario (o representante) que ha desarrollado la tramitaci�n telem�tica
	 */
	private String usuarioNif;
	/**
	 * Nif usuario (o representante) que ha desarrollado la tramitaci�n telem�tica
	 */
	private String usuarioNombre;	
	/**
	 * Nif del interesado
	 */
	private String representadoNif;
	/**
	 * Nombre del interesado
	 */
	private String representadoNombre;
	/**
	 * Documentos asociados a la entrada
	 */
	private Set documentos = new HashSet(0);
	/**
	 * Idioma con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	private String idioma;
	/**
	 * Tipo de confirmacion entrada para un preregistro/preenvio (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 */	 
	private String tipoConfirmacionPreregistro;
	/**
	 * Indica si se han habilitado los avisos de tramitacion
	 */	
    private String habilitarAvisos;
    /**
	 * Indica movil para avisos
	 */
  	private String avisoSMS;
  	/**
	 * Indica email para avisos
	 */
  	private String avisoEmail;
	/**
	 * Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.
	 */
	private String habilitarNotificacionTelematica;
	
	/**
	 * C�digo interno en la BTE
	 */	
	public Long getCodigoEntrada() {
		return codigoEntrada;
	}
	/**
	 * C�digo interno en la BTE
	 */
	public void setCodigoEntrada(Long codigoEntrada) {
		this.codigoEntrada = codigoEntrada;
	}
	/**
	 * Descripci�n tr�mite
	 */
	public String getDescripcionTramite() {
		return descripcionTramite;
	}
	/**
	 * Descripci�n tr�mite
	 */
	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}
	/**
	 * Documentos asociados a la entrada
	 */
	public Set getDocumentos() {
		return documentos;
	}
	/**
	 * Documentos asociados a la entrada
	 */
	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}
	/**
	 * Fecha de entrada en la BTE
	 */
	public Timestamp getFecha() {
		return fecha;
	}
	/**
	 * Fecha de entrada en la BTE
	 */
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	/**
	 * Identificador del tr�mite
	 */
	public String getIdentificadorTramite() {
		return identificadorTramite;
	}
	/**
	 * Identificador del tr�mite
	 */
	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}	
	/**
	 * Nivel de autenticaci�n con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}
	/**
	 * Nivel de autenticaci�n con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}
	/**
	 * N�mero de registro 
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	/**
	 * N�mero de registro 
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	/**
	 * Indica si ha sido procesada por el BackOffice (ver ConstantesBTE)
	 */
	public char getProcesada() {
		return procesada;
	}
	/**
	 * Indica si ha sido procesada por el BackOffice (ver ConstantesBTE)
	 */
	public void setProcesada(char procesada) {
		this.procesada = procesada;
	}	
	/**
	 * Tipo de entrada (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 */	
	public char getTipo() {
		return tipo;
	}
	/**
	 * Tipo de entrada (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 */	
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	/**
	 * Nif usuario (o representante) que ha desarrollado la tramitaci�n telem�tica
	 */
	public String getUsuarioNif() {
		return usuarioNif;
	}
	/**
	 * Nif usuario (o representante) que ha desarrollado la tramitaci�n telem�tica
	 */
	public void setUsuarioNif(String usuarioNif) {
		this.usuarioNif = usuarioNif;
	}
	/**
	 * Nif usuario (o representante) que ha desarrollado la tramitaci�n telem�tica
	 */
	public String getUsuarioNombre() {
		return usuarioNombre;
	}
	/**
	 * Nif usuario (o representante) que ha desarrollado la tramitaci�n telem�tica
	 */
	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}
	/**
	 * Versi�n tr�mite
	 */
	public int getVersionTramite() {
		return versionTramite;
	}
	/**
	 * Versi�n tr�mite
	 */
	public void setVersionTramite(int versionTramite) {
		this.versionTramite = versionTramite;
	}
	/**
	 * Usuario Seycon con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}
	/**
	 * Usuario Seycon con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}
	/**
	 * Nif del interesado
	 */
	public String getRepresentadoNif() {
		return representadoNif;
	}
	/**
	 * Nif del interesado
	 */
	public void setRepresentadoNif(String representadoNif) {
		this.representadoNif = representadoNif;
	}
	/**
	 * Nombre del interesado
	 */
	public String getRepresentadoNombre() {
		return representadoNombre;
	}
	/**
	 * Nombre del interesado
	 */
	public void setRepresentadoNombre(String representadoNombre) {
		this.representadoNombre = representadoNombre;
	}
	/**
	 * Idioma con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	public String getIdioma() {
		return idioma;
	}
	/**
	 * Idioma con el que se ha desarrollado la tramitaci�n telem�tica
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	/**
	 * Fecha de preregistro (para esquema de PreRegistro)
	 */
	public Date getFechaPreregistro() {
		return fechaPreregistro;
	}
	/**
	 * Fecha de preregistro (para esquema de PreRegistro)
	 */
	public void setFechaPreregistro(Date fechaPreregistro) {
		this.fechaPreregistro = fechaPreregistro;
	}
	/**
	 * Fecha de Registro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * Fecha de Registro
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * N�mero de preregistro (para esquema de PreRegistro)
	 */
	public String getNumeroPreregistro() {
		return numeroPreregistro;
	}
	/**
	 * N�mero de preregistro (para esquema de PreRegistro)
	 */
	public void setNumeroPreregistro(String numeroPreregistro) {
		this.numeroPreregistro = numeroPreregistro;
	}	
	/**
	 * N�mero de entrada en la BTE
	 */
	public String getNumeroEntrada() {
		return numeroEntrada;
	}
	/**
	 * N�mero de entrada en la BTE
	 */
	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}
	/**
	 * C�digo de unidad adminstrativa destino (Unidad Administrativa SAC)
	 */
	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	/**
	 * C�digo de unidad adminstrativa destino (Unidad Administrativa SAC)
	 */
	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	/**
	 * Clave de la Referencia RDS del Asiento 
	 */
	public String getClaveReferenciaRDSAsiento() {
		return claveReferenciaRDSAsiento;
	}
	/**
	 * Clave de la Referencia RDS del Asiento 
	 */
	public void setClaveReferenciaRDSAsiento(String claveReferenciaRDSAsiento) {
		this.claveReferenciaRDSAsiento = claveReferenciaRDSAsiento;
	}
	/**
	 * Clave de la Referencia RDS del Justificante 
	 */
	public String getClaveReferenciaRDSJustificante() {
		return claveReferenciaRDSJustificante;
	}
	/**
	 * Clave de la Referencia RDS del Justificante 
	 */
	public void setClaveReferenciaRDSJustificante(
			String claveReferenciaRDSJustificante) {
		this.claveReferenciaRDSJustificante = claveReferenciaRDSJustificante;
	}
	/**
	 * C�digo de la Referencia RDS del Asiento 
	 */
	public long getCodigoReferenciaRDSAsiento() {
		return codigoReferenciaRDSAsiento;
	}
	/**
	 * C�digo de la Referencia RDS del Asiento 
	 */
	public void setCodigoReferenciaRDSAsiento(long codigoReferenciaRDSAsiento) {
		this.codigoReferenciaRDSAsiento = codigoReferenciaRDSAsiento;
	}
	/**
	 * C�digo de la Referencia RDS del Justificante 
	 */
	public long getCodigoReferenciaRDSJustificante() {
		return codigoReferenciaRDSJustificante;
	}
	/**
	 * C�digo de la Referencia RDS del Justificante 
	 */
	public void setCodigoReferenciaRDSJustificante(
			long codigoReferenciaRDSJustificante) {
		this.codigoReferenciaRDSJustificante = codigoReferenciaRDSJustificante;
	}     

	/**
	 * Obtiene documento de la lista de documentos
	 * @param identificador
	 * @param instancia
	 * @return Devuelve documento (si no existe documento devuelve nulo)
	 */
	public DocumentoBTE getDocumento(String identificador,int instancia){
		for (Iterator it=this.getDocumentos().iterator();it.hasNext();){
			DocumentoBTE doc = (DocumentoBTE) it.next();
			if (doc.getPresentacionTelematica() != null) {
				if (doc.getPresentacionTelematica().getIdentificador().equals(identificador) && 
					doc.getPresentacionTelematica().getNumeroInstancia() == instancia ) return doc;
			}
			if (doc.getPresentacionPresencial() != null) {				
				if (doc.getPresentacionPresencial().getIdentificador() != null &&
					doc.getPresentacionPresencial().getIdentificador().equals(identificador) && 
					doc.getPresentacionPresencial().getNumeroInstancia() == instancia ) return doc;
			}
			
		}
		return null;
	}
	
	/**
	 * Obtiene numero de instancias de un documento
	 * @param identificador
	 * @return numero de instancias
	 */
	public int getNumeroInstanciasDocumento(String identificador){
		int num = 0;
		for (Iterator it=this.getDocumentos().iterator();it.hasNext();){
			DocumentoBTE doc = (DocumentoBTE) it.next();
			if (doc.getPresentacionTelematica() != null) {
				if (doc.getPresentacionTelematica().getIdentificador().equals(identificador)) num++;
			}else{
				if (doc.getPresentacionPresencial() != null) {
					if (doc.getPresentacionPresencial().getIdentificador() != null && doc.getPresentacionPresencial().getIdentificador().equals(identificador)) num++;
				}
			}
			
		}
		return num;
	}
	
	/**
	 * Tipo de confirmacion entrada para un preregistro/preenvio (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 */	
	public String getTipoConfirmacionPreregistro() {
		return tipoConfirmacionPreregistro;
	}
	/**
	 * Tipo de confirmacion entrada para un preregistro/preenvio (ver  es.caib.xml.registro.factoria.ConstantesAsientoXML: TIPO_REGISTRO_ENTRADA,TIPO_ENVIO,TIPO_PREREGISTRO,TIPO_PREENVIO )
	 */	
	public void setTipoConfirmacionPreregistro(String tipoConfirmacionPreregistro) {
		this.tipoConfirmacionPreregistro = tipoConfirmacionPreregistro;
	}
	
	/**
	 * Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.
	 */
	public String getHabilitarNotificacionTelematica() {
		return habilitarNotificacionTelematica;
	}
	/**
	 * Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.
	 */
	public void setHabilitarNotificacionTelematica(
			String habilitarNotificacionTelematica) {
		this.habilitarNotificacionTelematica = habilitarNotificacionTelematica;
	}
	/**
	 * Indica email para avisos
	 */		
	public String getAvisoEmail() {
		return avisoEmail;
	}
	/**
	 * Indica email para avisos
	 */
	public void setAvisoEmail(String avisoEmail) {
		this.avisoEmail = avisoEmail;
	}
	/**
	 * Indica movil para avisos
	 */
	public String getAvisoSMS() {
		return avisoSMS;
	}
	/**
	 * Indica movil para avisos
	 */
	public void setAvisoSMS(String avisoSMS) {
		this.avisoSMS = avisoSMS;
	}
	/**
	 * Indica si se han habilitado los avisos de tramitacion
	 */	
	public String getHabilitarAvisos() {
		return habilitarAvisos;
	}
	/**
	 * Indica si se han habilitado los avisos de tramitacion
	 */	
	public void setHabilitarAvisos(String habilitarAvisos) {
		this.habilitarAvisos = habilitarAvisos;
	}
	/**
	 * Indica si la entrada ha sido firmada digitalmente
	 */
	public boolean isFirmadaDigitalmente() {
		return firmadaDigitalmente;
	}
	/**
	 * Indica si la entrada ha sido firmada digitalmente
	 */
	public void setFirmadaDigitalmente(boolean firmadaDigitalmente) {
		this.firmadaDigitalmente = firmadaDigitalmente;
	}
	
}
