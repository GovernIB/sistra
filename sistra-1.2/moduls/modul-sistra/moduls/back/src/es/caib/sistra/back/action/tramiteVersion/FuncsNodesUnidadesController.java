package es.caib.sistra.back.action.tramiteVersion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.util.MessageResources;

import es.caib.sistra.back.action.BaseController;
import es.caib.sistra.modelInterfaz.ConstantesDominio;

public class FuncsNodesUnidadesController extends BaseController
{
    protected static Log log = LogFactory.getLog(FuncsNodesUnidadesController.class);
    
    
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
        try {
            log.debug("Entramos en FuncsNodesUnidadesController");
            
            request.setAttribute( "unidadesadministrativas", createNodos(request,   obtenerValoresDominio( ConstantesDominio.DOMINIO_SAC_ARBOL_UNIDADES_ADMINISTRATIVAS  )));
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
    
    
    private List createNodos(HttpServletRequest request, List unidadesAdministrativas)
    {
    	ArrayList result = new ArrayList();
		String parentId = "foldersTree"; 
    	NodoUnidadAdministrativa nodoRaiz = new NodoUnidadAdministrativa();
    	nodoRaiz.setId( parentId );
		nodoRaiz.setDescripcion(getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"tramiteVersion.unidadesAdministrativas"));
    	result.add( nodoRaiz );
    	NodoUnidadAdministrativa nodo = null;

    	// Cogemos el primero que es el Padre de todos
    	
    	for(int i=0; i<unidadesAdministrativas.size(); i++)
    	{
    		Map datos = (Map)unidadesAdministrativas.get(i);
    		String superior = (String) datos.get("PARENT");
    		nodo = new NodoUnidadAdministrativa();
    		String descripcion = (String) datos.get("DESCRIPCION");
    		if(descripcion == null) continue;
    		nodo.setDescripcion(descripcion);
    		String codigo = "c_" + (String) datos.get("CODIGO");
    		nodo.setId(codigo);
    		nodo.setFolder(true);
    		if(superior != null)
    		{
       			nodo.setParentId("c_" + superior);
    		}
    		else
    		{
    			nodo.setParentId(nodoRaiz.getId());
    		}
    		result.add(nodo);
    	}
    	return result;
    }
    
    public MessageResources getResources( HttpServletRequest request )
    {
     return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    }

    
}
