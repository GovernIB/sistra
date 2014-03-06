package es.caib.redose.back.action.migracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.model.MigracionExportWork;
import es.caib.redose.model.MigracionExportWorks;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsAdminDelegate;

/**
 * @struts.action
 *  path="/back/migracion/migracionIterationAction"
 *  scope="request"
 *  validate="false"
 *
 *
 *	REALIZA ITERACION PROCESO MIGRACION: OBTIENE TRABAJO EN SESION Y PROCESA SIGUIENTES ITERACION
 *	DEVUELVE:
 *		PROCESS:NUM_DOCS_PROCESADOS-TOTAL_DOCS_A_PROCESAR-TOTAL_DOCS-NUM_ERRORES-FINALIZADO
 *		ERROR:DESC_ERROR
 *		CANCEL:TRUE
 *
 */
public class MigracionIterationAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(MigracionIterationAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String res; 
		String id = null;
		try{
			
			RdsAdminDelegate dlg = DelegateUtil.getRdsAdminDelegate();
			
			id = request.getParameter("id");
			String cancel = request.getParameter("cancel");
			
			if (id!=null){				
				
				// Recogemos trabajo de sesion
				MigracionExportWorks works = (MigracionExportWorks) request.getSession().getAttribute(MigracionExportWorks.KEY_MIGRACION_WORKS);
				MigracionExportWork csv = works.getMigracionExportWork(id);
				
				// Verificamos si hay que cancelar el trabajo
				if ("true".equals(cancel)) {
					// CANCEL:TRUE
					res = "CANCEL:TRUE";
				} else {
					// Obtenemos siguientes docs a migrar
					Long[] docs = csv.obtenerDocsProximaIteracion();
					boolean maxErrores = false;
					for (int i=0;i<docs.length;i++){
						// Si sobrepasas el maximo de errores finalizamos
						maxErrores =  (csv.getNumErrores() > csv.getParametros().getNumMaxErrores()) ;
						if (maxErrores){
							break;
						}
						// Migramos doc
						try {
							dlg.migrarDocumento(docs[i], csv.getParametros().getUbicacionDestino(), csv.getParametros().isBorrarUbicacionOrigen());
						} catch (Exception ex) {
							// TODO COMO SE SABE ERRORES??
							log.error(ex);
							// Incrementamos numero de errores
							csv.incrementarError(ex);										
						}
					}		
					
					// Comprobamos si se ha finalizado
					boolean finalizado = maxErrores || (csv.getTotalProcesados() == csv.getTotalProcesar());
					
					// Devolvemos estado trabajo
					// PROCESS:NUM_DOCS_PROCESADOS-TOTAL_DOCS_A_PROCESAR-TOTAL_DOCS-NUM_ERRORES-FINALIZADO
					res = "PROCESS:" + csv.getTotalProcesados() + "-"+ csv.getTotalProcesar() + "-"+ csv.getTotalDocumentos() + "-" + csv.getNumErrores() + "-" + finalizado;
				}				
			}	else {
				// ERROR:DESC_ERROR
				res = "ERROR: Faltan parametros";
			}
			
			log.debug("[MIGRACION REDOSE] " + res);
			
		}catch(Exception ex){
			log.error("[MIGRACION REDOSE] " + ex.getMessage(),ex);
			// ERROR:DESC_ERROR
			res = "ERROR:" + ExceptionUtils.getFullStackTrace(ex);
		}
		
		// Si es un error limpiamos trabajo
		if (res.startsWith("ERROR:") && id != null) {
			MigracionExportWorks works = (MigracionExportWorks) request.getSession().getAttribute(MigracionExportWorks.KEY_MIGRACION_WORKS);
			if (works != null) {
				works.removeMigracionExportWork(id);				
			}
		}
		
		// Devolvemos respuesta
		response.getWriter().print(res);
        return null;
		
    }
		
}
