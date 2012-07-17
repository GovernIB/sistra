package es.caib.sistra.back.action.xml;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.sistra.back.Constants;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.ImportarVersionTramiteForm;
import es.caib.sistra.back.form.ImportarVersionTramiteProcessForm;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.model.betwixt.Configurator;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;
import es.caib.sistra.persistence.util.UtilDominios;

/**
 * @struts.action
 * name="importarVersionTramiteForm"
 * path="/importar/xmlPreview"
 * scope="request"
 * validate="true"
 * input=".tramiteVersion.importar"
 *
 * @struts.action-forward
 *  name="success" path=".tramiteVersion.importarPreview"
 *  					
 * @struts.action-forward
 *  name="fail" path=".tramiteVersion.importar"
 */
public class ImportarXMLPreviewAction extends BaseAction 
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
            
            // Almacenamos en sesion el objeto tramite version creado a partir del fichero de importacion
            request.getSession().setAttribute(Constants.XML_IMPORTACION_KEY,tramiteVersion);
            
            // Verificamos si existen los parametros de registro
            RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
            
            String organoDestino = null;
            if(tramiteVersion.getOrganoDestino() != null){
            	if(dlgRte.existeServicioDestino(tramiteVersion.getOrganoDestino())){
            		organoDestino = tramiteVersion.getOrganoDestino();
            	}
            }
            
            String asunto = null;
            if(tramiteVersion.getRegistroAsunto() != null){
            	if(dlgRte.existeTipoAsunto(tramiteVersion.getRegistroAsunto())){
            		asunto = tramiteVersion.getRegistroAsunto();
            	}
            }
			
            String registroOficina = null;
			if(tramiteVersion.getRegistroOficina() != null){
				if(dlgRte.existeOficinaRegistro(tramiteVersion.getRegistroOficina())){
					registroOficina = tramiteVersion.getRegistroOficina();
            	}
			}
			
			Long unidadAdministrativa=null;
			if(tramiteVersion.getUnidadAdministrativa() != null){
				boolean exist = false;
				List unidades = UtilDominios.obtenerValoresDominio( ConstantesDominio.DOMINIO_SAC_UNIDADES_ADMINISTRATIVAS, null );
				for(int i=0;i<unidades.size();i++){
					Map filaMap = ( Map ) unidades.get(i);
	    			if(tramiteVersion.getUnidadAdministrativa().toString().equals((String)filaMap.get("CODIGO"))){
	    				exist = true;
	    			}
				}
				if(exist){
					unidadAdministrativa = tramiteVersion.getUnidadAdministrativa();
            	}
            }
			
			// Pasamos a pantalla para cambiar parametros registro
			ImportarVersionTramiteProcessForm iFormProcess = (ImportarVersionTramiteProcessForm) obtenerActionForm(mapping,request,"/importar/xmlProcess");
			iFormProcess.setCodigoTramite(iForm.getCodigoTramite());
			iFormProcess.setUnidadAdministrativa(unidadAdministrativa);
			iFormProcess.setRegistroOficina(registroOficina);
			iFormProcess.setOrganoDestino(organoDestino);
			iFormProcess.setRegistroAsunto(asunto);
			return mapping.findForward("success");
			
        }else{
        	return mapping.findForward("fail");	
        }
        
    }        
}
