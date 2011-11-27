package org.ibit.rol.form.back.action.puntosalida;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.back.action.BaseController;
import org.ibit.rol.form.back.form.SalidaForm;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 *  Controller Salida
 */
public class SalidaController extends BaseController {
     
	public void execute(ComponentContext componentContext, HttpServletRequest request,
                         HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

            try {
                // Control bloqueo formulario
            	SalidaForm pf = (SalidaForm) request.getSession().getAttribute("salidaForm");            	
                Formulario formulario = null;
                if (pf.getIdFormulario() != null){
                	formulario  = DelegateUtil.getFormularioDelegate().obtenerFormulario(pf.getIdFormulario());                	        
                }
                controlBloqueoFormulario(request,formulario);
                                
            } catch (DelegateException e) {
                throw new ServletException(e);
            }
      }
}
