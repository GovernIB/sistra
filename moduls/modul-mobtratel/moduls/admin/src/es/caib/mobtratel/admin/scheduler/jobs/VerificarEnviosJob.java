package es.caib.mobtratel.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;

import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.ProcEnviosDelegate;


/**
 * Job que realiza el proceso de verificar los envios marcados para verificar
 *
 */
public class VerificarEnviosJob extends JobAutomatico
{
	private Log log = LogFactory.getLog( VerificarEnviosJob.class);
	
	public void doProceso() throws JobExecutionException {
		try
		{
			log.debug("Job verificar envios");
			ProcEnviosDelegate delegate = DelegateUtil.getProcEnviosDelegate();
			delegate.verificar();
		}
		catch( DelegateException exc )
		{
			throw new JobExecutionException( exc );
		}
	}


}	

