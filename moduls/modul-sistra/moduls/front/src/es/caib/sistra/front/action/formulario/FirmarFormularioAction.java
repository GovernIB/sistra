package es.caib.sistra.front.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.action.BaseAction;
import es.caib.sistra.front.form.formulario.FirmarFormularioForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

/**
 * @struts.action
 *  name="firmarFormularioForm"
 *  path="/protected/firmarFormulario"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class FirmarFormularioAction extends BaseAction
{
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		FirmarFormularioForm formulario = ( FirmarFormularioForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( formulario.getID_INSTANCIA(), request );
		
		FirmaIntf firma = null;
		boolean firmaDelegada = false;
		
		if ("S".equals(formulario.getFirmaDelegada())){
			firmaDelegada = true;
		}else{		
		if ( formulario.getFirma() != null )
		{
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			firma = plgFirma.parseFirmaFromHtmlForm( formulario.getFirma() );
		}
		}
		
		this.setRespuestaFront( request, delegate.firmarFormulario( formulario.getIdentificador(), formulario.getInstancia(), firma, firmaDelegada ) );
		return mapping.findForward("success");
    }

}
