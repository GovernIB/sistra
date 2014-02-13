package org.ibit.rol.form.front.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;

/**
 *  
 * @struts.action
 *  name="pantallaForm"
 *  path="/procesarDetalle"
 *  scope="request"
 *  validate="true"
 *  input=".verPantalla"
 *  
 *  @struts.action-forward name="success" path=".verPantalla"
 *  
 */
public class ProcesarPantallaDetalleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(ProcesarPantallaDetalleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	
    	InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }        
        
        boolean telematic = (delegate instanceof InstanciaTelematicaDelegate);
        
        if (isCancelled(request)) {
        	if (request.getParameter("DISCARD") != null) {
                if (telematic) {
                    try {
                        InstanciaTelematicaDelegate tDelegate = (InstanciaTelematicaDelegate) delegate;
                        String redirectUrl = tDelegate.cancelarFormulario();
                        response.reset();
                        response.sendRedirect(redirectUrl);
                        // INDRA: PROBLEMA CON FIREFOX??
                        // response.flushBuffer();
                        return null;
                    } finally {
                        RegistroManager.desregistrarInstancia(request);
                    }
                }

                RegistroManager.desregistrarInstancia(request);
                return mapping.findForward("main");

            } else { // Es tornar enrera
            	delegate.retrocederPantallaDetalle(false);
            }
        }else{
        	// Introducimos datos en elemento detalle
            PantallaForm pantallaForm = (PantallaForm) form;
            // - Creamos copia para que no se modifique el map origen
            Map datosFormOrigen = pantallaForm.getMap();
            Map datosForm = new HashMap();
            datosForm.putAll(datosFormOrigen);
            delegate.introducirDatosPantalla(datosForm);
            
            // Controlamos si tras salvar insertamos automaticamente un nuevo registro
            String insertPostSave = request.getParameter("INSERT_POST_SAVE");
            if (StringUtils.isNotEmpty(insertPostSave) && "true".equals(insertPostSave)){
            	// Insertamos tras salvar
            	delegate.retrocederPantallaDetalle(true);            	
            	int indice = Integer.parseInt(request.getParameter("listaelementos@indice")) + 1;
            	response.sendRedirect(prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") + 
            			"/verDetalle.do?listaelementos@accion=insertar" + 
            			"&listaelementos@campo="+request.getParameter("listaelementos@campo") +
            			"&listaelementos@indice="+ indice));
            	return null;
            }else{
	            // Volvemos a pantalla anterior        
	            delegate.retrocederPantallaDetalle(true);
            }
        }
        
        //return mapping.findForward("success");
        
        // Si es version 2 la pantalla de elementos se ejecuta en un iframe. La redireccion la hara el iframe.
        Formulario formulario = delegate.obtenerFormulario();
        if (formulario.getModoFuncionamiento().getCodigo() >= 2) {
        	// Version >2: Marcamos atributo para que el iframe redirija
        	request.setAttribute("listaelementos@retorno", "true");
            return mapping.findForward("success");
        } else {
        	// Version 1: Hacemos redireccion
        	String urlRetornoPantalla = prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") + "/ver.do");
            response.sendRedirect(urlRetornoPantalla);
            return null;
        }
        
    }

}
