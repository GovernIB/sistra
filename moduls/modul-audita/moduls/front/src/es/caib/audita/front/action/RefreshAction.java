package es.caib.audita.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.model.CuadroMandoInicio;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;
import es.caib.audita.persistence.util.Util;

/**
 * @struts.action path="/refresh" scope="request" validate="false"
 *
 * @struts.action-forward name="success" path=".inicio"
 *
 * @struts.action-forward name="fail" path=".error"
 */
public class RefreshAction extends BaseAction {

	private static Log logger = LogFactory.getLog( RefreshAction.class );

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();

		String ls_fcHasta = null;
		String ls_fcDesde = null;
		try {
			ls_fcHasta = Util.getFechaActual("DD/MM/YYYY");
			ls_fcDesde = ls_fcHasta.substring(0, 6)
					+ (Integer.parseInt(ls_fcHasta.substring(6).trim()) - 1);
		} catch (Throwable e) {
			logger.error("Error obteniendo intervalo de fechas", e);
			logger.error("Excepcion: " + e.getMessage(), e);
		}

		logger.info("Comienza generar cuadro mando inicio...");

		logger.info("Generacion cuadro mando inicio: generaEstadisticasServicios");
		delegate.generaCuadroMandoInicioEstadisticasServicios();
		logger.info("Generacion cuadro mando inicio: generaEstadisticasPortal");
		delegate.generaCuadroMandoInicioEstadisticasPortal(ls_fcDesde,
				ls_fcHasta);
		logger.info("Generacion cuadro mando inicio: generaEstadisticasTramitacion");
		delegate.generaCuadroMandoInicioEstadisticasTramitacion(ls_fcDesde,
				ls_fcHasta);
		logger.info("Generacion cuadro mando inicio: generaEstadisticasMasTramitados");
		delegate.generaCuadroMandoInicioEstadisticasMasTramitados();
		logger.info("Generacion cuadro mando inicio: generaEstadisticasMasAccedidos");
		delegate.generaCuadroMandoInicioEstadisticasMasAccedidos();
		logger.info("Generacion cuadro mando inicio: generaEstadisticasUltimosAlta");
		delegate.generaCuadroMandoInicioEstadisticasUltimosAlta();

		logger.info("Fin generar cuadro mando inicio");

		CuadroMandoInicio cuadroMandoDetalle = delegate
				.obtenerCuadroMandoInicio(getLang(request));
		request.setAttribute("cuadro", cuadroMandoDetalle);

		return mapping.findForward("success");
	}

}
