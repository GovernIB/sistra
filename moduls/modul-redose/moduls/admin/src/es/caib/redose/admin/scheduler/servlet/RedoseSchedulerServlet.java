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
import es.caib.redose.admin.scheduler.jobs.ConsolidacionGestorDocumentalJob;
import es.caib.redose.admin.scheduler.jobs.PurgadoDocumentosJob;

/**
 * @web.servlet name="redoseSchedulerServlet" load-on-startup="2"
 * @web.servlet-mapping url-pattern="/redoseSchedulerServlet"
 */
public class RedoseSchedulerServlet implements Servlet
{
	Log _log = LogFactory.getLog( RedoseSchedulerServlet.class );
	
	private static String NAMEPURGADOCUMENTOS = "Purgado documentos";
	public static String NAMEBORRADOGESTIONDOCUMENTAL = "Consolidacion gestor documental";
	public static String GROUP = "Redose";
	private static int SCHEDULER_DELAY_MINUTES = 5;
	
	private Scheduler scheduler = null;
	
	public void init(ServletConfig config) throws ServletException
	{
		try{
			//Prepraramos scheduler
			SchedulerConfiguration configuration = SchedulerConfiguration.getInstance();
			StdSchedulerFactory schedFact = this.getSchedulerFactory( config );
			scheduler = schedFact.getScheduler();
			//scheduler.addJobListener( new RedoseJobListener() );
			
			//inicializamos el Job de purgado de documentos
			 boolean schedule = Boolean.valueOf( configuration.get( "scheduler.jobPurgadoDocumentos.schedule" ) ).booleanValue();
			 String cronExpression = configuration.get( "scheduler.jobPurgadoDocumentos.cron.expression" );
			 
			 _log.info( NAMEPURGADOCUMENTOS + " - SCHEDULE [" + schedule + "]" );
			 _log.info( NAMEPURGADOCUMENTOS + " - TIME EXPRESSION [" + cronExpression + "]" );
			 
			 if ( schedule )
			 {
				 CronTrigger triggerSinUsos = new CronTrigger(NAMEPURGADOCUMENTOS, GROUP);
				 try 
				 {
					 JobDetail jobDetailSinUsos = new JobDetail( NAMEPURGADOCUMENTOS, GROUP, PurgadoDocumentosJob.class );
					 triggerSinUsos.setCronExpression( cronExpression );
					 
					 // Retrasamos 5 minutos
					java.util.Date startTime = new java.util.Date();					
					startTime.setTime( startTime.getTime() + ( SCHEDULER_DELAY_MINUTES * 60 * 1000 ) );
					_log.debug( "Job " + NAMEPURGADOCUMENTOS + ": Fecha de inicio[ " + startTime + "]");
					triggerSinUsos.setStartTime(startTime);
					
					scheduler.scheduleJob( jobDetailSinUsos, triggerSinUsos );
				 }
				 catch (Exception e) 
				 {
				  	_log.error( "Exception scheduling : ", e );
					 //e.printStackTrace();
				 }
			 }
			 
			 //inicializamos el Job de consolidacion gestor documental	 
			 schedule = Boolean.valueOf( configuration.get( "scheduler.jobConsolidacionGestorDocumental.schedule" ) ).booleanValue();;
			 cronExpression = configuration.get( "scheduler.jobConsolidacionGestorDocumental.cron.expression" );
			 
			 _log.info(NAMEBORRADOGESTIONDOCUMENTAL + " - SCHEDULE [" + schedule + "]" );
			 _log.info(NAMEBORRADOGESTIONDOCUMENTAL + " - TIME EXPRESSION [" + cronExpression + "]" );
			
			 
			 if(schedule){
				 CronTrigger triggerGestionDocumental = new CronTrigger(NAMEBORRADOGESTIONDOCUMENTAL, GROUP);
				 try 
				 {
					 JobDetail jobDetailGestionDocumental = new JobDetail( NAMEBORRADOGESTIONDOCUMENTAL, GROUP, ConsolidacionGestorDocumentalJob.class );
					 triggerGestionDocumental.setCronExpression( cronExpression );
					 
					 // Retrasamos 5 minutos
					java.util.Date startTime2 = new java.util.Date();					
					startTime2.setTime( startTime2.getTime() + ( SCHEDULER_DELAY_MINUTES * 60 * 1000 ) );
					_log.debug( "Job " + NAMEBORRADOGESTIONDOCUMENTAL + ": Fecha de inicio[ " + startTime2 + "]");
					triggerGestionDocumental.setStartTime(startTime2);
					
					scheduler.scheduleJob( jobDetailGestionDocumental, triggerGestionDocumental );
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
		return this.getServletConfig();
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException
	{


	}

	public String getServletInfo()
	{
		return "RedoseSchedulerServlet";
	}

	public void destroy()
	{
		try{scheduler.shutdown( true );}catch(Exception ex){}
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
