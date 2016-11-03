package es.caib.zonaper.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ProcesosAutoDelegate;

/**
 * Job que realiza el control de entrega de las notificaciones.
 *
 */
public class ControlEntregaNotificacionesJob implements Job
{
	private Log log = LogFactory.getLog( ControlEntregaNotificacionesJob.class  );
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ProcesosAutoDelegate delegate = DelegateUtil.getProcesosAutoDelegate();
		log.debug( "Job control entrega notificaciones ");
		try{
			delegate.controlEntregaNotificaciones();
		}catch( Exception exc ){
			throw new JobExecutionException( exc );
		}
	}
	
}	

