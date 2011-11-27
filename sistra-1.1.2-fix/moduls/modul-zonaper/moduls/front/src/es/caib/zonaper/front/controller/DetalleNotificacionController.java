package es.caib.zonaper.front.controller;

import java.io.ByteArrayInputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.util.ConvertUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosAsunto;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.DatosOrigen;
import es.caib.xml.registro.factoria.impl.DireccionCodificada;
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
			// Notificacion pendiente
			AvisoNotificacion avisoNotificacion = getAvisoNotificacion(notificacion);
			request.setAttribute( "avisoNotificacion", avisoNotificacion );
			AsientoRegistral acuseRecibo = obtenerAsientoAcuseRecibo (this.getDatosSesion( request), notificacion, asiento );
			String xmlAcuseRecibo = ServicioRegistroXML.crearFactoriaObjetosXML().guardarAsientoRegistral(acuseRecibo);
			request.setAttribute( "xmlAsientoAcuseRecibo", ConvertUtil.cadenaToBase64UrlSafe(xmlAcuseRecibo));						
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
	/**
	 * Genera acuse de recibo
	 * 
	 * @param datosSesion
	 * @param notificacion
	 * @param asientoNotificacion
	 * @return
	 * @throws Exception
	 */
	private AsientoRegistral obtenerAsientoAcuseRecibo( DatosSesion datosSesion, NotificacionTelematica notificacion, AsientoRegistral asientoNotificacion ) throws Exception
	{
		FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
		factoria.setEncoding(ConstantesXML.ENCODING);
						
		// Crear asiento registral
		AsientoRegistral asiento = factoria.crearAsientoRegistral();
		asiento.setVersion("1.0");
		
		// Crear datos origen
		DatosOrigen dOrigen = factoria.crearDatosOrigen();
		dOrigen.setCodigoEntidadRegistralOrigen ( asientoNotificacion.getDatosOrigen().getCodigoEntidadRegistralOrigen() );				
		dOrigen.setTipoRegistro( new Character( ConstantesAsientoXML.TIPO_ACUSE_RECIBO )  );
		dOrigen.setNumeroRegistro (notificacion.getNumeroRegistro() );
		dOrigen.setFechaEntradaRegistro( notificacion.getFechaRegistro() );
		asiento.setDatosOrigen (dOrigen);
		
		// Crear datos representante
		DatosInteresado dInteresadoRpte,dInteresadoRpdo=null;
		dInteresadoRpte = factoria.crearDatosInteresado();
		dInteresadoRpte.setNivelAutenticacion( new Character( datosSesion.getNivelAutenticacion() ) );
		dInteresadoRpte.setUsuarioSeycon( notificacion.getUsuarioSeycon() );						
		dInteresadoRpte.setTipoInteresado (ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE);		
		dInteresadoRpte.setTipoIdentificacion (Util.obtenerTipoIdentificacion( notificacion.getNifRepresentante() ) );
		dInteresadoRpte.setNumeroIdentificacion (notificacion.getNifRepresentante());
		dInteresadoRpte.setFormatoDatosInteresado (ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);
		dInteresadoRpte.setIdentificacionInteresado (notificacion.getNombreRepresentante());
		
		DireccionCodificada  direccionRpte = factoria.crearDireccionCodificada();
		direccionRpte.setCodigoProvincia( direccionRpte.getCodigoProvincia() );
		direccionRpte.setCodigoMunicipio( direccionRpte.getCodigoMunicipio() );
		direccionRpte.setPaisOrigen( direccionRpte.getPaisOrigen() );
		direccionRpte.setNombreMunicipio( direccionRpte.getNombreMunicipio() );
		direccionRpte.setNombreProvincia( direccionRpte.getNombreProvincia() );
		dInteresadoRpte.setDireccionCodificada( direccionRpte );
		
		asiento.getDatosInteresado().add(dInteresadoRpte);
		
		// Crear datos de representado si necesario
		if ( !StringUtils.isEmpty( notificacion.getNifRepresentado() ) )
		{
			dInteresadoRpdo = factoria.crearDatosInteresado();
			dInteresadoRpdo.setTipoInteresado( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO );
			dInteresadoRpdo.setTipoIdentificacion( Util.obtenerTipoIdentificacion( notificacion.getNifRepresentado() ));
			dInteresadoRpdo.setNumeroIdentificacion( notificacion.getNifRepresentado() );
			dInteresadoRpdo.setFormatoDatosInteresado( ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM );
			dInteresadoRpdo.setIdentificacionInteresado( notificacion.getNombreRepresentado() );
			asiento.getDatosInteresado().add(dInteresadoRpdo);
		}
		
		// Crear datos asunto
		DatosAsunto dAsunto = asientoNotificacion.getDatosAsunto();		
		dAsunto.setIdiomaAsunto (asientoNotificacion.getDatosAsunto().getIdiomaAsunto());
		dAsunto.setTipoAsunto (asientoNotificacion.getDatosAsunto().getTipoAsunto());
		dAsunto.setExtractoAsunto ("Acuse de recibo de notificación");
		dAsunto.setCodigoOrganoDestino ( asientoNotificacion.getDatosAsunto().getCodigoOrganoDestino()  );
		dAsunto.setDescripcionOrganoDestino( asientoNotificacion.getDatosAsunto().getDescripcionOrganoDestino() );
		dAsunto.setCodigoUnidadAdministrativa( asientoNotificacion.getDatosAsunto().getCodigoUnidadAdministrativa() );		
		asiento.setDatosAsunto (dAsunto);
		
		// Crear Anexo con el aviso		
		DatosAnexoDocumentacion anexoAvisoNotificacion = Util.obtenerAnexoAsientoDeTipo( asientoNotificacion, ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION );
		asiento.getDatosAnexoDocumentacion().add( anexoAvisoNotificacion );
		
		return asiento;
	}


}
