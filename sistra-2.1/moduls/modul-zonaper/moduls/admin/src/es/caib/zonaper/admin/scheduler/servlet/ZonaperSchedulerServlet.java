package es.caib.zonaper.admin.scheduler.servlet;

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

import es.caib.zonaper.admin.scheduler.conf.SchedulerConfiguration;
import es.caib.zonaper.admin.scheduler.jobs.BorrarTramitesBackupJob;
import es.caib.zonaper.admin.scheduler.jobs.ControlEntregaNotificacionesJob;
import es.caib.zonaper.admin.scheduler.jobs.RevisarRegistrosEfectuadosJob;
import es.caib.zonaper.admin.scheduler.jobs.TramitesCaducadosJob;

/**
 * @web.servlet name="zonaperSchedulerServlet" load-on-startup="2"
 * @web.servlet-mapping url-pattern="/zonaperSchedulerServlet"
 */
public class ZonaperSchedulerServlet implements Servlet
{
	Log _log = LogFactory.getLog( ZonaperSchedulerServlet.class );
	
	private static String NAME = "Tramites Caducados";
	private static String NAME2 = "Revisar Registros Efectuados";
	private static String NAME3 = "Borrar Tramites Backup";
	private static String NAME4 = "Control entrega notificaciones";
	private static String GROUP = "Zonaper";
	private static int SCHEDULER_DELAY_MINUTES = 10;
	
	private Scheduler scheduler = null;
	
	public void init(ServletConfig config) throws ServletException
	{
		 try {
			SchedulerConfiguration configuration = SchedulerConfiguration
					.getInstance();
			StdSchedulerFactory schedFact = this.getSchedulerFactory(config);
			scheduler = schedFact.getScheduler();
			//scheduler.addJobListener(new ZonaperJobListener());

			boolean schedule = Boolean.valueOf(
					configuration.get("scheduler.backup.schedule"))
					.booleanValue();
			String cronExpression = configuration
					.get("scheduler.backup.cron.expression");

			_log.info("SCHEDULE [" + schedule + "]");
			_log.info("TIME EXPRESSION [" + cronExpression + "]");

			if (schedule) {
				CronTrigger trigger = new CronTrigger(NAME, GROUP);
				try {
					JobDetail jobDetail = new JobDetail(NAME, GROUP,
							TramitesCaducadosJob.class);
					trigger.setCronExpression(cronExpression);

					// Retrasamos 2 minutos
					java.util.Date startTime = new java.util.Date();
					startTime.setTime(startTime.getTime()
							+ (SCHEDULER_DELAY_MINUTES * 60 * 1000));
					_log.debug("Job " + NAME + ": Fecha de inicio[ "
							+ startTime + "]");

					scheduler.scheduleJob(jobDetail, trigger);
				} catch (Exception e) {
					_log.error("Exception scheduling : ", e);
					// e.printStackTrace();
				}
			}

			// inicializamos el Job de revisión de registros efectuados
			schedule = Boolean
					.valueOf(
							configuration
									.get("scheduler.revisarRegistrosEfectuados.schedule"))
					.booleanValue();
			cronExpression = configuration
					.get("scheduler.revisarRegistrosEfectuados.cron.expression");

			_log.info("SCHEDULE [" + schedule + "]");
			_log.info("TIME EXPRESSION [" + cronExpression + "]");

			if (schedule) {
				CronTrigger trigger = new CronTrigger(NAME2, GROUP);
				try {
					JobDetail jobDetail = new JobDetail(NAME2, GROUP,
							RevisarRegistrosEfectuadosJob.class);
					trigger.setCronExpression(cronExpression);

					// Retrasamos 2 minutos
					java.util.Date startTime = new java.util.Date();
					startTime.setTime(startTime.getTime()
							+ (SCHEDULER_DELAY_MINUTES * 60 * 1000));
					_log.debug("Job " + NAME2 + ": Fecha de inicio[ "
							+ startTime + "]");

					scheduler.scheduleJob(jobDetail, trigger);
				} catch (Exception e) {
					_log.error("Exception scheduling : ", e);
					// e.printStackTrace();
				}
			}

			// inicializamos el Job de revisión de registros efectuados
			schedule = Boolean.valueOf(
					configuration.get("scheduler.borradoBackup.schedule"))
					.booleanValue();
			cronExpression = configuration
					.get("scheduler.borradoBackup.cron.expression");

			_log.info("SCHEDULE [" + schedule + "]");
			_log.info("TIME EXPRESSION [" + cronExpression + "]");

			if (schedule) {
				CronTrigger trigger = new CronTrigger(NAME3, GROUP);
				try {
					JobDetail jobDetail = new JobDetail(NAME3, GROUP,
							BorrarTramitesBackupJob.class);
					trigger.setCronExpression(cronExpression);

					// Retrasamos 2 minutos
					java.util.Date startTime = new java.util.Date();
					startTime.setTime(startTime.getTime()
							+ (SCHEDULER_DELAY_MINUTES * 60 * 1000));
					_log.debug("Job " + NAME3 + ": Fecha de inicio[ "
							+ startTime + "]");

					scheduler.scheduleJob(jobDetail, trigger);
				} catch (Exception e) {
					_log.error("Exception scheduling : ", e);
					// e.printStackTrace();
				}
			}
			
			// inicializamos el Job de control de entrega de notificaciones
			schedule = Boolean.valueOf(
					configuration.get("scheduler.entregaNotificaciones.schedule"))
					.booleanValue();
			cronExpression = configuration
					.get("scheduler.entregaNotificaciones.cron.expression");
			String controlEntregaNotificaciones = configuration
					.get("notificaciones.controlEntrega.habilitar");			

			_log.info("SCHEDULE [" + schedule + "]");
			_log.info("TIME EXPRESSION [" + cronExpression + "]");

			if (schedule && "true".equals(controlEntregaNotificaciones)) {
				CronTrigger trigger = new CronTrigger(NAME4, GROUP);
				try {
					JobDetail jobDetail = new JobDetail(NAME4, GROUP,
							ControlEntregaNotificacionesJob.class);
					trigger.setCronExpression(cronExpression);

					// Retrasamos 2 minutos
					java.util.Date startTime = new java.util.Date();
					startTime.setTime(startTime.getTime()
							+ (SCHEDULER_DELAY_MINUTES * 60 * 1000));
					_log.debug("Job " + NAME4 + ": Fecha de inicio[ "
							+ startTime + "]");

					scheduler.scheduleJob(jobDetail, trigger);
				} catch (Exception e) {
					_log.error("Exception scheduling : ", e);
					// e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			_log.error("Exception scheduling : ", ex);
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
		return "ZonaperSchedulerServlet";
	}

	public void destroy()
	{		
		try{scheduler.shutdown( true );}catch(Exception ex){};		
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
