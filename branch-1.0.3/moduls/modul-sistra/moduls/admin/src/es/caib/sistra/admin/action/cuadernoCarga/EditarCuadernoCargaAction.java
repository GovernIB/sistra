package es.caib.sistra.admin.action.cuadernoCarga;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
 * Action para editar un CuadernoCarga.
 *
 * @struts.action
 *  name="cuadernoCargaForm"
 *  scope="request"
 *  validate="true"
 *  input=".cuadernoCarga.editar"
 *  path="/admin/cuadernoCarga/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/admin/cuadernoCarga/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".cuadernoCarga.editar"
 *
 * @struts.action-forward
 *  name="success" path=".cuadernoCarga.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".cuadernoCarga.lista"
 *
 */
public class EditarCuadernoCargaAction extends BaseAction
{
	public static Log log = LogFactory.getLog( EditarCuadernoCargaAction.class );
	
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
    	log.debug( "Entramos en EditarCuadernoCarga" );
    	CuadernoCargaForm cuadernoCargaForm = ( CuadernoCargaForm ) form;
    	
        if (isCancelled(request)) 
        {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }
        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");
            CuadernoCargaDelegate delegate = DelegateUtil.getCuadernoCargaDelegate();
            CuadernoCarga cuadernoCarga = null;
            
            if ( isModificacion( request ) )
            {
            	cuadernoCarga = delegate.obtenerCuadernoCarga( cuadernoCargaForm.getCodigo() );
            	// Si se pasa a no auditado, cambiamos tambien la fecha de auditoria
            	if ( CuadernoCarga.AUDITADO == cuadernoCarga.getEstadoAuditoria() && CuadernoCarga.AUDITADO != cuadernoCargaForm.getEstadoAuditoria () )
            	{
            		cuadernoCarga.setFechaAuditoria( null );
            	}
            	
            }
            else
            {
            	cuadernoCarga = new CuadernoCarga();
            	cuadernoCarga.setFechaAlta( new java.sql.Timestamp( new java.util.Date().getTime()  ) );
            	cuadernoCargaForm.setEstadoAuditoria( CuadernoCarga.PENDIENTE_ENVIO );
            }

            if ( CuadernoCarga.AUDITADO == cuadernoCargaForm.getEstadoAuditoria () && CuadernoCarga.AUDITADO != cuadernoCarga.getEstadoAuditoria () )
            {
            	cuadernoCarga.setFechaAuditoria( new java.sql.Timestamp( new java.util.Date().getTime()  ) );
            }
            BeanUtils.copyProperties( cuadernoCarga, cuadernoCargaForm );
            // Ponemos fecha de auditoria cuando es necesario            
            cuadernoCarga.setFechaCarga( new Timestamp ( StringUtil.cadenaAFecha( cuadernoCargaForm.getRawFechaCarga() ).getTime() ));
            if ( !isModificacion( request )) 
            {
            	cuadernoCarga.setEstadoAuditoria( CuadernoCarga.PENDIENTE_ENVIO );
            }
            
            cuadernoCargaForm.setCodigo( delegate.grabarCuadernoCarga( cuadernoCarga ) );
            
            request.setAttribute( "cuadernoCarga", cuadernoCarga );
            
        }
        
        // Establecemos si se trata de auditor o administrador a través de la acción a realizar
        // y el estado al que se quiere pasar
        if ( isAlta( request ) )
        {
        	Util.setDevelopperMode( request );
        }
        else if ( isModificacion( request )  )
        {
        	if ( ( CuadernoCarga.AUDITADO == cuadernoCargaForm.getEstadoAuditoria() || CuadernoCarga.RECHAZADO == cuadernoCargaForm.getEstadoAuditoria() ) )
        	{
        		Util.setAuditorMode( request );
        	}
        	else
        	{
        		Util.setDevelopperMode( request );
        	}
        }
        
        
        return mapping.findForward( "success" );
    }

}
