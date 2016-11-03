package es.caib.mobtratel.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;

import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.ProcEnviosDelegate;


/**
 * Job que realiza el proceso de envios inmediatos (periodicidad 1 min.)
 *
 */
public class EnviosInmediatosJob extends JobAutomatico
{
	private Log log = LogFactory.getLog( EnviosInmediatosJob.class  );
	
	public void doProceso() throws JobExecutionException {
		try
		{
			log.debug("Jobs envios inmediatos");
			ProcEnviosDelegate delegate = DelegateUtil.getProcEnviosDelegate();
			delegate.enviarInmediatos();
		}
		catch( DelegateException exc )
		{
			throw new JobExecutionException( exc );
		}
	}


}	

