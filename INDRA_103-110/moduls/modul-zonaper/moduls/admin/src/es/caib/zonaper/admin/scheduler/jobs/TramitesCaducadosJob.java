package es.caib.zonaper.admin.scheduler.jobs;

import java.util.Date;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.zonaper.admin.scheduler.conf.SchedulerConfiguration;
import es.caib.zonaper.persistence.delegate.BackupDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.util.UsernamePasswordCallbackHandler;

/**
 * Job que realiza el borrado de los tramites y de los prerregistros caducados, guardandolos en la tabla de backup 
 * @author clmora
 *
 */
public class TramitesCaducadosJob implements Job
{
	private Log log = LogFactory.getLog( TramitesCaducadosJob.class  );
	
	/**
	 * Job que ejecuta la eliminación y backup de los tramites eliminados
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		BackupDelegate delegate = DelegateUtil.getBackupDelegate();
		SchedulerConfiguration configuration = SchedulerConfiguration.getInstance();
		boolean scheduleBorradoPreregistro = Boolean.valueOf( configuration.get( "scheduler.schedule.borradoPreregistro" ) ).booleanValue();
		log.debug( "Job borrado tramites caducados [borrado prerregistros = " + scheduleBorradoPreregistro + "]");
		
		LoginContext lc = null;
		try{					
			// Realizamos login JAAS con usuario para proceso automatico					
			String user = (String) configuration.get("auto.user");
			String pass = (String) configuration.get("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();						
			
			Date fechaEjecucion = new Date();
			delegate.procesaTramitesCaducados( fechaEjecucion, scheduleBorradoPreregistro );
		}
		catch( Exception exc )
		{
			throw new JobExecutionException( exc );
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}
}	


