package es.caib.sistra.back.action.documento;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.Documento;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una documento.
 *
 * @struts.action
 *  path="/back/documento/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".documento.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".documento.lista"
 *
 */
public class SeleccionDocumentoAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionDocumentoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionDocumento");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la documento " + idString);

        Long id = new Long(idString);
        Documento doc = guardarDocumento(mapping, request, id);
        // Establecemos bloqueo
        this.setBloqueado(request,doc.getTramiteVersion().getBloqueadoModificacion(),doc.getTramiteVersion().getBloqueadoModificacionPor());        

        return mapping.findForward("success");
    }



}
