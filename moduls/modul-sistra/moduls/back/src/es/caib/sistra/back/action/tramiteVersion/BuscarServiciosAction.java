package es.caib.sistra.back.action.tramiteVersion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.BuscarServiciosForm;

/**
 * Action para consultar una tramiteVersion.
 *
 * @struts.action path="/back/tramiteVersion/buscarServicios"
 *                name="buscarServiciosForm" scope="session" validate="false"
 *
 *
 * @struts.action-forward name="success" path=".busquedaServicios"
 *
 * @struts.action-forward name="fail" path="/arbolServicios.do"
 *
 */
public class BuscarServiciosAction extends BaseAction {
	protected static Log log = LogFactory.getLog(BuscarServiciosAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BuscarServiciosForm bf = (BuscarServiciosForm) form;
		
		try {
			// Buscar servicios
			String filtro = bf.getFiltro();

			RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
			List organosDestino = dlgRte.obtenerServiciosDestino();
			
			// Limpiamos los que no cumplan el filtro
			List result = new ArrayList();
			if (StringUtils.isBlank(filtro)) {
				result.addAll(organosDestino);
			} else {
				filtro = filtro.toLowerCase();
				for (Iterator it = organosDestino.iterator(); it.hasNext();) {
					ValorOrganismo vo = (ValorOrganismo) it.next();
					if (	(vo.getCodigo().toLowerCase().contains(filtro)) ||  
							(vo.getDescripcion() != null && vo.getDescripcion().toLowerCase().contains(filtro))
						){
						result.add(vo);
					}
				}
			}
			
			request.setAttribute("id", bf.getIdCampo());
			request.setAttribute("servicios", result);
			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("Excepcion buscando servicios: " + e.getMessage(), e);
			request.setAttribute("id", bf.getIdCampo());
			return mapping.findForward("fail");
		}
		
	}

}
