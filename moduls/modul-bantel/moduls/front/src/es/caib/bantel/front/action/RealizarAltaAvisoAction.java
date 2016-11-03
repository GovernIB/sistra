package es.caib.bantel.front.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;
import es.caib.zonaper.modelInterfaz.EventoExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  name="detalleAvisoForm"
 *  path="/realizarAltaAviso"
 *  validate="true"
 *  input=".altaAviso"
 *  
 * @struts.action-forward
 *  name="success" path=".recuperarExpediente"
 *
 * @struts.action-forward
 *  name="errorExtensiones" path=".altaAviso"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RealizarAltaAvisoAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(RealizarAltaAvisoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		DetalleAvisoForm avisoForm = (DetalleAvisoForm)form;
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		ArrayList documentos;
		EventoExpedientePAD eventoExpediente = new EventoExpedientePAD();
		DocumentoRDS documentRDS = new DocumentoRDS();
		
		// Recuperamos de sesion el expediente actual
		String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
		Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
		String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
		
		try{
			if(request.getSession().getAttribute("documentosAltaAviso") == null){
				documentos = new ArrayList();
			}else{
				documentos = (ArrayList)request.getSession().getAttribute("documentosAltaAviso");
			}
			if(documentos != null){
				for(int i=0;i<documentos.size();i++){
					DocumentoExpedientePAD docExpPAD = (DocumentoExpedientePAD)documentos.get(i);
					eventoExpediente.addDocumento(docExpPAD);
				}
			}
			eventoExpediente.setTitulo(avisoForm.getTitulo());
			eventoExpediente.setTexto(avisoForm.getTexto());
			eventoExpediente.setTextoSMS(avisoForm.getTextoSMS());
			eventoExpediente.setAccesiblePorClave(new Boolean("S".equals(avisoForm.getAccesoPorClave())));

			// Alta del aviso
			ejb.altaEvento(uniAdm, idExpe, claveExpe, eventoExpediente );
			
			// Redirigimos a consulta expediente
			response.sendRedirect("recuperarExpediente.do?unidadAdm=" + uniAdm + "&identificadorExp=" + idExpe + "&claveExp=" + claveExpe );
			return null;
						
		}catch(Exception e){
			log.error("Excepcion realizando alta aviso",e);
			request.setAttribute( "enlace", "altaAviso");
			String mensajeOk = MensajesUtil.getValue("error.aviso.Excepcion", request) + ": " + e.getMessage();
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
    }

}
