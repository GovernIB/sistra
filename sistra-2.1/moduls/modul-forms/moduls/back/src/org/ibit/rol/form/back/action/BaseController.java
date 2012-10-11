package org.ibit.rol.form.back.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;

/**
 * Controller con métodos de utilidad.
 */
public abstract class BaseController implements Controller {

    public final void perform(ComponentContext tileContext,
                              HttpServletRequest request, HttpServletResponse response,
                              ServletContext servletContext)
            throws ServletException, IOException {
        try {
            execute(tileContext, request, response, servletContext);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    abstract public void execute(ComponentContext tileContext,
                                 HttpServletRequest request, HttpServletResponse response,
                                 ServletContext servletContext) throws Exception;

 
    /**
     * Realiza la comprobacion de bloqueo sobre el formulario estableciendo en la request los atributos
     * para que en las vistas se muestren o oculten los botones
     * 
     * @param request
     * @param f
     * @throws Exception
     */
    protected void controlBloqueoFormulario(HttpServletRequest request,Formulario f) throws DelegateException{
    	if (f == null || f.getId() == null){
        	// Formulario / pantalla / componente nuevo: bloqueamos xa usuario
        	request.setAttribute("bloqueado","true");     
            request.setAttribute("bloqueadoPor",request.getUserPrincipal().getName());            
        }else{
        	// Formulario existente: comprobamos quien lo ha bloqueado
        	FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        	Formulario formulario = formularioDelegate.obtenerFormulario(f.getId());
        	if (formulario.isBloqueado()){
        		// Comprobamos si lo blqamos xa el usuario 
        		if (formulario.getBloqueadoPor().equals(request.getUserPrincipal().getName())) {
        			request.setAttribute("bloqueado","true");
        		}	
        		// Indicamos que esta bloqueado
        		request.setAttribute("bloqueadoPor",formulario.getBloqueadoPor());
        	}                 
        }
    }
    
    
}
