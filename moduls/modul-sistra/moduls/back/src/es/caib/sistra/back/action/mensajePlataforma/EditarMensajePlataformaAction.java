package es.caib.sistra.back.action.mensajePlataforma;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.MensajePlataformaForm;
import es.caib.sistra.model.MensajePlataforma;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.MensajePlataformaDelegate;

/**
 * Action para editar una MensajePlataforma.
 *
 * @struts.action
 *  name="mensajePlataformaForm"
 *  scope="session"
 *  validate="true"
 *  input=".mensajePlataforma.editar"
 *  path="/back/mensajePlataforma/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/mensajePlataforma/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".mensajePlataforma.editar"
 *
 * @struts.action-forward
 *  name="success" path=".mensajePlataforma.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".mensajePlataforma.lista"
 *
 */
public class EditarMensajePlataformaAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarMensajePlataformaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarMensajePlataforma");
        MensajePlataformaDelegate mensajePlataformaDelegate = DelegateUtil.getMensajePlataformaDelegate();
        MensajePlataformaForm mensajePlataformaForm = (MensajePlataformaForm) form;
        MensajePlataforma mensajePlataforma = (MensajePlataforma) mensajePlataformaForm.getValues();
        //TraMensajePlataforma traduccion = ( TraMensajePlataforma ) mensajePlataforma.getTraduccion( mensajePlataformaForm.getLang() );
        //TraMensajePlataforma traduccion = ( TraMensajePlataforma ) mensajePlataforma.getTraduccion();

        if (isCancelled(request)) 
        {
            log.info("isCancelled");            
            return mapping.findForward("cancel");
        }
        
        // Elimina traducciones que no son validas
        mensajePlataformaForm.validaTraduccion(mapping, request);
        
        //log.info( "EDICION DE PLANTILLA; PLANTILLA PERSISTENTE " + mensajePlataforma );

        if (isAlta(request) || isModificacion(request)) 
        {
            log.debug("isAlta || isModificacio");                                   
            
           	MensajePlataforma mensajePlataformaAntesGrabar = mensajePlataformaDelegate.obtenerMensajePlataforma( mensajePlataforma.getCodigo() );            
            mensajePlataformaDelegate.grabarMensajePlataforma(mensajePlataforma);
            log.debug("Creat/Actualitzat " + mensajePlataforma.getCodigo());
            guardarMensajePlataforma(mapping, request, mensajePlataforma.getCodigo());
            return mapping.findForward("success");

        }
        
        
        // Cambio de idioma
        mensajePlataformaForm.reloadLang();
        
        
        //log.info( "EDICION DE PLANTILLA; BEFORE FORWARD " + mensajePlataforma );
        
        
        return mapping.findForward("reload");
    }
    


}
