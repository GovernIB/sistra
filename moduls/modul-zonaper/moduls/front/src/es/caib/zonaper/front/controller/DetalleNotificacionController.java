package es.caib.zonaper.front.controller;

import java.io.ByteArrayInputStream;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.util.ConvertUtil;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.zonaper.front.util.Util;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class DetalleNotificacionController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		NotificacionTelematica notificacion = (NotificacionTelematica) request.getAttribute("notificacion");
		ElementoExpediente elementoExpediente = (ElementoExpediente) request.getAttribute("elementoExpediente");
		
		// Obtenemos asiento registral
		AsientoRegistral asiento = getAsientoRegistral(notificacion);
		
		if (notificacion.getFechaAcuse() == null){
			
			// Genera xml de acuse de recibo
			AsientoRegistral acuseRecibo = DelegateUtil.getNotificacionTelematicaDelegate().generarAcuseReciboNotificacion(notificacion.getCodigo(), false);
			String xmlAcuseRecibo = ServicioRegistroXML.crearFactoriaObjetosXML().guardarAsientoRegistral(acuseRecibo);
			request.setAttribute( "xmlAsientoAcuseRecibo", ConvertUtil.cadenaToBase64UrlSafe(xmlAcuseRecibo));
			
			// Obtenemos aviso
			AvisoNotificacion avisoNotificacion = getAvisoNotificacion(notificacion);
			request.setAttribute( "avisoNotificacion", avisoNotificacion );
			
			// Comprobamos fecha fin plazo (en caso de que se controle entrega)
			if (isControlarEntregaNotificaciones(request)) {
				request.setAttribute( "controlEntrega", "S" );
				Date ahora = new Date();
				if (notificacion.getFechaFinPlazo() != null && ahora.after(notificacion.getFechaFinPlazo())) {
					request.setAttribute( "rechazada", "S" );
				} else {
					request.setAttribute( "rechazada", "N" );
				}
			} else {
				request.setAttribute( "controlEntrega", "N" );
				request.setAttribute( "rechazada", "N" );
			}
			
		}else{
			// Notificacion recibida: asiento + oficio			
			OficioRemision oficio = getOficioRemision(notificacion);
			request.setAttribute( "asiento", asiento );
			request.setAttribute( "oficioNotificacion", oficio );
		}
		
		// Comprobamos si tiene permisos para abrir la notificacion: es el interesado o es un delegado
		String puedeAbrir = "S";
		DatosSesion datosSesion = this.getDatosSesion(request);
		if (datosSesion.getPerfilAcceso().equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO)){
			if (datosSesion.getPermisosDelegacion().indexOf(ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION) == -1){
				puedeAbrir = "N";
			}
		}
		
		
		request.setAttribute("codigoExpediente",elementoExpediente.getExpediente().getIdExpediente());
		request.setAttribute("descExpediente",elementoExpediente.getExpediente().getDescripcion());
		request.setAttribute( "unidadAdministrativa", DelegateUtil.getDominiosDelegate().obtenerDescripcionUA(asiento.getDatosAsunto().getCodigoUnidadAdministrativa()));
		request.setAttribute( "representado",Util.obtenerDatosRepresentado(asiento));
		request.setAttribute( "representante",Util.obtenerDatosRepresentante(asiento));
		request.setAttribute( "puedeAbrir",puedeAbrir);
		
	}
	
	/**
	 * Obtiene aviso notificacion
	 * 
	 * @param entrada
	 * @return
	 * @throws Exception
	 */
	private AvisoNotificacion getAvisoNotificacion( NotificacionTelematica notificacion ) throws Exception
	{
		// Obtenemos datos del RDS
		DocumentoRDS docRDS = null;
		try{
			docRDS = Util.consultarDocumentoRDS(notificacion.getCodigoRdsAviso(),notificacion.getClaveRdsAviso());
		}catch (Exception ex){
			throw new Exception("Error consultando del RDS el aviso de notificacion",ex);
		}
		
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(docRDS.getDatosFichero());
		try{
			FactoriaObjetosXMLAvisoNotificacion factoriaAvisoNotificacion = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
			bis = new ByteArrayInputStream(docRDS.getDatosFichero());
			AvisoNotificacion avisoNotificacion = factoriaAvisoNotificacion.crearAvisoNotificacion(bis);
			return avisoNotificacion;
		}catch (Exception ex){
			throw new Exception("Error parseando aviso notificacion",ex);
		}finally{
			bis.close();
		}
	}
	
	/**
	 * Obtiene oficion remision
	 * 
	 * @param entrada
	 * @return
	 * @throws Exception
	 */
	private OficioRemision getOficioRemision( NotificacionTelematica notificacion ) throws Exception
	{
		// Obtenemos datos del RDS
		DocumentoRDS docRDS = null;
		try{
			docRDS = Util.consultarDocumentoRDS(notificacion.getCodigoRdsOficio(),notificacion.getClaveRdsOficio());
		}catch (Exception ex){
			throw new Exception("Error consultando del RDS el oficio de remision",ex);
		}
		
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(docRDS.getDatosFichero());
		try{
			FactoriaObjetosXMLOficioRemision factoria = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
			bis = new ByteArrayInputStream(docRDS.getDatosFichero());
			OficioRemision oficio = factoria.crearOficioRemision(bis);
			return oficio;
		}catch (Exception ex){
			throw new Exception("Error parseando oficio de remision",ex);
		}finally{
			bis.close();
		}
	}
	
	/**
	 * Obtiene asiento
	 * 
	 * @param entrada
	 * @return
	 * @throws Exception
	 */
	private AsientoRegistral getAsientoRegistral( NotificacionTelematica notificacion ) throws Exception
	{
		// Obtenemos datos del RDS
		DocumentoRDS docRDS = null;
		try{
			docRDS = Util.consultarDocumentoRDS( notificacion.getCodigoRdsAsiento(), notificacion.getClaveRdsAsiento());
		}catch (Exception ex){
			throw new Exception("Error consultando del RDS el asiento",ex);
		}
		
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(docRDS.getDatosFichero());
		try{
			FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			AsientoRegistral asiento = factoria.crearAsientoRegistral( bis );			
			return asiento;
		}catch (Exception ex){
			throw new Exception("Error parseando asiento registral",ex);
		}finally{
			bis.close();
		}
	}
	


}
