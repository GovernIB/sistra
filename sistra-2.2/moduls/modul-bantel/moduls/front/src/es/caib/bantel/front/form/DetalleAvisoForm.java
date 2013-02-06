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



public class DetalleAvisoForm extends ValidatorForm

{
	private String titulo;
	private String texto;
	private String permitirSms = "N"; // S / N 
	private String textoSMS;
	
	private String tipoDocumento; // URL / FICHERO
	private String documentoAnexoTitulo;
	private FormFile documentoAnexoFichero;	
	private String  documentoUrlAnexo;
	
	
	private String descripcionExpediente;
    private String firmar;
    private String flagValidacion;
    private String rutaFitxer;
    private String firma;
    private String idioma;
    
    private String accesoPorClave;
    
    private String existeNifExpediente;
    
	public String getIdioma() {
		if(idioma == null || "".equals(idioma)){
			return "es";
		}
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getDescripcionExpediente() {
		return descripcionExpediente;
	}

	public void setDescripcionExpediente(String descripcionExpediente) {
		this.descripcionExpediente = descripcionExpediente;
	}

	public FormFile getDocumentoAnexoFichero() {
		return documentoAnexoFichero;
	}

	public void setDocumentoAnexoFichero(FormFile documentoAnexoFichero) {
		this.documentoAnexoFichero = documentoAnexoFichero;
	}

	public String getDocumentoAnexoTitulo() {
		return documentoAnexoTitulo;
	}

	public void setDocumentoAnexoTitulo(String documentoAnexoTitulo) {
		this.documentoAnexoTitulo = documentoAnexoTitulo;
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTextoSMS() {
		return textoSMS;
	}

	public void setTextoSMS(String textoSMS) {
		this.textoSMS = textoSMS;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public String getRutaFitxer() {
		return rutaFitxer;
	}

	public void setRutaFitxer(String rutaFitxer) {
		this.rutaFitxer = rutaFitxer;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        if(documentoAnexoFichero != null && documentoAnexoFichero.getFileName() != null && !"".equals(documentoAnexoFichero.getFileName())){
        	
        	if(!DocumentosUtil.extensionCorrecta(documentoAnexoFichero.getFileName())){
        		errors.add("altaNotificacion",new ActionError("error.aviso.extensiones.fichero"));
        	}
        }
        if(StringUtils.isNotEmpty(flagValidacion) && flagValidacion.equals("alta")){
        	if(StringUtils.isEmpty(titulo)){
        		errors.add("altaAviso", new ActionError("errors.required", MensajesUtil.getValue("aviso.titulo")));
        	}
        	if(StringUtils.isEmpty(texto)){
        		errors.add("altaAviso", new ActionError("errors.required", MensajesUtil.getValue("aviso.texto")));
        	}   
        	if(StringUtils.isNotEmpty(titulo) && titulo.length() > 500){
        		errors.add("altaAviso", new ActionError("errors.maxlength", MensajesUtil.getValue("aviso.titulo"), "500"));
        	}
        	if(StringUtils.isNotEmpty(texto) && texto.length() > 4000){
        		errors.add("altaAviso", new ActionError("errors.maxlength", MensajesUtil.getValue("aviso.texto"), "4000"));
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

	public String getDocumentoUrlAnexo() {
		return documentoUrlAnexo;
	}

	public void setDocumentoUrlAnexo(String urlAnexo) {
		this.documentoUrlAnexo = urlAnexo;
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

	public void setAccesoPorClave(String accesiblePorClave) {
		this.accesoPorClave = accesiblePorClave;
	}

	public String getExisteNifExpediente() {
		return existeNifExpediente;
	}

	public void setExisteNifExpediente(String existeNifExpediente) {
		this.existeNifExpediente = existeNifExpediente;
	}
	
}
