package es.caib.sistra.back.action.documentoNivel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.DocumentoNivel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una documentoNivel.
 *
 * @struts.action
 *  path="/back/documentoNivel/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".documentoNivel.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".documentoNivel.lista"
 *
 */
public class SeleccionDocumentoNivelAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionDocumentoNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionDocumentoNivel");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la documentoNivel " + idString);

        Long id = new Long(idString);
        DocumentoNivel dn = guardarDocumentoNivel(mapping, request, id);
        // Establecemos bloqueo
        this.setBloqueado(request,dn.getDocumento().getTramiteVersion().getBloqueadoModificacion(),dn.getDocumento().getTramiteVersion().getBloqueadoModificacionPor());        


        return mapping.findForward("success");
    }



}
