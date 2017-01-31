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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;

import es.caib.regtel.model.ConstantesRegtel;
import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.sistra.modelInterfaz.ConstantesDominio;

public class FuncsNodesServiciosController implements Controller
{
    protected static Log log = LogFactory.getLog(FuncsNodesServiciosController.class);
    
    
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
        try {
            RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
            List organosDestino = dlgRte.obtenerServiciosDestino();
            request.setAttribute( "unidadesAdministrativas", createNodos(request, organosDestino));
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
    
    
    private List createNodos(HttpServletRequest request, List unidadesAdministrativas)
    {
    	ArrayList result = new ArrayList();
		String parentId = "foldersTree"; 
		
		NodoUnidadAdministrativa nodoRaiz = new NodoUnidadAdministrativa();
    	nodoRaiz.setId(parentId);
		nodoRaiz.setDescripcion(getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"tramite.gestionExpedientes.servicioRegistro"));
    	result.add( nodoRaiz );
    	
    	NodoUnidadAdministrativa nodo = null;

    	// Cogemos el primero que es el Padre de todos
    	
    	for(int i=0; i<unidadesAdministrativas.size(); i++)
    	{
    		ValorOrganismo vo = (ValorOrganismo) unidadesAdministrativas.get(i);
    		nodo = new NodoUnidadAdministrativa();
    		String descripcion = vo.getDescripcion();
    		if(descripcion == null) continue;
    		nodo.setDescripcion(vo.getCodigo() + " - " + descripcion);
    		String codigo = "c_" + vo.getCodigo();
    		nodo.setId(codigo);
    		nodo.setFolder(true);
    		if(vo.getCodigoPadre() != null)
    		{
       			nodo.setParentId("c_" + vo.getCodigoPadre());
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
