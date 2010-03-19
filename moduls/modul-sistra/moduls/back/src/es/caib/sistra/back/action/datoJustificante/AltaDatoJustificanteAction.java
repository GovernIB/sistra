package es.caib.sistra.back.action.datoJustificante;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DatoJustificanteForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una DatoJustificante.
 *
 * @struts.action
 *  path="/back/datoJustificante/alta"
 *
 * @struts.action-forward
 *  name="success" path=".datoJustificante.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".datoJustificante.lista"
 *
 */
public class AltaDatoJustificanteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaDatoJustificanteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idEspecTramiteNivelString = request.getParameter("idEspecTramiteNivel");
        String idTramiteNivelString = request.getParameter("idTramiteNivel");
        String idTramiteVersionString = request.getParameter("idTramiteVersion");
        if (idEspecTramiteNivelString == null || idEspecTramiteNivelString.length() == 0) {
            log.info("idEspecTramiteNivel es null");
            return mapping.findForward("fail");
        }

        Long idEspecTramiteNivel = new Long(idEspecTramiteNivelString);


        DatoJustificanteForm datoJustificanteForm = (DatoJustificanteForm) obtenerActionForm(mapping,request, "/back/datoJustificante/editar");
        datoJustificanteForm.destroy(mapping, request);

        datoJustificanteForm.setIdEspecTramiteNivel(idEspecTramiteNivel);
        
        if ( idTramiteNivelString != null && !"".equals( idTramiteNivelString.trim() ) )
        {
        	datoJustificanteForm.setIdTramiteNivel( new Long( idTramiteNivelString ) );
        }
        
        return mapping.findForward("success");
    }
}
