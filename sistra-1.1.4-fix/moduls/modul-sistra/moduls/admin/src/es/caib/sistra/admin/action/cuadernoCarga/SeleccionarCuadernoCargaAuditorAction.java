package es.caib.sistra.admin.action.cuadernoCarga;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.util.Util;

/**
 * Action para consultar un CuadernoCarga.
 *
 * @struts.action
 *  name="cuadernoCargaForm"
 *  path="/admin/cuadernoCarga/seleccionAuditor"
 *  validate="false"
 *
 *
 * @struts.action-forward
 *  name="success" path=".cuadernoCarga.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".cuadernoCarga.pendientesAuditor"
 *
 */
public class SeleccionarCuadernoCargaAuditorAction extends SeleccionarCuadernoCargaAction
{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		super.execute( mapping, form, request, response );
		Util.setAuditorMode( request );
		return mapping.findForward( "success" );
	}

}
