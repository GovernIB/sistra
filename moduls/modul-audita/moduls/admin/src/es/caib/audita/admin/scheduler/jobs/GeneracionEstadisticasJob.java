package es.caib.audita.admin.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.CuadroMandoInicioDelegate;
import es.caib.audita.persistence.delegate.DelegateException;
import es.caib.audita.persistence.delegate.DelegateUtil;
import es.caib.audita.persistence.util.Util;

/**
 * Job que realiza la generacion de estadisticas
 *
 * @author clmora
 *
 */
public class GeneracionEstadisticasJob implements Job {
	private Log log = LogFactory.getLog(GeneracionEstadisticasJob.class);

	/**
	 * Job que ejecuta la eliminación y backup de los tramites eliminados
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.debug("Job generacion estadisticas");
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		try {

			String ls_fcHasta = null;
			String ls_fcDesde = null;
			try {
				ls_fcHasta = Util.getFechaActual("DD/MM/YYYY");
				ls_fcDesde = ls_fcHasta.substring(0, 6)
						+ (Integer.parseInt(ls_fcHasta.substring(6).trim()) - 1);
			} catch (Throwable e) {
				log.error("Error obteniendo intervalo de fechas", e);
				log.error("Excepcion: " + e.getMessage(), e);
			}

			log.info("Comienza generar cuadro mando inicio...");

			log.info("Generacion cuadro mando inicio: generaEstadisticasServicios");
			delegate.generaCuadroMandoInicioEstadisticasServicios();
			log.info("Generacion cuadro mando inicio: generaEstadisticasPortal");
			delegate.generaCuadroMandoInicioEstadisticasPortal(ls_fcDesde,
					ls_fcHasta);
			log.info("Generacion cuadro mando inicio: generaEstadisticasTramitacion");
			delegate.generaCuadroMandoInicioEstadisticasTramitacion(ls_fcDesde,
					ls_fcHasta);
			log.info("Generacion cuadro mando inicio: generaEstadisticasMasTramitados");
			delegate.generaCuadroMandoInicioEstadisticasMasTramitados();
			log.info("Generacion cuadro mando inicio: generaEstadisticasMasAccedidos");
			delegate.generaCuadroMandoInicioEstadisticasMasAccedidos();
			log.info("Generacion cuadro mando inicio: generaEstadisticasUltimosAlta");
			delegate.generaCuadroMandoInicioEstadisticasUltimosAlta();

			log.info("Fin generar cuadro mando inicio");

		} catch (DelegateException e) {
			log.error("Excepcion lanzando Job: " + e.getMessage(), e);
		}

	}
}
