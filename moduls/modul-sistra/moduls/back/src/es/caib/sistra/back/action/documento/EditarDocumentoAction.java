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
import es.caib.sistra.back.form.DocumentoForm;
import es.caib.sistra.model.Documento;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;

/**
 * Action para editar una Documento.
 *
 * @struts.action
 *  name="documentoForm"
 *  scope="session"
 *  validate="true"
 *  input=".documento.editar"
 *  path="/back/documento/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/documento/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".documento.editar"
 *
 * @struts.action-forward
 *  name="success" path=".documento.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".documento.lista"
 *
 */
public class EditarDocumentoAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarDocumentoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarDocumento");
        DocumentoDelegate documentoDelegate = DelegateUtil.getDocumentoDelegate();
        DocumentoForm documentoForm = (DocumentoForm) form;
        Documento documento = (Documento) documentoForm.getValues();

        if (isCancelled(request)) 
        {
            log.info("isCancelled");
            Long idTramiteVersion = documentoForm.getIdTramiteVersion();
            guardarTramiteVersion(mapping, request, idTramiteVersion);
            return mapping.findForward("cancel");
        }
        
        // Elimina traducciones que no son validas
        documentoForm.validaTraduccion(mapping, request);
        
        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");
            
            boolean recargarArbol = false;
            if ( isAlta( request ) )
            {
            	recargarArbol =  true;
            }
            else
            {
            	Documento documentoAntesGrabar = documentoDelegate.obtenerDocumento( documento.getCodigo() );
            	recargarArbol = !documentoAntesGrabar.getIdentificador().equals( documento.getIdentificador() ) 
            					|| documentoAntesGrabar.getTipo() != documento.getTipo();
            }

            Long idTramiteVersion = documentoForm.getIdTramiteVersion();
            documentoDelegate.grabarDocumento(documento, idTramiteVersion);

            //actualizaPath(request.getSession(true), 2, documento.getId().toString());
            log.info("Creat/Actualitzat " + documento.getCodigo());

            guardarDocumento(mapping, request, documento.getCodigo());
            //request.setAttribute("reloadMenu", "true");
            
            if ( recargarArbol )
            {
            	this.setReloadTree( request, Nodo.IR_A_DEFINICION_DOCUMENTO, documento.getCodigo() );
            }
            
            return mapping.findForward("success");

        }
        
        // Cambio de idioma
        documentoForm.reloadLang();
        
        return mapping.findForward("reload");
    }

}
