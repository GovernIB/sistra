package es.caib.zonaper.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EventoExpedienteDelegate;

public class DetalleAvisoController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		// En caso de que este pendiente la marcamos como consultada
		EventoExpediente aviso = (EventoExpediente) request.getAttribute("aviso");
		if (aviso.getFechaConsulta() == null){
			EventoExpedienteDelegate ed = DelegateUtil.getEventoExpedienteDelegate();
			EventoExpediente avisoC;
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
				ed.marcarConsultadoEventoExpedienteAnonimo(aviso.getCodigo(),this.getIdPersistencia(request));
				avisoC = ed.obtenerEventoExpedienteAnonimo(aviso.getCodigo(),this.getIdPersistencia(request));
			}else{
				ed.marcarConsultadoEventoExpedienteAutenticado(aviso.getCodigo());
				avisoC = ed.obtenerEventoExpedienteAutenticado(aviso.getCodigo());
			}			
			
			request.setAttribute("aviso",avisoC);
		}
		
		// Info acerca codigo expediente y unidad administrativa
		ElementoExpediente elementoExpediente = (ElementoExpediente) request.getAttribute("elementoExpediente");
		request.setAttribute("codigoExpediente",elementoExpediente.getExpediente().getIdExpediente());
		request.setAttribute("descExpediente",elementoExpediente.getExpediente().getDescripcion());
		request.setAttribute( "unidadAdministrativa", DelegateUtil.getDominiosDelegate().obtenerDescripcionUA(elementoExpediente.getExpediente().getUnidadAdministrativa().toString()));
		
		
	}

}
