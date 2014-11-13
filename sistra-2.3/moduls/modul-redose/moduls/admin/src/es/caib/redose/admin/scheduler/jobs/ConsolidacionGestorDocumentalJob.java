package es.caib.redose.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.redose.admin.scheduler.servlet.RedoseSchedulerServlet;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsProcesosDelegate;
import es.caib.sistra.plugins.PluginFactory;

/**
 * 
 * Job que realiza la consolidacion en el gestor documental
 *
 */
public class ConsolidacionGestorDocumentalJob extends JobAutomatico
{
	private Log log = LogFactory.getLog( ConsolidacionGestorDocumentalJob.class  );
	
	/**
	 * Job que realiza consolidacion en el gestor documental
	 */
	public void doProceso(JobExecutionContext context) throws JobExecutionException {
		RdsProcesosDelegate delegate = DelegateUtil.getRdsProcesosDelegate();
		log.debug( "Job consolidacion gestor documental");
		try
		{
			boolean existePluginGestionDocumental = true;
			// Comprobamos si existe plugin de gestion documental y si no existe paramos el trabajo
			try {
				PluginFactory.getInstance().getPluginGestionDocumental();
			} catch (Exception e) {
				if(context.getScheduler() != null){
					log.debug( "No existe plugin gestion documental: desactivamos job consolidacion gestor documental");
					context.getScheduler().deleteJob(RedoseSchedulerServlet.NAMEBORRADOGESTIONDOCUMENTAL,RedoseSchedulerServlet.GROUP);
				}
				existePluginGestionDocumental = false;
			}
			if(existePluginGestionDocumental){
				delegate.consolidacionGestorDocumental();
			}
		}
		catch( Exception exc )
		{
			throw new JobExecutionException( exc );
		}
	}
	
}	

