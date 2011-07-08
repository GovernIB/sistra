package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 * 	name="detalleNotificacionForm"
 *  path="/altaNotificacion"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".altaNotificacion"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaNotificacionAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(AltaNotificacionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute("documentosAltaNotificacion",null);
		request.getSession().setAttribute("parametrosAltaNotificacion",null);
		MensajesUtil.setMsg(this.getResources(request));
		ExpedientePAD exp;
		try{
			
			// Recuperamos de sesion el expediente actual
			String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
			Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
			String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
			
			// Recuperamos expediente
			exp = ejb.consultaExpediente(uniAdm, idExpe,claveExpe);
						
			if(exp != null){
				notificacionForm.setDescripcionExpediente(exp.getDescripcion());
				notificacionForm.setIdiomaExp(exp.getIdioma());
				
				// Si expediente es autenticado recuperamos datos persona
				if (exp.isAutenticado()){
					PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(exp.getNifRepresentante());
					if (p == null){
						throw new Exception("No se encuentra usuario en la tabla de personas asociado a usuario: " + exp.getIdentificadorUsuario());
					}
					notificacionForm.setUsuarioSey(exp.getIdentificadorUsuario());
					notificacionForm.setNif(p.getNif());
					//notificacionForm.setApellidos(p.getNombreCompleto());
					notificacionForm.setApellidos(p.getApellidosNombre());
					notificacionForm.setAcuse(p.getApellidosNombre());
					notificacionForm.setIdioma(exp.getIdioma());
				}
				
				return mapping.findForward( "success" );
				
			}else{
				throw new Exception("No se ha encontrado expediente");
			}
			
		}catch(Exception e){
			log.error("Excepcion mostrando alta notificacion",e);
			String mensajeOk = MensajesUtil.getValue("error.notificacio.Excepcion");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}		
    }
	
	
}
