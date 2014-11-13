package es.caib.redose.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsProcesosDelegate;

/**
 * Job que realiza el purgado de los documentos en el RDS:
 * 	- marcar para borrar documentos sin usos
 *  - borrar documentos marcados para borrar (tras periodo indicado)
 *  - purgar documentos custodia
 *  - purgar documentos externos
 *
 */
public class PurgadoDocumentosJob extends JobAutomatico {
	private Log log = LogFactory.getLog( PurgadoDocumentosJob.class  );
	
	/**
	 * Job que realiza el purgado de los documentos en el RDS
	 */
	public void doProceso(JobExecutionContext context) throws JobExecutionException {	
		RdsProcesosDelegate delegate = DelegateUtil.getRdsProcesosDelegate();
		log.debug( "Job purgado de los documentos en el RDS");
		try
		{
			delegate.purgadoDocumentos();
		}
		catch( Exception exc )
		{
			throw new JobExecutionException( exc );
		}
	}
}	

