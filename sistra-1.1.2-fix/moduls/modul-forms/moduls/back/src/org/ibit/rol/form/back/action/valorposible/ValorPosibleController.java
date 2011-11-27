package org.ibit.rol.form.back.action.valorposible;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.back.form.ValorPosibleForm;
import org.ibit.rol.form.back.action.BaseController;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 *  Controller valor posible
 */
public class ValorPosibleController extends BaseController {
     
	public void execute(ComponentContext componentContext, HttpServletRequest request,
                         HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

            try {
                // Control bloqueo formulario
            	ValorPosibleForm pf = (ValorPosibleForm) request.getSession().getAttribute("valorPosibleForm");
                ValorPosible p = (ValorPosible) pf.getValues(); 
                
                Formulario formulario = null;
                if (p.getId() != null){
                	Componente c = DelegateUtil.getComponenteDelegate().obtenerComponente(p.getCampo().getId());
                	if (c.hasPantalla()){
                    	Pantalla pantalla = DelegateUtil.getPantallaDelegate().obtenerPantalla(c.getPantalla().getId());
                    	formulario = pantalla.getFormulario();
                    }
                }
                controlBloqueoFormulario(request,formulario);
                                
            } catch (DelegateException e) {
                throw new ServletException(e);
            }
      }
}
