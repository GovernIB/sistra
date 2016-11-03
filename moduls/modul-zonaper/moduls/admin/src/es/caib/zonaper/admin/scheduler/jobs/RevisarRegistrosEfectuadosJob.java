package es.caib.zonaper.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ProcesosAutoDelegate;

/**
 * Job que revisar los registros efectuados y borra los que no se han terminado de realizar.
 *
 */
public class RevisarRegistrosEfectuadosJob implements Job
{
	private Log log = LogFactory.getLog( RevisarRegistrosEfectuadosJob.class  );
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ProcesosAutoDelegate delegate = DelegateUtil.getProcesosAutoDelegate();
		log.debug( "Job revisar Registros Efectuados ");
		try{
			delegate.revisarRegistrosEfectuados();
		}catch( Exception exc ){
			throw new JobExecutionException( exc );
		}
	}
	
}	

