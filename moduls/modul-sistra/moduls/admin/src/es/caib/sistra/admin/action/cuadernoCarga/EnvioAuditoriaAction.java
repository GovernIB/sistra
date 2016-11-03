package es.caib.sistra.admin.action.cuadernoCarga;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.admin.form.CuadernoCargaForm;
import es.caib.sistra.model.admin.CuadernoCarga;
import es.caib.sistra.persistence.delegate.CuadernoCargaDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;

/**
 * Action para consultar un CuadernoCarga.
 *
 * @struts.action
 *  path="/admin/cuadernoCarga/envioAuditoria"
 *  validate="false"
 *
 *
 * @struts.action-forward
 *  name="success" path=".cuadernoCarga.pendientesDesarrollador"
 *
 * @struts.action-forward
 *  name="fail" path=".cuadernoCarga.pendientesDesarrollador"
 *
 */
public class EnvioAuditoriaAction extends BaseAction
{
	private static Log log = LogFactory.getLog( EnvioAuditoriaAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        Long id = new Long(idString);
        
        CuadernoCargaForm cuadernoCargaForm = ( CuadernoCargaForm ) form;
        
        CuadernoCargaDelegate delegate = DelegateUtil.getCuadernoCargaDelegate();
        CuadernoCarga cuadernoCarga = delegate.obtenerCuadernoCarga( id );
        boolean requiereAuditoria = requiereAuditoria( cuadernoCarga );
        cuadernoCarga.setEstadoAuditoria( requiereAuditoria ? CuadernoCarga.PENDIENTE_AUDITAR : CuadernoCarga.NO_REQUIERE_AUDITORIA ) ;
        cuadernoCarga.setFechaEnvio( new Timestamp( System.currentTimeMillis() ) );
        
        delegate.grabarCuadernoCarga( cuadernoCarga );
        request.setAttribute( "requiereAuditoria", "" + requiereAuditoria );
		
		return mapping.findForward( "success" );
    }
	
	private boolean requiereAuditoria( CuadernoCarga cuadernoCarga ) throws Exception
	{
		return DelegateUtil.getAuditoriaCuadernoDelegate().auditoriaRequerida( cuadernoCarga.getCodigo() );
	}

}
