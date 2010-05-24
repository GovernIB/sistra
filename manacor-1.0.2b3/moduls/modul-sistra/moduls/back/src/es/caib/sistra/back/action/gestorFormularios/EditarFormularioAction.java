package es.caib.sistra.back.action.gestorFormularios;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DominioForm;
import es.caib.sistra.back.form.GestorFormulariosForm;
import es.caib.sistra.back.taglib.Constants;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DominioDelegate;
import es.caib.sistra.persistence.delegate.GestorFormularioDelegate;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Dominio.
 *
 * @struts.action
 *  name="gestorFormulariosForm"
 *  scope="session"
 *  validate="true"
 *  input=".gestorFormularios.editar"
 *  path="/back/gestorFormularios/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".gestorFormularios.editar"
 *
 * @struts.action-forward
 *  name="success" path=".gestorFormularios.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".gestorFormularios.lista"
 *
 */
public class EditarFormularioAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarFormularioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarFormulario");

        GestorFormularioDelegate gFormulairoDelegate = DelegateUtil.getGestorFormularioDelegate();
        GestorFormulariosForm gFormularioForm = (GestorFormulariosForm) form;
        GestorFormulario gformulairo = (GestorFormulario) gFormularioForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarTramite") != null) 
        {
            return mapping.findForward("reload");
        } 
        
        if (isAlta(request) || isModificacion(request)) {
            log.info("isAlta || isModificacio");
            if(isAlta(request)){
            	gFormulairoDelegate.grabarFormularioAlta( gformulairo );
            }else{
            	gFormulairoDelegate.grabarFormularioUpdate( gformulairo );
            }
            log.info("Creat/Actualitzat " + gformulairo.getIdentificador());

            guardarGestorFormulario(mapping, request, gformulairo.getIdentificador());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }

}
