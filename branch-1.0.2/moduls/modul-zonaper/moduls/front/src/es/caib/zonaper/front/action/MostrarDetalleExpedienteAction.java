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
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.NotificacionTelematica;
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
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
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
		
		
		
		Set elementosExpe;
		String descExpe;
		String codigoExpe = null;
		Set elementosExpePendientes = new HashSet();
		Map elementosExpeDescripcion = new HashMap();
		ElementoExpedienteDelegate ed = DelegateUtil.getElementoExpedienteDelegate();
			
		if (tipoElemento.equals(EstadoExpediente.TIPO_ENTRADA_PREREGISTRO)){
				// Tramite preregistro sin expediente
				EntradaPreregistro entradaPre;
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					entradaPre = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAnonima(codigoElemento,this.getIdPersistencia(request));
				}else{
					entradaPre = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAutenticada(codigoElemento);
				}				
								
				ElementoExpediente elemento = new ElementoExpediente();
				elemento.setCodigo(null);
				elemento.setCodigoElemento(entradaPre.getCodigo());
				elemento.setExpediente(null);
				elemento.setFecha(entradaPre.getFecha());
				elemento.setTipoElemento(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO);
				
				Set elementos = new HashSet();
				elementos.add(elemento);
				
				elementosExpe = elementos;
				descExpe = entradaPre.getDescripcionTramite();
				elementosExpeDescripcion.put(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO + entradaPre.getCodigo(),entradaPre.getDescripcionTramite());
				
				
		}else if (tipoElemento.equals(EstadoExpediente.TIPO_ENTRADA_TELEMATICA)){			
				// Tramite telematico sin expediente
				EntradaTelematica entradaTel;
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					entradaTel = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAnonima(codigoElemento,this.getIdPersistencia(request));
				}else{
					entradaTel = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAutenticada(codigoElemento);
				}
				
				ElementoExpediente elemento = new ElementoExpediente();
				elemento.setCodigo(null);
				elemento.setCodigoElemento(entradaTel.getCodigo());
				elemento.setExpediente(null);
				elemento.setFecha(entradaTel.getFecha());
				elemento.setTipoElemento(ElementoExpediente.TIPO_ENTRADA_TELEMATICA);
				
				Set elementos = new HashSet();
				elementos.add(elemento);
				
				elementosExpe = elementos;
				descExpe = entradaTel.getDescripcionTramite();
				elementosExpeDescripcion.put(ElementoExpediente.TIPO_ENTRADA_TELEMATICA + entradaTel.getCodigo(),entradaTel.getDescripcionTramite());
		}else if (tipoElemento.equals(EstadoExpediente.TIPO_EXPEDIENTE)){
				// Expediente
				Expediente expe;
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
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
					if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
						detalleElemento = ed.obtenerDetalleElementoExpedienteAnonimo(elemento.getCodigo(),this.getIdPersistencia(request));
					}else{
						detalleElemento = ed.obtenerDetalleElementoExpedienteAutenticado(elemento.getCodigo());
					}
					
					if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE)){
						elementosExpeDescripcion.put(elemento.getTipoElemento() + elemento.getCodigoElemento(), ((EventoExpediente) detalleElemento).getTitulo());						
						if  (((EventoExpediente) detalleElemento).getFechaConsulta() == null){
							elementosExpePendientes.add(elemento.getCodigo());
						}
					}else if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION)){
						elementosExpeDescripcion.put(elemento.getTipoElemento() + elemento.getCodigoElemento(), ((NotificacionTelematica) detalleElemento).getTituloAviso());
						if (((NotificacionTelematica) detalleElemento).getFechaAcuse() == null){							
							elementosExpePendientes.add(elemento.getCodigo());
						}
					}else if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO)){
						elementosExpeDescripcion.put(elemento.getTipoElemento() + elemento.getCodigoElemento(), ((Entrada) detalleElemento).getDescripcionTramite());
					}else if (elemento.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA)){
						elementosExpeDescripcion.put(elemento.getTipoElemento() + elemento.getCodigoElemento(), ((Entrada) detalleElemento).getDescripcionTramite());
					}					
				}
				
		}else{
			throw new Exception("Tipo de elemento no permitido " + tipoElemento);
		}
		
		
		request.setAttribute("descripcionExpediente",descExpe);
		request.setAttribute("codigoExpediente",codigoExpe);
		request.setAttribute("elementosExpediente",elementosExpe);
		request.setAttribute("elementosExpedientePendientes",elementosExpePendientes);
		request.setAttribute("elementosExpeDescripcion",elementosExpeDescripcion);
		
		if (this.getDatosSesion(request).getNivelAutenticacion() != 'A'){
			return mapping.findForward("success");
		}else{
			return mapping.findForward("successAnonimo");
		}
	}	
	
}
