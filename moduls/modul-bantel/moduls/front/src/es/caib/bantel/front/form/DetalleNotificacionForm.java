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
import es.caib.bantel.front.util.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;


public class DetalleNotificacionForm extends ValidatorForm
{
	private String identificadorExpediente;
	private String unidadAdministrativa;
	private String claveExpediente;
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
	private String tipoAsunto;
	private String tituloAviso;
	private String textoAviso;
	private String textoSmsAviso;
	private String tituloOficio;
	private String textoOficio;
	private String tituloAnexoOficio;
	private FormFile documentoAnexoOficio;
	private String modeloAnexo;
    private String versionAnexo;
    
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


	public FormFile getDocumentoAnexoOficio() {
		return documentoAnexoOficio;
	}


	public void setDocumentoAnexoOficio(FormFile documentoAnexoOficio) {
		this.documentoAnexoOficio = documentoAnexoOficio;
	}


	public String getIdentificadorExpediente() {
		return identificadorExpediente;
	}


	public void setIdentificadorExpediente(String identificadorExpediente) {
		this.identificadorExpediente = identificadorExpediente;
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


	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}


	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}


	public String getUsuarioSey() {
		return usuarioSey;
	}


	public void setUsuarioSey(String usuarioSey) {
		this.usuarioSey = usuarioSey;
	}


	public String getModeloAnexo() {
		return modeloAnexo;
	}


	public void setModeloAnexo(String modeloAnexo) {
		this.modeloAnexo = modeloAnexo;
	}


	public String getVersionAnexo() {
		return versionAnexo;
	}


	public void setVersionAnexo(String versionAnexo) {
		this.versionAnexo = versionAnexo;
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

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        boolean error = false;
        if(documentoAnexoOficio != null && documentoAnexoOficio.getFileName() != null  && !"".equals(documentoAnexoOficio.getFileName())){
        	if(!DocumentosUtil.extensionCorrecta(documentoAnexoOficio.getFileName())){
        		errors.add("altaNotificacion",new ActionError("error.aviso.extensiones.fichero"));
        		error = true;
        	}
        }
        if(StringUtils.isNotEmpty(flagValidacion) && flagValidacion.equals("altaNotificacion")){
        	
        	
        	if(StringUtils.isEmpty(usuarioSey)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("expediente.usuarioSeycon")));
        		error = true;
        	}
        	if(StringUtils.isEmpty(nif)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("expediente.nif")));
        		error = true;
        	}
        	if(StringUtils.isEmpty(apellidos)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("notificacion.nombre.apellisos")));
        		error = true;
        	}
        	if(StringUtils.isEmpty(tituloAviso)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.aviso.titulo") ));
        		error = true;
        	}
        	if(StringUtils.isEmpty(textoAviso)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.aviso.texto")));
        		error = true;
        	}
        	if(StringUtils.isEmpty( tituloOficio )){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.oficio.titulo")));
        		error = true;
        	}
        	if(StringUtils.isEmpty( textoOficio )){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.oficio.texto")));
        		error = true;
        	}
        	if(StringUtils.isEmpty(tipoAsunto)){
        		errors.add("altaNotificacion", new ActionError("errors.required", MensajesUtil.getValue("valida.notificacion.tipo.asunto")));
        		error = true;
        	}
        	if(error){
				try {
					carregarLlistes(request);
				} catch (Exception e) {}
        	}
        		
        }  
        return errors;
    }

	private void carregarLlistes(HttpServletRequest request) throws Exception{
		List unidades=Dominios.listarUnidadesAdministrativas();
		request.setAttribute("unidades",unidades);
		List paises = Dominios.listarPaises();
		request.setAttribute("paises",paises);
		List provincias = Dominios.listarProvincias();
		request.setAttribute("provincias",provincias);
		List municipios = new ArrayList();
		if(StringUtils.isNotEmpty(codigoProvincia))
			municipios = Dominios.listarLocalidadesProvincia(codigoProvincia);
		request.setAttribute("municipios",municipios);
		RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
        List organosDestino = dlgRte.obtenerServiciosDestino();
        request.setAttribute( "listaorganosdestino", regtelToBantel(organosDestino));
        List oficinasRegistro = dlgRte.obtenerOficinasRegistro();
        request.setAttribute( "listaoficinasregistro", regtelToBantel(oficinasRegistro));
        List tiposAsunto = dlgRte.obtenerTiposAsunto();
        request.setAttribute("tiposAsunto", regtelToBantel(tiposAsunto));
	}
	
	private List regtelToBantel(List lista){
		List listaBantel = new ArrayList();
		if(lista != null){
			for(int i=0;i<lista.size();i++){
				ValorOrganismo vo = new ValorOrganismo();
				vo.setCodigo(((es.caib.regtel.model.ValorOrganismo)lista.get(i)).getCodigo());
				vo.setDescripcion(((es.caib.regtel.model.ValorOrganismo)lista.get(i)).getDescripcion());
				listaBantel.add(vo);
			}
		}
		return listaBantel;
	}
	
}
