package es.caib.sistra.back.action.documentoNivel;

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


public class FuncsNodesFormsController extends BaseController
{
    protected static Log log = LogFactory.getLog(FuncsNodesFormsController.class);
    
    
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
        try {
            log.debug("Entramos en FuncsNodesFormsController");
            request.setAttribute( "unidadesForms", createNodos(request,obtenerValoresDominio( ConstantesDominio.DOMINIO_FORMS_ARBOL_VERSIONES_MODELO)));
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
    
    
    private List createNodos(HttpServletRequest request, List unidadesForms)
    {
    	ArrayList result = new ArrayList();
		String parentId = "foldersTree"; 
    	NodoForms nodoRaiz = new NodoForms();
    	nodoRaiz.setId( parentId );
		nodoRaiz.setDescripcion(getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"documentoNivel.formulariosForms"));
    	result.add( nodoRaiz );
    	NodoForms nodo = null;
    	
    	String modAnt = "";
    	String modelo,descripcion,version;
    	
    	for(int i=0; i<unidadesForms.size(); i++)
    	{
    		Map datos = (Map)unidadesForms.get(i);
    		
    		modelo = (String) datos.get("MODELO");
    		descripcion = (String) datos.get("DESCRIPCION");
    		version = datos.get("VERSION").toString();
    		
    		// Controlamos cambio de modelo -> insertamos nodo para modelo
    		if (!modAnt.equals(modelo)){
    			nodo = new NodoForms();
    			nodo.setDescripcion(descripcion);
    			nodo.setId("M_"+modelo);
    			nodo.setFolder(true);
    			nodo.setParentId(nodoRaiz.getId());
    			nodo.setModelo(modelo);
    			result.add(nodo);
    			modAnt = modelo;
    		}
    		
    		// Insertamos nodo para version
    		nodo = new NodoForms();
			nodo.setDescripcion("Versión " + version);
			nodo.setFolder(true);
			nodo.setId("M_"+modelo + "_V_" + version);
			nodo.setParentId("M_"+modelo);
			nodo.setModelo(modelo);
			nodo.setVersion(version);			
			result.add(nodo);    		
    	}
    	return result;
    }
    
    public MessageResources getResources( HttpServletRequest request )
    {
     return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    }

    
}
