package es.caib.sistra.admin.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/*
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
*/
import es.caib.sistra.admin.Constants;

/**
 * @struts.action
 *  path="/admin/download"
 *  scope="request"
 *  validate="false"
 */
public class FileDownloadAction extends Action {
	private static Log _log = LogFactory.getLog( FileDownloadAction.class );

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		// Recogemos nombre fichero y datos
		String nombreFichero = ( String ) request.getAttribute( Constants.NOMBREFICHERO_KEY);
		byte[] datosFichero = ( byte[] ) request.getAttribute( Constants.DATOSFICHERO_KEY );
		
		// Volcamos fichero en stream response		
		ByteArrayInputStream bis = new ByteArrayInputStream(datosFichero);
		try{			
			response.reset();		
			response.setContentType("application/octet-stream");
			
			// Normalizamos fichero quitando los blancos (problema firefox)
			nombreFichero = nombreFichero.replaceAll(" ","_");
			
			response.setHeader("Content-Disposition","attachment; filename="+ nombreFichero + ";");
            copy(bis,response.getOutputStream());            
        }
		catch( java.io.IOException exc )
		{
			_log.info( "Client aborted" );
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
		finally{
        	try{if ( !response.isCommitted() ) response.flushBuffer();}catch(Exception ex){ex.printStackTrace();}
            if(bis != null)
            	bis.close();
        }

        return null;
	} 
	
	  private int copy(InputStream input, OutputStream output) throws IOException{
	      byte buffer[] = new byte[4096];
	      int count = 0;
	      for(int n = 0; -1 != (n = input.read(buffer));)
	      {
	          output.write(buffer, 0, n);
	          count += n;
	      }
	
	      return count;
	  }
}