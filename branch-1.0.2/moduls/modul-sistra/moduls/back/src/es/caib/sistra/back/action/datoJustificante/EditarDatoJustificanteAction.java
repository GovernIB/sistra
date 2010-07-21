package es.caib.sistra.back.action.datoJustificante;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DatoJustificanteForm;
import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.persistence.delegate.DatoJustificanteDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.xml.ConstantesXML;

/**
 * Action para editar una DatoJustificante.
 *
 * @struts.action
 *  name="datoJustificanteForm"
 *  scope="session"
 *  validate="true"
 *  input=".datoJustificante.editar"
 *  path="/back/datoJustificante/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/datoJustificante/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".datoJustificante.editar"
 *
 * @struts.action-forward
 *  name="success" path=".datoJustificante.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".datoJustificante.lista"
 *
 */
public class EditarDatoJustificanteAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarDatoJustificanteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarDatoJustificante");
        DatoJustificanteDelegate datoJustificanteDelegate = DelegateUtil.getDatoJustificanteDelegate();
        DatoJustificanteForm datoJustificanteForm = (DatoJustificanteForm) form;
        DatoJustificante datoJustificante = (DatoJustificante) datoJustificanteForm.getValues();

        if (isCancelled(request)) 
        {
            log.info("isCancelled");
            Long idEspecTramiteNivel = datoJustificanteForm.getIdEspecTramiteNivel();
            // TODO : Ver como se guarda la espec. tramite. nivel.
            Long idTramiteNivel = datoJustificanteForm.getIdTramiteNivel();
            if ( idTramiteNivel != null )
            {
            	guardarTramiteNivel(mapping, request, idTramiteNivel);
            }
            if ( idTramiteNivel == null && idEspecTramiteNivel != null )
            {
            	guardarEspecTramiteNivel( mapping, request, idEspecTramiteNivel );
            }
            
            return mapping.findForward("cancel");
        }
        
        // Elimina traducciones que no son validas
        datoJustificanteForm.validaTraduccion(mapping, request);
        
        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");

            Long idEspecTramiteNivel = datoJustificanteForm.getIdEspecTramiteNivel();
            datoJustificante.setVisibleScript( datoJustificanteForm.getVisibleScript().getBytes(ConstantesXML.ENCODING) );
            datoJustificante.setValorCampoScript(datoJustificanteForm.getValorCampoScript().getBytes(ConstantesXML.ENCODING) );
            datoJustificanteDelegate.grabarDatoJustificante(datoJustificante, idEspecTramiteNivel);

            //actualizaPath(request.getSession(true), 2, datoJustificante.getId().toString());
            log.info("Creat/Actualitzat " + datoJustificante.getCodigo());

            guardarDatoJustificante(mapping, request, datoJustificante.getCodigo());
            //request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        
        // Cambio de idioma
        datoJustificanteForm.reloadLang();
        
        return mapping.findForward("reload");
    }

}
