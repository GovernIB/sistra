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
		
		FormFile file = formulario.getDatos();
		// TODO : file extension instead content type
		if ( file != null )
		{
			// Obtenemos extensión fichero
			String fileName = file.getFileName();
			String fileExtension = "";
			int firstIndex = fileName.lastIndexOf( Constants.POINT_EXTENSION );
			if ( firstIndex != -1 )
			{
				fileExtension = fileName.substring( firstIndex + 1 );
			}
			
			// Validamos tamaño máximo			
			if (formulario.getTamanyoMaximo() < (file.getFileSize() / 1024) ) {
				this.setErrorRecoverableMessage(request,"anexarDocumentos.anexar.tamanyoNoValido");
				return success;
			}
			
			// Validamos extensiones					
			if ((formulario.getExtensiones().toLowerCase() + ",").indexOf(fileExtension.toLowerCase() + ",") == -1){
				this.setErrorRecoverableMessage(request,"anexarDocumentos.anexar.extensionNoValida");
				return success;
			}
			
			FirmaIntf firma = null;
			if ( StringUtils.isNotEmpty(formulario.getFirma()) )
			{
				PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
				firma = plgFirma.parseFirmaFromHtmlForm(formulario.getFirma());				
			}
					
			// Anexamos documento
			this.setRespuestaFront( request, delegate.anexarDocumento( formulario.getIdentificador(), formulario.getInstancia(), file.getFileData(), fileName, fileExtension, formulario.getDescPersonalizada(), firma ) );
			return success;
			
		}else{
			// PARA DOCUMENTOS PRESENCIALES NO HACE FALTA FICHERO
			this.setRespuestaFront( request, delegate.anexarDocumento( formulario.getIdentificador(), formulario.getInstancia(), null, null,null,formulario.getDescPersonalizada(),null) );
			return success;
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
