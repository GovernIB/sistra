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
import es.caib.sistra.back.util.NodoArbol;
import es.caib.sistra.back.util.UtilArbol;
import es.caib.sistra.modelInterfaz.ConstantesDominio;

public class FuncsNodesUnidadesController extends BaseController
{
    protected static Log log = LogFactory.getLog(FuncsNodesUnidadesController.class);
    
    
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
        try {
            log.debug("Entramos en FuncsNodesUnidadesController");
            request.setAttribute( "nodosArbol", createNodos(request,   obtenerValoresDominio( ConstantesDominio.DOMINIO_SAC_ARBOL_UNIDADES_ADMINISTRATIVAS  )));
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
    
    
    private List createNodos(HttpServletRequest request, List unidadesAdministrativas)
    {
    	List result = new ArrayList();
    	
    	String idNodoRaiz = "foldersTree"; 
		NodoArbol nodoRaiz = new NodoArbol();
		nodoRaiz.setIdCampo(idNodoRaiz);
    	nodoRaiz.setId(idNodoRaiz);
		nodoRaiz.setDescripcion(getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"tramiteVersion.unidadesAdministrativas"));
    	result.add( nodoRaiz );
    	
    	
    	NodoArbol nodo = null;

    	// Unidades administrativas
    	for(int i=0; i<unidadesAdministrativas.size(); i++)
    	{
    		Map datos = (Map) unidadesAdministrativas.get(i);
    		
    		String codigo = (String) datos.get("CODIGO");
    		String descripcion = (String) datos.get("DESCRIPCION");
    		String codigoPadre = (String) datos.get("PARENT");
    		
    		if(descripcion == null) continue;
    		
    		nodo = new NodoArbol();
    		nodo.setIdCampo("c"+i);
    		nodo.setId(codigo);
    		nodo.setDescripcion(descripcion);
    		nodo.setSeleccionable(true);
    		
    		if(codigoPadre != null)	{
    			nodo.setParentId(codigoPadre);
    		}
    		else {
    			nodo.setParentId(nodoRaiz.getId());
    		}
    		
    		result.add(nodo);
    	}
    	
    	// Rellenamos idcampo padre
    	result = UtilArbol.prepararArbol(result);
    	
    	return result;
    }
    
    public MessageResources getResources( HttpServletRequest request )
    {
     return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    }

    
}
