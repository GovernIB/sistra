package es.caib.sistra.back.action.especificacionesGenericas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.EspecTramiteNivelDelegate;

/**
 * Action para consultar una especificacionesGenericas.
 *
 * @struts.action
 *  path="/back/especificacionesGenericas/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".especificacionesGenericas.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".especificacionesGenericas.lista"
 *
 */
public class SeleccionEspecificacionesGenericasAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionEspecificacionesGenericasAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionTramiteNivel");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        String bloqueado = request.getParameter("bloqueado");
        if (bloqueado != null){
        	this.setBloqueado(request,"S",bloqueado);
        }
        
        log.info("Seleccionar la especificacionesGenericas " + idString);

        Long id = new Long(idString);
        EspecTramiteNivel esp = guardarEspecTramiteNivel(mapping, request, id);
        
        // Establecemos bloqueo
        EspecTramiteNivelDelegate espd = DelegateUtil.getEspecTramiteNivelDelegate();
        TramiteVersion tv = espd.obtenerTramiteVersion(id);
        this.setBloqueado(request,tv.getBloqueadoModificacion(),tv.getBloqueadoModificacionPor());       
        
        return mapping.findForward("success");
    }

}
