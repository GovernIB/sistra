package org.ibit.rol.form.back.action;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.ibit.rol.form.back.form.ImportarForm;
import org.ibit.rol.form.back.taglib.Constants;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Mascara;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Patron;
import org.ibit.rol.form.model.RolUsuarioFormulario;
import org.ibit.rol.form.model.RolUsuarioFormularioId;
import org.ibit.rol.form.model.Validacion;
import org.ibit.rol.form.model.betwixt.Configurator;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;
import org.ibit.rol.form.persistence.delegate.MascaraDelegate;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;

import es.caib.util.ConvertUtil;

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
        try{
        ImportarForm iForm = (ImportarForm) form;
        if (iForm.getFitxer() != null) {

            BeanReader beanReader = new BeanReader();
            Configurator.configure(beanReader);

            Formulario formulario = (Formulario) beanReader.parse(iForm.getFitxer().getInputStream());
            
	            //BUGS IMPORTACION: para cada componente de cada pagina del formulario si tiene un patrón asignado
	            //	* Patrones: se debe relacionar el codigo del patron con el id especifico del entorno
            	//  * Mascaras: se debe relacionar el codigo del patron con el id especifico del entorno
            	//  * Scripts: se pasan los scripts a b64 para solventar los saltos de linea
	            modificarIdPatronesMascarasYScripts(formulario);
	            
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
        }catch(Exception e){
        	ActionErrors errors = new ActionErrors();
        	if("errors.importacion.patrones".equals(e.getMessage())){
        		errors.add("version", new ActionError("errors.importacion.patrones"));
        	}else{
        		errors.add("version", new ActionError("errors.importacion.general"));
        	}
            saveErrors(request, errors);
        }
        return mapping.findForward("fail");
    }
    
    private void modificarIdPatronesMascarasYScripts(Formulario form) throws Exception{
    	try{
    		PatronDelegate patDeleg = DelegateUtil.getPatronDelegate();
    		MascaraDelegate masDeleg = DelegateUtil.getMascaraDelegate();
	    	if(form != null && form.getPantallas() != null && form.getPantallas().size() > 0){
	    		for(int i=0;i<form.getPantallas().size();i++){
	    			Pantalla pant = (Pantalla)form.getPantallas().get(i);
	    			
	    			// Modificacion scripts pantalla
	    			if(pant.getExpresion() != null && !"".equals(pant.getExpresion())){
	    				if(pant.getExpresion().startsWith(Constants.TAG_B64)){
	    					pant.setExpresion(ConvertUtil.base64UrlSafeToCadena(pant.getExpresion().substring(Constants.TAG_B64.length(),pant.getExpresion().length())));
	    				}
					}
	    			
	    			if(pant != null && pant.getComponentes() != null && pant.getComponentes().size() > 0){
	    				for(int j=0;j<pant.getComponentes().size();j++){
	    					Componente comp = (Componente)pant.getComponentes().get(j);
	    					if(comp != null && comp instanceof Campo){
	    						Campo camp = (Campo)comp;
	    						
	    						// Modificacion patron campo
	    						if(camp.getPatron() != null){
	    							Patron pat = patDeleg.obtenerPatron(camp.getPatron().getNombre());
	    							if (pat == null) {
	    								throw new Exception("No existe patron: " + camp.getPatron().getNombre());
	    							}
	    							camp.setPatron(pat);
	    						}
	    						
	    						// Modificacion mascaras validaciones campo
	    						if (camp.getValidaciones() != null && camp.getValidaciones().size() > 0) {
	    							for (Iterator it = camp.getValidaciones().iterator(); it.hasNext();){
	    								Validacion validacion = (Validacion) it.next();
	    								if (validacion.getMascara() != null) {
	    									Mascara masc = masDeleg.obtenerMascara(validacion.getMascara().getNombre());
	    									if (masc == null) {
	    	    								throw new Exception("No existe mascara: " + validacion.getMascara().getNombre());
	    	    							}
	    									validacion.setMascara(masc);
	    								}
	    							}
	    						}
	    							    						
	    						// Modificacion scripts asociados a campo
	    						if(camp.getExpresionAutocalculo() != null && !"".equals(camp.getExpresionAutocalculo()) && camp.getExpresionAutocalculo().startsWith(Constants.TAG_B64)){
    								camp.setExpresionAutocalculo(ConvertUtil.base64UrlSafeToCadena(camp.getExpresionAutocalculo().substring(Constants.TAG_B64.length(),camp.getExpresionAutocalculo().length())));
	    						}
	    						if(camp.getExpresionAutorellenable() != null && !"".equals(camp.getExpresionAutorellenable()) && camp.getExpresionAutorellenable().startsWith(Constants.TAG_B64)){
    								camp.setExpresionAutorellenable(ConvertUtil.base64UrlSafeToCadena(camp.getExpresionAutorellenable().substring(Constants.TAG_B64.length(),camp.getExpresionAutorellenable().length())));
	    						}
	    						if(camp.getExpresionDependencia() != null && !"".equals(camp.getExpresionDependencia()) && camp.getExpresionDependencia().startsWith(Constants.TAG_B64)){
    								camp.setExpresionDependencia(ConvertUtil.base64UrlSafeToCadena(camp.getExpresionDependencia().substring(Constants.TAG_B64.length(),camp.getExpresionDependencia().length())));
	    						}
	    						if(camp.getExpresionPostProceso() != null && !"".equals(camp.getExpresionPostProceso()) && camp.getExpresionPostProceso().startsWith(Constants.TAG_B64)){
    								camp.setExpresionPostProceso(ConvertUtil.base64UrlSafeToCadena(camp.getExpresionPostProceso().substring(Constants.TAG_B64.length(),camp.getExpresionPostProceso().length())));
	    						}
	    						if(camp.getExpresionValidacion() != null && !"".equals(camp.getExpresionValidacion()) && camp.getExpresionValidacion().startsWith(Constants.TAG_B64)){
    								camp.setExpresionValidacion(ConvertUtil.base64UrlSafeToCadena(camp.getExpresionValidacion().substring(Constants.TAG_B64.length(),camp.getExpresionValidacion().length())));
	    						}
	    						if(camp.getExpresionValoresPosibles() != null && !"".equals(camp.getExpresionValoresPosibles()) && camp.getExpresionValoresPosibles().startsWith(Constants.TAG_B64)){
    								camp.setExpresionValoresPosibles(ConvertUtil.base64UrlSafeToCadena(camp.getExpresionValoresPosibles().substring(Constants.TAG_B64.length(),camp.getExpresionValoresPosibles().length())));
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
    	}catch(Exception e){
    		log.error("Error en el proceso de importación: ",e);
    		throw new Exception("errors.importacion.patrones");
    	}
    }
}
