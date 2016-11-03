package es.caib.redose.back.trad;
import java.util.Map;
import java.util.Set;

import es.caib.redose.model.Traduccion;

public interface Traducible 
{
	public Map getTraducciones();
	
	public Traduccion getTraduccion( String idioma );
	
	public Traduccion getTraduccion();
	
	public void setTraduccion( String idioma, Traduccion traduccion );
	
	public Set getLangs();
	
	public String getCurrentLang();
	
	public void setCurrentLang(String currentLang);

}
