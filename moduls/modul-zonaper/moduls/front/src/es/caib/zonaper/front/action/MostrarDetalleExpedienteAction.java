package es.caib.zonaper.front.action;

import java.util.HashMap;
import java.util.HashSet;
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
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ElementoExpedienteDelegate;

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
 *  
 *  
 */
public class MostrarDetalleExpedienteAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		
		DatosSesion datosSesion = this.getDatosSesion(request);
		
		boolean accesoDelegado = (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals( datosSesion.getPerfilAcceso()));
		
		// Cargamos estado expediente
		String id = request.getParameter("id");
		
		// Si el id expediente es nulo obtenemos el ultimo mostrado
		if (StringUtils.isEmpty(id)){
			id = (String) request.getSession().getAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE);
		}else{
			request.getSession().setAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE,id);
		}
		
		if (StringUtils.isEmpty(id)){
			throw new Exception("Falta parametro id");
		}
		
		String tipoElemento   = id.substring(0,1);
		Long codigoElemento =  new Long(id.substring(1)); 
		
		// Si es un tramite (telematico/preregistro) comprobamos que si existe a un expediente
		// Si es así, mostramos el expediente
		if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(tipoElemento) || ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(tipoElemento)){
			ElementoExpediente e;
			if (datosSesion.getNivelAutenticacion() == 'A'){
				e = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(tipoElemento,codigoElemento,this.getIdPersistencia(request));
			}else{
				e = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(tipoElemento,codigoElemento);
			}
			
			// Si pertenece a un expediente, mostraremos el expediente
			if (e != null){
				tipoElemento = EstadoExpediente.TIPO_EXPEDIENTE; 
				codigoElemento = e.getExpediente().getCodigo();
			}
			
		}
		
		
		// Comprobamos si es un tramite sin expediente y generamos un ElementoExpediente virtual
		ElementoExpedienteDelegate ed = DelegateUtil.getElementoExpedienteDelegate();
		if (tipoElemento.equals(EstadoExpediente.TIPO_ENTRADA_PREREGISTRO)){
				// Tramite preregistro sin expediente
				EntradaPreregistro entradaPre;
				if (datosSesion.getNivelAutenticacion() == 'A'){
					entradaPre = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAnonima(codigoElemento,this.getIdPersistencia(request));
				}else{
					entradaPre = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAutenticada(codigoElemento);
				}				
								
				ElementoExpediente elemento = new ElementoExpediente();
				elemento.setCodigo(new Long(0)); // SOLO HABRA 1 ELEMENTO, EL PROPIO TRAMITE
				elemento.setCodigoElemento(entradaPre.getCodigo());
				elemento.setExpediente(null);
				elemento.setFecha(entradaPre.getFecha());
				elemento.setTipoElemento(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO);
				
				Set elementos = new HashSet();
				elementos.add(elemento);
				
				elementosExpe = elementos;
				descExpe = entradaPre.getDescripcionTramite();
				elementosExpeDescripcion.put(elemento.getCodigo().toString(),entradaPre.getDescripcionTramite());
				
				
				// Comprobamos si no esta confirmada
				if (entradaPre.getNumeroRegistro() == null){
					elementosExpeNovedad.put(elemento.getCodigo().toString(),"true");
					elementosExpeEstado.put(elemento.getCodigo().toString(), "detalleExpediente.tramite.estado.pendienteDocumentacionPresencial");
					pieDocPresencial = "S";
				} else {
					elementosExpeNovedad.put(elemento.getCodigo().toString(),"false");
					elementosExpeEstado.put(elemento.getCodigo().toString(), "detalleExpediente.tramite.estado.enviada");
				}
				
		}else if (tipoElemento.equals(EstadoExpediente.TIPO_ENTRADA_TELEMATICA)){			
				// Tramite telematico sin expediente
				EntradaTelematica entradaTel;
				if (datosSesion.getNivelAutenticacion() == 'A'){
					entradaTel = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAnonima(codigoElemento,this.getIdPersistencia(request));
				}else{
					entradaTel = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAutenticada(codigoElemento);
				}
				
				ElementoExpediente elemento = new ElementoExpediente();
				elemento.setCodigo(new Long(0)); // SOLO HABRA 1 ELEMENTO, EL PROPIO TRAMITE
				elemento.setCodigoElemento(entradaTel.getCodigo());
				elemento.setExpediente(null);
				elemento.setFecha(entradaTel.getFecha());
				elemento.setTipoElemento(ElementoExpediente.TIPO_ENTRADA_TELEMATICA);
				
				Set elementos = new HashSet();
				elementos.add(elemento);
				
				elementosExpe = elementos;
				descExpe = entradaTel.getDescripcionTramite();
				elementosExpeDescripcion.put(elemento.getCodigo().toString(),entradaTel.getDescripcionTramite());
				elementosExpeNovedad.put(elemento.getCodigo().toString(),"false");
				elementosExpeEstado.put(elemento.getCodigo().toString(), "detalleExpediente.tramite.estado.enviada");
		}else if (tipoElemento.equals(EstadoExpediente.TIPO_EXPEDIENTE)){
				// Expediente
				Expediente expe;
				if (datosSesion.getNivelAutenticacion() == 'A'){
					expe = DelegateUtil.getExpedienteDelegate().obtenerExpedienteAnonimo(codigoElemento,this.getIdPersistencia(request));
				}else{
					expe = DelegateUtil.getExpedienteDelegate().obtenerExpedienteAutenticado(codigoElemento);
				}
				 
				elementosExpe = expe.getElementos();
				descExpe =  expe.getDescripcion();		
				codigoExpe= expe.getIdExpediente();
				
				// Buscamos elementos pendientes				
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
				
		}else{
			throw new Exception("Tipo de elemento no permitido " + tipoElemento);
		}
		
		
		request.setAttribute("codigoExpediente",codigoExpe);
		request.setAttribute("descripcionExpediente",descExpe);
		request.setAttribute("elementosExpediente",elementosExpe);
		request.setAttribute("elementosExpeDescripcion",elementosExpeDescripcion);
		request.setAttribute("elementosExpeEstado",elementosExpeEstado);
		request.setAttribute("elementosExpeNovedad",elementosExpeNovedad);
		request.setAttribute("pieDocPresencial", pieDocPresencial );		
		
		if (datosSesion.getNivelAutenticacion() != 'A'){
			return mapping.findForward("success");
		}else{
			return mapping.findForward("successAnonimo");
		}
	}	
	
}
