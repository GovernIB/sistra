package es.caib.sistra.front.action;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jboss.security.SecurityContext;
import org.jboss.security.SecurityContextAssociation;

import es.caib.sistra.front.form.RegistrarTramiteForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.util.ConvertUtil;

/**
 * @struts.action
 *  name="registrarTramiteForm"
 *  path="/protected/registrarTramite"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class RegistrarTramiteAction extends BaseAction
{
	private static Log log = LogFactory.getLog(RegistrarTramiteAction.class);
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
			
		RegistrarTramiteForm formulario = ( RegistrarTramiteForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		
	  	String asiento = ConvertUtil.base64UrlSafeToCadena( formulario.getAsiento() );
		
		FirmaIntf firma = null;
		if ( !StringUtils.isEmpty( formulario.getFirma() ) )
		{
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			firma =  plgFirma.parseFirmaFromHtmlForm( formulario.getFirma() );
		}
		
		Properties propsConfig = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
		boolean workaround = (propsConfig.getProperty("sistra.workaround.jmsError") != null ? "true".equals(propsConfig.getProperty("sistra.workaround.jmsError")): false);
		
		
		SecurityContext oldContext = SecurityContextAssociation.getSecurityContext();
	  	
		this.setRespuestaFront( request, delegate.registrarTramite(asiento, firma  ));
		// TODO : Multiidioma, distinguir si es registro, envio o consulta, etc. 
		/*
		if ( !this.isSetMessage( request ) )
		{
			this.setMessage( request, "message.registro.success", new Object[] { this.getTramiteFront( request ).getIdPersistencia() } );
		}
		*/

		if (workaround){
			SecurityContextAssociation.setSecurityContext( oldContext );
		}
		
		return mapping.findForward("success");
    }
}
