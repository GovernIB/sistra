package es.caib.mobtratel.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;

import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.ProcEnviosDelegate;


/**
 * Job que realiza el proceso de envios programados (periodicidad 1 hora)
 *
 */
public class EnviosJob extends JobAutomatico
{
	private Log log = LogFactory.getLog( EnviosJob.class  );
	
	public void doProceso() throws JobExecutionException {
		try
		{
			log.debug("Job envios programados");
			ProcEnviosDelegate delegate = DelegateUtil.getProcEnviosDelegate();
			delegate.enviar();
		}
		catch( DelegateException exc )
		{
			throw new JobExecutionException( exc );
		}
	}


}	

