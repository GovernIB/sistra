package org.ibit.rol.form.back.action.componente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.ComponenteForm;
import org.ibit.rol.form.back.form.EleccionComponenteForm;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PantallaDelegate;

/**
 * Action para elegir el tipo de Componente.
 *
 * @struts.action
 *  name="eleccionComponenteForm"
 *  scope="request"
 *  validate="true"
 *  input=".componente.eleccion"
 *  path="/back/componente/eleccion"
 *
 * @struts.action-forward
 *  name="label" path=".label.editar"
 * @struts.action-forward
 *  name="textbox" path=".textbox.editar"
 * @struts.action-forward
 *  name="combobox" path=".combobox.editar"
 * @struts.action-forward
 *  name="listbox" path=".listbox.editar"
 * @struts.action-forward
 *  name="treebox" path=".treebox.editar"
 * @struts.action-forward
 *  name="listaelementos" path=".listaelementos.editar"
 * @struts.action-forward
 *  name="checkbox" path=".checkbox.editar"
 * @struts.action-forward
 *  name="filebox" path=".filebox.editar"
 * @struts.action-forward
 *  name="radiobutton" path=".radiobutton.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".pantalla.editar"
 */
public class EleccionComponenteAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EleccionComponenteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

       log.debug("Entramos en EleccionComponente");
       EleccionComponenteForm ecForm = (EleccionComponenteForm) form;

        if (isCancelled(request)) {
            Long idPantalla = ecForm.getIdPantalla();
            guardarPantalla(mapping, request, idPantalla);
            return mapping.findForward("cancel");
        }

        // --- INDRA: CONTROL DE QUE UNA PAGINA DETALLE NO SE PUEDE ESTABLECER UNA LISTA DE ELEMENTOS
        PantallaDelegate pd = DelegateUtil.getPantallaDelegate();
        if (StringUtils.isNotEmpty(pd.obtenerPantalla(ecForm.getIdPantalla()).getComponenteListaElementos()) && 
        		ecForm.getTipo().equals("listaelementos") ){
        	// TODO RAFA CONTROLAR MENSAJE
        	throw new Exception("No se puede elegir un componente de lista de elementos en una pantalla detalle de lista de elementos");        
        }
        // --- INDRA: CONTROL DE QUE UNA PAGINA DETALLE NO SE PUEDE ESTABLECER UNA LISTA DE ELEMENTOS
        
        ComponenteForm cForm = (ComponenteForm) obtenerActionForm(mapping, request, "/back/" + ecForm.getTipo() + "/editar") ;
        cForm.destroy(mapping, request);
        cForm.setIdPantalla(ecForm.getIdPantalla());

        return mapping.findForward(ecForm.getTipo());
    }
}
