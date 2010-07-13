package es.caib.bantel.front.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.front.util.ValorOrganismo;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.regtel.model.ReferenciaRDSAsientoRegistral;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
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
 *  name="successNoExp" path=".confirmacionRecuperacionExpediente"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RealizarAltaNotificacionAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		ArrayList documentos;
		ExpedientePAD exp;
		
		try{
			if(request.getSession().getAttribute("documentosAltaNotificacion") == null){
				documentos = new ArrayList();
			}else{
				documentos = (ArrayList)request.getSession().getAttribute("documentosAltaNotificacion");
			}
			RegistroSalidaHelper r = new RegistroSalidaHelper();
			r.setOficinaRegistro(notificacionForm.getOrganoDestino(),notificacionForm.getOficinaRegistro());
			r.setExpediente(Long.parseLong(notificacionForm.getUnidadAdministrativa()),notificacionForm.getIdentificadorExpediente(),notificacionForm.getClaveExpediente());
			r.setDatosInteresado(notificacionForm.getNif(),notificacionForm.getApellidos(), StringUtils.isEmpty(notificacionForm.getUsuarioSey())?null:notificacionForm.getUsuarioSey(),notificacionForm.getCodigoPais(),notificacionForm.getNombrePais(),notificacionForm.getCodigoProvincia(),notificacionForm.getNombreProvincia(),notificacionForm.getCodigoMunicipio(),notificacionForm.getNombreMunicipio());
			r.setDatosNotificacion(notificacionForm.getIdioma(),notificacionForm.getTipoAsunto(),notificacionForm.getTituloAviso(),notificacionForm.getTextoAviso(),(StringUtils.isNotEmpty(notificacionForm.getTextoSmsAviso())?notificacionForm.getTextoSmsAviso():null),notificacionForm.getTituloOficio(),notificacionForm.getTextoOficio(),"S".equals(notificacionForm.getAcuse()));
				
			if(documentos != null){
				
				for(int i=0;i<documentos.size();i++){
					ReferenciaRDS refRDS = new ReferenciaRDS(((DocumentoExpedientePAD)documentos.get(i)).getCodigoRDS(),((DocumentoExpedientePAD)documentos.get(i)).getClaveRDS());
					r.addDocumento(refRDS);					
				}
			}
			ReferenciaRDSAsientoRegistral ar = r.generarAsientoRegistral("LOCAL");
			ResultadoRegistroTelematico res = r.registrar("LOCAL",ar);
			if(notificacionForm.getClaveExpediente()!=null && !"".equals(notificacionForm.getClaveExpediente())){
				exp = ejb.consultaExpediente( new Long(notificacionForm.getUnidadAdministrativa()), notificacionForm.getIdentificadorExpediente(),notificacionForm.getClaveExpediente());
			}else{
				exp = ejb.consultaExpediente( new Long(notificacionForm.getUnidadAdministrativa()), notificacionForm.getIdentificadorExpediente());
			}
			request.getSession().setAttribute("documentosAltaNotificacion",null);
			
			
			// Redirigimos a la consulta del expediente
			response.sendRedirect("recuperarExpediente.do?unidadAdm=" + exp.getUnidadAdministrativa() + "&identificadorExp=" + exp.getIdentificadorExpediente() + ( exp.getClaveExpediente() != null?"&claveExp=" + exp.getClaveExpediente():"") );
			return null;
			
			/*
			if(exp != null){
				request.setAttribute("expediente", exp);
			}else{
				return mapping.findForward("successNoExp");
			}
			*/
		}catch(Exception e){
			request.setAttribute( "enlace", "altaNotif");
			String mensajeOk = MensajesUtil.getValue("error.notificacio.Excepcion");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
		
		/*
		ActionMessages mensajes = new ActionMessages();
		mensajes.add("msg", new ActionMessage("altaNotificacionOk"));
		saveMessages(request,mensajes);
		return mapping.findForward( "success" );
		*/
    }
	
	private void carregarLlistes(HttpServletRequest request, String provincia) throws Exception{
		List unidades=Dominios.listarUnidadesAdministrativas();
		request.setAttribute("unidades",unidades);
		List paises = Dominios.listarPaises();
		request.setAttribute("paises",paises);
		List provincias = Dominios.listarProvincias();
		request.setAttribute("provincias",provincias);
		List municipios = new ArrayList();
		if(StringUtils.isNotEmpty(provincia)){
			municipios = Dominios.listarLocalidadesProvincia(provincia);
		}
		request.setAttribute("municipios",municipios);
		RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
        List organosDestino = dlgRte.obtenerServiciosDestino();
        request.setAttribute( "listaorganosdestino", regtelToBantel(organosDestino));
        List oficinasRegistro = dlgRte.obtenerOficinasRegistro();
        request.setAttribute( "listaoficinasregistro", regtelToBantel(oficinasRegistro));
        List tiposAsunto = dlgRte.obtenerTiposAsunto();
        request.setAttribute("tiposAsunto", regtelToBantel(tiposAsunto));
	}
	
	private List regtelToBantel(List lista){
		List listaBantel = new ArrayList();
		if(lista != null){
			for(int i=0;i<lista.size();i++){
				ValorOrganismo vo = new ValorOrganismo();
				vo.setCodigo(((es.caib.regtel.model.ValorOrganismo)lista.get(i)).getCodigo());
				vo.setDescripcion(((es.caib.regtel.model.ValorOrganismo)lista.get(i)).getDescripcion());
				listaBantel.add(vo);
			}
		}
		return listaBantel;
	}
}
