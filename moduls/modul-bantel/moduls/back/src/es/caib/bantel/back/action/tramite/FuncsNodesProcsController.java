package es.caib.bantel.back.action.tramite;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;

import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;


public class FuncsNodesProcsController implements Controller
{
    protected static Log log = LogFactory.getLog(FuncsNodesProcsController.class);
    
    
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
        try {
            log.debug("Entramos en FuncsNodesProcsController");
            request.setAttribute( "unidadesProcs", createNodos(request));
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
    
    
    private List createNodos(HttpServletRequest request) throws Exception
    {
    	// Obtenemos valores dominio del EJB			
    	ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GESACPRO" , null);
    	ArrayList result = new ArrayList();
		String parentId = "foldersTree"; 
    	NodoProcs nodoRaiz = new NodoProcs();
    	nodoRaiz.setId( parentId );
		nodoRaiz.setDescripcion(getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"tramite.procedimientosExt"));
    	result.add( nodoRaiz );
    	NodoProcs nodo = null;
    	
    	String identificador,descripcion;
    	
    	log.debug("PROCEDIMIENTOS DEVUELTOS POR EL DOMINIO: " + dom.getNumeroFilas());
    	for(int i=1; i<=dom.getNumeroFilas(); i++)
    	{
    		identificador = dom.getValor(i,"CODIGO");
    		descripcion= identificador + "-" + StringUtils.defaultString(dom.getValor(i,"DESCRIPCION"));
			//if (descripcion.length() > 35) descripcion = descripcion.substring(0,33) + "...";
    		// Insertamos nodo para version
    		nodo = new NodoProcs();
			nodo.setDescripcion(descripcion);
			nodo.setFolder(true);
			nodo.setParentId(parentId);
			nodo.setId("I_"+identificador);
			nodo.setIdentificador(identificador);
			nodo.setDescripcion(descripcion);
			result.add(nodo);
    	}
    	log.debug("NODOS DEVUELTOS " + result.size());
    	return result;
    }
    
    public MessageResources getResources( HttpServletRequest request )
    {
     return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    }

    
}
