package es.caib.bantel.front.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;



public class DetalleAvisoForm extends ValidatorForm

{
	private String identificadorExpediente;
	private String unidadAdministrativa;
	private String claveExpediente;
	private String titulo;
	private String texto;
	private String textoSMS;
	private String documentoAnexoTitulo;
	private FormFile documentoAnexoFichero;
	private String descripcionExpediente;
    private String firmar;
    private String flagValidacion;
    private String rutaFitxer;
    private String firma;
    private String idioma;
    
	public String getIdioma() {
		if(idioma == null || "".equals(idioma)){
			return "es";
		}
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getClaveExpediente() {
		return claveExpediente;
	}

	public void setClaveExpediente(String claveExpediente) {
		this.claveExpediente = claveExpediente;
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

	public String getIdentificadorExpediente() {
		return identificadorExpediente;
	}

	public void setIdentificadorExpediente(String identificadorExpediente) {
		this.identificadorExpediente = identificadorExpediente;
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

	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
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
        boolean error = false;
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        if(documentoAnexoFichero != null && documentoAnexoFichero.getFileName() != null && !"".equals(documentoAnexoFichero.getFileName())){
        	
        	if(!DocumentosUtil.extensionCorrecta(documentoAnexoFichero.getFileName())){
        		errors.add("altaNotificacion",new ActionError("error.aviso.extensiones.fichero"));
        		error = true;
        	}
        }
        if(StringUtils.isNotEmpty(flagValidacion) && flagValidacion.equals("alta")){
        	if(StringUtils.isEmpty(titulo)){
        		errors.add("altaAviso", new ActionError("errors.required", MensajesUtil.getValue("aviso.titulo")));
        		error = true;
        	}
        	if(StringUtils.isEmpty(texto)){
        		errors.add("altaAviso", new ActionError("errors.required", MensajesUtil.getValue("aviso.texto")));
        		error = true;
        	}
        	if(error){
        		List unidades = new ArrayList();
				try {
					unidades = Dominios.listarUnidadesAdministrativas();
					
				} catch (Exception e) {}
				request.setAttribute("unidades",unidades);
        	}
        		
        }
    	
        return errors;
    }
	
}
