package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  name="detalleExpedienteForm"
 *  path="/realizarAltaExpediente"
 *  validate="true"
 *  input = ".altaExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".recuperarExpediente"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RealizarAltaExpedienteAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleExpedienteForm expedienteForm = (DetalleExpedienteForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
				
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute("nombreUnidad",expedienteForm.getNombreUnidad());
		try{
			ExpedientePAD expediente = new ExpedientePAD();
			expediente.setDescripcion(expedienteForm.getDescripcion());
			expediente.setIdioma(expedienteForm.getIdioma());
			expediente.setIdentificadorExpediente(expedienteForm.getIdentificadorExp());
			expediente.setClaveExpediente(expedienteForm.getClaveExp());
			if(StringUtils.isNotEmpty(expedienteForm.getNif()) && StringUtils.isNotEmpty(expedienteForm.getNombre())){
				PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(expedienteForm.getNif());	
				if(p!=null){//&& StringUtils.equals(expedienteForm.getNombre(),p.getNombre()) || StringUtils.equals(expedienteForm.getNombre(),p.getNombreCompleto())
					expediente.setAutenticado(true);
					expedienteForm.setUsuarioSeycon(p.getUsuarioSeycon());
				}else{
					throw new Exception("Usuario inexistente");
				}

			}else{
				expediente.setAutenticado(StringUtils.isNotEmpty(expedienteForm.getUsuarioSeycon()));
			}
			if (expediente.isAutenticado()){
				expediente.setIdentificadorUsuario(expedienteForm.getUsuarioSeycon());
			}
			expediente.setUnidadAdministrativa( new Long( expedienteForm.getUnidadAdm() ) );
			expediente.setNumeroEntradaBTE(expedienteForm.getNumeroEntrada());
			if(!StringUtils.isEmpty(expedienteForm.getHabilitarAvisos()) ){
				expediente.getConfiguracionAvisos().setHabilitarAvisos(new Boolean(expedienteForm.getHabilitarAvisos().equals("S")));
				expediente.getConfiguracionAvisos().setAvisoEmail(expedienteForm.getEmail());
				expediente.getConfiguracionAvisos().setAvisoSMS(expedienteForm.getMovil());
			}
			ejb.altaExpediente(expediente);
			
			// Redirigimos a consulta expediente
			response.sendRedirect("recuperarExpediente.do?unidadAdm=" + expediente.getUnidadAdministrativa() + "&identificadorExp=" + expediente.getIdentificadorExpediente() + ( expediente.getClaveExpediente() != null?"&claveExp=" + expediente.getClaveExpediente():"") );
			return null;
			
			/*
			ActionMessages mensajes = new ActionMessages();
			mensajes.add("msg", new ActionMessage("altaExpedienteOk"));
			saveMessages(request,mensajes);
			//request.setAttribute("expediente", expediente);
			
			return mapping.findForward( "success" );
			*/
		}catch(Exception e){
			String mensajeOk = "";
			
			if(e.getMessage().equals("Usuario inexistente")){
				mensajeOk = MensajesUtil.getValue("error.expediente.UsuarioInexistente");
			}else if(e.getMessage().equals("La entrada ya tiene un expediente enlazado")){
				mensajeOk = MensajesUtil.getValue("error.expediente.ExpedienteEnlazado");
			}else{
				mensajeOk = MensajesUtil.getValue("error.expediente.Excepcion");
			}
			
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			request.setAttribute( "enlace","errorExpediente");
			return mapping.findForward("fail");
		}
    }
}

