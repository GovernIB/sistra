package es.caib.audita.admin.scheduler.servlet;

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

import es.caib.audita.admin.scheduler.conf.SchedulerConfiguration;
import es.caib.audita.admin.scheduler.jobs.GeneracionEstadisticasJob;

/**
 * @web.servlet name="auditaSchedulerServlet" load-on-startup="2"
 * @web.servlet-mapping url-pattern="/auditaSchedulerServlet"
 */
public class AuditaSchedulerServlet implements Servlet
{
	Log _log = LogFactory.getLog( AuditaSchedulerServlet.class );
	
	private static String NAME = "Generación Estadísticas";
	private static String GROUP = "Audita";
	private static int SCHEDULER_DELAY_MINUTES = 5;
	
	private Scheduler sched = null;
	
	public void init(ServletConfig config) throws ServletException
	{
		 SchedulerConfiguration configuration = SchedulerConfiguration.getInstance();
		 boolean schedule = Boolean.valueOf( configuration.get( "scheduler.schedule" ) ).booleanValue();
		 String cronExpression = configuration.get( "scheduler.cron.expression" );
		 
		 _log.info( "SCHEDULE [" + schedule + "]" );
		 _log.info( "TIME EXPRESSION [" + cronExpression + "]" );
		 
		 if ( schedule )
		 {
			 StdSchedulerFactory schedFact = this.getSchedulerFactory( config );
			 CronTrigger trigger = new CronTrigger(NAME, GROUP);
			 try 
			 {
				 JobDetail jobDetail = new JobDetail( NAME, GROUP, GeneracionEstadisticasJob.class );
				 trigger.setCronExpression( cronExpression );
				 //				 Retrasamos 2 minutos
				 java.util.Date startTime = new java.util.Date();					
				 startTime.setTime( startTime.getTime() + ( SCHEDULER_DELAY_MINUTES * 60 * 1000 ) );
				 _log.debug( "Job " + NAME + ": Fecha de inicio[ " + startTime + "]");
				 trigger.setStartTime( startTime );
				 //Scheduler sched = schedFact.getDefaultScheduler();
				 sched = schedFact.getScheduler();
				 //sched.addJobListener( new AuditJobListener() );
				 sched.scheduleJob( jobDetail, trigger );
			 }
			 catch (Exception e) 
			 {
			  	_log.error( "Exception scheduling : ", e );
				 //e.printStackTrace();
			 }
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
			sched.shutdown( true );
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
