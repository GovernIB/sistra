package es.caib.sistra.admin.action.cuadernoCarga;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.admin.util.Util;
import es.caib.sistra.model.admin.CuadernoCarga;

/**
 * Action para preparar el alta de un Cuaderno de Carga.
 *
 * @struts.action
 * 	name="cuadernoCargaForm"
 *  path="/admin/cuadernoCarga/alta"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".cuadernoCarga.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".cuadernoCarga.lista"
 *
 */
public class AltaCuadernoCargaAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( AltaCuadernoCargaAction.class );
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
    	CuadernoCarga cuadernoCarga = new CuadernoCarga();
    	cuadernoCarga.setEstadoAuditoria( CuadernoCarga.PENDIENTE_ENVIO );
    	request.setAttribute( "cuadernoCarga", cuadernoCarga );
    	Util.setDevelopperMode( request );
    	return mapping.findForward( "success" );
    }
}
