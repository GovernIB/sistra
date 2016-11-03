package es.caib.bantel.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.bantel.persistence.delegate.BteProcesosDelegate;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;

/**
 * Job que realiza el proceso de avisos de nuevas entradas a los BackOffices
 *
 */
public class AvisoBackOfficesJob implements Job
{
	private Log log = LogFactory.getLog( AvisoBackOfficesJob.class  );
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			log.debug( "Job Aviso Backoffices");
			BteProcesosDelegate delegate = DelegateUtil.getBteProcesosDelegate();			
			delegate.avisoBackOffices();
		}
		catch( DelegateException exc )
		{
			throw new JobExecutionException( exc );
		}
	}
}	

