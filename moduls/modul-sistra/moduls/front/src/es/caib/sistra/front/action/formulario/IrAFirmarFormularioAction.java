package es.caib.sistra.front.action.formulario;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.action.BaseAction;
import es.caib.sistra.front.form.formulario.IrAFirmarFormularioForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.ConvertUtil;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;

/**
 * @struts.action
 *  name="irAFirmarFormularioForm"
 *  path="/protected/irAFirmarFormulario"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".irAFirmarFormulario"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class IrAFirmarFormularioAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		IrAFirmarFormularioForm formulario = ( IrAFirmarFormularioForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		
		RespuestaFront respuestaFront = delegate.pasoActual();
		
		TramiteFront tramite = respuestaFront.getInformacionTramite();
		
		// Buscamos formulario a firmar
		ArrayList arlFormularios = tramite.getFormularios();
		DocumentoFront f = null; 
		for ( int i = 0; i < arlFormularios.size(); i++ )
		{
			f = ( DocumentoFront ) arlFormularios.get( i );
			if ( formulario.getIdentificador().equals( f.getIdentificador() ) )
			{
				request.setAttribute( "formulario", f );
				break;
			}
		}
		
		// Pasamos a firmar formulario 
		respuestaFront = delegate.irAFirmarFormulario( formulario.getIdentificador(), formulario.getInstancia() );
		
		// Establecemos datos formulario		
		byte[] datosFormulario =  (byte[]) respuestaFront.getParametros().get( "datos" );
		String formateado = (String) respuestaFront.getParametros().get( "formateado" );
		
		String base64EncXml = ConvertUtil.bytesToBase64UrlSafe(datosFormulario);
		request.setAttribute( "base64XmlForm", base64EncXml );
		request.setAttribute("formateado", formateado);
		
		// Indicamos si debemos mostrar la firma digital o firma delegada
		String mostrarFirmaDigital = "N";
		String listaFirmantes = "";
		String[] nifFirmantes = null;
		if (f.isFirmar()){
			if (f.isFirmaDelegada()){
				mostrarFirmaDigital = "D";
			}else{
				mostrarFirmaDigital = "S";
			}
			// Formateamos lista de firmantes
			nifFirmantes = f.getFirmante().split("#");
			String[] nomFirmantes = f.getNombreFirmante().split("#");
			
			String partY = " y ";
			if ("ca".equals(this.getLang(request))){
				partY = " i ";
			}else if ("en".equals(this.getLang(request))){
				partY = " and ";
			}
					
			for (int i=0;i<nifFirmantes.length;i++){
				if (i>0){
					if (i == (nifFirmantes.length - 1)){
						listaFirmantes +=  partY;
					}else{
						listaFirmantes += ", ";
					}
				}
				listaFirmantes += nifFirmantes[i] + (StringUtils.isNotBlank(nomFirmantes[i])?" - " + nomFirmantes[i]:"");				
			}
		}
		request.setAttribute(Constants.MOSTRAR_FIRMA_DIGITAL,mostrarFirmaDigital);
		request.setAttribute( "listaFirmantes", listaFirmantes );
		
		// Identificamos firmante para plugin firma web
		String nifFirmante = "";
		if (nifFirmantes != null && nifFirmantes.length == 1) {
			nifFirmante = nifFirmantes[0];
		} else if (respuestaFront.getInformacionTramite().getDatosSesion().getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO) {
			String perfilAcceso = respuestaFront.getInformacionTramite().getDatosSesion().getPerfilAcceso();
			if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(perfilAcceso)) {
				nifFirmante = respuestaFront.getInformacionTramite().getDatosSesion().getNifDelegado();
			} else {
				nifFirmante = respuestaFront.getInformacionTramite().getDatosSesion().getNifUsuario();
			}
		}
		request.setAttribute( "nifFirmante", nifFirmante );
		
		this.setRespuestaFront( request, respuestaFront );
		return mapping.findForward("success");
	}
}
