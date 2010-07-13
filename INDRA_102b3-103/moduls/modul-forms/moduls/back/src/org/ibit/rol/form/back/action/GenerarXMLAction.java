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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Validacion;
import org.ibit.rol.form.model.betwixt.Configurator;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;

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
}
