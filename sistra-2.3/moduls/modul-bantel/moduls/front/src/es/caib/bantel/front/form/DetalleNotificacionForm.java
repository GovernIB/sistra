package es.caib.bantel.front.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.bantel.front.util.MensajesUtil;


public class DetalleNotificacionForm extends ValidatorForm
{
	
	private String descripcionExpediente;
	
	private String usuarioSey;
	private String nif;
	private String apellidos;
	private String nombrePais; 
	private String nombreProvincia;
	private String nombreMunicipio;
	private String codigoPais; 
	private String codigoProvincia;
	private String codigoMunicipio;
	
	private String oficinaRegistro;
	private String organoDestino;
	
	private String idioma; 
	private String acuse;
	private String accesoPorClave = "N"; // S / N
	private String tipoAsunto;
	private String tituloAviso;
	private String textoAviso;
	private String permitirSms = "N"; // S / N 
	private String textoSmsAviso;
	private String tituloOficio;
	private String textoOficio;
	
	
	private String tipoDocumento; // URL / FICHERO
	private String tituloAnexoOficio;
	private FormFile documentoAnexoOficio;	
	private String  urlAnexoOficio;
	
	private String tramiteSubsanacion = "N"; // S / N 
	
    private String descripcionTramiteSubsanacion; 
    private String identificadorTramiteSubsanacion;
    private String versionTramiteSubsanacion;
    private String codigoParametroTramiteSubsanacion;
    private String valorParametroTramiteSubsanacion;
    
    private String firmar;
	private String flagValidacion;
	
    private String rutaFitxer;
    private String firma;
    
    private String idiomaExp;
    
	public String getIdiomaExp() {
		return idiomaExp;
	}


	public void setIdiomaExp(String idiomaExp) {
		this.idiomaExp = idiomaExp;
	}


	public String getAcuse() {
		return acuse;
	}


