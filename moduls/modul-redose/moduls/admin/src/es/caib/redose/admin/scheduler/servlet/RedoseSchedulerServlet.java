package es.caib.redose.admin.scheduler.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

import es.caib.redose.admin.scheduler.conf.SchedulerConfiguration;
import es.caib.redose.admin.scheduler.jobs.DocumentosSinUsosJob;
import es.caib.redose.admin.scheduler.jobs.EliminarDocumentosCustodiaJob;
import es.caib.redose.admin.scheduler.listener.RedoseJobListener;

/**
 * @web.servlet name="redoseSchedulerServlet" load-on-startup="2"
 * @web.servlet-mapping url-pattern="/redoseSchedulerServlet"
 */
public class RedoseSchedulerServlet implements Servlet
{
	Log _log = LogFactory.getLog( RedoseSchedulerServlet.class );
	
	private static String NAMEBORRADOSINUSOS = "Borrado docs sin usos";
	private static String NAMEBORRADOCUSTODIA = "Borrado docs custodia";
	private static String GROUP = "Redose";
	private static int SCHEDULER_DELAY_MINUTES = 5;
	
	private Scheduler schedSinUsos = null;
	private Scheduler schedCustodia = null;
	
	public void init(ServletConfig config) throws ServletException
	{
		try{
			//Prepraramos scheduler
		 SchedulerConfiguration configuration = SchedulerConfiguration.getInstance();
			StdSchedulerFactory schedFact = this.getSchedulerFactory( config );

			//inicializamos el Job de borrado de documentos sin uso
			 boolean schedule = Boolean.valueOf( configuration.get( "scheduler.jobBorradoDocsSinUsos.schedule" ) ).booleanValue();
			 String cronExpression = configuration.get( "scheduler.jobBorradoDocsSinUsos.cron.expression" );
		 
		 _log.info( "SCHEDULE [" + schedule + "]" );
		 _log.info( "TIME EXPRESSION [" + cronExpression + "]" );
		 
		 if ( schedule )
		 {
				 CronTrigger triggerSinUsos = new CronTrigger(NAMEBORRADOSINUSOS, GROUP);
			 try 
			 {
					 JobDetail jobDetailSinUsos = new JobDetail( NAMEBORRADOSINUSOS, GROUP, DocumentosSinUsosJob.class );
					 triggerSinUsos.setCronExpression( cronExpression );
				 
				 // Retrasamos 2 minutos
				java.util.Date startTime = new java.util.Date();					
				startTime.setTime( startTime.getTime() + ( SCHEDULER_DELAY_MINUTES * 60 * 1000 ) );
					_log.debug( "Job " + NAMEBORRADOSINUSOS + ": Fecha de inicio[ " + startTime + "]");
				 
					schedSinUsos = schedFact.getScheduler();
					schedSinUsos.addGlobalJobListener( new RedoseJobListener() );
					schedSinUsos.scheduleJob( jobDetailSinUsos, triggerSinUsos );
			 }
			 catch (Exception e) 
			 {
			  	_log.error( "Exception scheduling : ", e );
				 //e.printStackTrace();
			 }
		 }
			
			 //inicializamos el Job de borrado de documetnos en custodia			 
			 schedule = Boolean.valueOf( configuration.get( "scheduler.jobBorradoDocsCustodia.schedule" ) ).booleanValue();;
			 cronExpression = configuration.get( "scheduler.jobBorradoDocsCustodia.cron.expression" );
			 
			 _log.info( "SCHEDULE [" + schedule + "]" );
			 _log.info( "TIME EXPRESSION [" + cronExpression + "]" );
			
			 if(schedule){
				 CronTrigger triggerCustodia = new CronTrigger(NAMEBORRADOCUSTODIA, GROUP);
				 try 
				 {
					 JobDetail jobDetailCustodia = new JobDetail( NAMEBORRADOCUSTODIA, GROUP, EliminarDocumentosCustodiaJob.class );
					 triggerCustodia.setCronExpression( cronExpression );
					 
					 // Retrasamos 2 minutos
					java.util.Date startTime2 = new java.util.Date();					
					startTime2.setTime( startTime2.getTime() + ( SCHEDULER_DELAY_MINUTES * 60 * 1000 ) );
					_log.debug( "Job " + NAMEBORRADOCUSTODIA + ": Fecha de inicio[ " + startTime2 + "]");
					 
					schedCustodia = schedFact.getScheduler();
					schedCustodia.addGlobalJobListener( new RedoseJobListener() );
					schedCustodia.scheduleJob( jobDetailCustodia, triggerCustodia );
				 }
				 catch (Exception e) 
				 {
				  	_log.error( "Exception scheduling : ", e );
				 }
			 }
		}catch (Exception e){
		  	_log.error( "Exception scheduling : ", e );
		}	 
	}

	public ServletConfig getServletConfig()
	{

		return null;
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException
	{


	}

	public String getServletInfo()
	{

		return null;
	}

	public void destroy()
	{
		try
		{
			schedSinUsos.shutdown( true );
			schedCustodia.shutdown( true );
		}
		catch ( Exception exc )
		{
			_log.error ( exc );
		}

	}
	
	private StdSchedulerFactory getSchedulerFactory( ServletConfig config )
	{
		 StdSchedulerFactory schedFact = 
			 (StdSchedulerFactory) config.
			 							getServletContext().
			 							getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
		 return schedFact;
	}
}
