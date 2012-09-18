package org.ibit.rol.form.back.action.pantalla;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PantallaDelegate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Guarda la lista de pantallas de un formulario en el contexto.
 */
public class ListaPantallasController implements Controller{
    protected static Log log = LogFactory.getLog(ListaPantallasController.class);

    public void perform(ComponentContext    tileContext,
                        HttpServletRequest  request,
                        HttpServletResponse response,
                        ServletContext      servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en ListaPantallasController");

             Object attribute = tileContext.getAttribute("id");
             Long id = new Long(attribute.toString());

            PantallaDelegate pantallaDelegate = DelegateUtil.getPantallaDelegate();
            
            // -- INDRA: LISTA ELEMENTOS
            // Filtramos pantallas de detalle
            // tileContext.putAttribute("pantallaOptions", pantallaDelegate.listarPantallasFormulario(id));
            List pantallas = pantallaDelegate.listarPantallasFormulario(id);
            List pantallasMostrar =new ArrayList();
            for (Iterator it=pantallas.iterator();it.hasNext();){
            	Pantalla p =  (Pantalla) it.next();
            	if (StringUtils.isEmpty(p.getComponenteListaElementos())) pantallasMostrar.add(p);
            }
            //tileContext.putAttribute("pantallaOptions", pantallasMostrar);
            tileContext.putAttribute("pantallaOptions", pantallaDelegate.listarPantallasFormulario(id));
            // -- INDRA: LISTA ELEMENTOS
            
            
            tileContext.putAttribute("idFormulario", id);

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
