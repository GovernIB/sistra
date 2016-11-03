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
import es.caib.zonaper.back.form.ConfirmarPreregistroForm;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.PeticionImpresionSello;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * @struts.action
 *  name="confirmarPreregistroForm"
 *  path="/confirmarPreregistro"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".exitoConfirmacion"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class ConfirmarPreregistroAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		ConfirmarPreregistroForm formulario 	= ( ConfirmarPreregistroForm ) form;
		PadDelegate padDelegate = DelegatePADUtil.getPadDelegate();
		
		// Obtenemos datos geograficos
		String muniBaleares = formulario.getMunicipioBaleares();
		String muniFuera = formulario.getMunicipioFuera();
		String codProv,codMuni,descMuni;
		if (StringUtils.isNotEmpty(muniBaleares)){
			codProv = Constants.CODIGO_PROVINCIA_BALEARES;
			codMuni = muniBaleares;
			descMuni = DelegateUtil.getDominiosDelegate().obtenerDescripcionMunicipio(codProv,codMuni);
		}else{
			codProv = null;
			codMuni = null;
			descMuni = muniFuera;
		}
		
		// Comprobamos si es un preenvio que se ha confirmado automaticamente
		EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
    	EntradaPreregistro e = delegate.obtenerEntradaPreregistro(formulario.getCodigo());
    	if (e.getConfirmadoAutomaticamente() == 'S'){
    		padDelegate.confirmarPreenvioAutomatico( formulario.getCodigo(), formulario.getOficina(), codProv, codMuni, descMuni );
    	}else{		
    		padDelegate.confirmarPreregistro( formulario.getCodigo(), formulario.getOficina(),codProv, codMuni, descMuni );
    	}
		
    	// Obtenemos de nuevo la informacion del preregistro para obtener informacion de registro		
		EntradaPreregistro preregistroConfirmado = delegate.obtenerEntradaPreregistro( formulario.getCodigo() );
		
		// Generamos peticion de impresion
		String idPeticionImp = Constants.IMPRESION_SELLO_KEY +  Long.toString(System.currentTimeMillis());
		PeticionImpresionSello pi = new PeticionImpresionSello();
		pi.setCodigoEntradaPreregistro(formulario.getCodigo());
		pi.setCodigoOficinaRegistro(formulario.getOficina());
		request.getSession().setAttribute(idPeticionImp,pi);
		
		Map hsmParams = new HashMap();
		hsmParams.put( "codigo", idPeticionImp );
		
		String actionLabelKey = "exitoConfirmacion.enlaceImpresionSello";
		
		this.setMessage( request, "mensajes.confirmacionOK", new Object []{ preregistroConfirmado.getNumeroPreregistro(), preregistroConfirmado.getNumeroRegistro(), StringUtil.timestampACadena( preregistroConfirmado.getFechaConfirmacion() ) }, "imprimirSello", hsmParams, actionLabelKey );	
		return mapping.findForward( "success" );
    }

}
