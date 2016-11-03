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
 * Job que realiza el proceso de avisos de monitorizacion de errores a los Gestores
 *
 */
public class AvisoMonitorizacionJob implements Job
{
	private Log log = LogFactory.getLog( AvisoMonitorizacionJob.class  );
	
	public void execute(JobExecutionContext context) throws JobExecutionException
	{		
		try
		{
			log.debug( "Job Aviso Monitorizacion");
			BteProcesosDelegate delegate = DelegateUtil.getBteProcesosDelegate();
			delegate.avisoMonitorizacion();
		}
		catch( DelegateException exc )
		{
			throw new JobExecutionException( exc );
		}
		
	}
}	

