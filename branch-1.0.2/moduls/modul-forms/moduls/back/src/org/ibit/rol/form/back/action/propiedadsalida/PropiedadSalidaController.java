package org.ibit.rol.form.back.action.propiedadsalida;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.back.action.BaseController;
import org.ibit.rol.form.back.form.PropiedadSalidaForm;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 *  Controller Propiedad Salida
 */
public class PropiedadSalidaController extends BaseController {
     
	public void execute(ComponentContext componentContext, HttpServletRequest request,
                         HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

            try {
                // Control bloqueo formulario
            	PropiedadSalidaForm pf = (PropiedadSalidaForm) request.getSession().getAttribute("propiedadSalidaForm");
            	PropiedadSalida p = (PropiedadSalida) pf.getValues(); 
                
                Formulario formulario = null;
                if (p.getId() != null){
                	PropiedadSalida c = DelegateUtil.getPropiedadSalidaDelegate().obtenerPropiedadSalida(p.getId());
                	formulario = c.getSalida().getFormulario();                	
                }
                controlBloqueoFormulario(request,formulario);
                                
            } catch (DelegateException e) {
                throw new ServletException(e);
            }
      }
}
