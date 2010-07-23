package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.AnexarDocumentoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

/**
 * @struts.action
 *  name="anexarDocumentoForm"
 *  path="/protected/anexarDocumento"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class AnexarDocumentoAction extends BaseAction
{
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		AnexarDocumentoForm formulario = ( AnexarDocumentoForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( formulario.getID_INSTANCIA(), request );
		
		ActionForward success = mapping.findForward("success"); 
		try{
			byte[] file = (byte[])request.getSession().getAttribute(formulario.getID_INSTANCIA());
			String fileName = (String)request.getSession().getAttribute(formulario.getID_INSTANCIA()+"Nombre");
			String fileExtension = (String)request.getSession().getAttribute(formulario.getID_INSTANCIA()+"Extension");
		// TODO : file extension instead content type
		if ( file != null )
		{
			
			FirmaIntf firma = null;
			if ( StringUtils.isNotEmpty(formulario.getFirma()) )
			{
				PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
				firma = plgFirma.parseFirmaFromHtmlForm(formulario.getFirma());				
			}
					
			// Anexamos documento
				this.setRespuestaFront( request, delegate.anexarDocumento( formulario.getIdentificador(), formulario.getInstancia(), file, fileName, fileExtension, formulario.getDescPersonalizada(), firma ) );
				request.removeAttribute(formulario.getID_INSTANCIA());
				request.removeAttribute(formulario.getID_INSTANCIA()+"Nombre");
				request.removeAttribute(formulario.getID_INSTANCIA()+"Extension");
			return success;
			
		}else{
			// PARA DOCUMENTOS PRESENCIALES NO HACE FALTA FICHERO
			this.setRespuestaFront( request, delegate.anexarDocumento( formulario.getIdentificador(), formulario.getInstancia(), null, null,null,formulario.getDescPersonalizada(),null) );
				request.removeAttribute(formulario.getID_INSTANCIA());
				request.removeAttribute(formulario.getID_INSTANCIA()+"Nombre");
				request.removeAttribute(formulario.getID_INSTANCIA()+"Extension");
			return success;
		}	
		}catch(Exception e){
			return mapping.findForward( "fail" );
		}
		
		/*
		 * PARA FOTOCOPIAS NO HACE FALTA FICHERO
		else
		{
//		 	TODO : ¿Qué pasa con el multiidioma?
			this.setErrorMessage( request, "Anexe un fichero" );
		}
		*/
		
		/*
		this.setRespuestaFront( request, delegate.pasoActual() );
		return success;
		*/ 
    }

}
