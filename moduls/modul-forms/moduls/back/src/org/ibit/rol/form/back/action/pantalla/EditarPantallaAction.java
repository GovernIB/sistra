package org.ibit.rol.form.back.action.pantalla;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.PantallaForm;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PantallaDelegate;
import org.ibit.rol.form.persistence.util.CampoUtils;

/**
 * Action para editar una pantalla.
 *
 * @struts.action
 *  name="pantallaForm"
 *  scope="session"
 *  validate="true"
 *  input=".pantalla.editar"
 *  path="/back/pantalla/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/pantalla/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".pantalla.editar"
 *
 * @struts.action-forward
 *  name="success" path=".pantalla.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".formulario.editar"
 *  
 * @struts.action-forward
 *  name="error" path=".idOperacion.error"
 *
 * @struts.action-forward
 *  name="cancelListaElementos" path=".listaelementos.editar"
 *
 */
public class EditarPantallaAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarPantallaAction.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarPantalla");
        PantallaDelegate pantallaDelegate = DelegateUtil.getPantallaDelegate();
        PantallaForm pantallaForm = (PantallaForm) form;
        Pantalla pantalla = (Pantalla) pantallaForm.getValues();

        /*
        En el caso de volver atrás desde una pantalla del formulario, si este está
        bloqueado debe informarse de esta propiedad
        */
		if(Util.getOperacionPermitida(request)){  
        if (isCancelled(request)) {
            log.debug("isCancelled");
            
            // INDRA: LISTA ELEMENTOS
            // Si la pantalla es de detalle de lista de elementos volvemos al elemento lista
            if (StringUtils.isNotEmpty(pantalla.getComponenteListaElementos())){
            	PantallaDelegate pd = DelegateUtil.getPantallaDelegate();
            	String nomPantalla = CampoUtils.getPantallaListaElementos(pantalla.getComponenteListaElementos());
            	String nomComponente = CampoUtils.getCampoListaElementos(pantalla.getComponenteListaElementos());
            	Pantalla p = pd.obtenerPantalla(pantalla.getFormulario().getModelo(),pantalla.getFormulario().getVersion(),nomPantalla);
            	Componente componente = null;
            	for (Iterator it=p.getComponentes().iterator();it.hasNext();){
            		componente = (Componente) it.next();
            		if (componente.getNombreLogico().equals(nomComponente)) break;
            	}
            	guardarComponente(mapping, request, componente.getId());
            	return mapping.findForward("cancelListaElementos");
            }            
            // INDRA: LISTA ELEMENTOS
            
            Long idFormulario = pantallaForm.getIdFormulario();
            Formulario formulario = guardarFormulario(mapping, request, idFormulario);
           
            return mapping.findForward("cancel");
        }

        // Elimina traducciones que no son validas
        pantallaForm.validaTraduccion(mapping, request);


        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");

            Long idFormulario = pantallaForm.getIdFormulario();
            pantallaDelegate.gravarPantalla(pantalla, idFormulario);

            //actualizaPath(request.getSession(true), 2, pantalla.getId().toString());
            log.debug("Creat/Actualitzat " + pantalla.getId());

            //guardarFormulario(mapping, request, idFormulario);
            guardarPantalla(mapping, request, pantalla.getId());

            request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        
        // Cambio de idioma
        pantallaForm.reloadLang();
        return mapping.findForward("reload");
		}else{
			log.debug("Error el id de operación modificado es diferente al id de operación de la sesión.");
        	return mapping.findForward("error");
		}
    }

}
