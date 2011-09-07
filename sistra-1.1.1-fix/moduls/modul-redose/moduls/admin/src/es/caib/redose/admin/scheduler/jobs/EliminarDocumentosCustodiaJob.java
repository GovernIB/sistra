package es.caib.redose.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import es.caib.redose.admin.scheduler.servlet.RedoseSchedulerServlet;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsProcesosDelegate;
import es.caib.sistra.plugins.PluginFactory;

/**
 * Job que realiza el borrado de los documentos en custodia
 *
 */
public class EliminarDocumentosCustodiaJob implements Job
{
	private Log log = LogFactory.getLog( EliminarDocumentosCustodiaJob.class  );
	
	/**
	 * Job que realiza el borrado de los documentos en custodia
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		RdsProcesosDelegate delegate = DelegateUtil.getRdsProcesosDelegate();
		log.debug( "Job borrado documentos en custodia");
		try
		{
			boolean existePluginCustodia = true;
			// Comprobamos si existe plugin de custodia y si no existe paramos el trabajo
			 try {
				PluginFactory.getInstance().getPluginCustodia();
			} catch (Exception e) {
				if(context.getScheduler() != null){
					context.getScheduler().deleteJob(RedoseSchedulerServlet.NAMEBORRADOCUSTODIA,RedoseSchedulerServlet.GROUP);
				}
				existePluginCustodia = false;
			}
			if(existePluginCustodia){
			delegate.borradoDocumentosCustodia();
		}
		}
		catch( Exception exc )
		{
			throw new JobExecutionException( exc );
		}
	}
	
}	

