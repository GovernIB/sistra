package es.caib.zonaper.admin.scheduler.jobs;

import java.util.Calendar;
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
 * Job que realiza el borrado definitivo de los tramites y de los prerregistros guardandos en la tabla de backup 
 * @author dpardell
 *
 */
public class BorrarTramitesBackupJob implements Job
{
	private Log log = LogFactory.getLog( BorrarTramitesBackupJob.class  );
	
	/**
	 * Job que ejecuta la eliminación de los tramites guardados en la tabla backup y que llevan allí n meses 
	 * (n es el valor de la variable zonaper.meses.borrado.definitivo del archivo zonaper.properties)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		BackupDelegate delegate = DelegateUtil.getBackupDelegate();
		SchedulerConfiguration configuration = SchedulerConfiguration.getInstance();
		log.debug( "Job borrado tramites backup");
		
		LoginContext lc = null;
		try{					
			// Realizamos login JAAS con usuario para proceso automatico					
			String user = (String) configuration.get("auto.user");
			String pass = (String) configuration.get("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();						
			
			Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, new Integer("-"+configuration.get("scheduler.borradoBackup.meses")).intValue());
			delegate.procesaEliminarTramitesBackup( cal.getTime() );
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


