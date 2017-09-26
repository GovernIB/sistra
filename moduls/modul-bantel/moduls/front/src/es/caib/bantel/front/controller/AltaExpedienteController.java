package es.caib.bantel.front.controller;

import java.util.List;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.Globals;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.front.json.Pais;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.model.Procedimiento;

public class AltaExpedienteController extends BaseController
{
		
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		DetalleExpedienteForm notificacionForm = (DetalleExpedienteForm) request.getAttribute("detalleExpedienteForm");
		if (StringUtils.isNotEmpty(notificacionForm.getNumeroEntrada())) {
			request.setAttribute("existeEntrada","S");
		}
		
		/*
		// Combo unidades administrativas
		List unidades=Dominios.listarUnidadesAdministrativas();
		request.setAttribute("unidades",unidades);
		*/
		
		List paises = Dominios.listarPaises();
		// Se a�ade la opci�n vacia al listado
		Pais p = new Pais();
		p.setCodigo("");
		p.setDescripcion("");
		paises.add(0, p);
		
		
		request.setAttribute("paises",paises);
		
		// Combo procedimientos gestor
		GestorBandeja gestor = DelegateUtil.getGestorBandejaDelegate().obtenerGestorBandeja(this.getPrincipal(request).getName());
		
		Locale local = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
		
		Set procs = gestor.getProcedimientosGestionados();
		
		for (Iterator it = procs.iterator(); it.hasNext();) {
        	Procedimiento proc = (Procedimiento) it.next();
        	proc.setCurrentLang(local.getLanguage());
        }		
		
		request.setAttribute("procedimientosGestor", gestor.getProcedimientosGestionados());
		
		// Indica si son obligatorios los avisos en la creacion del expediente
		request.setAttribute("obligatorioAvisos", request.getSession().getServletContext().getAttribute(Constants.GESTIONEXPEDIENTES_OBLIGATORIOAVISOS));
		
	}

}