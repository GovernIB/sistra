package es.caib.sistra.back.form;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class ImportarDominioForm extends ValidatorForm
{
	private transient FormFile fitxer;
	private Long codigoOrganoOrigen;
	public FormFile getFitxer()
	{
		return fitxer;
	}
	public void setFitxer(FormFile fitxer)
	{
		this.fitxer = fitxer;
	}
	public Long getCodigoOrganoOrigen()
	{
		return codigoOrganoOrigen;
	}
	public void setCodigoOrganoOrigen(Long codigoOrganoOrigen)
	{
		this.codigoOrganoOrigen = codigoOrganoOrigen;
	}
}
