package es.caib.redose.admin.action;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quartz.JobExecutionException;

import es.caib.redose.admin.scheduler.conf.SchedulerConfiguration;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsProcesosDelegate;
import es.caib.util.UsernamePasswordCallbackHandler;

/**
 * @struts.action
 *  path="/mantenimientoRDS"
 *  scope="request"
 *  validate="false"
 */
public class MantenimientoRDSAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		
		LoginContext lc = null;
		
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			String user = SchedulerConfiguration.getInstance().get("auto.user");
			String pass = SchedulerConfiguration.getInstance().get("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();						
						
			RdsProcesosDelegate delegate = DelegateUtil.getRdsProcesosDelegate();
			delegate.purgadoDocumentos();
			
			response.getOutputStream().write("Proceso finalizado".getBytes());
			return null;
									
		}catch (Exception ex){			
			throw new Exception( ex );
		}finally{
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
		
		
	}    
	

}
