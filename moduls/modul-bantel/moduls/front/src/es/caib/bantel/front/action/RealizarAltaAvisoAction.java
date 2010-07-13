package es.caib.bantel.front.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;
import es.caib.zonaper.modelInterfaz.EventoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
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
 *  name="successNoExp" path=".confirmacionRecuperacionExpediente"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RealizarAltaAvisoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		DetalleAvisoForm avisoForm = (DetalleAvisoForm)form;
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		ArrayList documentos;
		EventoExpedientePAD eventoExpediente = new EventoExpedientePAD();
		ExpedientePAD exp;
		
		DocumentoRDS documentRDS = new DocumentoRDS();
		
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
			//alta del aviso
			ejb.altaEvento(Long.parseLong(avisoForm.getUnidadAdministrativa()), avisoForm.getIdentificadorExpediente(),avisoForm.getClaveExpediente(), eventoExpediente );
			//retorna los datos del expediente
			if(avisoForm.getClaveExpediente()!=null && !"".equals(avisoForm.getClaveExpediente())){
				exp = ejb.consultaExpediente( new Long(avisoForm.getUnidadAdministrativa()), avisoForm.getIdentificadorExpediente(),avisoForm.getClaveExpediente());
			}else{
				exp = ejb.consultaExpediente( new Long(avisoForm.getUnidadAdministrativa()), avisoForm.getIdentificadorExpediente());
			}
			
			// Redirigimos a consulta expediente
			response.sendRedirect("recuperarExpediente.do?unidadAdm=" + exp.getUnidadAdministrativa() + "&identificadorExp=" + exp.getIdentificadorExpediente() + ( exp.getClaveExpediente() != null?"&claveExp=" + exp.getClaveExpediente():"") );
			return null;
			
			/*
			request.getSession().setAttribute("documentosAltaAviso",null);
			if(exp != null){
				request.setAttribute("expediente", exp);
			}else{
				String mensajeOk = MensajesUtil.getValue("error.noExpediente");
				request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
				return mapping.findForward("successNoExp");
			}
			ActionMessages mensajes = new ActionMessages();
			mensajes.add("msg", new ActionMessage("altaAvisoOk"));
			saveMessages(request,mensajes);
				
			return mapping.findForward( "success" );
			*/
		}catch(Exception e){
			request.setAttribute( "enlace", "altaAviso");
			String mensajeOk = MensajesUtil.getValue("error.aviso.Excepcion");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
    }

}
