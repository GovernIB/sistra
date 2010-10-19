package es.caib.redose.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsProcesosDelegate;

/**
 * Job que realiza el borrado de los documentos marcados para borrar 
 *
 */
public class BorrarDocumentosDefinitivamenteJob implements Job
{
	private Log log = LogFactory.getLog( BorrarDocumentosDefinitivamenteJob.class  );
	
	/**
	 * Job que realiza el borrado de los documentos sin usos
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		RdsProcesosDelegate delegate = DelegateUtil.getRdsProcesosDelegate();
		log.debug( "Job borrado documentos sin usos");
		try
		{
			delegate.borradoDocumentosDefinitivamente();
		}
		catch( Exception exc )
		{
			throw new JobExecutionException( exc );
		}
	}
}	

