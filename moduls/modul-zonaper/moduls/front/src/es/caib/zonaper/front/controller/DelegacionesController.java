package es.caib.zonaper.front.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.util.DelegacionFront;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.Delegacion;
import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class DelegacionesController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		List delegaciones = new ArrayList();
		String nifFirmante = "";
		
		try{
			DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
			ConsultaPADDelegate consulta = DelegateUtil.getConsultaPADDelegate();
			
			if(request.getSession().getAttribute( Constants.DATOS_SESION_KEY ) != null){
				DatosSesion datosSes = (DatosSesion)request.getSession().getAttribute( Constants.DATOS_SESION_KEY );
				String nifEntidad = "";
				if (datosSes.getPerfilAcceso().equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO)){
					nifEntidad = datosSes.getNifEntidad();
					request.setAttribute("permisosEntidad",datosSes.getPermisosDelegacion());
				}else{
					nifEntidad = datosSes.getNifUsuario();
					request.setAttribute("permisosEntidad",es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD );
				}
				List delegacionesNorm = deleg.obtenerDelegacionesEntidad(nifEntidad);
				for(int i=0;i<delegacionesNorm.size();i++){
					DelegacionFront delFront = delToDelFront((Delegacion)delegacionesNorm.get(i));
					delFront.setNombreDelegado(consulta.obtenerDatosPADporNif(delFront.getNifDelegado()).getNombreCompleto());
					delegaciones.add(delFront);
				}
				
				nifFirmante = datosSes.getNifUsuario();
			}
			request.setAttribute("delegaciones",delegaciones);
		}catch(Exception e){
			request.setAttribute("delegaciones",delegaciones);
		}
		
		// Obtenemos descripcion Mi portal		 
		OrganismoInfo info = getOrganismoInfo(request);
		Locale locale = this.getLocale( request);
		request.setAttribute("tituloMiPortal",(String) info.getTituloPortal().get(locale.getLanguage()));

		// Nif firmante
		request.setAttribute("nifFirmante", nifFirmante);
	}
	
	private DelegacionFront delToDelFront(Delegacion del){
		DelegacionFront delFront = new DelegacionFront();
		delFront.setAnulada(del.getAnulada());
		delFront.setClaveRdsDocDelegacion(del.getClaveRdsDocDelegacion());
		delFront.setCodigo(del.getCodigo());
		delFront.setCodigoRdsDocDelegacion(del.getCodigoRdsDocDelegacion());
		delFront.setFechaFinDelegacion(del.getFechaFinDelegacion());
		delFront.setFechaInicioDelegacion(del.getFechaInicioDelegacion());
		delFront.setNifDelegado(del.getNifDelegado());
		delFront.setNifDelegante(del.getNifDelegante());
		delFront.setPermisos(del.getPermisos());
		return delFront;
	}
}
