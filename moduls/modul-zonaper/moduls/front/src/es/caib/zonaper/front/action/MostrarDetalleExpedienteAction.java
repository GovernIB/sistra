package es.caib.zonaper.front.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ElementoExpedienteDelegate;
import es.caib.zonaper.persistence.delegate.ExpedienteDelegate;

/**
 * @struts.action
 *  path="/protected/mostrarDetalleExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".detalleExpediente"
 *  
 * @struts.action-forward
 *  name="successAnonimo" path=".detalleExpedienteAnonimo"
 *  
 */
public class MostrarDetalleExpedienteAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Long codigoExpediente = null;	
		
		// Obtenemos codigo expediente de la request
		String id = request.getParameter("id");
		
		// Si el id expediente es nulo obtenemos el ultimo mostrado
		if (StringUtils.isEmpty(id)){
			codigoExpediente = (Long) request.getSession().getAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE);			
		}else{
			codigoExpediente = new Long(id);
			request.getSession().setAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE,codigoExpediente);
		}
		
		// Si no hay codigo expediente generamos error
		if (codigoExpediente == null){
			throw new Exception("Falta parametro id");
		}
		
		// Obtenemos datos sesion
		DatosSesion datosSesion = this.getDatosSesion(request);
		
		// Obtenemos expediente
		Expediente expe;
		ExpedienteDelegate expedienteDelegate = DelegateUtil.getExpedienteDelegate();
			
		if (datosSesion.getNivelAutenticacion() == 'A'){
			expe = expedienteDelegate.obtenerExpedienteAnonimo(codigoExpediente,this.getIdPersistencia(request));
		}else{
			expe = expedienteDelegate.obtenerExpedienteAutenticado(codigoExpediente);
		}
		 
		// Establecemos atributos request
		establecerAtributosRequest(request, expe);		
		
		// Redirigimos segun nivel autenticacion
		if (datosSesion.getNivelAutenticacion() != 'A'){
			return mapping.findForward("success");
		}else{
			return mapping.findForward("successAnonimo");
		}
	}

	private void establecerAtributosRequest(HttpServletRequest request,
			Expediente expe) throws DelegateException {
		// Establecemos en la request los elementos pendientes
		DatosSesion datosSesion = this.getDatosSesion(request);
		// Codigo expediente
		String codigoExpe = null;
		// Descripcion expediente
		String descExpe;
		// Elementos expedientes
		Set elementosExpe;
		// Descripcion elemento expediente
		Map elementosExpeDescripcion = new HashMap();
		// Estado elementos expediente (key literal, vacio si no hay que mostrar estado)
		Map elementosExpeEstado = new HashMap();
		// Indica si el elemento del expediente se marca como novedad (true/false)
		Map elementosExpeNovedad = new HashMap();
		// Indica si hay que poner el pie de entregar doc presencial
		String pieDocPresencial = "N";
		
		
		elementosExpe = expe.getElementos();
		descExpe =  expe.getDescripcion();
		if (Expediente.TIPO_EXPEDIENTE_REAL.equals(expe.getTipoExpediente()) ) {
			codigoExpe= expe.getIdExpediente();
		}
		
		ElementoExpedienteDelegate ed = DelegateUtil.getElementoExpedienteDelegate();
		
		for (Iterator it=elementosExpe.iterator();it.hasNext();){
			ElementoExpediente elemento = (ElementoExpediente) it.next();
			
			ElementoExpedienteItf detalleElemento;
			
			if (datosSesion.getNivelAutenticacion() == 'A'){
				detalleElemento = ed.obtenerDetalleElementoExpedienteAnonimo(elemento.getCodigo(),this.getIdPersistencia(request));
			}else{
				detalleElemento = ed.obtenerDetalleElementoExpedienteAutenticado(elemento.getCodigo());
			}
			
			String novedad = "false";
			String keyEstado = "";
			String  descripcion= "";
			
			if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE)){
				descripcion = ((EventoExpediente) detalleElemento).getTitulo();
				if  (((EventoExpediente) detalleElemento).getFechaConsulta() == null){	
					novedad = "true";
					keyEstado = "detalleExpediente.aviso.estado.pendiente";							
				} else {
					keyEstado = "detalleExpediente.aviso.estado.recibido";		
				}
			}else if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION)){
				NotificacionTelematica notificacionTelematica = (NotificacionTelematica) detalleElemento;
				descripcion = notificacionTelematica.getTituloAviso();						
				if (notificacionTelematica.isRechazada()){
					novedad = "true";
					keyEstado = "detalleExpediente.notificacion.estado.rechazada";							
				} else if (notificacionTelematica.getFechaAcuse() == null){							
					novedad = "true";
					keyEstado = "detalleExpediente.notificacion.estado.pendiente";
				} else {
					keyEstado = "detalleExpediente.notificacion.estado.recibida";
				}
			}else if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO)){
				descripcion = ((Entrada) detalleElemento).getDescripcionTramite();
				if (((Entrada) detalleElemento).getNumeroRegistro() == null){
					novedad = "true";
					keyEstado = "detalleExpediente.tramite.estado.pendienteDocumentacionPresencial";							
					pieDocPresencial = "S";
				} else {
					keyEstado = "detalleExpediente.tramite.estado.enviada";							
				}
			}else if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA)){
				descripcion = ((Entrada) detalleElemento).getDescripcionTramite();
				keyEstado = "detalleExpediente.tramite.estado.enviada";
			}		
			
			elementosExpeDescripcion.put(elemento.getCodigo().toString(), descripcion);
			elementosExpeNovedad.put(elemento.getCodigo().toString(),novedad);
			elementosExpeEstado.put(elemento.getCodigo().toString(), keyEstado);
			
		}
		
		request.setAttribute("codigoExpediente",codigoExpe);
		request.setAttribute("descripcionExpediente",descExpe);
		request.setAttribute("elementosExpediente",elementosExpe);
		request.setAttribute("elementosExpeDescripcion",elementosExpeDescripcion);
		request.setAttribute("elementosExpeEstado",elementosExpeEstado);
		request.setAttribute("elementosExpeNovedad",elementosExpeNovedad);
		request.setAttribute("pieDocPresencial", pieDocPresencial );		
	}	
	
}
