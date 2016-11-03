package es.caib.redose.back.trad;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.model.Formateador;
import es.caib.redose.model.Idioma;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Traduccion;


public class PlantillaTraducible extends Plantilla implements Traducible
{
	protected static Log log = LogFactory.getLog(PlantillaTraducible.class);
	
	public PlantillaTraducible()
	{
		super();
	}
	
	
	/*
	public PlantillaTraducible( Plantilla plantilla )
	{
		log.info( "CONSTRUCTOR; PLANTILLA PERSISTENTE : " + plantilla );
		
		this.plantilla = plantilla;
		super.setCodigo( plantilla.getCodigo() );
		super.setTipo( plantilla.getTipo() );
		super.setPlantillasIdioma( plantilla.getPlantillasIdioma() );
		super.setVersion( plantilla.getVersion() );
		
		Set plantillasIdioma = plantilla.getPlantillasIdioma();
		Iterator it = plantillasIdioma.iterator();
		while ( it.hasNext() )
		{
			PlantillaIdioma plantillaIdioma = ( PlantillaIdioma ) it.next();
			traducciones.put( plantillaIdioma.getIdioma(), plantillaIdioma );
		}
		
		log.info( "CONSTRUCTOR, CONSTRUIDO MAP DE TRADUCCIONES : " + traducciones );
	}
	*/
	
	private Plantilla plantilla;
	
	// TODO rellenar el traducciones
	private Map traducciones = new HashMap();
	
	protected String currentLang = Idioma.DEFAULT;

	public Map getTraducciones()
	{
		return traducciones;
	}

	public Traduccion getTraduccion(String idioma)
	{
		return (Traduccion) traducciones.get(idioma);
	}

	public Traduccion getTraduccion()
	{
		return (Traduccion) traducciones.get(currentLang);
	}

	public void setTraduccion(String idioma, Traduccion traduccion)
	{
		if (traduccion == null) {
            traducciones.remove(idioma);
        } else {
            traducciones.put(idioma, traduccion);
        }
	}

	public Set getLangs()
	{
		return this.traducciones.keySet();
	}

	public String getCurrentLang()
	{
		return currentLang;
	}

	public void setCurrentLang(String currentLang)
	{
		if (currentLang != null && getLangs().contains(currentLang)) 
		{
            this.currentLang = currentLang;
        }
	}
	
	public Plantilla getPlantilla()
	{
		if ( plantilla!= null )
		{
			log.info( "PLANTILLA : " + this.plantilla );
			return plantilla;
		}

		Plantilla tmp = new Plantilla();
		
		tmp.setCodigo( getCodigo() );
		tmp.setTipo( getTipo() );
		tmp.setPlantillasIdioma( getPlantillasIdioma() );
		tmp.setVersion( getVersion() );
		tmp.setFormateador(getFormateador());
		plantilla = tmp;

		log.info( "PLANTILLA : " + this.plantilla );
		return tmp;
		
		
	}

}
