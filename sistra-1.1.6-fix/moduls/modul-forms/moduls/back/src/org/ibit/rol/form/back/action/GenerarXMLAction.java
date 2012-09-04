package org.ibit.rol.form.back.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.taglib.Constants;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Patron;
import org.ibit.rol.form.model.Validacion;
import org.ibit.rol.form.model.betwixt.Configurator;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;

import es.caib.util.ConvertUtil;

/**
 * @struts.action
 * path="/generar/xml"
 * scope="request"
 * validate="false"
 * 
 * @struts.action-forward
 * name="controlDuplicados"
 * path=".formulario.controlDuplicados"
 * 
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 */
public class GenerarXMLAction extends BaseAction {

    protected static final Log log = LogFactory.getLog(GenerarXMLAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Long idForm = new Long(request.getParameter("idForm"));
        
        if( Boolean.valueOf( DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("habilitar.permisos")).booleanValue()){
    		GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
	        if( !(gruposDelegate.existeUsuarioByGruposForm(request.getUserPrincipal().getName(),idForm) 
	        	|| gruposDelegate.existeUsuarioByForm(request.getUserPrincipal().getName(),idForm) 
	        	))
	        {
	        	request.setAttribute("message","No tiene permisos para exportar este formulario");
	        	return mapping.findForward("fail");
	        }
        }
        Formulario formulario = delegate.obtenerFormularioCompleto(idForm);
        formulario.setFechaExportacion(new Date());
        try{
        	modificarScriptsB64(formulario);
        }catch(Exception ex){
        	ActionErrors errors = new ActionErrors();
        	errors.add("version", new ActionError("errors.exportacion.saltoLineasScripts"));
            saveErrors(request, errors);
        	return mapping.findForward("fail");	
        }
        // No generamos info de bloqueo
        formulario.setBloqueado(false);
        formulario.setBloqueadoPor(null);
        
        /* INDRA: CONTROL ELEMENTOS CON XPATHS DUPLICADOS */
        String generarDuplicados = request.getParameter("generarDuplicados");
        if (StringUtils.isEmpty(generarDuplicados)) {        	
        	List duplicados = Util.controlXpathDuplicados(formulario);
        	if (duplicados.size() > 0){
        		request.setAttribute("idForm",idForm);
        		request.setAttribute("duplicados",duplicados);
        		return mapping.findForward("controlDuplicados");        		
        	}        
        }else{
        	if (generarDuplicados.equals("N")){
        		return mapping.findForward("fail");	
        	}
        }
        /* INDRA: CONTROL ELEMENTOS CON XPATHS DUPLICADOS */
        
        
        
        /* INDRA: BUG EXPORTACION - NOS ASEGURAMOS QUE LAS VALIDACIONES TENGAN PROPIEDADES NO NULAS PARA ASEGURAR QUE SE EXPORTA EL VALOR */
        for (Iterator it = formulario.getPantallas().iterator();it.hasNext();){
        	Pantalla p = (Pantalla) it.next();
        	for (Iterator it2 = p.getCampos().iterator();it2.hasNext();){
            	Campo c = (Campo) it2.next();
            	for (Iterator it3 = c.getValidaciones().iterator();it3.hasNext();){
                	Validacion v = (Validacion) it3.next();
                	String [] valores = v.getValores();
                	for (int i=0;i<valores.length;i++){
                		if (valores[i] == null) valores[i]="";
                	}
                	v.setValores(valores);               	
                }
            }
        }
        /* INDRA: BUG EXPORTACION - NOS ASEGURAMOS QUE LAS VALIDACIONES TENGAN PROPIEDADES NO NULAS */

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"form-" + formulario.getModelo() + ".xml\"");
        BeanWriter beanWriter = new BeanWriter(response.getOutputStream(), "UTF-8");
        beanWriter.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        Configurator.configure(beanWriter);
        beanWriter.write(formulario);
        beanWriter.close();

        return null;
    }
    
    private void modificarScriptsB64(Formulario form) throws Exception{
    	try{
	    	if(form != null && form.getPantallas() != null && form.getPantallas().size() > 0){
	    		for(int i=0;i<form.getPantallas().size();i++){
	    			Pantalla pant = (Pantalla)form.getPantallas().get(i);
	    			if(pant != null){
	    				if(pant.getExpresion() != null && !"".equals(pant.getExpresion())){
	    					pant.setExpresion(Constants.TAG_B64+ConvertUtil.cadenaToBase64UrlSafe(pant.getExpresion()));
	    				}
	    				if(pant.getComponentes() != null && pant.getComponentes().size() > 0){
		    				for(int j=0;j<pant.getComponentes().size();j++){
		    					Componente comp = (Componente)pant.getComponentes().get(j);
		    					if(comp != null && comp instanceof Campo){
		    						Campo camp = (Campo)comp;
		    						if(camp.getExpresionAutocalculo() != null && !"".equals(camp.getExpresionAutocalculo())){
		    							camp.setExpresionAutocalculo(Constants.TAG_B64+ConvertUtil.cadenaToBase64UrlSafe(camp.getExpresionAutocalculo()));
		    						}
		    						if(camp.getExpresionAutorellenable() != null && !"".equals(camp.getExpresionAutorellenable())){
		    							camp.setExpresionAutorellenable(Constants.TAG_B64+ConvertUtil.cadenaToBase64UrlSafe(camp.getExpresionAutorellenable()));
		    						}
		    						if(camp.getExpresionDependencia() != null && !"".equals(camp.getExpresionDependencia())){
		    							camp.setExpresionDependencia(Constants.TAG_B64+ConvertUtil.cadenaToBase64UrlSafe(camp.getExpresionDependencia()));
		    						}
		    						if(camp.getExpresionPostProceso() != null && !"".equals(camp.getExpresionPostProceso())){
		    							camp.setExpresionPostProceso(Constants.TAG_B64+ConvertUtil.cadenaToBase64UrlSafe(camp.getExpresionPostProceso()));
		    						}
		    						if(camp.getExpresionValidacion() != null && !"".equals(camp.getExpresionValidacion())){
		    							camp.setExpresionValidacion(Constants.TAG_B64+ConvertUtil.cadenaToBase64UrlSafe(camp.getExpresionValidacion()));
		    						}
		    						if(camp.getExpresionValoresPosibles() != null && !"".equals(camp.getExpresionValoresPosibles())){
		    							camp.setExpresionValoresPosibles(Constants.TAG_B64+ConvertUtil.cadenaToBase64UrlSafe(camp.getExpresionValoresPosibles()));
		    						}
		    					}
		    				}
		    			}
	    			}
	    		}
	    	}
    	}catch(Exception e){
    		log.error("Error en el proceso de exportación: ",e);
    		throw new Exception("errors.exportacion.saltoLineasScripts");
    	}
    }
}
