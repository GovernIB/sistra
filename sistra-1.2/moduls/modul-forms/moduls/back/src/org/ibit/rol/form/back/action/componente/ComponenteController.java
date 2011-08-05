package org.ibit.rol.form.back.action.componente;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.back.action.BaseController;
import org.ibit.rol.form.back.form.ComponenteForm;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;

/**
 *  Controller componente
 */
public class ComponenteController extends BaseController {
     
	public void execute(ComponentContext componentContext, HttpServletRequest request,
                         HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

            try {
                // Control bloqueo formulario            	
            	String formBean = (String) componentContext.getAttribute("form.bean");
                ComponenteForm pf = (ComponenteForm) request.getSession().getAttribute(formBean);
                Componente p = (Componente) pf.getValues(); 
                
                Formulario formulario = null;
                if (p.getId() != null){
                	if (p.hasPantalla()){
                    	Pantalla pantalla = DelegateUtil.getPantallaDelegate().obtenerPantalla(p.getPantalla().getId());
                    	formulario = pantalla.getFormulario();
                    }
                }
                controlBloqueoFormulario(request,formulario);
                                
                // Lista de patrones para textbox
                PatronDelegate patronDelegate = DelegateUtil.getPatronDelegate();
                request.setAttribute("patronOptions", patronDelegate.listarPatrones());
                                
            } catch (DelegateException e) {
                throw new ServletException(e);
            }
      }
}
