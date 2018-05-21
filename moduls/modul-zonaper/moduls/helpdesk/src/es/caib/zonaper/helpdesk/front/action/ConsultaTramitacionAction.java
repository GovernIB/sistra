package es.caib.zonaper.helpdesk.front.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.delegate.AuditaDelegate;
import es.caib.audita.persistence.delegate.DelegateAUDUtil;
import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.ConsultaTramitacionForm;
import es.caib.zonaper.helpdesk.front.plugins.PluginAudita;
import es.caib.zonaper.helpdesk.front.util.LangUtil;
import es.caib.zonaper.helpdesk.front.util.Util;
import es.caib.zonaper.model.LogTramitacion;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;


/**
 * @struts.action
 *  name="consultaTramitacionForm"
 *  path="/consultaTramitacion"
 *  scope="request"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".busqueda"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class ConsultaTramitacionAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(ConsultaTramitacionAction.class);
	

	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		ConsultaTramitacionForm formularioConsulta = ( ConsultaTramitacionForm ) form;
		
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.AUDITORIA_TAB);		

		AuditaDelegate delegate = DelegateAUDUtil.getAuditaDelegate();
		
		String autenticacion = null;
		
		log.debug("Nivel de Autenticacion: " + formularioConsulta.getNivelAutenticacion());
		
		request.setAttribute("nivel", Character.toString(formularioConsulta.getNivelAutenticacion()));

		Date fechaInicial = null;
		Date fechaFinal = null;
		if(formularioConsulta.getNivelAutenticacion() == Constants.MODO_AUTENTICACION_ANONIMO)
		{
			autenticacion = formularioConsulta.getClavePersistencia();
		}else
		{
			PersonaPAD persona = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(formularioConsulta.getUsuarioNif());
			if(persona == null)
			{
				return mapping.findForward( "success" );
			}
			autenticacion = persona.getUsuarioSeycon();
			log.debug("Fecha: " + formularioConsulta.getFecha() + " HoraInicial: " +
					  formularioConsulta.getHoraInicial() + " HoraFinal: " +
					  formularioConsulta.getHoraFinal());
			
			SimpleDateFormat df = new SimpleDateFormat(Constants.FORMATO_FECHAS);
			String fecha = formularioConsulta.getFecha();
			
			
			fechaInicial = df.parse(fecha + " " + formularioConsulta.getHoraInicial()+ ":" +
					                     formularioConsulta.getMinInicial());
			fechaFinal = df.parse(fecha + " " + formularioConsulta.getHoraFinal() + ":" +
					                   formularioConsulta.getMinFinal());
			
		}
		
		List logs = delegate.obtenerAuditoria(fechaInicial,fechaFinal,
											  formularioConsulta.getNivelAutenticacion(),
											  autenticacion);
		
		log.debug("Recibimos " + logs.size() + " logs");
		
		Map descTramites = PluginAudita.getInstance().obtenerDescripcionTramites(LangUtil.getLang(request));
		String descTramite = "";
		Map tiposEvento = PluginAudita.getInstance().obtenerTiposEventos(LangUtil.getLang(request));
		
		List logsTramitacion = new ArrayList();
		
		for(Iterator it = logs.iterator(); it.hasNext();)
		{
			Evento evento = (Evento)it.next();
			if(!formularioConsulta.getIdioma().equals(Constants.TODOS) && !evento.getIdioma().equals(formularioConsulta.getIdioma())) continue;
			LogTramitacion logTramitacion = new LogTramitacion();
			logTramitacion.setClavePersistencia(evento.getIdPersistencia());
			descTramite = (String) descTramites.get(evento.getModeloTramite());
			if(descTramite == null) {
				descTramite = evento.getModeloTramite();
			}else{
				descTramite = evento.getModeloTramite() + "-" + descTramite;
			}
			logTramitacion.setDescTramite(descTramite);
			logTramitacion.setHora(Util.convertDate(evento.getFecha()));
			logTramitacion.setTipoEvento((String) tiposEvento.get(evento.getTipo()));
			logTramitacion.setIdioma(evento.getIdioma());
			logTramitacion.setClave(evento.getClave()!=null?evento.getClave():"");
			logsTramitacion.add(logTramitacion);
		}
		
		request.setAttribute( "logs", logsTramitacion );
		
		return mapping.findForward( "success" );
    }
	
	
	
}
