package es.caib.bantel.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.regtel.model.ReferenciaRDSAsientoRegistral;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.persistence.util.RegistroSalidaHelper;
import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;
/**
 * @struts.action
 * 	name="detalleNotificacionForm"
 *  path="/realizarAltaNotificacion"
 *  validate="true"
 *  input = ".altaNotificacion"
 *  
 * @struts.action-forward
 *  name="success" path=".recuperarExpediente"
 *
 * @struts.action-forward
 *  name="errorExtension" path=".altaNotificacion"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RealizarAltaNotificacionAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(RealizarAltaNotificacionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		ArrayList documentos;
		ExpedientePAD exp;
		
		// Recuperamos de sesion el expediente actual
		String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
		Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
		String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
		
		try{
			if(request.getSession().getAttribute("documentosAltaNotificacion") == null){
				documentos = new ArrayList();
			}else{
				documentos = (ArrayList)request.getSession().getAttribute("documentosAltaNotificacion");
			}
			RegistroSalidaHelper r = new RegistroSalidaHelper();
			r.setOficinaRegistro(notificacionForm.getOrganoDestino(),notificacionForm.getOficinaRegistro());
			r.setExpediente(uniAdm,idExpe,claveExpe);
			r.setDatosInteresado(notificacionForm.getNif(),notificacionForm.getApellidos(), StringUtils.isEmpty(notificacionForm.getUsuarioSey())?null:notificacionForm.getUsuarioSey(),notificacionForm.getCodigoPais(),notificacionForm.getNombrePais(),notificacionForm.getCodigoProvincia(),notificacionForm.getNombreProvincia(),notificacionForm.getCodigoMunicipio(),notificacionForm.getNombreMunicipio());
			r.setDatosNotificacion(notificacionForm.getIdioma(),notificacionForm.getTipoAsunto(),notificacionForm.getTituloAviso(),
					notificacionForm.getTextoAviso(),(StringUtils.isNotEmpty(notificacionForm.getTextoSmsAviso())?notificacionForm.getTextoSmsAviso():null),
					notificacionForm.getTituloOficio(),notificacionForm.getTextoOficio(),"S".equals(notificacionForm.getAcuse()),
					new Boolean("S".equals(notificacionForm.getAccesoPorClave())), new Boolean("S".equals(notificacionForm.getFirmaPorClave())));
			if("S".equals(notificacionForm.getTramiteSubsanacion())){
				Map parametros = null;
				if(request.getSession().getAttribute("parametrosAltaNotificacion") != null){
					parametros = (HashMap)request.getSession().getAttribute("parametrosAltaNotificacion");
				}
				r.setTramiteSubsanacion(notificacionForm.getDescripcionTramiteSubsanacion(),notificacionForm.getIdentificadorTramiteSubsanacion(),notificacionForm.getVersionTramiteSubsanacionInteger().intValue(),parametros);
			}
			if(documentos != null){
				
				for(int i=0;i<documentos.size();i++){
					ReferenciaRDS refRDS = new ReferenciaRDS(((DocumentoExpedientePAD)documentos.get(i)).getCodigoRDS(),((DocumentoExpedientePAD)documentos.get(i)).getClaveRDS());
					r.addDocumento(refRDS);					
				}
			}
			ReferenciaRDSAsientoRegistral ar = r.generarAsientoRegistral("LOCAL");
			ResultadoRegistroTelematico res = r.registrar("LOCAL",ar);
			
			request.getSession().setAttribute("documentosAltaNotificacion",null);
			request.getSession().setAttribute("parametrosAltaNotificacion",null);
			
			// Redirigimos a la consulta del expediente
			response.sendRedirect("recuperarExpediente.do?unidadAdm=" + uniAdm + "&identificadorExp=" + idExpe + "&claveExp=" + claveExpe );
			return null;
						
		}catch(Exception e){
			log.error("Excepcion alta notificacion",e);
			request.setAttribute( "enlace", "altaNotif");
			String mensajeOk = MensajesUtil.getValue("error.notificacio.Excepcion");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk + ": " + e.getMessage());
			return mapping.findForward("fail");
		}		
    }
		
}
