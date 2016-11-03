package es.caib.sistra.admin.action.cuadernoCarga;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.admin.form.CuadernoCargaForm;
import es.caib.sistra.admin.util.Util;
import es.caib.sistra.model.admin.CuadernoCarga;
import es.caib.sistra.persistence.delegate.CuadernoCargaDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.util.StringUtil;

/**
 * Action para consultar un CuadernoCarga.
 *
 * @struts.action
 *  name="cuadernoCargaForm"
 *  path="/admin/cuadernoCarga/seleccion"
 *  validate="false"
 *
 *
 * @struts.action-forward
 *  name="success" path=".cuadernoCarga.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".cuadernoCarga.pendientesDesarrollador"
 *
 */
public class SeleccionarCuadernoCargaAction extends BaseAction
{
	private static Log log = LogFactory.getLog( SeleccionarCuadernoCargaAction.class );
	 
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
        String idString = request.getParameter("codigo");
        idString = StringUtils.defaultString( idString, ( String ) request.getAttribute( "codigoCuaderno" ));
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        Long id = new Long(idString);
        
        CuadernoCargaForm cuadernoCargaForm = ( CuadernoCargaForm ) form;
        
        CuadernoCargaDelegate delegate = DelegateUtil.getCuadernoCargaDelegate();
        CuadernoCarga cuadernoCarga = delegate.obtenerCuadernoCarga( id );
        
        BeanUtils.copyProperties( cuadernoCargaForm, cuadernoCarga );
        
        cuadernoCargaForm.setRawFechaCarga( StringUtil.fechaACadena( cuadernoCarga.getFechaCarga() ) );
        
        request.setAttribute( "cuadernoCarga", cuadernoCarga );
        Util.setDevelopperMode( request );
		
        return mapping.findForward( "success" );
        
    }
	 
}
