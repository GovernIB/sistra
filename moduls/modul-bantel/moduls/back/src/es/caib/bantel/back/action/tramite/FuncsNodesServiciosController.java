package es.caib.bantel.back.action.tramite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;

import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.bantel.back.util.NodoArbol;
import es.caib.bantel.back.util.UtilArbol;

public class FuncsNodesServiciosController implements Controller
{
    protected static Log log = LogFactory.getLog(FuncsNodesServiciosController.class);
    
    
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
        try {
        	RegistroTelematicoDelegate regtel = DelegateRegtelUtil.getRegistroTelematicoDelegate();
            List organosDestino = regtel.obtenerServiciosDestino(); 
        	request.setAttribute( "nodosArbol", createNodos(request, organosDestino));
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
    
    
    private List createNodos(HttpServletRequest request, List organosDestino)
    {
    	List result = new ArrayList();
    	int indexCampo = 0;
		
    	String idNodoRaiz = "foldersTree"; 
		NodoArbol nodoRaiz = new NodoArbol();
		nodoRaiz.setIdCampo(idNodoRaiz);
    	nodoRaiz.setId(idNodoRaiz);
		nodoRaiz.setDescripcion(getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"tramite.gestionExpedientes.servicioRegistro"));
    	result.add( nodoRaiz );
    	    	  
    	
    	NodoArbol nodo = null;
    	
	  	// Establecemos servicios
    	for(int i=0; i<organosDestino.size(); i++)
    	{
    		ValorOrganismo vo = (ValorOrganismo) organosDestino.get(i);
    		if(vo.getDescripcion() == null) continue;
    		
    		indexCampo++;
    		nodo = new NodoArbol();
    		nodo.setIdCampo("c"+ indexCampo);
    		nodo.setId(vo.getCodigo());
    		nodo.setDescripcion(vo.getDescripcion());
    		nodo.setSeleccionable(true);
    		if(vo.getCodigoPadre() != null)
    		{    			
       			nodo.setParentId(vo.getCodigoPadre());
    		}
    		else
    		{
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
