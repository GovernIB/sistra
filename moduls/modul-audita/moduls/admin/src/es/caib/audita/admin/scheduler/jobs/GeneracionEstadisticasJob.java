package es.caib.audita.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateException;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * Job que realiza la generacion de estadisticas
 * @author clmora
 *
 */
public class GeneracionEstadisticasJob implements Job
{
	private Log log = LogFactory.getLog( GeneracionEstadisticasJob.class  );
	
	/**
	 * Job que ejecuta la eliminación y backup de los tramites eliminados
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		log.debug("Job generacion estadisticas");
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		try {
			delegate.generaCuadroMandoInicio();
		} catch (DelegateException e) {
			log.error("Excepcion lanzando Job: " + e.getMessage(), e);
		}
		
		
	}
}	

