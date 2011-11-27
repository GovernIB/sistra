package es.caib.sistra.back.action.dominio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.ImportarDominioForm;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.betwixt.Configurator;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DominioDelegate;

/**
 * @struts.action
 * name="importarDominioForm"
 * path="/back/dominio/importarXML"
 * scope="request"
 * validate="true"
 * input=".dominio.importar"
 *
 * @struts.action-forward
 *  name="success" path=".dominio.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".dominio.importar"
 */
public class ImportarDominioAction extends BaseAction
{
	 private static Log log = LogFactory.getLog( ImportarDominioAction.class );
	 
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
             HttpServletResponse response) throws Exception 
     {
		 DominioDelegate delegate = DelegateUtil.getDominioDelegate();
		 ImportarDominioForm iForm = ( ImportarDominioForm ) form;
		 if (iForm.getFitxer() != null) 
		 {
            BeanReader beanReader = new BeanReader();
            Configurator.configure(beanReader);
            Dominio dominio = ( Dominio ) beanReader.parse(iForm.getFitxer().getInputStream() );
            
            Dominio dominioExistente = delegate.obtenerDominio( dominio.getIdentificador() ); 
            if (  dominioExistente != null )
            {
            	request.setAttribute( "idDominio", dominio.getIdentificador() );
            	request.setAttribute( "errorKey", "errors.dominio.duplicado" );
            	return mapping.findForward("fail");
            }
            
            log.info( "Grabando dominio " + dominio.getIdentificador() + " para organoResponsable " + iForm.getCodigoOrganoOrigen() );
            
            delegate.grabarDominio( dominio, iForm.getCodigoOrganoOrigen() );
            
   		 	return mapping.findForward("success");            
            
		 }
		 return mapping.findForward("fail");
	 }
}
