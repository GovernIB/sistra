package es.caib.sistra.back.action.xml;

import java.util.List;
import java.util.Map;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.regtel.model.ConstantesRegtel;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.back.form.ImportarVersionTramiteForm;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.model.betwixt.Configurator;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;
import es.caib.sistra.persistence.util.UtilDominios;

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
        boolean error = false;
        ActionErrors messages = new ActionErrors();
        ImportarVersionTramiteForm iForm = (ImportarVersionTramiteForm) form;
        if (iForm.getFitxer() != null) {

            BeanReader beanReader = new BeanReader();
            Configurator.configure(beanReader);

            TramiteVersion tramiteVersion = (TramiteVersion) beanReader.parse(iForm.getFitxer().getInputStream());
            tramiteVersion.setVersion( iForm.getVersion() );
            
            
            RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
//            request.setAttribute( "listaunidadesadministrativas", ajustarTamListaDesplegable(  obtenerValoresDominio( ConstantesDominio.DOMINIO_SAC_UNIDADES_ADMINISTRATIVAS  ),  MAX_COMBO_DESC , "DESCRIPCION") );
         
            if(tramiteVersion.getOrganoDestino() != null){
            	if(!dlgRte.existeServicioDestino(tramiteVersion.getOrganoDestino())){
					messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.organo.destino.no.existe"));
            		error = true;
            	}
            }
            
            if(tramiteVersion.getRegistroAsunto() != null){
            	if(!dlgRte.existeTipoAsunto(tramiteVersion.getRegistroAsunto())){
            		messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.tipo.asunto.no.existe"));
            		error = true;
            	}
            }
			
			if(tramiteVersion.getRegistroOficina() != null){
				if(!dlgRte.existeOficinaRegistro(ConstantesRegtel.REGISTRO_ENTRADA, tramiteVersion.getRegistroOficina())){
					messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.oficina.registro.no.existe"));
            		error = true;
            	}
			}
			
			if(tramiteVersion.getUnidadAdministrativa() != null){
				boolean exist = false;
				List unidades = UtilDominios.obtenerValoresDominio( ConstantesDominio.DOMINIO_SAC_UNIDADES_ADMINISTRATIVAS, null );
				for(int i=0;i<unidades.size();i++){
					Map filaMap = ( Map ) unidades.get(i);
	    			if(tramiteVersion.getUnidadAdministrativa().toString().equals((String)filaMap.get("CODIGO"))){
	    				exist = true;
	    			}
				}
				if(!exist){
					messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.unidad.administrativa.no.existe"));
            		error = true;
				}
            }
			if(error){
				saveErrors(request,messages);  
				return mapping.findForward("fail");
			}else{
	            log.info("Gravant formulari: " + tramiteVersion.getVersion() );
	            Long idTramiteVersion 	= delegate.grabarTramiteVersion( tramiteVersion, iForm.getCodigoTramite() );
	
	            guardarTramiteVersion(mapping, request, idTramiteVersion );
	            
	            this.setReloadTree( request, Nodo.IR_A_DEFINICION_TRAMITE_VERSION, idTramiteVersion );
	
	            return mapping.findForward("success");
	        }
        }

        return mapping.findForward("fail");
    }
}
