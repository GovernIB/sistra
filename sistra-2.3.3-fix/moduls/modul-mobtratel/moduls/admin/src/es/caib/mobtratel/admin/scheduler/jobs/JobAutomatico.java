package es.caib.mobtratel.admin.scheduler.jobs;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.mobtratel.admin.scheduler.conf.SchedulerConfiguration;
import es.caib.mobtratel.admin.util.UsernamePasswordCallbackHandler;

/**
 * Job que realiza el proceso diario de importacion
 *
 */
public abstract class JobAutomatico implements Job
{
	
	private Log log = LogFactory.getLog( JobAutomatico.class  );
	
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		LoginContext lc = null;
				
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			String user = SchedulerConfiguration.getInstance().get("auto.user");
			String pass = SchedulerConfiguration.getInstance().get("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();						
						
			// Realizamos proceso
			doProceso();
									
		}catch (Exception ex){
			// Informamos al log			
			log.error("Error al realizar proceso automatico: " + ex.getMessage(),ex);
			throw new JobExecutionException( ex );
		}finally{
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	
	}

	public abstract void doProceso() throws JobExecutionException;
	
	
}	

