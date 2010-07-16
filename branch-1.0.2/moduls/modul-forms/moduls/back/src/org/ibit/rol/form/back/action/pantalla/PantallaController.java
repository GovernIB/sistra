package org.ibit.rol.form.back.action.pantalla;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.back.action.BaseController;
import org.ibit.rol.form.back.form.PantallaForm;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PerfilDelegate;

/**
 *  Controller pantalla
 */
public class PantallaController extends BaseController {
     
	public void execute(ComponentContext componentContext, HttpServletRequest request,
                         HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

            try {
                // lista de perfiles
            	PerfilDelegate perfilDelegate = DelegateUtil.getPerfilDelegate();
                request.setAttribute("perfilOptions", perfilDelegate.listarPerfiles());
                
                // Control bloqueo formulario
                PantallaForm pf = (PantallaForm) request.getSession().getAttribute("pantallaForm");
                Pantalla p = (Pantalla) pf.getValues();
                Formulario formulario =  null;
                if (p.getId() != null){
                	formulario = DelegateUtil.getFormularioDelegate().obtenerFormulario(p.getFormulario().getId());
                }
                controlBloqueoFormulario(request,formulario);
                                
            } catch (DelegateException e) {
                throw new ServletException(e);
            }
      }
}
