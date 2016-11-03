package es.caib.zonaper.front.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

public class TramitesSinEnviarController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		PadDelegate zonaPersonalDelegate = DelegatePADUtil.getPadDelegate();
		ConsultaPADDelegate consultaPAD = DelegateUtil.getConsultaPADDelegate();
		
		DatosSesion datosSesion = this.getDatosSesion(request);
		
		List lResult = null;
		
		if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(datosSesion.getPerfilAcceso())){
			// Obtenemos tramites persistentes asociados a la entidad delegada	
			lResult = zonaPersonalDelegate.obtenerTramitesPersistentesEntidadDelegada(datosSesion.getNifEntidad());
		}else{			
		// Obtenemos tramites persistentes asociados al usuario	
			lResult = zonaPersonalDelegate.obtenerTramitesPersistentesUsuario();
		}
		
		request.setAttribute( "tramitesPersistentes", lResult );
		
		// Obtenemos datos usuarios implicados en los trámites persistentes (blindamos ante posible error de que el usuario no este en PAD)
		HashMap usu = new HashMap();		
		PersonaPAD pers,persNoRegistrado;
		persNoRegistrado = new PersonaPAD();
		persNoRegistrado.setNombre("Usuario no registrado");
		persNoRegistrado.setNif("");
		for (int i=0;i<lResult.size();i++){
			TramitePersistentePAD t = (TramitePersistentePAD) lResult.get(i);
			if (t.getUsuario() != null && !usu.containsKey(t.getUsuario())){
				pers=consultaPAD.obtenerDatosPADporUsuarioSeycon(t.getUsuario());
				if (pers == null) pers = persNoRegistrado;
				usu.put(t.getUsuario(),pers);
			}
			if (t.getUsuarioFlujoTramitacion() != null && !usu.containsKey(t.getUsuarioFlujoTramitacion())) {
				pers=consultaPAD.obtenerDatosPADporUsuarioSeycon(t.getUsuarioFlujoTramitacion());
				if (pers == null) pers = persNoRegistrado;
				usu.put(t.getUsuarioFlujoTramitacion(),pers);
			}
		}
		request.setAttribute( "usuarios", usu );
		
		
		int numeroDeTramitesInacabados = lResult.size();
		if ( numeroDeTramitesInacabados > 0 )
		{
			request.setAttribute( "numeroDeTramitesSinEnviar", String.valueOf( numeroDeTramitesInacabados ) );
		}
	}

}
