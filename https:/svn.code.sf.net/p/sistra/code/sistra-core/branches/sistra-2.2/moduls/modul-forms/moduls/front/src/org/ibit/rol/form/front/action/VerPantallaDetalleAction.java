package org.ibit.rol.form.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 *  
 * @struts.action
 *  path="/verDetalle"
 *  scope="request"
 *  
 */
public class VerPantallaDetalleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(VerPantallaDetalleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	
    	String detalleAccion = request.getParameter("listaelementos@accion");
    	String detalleCampo = request.getParameter("listaelementos@campo");
    	String detalleIndice = request.getParameter("listaelementos@indice");
    	    	
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }        
        
        // Vemos si la accion es insertar/modificar --> redireccionamos a pantalla detalle        
        if (detalleAccion.equals("insertar") || detalleAccion.equals("modificar")){
        	// Pasamos a ventana detalle
            delegate.avanzarPantallaDetalle(detalleCampo,detalleAccion,detalleIndice);            
        }else if (detalleAccion.equals("eliminar")){        
        	// borrar --> borramos elemento y nos quedamos en pantalla actual
        	delegate.eliminarElemento(detalleCampo,detalleIndice);
        }else if (detalleAccion.equals("subir")){        
        	// subir --> reordenamos y nos quedamos en pantalla actual
        	delegate.subirElemento(detalleCampo,detalleIndice);
        }else if (detalleAccion.equals("bajar")){        
        	// borrar --> reordenamos y nos quedamos en pantalla actual
        	delegate.bajarElemento(detalleCampo,detalleIndice);
        }
        
        //return mapping.findForward("success");
        response.sendRedirect(prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") +
        		"/ver.do?listaelementos@accion="+detalleAccion + 
        		"&listaelementos@campo="+detalleCampo +
        		"&listaelementos@indice="+detalleIndice));    	
        // INDRA: PROBLEMA CON FIREFOX??
        // response.flushBuffer();
        return null;
    }

}
