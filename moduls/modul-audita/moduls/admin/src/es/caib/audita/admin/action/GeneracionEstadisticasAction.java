package es.caib.audita.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateException;
import es.caib.audita.persistence.delegate.DelegateUtil;
import es.caib.audita.persistence.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @struts.action
 *  path="/generacionEstadisticas"
 *  scope="request"
 *  validate="false"
 */
public class GeneracionEstadisticasAction extends Action
{

	private static Log log = LogFactory.getLog( GeneracionEstadisticasAction.class );

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
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
			throw new Exception(e.getMessage(), e);
		}

		response.getOutputStream().write("Proceso finalizado".getBytes());
		return null;
	}


}
