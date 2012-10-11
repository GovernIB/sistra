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
import es.caib.sistra.back.form.DocumentoNivelForm;
import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoNivelDelegate;
import es.caib.xml.ConstantesXML;

/**
 * Action para editar una DocumentoNivel.
 *
 * @struts.action
 *  name="documentoNivelForm"
 *  scope="session"
 *  validate="true"
 *  input=".documentoNivel.editar"
 *  path="/back/documentoNivel/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/documentoNivel/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".documentoNivel.editar"
 *
 * @struts.action-forward
 *  name="success" path=".documentoNivel.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".documento.editar"
 *
 */
public class EditarDocumentoNivelAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarDocumentoNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarDocumentoNivel");
        DocumentoNivelDelegate documentoNivelDelegate = DelegateUtil.getDocumentoNivelDelegate();
        DocumentoNivelForm documentoNivelForm = (DocumentoNivelForm) form;
        DocumentoNivel documentoNivel = (DocumentoNivel) documentoNivelForm.getValues();

        if (isCancelled(request)) 
        {
            log.info("isCancelled");
            Long idDocumento = documentoNivelForm.getIdDocumento();
            guardarDocumento(mapping, request, idDocumento);
            return mapping.findForward("cancel");
        }
        
        // Elimina traducciones que no son validas
        documentoNivelForm.validaTraduccion(mapping, request);
        
        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");

            Long idDocumento = documentoNivelForm.getIdDocumento();
            
            documentoNivel.setFormularioDatosInicialesScript( documentoNivelForm.getFormularioDatosInicialesScript().getBytes(ConstantesXML.ENCODING)  );
            documentoNivel.setFormularioDatosInicialesScript(  documentoNivelForm.getFormularioDatosInicialesScript().getBytes(ConstantesXML.ENCODING)  );
            documentoNivel.setObligatorioScript(  documentoNivelForm.getObligatorioScript().getBytes(ConstantesXML.ENCODING)  );
            documentoNivel.setFormularioValidacionPostFormScript(  documentoNivelForm.getFormularioValidacionPostFormScript().getBytes(ConstantesXML.ENCODING)  );
            documentoNivel.setFormularioModificacionPostFormScript( documentoNivelForm.getFormularioModificacionPostFormScript().getBytes(ConstantesXML.ENCODING)  );
            documentoNivel.setPagoCalcularPagoScript(  documentoNivelForm.getPagoCalcularPagoScript().getBytes(ConstantesXML.ENCODING)  );
            documentoNivel.setFormularioConfiguracionScript( documentoNivelForm.getFormularioConfiguracionScript().getBytes(ConstantesXML.ENCODING) );
            documentoNivel.setFormularioPlantillaScript(documentoNivelForm.getFormularioPlantillaScript().getBytes(ConstantesXML.ENCODING) );
            documentoNivel.setFlujoTramitacionScript(documentoNivelForm.getFlujoTramitacionScript().getBytes(ConstantesXML.ENCODING) );
            
            String [] nivelesAutenticacion = documentoNivelForm.getNivelesAutenticacionSelected();
            if ( nivelesAutenticacion != null )
            {
            	documentoNivel.setNivelAutenticacion( Util.concatArrString( nivelesAutenticacion ) );
            }
            
            boolean recargarArbol = false;
            if ( isAlta( request ) )
            {
            	recargarArbol =  true;
            }
            else
            {
            	DocumentoNivel documentoNivelAntesGrabar = documentoNivelDelegate.obtenerDocumentoNivel( documentoNivel.getCodigo() );
            	recargarArbol = !documentoNivelAntesGrabar.getNivelAutenticacion().equals( documentoNivel.getNivelAutenticacion() );
            }
            
            
            documentoNivelDelegate.grabarDocumentoNivel(documentoNivel, idDocumento);

            //actualizaPath(request.getSession(true), 2, documentoNivel.getId().toString());
            log.info("Creat/Actualitzat " + documentoNivel.getCodigo());

            guardarDocumentoNivel(mapping, request, documentoNivel.getCodigo());
            //request.setAttribute("reloadMenu", "true");
            if ( recargarArbol )
            {
            	this.setReloadTree( request, Nodo.IR_A_DOCUMENTONIVEL, documentoNivel.getCodigo() );
            }
            return mapping.findForward("success");

        }
        
        // Cambio de idioma
        documentoNivelForm.reloadLang();
        
        return mapping.findForward("reload");
    }

}
