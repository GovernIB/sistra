package es.caib.sistra.back.action.xml;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.back.form.ImportarVersionTramiteForm;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.model.betwixt.Configurator;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 * name="importarVersionTramiteForm"
 * path="/importar/xml"
 * scope="request"
 * validate="true"
 * input=".tramiteVersion.importar"
 *
 * @struts.action-forward
 *  name="success" path=".tramiteVersion.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".tramiteVersion.importar"
 */
public class ImportarXMLAction extends BaseAction 
{
    protected static final Log log = LogFactory.getLog(ImportarXMLAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();

        ImportarVersionTramiteForm iForm = (ImportarVersionTramiteForm) form;
        if (iForm.getFitxer() != null) {

            BeanReader beanReader = new BeanReader();
            Configurator.configure(beanReader);

            TramiteVersion tramiteVersion = (TramiteVersion) beanReader.parse(iForm.getFitxer().getInputStream());
            tramiteVersion.setVersion( iForm.getVersion() );
            
            log.info("Gravant formulari: " + tramiteVersion.getVersion() );
            Long idTramiteVersion 	= delegate.grabarTramiteVersion( tramiteVersion, iForm.getCodigoTramite() );

            guardarTramiteVersion(mapping, request, idTramiteVersion );
            
            this.setReloadTree( request, Nodo.IR_A_DEFINICION_TRAMITE_VERSION, idTramiteVersion );

            return mapping.findForward("success");
        }

        return mapping.findForward("fail");
    }
}
