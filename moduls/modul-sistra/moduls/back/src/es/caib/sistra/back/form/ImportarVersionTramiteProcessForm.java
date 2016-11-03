package es.caib.sistra.back.form;

import org.apache.struts.action.ActionForm;

public class ImportarVersionTramiteProcessForm extends ActionForm
{
	private Long codigoTramite;
	private Long unidadAdministrativa;
	private String organoDestino;
	private String registroOficina;
    private String registroAsunto;
    
	public String getOrganoDestino() {
		return organoDestino;
	}
	public void setOrganoDestino(String organoDestino) {
		this.organoDestino = organoDestino;
	}
	public String getRegistroOficina() {
		return registroOficina;
	}
	public void setRegistroOficina(String registroOficina) {
		this.registroOficina = registroOficina;
	}
	public String getRegistroAsunto() {
		return registroAsunto;
	}
	public void setRegistroAsunto(String registroAsunto) {
		this.registroAsunto = registroAsunto;
	}
	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public Long getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(Long codigoTramite) {
		this.codigoTramite = codigoTramite;
	}
	
		
}
