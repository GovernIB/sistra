package es.caib.zonaper.back.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.StringUtil;
import es.caib.zonaper.back.Constants;
import es.caib.zonaper.back.form.BusquedaPreregistroForm;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;

/**
 * @struts.action
 *  name="busquedaPreregistroForm"
 *  path="/busquedaPreregistro"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".datosRegistroEntrada"
 *  
 * @struts.action-forward
 *  name="confirmado" path=".exitoConfirmacion"  
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaPreregistroAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaPreregistroForm formularioBusqueda = ( BusquedaPreregistroForm ) form;
		
		if ( StringUtils.isEmpty( formularioBusqueda.getNumeroPreregistro() ) )
		{
			this.setMessage( request, "errors.preregistroNoExiste", new Object[] { formularioBusqueda.getNumeroPreregistro() } );
			return mapping.findForward( "fail" );
		}
		if ( StringUtils.isEmpty( formularioBusqueda.getDigitoControl() ) )
		{
			this.setMessage( request, "busqueda.digitoControl.vacio" );
			return mapping.findForward( "fail" );
		}
		
		// Validamos DC
		if (!StringUtil.calculaDC(formularioBusqueda.getNumeroPreregistro().trim()).equals(formularioBusqueda.getDigitoControl())){
			this.setMessage( request, "errors.digitoControl" );
			return mapping.findForward( "fail" );
		}
		
		EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
		EntradaPreregistro preregistro = delegate.obtenerEntradaPreregistroPorNumero( formularioBusqueda.getNumeroPreregistro().trim() );
		if ( preregistro == null )	
		{
			this.setMessage( request, "errors.preregistroNoExiste", new Object[] { formularioBusqueda.getNumeroPreregistro() } );
			return mapping.findForward( "fail" );
		}
		
		// Comprobamos si es un preregistro confirmado presencialmente
		// 	- preregistro/preenvio confirmado normalmente
		//  - preenvio automatico y numero preregistro <> numero registro
		// y mostramos opcion de volver a imprimir sello
		if ( preregistro.getFechaConfirmacion() != null && preregistro.getNumeroRegistro() != null)
		{				
			
			// Comprobamos si:
			//		- es un preregistro confirmado automaticamente
			// 		- es un preregistro confirmado automaticamente y despues confirmado presencialmente
			// 		- es un preregistro confirmado por el gestor
			if (preregistro.getNumeroRegistro().equals(preregistro.getNumeroPreregistro())){
				// Si no es preenvio automatico indica que esta confirmado por el gestor
				if (preregistro.getConfirmadoAutomaticamente() == 'N'){
					this.setMessage( request, "errors.preregistroConfirmadoGestor");
					return mapping.findForward( "fail" );
				}else{
					// Si es preenvio automatico tomamos datos para la confirmacion presencial
					request.setAttribute( "preregistro", preregistro );
					return mapping.findForward( "success" );
				}
			}
			
			// Generamos peticion de impresion
			String idPeticionImp = Constants.IMPRESION_SELLO_KEY +  Long.toString(System.currentTimeMillis());
			request.getSession().setAttribute(idPeticionImp,preregistro.getCodigo());
				
			Map hsmParams = new HashMap();
			hsmParams.put( "codigo", idPeticionImp );
			String actionLabelKey = "exitoConfirmacion.enlaceImpresionSello";
				
			this.setMessage( request, "errors.preregistroYaConfirmado", new Object []{ formularioBusqueda.getNumeroPreregistro(), preregistro.getNumeroRegistro(), StringUtil.fechaACadena( preregistro.getFechaConfirmacion() ) }, "imprimirSello", hsmParams, actionLabelKey );	
			return mapping.findForward( "confirmado" );
			
		}
		
		// Pasamos a tomar los datos para la confirmacion
		request.setAttribute( "preregistro", preregistro );
		return mapping.findForward( "success" );
    }
}
