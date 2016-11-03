package es.caib.bantel.back.action.fuenteDatos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.FuenteDatosForm;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;

/**
 * Action para editar fuente datos.
 *
 * @struts.action
 *  name="fuenteDatosForm"
 *  scope="session"
 *  validate="true"
 *  input=".fuenteDatos.editar"
 *  path="/back/fuenteDatos/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".fuenteDatos.editar"
 *
 * @struts.action-forward
 *  name="success" path=".fuenteDatos.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".fuenteDatos.lista"
 *  
 */
public class EditarFuenteDatosAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarFuenteDatosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	FuenteDatosDelegate fuenteDatosDelegate = DelegateUtil.getFuenteDatosDelegate();
        FuenteDatosForm fuenteDatosForm = (FuenteDatosForm) form;
        FuenteDatos fuenteDatos = (FuenteDatos) fuenteDatosForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarFuenteDatos") != null) 
        {
            return mapping.findForward("reload");
        }         
        
        if (isAlta(request) || isModificacion(request)) {
            if (isAlta(request)) {
	        	fuenteDatosDelegate.altaFuenteDatos(fuenteDatos.getIdentificador(), fuenteDatosForm.getIdProcedimiento(), fuenteDatos.getDescripcion());        	        	
	        } else {
	        	fuenteDatosDelegate.modificarFuenteDatos(fuenteDatosForm.getIdentificadorOld(), fuenteDatos.getIdentificador(), fuenteDatosForm.getIdProcedimiento(), fuenteDatos.getDescripcion());
	        }       
	            
	        //request.setAttribute("reloadMenu", "true");
	        log.debug("Creat/Actualitzat " + fuenteDatos.getIdentificador());
	
	        guardarFuenteDatos(mapping, request, fuenteDatos.getIdentificador());
	        return mapping.findForward("success");
        }

        return mapping.findForward("reload");
    }       

}
