package org.ibit.rol.form.back.action.formularioseguro;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.back.action.BaseController;
import org.ibit.rol.form.back.form.FormularioForm;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ValidadorFirmaDelegate;

public class FormularioSeguroController extends BaseController{
    protected static Log log = LogFactory.getLog(FormularioSeguroController.class);

    public void execute(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en FormularioController");

            // CONTROL BLOQUEO FORMULARIO
            FormularioForm ff = (FormularioForm) request.getSession().getAttribute("formularioForm");
            Formulario f = (Formulario) ff.getValues();
            controlBloqueoFormulario(request,f);
            
            // MODOS FUNCIONAMIENTO        
            request.setAttribute("modosFuncionamiento",DelegateUtil.getVersionDelegate().listar());
            
            // Validadores
            ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();
            request.setAttribute("validadorOptions", validadorDelegate.listarValidadoresFirma());
            
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
