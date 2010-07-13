package es.caib.bantel.front.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.front.util.ValorOrganismo;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
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
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute("documentosAltaNotificacion",null);
		MensajesUtil.setMsg(this.getResources(request));
		ExpedientePAD exp;
		try{
			if(notificacionForm.getClaveExpediente()!=null && !"".equals(notificacionForm.getClaveExpediente())){
				exp = ejb.consultaExpediente( new Long(notificacionForm.getUnidadAdministrativa()), notificacionForm.getIdentificadorExpediente(),notificacionForm.getClaveExpediente());
			}else{
				exp = ejb.consultaExpediente( new Long(notificacionForm.getUnidadAdministrativa()), notificacionForm.getIdentificadorExpediente());
			}
			if(exp != null){
				notificacionForm.setDescripcionExpediente(exp.getDescripcion());
				notificacionForm.setUsuarioSey(exp.getIdentificadorUsuario());
				notificacionForm.setIdiomaExp(exp.getIdioma());
				PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(exp.getIdentificadorUsuario());	
				notificacionForm.setNif(p.getNif());
				notificacionForm.setApellidos(p.getNombreCompleto());
				carregarLlistes(request);
			}else{
				return mapping.findForward("fail");
			}
		}catch(Exception e){
			String mensajeOk = MensajesUtil.getValue("error.notificacio.Excepcion");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
		return mapping.findForward( "success" );
    }
	
	private void carregarLlistes(HttpServletRequest request) throws Exception{
		List unidades=Dominios.listarUnidadesAdministrativas();
		request.setAttribute("unidades",unidades);
		List paises = Dominios.listarPaises();
		request.setAttribute("paises",paises);
		List provincias = Dominios.listarProvincias();
		request.setAttribute("provincias",provincias);
		List municipios = new ArrayList();
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