	public void setAcuse(String acuse) {
		this.acuse = acuse;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDescripcionExpediente() {
		return descripcionExpediente;
	}


	public void setDescripcionExpediente(String descripcionExpediente) {
		this.descripcionExpediente = descripcionExpediente;
	}


	public FormFile getDocumentoAnexoOficio() {
		return documentoAnexoOficio;
	}


	public void setDocumentoAnexoOficio(FormFile documentoAnexoOficio) {
		this.documentoAnexoOficio = documentoAnexoOficio;
	}	

	public String getIdioma() {
		return idioma;
	}


	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}


	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}


	public String getOficinaRegistro() {
		return oficinaRegistro;
	}


	public void setOficinaRegistro(String oficinaRegistro) {
		this.oficinaRegistro = oficinaRegistro;
	}


	public String getOrganoDestino() {
		return organoDestino;
	}


	public void setOrganoDestino(String organoDestino) {
		this.organoDestino = organoDestino;
	}


	public String getTextoAviso() {
		return textoAviso;
	}


	public void setTextoAviso(String textoAviso) {
		this.textoAviso = textoAviso;
	}


	public String getTextoOficio() {
		return textoOficio;
	}


	public void setTextoOficio(String textoOficio) {
		this.textoOficio = textoOficio;
	}


	public String getTextoSmsAviso() {
		return textoSmsAviso;
	}


	public void setTextoSmsAviso(String textoSmsAviso) {
		this.textoSmsAviso = textoSmsAviso;
	}


	public String getTituloAnexoOficio() {
		return tituloAnexoOficio;
	}


	public void setTituloAnexoOficio(String tituloAnexoOficio) {
		this.tituloAnexoOficio = tituloAnexoOficio;
	}


	public String getTituloAviso() {
		return tituloAviso;
	}


	public void setTituloAviso(String tituloAviso) {
		this.tituloAviso = tituloAviso;
	}


	public String getTituloOficio() {
		return tituloOficio;
	}


	public void setTituloOficio(String tituloOficio) {
		this.tituloOficio = tituloOficio;
	}


	public String getUsuarioSey() {
		return usuarioSey;
	}


	public void setUsuarioSey(String usuarioSey) {
		this.usuarioSey = usuarioSey;
	}	

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}


	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}


	public String getCodigoPais() {
		return codigoPais;
	}


	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}


	public String getCodigoProvincia() {
		return codigoProvincia;
	}


	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}


	public String getNombreMunicipio() {
		return nombreMunicipio;
	}


	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}


	public String getNombrePais() {
		return nombrePais;
	}


	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}


	public String getNombreProvincia() {
		return nombreProvincia;
	}


	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public String getFirmar() {
		return firmar;
	}


	public void setFirmar(String firmar) {
		this.firmar = firmar;
	}
	
	public String getFlagValidacion() {
		return flagValidacion;
	}


	public void setFlagValidacion(String flagValidacion) {
		this.flagValidacion = flagValidacion;
	}

	public String getFirma() {
		return firma;
	}


	public void setFirma(String firma) {
		this.firma = firma;
	}


	public String getRutaFitxer() {
		return rutaFitxer;
	}


	public void setRutaFitxer(String rutaFitxer) {
		this.rutaFitxer = rutaFitxer;
	}

	public String getTipoAsunto() {
		return tipoAsunto;
	}

	public void setTipoAsunto(String tipoAsunto) {
		this.tipoAsunto = tipoAsunto;
	}

	public String getDescripcionTramiteSubsanacion() {
		return descripcionTramiteSubsanacion;
	}


	public void setDescripcionTramiteSubsanacion(
			String descripcionTramiteSubsanacion) {
		this.descripcionTramiteSubsanacion = descripcionTramiteSubsanacion;
	}


	public String getIdentificadorTramiteSubsanacion() {
		return identificadorTramiteSubsanacion;
	}


	public void setIdentificadorTramiteSubsanacion(
			String identificadorTramiteSubsanacion) {
		this.identificadorTramiteSubsanacion = identificadorTramiteSubsanacion;
	}


	public String getVersionTramiteSubsanacion() {
		return versionTramiteSubsanacion;
	}


	public void setVersionTramiteSubsanacion(String versionTramiteSubsanacion) {
		this.versionTramiteSubsanacion = versionTramiteSubsanacion;
	}
	
			
	public String getCodigoParametroTramiteSubsanacion() {
		return codigoParametroTramiteSubsanacion;
	}


	public void setCodigoParametroTramiteSubsanacion(
			String codigoParametroTramiteSubsanacion) {
		this.codigoParametroTramiteSubsanacion = codigoParametroTramiteSubsanacion;
	}


	public String getValorParametroTramiteSubsanacion() {
		return valorParametroTramiteSubsanacion;
	}


	public void setValorParametroTramiteSubsanacion(
			String valorParametroTramiteSubsanacion) {
		this.valorParametroTramiteSubsanacion = valorParametroTramiteSubsanacion;
	}


	public Integer getVersionTramiteSubsanacionInteger() {
		try{
			return new Integer(versionTramiteSubsanacion);
		}catch(Exception e){
			return null;			
		}
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        if(documentoAnexoOficio != null && documentoAnexoOficio.getFileName() != null  && !"".equals(documentoAnexoOficio.getFileName())){
        	if(!"PDF".equalsIgnoreCase(DocumentosUtil.getExtension(documentoAnexoOficio.getFileName())) &&  !DocumentosUtil.extensionCorrecta(documentoAnexoOficio.getFileName())){
        		errors.add("altaNotificacion",new ActionError("error.aviso.extensiones.fichero"));
        	}
        }
        if(StringUtils.isNotEmpty(flagValidacion) && flagValidacion.equals("altaNotificacion")){
        	        	
        	if(StringUtils.isEmpty(nif)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("expediente.nif", request)));
        	}
        	if(StringUtils.isEmpty(apellidos)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("notificacion.nombre.apellisos", request)));
        	}
        	if(StringUtils.isEmpty(codigoPais)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("notificacion.pais", request)));
        	}
        	if( "ESP".equals(codigoPais) && (StringUtils.isEmpty(codigoProvincia))){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("notificacion.provincia", request)));
        	}        	
        	if( "ESP".equals(codigoPais) && (StringUtils.isEmpty(codigoMunicipio))){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("notificacion.municipio", request)));
        	}
        	if(StringUtils.isEmpty(tituloAviso)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.aviso.titulo", request) ));
        	}
        	if(StringUtils.isEmpty(textoAviso)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.aviso.texto", request)));
        	}
        	if(StringUtils.isEmpty( tituloOficio )){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.oficio.titulo", request)));
        	}
        	if(StringUtils.isEmpty( textoOficio )){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.oficio.texto", request)));
        	}
        	if(StringUtils.isEmpty(tipoAsunto)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.tipo.asunto", request)));
        	}
        	if("S".equals(tramiteSubsanacion)){
        		if(StringUtils.isBlank(descripcionTramiteSubsanacion)){
        			errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.subsanacion.descripcion", request)));
        		}
        		if(StringUtils.isBlank(identificadorTramiteSubsanacion)){
        			errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.subsanacion.identificador", request)));
        		}
        		if(StringUtils.isBlank(versionTramiteSubsanacion)){
        			errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.subsanacion.version", request)));
        		}else if(getVersionTramiteSubsanacionInteger() == null){
        			errors.add("altaNotificacion", new ActionError("errors.integer", MensajesUtil.getValue("valida.notificacion.subsanacion.version", request)));
            	}
        	}
        	
        	if(StringUtils.isNotEmpty(tituloAviso) && tituloAviso.length() > 500){
        		errors.add("altaNotificacion", new ActionError("errors.maxlength", MensajesUtil.getValue("valida.notificacion.aviso.titulo", request), "500"));
        	}
        	
        	if(StringUtils.isNotEmpty(textoAviso) && textoAviso.length() > 4000){
        		errors.add("altaNotificacion", new ActionError("errors.maxlength", MensajesUtil.getValue("valida.notificacion.aviso.texto", request), "4000"));
        	}
        	
        	if(StringUtils.isNotEmpty(tituloOficio) && tituloOficio.length() > 500){
        		errors.add("altaNotificacion", new ActionError("errors.maxlength", MensajesUtil.getValue("valida.notificacion.oficio.titulo", request), "500"));
        	}
        	
        	if(StringUtils.isNotEmpty(textoOficio) && textoOficio.length() > 4000){
        		errors.add("altaNotificacion", new ActionError("errors.maxlength", MensajesUtil.getValue("valida.notificacion.oficio.texto", request), "4000"));
        	}
        	
        	if(StringUtils.isNotEmpty(textoSmsAviso) && textoSmsAviso.length() > 150){
        		errors.add("altaNotificacion", new ActionError("errors.maxlength", MensajesUtil.getValue("valida.notificacion.oficio.texto", request), "150"));
        	}
        	
        }  
        return errors;
    }


	public String getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


	public String getUrlAnexoOficio() {
		return urlAnexoOficio;
	}


	public void setUrlAnexoOficio(String urlAnexoOficio) {
		this.urlAnexoOficio = urlAnexoOficio;
	}


	public String getTramiteSubsanacion() {
		return tramiteSubsanacion;
	}


	public void setTramiteSubsanacion(String tramiteSubsanacion) {
		this.tramiteSubsanacion = tramiteSubsanacion;
	}


	public String getPermitirSms() {
		return permitirSms;
	}


	public void setPermitirSms(String permitirSms) {
		this.permitirSms = permitirSms;
	}


	public String getAccesoPorClave() {
		return accesoPorClave;
	}


	public void setAccesoPorClave(String accesoPorClave) {
		this.accesoPorClave = accesoPorClave;
	}
	
}
