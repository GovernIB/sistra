package es.caib.bantel.back.action.tramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.bantel.back.util.Util;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.TramiteForm;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.util.CifradoUtil;

/**
 * Action para editar un Tramite.
 *
 * @struts.action
 *  name="tramiteForm"
 *  scope="session"
 *  validate="true"
 *  input=".tramite.editar"
 *  path="/back/tramite/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".tramite.editar"
 *
 * @struts.action-forward
 *  name="success" path=".tramite.editar"
 * 
 * @struts.action-forward
 * name="error" path=".idOperacion.error"
 * 
 * @struts.action-forward
 *  name="cancel" path=".tramite.lista"
 *  
 */
public class EditarTramiteAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	log.debug("Entramos en EditarTramite");
    	
    	TramiteForm tramiteForm = (TramiteForm) form;
    	
    	if (isModificacion(request)) {
    		request.setAttribute( "idReadOnly", "true" );    	
    	}
    	
    	
        ProcedimientoDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
        
        Procedimiento tramite = (Procedimiento) tramiteForm.getValues();
        
        // Elimina traducciones que no son validas
        tramiteForm.validaTraduccion(mapping, request);

        if(Util.getOperacionPermitida(request)){
        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarTramite") != null) 
        {
            return mapping.findForward("reload");
        }         
        
        
        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");
            log.info("ENTRA A GRABAR PROCEDIMIENTO");
            
            String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
            tramite.setUsr(CifradoUtil.cifrar(claveCifrado,tramiteForm.getUserPlain()));
            tramite.setPwd(CifradoUtil.cifrar(claveCifrado,tramiteForm.getPassPlain()));
            
            tramiteDelegate.grabarProcedimiento( tramite );
            //request.setAttribute("reloadMenu", "true");
            log.debug("Creat/Actualitzat " + tramite.getCodigo());

            guardarTramite(mapping, request, tramite.getCodigo());
            
            request.setAttribute( "idReadOnly", "true" );

            return mapping.findForward("success");

        }
        
        // Cambio de idioma
        tramiteForm.reloadLang();

        return mapping.findForward("reload");
        }else{
       		log.debug("Error el id de operación modificado es diferente al id de operación de la sesión.");
        	return mapping.findForward("error");
       	}
    }       

}
