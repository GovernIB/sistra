package es.caib.redose.back.action.migracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.model.MigracionExportWork;
import es.caib.redose.model.MigracionExportWorks;


/**
 * @struts.action
 *  path="/back/migracion/migracionFinalizarAction"
 *  scope="request"
 *  validate="false"
 *
 *
 *	FINALIZA PROCESO DE EXPORTACION LIMPIANDO INFO DE SESION
 *
 *	DEVUELVE:
 *
 *		FIN:DESC_ERRORES
 *
 *
 */
public class MigracionFinalizarAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(MigracionFinalizarAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String id = request.getParameter("id");
		String descErrores = "NO_ERROR";
		if (id!=null){
			MigracionExportWorks works = MigracionExportWorks.getInstance(request);
			if (works != null) {
				MigracionExportWork work = works.getMigracionExportWork(id);
				if (work != null) {
					if (work.getNumErrores() > 0) {
						descErrores = work.obtenerDescripcionErrores();
					}
				}
				works.removeMigracionExportWork(id);				
			}
		}
		response.getWriter().print("FIN:" + descErrores);
        return null;
	}
	
	
}
