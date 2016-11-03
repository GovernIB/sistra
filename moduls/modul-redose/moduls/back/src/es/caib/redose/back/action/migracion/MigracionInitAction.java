package es.caib.redose.back.action.migracion;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.MigracionForm;
import es.caib.redose.model.MigracionExportWork;
import es.caib.redose.model.MigracionExportWorks;
import es.caib.redose.model.MigracionParametros;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsAdminDelegate;
import es.caib.util.DataUtil;
import es.caib.util.StringUtil;

/**
 * @struts.action
 *  name="migracionForm"
 *  path="/back/migracion/migracionInitAction"
 *  scope="request"
 *  validate="false"
 *
 *
 *	INICIA PROCESO DE MIGRACION: CREA TRABAJO EN SESION
 *
 *	Devuelve: INIT:ID_TRABAJO-NUM_DOCS_MIGRAR-NUM_DOCS_TOTAL
 *
 *
 */
public class MigracionInitAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(MigracionInitAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		String result="ERROR:Error desconocido";
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		Locale locale = getLocale( request );
		
		try{
			
			RdsAdminDelegate dlg = DelegateUtil.getRdsAdminDelegate();
			
			MigracionForm f  = ( MigracionForm ) form;
			
			// Trabajos de migracion
			MigracionExportWorks works = MigracionExportWorks.getInstance(request);
			
			// Buscamos si existe algun trabajo anterior sin finalizar
			if (works.existeTrabajoIniciado()) {
				// Existen trabajos pendientes (esperar 15 minutos)
				result="ERROR:" + resources.getMessage( locale, "migracion.errorExisteProcesoAnterior");
			} else {
				// Verificamos parametros de entrada
				MigracionParametros params = verificarParametrosMigracion(f,
						resources, locale);
			
				// Buscamos documentos a exportar
				long totalDocs = dlg.contarDocumentosMigracion(params.getUbicacionOrigen(), params.getFechaDesde(), params.getFechaHasta());
				
				if (totalDocs == 0) {
					// No hay docs a migrar
					result="ERROR:" + resources.getMessage( locale, "migracion.errorNoHayDocs");
				} else {
					// Creamos trabajo y lo guardamos en la sesion
					List codigos = dlg.listarDocumentosMigracion(params.getUbicacionOrigen(), params.getFechaDesde(), params.getFechaHasta(), params.getNumMaxDocs());			
					MigracionExportWork trabajo = new MigracionExportWork(params, totalDocs, codigos);				
					works.addWork(trabajo);					
					result = "INIT:"+trabajo.getId()+"-"+codigos.size()+"-"+totalDocs;
				}
			}
					
		}catch(Exception ex){			 
			 result="ERROR:" + resources.getMessage( locale, "migracion.errorIniciandoProcesoExportacion") + ex.getMessage();
		}			
		
		// Devolvemos Id trabajo
		response.getWriter().print(result);
        return null;
		
    }

	private MigracionParametros verificarParametrosMigracion(MigracionForm f,
			MessageResources resources, Locale locale) throws Exception {		
		// - Parametros obligatorios
		if (StringUtils.isEmpty(f.getUbicacionOrigen()) ||
			StringUtils.isEmpty(f.getUbicacionDestino()) ||
			StringUtils.isEmpty(f.getNumDocsIteracion()) || 
			StringUtils.isEmpty(f.getTimeoutIteracion()) ||
			StringUtils.isEmpty(f.getNumMaxErrores()) ||
			StringUtils.isEmpty(f.getNumMaxDocs()) ||
			StringUtils.isEmpty(f.getBorrarUbicacionOrigen()))  {
			throw new Exception(resources.getMessage( locale, "migracion.faltaParametros"));			
		}
		// - Ubicacion origen distinta de destino
		if (f.getUbicacionOrigen().equals(f.getUbicacionDestino())) {
			throw new Exception(resources.getMessage( locale, "migracion.mismaUbicacion"));
		}
		// - Validacion fechas
		Date desde=null;		
		if (StringUtils.isNotEmpty(f.getDesde())) {
			desde = StringUtil.cadenaAFecha(f.getDesde(),StringUtil.FORMATO_FECHA);
			if (desde == null) {				
				
				throw new Exception(resources.getMessage( locale, "migracion.fechaDesdeNoValida"));	
			}
			desde = DataUtil.obtenerPrimeraHora(desde);
		}
		Date hasta=null;		
		if (StringUtils.isNotEmpty(f.getHasta())) {
			hasta = StringUtil.cadenaAFecha(f.getHasta(),StringUtil.FORMATO_FECHA);
			if (hasta == null) {				
				throw new Exception(resources.getMessage( locale, "migracion.fechaHastaNoValida"));	
			}
			hasta = DataUtil.obtenerUltimaHora(hasta);
		}										
		MigracionParametros params = new MigracionParametros();
		params.setUbicacionOrigen(Long.parseLong(f.getUbicacionOrigen()));
		params.setUbicacionDestino(Long.parseLong(f.getUbicacionDestino()));
		params.setFechaDesde(desde);
		params.setFechaHasta(hasta);
		params.setNumDocsIteracion(Integer.parseInt(f.getNumDocsIteracion()));
		params.setTimeoutIteracion(Integer.parseInt(f.getTimeoutIteracion()));
		params.setNumMaxDocs(Integer.parseInt(f.getNumMaxDocs()));
		params.setNumMaxErrores(Integer.parseInt(f.getNumMaxErrores()));
		params.setBorrarUbicacionOrigen(Boolean.parseBoolean(f.getBorrarUbicacionOrigen()));
		return params;
	}
	
	
}
