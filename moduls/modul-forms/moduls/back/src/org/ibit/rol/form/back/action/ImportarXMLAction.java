package org.ibit.rol.form.back.action;

import java.util.List;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.ibit.rol.form.back.form.ImportarForm;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.RolUsuarioFormulario;
import org.ibit.rol.form.model.RolUsuarioFormularioId;
import org.ibit.rol.form.model.betwixt.Configurator;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 * name="importarForm"
 * path="/importar/xml"
 * scope="request"
 * validate="true"
 * input=".formulario.importar"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.importar"
 */
public class ImportarXMLAction extends BaseAction {

    protected static final Log log = LogFactory.getLog(ImportarXMLAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();

        ImportarForm iForm = (ImportarForm) form;
        if (iForm.getFitxer() != null) {

            BeanReader beanReader = new BeanReader();
            Configurator.configure(beanReader);

            Formulario formulario = (Formulario) beanReader.parse(iForm.getFitxer().getInputStream());
            
            // No guardamos informacion de bloqueo
            formulario.setBloqueado(false);
            formulario.setBloqueadoPor(null);
            
            // Controlamos que se haya especificado version
        	if (iForm.getVersion()<=0){
        		ActionErrors errors = new ActionErrors();
                errors.add("version", new ActionError("errors.formulario.version.noEspecificada"));
                saveErrors(request, errors);
                return mapping.findForward("fail");            		
        	}
        	
        	// Controlamos que no exista formulario con esa version
        	if  (delegate.obtenerFormulario(iForm.getModel(),iForm.getVersion()) != null){
        		 ActionErrors errors = new ActionErrors();
                 errors.add("version", new ActionError("errors.formulario.duplicado.version"));
                 saveErrors(request, errors);
                 return mapping.findForward("fail");
        	}
        	
            
            /* INDRA: CONTROL ELEMENTOS CON XPATHS DUPLICADOS */
            String generarDuplicados = iForm.getGenerarDuplicados();
            if (StringUtils.isEmpty(generarDuplicados)) {        	
            	List duplicados = Util.controlXpathDuplicados(formulario);
            	if (duplicados.size() > 0){
            		request.setAttribute("duplicados",duplicados);
            		return mapping.findForward("fail");        		
            	}        
            }else{
            	if (generarDuplicados.equals("N")){
            		return mapping.findForward("fail");	
            	}
            }
            /* INDRA: CONTROL ELEMENTOS CON XPATHS DUPLICADOS */
            
            
            //-- INDRA: SI NO TIENE MODO DE FUNCIONAMIENTO SE LO ASIGNAMOS (PARA PERMITIR IMPORTAR ANTIGUOS)
            if (formulario.getModoFuncionamiento() == null){
            	formulario.setModoFuncionamiento(DelegateUtil.getVersionDelegate().obtener(new Long(0)));
            }
            //-- INDRA: FIN
            
            formulario.setModelo(iForm.getModel());
            formulario.setVersion(iForm.getVersion());            
           
            
            // Obtenemos ultima version formulario
            Formulario formLastVersion = delegate.ultimaVersionFormulario(formulario.getModelo());
            
            // Si la version es mayor actualizamos flag de ultima version
            if (formLastVersion == null || formulario.getVersion() > formLastVersion.getVersion()){
            	if (formLastVersion != null) delegate.ultimaVersionFalse(formLastVersion.getId());
            	formulario.setLastVersion(true);
            }else{
            	formulario.setLastVersion(false);
            }
            

            log.debug("Gravant formulari: " + formulario.getModelo() + " - " + formulario.getVersion());
            Long idForm = delegate.gravarFormulario(formulario);
            GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
            if(StringUtils.isNotEmpty(request.getUserPrincipal().getName())){
				RolUsuarioFormulario userForm = new RolUsuarioFormulario(new RolUsuarioFormularioId(request.getUserPrincipal().getName(),formulario.getId()));
				gruposDelegate.asociarUsuarioFormulario(userForm);
			 }
                        
            
            // INDRA: Al guardar nuevo se bloquea automaticamente para el usuario --> desbloqueamos
            delegate.desbloquearFormulario(idForm);
            // INDRA: Al guardar nuevo se bloquea automaticamente --> desbloqueamos
            
            guardarFormulario(mapping, request, idForm);
            
            return mapping.findForward("success");
        }

        return mapping.findForward("fail");
    }
}
