package es.caib.sistra.front.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.AnexarDocumentoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="uploadDocumentoForm"
 *  path="/protected/uploadDocumento"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class UploadDocumentoAction extends BaseAction
{
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String funcion;
		try{
			AnexarDocumentoForm formulario = ( AnexarDocumentoForm ) form;
			InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( formulario.getID_INSTANCIA(), request );
			funcion = "parent.fileUploaded('"+formulario.getID_INSTANCIA()+"')";
			FormFile file = formulario.getDatos();
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
					throw new Exception("anexarDocumentos.anexar.tamanyoNoValido");
				}
				
				// Validamos extensiones					
				if ((formulario.getExtensiones().toLowerCase() + ",").indexOf(fileExtension.toLowerCase() + ",") == -1){
					throw new Exception("anexarDocumentos.anexar.extensionNoValida");
				}
	
				request.getSession().setAttribute(formulario.getID_INSTANCIA(),file.getFileData());
				request.getSession().setAttribute(formulario.getID_INSTANCIA()+"Nombre",fileName);
				request.getSession().setAttribute(formulario.getID_INSTANCIA()+"Extension",fileExtension);
				
			}else{
	//			// PARA DOCUMENTOS PRESENCIALES NO HACE FALTA FICHERO
				request.getSession().setAttribute(formulario.getID_INSTANCIA(),null);
			}
		}catch(Exception ex){
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			if(ex.getMessage() != null && ex.getMessage().startsWith("anexarDocumentos.anexar.")){
				funcion="parent.errorFileUploaded(\"" + resources.getMessage( getLocale( request ), ex.getMessage()) + "\")";				
			}else{
				funcion="parent.errorFileUploaded(\""+resources.getMessage( getLocale( request ), "anexarDocumentos.anexar.errorUpdate")+"\")";
			}
		}
	    response.setContentType("text/html");		    
	    PrintWriter pw = response.getWriter();
	    pw.println("<html>");
	    pw.println("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
	    pw.println("<script type=\"text/javascript\">");
	    pw.println("<!--");
	    pw.println(funcion);
	    pw.println("-->");
	    pw.println("</script>");
	    pw.println("</head>");
		pw.println("<body>");
		pw.println("</body>");
		pw.println("</html>");
		return null;
		
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
