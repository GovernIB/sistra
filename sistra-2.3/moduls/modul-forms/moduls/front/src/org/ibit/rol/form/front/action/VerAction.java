package org.ibit.rol.form.front.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;

/**
 * @struts.action
 *  path="/ver"
 *
 * @struts.action
 *  path="/auth/ver"
 *
 * @struts.action
 *  path="/secure/ver"
 *
 * @struts.action
 *  path="/auth/secure/ver"
 *
 * @struts.action-forward name="view" path=".verPantalla"
 * @struts.action-forward name="end"  path=".penultima"
 */
public class VerAction extends BaseAction {

    protected static Log log = LogFactory.getLog(VerAction.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {
        
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        Pantalla p = delegate.obtenerPantalla();
        if (p == null) {
        	
        	// En caso de ser telematico comprobamos si debemos ir a la penultima pantalla o bien devolver el control
        	boolean telematic = (delegate instanceof InstanciaTelematicaDelegate);
        	if (telematic) {
                InstanciaTelematicaDelegate itd = (InstanciaTelematicaDelegate) delegate;
                Map propiedadesForm = itd.obtenerPropiedadesFormulario();
                boolean mostrarPantallaFin = false;
            	try{
            		mostrarPantallaFin = (propiedadesForm.get("pantallaFin.mostrar")!=null?Boolean.parseBoolean((String)propiedadesForm.get("pantallaFin.mostrar")):false);
            	}catch (Exception ex){
            		log.error("La propiedad pantallaFin.mostrar no tiene un valor válido (true/false): " + propiedadesForm.get("pantallaFin.mostrar"));
            		mostrarPantallaFin = false;
            	}
            	if (!mostrarPantallaFin){
            		response.sendRedirect("finalitzarTelematic.do?ID_INSTANCIA=" + (String) request.getAttribute(RegistroManager.ID_INSTANCIA));
            		return null;
            	}
            }
        	
        	// Vamos a penultima pantalla
            return mapping.findForward("end");
            
        } else {
            ModuleConfig config = mapping.getModuleConfig();
            ActionMapping nextMapping = (ActionMapping) config.findActionConfig("/procesar");
            PantallaForm pantallaForm = (PantallaForm) RequestUtils.createActionForm(request, nextMapping, config, getServlet());
            request.setAttribute(nextMapping.getAttribute(), pantallaForm);
            pantallaForm.reset(nextMapping, request);
            // Alimentamos con datos pantalla actual
        	BeanUtils.populate(pantallaForm, delegate.obtenerDatosPantalla());
            return mapping.findForward("view");
        }
    }
}
