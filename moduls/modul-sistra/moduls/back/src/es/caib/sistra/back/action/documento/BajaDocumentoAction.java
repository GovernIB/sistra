package es.caib.sistra.back.action.documento;

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
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;

/**
 * Action para preparar borrar una Documento.
 *
 * @struts.action
 *  name="documentoForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/documento/baja"
 *
 * @struts.action-forward
 *  name="success" path=".exitoBaja"
 *  
 * @struts.action-forward
 * name="fail" path="/error.jsp"
 */
public class BajaDocumentoAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaDocumentoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaDocumento");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        DocumentoDelegate documentoDelegate = DelegateUtil.getDocumentoDelegate();
        Long id = new Long(idString);
        Documento documento = documentoDelegate.obtenerDocumento( id );
        TramiteVersion tramiteVersion = documento.getTramiteVersion();
        
        this.setReloadTree( request, Nodo.IR_A_DOCUMENTOS, tramiteVersion.getCodigo() );
        
        documentoDelegate.borrarDocumento(id);
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
