package es.caib.audita.front.form;

import org.apache.struts.validator.ValidatorForm;

public class InitForm extends ValidatorForm
{
	private String language;
	private String lang;

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getLang()
	{
		return lang;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}
}
