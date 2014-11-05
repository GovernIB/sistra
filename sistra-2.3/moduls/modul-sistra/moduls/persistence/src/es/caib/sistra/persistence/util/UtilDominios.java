package es.caib.sistra.persistence.util;

import java.util.List;

import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.plugins.PluginDominio;

public class UtilDominios
{
	public static List obtenerValoresDominio(String nombredominio ) throws Exception
    {
    	return obtenerValoresDominio( nombredominio, null );
    }
    
    public static List obtenerValoresDominio(String nombredominio, List params ) throws Exception
    {
		PluginDominio plgDom = new PluginDominio();
		String idDom = plgDom.crearDominio(nombredominio);
		
		if ( params != null )
		{
			for ( int i = 0; i < params.size(); i++ )
			{
				plgDom.establecerParametro( idDom, ( String ) params.get( i ) ); 
			}
		}
		plgDom.recuperaDominio(idDom);
		
		ValoresDominio valoresDominio = plgDom.getValoresDominio( idDom );
		List lstFilas = valoresDominio.getFilas();
		plgDom.removeDominio( idDom );
		return lstFilas;
	}
    
    public static ValoresDominio recuperarValoresDominio(String nombredominio ) throws Exception
    {
    	return recuperarValoresDominio( nombredominio, null );
    }
    
    public static ValoresDominio recuperarValoresDominio(String nombredominio, List params ) throws Exception
    {
		PluginDominio plgDom = new PluginDominio();
		String idDom = plgDom.crearDominio(nombredominio);
		
		if ( params != null )
		{
			for ( int i = 0; i < params.size(); i++ )
			{
				plgDom.establecerParametro( idDom, ( String ) params.get( i ) ); 
			}
		}
		plgDom.recuperaDominio(idDom);
				
		ValoresDominio valoresDominio = plgDom.getValoresDominio( idDom );
		plgDom.removeDominio( idDom );
		return valoresDominio;
	}
    
}
