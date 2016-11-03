package es.caib.zonaper.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ProcesosAutoDelegate;

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
		ProcesosAutoDelegate delegate = DelegateUtil.getProcesosAutoDelegate();
		log.debug( "Job borrado tramites backup");
		try{
			delegate.procesaEliminarTramitesBackup();
		}catch( Exception exc ){
			throw new JobExecutionException( exc );
		}		
	}
}	


