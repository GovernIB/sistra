package es.caib.sistra.back.action.documentoNivel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoNivelDelegate;

/**
 * Action para preparar borrar una DocumentoNivel.
 *
 * @struts.action
 *  name="documentoNivelForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/documentoNivel/baja"
 *
 * @struts.action-forward
 *  name="success" path=".exitoBaja"
 *  
 * @struts.action-forward
 * name="fail" path="/error.jsp"

 */
public class BajaDocumentoNivelAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaDocumentoNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaDocumentoNivel");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        DocumentoNivelDelegate documentoNivelDelegate = DelegateUtil.getDocumentoNivelDelegate();
        Long id = new Long(idString);
        
        DocumentoNivel documentoNivel = documentoNivelDelegate.obtenerDocumentoNivel( id );
        Documento documento = documentoNivel.getDocumento();
        this.setReloadTree( request, Nodo.IR_A_ESPECIFICACIONES_NIVEL_DOCUMENTO, documento.getCodigo() );
        
        documentoNivelDelegate.borrarDocumentoNivel(id);
        //request.setAttribute("reloadMenu", "true");

        /*
        String idTramiteVersionString = request.getParameter("idTramiteVersion");
        if (idTramiteVersionString == null || idTramiteVersionString.length() == 0) {
            log.warn("El paràmetre idTramiteVersion és null");
            return mapping.findForward("fail");
        }

        Long idTramiteVersion = new Long( idTramiteVersionString );
        guardarTramiteVersion( mapping, request, idTramiteVersion );
        
        */

        return mapping.findForward("success");
    }

}
