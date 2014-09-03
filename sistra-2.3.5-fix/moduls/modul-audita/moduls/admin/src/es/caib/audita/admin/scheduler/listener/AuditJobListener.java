package es.caib.audita.admin.scheduler.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class AuditJobListener implements JobListener
{
	Log log = LogFactory.getLog( AuditJobListener.class );
	
	public String getName()
	{
		return "Job Listener";
	}

	public void jobToBeExecuted(JobExecutionContext arg0)
	{
		log.debug( "Job to be executed: " + arg0.getJobDetail().getName() );

	}

	public void jobExecutionVetoed(JobExecutionContext arg0)
	{
		log.debug( "Job execution vetoed: " + arg0.getJobDetail().getName());

	}

	public void jobWasExecuted(JobExecutionContext arg0,
			JobExecutionException exception)
	{
		
		log.debug ( "Job " + arg0.getJobDetail().getName() + " executed at time " + new java.util.Date() );
	
		if (exception != null) {
			log.error("Job error: " + arg0.getJobDetail().getName(),exception );					
		}

	}

}
