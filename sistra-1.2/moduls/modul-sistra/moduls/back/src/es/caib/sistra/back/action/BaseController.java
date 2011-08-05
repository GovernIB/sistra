package es.caib.sistra.back.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.regtel.model.ValorOrganismo;
import es.caib.sistra.persistence.util.UtilDominios;

public abstract class BaseController implements Controller
{

	protected static int MAX_COMBO_DESC = 65;
	
	public abstract void perform(ComponentContext arg0, HttpServletRequest arg1,
			HttpServletResponse arg2, ServletContext arg3)
			throws ServletException, IOException;
	
	protected List obtenerValoresDominio(String nombredominio ) throws Exception
    {
    	return obtenerValoresDominio( nombredominio, null );
    }
    
    protected List obtenerValoresDominio(String nombredominio, List params ) throws Exception
    {
    	return UtilDominios.obtenerValoresDominio( nombredominio, params );
	}
    
    protected List ajustarTamListaDesplegable( List listaDominio, int longitud, String keyCampo )
    {
    	return ajustarTamListaDesplegable( listaDominio, longitud, keyCampo, true );
    }
    
    protected List ajustarTamListaDesplegable( List listaDominio, int longitud, String keyCampo, boolean blankParam )
    {
    	List lstReturn = new ArrayList();
    	if ( blankParam )
    	{
    		HashMap hsmFila0 = new HashMap();
    		hsmFila0.put( "CODIGO", "" );
    		hsmFila0.put( keyCampo, "" );
    		lstReturn.add( hsmFila0 );
    	}
    	for ( int i = 0; i< listaDominio.size(); i++ )
    	{
    		String valorCampo,descCampo;
    		
    		// Obtenemos valor
    		Object fila = listaDominio.get( i );
    		if (fila instanceof Map){
    			Map filaMap = ( Map ) fila;
    			valorCampo = ( String ) filaMap.get("CODIGO");
    			descCampo = (String) filaMap.get(keyCampo);
    		}else { // Ajuste para que funcione con los dominios de registro
    			ValorOrganismo vo = (ValorOrganismo) fila;
    			valorCampo = vo.getCodigo();
    			descCampo = vo.getDescripcion();
    		}
    		
    		// Ajustamos tamanyo
    		valorCampo = valorCampo.length() > longitud ? valorCampo.substring( 0, longitud ) : valorCampo;
    		
    		// Añadimos fila ajustada
    		HashMap filaJust = new HashMap();
    		filaJust.put("CODIGO",valorCampo);
    		filaJust.put( keyCampo, descCampo );    		
    		lstReturn.add( filaJust );
    	}
    	return lstReturn;
    }

}
