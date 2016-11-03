package es.caib.sistra.back.action.xml;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.Constants;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.back.form.ImportarVersionTramiteProcessForm;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

/**
 * @struts.action
 * name="importarVersionTramiteProcessForm"
 * path="/importar/xmlProcess"
 * scope="request"
 * input=".tramiteVersion.importarPreview"
 *
 * @struts.action-forward
 *  name="success" path=".tramiteVersion.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".tramiteVersion.importar"
 */
public class ImportarXMLProcessAction extends BaseAction 
{
    protected static final Log log = LogFactory.getLog(ImportarXMLProcessAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
        ImportarVersionTramiteProcessForm iForm = (ImportarVersionTramiteProcessForm) form;
        

        // Recuperamos de sesion el tramite version importado
        TramiteVersion tramiteVersion = (TramiteVersion) request.getSession().getAttribute(Constants.XML_IMPORTACION_KEY);
        
        if (tramiteVersion != null) {
           // Cambiamos los parametros
           tramiteVersion.setRegistroOficina(iForm.getRegistroOficina());
           tramiteVersion.setRegistroAsunto(iForm.getRegistroAsunto());
           tramiteVersion.setOrganoDestino(iForm.getOrganoDestino());
           tramiteVersion.setUnidadAdministrativa(iForm.getUnidadAdministrativa());          
           // Guardamos version tramite
           log.info("Gravant formulari: " + tramiteVersion.getVersion() );
           Long idTramiteVersion 	= delegate.grabarTramiteVersion( tramiteVersion, iForm.getCodigoTramite() );
           guardarTramiteVersion(mapping, request, idTramiteVersion );         
           this.setReloadTree( request, Nodo.IR_A_DEFINICION_TRAMITE_VERSION, idTramiteVersion );
           return mapping.findForward("success");	        
        }

        return mapping.findForward("fail");
    }
}
