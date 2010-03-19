package es.caib.mobtratel.admin.scheduler.servlet;

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

import es.caib.mobtratel.admin.scheduler.conf.SchedulerConfiguration;
import es.caib.mobtratel.admin.scheduler.listener.MobtratelJobListener;


/**
 * @web.servlet name="mobtratelSchedulerServlet" load-on-startup="2"
 * @web.servlet-mapping url-pattern="/mobtratelSchedulerServlet"
 */
public class MobtratelSchedulerServlet implements Servlet
{
	Log _log = LogFactory.getLog( MobtratelSchedulerServlet.class );
		
	private static String GROUP = "Mobtratel";
	private static int SCHEDULER_DELAY_MINUTES = 5;
	
	private Scheduler sched = null;
	
	public void init(ServletConfig config) throws ServletException
	{
		try 
		{
			boolean jobSchedule;
			String jobName,jobCron,jobClassName;
			int jobNumber;
					
			// Prepraramos scheduler
			SchedulerConfiguration configuration = SchedulerConfiguration.getInstance();
			StdSchedulerFactory schedFact = this.getSchedulerFactory( config );
			sched = schedFact.getScheduler();
			sched.addGlobalJobListener( new MobtratelJobListener() );
			
			// Programación Jobs
			jobNumber = Integer.parseInt(configuration.get( "scheduler.jobs.number" ));
			for (int i=1;i<=jobNumber;i++){
				jobName = configuration.get( "scheduler.job." + i + ".name" );
				jobClassName = configuration.get( "scheduler.job." + i + ".classname" );
				jobSchedule = Boolean.valueOf( configuration.get( "scheduler.job." + i + ".schedule" ) ).booleanValue();
				jobCron = configuration.get( "scheduler.job." + i + ".cron.expression" );
				
				_log.debug(jobName + ": Schedule? " + jobSchedule );
				_log.debug(jobName + ": Cron? " + jobCron );
				 
				 if ( jobSchedule )
				 {				 
					CronTrigger trigger = new CronTrigger(jobName, GROUP);
					JobDetail jobDetail = new JobDetail( jobName, GROUP, Class.forName(jobClassName));
					trigger.setCronExpression( jobCron );
					// Retrasamos 2 minutos
					java.util.Date startTime = new java.util.Date();					
					startTime.setTime( startTime.getTime() + ( SCHEDULER_DELAY_MINUTES * 60 * 1000 ) );
					_log.debug( "Job " + jobName + ": Fecha de inicio[ " + startTime + "]");
					trigger.setStartTime( startTime );
					sched.scheduleJob( jobDetail, trigger );				
				 }
			}
		
		 }
		 catch (Exception e) 
		 {
		  	_log.error( "Exception scheduling : ", e );
		 }
				 
	}

	public ServletConfig getServletConfig()
	{
		return this.getServletConfig();
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException
	{

	}

	public String getServletInfo()
	{
		return "MobtratelSchedulerServlet";
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
