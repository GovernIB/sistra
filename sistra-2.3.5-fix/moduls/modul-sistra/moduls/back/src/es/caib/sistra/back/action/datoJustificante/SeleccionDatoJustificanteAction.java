package es.caib.sistra.back.action.datoJustificante;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.EspecTramiteNivelDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una datoJustificante.
 *
 * @struts.action
 *  path="/back/datoJustificante/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".datoJustificante.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".datoJustificante.lista"
 *
 */
public class SeleccionDatoJustificanteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionDatoJustificanteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionDatoJustificante");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la datoJustificante " + idString);

        Long id = new Long(idString);
        DatoJustificante dj = guardarDatoJustificante(mapping, request, id);
                
        // Establecemos bloqueo
        EspecTramiteNivelDelegate espd = DelegateUtil.getEspecTramiteNivelDelegate();
        TramiteVersion tv = espd.obtenerTramiteVersion(dj.getEspecTramiteNivel().getCodigo());
        this.setBloqueado(request,tv.getBloqueadoModificacion(),tv.getBloqueadoModificacionPor()); 
        
        return mapping.findForward("success");
    }



}
