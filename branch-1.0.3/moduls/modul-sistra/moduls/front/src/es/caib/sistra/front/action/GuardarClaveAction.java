package es.caib.sistra.front.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.OrganismoInfo;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.util.ConvertUtil;
import es.caib.util.StringUtil;

/**
 * @struts.action
 *  name="guardarClaveForm"
 *  path="/protected/guardarClave"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/protected/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="fail" path="/index.jsp"
 */
public class GuardarClaveAction extends BaseAction
{

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		//GuardarClaveForm formulario = ( GuardarClaveForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = delegate.pasoActual();
		TramiteFront tramiteInfo = respuestaFront.getInformacionTramite();
		
		// Obtenemos url donde esta instalado sistra
		Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
		String urlSistra = props.getProperty("sistra.url");
		
		// Obtenemos props organismo
		OrganismoInfo oi = (OrganismoInfo) request.getSession().getServletContext().getAttribute(Constants.ORGANISMO_INFO_KEY);
		
		// Generamos nombre de fichero		
		String lang = this.getLang(request);
		String nombreFichero = ConstantesSTR.CAT_LANGUAGE.equals( lang ) ? Constants.NOMBRE_FICHERO_CLAVEPERSISTENCIA_CAT : Constants.NOMBRE_FICHERO_CLAVEPERSISTENCIA_CAS; 		
		String shortDesc = StringUtil.escapeBadCharacters( tramiteInfo.getDescripcion() );
		int nMaxIndex = shortDesc.length() > Constants.MAX_FILE_NAME_SIZE ? Constants.MAX_FILE_NAME_SIZE :  shortDesc.length();
		shortDesc = shortDesc.substring( 0, nMaxIndex );
		//nombreFichero = nombreFichero.replaceAll( "<\\?>", shortDesc + "_" + StringUtil.fechaACadena( new Date(), "yyyyMMddHHmmss" ) );
		nombreFichero = nombreFichero.replaceAll( "<\\?>", StringUtil.fechaACadena( new Date(), "dd-MM-yyyy_HH-mm-ss" ) );
		
		// Generamos html con la clave		
		InputStream plantillaHtml = this.getClass().getResourceAsStream("GuardarClavePlantilla.properties");		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();		
		ConvertUtil.copy(plantillaHtml,bos);
		
		String  textoContinuarTramitacion = this.getResources(request).getMessage(this.getLocale(request),"guardarClave.textoContinuarTramitacion",tramiteInfo.getIdPersistencia(),Integer.toString(tramiteInfo.getDiasPersistencia()));
		String  textoPlazoEntrega = "";
	
		if (tramiteInfo.getFechaFinPlazo() != null){
			if (tramiteInfo.getTipoTramitacion() == 'T'){
				textoPlazoEntrega = this.getResources(request).getMessage(this.getLocale(request),"guardarClave.textoPlazoEntrega.telematica",StringUtil.fechaACadena(tramiteInfo.getFechaFinPlazo(),"dd-MM-yyyy"));
			}else{
				textoPlazoEntrega = this.getResources(request).getMessage(this.getLocale(request),"guardarClave.textoPlazoEntrega.presencial",StringUtil.fechaACadena(tramiteInfo.getFechaFinPlazo(),"dd-MM-yyyy"));
			}
		}
				
		String  textoEstadoTramitacion = this.getResources(request).getMessage(this.getLocale(request),"guardarClave.textoEstadoTramitacion", urlSistra, oi.getReferenciaPortal().get(lang));
		String  textoEnlace = this.getResources(request).getMessage(this.getLocale(request),"guardarClave.textoEnlace");
		
		String html = bos.toString();
		html = StringUtil.replace(html,"[#TITULO_TRAMITE#]",tramiteInfo.getDescripcion());
		html = StringUtil.replace(html,"[#CONTINUAR_TRAMITACION#]",textoContinuarTramitacion);
		html = StringUtil.replace(html,"[#PLAZO_ENTREGA#]",textoPlazoEntrega);
		html = StringUtil.replace(html,"[#ESTADO_TRAMITACION#]",textoEstadoTramitacion);
		html = StringUtil.replace(html,"[#ENLACE#]",textoEnlace);
		html = StringUtil.replace(html,"[#ID_PERSISTENCIA#]",tramiteInfo.getIdPersistencia());
		html = StringUtil.replace(html,"[#IDIOMA#]",this.getLocale(request).getLanguage());
		html = StringUtil.replace(html,"[#MODELO_TRAMITE#]",tramiteInfo.getModelo());
		html = StringUtil.replace(html,"[#VERSION_TRAMITE#]",Integer.toString(tramiteInfo.getVersion()));
		html = StringUtil.replace(html,"[#DESC_ORGANISMO#]",oi.getNombre());
		html = StringUtil.replace(html,"[#URL_PORTAL#]",oi.getUrlPortal());
		html = StringUtil.replace(html,"[#LOGO_ORGANISMO#]",oi.getUrlLogo());
		html = StringUtil.replace(html,"[#URL_SISTRA#]",urlSistra);
		
		byte [] datosFichero = html.getBytes("iso-8859-1");		
				
		// Guardamos en sesion un flag que nos indica que la clave ya ha sido almacenada (para no mostrar mensaje)
		request.getSession().setAttribute(Constants.CLAVE_ALMACENADA_KEY + "-" + tramiteInfo.getIdPersistencia(),"true");
		
		// Volcamos a fichero
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nombreFichero );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, datosFichero );
		return mapping.findForward( "success" );
	}

}