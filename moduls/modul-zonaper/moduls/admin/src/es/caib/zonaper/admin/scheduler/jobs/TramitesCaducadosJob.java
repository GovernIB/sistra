package es.caib.zonaper.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ProcesosAutoDelegate;

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
		ProcesosAutoDelegate delegate = DelegateUtil.getProcesosAutoDelegate();
		log.debug( "Job procesa tramites caducados ");
		try{
			delegate.procesaTramitesCaducados();
		}catch( Exception exc ){
			throw new JobExecutionException( exc );
		}						
	}
}	


