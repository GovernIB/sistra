package es.caib.bantel.front.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.ValorOrganismo;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.ConfiguracionDelegate;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.sistra.plugins.regtel.ConstantesPluginRegistro;

public class AltaNotificacionController extends BaseController
{
		
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		// Indicamos si expe es autenticado
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm) request.getAttribute("detalleNotificacionForm");
		request.setAttribute("autenticado",Boolean.toString(StringUtils.isNotEmpty(notificacionForm.getUsuarioSey())));
		
		// Cargamos listas valores
		carregarLlistes(request);
		
		// Establecemos url back
		String urlSistra = "";
		try{
			ConfiguracionDelegate delegate = DelegateUtil.getConfiguracionDelegate();
			urlSistra = delegate.obtenerConfiguracion().getProperty("sistra.url.back");
		}catch (Exception e) {
			urlSistra = "";
		}
		request.setAttribute( "urlSistraAFirma", urlSistra );
		
		// Comprobamos si puede cambiar el plazo de notificaciones
		String idProcedimiento = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_PROCEDIMIENTO_KEY);
		Procedimiento p = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(idProcedimiento);
		request.setAttribute("permitirPlazoNotificacionesVariable", p != null ? p.getPermitirPlazoNotificacionesVariable() : "N");		
	}

	
	private void carregarLlistes(HttpServletRequest request) throws Exception{
		
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm) request.getAttribute("detalleNotificacionForm");
		
		List unidades=Dominios.listarUnidadesAdministrativas();
		request.setAttribute("unidades",unidades);
		List paises = Dominios.listarPaises();
		request.setAttribute("paises",paises);
		List provincias = Dominios.listarProvincias();
		request.setAttribute("provincias",provincias);
		List municipios = Dominios.listarLocalidadesProvincia(notificacionForm.getCodigoProvincia());
		request.setAttribute("municipios",municipios);
		RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
        List organosDestino = dlgRte.obtenerServiciosDestino();
        request.setAttribute( "listaorganosdestino", regtelToBantel(organosDestino));
        List oficinasRegistro = dlgRte.obtenerOficinasRegistro(ConstantesPluginRegistro.REGISTRO_ENTRADA);
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